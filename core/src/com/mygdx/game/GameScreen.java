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
        playerShip = new Ship(2, 3, WORLD_WIDTH/2, WORLD_HEIGHT/4, 10, 10, playerShipTextureRegion,playerShieldTextureRegion );
        enemyShip = new Ship(2, 1, WORLD_WIDTH/2, WORLD_HEIGHT*3/4, 10, 10, enemyShipTextureRegion,enemyShieldTextureRegion );

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

        // enemy ship
        enemyShip.draw(spriteBatch);

        // player ship
        playerShip.draw(spriteBatch);

        // laser

        // explosion

        spriteBatch.end();
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
