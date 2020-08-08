package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Ship {

    // ship charasteristics
        float movementSpeed;
        int shield;



    // position and dimens
        float xPosition, yPosition ;
        float width, height;

        float laserWidth , laserHeight;
        float timeBetweenTheShots;
        float timeSinceLastShot = 0;
        float laserMovementSpeed ;

    // graphics
        TextureRegion shipTextureRegion, shieldTextureRegion, laserTextureRegion;

    public Ship(float movementSpeed,
                int shield, float xPosition,
                float yPosition, float width,
                float height, TextureRegion shipTexture,
                TextureRegion shieldTexture, TextureRegion laserTextureRegion, float laserWidth, float laserHeight, float laserMovementSpeed, float timeBetweenShots) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xPosition - width/2;
        this.yPosition = yPosition - height/2;
        this.width = width;
        this.height = height;
        this.shipTextureRegion = shipTexture;
        this.shieldTextureRegion = shieldTexture;
        this.laserTextureRegion = laserTextureRegion;
        this.laserWidth = laserWidth;
        this.laserHeight = laserHeight;
        this.laserMovementSpeed = laserMovementSpeed;
        this.timeBetweenTheShots = timeBetweenShots;
    }

    public void update(float deltaTime){
            timeSinceLastShot += deltaTime;
    }

    public boolean canFireLaser(){
        return  timeSinceLastShot - timeBetweenTheShots >= 0 ;
    }

    public abstract  Laser[] fireLaser();

    public void draw(Batch batch){
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield>0){
            batch.draw(shieldTextureRegion, xPosition, yPosition, width, height );
        }
    }

}
