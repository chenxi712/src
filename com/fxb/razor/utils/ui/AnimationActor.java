package com.fxb.razor.utils.ui;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;

public class AnimationActor extends Actor
{
    private Animation animation;
    private float curTime;
    public boolean isAutoFree;
    private boolean isChangeWidth;
    private boolean isLoop;
    private int playCount;
    
    public AnimationActor(final float n, final Array<? extends TextureRegion> array) {
        this.curTime = 1000.0f;
        this.isLoop = false;
        this.isChangeWidth = false;
        this.isAutoFree = false;
        this.animation = new Animation(n, array);
        this.setSize(((TextureRegion)array.get(0)).getRegionWidth(), ((TextureRegion)array.get(0)).getRegionHeight());
        this.playCount = 0;
    }
    
    public static AnimationActor createAnimation(final Group group, final float n, final Array<? extends TextureRegion> array, final float n2, final float n3, final float scale) {
        final AnimationActor animationActor = new AnimationActor(n, array);
        animationActor.setScale(scale);
        animationActor.setPosition(n2, n3);
        group.addActor(animationActor);
        return animationActor;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.curTime += n;
        if (this.isAutoFree && this.curTime > this.animation.animationDuration) {
            this.remove();
            Pools.free(this);
        }
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, float width) {
        super.draw(spriteBatch, width);
        if ((this.isLoop || this.curTime <= this.animation.animationDuration) && (!this.isLoop || this.playCount == 0 || this.curTime <= this.animation.animationDuration * this.playCount)) {
            final TextureAtlas.AtlasRegion atlasRegion = (TextureAtlas.AtlasRegion)this.animation.getKeyFrame(this.curTime, this.isLoop);
            if (atlasRegion != null) {
                if (this.isChangeWidth) {
                    width = this.getWidth();
                }
                else {
                    width = atlasRegion.getRegionWidth();
                }
                spriteBatch.draw(atlasRegion, atlasRegion.offsetX + this.getX(), atlasRegion.offsetY + this.getY(), this.getOriginX() - atlasRegion.offsetX, this.getOriginY() - atlasRegion.offsetY, width, atlasRegion.getRegionHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
            }
        }
    }
    
    public boolean isPlaying() {
        return (this.isLoop || this.curTime <= this.animation.animationDuration) && (!this.isLoop || this.playCount == 0 || this.curTime <= this.animation.animationDuration * this.playCount);
    }
    
    public void setChangeWidth(final boolean isChangeWidth) {
        this.isChangeWidth = isChangeWidth;
    }
    
    public void setLoop(final boolean isLoop) {
        this.isLoop = isLoop;
    }
    
    public void setOver() {
        this.curTime = 1000.0f;
    }
    
    public void setPlayCount(final int playCount) {
        this.playCount = playCount;
    }
    
    public void setStart() {
        this.curTime = 0.0f;
    }
}
