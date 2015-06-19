package com.fxb.razor.objects;

import com.badlogic.gdx.math.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Bullet7 extends BulletPlayer
{
    Polygon polygon;
    Vector2 posCross;
    
    public Bullet7() {
        this.posCross = new Vector2();
        this.bulletType = Constant.BulletPlayerType.DoublePipe;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 30.0f, 0.0f, 30.0f, 10.0f, 0.0f, 10.0f });
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.setPosition(0.0f, 0.0f);
        this.polygon.setPosition(0.0f, 0.0f);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() - 10.0f, this.getY());
        }
        if (this.getY() <= 95.0f && this.getY() >= 85.0f && MathUtils.randomBoolean()) {
            Effect.addSmoke(this.getX(), this.getY());
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        boolean b = false;
        if (baseEnemy.polygon != null) {
            if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
                if (MathUtils.random(0, 6) >= 3) {
                    Effect.addSmoke(this.posCross.x, this.posCross.y - 10.0f);
                }
                vector2.set(this.posCross);
                b = true;
            }
            return b;
        }
        return super.isOverlap(baseEnemy, vector2);
    }
    
    @Override
    public void showPolygon() {
        if (this.polygon != null) {
            MyMethods.showPolygon(Global.rend, this.polygon);
        }
    }
}
