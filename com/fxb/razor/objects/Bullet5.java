package com.fxb.razor.objects;

import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class Bullet5 extends BulletPlayer
{
    private float angle;
    public Vector2 pEnd;
    public Vector2 pStart;
    
    public Bullet5() {
        this.angle = 0.0f;
        this.bulletType = Constant.BulletPlayerType.Laser;
        this.isAddTrace = false;
        this.pStart = new Vector2();
        this.pEnd = new Vector2();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon != null) {
            return MyMethods.isSegPolyOverlap(this.pStart, this.pEnd, baseEnemy.polygon, vector2);
        }
        return Collision.IsOverlap(baseEnemy, this);
    }
    
    public void setAngle(final float angle) {
        this.angle = angle;
        this.pEnd.x = this.pStart.x + MathUtils.cosDeg(angle) * 600.0f;
        this.pEnd.y = this.pStart.x + MathUtils.sinDeg(angle) * 600.0f;
    }
}
