package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.ListIterator;

public class GameScreen implements Screen {

    // screen
    Camera camera;
    Viewport viewport;

    // graphic
    SpriteBatch spriteBatch;
//    Texture background;
    TextureAtlas textureAtlas;
    TextureRegion[] backgrounds;

    TextureRegion playerShipTextureRegion, playerShieldTextureRegion, enemyShipTextureRegion, enemyShieldTextureRegion, playerLaserTextureRegion, enemyLaserTextureRegion;

    // timer
//    int backgroundOffset ;
    float[] backgroundOffsets = {0, 0, 0, 0};
    float backgroundMaxScrollingSpeed ;

    // world parameter
    private final int WORLD_WIDTH = 72;
    private final int WORLD_HEIGHT = 128 ;

    // lasers
    LinkedList<Laser> playerLaserList ;
    LinkedList<Laser> enemyLaserList;


    Ship playerShip ;
    Ship enemyShip;

    GameScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH , WORLD_HEIGHT, camera);

//        background = new Texture("darkPurpleStarscape.png");
//        backgroundOffset = 0;

        textureAtlas = new TextureAtlas("immages.atlas");


        backgrounds = new TextureRegion[4];
        backgrounds[0] = textureAtlas.findRegion("Starscape00");
        backgrounds[1] = textureAtlas.findRegion("Starscape01");
        backgrounds[2] = textureAtlas.findRegion("Starscape02");
        backgrounds[3] = textureAtlas.findRegion("Starscape03");

        backgroundMaxScrollingSpeed = (float) (WORLD_HEIGHT) / 4;

        // initialize texture
        playerShipTextureRegion = textureAtlas.findRegion("playerShip1_blue");
        playerShieldTextureRegion = textureAtlas.findRegion("shield1");

        enemyShieldTextureRegion = textureAtlas.findRegion("shield2");
        enemyShipTextureRegion = textureAtlas.findRegion("enemyBlack3");
        enemyShieldTextureRegion.flip(false, true);

        playerLaserTextureRegion = textureAtlas.findRegion("laserRed01");
        enemyLaserTextureRegion = textureAtlas.findRegion("laserBlue03");


        // create ships
        playerShip = new PlayerShip(2, 3, WORLD_WIDTH/2, WORLD_HEIGHT/4, 10, 10, playerShipTextureRegion,playerShieldTextureRegion, playerLaserTextureRegion, 0.4f,4f, 45, 0.5f );
        enemyShip = new EnemyShip(2, 1, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 10, 10, enemyShipTextureRegion,enemyShieldTextureRegion, enemyLaserTextureRegion, 0.3f,5f, 50, 0.8f );

        playerLaserList = new LinkedList<>();
        enemyLaserList = new LinkedList<>();

        spriteBatch = new SpriteBatch();


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.begin();
        // scrolling background
        renderBackground(delta);

        playerShip.update(delta);
        enemyShip.update(delta);
        // enemy ship
        enemyShip.draw(spriteBatch);

        // player ship
        playerShip.draw(spriteBatch);

       renderLaser(delta);

        // explosion
        renderExplosion(delta);

        // detect collision between laser and ship
        detectCollision();

        spriteBatch.end();
    }

    private void detectCollision() {

        ListIterator<Laser> listIterator = playerLaserList.listIterator();
        while (listIterator.hasNext()){
            Laser laser = listIterator.next();
            if(enemyShip.intersects(laser.getBoundingBox())){
                enemyShip.hit(laser);
                listIterator.remove();
            }
        }

        ListIterator<Laser> iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            if(playerShip.intersects(laser.getBoundingBox())){
                playerShip.hit(laser);
                iterator.remove();
            }
        }

    }

    private void renderExplosion(float delta) {

    }

    private void renderLaser(float delta) {

        // laser
        // create new Lasers
        if(playerShip.canFireLaser()){
            Laser[] lasers = playerShip.fireLaser();
            for (Laser laser : lasers){
                playerLaserList.add(laser);

            }
        }

        // enemy lasers
        if(enemyShip.canFireLaser()){
            Laser[] lasers = enemyShip.fireLaser();
            for (Laser laser : lasers){
                enemyLaserList.add(laser);

            }
        }

        // remove old lasers

        ListIterator<Laser> listIterator = playerLaserList.listIterator();
        while (listIterator.hasNext()){
            Laser laser = listIterator.next();
            laser.draw(spriteBatch);
            laser.yPosition += laser.movementSpeed*delta;
            if(laser.yPosition>WORLD_HEIGHT){
                listIterator.remove();
            }
        }

        ListIterator<Laser> iterator = enemyLaserList.listIterator();
        while (iterator.hasNext()){
            Laser laser = iterator.next();
            laser.draw(spriteBatch);
            laser.yPosition -= laser.movementSpeed*delta;
            if(laser.yPosition + laser.height < 0 ){
                iterator.remove();
            }
        }

    }

    private void renderBackground(float delta) {
        backgroundOffsets[0] += delta * backgroundMaxScrollingSpeed / 8;
        backgroundOffsets[1] += delta * backgroundMaxScrollingSpeed / 4;
        backgroundOffsets[2] += delta * backgroundMaxScrollingSpeed / 2;
        backgroundOffsets[3] += delta * backgroundMaxScrollingSpeed ;

        for(int layer = 0; layer<backgroundOffsets.length; layer ++){
            if(backgroundOffsets[layer] > WORLD_HEIGHT) backgroundOffsets[layer] = 0;
            spriteBatch.draw(backgrounds[layer], 0, -backgroundOffsets[layer], WORLD_WIDTH, WORLD_HEIGHT);
            spriteBatch.draw(backgrounds[layer], 0, -backgroundOffsets[layer] + WORLD_HEIGHT, WORLD_WIDTH, WORLD_HEIGHT);
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        spriteBatch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
