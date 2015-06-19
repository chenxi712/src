package com.fxb.razor.flash;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;

public class FlashPlayer implements Flash
{
    static final int pause = 1;
    static final int play = 0;
    static final int stop = 2;
    public float alphaMultiplier;
    float alphaRunTime;
    float alphaTime;
    public final FlashElements flashElement;
    boolean inScreen;
    boolean isAlpha;
    boolean isEnd;
    private boolean isLooping;
    boolean isResponeTimeEvent;
    public LayerPlayer[] layerPlayer;
    public FlashListener listener;
    int playCount;
    public Vector2 position;
    float responseTime;
    public Vector2 rotate;
    private float runTime;
    private float scale;
    private int state;
    TextureAtlas texture;
    private float timeScale;
    
    public FlashPlayer(final FlashElements flashElements, final TextureAtlas textureAtlas, final Vector2 vector2) {
        this(flashElements, textureAtlas, vector2, false);
    }
    
    public FlashPlayer(final FlashElements flashElements, final TextureAtlas textureAtlas, final Vector2 vector2, final boolean b) {
        this(flashElements, textureAtlas, vector2, b, false);
    }
    
    public FlashPlayer(final FlashElements flashElement, final TextureAtlas texture, final Vector2 vector2, final boolean isLooping, final boolean inScreen) {
        this.alphaMultiplier = 1.0f;
        this.rotate = new Vector2(0.0f, 0.0f);
        this.inScreen = true;
        this.isEnd = false;
        this.playCount = 0;
        this.alphaTime = 0.0f;
        this.alphaRunTime = this.alphaTime;
        this.isAlpha = false;
        this.scale = 1.0f;
        this.timeScale = 1.0f;
        this.listener = new FlashListener() {
            @Override
            public void playerEnd() {
            }
            
            @Override
            public void responeTimeEvent() {
            }
        };
        this.runTime = 0.0f;
        this.responseTime = 0.0f;
        this.isResponeTimeEvent = false;
        this.flashElement = flashElement;
        this.texture = texture;
        this.isLooping = isLooping;
        this.inScreen = inScreen;
        this.state = 2;
        this.initLayerPlayer(flashElement, texture);
        this.position = new Vector2(vector2);
    }
    
    public FlashPlayer(final String s, final TextureAtlas textureAtlas, final Vector2 vector2) {
        this(Fanimation2.getFanimation(s), textureAtlas, vector2, false);
    }
    
    public FlashPlayer(final String s, final TextureAtlas textureAtlas, final Vector2 vector2, final boolean b) {
        this(Fanimation2.getFanimation(s), textureAtlas, vector2, b, false);
    }
    
    public FlashPlayer(final String s, final TextureAtlas textureAtlas, final Vector2 vector2, final boolean b, final boolean b2) {
        this(Fanimation2.getFanimation(s), textureAtlas, vector2, b, b2);
    }
    
    private void initLayerPlayer(final FlashElements flashElements, final TextureAtlas textureAtlas) {
        this.layerPlayer = new LayerPlayer[flashElements.layersNumer];
        for (int i = 0; i < flashElements.layersNumer; ++i) {
            this.layerPlayer[i] = new LayerPlayer(flashElements.layers[i], textureAtlas, this);
        }
    }
    
    private void initResponseParamer() {
        this.responseTime = 0.0f;
        this.isResponeTimeEvent = false;
    }
    
    private void lisenterResponeTimeEvent(final float n) {
        if (this.responseTime > 0.0f && n > this.responseTime && this.isResponeTimeEvent) {
            this.listener.responeTimeEvent();
            this.initResponseParamer();
        }
    }
    
    public float GetEndTime() {
        return this.flashElement.endTime;
    }
    
    public float GetPlayPercent() {
        return this.runTime / this.flashElement.endTime;
    }
    
    public void SetFlipX(final boolean b) {
        for (int i = 0; i < this.layerPlayer.length; ++i) {
            this.layerPlayer[i].SetFlipX(b);
        }
    }
    
    public void SetOrigin(final float n, final float n2) {
        for (int i = 0; i < this.layerPlayer.length; ++i) {
            this.layerPlayer[i].SetOrigin(n, n2);
        }
    }
    
    public void SetTimeScale(final float timeScale) {
        this.timeScale = timeScale;
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s) {
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            this.layerPlayer[i].changeRegion(textureAtlas, s);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String s, final String s2) {
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            this.layerPlayer[i].changeRegion(textureAtlas, s, s2);
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2) {
        if (array != null && array2 != null && array.length == array2.length) {
            for (int i = 0; i < this.flashElement.layersNumer; ++i) {
                this.layerPlayer[i].changeRegion(textureAtlas, array, array2);
            }
        }
    }
    
    public void changeRegion(final TextureAtlas textureAtlas, final String[] array, final String[] array2, final String s) {
        if (array != null && array2 != null && array.length == array2.length) {
            for (int i = 0; i < this.flashElement.layersNumer; ++i) {
                this.layerPlayer[i].changeRegion(textureAtlas, array, array2, s);
            }
        }
    }
    
    public void drawFlash(final SpriteBatch spriteBatch) {
        this.drawFlash(spriteBatch, this.position);
    }
    
    public void drawFlash(final SpriteBatch spriteBatch, final float n, final float n2) {
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, this.alphaMultiplier);
        }
        if (this.state == 0 || this.state == 1) {
            for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                if (this.isEnd || this.isAlpha) {
                    this.layerPlayer[i].drawLayerEnd(spriteBatch, n, n2, this.scale);
                }
                else {
                    this.layerPlayer[i].updataLayer(this.runTime);
                    this.layerPlayer[i].drawLayer(spriteBatch, n, n2, this.scale);
                }
            }
        }
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void drawFlash(final SpriteBatch spriteBatch, final float n, final float n2, float runTime, final boolean b, final float n3) {
        if (this.state == 0 || this.state == 1) {
            for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                if (this.isEnd) {
                    this.layerPlayer[i].drawLayerEnd(spriteBatch, n, n2, n3);
                }
                else {
                    if (b) {
                        final LayerPlayer layerPlayer = this.layerPlayer[i];
                        runTime = this.runTime;
                        layerPlayer.updataLayerBack(runTime, this.inScreen && !this.isLooping);
                    }
                    else {
                        this.layerPlayer[i].updataLayer(this.runTime);
                    }
                    this.layerPlayer[i].drawLayer(spriteBatch, n, n2, n3);
                }
            }
        }
    }
    
    public void drawFlash(final SpriteBatch spriteBatch, final float n, final boolean b, final float n2) {
        this.drawFlash(spriteBatch, this.position, n, b, n2);
    }
    
    public void drawFlash(final SpriteBatch spriteBatch, final Vector2 vector2) {
        this.drawFlash(spriteBatch, vector2.x, vector2.y);
    }
    
    public void drawFlash(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final boolean b, final float n2) {
        this.drawFlash(spriteBatch, vector2.x, vector2.y, n, b, n2);
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final float n, final float n2, final float n3) {
        this.drawFlashRotation(spriteBatch, this.position, n, n2, n3);
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final float n4, final float n5) {
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, this.alphaMultiplier);
        }
        if (this.state == 0 || this.state == 1) {
            for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                if (this.isEnd) {
                    this.layerPlayer[i].drawLayerEnd(spriteBatch, n, n2, n3, n4, n5, this.scale);
                }
                else {
                    this.layerPlayer[i].updataLayer(this.runTime);
                    this.layerPlayer[i].drawLayer(spriteBatch, n, n2, n3, n4, n5, this.scale);
                }
            }
        }
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final float n4, final float n5, final boolean b) {
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, this.alphaMultiplier);
        }
        if (this.state == 0 || this.state == 1) {
            for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                if (this.isEnd || b) {
                    this.layerPlayer[i].drawLayerEnd(spriteBatch, n, n2, n3, n4, n5, this.scale);
                }
                else {
                    this.layerPlayer[i].updataLayer(this.runTime);
                    this.layerPlayer[i].drawLayer(spriteBatch, n, n2, n3, n4, n5, this.scale);
                }
            }
        }
        if (this.alphaMultiplier != 1.0f) {
            spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final float n, final float n2, final float n3, final boolean b) {
        this.drawFlashRotation(spriteBatch, this.position, n, n2, n3, b);
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final float n2, final float n3) {
        this.drawFlashRotation(spriteBatch, vector2.x, vector2.y, n, n2, n3);
    }
    
    public void drawFlashRotation(final SpriteBatch spriteBatch, final Vector2 vector2, final float n, final float n2, final float n3, final boolean b) {
        this.drawFlashRotation(spriteBatch, vector2.x, vector2.y, n, n2, n3, b);
    }
    
    public LayerPlayer findLayerPlayer(final String s) {
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            if (s.equals(this.layerPlayer[i].layer.layerName)) {
                return this.layerPlayer[i];
            }
        }
        return null;
    }
    
    public int getFrameIndex() {
        return (int)(this.runTime / 0.04f);
    }
    
    public float getHeight() {
        return this.flashElement.height;
    }
    
    public boolean getInScreen(final boolean b) {
        return this.inScreen;
    }
    
    public int getPlayCount() {
        return this.playCount;
    }
    
    @Override
    public Vector2 getPosition() {
        return this.position;
    }
    
    @Override
    public float getTimePosition() {
        return this.runTime;
    }
    
    public float getWidth() {
        return this.flashElement.width;
    }
    
    public boolean isAlpha() {
        return this.isAlpha;
    }
    
    public boolean isEnd() {
        return this.isEnd;
    }
    
    @Override
    public boolean isLooping() {
        return this.isLooping;
    }
    
    @Override
    public boolean isPlaying() {
        return this.state == 0;
    }
    
    public boolean isStop() {
        return this.state == 2;
    }
    
    @Override
    public void pause() {
        if (this.state == 0) {
            this.state = 1;
        }
    }
    
    @Override
    public void play() {
        this.state = 0;
        this.isEnd = false;
    }
    
    public void rePlay() {
        this.state = 0;
        this.isEnd = false;
        this.runTime = 0.0f;
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            this.layerPlayer[i].reset();
        }
        this.initResponseParamer();
    }
    
    public void reset() {
        this.state = 2;
        this.isEnd = false;
        this.runTime = 0.0f;
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            this.layerPlayer[i].reset();
        }
        this.initResponseParamer();
    }
    
    public void selectRegion(final String[] array) {
        for (int i = 0; i < this.flashElement.layersNumer; ++i) {
            this.layerPlayer[i].selectRegion(array);
        }
    }
    
    public void setAlphaTime(final float alphaTime) {
        this.alphaTime = alphaTime;
        this.alphaRunTime = this.alphaTime;
    }
    
    public void setFrameIndex(final int frameIndex) {
        this.runTime = this.layerPlayer[0].getFrameStartTime(frameIndex);
        for (int i = 0; i < this.layerPlayer.length; ++i) {
            this.layerPlayer[i].setFrameIndex(frameIndex);
        }
    }
    
    public void setInScreen(final boolean inScreen) {
        this.inScreen = inScreen;
    }
    
    @Override
    public void setLooping(final boolean isLooping) {
        this.isLooping = isLooping;
    }
    
    public void setPlayPercent(final float n) {
        this.runTime = this.GetEndTime() * n;
    }
    
    public void setPosition(final float n, final float n2) {
        this.position.set(n, n2);
    }
    
    @Override
    public void setPosition(final Vector2 vector2) {
        this.position.set(vector2);
    }
    
    public void setResponseTime(final float responseTime) {
        this.responseTime = responseTime;
        this.isResponeTimeEvent = true;
    }
    
    public void setRunTime(final float runTime) {
        this.runTime = runTime;
    }
    
    public void setScale(final float scale) {
        this.scale = scale;
    }
    
    @Override
    public void stop() {
        this.state = 2;
        this.runTime = 0.0f;
        this.playCount = 0;
    }
    
    public void updateAlphaTime(final float n) {
        if (this.state == 0) {
            this.alphaRunTime -= n;
            if (this.alphaTime - this.alphaRunTime > 0.5f) {
                this.alphaMultiplier = this.alphaRunTime / (this.alphaTime - 0.5f);
            }
            else {
                this.alphaMultiplier = 1.0f;
            }
            if (this.alphaRunTime <= 0.0f) {
                this.alphaTime = 0.0f;
                this.isAlpha = false;
                this.isEnd = true;
                this.state = 2;
            }
        }
    }
    
    public void updateRunTime(final float n) {
        float n2 = n;
        if (this.timeScale != 1.0f) {
            n2 = n * this.timeScale;
        }
        if (this.state == 0) {
            if (!this.isAlpha && !this.isLooping && this.runTime >= this.flashElement.endTime) {
                this.isAlpha = true;
            }
            if (this.isAlpha) {
                this.updateAlphaTime(n2);
            }
            else if (this.runTime >= this.flashElement.endTime) {
                this.runTime = 0.0f;
                if (!this.isLooping) {
                    this.isEnd = true;
                    if (!this.inScreen) {
                        this.state = 2;
                    }
                    this.listener.playerEnd();
                }
                else {
                    ++this.playCount;
                    for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                        this.layerPlayer[i].reset();
                    }
                    this.listener.playerEnd();
                }
            }
            else {
                this.runTime += n2;
            }
            this.lisenterResponeTimeEvent(this.runTime);
        }
    }
    
    public void updateRunTime(final float n, final float n2) {
        this.updateRunTime(n * n2);
    }
    
    public void updateRunTimeBack(final float n) {
        if (this.state == 0) {
            this.runTime -= n;
            if (this.runTime > 0.0f) {
                this.runTime -= n;
                return;
            }
            this.runTime = this.flashElement.endTime;
            if (!this.isLooping) {
                this.isEnd = true;
                if (!this.inScreen) {
                    this.state = 2;
                }
            }
            else {
                for (int i = this.layerPlayer.length - 1; i >= 0; --i) {
                    this.layerPlayer[i].resetBack();
                }
            }
        }
    }
    
    public void updateRunTimeBack(final float n, final float n2) {
        this.updateRunTimeBack(n * n2);
    }
}
