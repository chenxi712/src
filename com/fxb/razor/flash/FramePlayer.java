package com.fxb.razor.flash;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;

public class FramePlayer
{
    private ElementPlayer[] elementPlayers;
    
    public FramePlayer(final Frame frame, final TextureAtlas textureAtlas, final FlashPlayer flashPlayer) {
        this.elementPlayers = new ElementPlayer[frame.elementsNumber];
        for (int i = 0; i < frame.elementsNumber; ++i) {
            this.elementPlayers[i] = new ElementPlayer(frame.elements[i], textureAtlas, flashPlayer);
        }
    }
    
    public void SetFlipX(final boolean b) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].SetFlipX(b);
        }
    }
    
    public void SetOrigin(final float n, final float n2) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].SetOrigin(n, n2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].changeRegion(textureAtlas, s);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s, final String s2) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].changeRegion(textureAtlas, s, s2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].changeRegion(textureAtlas, array, array2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2, final String s) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].changeRegion(textureAtlas, array, array2, s);
        }
    }
    
    public void drawFrame(final SpriteBatch spriteBatch, final float n, final float n2, final float n3) {
        for (int i = this.elementPlayers.length - 1; i >= 0; --i) {
            this.elementPlayers[i].drawElement(spriteBatch, n, n2, n3);
        }
    }
    
    public void drawFrame(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final float n4, final float n5, final float n6) {
        for (int i = this.elementPlayers.length - 1; i >= 0; --i) {
            this.elementPlayers[i].drawElement(spriteBatch, n, n2, n3, n4, n5, n6);
        }
    }
    
    public void drawFrame(final SpriteBatch spriteBatch, final Vector2 vector2, final float n) {
        this.drawFrame(spriteBatch, vector2.x, vector2.y, n);
    }
    
    public void drawFrame(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final float n2, final float n3, final float n4) {
        this.drawFrame(spriteBatch, vector2.x, vector2.y, n, n2, n3, n4);
    }
    
    public ElementPlayer findElementPlayer(final String s) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            if (s.equals(this.elementPlayers[i].element.textureName)) {
                return this.elementPlayers[i];
            }
        }
        return null;
    }
    
    public void selectRegion(final String[] array) {
        for (int i = 0; i < this.elementPlayers.length; ++i) {
            this.elementPlayers[i].selectRegion(array);
        }
    }
}
