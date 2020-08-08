package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PlayerShip extends Ship {

    public PlayerShip(float movementSpeed, int shield, float xPosition, float yPosition, float width, float height, TextureRegion shipTexture, TextureRegion shieldTexture, TextureRegion laserTextureRegion, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots) {
        super(movementSpeed, shield, xPosition, yPosition, width, height, shipTexture, shieldTexture, laserTextureRegion, laserWidth, laserHeight, laserMovementSpeed, timeBetweenShots);
    }

    @Override
    public Laser[] fireLaser() {
        Laser[] lasers = new Laser[2];
        lasers[0] = new Laser(xPosition+width*0.07f,yPosition+height*0.45f,laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        lasers[1] = new Laser(xPosition+width*0.93f,yPosition+height*0.45f,laserWidth, laserHeight, laserMovementSpeed, laserTextureRegion);
        timeSinceLastShot = 0 ;
        return lasers;
    }
}
