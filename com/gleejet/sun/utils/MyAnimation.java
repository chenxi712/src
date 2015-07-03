package com.gleejet.sun.utils;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;

public class MyAnimation extends Animation
{
    private float height;
    private boolean isValid;
    private float originX;
    private float originY;
    private float rotation;
    private float scale;
    private float startTime;
    private float width;
    private float x;
    private float y;
    
    public MyAnimation(final float n, final Array<? extends TextureRegion> array) {
        super(n, array);
        this.isValid = false;
        this.rotation = 0.0f;
        this.scale = 1.0f;
        this.startTime = 0.0f;
        this.y = 0.0f;
        this.x = 0.0f;
        this.width = ((TextureRegion)array.get(0)).getRegionWidth();
        this.height = ((TextureRegion)array.get(0)).getRegionHeight();
    }
    
    public void draw(final SpriteBatch spriteBatch, float n) {
        if (this.isValid) {
            n -= this.startTime;
            if (n < this.animationDuration) {
                final TextureRegion keyFrame = this.getKeyFrame(n);
                if (this.rotation != 0.0f || this.scale != 1.0f) {
                    spriteBatch.draw(keyFrame, this.x, this.y, this.originX, this.originY, this.width, this.height, this.scale, this.scale, this.rotation);
                    return;
                }
                spriteBatch.draw(keyFrame, this.x, this.y, this.width, this.height);
            }
            return;
        }
        this.isValid = false;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public void setOrigion(final float originX, final float originY) {
        this.originX = originX;
        this.originY = originY;
    }
    
    public void setPosition(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setRotation(final float rotation) {
        this.rotation = rotation;
    }
    
    public void setScale(final float scale) {
        this.scale = scale;
    }
    
    public void setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
    }
    
    public void setStartTime(final float startTime) {
        this.startTime = startTime;
    }
    
    public void setValid() {
        this.isValid = true;
    }
}
