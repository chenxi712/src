package com.gleejet.sun.flash;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class LayerPlayer
{
    private FramePlayer[] framePlayers;
    private int frameindex;
    public Layer layer;
    
    public LayerPlayer(final Layer layer, final TextureAtlas textureAtlas, final FlashPlayer flashPlayer) {
        this.frameindex = 0;
        this.layer = layer;
        this.framePlayers = new FramePlayer[layer.framesNumber];
        for (int i = 0; i < layer.framesNumber; ++i) {
            this.framePlayers[i] = new FramePlayer(layer.frames[i], textureAtlas, flashPlayer);
        }
    }
    
    public void SetFlipX(final boolean b) {
        for (int i = 0; i < this.framePlayers.length; ++i) {
            this.framePlayers[i].SetFlipX(b);
        }
    }
    
    public void SetOrigin(final float n, final float n2) {
        for (int i = 0; i < this.framePlayers.length; ++i) {
            this.framePlayers[i].SetOrigin(n, n2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s) {
        for (int i = 0; i < this.layer.framesNumber; ++i) {
            this.framePlayers[i].changeRegion(textureAtlas, s);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s, final String s2) {
        for (int i = 0; i < this.layer.framesNumber; ++i) {
            this.framePlayers[i].changeRegion(textureAtlas, s, s2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2) {
        for (int i = 0; i < this.layer.framesNumber; ++i) {
            this.framePlayers[i].changeRegion(textureAtlas, array, array2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2, final String s) {
        for (int i = 0; i < this.layer.framesNumber; ++i) {
            this.framePlayers[i].changeRegion(textureAtlas, array, array2, s);
        }
    }
    
    public void drawLayer(final SpriteBatch spriteBatch, final float n, final float n2, final float n3) {
        this.framePlayers[this.frameindex].drawFrame(spriteBatch, n, n2, n3);
    }
    
    public void drawLayer(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.framePlayers[this.frameindex].drawFrame(spriteBatch, n, n2, n3, n4, n5, n6);
    }
    
    public void drawLayer(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final float n2, final float n3, final float n4) {
        this.framePlayers[this.frameindex].drawFrame(spriteBatch, vector2, n, n2, n3, n4);
    }
    
    public void drawLayerEnd(final SpriteBatch spriteBatch, final float n, final float n2, final float n3) {
        this.framePlayers[this.layer.framesNumber - 1].drawFrame(spriteBatch, n, n2, n3);
    }
    
    public void drawLayerEnd(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        this.framePlayers[this.layer.framesNumber - 1].drawFrame(spriteBatch, n, n2, n3, n4, n5, n6);
    }
    
    public void drawLayerEnd(final SpriteBatch spriteBatch, final Vector2 vector2, final float n) {
        this.framePlayers[this.layer.framesNumber - 1].drawFrame(spriteBatch, vector2, n);
    }
    
    public void drawLayerEnd(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final float n2, final float n3, final float n4) {
        this.framePlayers[this.layer.framesNumber - 1].drawFrame(spriteBatch, vector2, n, n2, n3, n4);
    }
    
    public ElementPlayer findElementPlayer(final String s) {
        return null;
    }
    
    public int getFrameIndex() {
        return this.frameindex;
    }
    
    public float getFrameStartTime(final int n) {
        return this.layer.frames[n].startTime;
    }
    
    public void reset() {
        this.frameindex = 0;
    }
    
    public void resetBack() {
        this.frameindex = this.layer.framesNumber - 1;
    }
    
    public void selectRegion(final String[] array) {
        for (int i = 0; i < this.layer.framesNumber; ++i) {
            this.framePlayers[i].selectRegion(array);
        }
    }
    
    public void setFrameIndex(int n) {
        float n2;
        for (n2 = n, n = 0; n < this.layer.framesNumber && this.layer.frames[n].endTime < n2 * 0.04f; ++n) {}
        int frameindex;
        if ((frameindex = n) == this.layer.framesNumber) {
            frameindex = n - 1;
        }
        this.frameindex = frameindex;
    }
    
    public void updataLayer(final float n) {
        if (n >= this.layer.frames[this.frameindex].endTime) {
            ++this.frameindex;
        }
        if (this.frameindex >= this.layer.framesNumber) {
            this.frameindex = this.layer.framesNumber - 1;
        }
    }
    
    public void updataLayerBack(final float n, final boolean b) {
        if (n <= this.layer.frames[this.frameindex].startTime) {
            --this.frameindex;
        }
        if (this.frameindex < 0) {
            this.frameindex = 0;
        }
    }
}
