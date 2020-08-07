package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ship {

    // ship charasteristics
        float movementSpeed;
        int shield;

    // position and dimens
        float xPosition, yPosition ;
        float width, height;


    // graphics
        TextureRegion shipTexture, shieldTexture;

    public Ship(float movementSpeed,
                int shield, float xPosition,
                float yPosition, float width,
                float height, TextureRegion shipTexture,
                TextureRegion shieldTexture) {
        this.movementSpeed = movementSpeed;
        this.shield = shield;
        this.xPosition = xPosition - width/2;
        this.yPosition = yPosition - height/2;
        this.width = width;
        this.height = height;
        this.shipTexture = shipTexture;
        this.shieldTexture = shieldTexture;
    }

    public void draw(Batch batch){
        batch.draw(shipTexture, xPosition, yPosition, width, height);
        if(shield>0){
            batch.draw(shieldTexture, xPosition, yPosition, width, height );
        }



    }

}
