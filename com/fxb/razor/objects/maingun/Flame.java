package com.fxb.razor.objects.maingun;

import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.common.*;

public class Flame extends BaseMainGun
{
    private static Array<TextureAtlas.AtlasRegion> arrRegionBreak;
    private static String strEffect;
    private static String strSound;
    AnimationActor actorShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionBullet;
    float duralTime;
    float gapTime;
    float lastAutoTime;
    Vector2 posOrigin;
    ShapeRenderer rend;
    private Runnable runAttack;
    
    static {
        Flame.strEffect = "effect/flame.pack";
        Flame.strSound = "sound/weapon_flame.ogg";
    }
    
    public Flame() {
        this.posOrigin = new Vector2();
        this.rend = new ShapeRenderer();
        this.gapTime = 0.1f;
        this.duralTime = 1.5f;
        this.lastAutoTime = -5.0f;
        this.AddItems();
        this.SetProperty();
    }
    
    public static Array<TextureAtlas.AtlasRegion> getArrRegionBreak() {
        return Flame.arrRegionBreak;
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 4; ++i) {
                    final Bullet4 bulletDamage = Pools.obtain(Bullet4.class);
                    bulletDamage.Clear();
                    Flame.this.setBulletDamage(bulletDamage);
                    final TextureRegion region = Flame.this.getRegion();
                    bulletDamage.setRegion(region);
                    final float random = MathUtils.random(0.22f, 0.68f);
                    bulletDamage.setSize(region.getRegionWidth() * random, region.getRegionHeight() * random);
                    bulletDamage.setOrigin(bulletDamage.getWidth() / 2.0f, bulletDamage.getHeight() / 2.0f);
                    final float random2 = MathUtils.random(0.85f, 1.15f);
                    bulletDamage.setRadius(Flame.this.attackRadius);
                    bulletDamage.setYIncrease(Flame.this.yIncrease * random2);
                    final float n = Flame.this.curAngle + MathUtils.random(-5.0f, 5.0f);
                    bulletDamage.GetSpeed().set(MathUtils.cosDeg(n), MathUtils.sinDeg(n)).nor().scl(Flame.this.bulletSpeed * random2);
                    Flame.this.position.set(Flame.this.imgGunFront.getRight() + 5.0f, Flame.this.imgGunFront.getY() + Flame.this.imgGunFront.getHeight() / 2.0f);
                    Flame.this.groupGun.localToStageCoordinates(Flame.this.position);
                    bulletDamage.setPosition(Flame.this.position.x - bulletDamage.getOriginX(), Flame.this.position.y - bulletDamage.getOriginY());
                    Global.groupBulletPlayer.addActor(bulletDamage);
                }
            }
        };
    }
    
    public static void loadElements() {
        Global.manager.load(Flame.strEffect, TextureAtlas.class);
        Global.manager.load(Flame.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        Global.manager.unload(Flame.strEffect);
        Global.manager.unload(Flame.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-4"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-4"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-4"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-4"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-4"));
        this.imgDrawf.setPosition(11.0f, 17.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(66.0f, 6.0f);
        this.imgGunFront.setPosition(28.0f, -8.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(55.0f, 24.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.addActor(this.imgDrawf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(65.0f, 130.0f);
        this.setIcon(4);
        final TextureAtlas textureAtlas = Global.manager.get(Flame.strEffect, TextureAtlas.class);
        Flame.arrRegionBreak = textureAtlas.findRegions("flame_break");
        this.arrRegionBullet = textureAtlas.findRegions("flame_bullet");
        (this.actorShot = new AnimationActor(0.033333335f, textureAtlas.findRegions("flame_shot"))).setScale(1.0f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - this.actorShot.getScaleX() * 83.5f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot.getScaleY() * 158.0f);
        this.actorShot.setPlayCount(2);
        this.actorShot.setLoop(true);
        this.groupGun.addActor(this.actorShot);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        MyMethods.initPoolNum(Bullet4.class, 40);
        MyMethods.initPoolNum(Effect.EffectFlame.class, 10);
        this.initRun();
    }
    
    public void SetProperty() {
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.curTime - this.lastAttackTime < this.duralTime && this.curTime - this.lastAutoTime >= this.gapTime && this.workState == WorkState.State_Work) {
            this.addBullet();
        }
    }
    
    public void addBullet() {
        MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
        this.lastAutoTime = this.curTime;
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.lastAttackTime = this.curTime;
        this.actorShot.setStart();
        this.duralHandle();
        SoundHandle.playForFlame();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet4.class);
        MyMethods.clearPool(Effect.EffectFlame.class);
    }
    
    public TextureRegion getRegion() {
        return this.arrRegionBullet.get(MathUtils.random(this.arrRegionBullet.size - 1));
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX(), actor.getY() - 7.0f);
    }
}
