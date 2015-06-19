package com.fxb.razor.objects;

import com.badlogic.gdx.math.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.common.*;

public class Bullet12 extends BulletPlayer
{
    float curTime;
    public Polygon polygon;
    Vector2 posCross;
    
    public Bullet12() {
        this.posCross = new Vector2();
        this.curTime = 0.0f;
        this.bulletType = Constant.BulletPlayerType.Shock;
        this.isAddTrace = false;
        this.isShock = true;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 100.0f, 0.0f, 100.0f, 50.0f, 0.0f, 50.0f });
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.setPosition(0.0f, 0.0f);
        this.polygon.setPosition(this.getX(), this.getY());
        for (int i = 0; i < Global.groupEnemy.getChildren().size; ++i) {
            ((BaseEnemy)Global.groupEnemy.getChildren().get(i)).attackBullet = null;
        }
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.polygon != null) {
            this.polygon.setPosition(this.getX() + 5.0f, this.getY() + 5.0f);
        }
        this.checkCollision();
    }
    
    public void checkCollision() {
        for (int i = 0; i < Global.groupEnemy.getChildren().size; ++i) {
            final BaseEnemy baseEnemy = (BaseEnemy)Global.groupEnemy.getChildren().get(i);
            if (baseEnemy.getCurrentHp() > 0.0f && baseEnemy.attackBullet != this && this.isOverlap(baseEnemy, this.posCross)) {
                Effect.addExplosion(this.posCross.x, this.posCross.y - 30.0f, 0.7f);
                baseEnemy.BeAttacked(baseEnemy.attackBullet = this);
                this.decreaseDamage(0.5f);
            }
        }
    }
    
    public void decreaseDamage(final float n) {
        this.damageGround *= n;
        this.damageAir *= n;
        this.damageBuild *= n;
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
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
