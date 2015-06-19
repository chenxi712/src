package com.fxb.razor.objects;

import com.badlogic.gdx.math.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Bullet8 extends BulletPlayer
{
    public Polygon polygon;
    private Vector2 posCross;
    
    public Bullet8() {
        this.posCross = new Vector2();
        this.bulletType = Constant.BulletPlayerType.Acid;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 13.0f, 22.0f, 70.0f, 21.0f, 84.0f, 69.0f, 33.0f, 72.0f });
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
        this.polygon.setPosition(this.getX() - 50.0f, this.getY() - 20.0f);
        if (this.getY() <= 95.0f && this.getY() >= 85.0f && MathUtils.random(0, 3) == 2) {
            Effect.addAcidEffect(this.getX(), this.getY());
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
            if (MathUtils.random(0, 6) >= 1) {
                Effect.addAcidEffect(this.getX(), this.getY());
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
