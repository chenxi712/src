package com.gleejet.sun.objects;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Bullet2 extends BulletPlayer implements Pool.Poolable
{
    boolean isAddBreak;
    public Polygon polygon;
    Vector2 posCross;
    
    public Bullet2() {
        this.posCross = new Vector2();
        this.isAddBreak = false;
        this.bulletType = Constant.BulletPlayerType.Cannon;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 35.0f, 0.0f, 35.0f, 16.0f, 0.0f, 16.0f });
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.isAddBreak = false;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.polygon.setPosition(this.getX(), this.getY());
        if (!this.isAddBreak && this.getY() < 100.0f) {
            Effect.addExplosion(this.getX() + 10.0f, this.getY() - 5.0f, 0.8f);
            this.isAddBreak = true;
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
            Effect.addExplosion(this.posCross.x, this.posCross.y - 5.0f, 0.8f);
            this.isAddBreak = true;
            vector2.set(this.posCross);
            Global.pCross = this.posCross;
            return true;
        }
        return false;
    }
    
    public boolean isOverlap0(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon != null) {
            final float[] vertices = this.polygon.getVertices();
            final float x = this.polygon.getX();
            final float y = this.polygon.getY();
            for (int i = 0; i < vertices.length / 2; ++i) {
                if (baseEnemy.polygon.contains(vertices[i * 2] + x, vertices[i * 2 + 1] + y)) {
                    vector2.set(vertices[i * 2] + x, vertices[i * 2 + 1] + y);
                    return true;
                }
            }
            return false;
        }
        return Collision.IsOverlap(baseEnemy, this);
    }
    
    public void reset() {
    }
    
    @Override
    public void showPolygon() {
        if (this.polygon != null) {
            MyMethods.showPolygon(Global.rend, this.polygon);
        }
    }
}
