package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class EnemyShip extends Ship {

    public EnemyShip(float movementSpeed, int shield, float xPosition, float yPosition, float width, float height, TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTextureRegion, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots) {
        super(movementSpeed, shield, xPosition, yPosition, width, height, shipTexture, shieldTexture, laserTextureRegion, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];
        lasers[0] = new Laser(xPosition+width*0.18f,yPosition-laserHeight,laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        lasers[1] = new Laser(xPosition+width*0.82f,yPosition- laserHeight,laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0 ;
        return lasers;
    }

    @Override
    public void draw(Batch batch){
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield>0){
            batch.draw(shieldTextureRegion, xPosition, yPosition-height*0.2f, width, height );
        }
    }

}
