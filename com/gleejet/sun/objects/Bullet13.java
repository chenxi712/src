package com.gleejet.sun.objects;

import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.objects.maingun.*;
import com.gleejet.sun.roles.*;

public class Bullet13 extends BulletPlayer
{
    public int collisionCount;
    public Polygon polygon;
    Vector2 posCross;
    BaseEnemy targetEnemy;
    
    public Bullet13() {
        this.posCross = new Vector2();
        this.bulletType = Constant.BulletPlayerType.Leap;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 60.0f, 0.0f, 60.0f, 50.0f, 0.0f, 60.0f });
        this.targetEnemy = null;
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.setPosition(-50.0f, -50.0f);
        this.polygon.setPosition(this.getX(), this.getY());
        this.collisionCount = 0;
        this.targetEnemy = null;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() - 3.0f, this.getY());
        }
        if (this.getY() < 110.0f) {
            this.speed.y = -this.speed.y * MathUtils.random(0.8f, 0.95f);
            if (this.speed.y < 6.0f) {
                this.speed.y = 6.0f;
            }
            if (this.speed.x < 3.0f) {
                this.speed.x = 3.0f;
            }
        }
    }
    
    public boolean collisonCheck() {
        ++this.collisionCount;
        return this.collisionCount >= Leap.MaxCollisionCount;
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy targetEnemy, final Vector2 vector2) {
        if (this.targetEnemy != targetEnemy) {
            if (targetEnemy.polygon == null) {
                return super.isOverlap(targetEnemy, vector2);
            }
            if (MyMethods.isPolyPolyOverlap(this.polygon, targetEnemy.polygon, this.posCross)) {
                Effect.addExplosion(this.posCross.x, this.posCross.y - 30.0f, 0.7f);
                vector2.set(this.posCross);
                this.targetEnemy = targetEnemy;
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void showPolygon() {
        if (this.polygon != null) {
            MyMethods.showPolygon(Global.rend, this.polygon);
        }
    }
}
