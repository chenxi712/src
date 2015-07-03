package com.gleejet.sun.objects;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.utils.ui.*;

public class Bullet10 extends BulletPlayer
{
    public static Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    public AnimationActor actorSpark;
    boolean isAddBreak;
    public Polygon polygon;
    Vector2 pos;
    Vector2 posCross;
    
    public Bullet10() {
        this.posCross = new Vector2();
        this.isAddBreak = false;
        this.pos = new Vector2();
        final Constant.BulletPlayerType bulletType = this.bulletType;
        this.bulletType = Constant.BulletPlayerType.Missile;
        this.isAddTrace = false;
        this.polygon = new Polygon(new float[] { 0.0f, 0.0f, 50.0f, 0.0f, 50.0f, 25.0f, 0.0f, 25.0f });
        (this.actorSpark = new AnimationActor(0.05f, Bullet10.arrRegionBullet)).setOrigin(84.0f + this.getOriginX(), 26.0f);
        this.actorSpark.setLoop(true);
        this.Clear();
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.isAddBreak = false;
        this.setPosition(-500.0f, -500.0f);
        this.polygon.setPosition(this.getX(), this.getY());
        this.actorSpark.setPosition(this.getX(), this.getY());
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.polygon.setPosition(this.getX(), this.getY());
        if (!this.isAddBreak && this.getY() < 100.0f) {
            Effect.addExplosion(this.getX() + 25.0f, this.getY() - 12.0f, 0.8f);
            this.isAddBreak = true;
        }
        this.actorSpark.setOrigin(84.0f + this.getOriginX(), 26.0f);
        this.pos.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY());
        this.actorSpark.setPosition(this.pos.x - this.actorSpark.getOriginX(), this.pos.y - this.actorSpark.getOriginY());
        this.actorSpark.setRotation(this.getRotation());
        this.actorSpark.act(n);
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
        this.actorSpark.draw(spriteBatch, n);
    }
    
    @Override
    public boolean isOverlap(final BaseEnemy baseEnemy, final Vector2 vector2) {
        if (baseEnemy.polygon == null) {
            return super.isOverlap(baseEnemy, vector2);
        }
        if (MyMethods.isPolyPolyOverlap(this.polygon, baseEnemy.polygon, this.posCross)) {
            Effect.addExplosion(this.posCross.x + 12.0f, this.posCross.y - 20.0f, 0.8f);
            this.isAddBreak = true;
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
