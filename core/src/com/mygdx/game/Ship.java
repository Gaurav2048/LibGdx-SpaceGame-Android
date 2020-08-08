package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class Ship {

    // ship charasteristics
        float movementSpeed;
        int shield;

        Rectangle boundingBox;

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
        this.boundingBox = new Rectangle(xPosition , yPosition, width, height);
    }

    public void update(float deltaTime){
        boundingBox.set(xPosition, yPosition, width,height);
            timeSinceLastShot += deltaTime;
    }

    public void translate(float xChange, float yChange){
        boundingBox.setPosition(boundingBox.x+xChange, boundingBox.y+yChange);
    }

    public boolean canFireLaser(){
        return  timeSinceLastShot - timeBetweenTheShots >= 0 ;
    }

    public boolean intersects(Rectangle rectangle){
        return boundingBox.overlaps(rectangle);
    }

    public void hit(Laser laser){
        if(shield > 0) shield --;

    }

    public abstract  Laser[] fireLaser();

    public void draw(Batch batch){
        batch.draw(shipTextureRegion, xPosition, yPosition, width, height);
        if(shield>0){
            batch.draw(shieldTextureRegion, xPosition, yPosition, width, height );
        }
    }

}
