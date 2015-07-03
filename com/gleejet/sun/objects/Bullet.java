package com.gleejet.sun.objects;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.math.*;

public abstract class Bullet extends Actor
{
    protected float aniTime;
    protected MyAnimation animation;
    protected float damageRate;
    public boolean isAddTrace;
    public boolean isValid;
    public Vector2 ptEnd;
    public Vector2 ptLast;
    public Vector2 ptStart;
    protected float radius;
    protected TextureRegion region;
    protected Vector2 speed;
    protected float traceX;
    protected float traceY;
    protected float yIncrease;
    
    public Bullet() {
        this.damageRate = 1.0f;
        this.speed = new Vector2();
        this.animation = null;
        this.aniTime = 1000.0f;
        this.isValid = true;
        this.ptStart = new Vector2();
        this.ptEnd = new Vector2();
        this.ptLast = new Vector2();
        this.isAddTrace = true;
    }
    
    public void Clear() {
        super.clear();
        this.damageRate = 1.0f;
        this.radius = 0.0f;
        this.yIncrease = 0.0f;
        this.setVisible(true);
        this.aniTime = 1000.0f;
        this.animation = null;
        this.isValid = true;
        this.isAddTrace = false;
        this.setRotation(0.0f);
    }
    
    public Vector2 GetSpeed() {
        return this.speed;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.translate(this.speed.x, this.speed.y);
        final Vector2 speed = this.speed;
        speed.y -= this.yIncrease;
        if (this.isSetAngle()) {
            this.setAngle();
        }
        this.ptLast.set(this.ptEnd);
        this.ptEnd.set(this.getX(), this.getY());
        if (this.isValid) {
            this.checkAlive();
        }
        else {
            this.remove();
            Pools.free(this);
        }
        this.aniTime += n;
    }
    
    public void checkAlive() {
        if (this.getX() < 0.0f || this.getX() > 800.0f || this.getY() > 480.0f) {
            this.isValid = false;
        }
        if (!this.isGround() && this.getY() < 95.0f) {
            this.ptEnd.set(this.ptLast.x + (95.0f - this.ptLast.y) * ((this.ptEnd.x - this.ptLast.x) / (this.ptEnd.y - this.ptLast.y)), 95.0f);
            this.isValid = false;
        }
    }
    
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
//        while (true) {
//            if (this.region != null) {
//                spriteBatch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
//                return;
//            }
//            continue;
//        }
        if (this.region != null) {
            spriteBatch.draw(this.region, this.getX(), this.getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
        }
    }
    
    public MyAnimation getAnimation() {
        return this.animation;
    }
    
    public void initAnimation(final float n, final Array<? extends TextureRegion> array) {
        this.animation = new MyAnimation(n, array);
    }
    
    protected boolean isGround() {
        boolean b = false;
        if (this instanceof BulletEnemy) {
            if (((BulletEnemy)this).bulletType == Constant.BulletEnemyType.Freezecar) {
                b = true;
            }
        }
        return b;
    }
    
    protected abstract boolean isSetAngle();
    
    protected void setAngle() {
        this.setRotation(MathUtils.atan2(this.speed.y, this.speed.x) * 57.295776f);
    }
    
    public void setDamageRate(final float damageRate) {
        this.damageRate = damageRate;
    }
    
    @Override
    public void setPosition(final float n, final float n2) {
        super.setPosition(n, n2);
    }
    
    public void setRegion(final TextureRegion region) {
        this.region = region;
    }
    
    public void setStartTime() {
        this.aniTime = 0.0f;
    }
    
    public void setTrace(final float traceX, final float traceY) {
        this.traceX = traceX;
        this.traceY = traceY;
    }
    
    public void setYIncrease(final float yIncrease) {
        this.yIncrease = yIncrease;
    }
}
