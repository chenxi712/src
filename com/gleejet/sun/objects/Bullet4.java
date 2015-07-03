package com.gleejet.sun.objects;

import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;

public class Bullet4 extends BulletPlayer
{
    int count;
    public Polygon polygon;
    Vector2 posCross;
    
    public Bullet4() {
        this.posCross = new Vector2();
        this.count = 0;
        this.bulletType = Constant.BulletPlayerType.Flame;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 30.0f, 20.0f, 82.0f, 22.0f, 82.0f, 52.0f, 38.0f, 52.0f });
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.setPosition(-50.0f, -50.0f);
        this.polygon.setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.polygon.setPosition(this.getX() - 15.0f, this.getY() - 10.0f);
        if (this.getY() <= 95.0f && this.getY() >= 85.0f && MathUtils.random(0, 4) == 1) {
            Effect.addFlameEffect(this.getX() + 50.0f, this.getY());
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
            if (MathUtils.random(0, 6) >= 1) {
                Effect.addFlameEffect(this.posCross.x, this.posCross.y);
            }
            vector2.set(this.posCross);
            return true;
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
