package com.fxb.razor.objects;

import com.badlogic.gdx.math.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Bullet3 extends BulletPlayer
{
    private boolean isAddBreak;
    Vector2 posBulletEnd;
    public Vector2 posCross;
    Vector2 posGroundLeft;
    Vector2 posGroundRight;
    
    public Bullet3() {
        this.posCross = new Vector2();
        this.isAddBreak = false;
        this.posGroundLeft = new Vector2(100.0f, 100.0f);
        this.posGroundRight = new Vector2(800.0f, 100.0f);
        this.posBulletEnd = new Vector2();
        this.bulletType = Constant.BulletPlayerType.SinglePipe;
        this.isAddTrace = false;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.posBulletEnd.set(this.ptStart.x + MathUtils.cosDeg(-10.0f) * 600.0f, this.ptStart.y + MathUtils.sinDeg(-10.0f) * 600.0f);
        if (!this.isAddBreak && this.getY() < 100.0f) {
            Effect.addPipeClick(this.ptEnd.x, this.ptEnd.y, 0.0f);
            this.isAddBreak = true;
        }
        if (Intersector.intersectSegments(this.ptStart, this.posBulletEnd, this.posGroundLeft, this.posGroundRight, this.posCross)) {
            Effect.addPipeClick(this.posCross.x, this.posCross.y, 0.0f);
            Global.pCross = this.posCross;
            this.isAddBreak = true;
        }
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        final Vector2 ptStart = this.ptStart;
        final Vector2 ptEnd = this.ptEnd;
        final Vector2 ptLast = this.ptLast;
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isSegPolyOverlap(ptStart, ptEnd, baseEnemy.polygon, this.posCross)) {
            Effect.addPipeClick(this.posCross.x, this.posCross.y, 90.0f);
            vector2.set(this.posCross);
            Global.pCross = this.posCross;
            return this.isAddBreak = true;
        }
        return false;
    }
}
