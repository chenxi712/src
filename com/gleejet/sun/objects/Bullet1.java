package com.gleejet.sun.objects;

import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;

public class Bullet1 extends BulletPlayer
{
    private boolean isAddBreak;
    private boolean isAddShot;
    public Vector2 posCross;
    Vector2 posGroundLeft;
    Vector2 posGroundRight;
    
    public Bullet1() {
        this.posCross = new Vector2();
        this.isAddBreak = false;
        this.isAddShot = false;
        this.posGroundLeft = new Vector2(100.0f, 100.0f);
        this.posGroundRight = new Vector2(800.0f, 100.0f);
        this.bulletType = Constant.BulletPlayerType.SinglePipe;
        this.isAddTrace = true;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (!this.isAddBreak && this.getY() < 100.0f && Intersector.intersectSegments(this.ptStart, this.ptEnd, this.posGroundLeft, this.posGroundRight, this.posCross)) {
            Global.pCross = this.posCross;
            this.isAddBreak = true;
        }
        if (this.isAddBreak) {
            this.ptEnd.set(this.posCross);
        }
        if (!Global.groupBulletPlayer.getChildren().contains(this, true)) {
            return;
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
            vector2.set(this.posCross);
            Global.pCross = this.posCross;
            return this.isAddBreak = true;
        }
        return false;
    }
}
