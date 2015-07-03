package com.gleejet.sun.objects;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;

public class Bullet11 extends BulletPlayer
{
    float checkTime;
    float curTime;
    BaseEnemy enemy;
    boolean isOver;
    boolean isSet;
    public Polygon polygon;
    Vector2 posCross;
    float speedValue;
    
    public Bullet11() {
        this.posCross = new Vector2();
        this.curTime = 0.0f;
        this.isOver = false;
        this.checkTime = 0.05f;
        this.isSet = false;
        this.bulletType = Constant.BulletPlayerType.Track;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 40.0f, 0.0f, 40.0f, 18.0f, 0.0f, 18.0f });
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.setPosition(0.0f, 0.0f);
        this.polygon.setPosition(this.getX(), this.getY());
        this.isOver = false;
        this.curTime = 0.0f;
        this.enemy = null;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() - 3.0f, this.getY());
        }
        if (!this.isOver) {
            this.checkTime -= n;
            if (this.checkTime <= 0.0f) {
                this.setSpeed();
                this.checkTime = 0.05f;
            }
        }
        this.curTime += n;
        if (this.getY() <= 95.0f && this.getY() >= 85.0f && MathUtils.randomBoolean()) {
            Effect.addSmoke(this.getX(), this.getY(), 0.7f);
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
            Effect.addSmoke(this.posCross.x, this.posCross.y - 30.0f, 0.7f);
            vector2.set(this.posCross);
            return true;
        }
        return false;
    }
    
    public void setEnemy() {
        final Array<BaseEnemy> array = new Array<BaseEnemy>();
        for (int i = 0; i < Global.groupEnemy.getChildren().size; ++i) {
            final BaseEnemy baseEnemy = (BaseEnemy)Global.groupEnemy.getChildren().get(i);
            if (baseEnemy.getCurrentHp() > 0.0f) {
                array.add(baseEnemy);
            }
        }
        if (array.size < 0) {
            this.isOver = true;
            return;
        }
        this.enemy = array.random();
    }
    
    public void setSpeed() {
        if (this.enemy == null) {
            this.setEnemy();
            if (this.enemy == null) {
                return;
            }
        }
        Vector2 vector2 = new Vector2(this.enemy.getX(), this.enemy.getY() + this.enemy.getHeight() / 2.0f);
        vector2 = new Vector2(this.getX() + this.getWidth() / 2.0f, this.getY() + this.getHeight() / 2.0f);
        final Vector2 vector3 = new Vector2(vector2.x - vector2.x, vector2.y - vector2.y);
        final float n = MathUtils.atan2(vector3.y, vector3.x) * 57.295776f;
        final float rotation = this.getRotation();
        float n2;
        if (Math.abs(n - rotation) < 5.0f) {
            n2 = n;
            this.isOver = true;
        }
        else if (rotation > n) {
            n2 = rotation - (rotation - n) * 0.2f;
        }
        else {
            n2 = rotation;
            if (rotation < n) {
                n2 = rotation + (n - rotation) * 0.2f;
            }
        }
        this.speed.set(MathUtils.cosDeg(n2), MathUtils.sinDeg(n2)).scl(this.speedValue);
    }
    
    public void setSpeedValue(final float speedValue) {
        this.speedValue = speedValue;
    }
    
    @Override
    public void showPolygon() {
        if (this.polygon != null) {
            MyMethods.showPolygon(Global.rend, this.polygon);
        }
    }
    
    public float sqrt(final float n) {
        return (float)Math.sqrt(n);
    }
}
