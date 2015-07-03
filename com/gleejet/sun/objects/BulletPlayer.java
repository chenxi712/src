package com.gleejet.sun.objects;

import com.badlogic.gdx.math.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class BulletPlayer extends Bullet
{
    public Constant.BulletPlayerType bulletType;
    float damageAir;
    float damageBuild;
    float damageGround;
    public boolean isShock;
    
    public BulletPlayer() {
        this.isShock = false;
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
    }
    
    public float getDamageAir() {
        return this.damageAir;
    }
    
    public float getDamageBuild() {
        return this.damageBuild;
    }
    
    public float getDamageGround() {
        return this.damageGround;
    }
    
    public float getRadius() {
        return this.radius;
    }
    
    public boolean isAreaAttack() {
        return this.radius > 0.0f;
    }
    
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        return Collision.IsOverlap(baseEnemy, this);
    }
    
    @Override
    protected boolean isSetAngle() {
        return this.bulletType != Constant.BulletPlayerType.Leap;
    }
    
    public void setDamage(final float damageGround, final float damageAir, final float damageBuild) {
        this.damageGround = damageGround;
        this.damageAir = damageAir;
        this.damageBuild = damageBuild;
    }
    
    public void setRadius(final float radius) {
        this.radius = radius;
    }
    
    @Override
    public void setSize(final float n, final float n2) {
        super.setSize(n, n2);
        this.setTrace(0.0f, this.getHeight() / 2.0f);
    }
    
    public void showPolygon() {
    }
}
