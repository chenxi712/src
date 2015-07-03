package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.*;

public class MyImage extends Actor
{
    TextureRegion region;
    
    public MyImage() {
    }
    
    public MyImage(final TextureRegion region) {
        this.setRegion(region);
    }
    
    public void draw(final SpriteBatch spriteBatch, float rotation) {
        if (this.region == null) {
            return;
        }
        final Color color = spriteBatch.getColor();
        final Color color2 = this.getColor();
        spriteBatch.setColor(color2.r, color2.g, color2.b, color2.a * rotation);
        rotation = this.getRotation();
        if (this.getScaleX() == 1.0f && this.getScaleY() == 1.0f && rotation == 0.0f) {
            spriteBatch.draw(this.region, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
        else {
            spriteBatch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), rotation);
        }
        spriteBatch.setColor(color);
    }
    
    public void flip(final boolean b, final boolean b2) {
        final TextureRegion region = new TextureRegion(this.region);
        region.flip(b, b2);
        this.region = region;
    }
    
    public TextureRegion getRegion() {
        return this.region;
    }
    
    public void setRegion(final TextureRegion region) {
        this.region = region;
        if (this.region != null) {
            this.setSize(this.region.getRegionWidth(), region.getRegionHeight());
        }
    }
}
