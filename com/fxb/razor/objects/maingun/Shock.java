package com.fxb.razor.objects.maingun;

import com.fxb.razor.objects.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.math.*;

public class Shock extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    private Runnable runAttack;
    private Runnable runShot;
    
    static {
        Shock.PlayerNum = 1;
        Shock.strRoot = "maingun/xml/";
        Shock.strPath = new String[] { "maingun_total_c" };
        Shock.strEffect = "effect/shock.pack";
        Shock.strSound = "sound/weapon_shock.ogg";
    }
    
    public Shock() {
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.myclear();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runShot = new Runnable() {
            @Override
            public void run() {
                Shock.this.actorShot.setStart();
            }
        };
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                final Bullet12 bulletDamage = Pools.obtain(Bullet12.class);
                bulletDamage.Clear();
                bulletDamage.setRegion(Shock.this.regionBullet);
                bulletDamage.setSize(Shock.this.bulletWidth, Shock.this.bulletHeight);
                bulletDamage.setOrigin(54.0f, 32.0f);
                Shock.this.setBulletDamage(bulletDamage);
                bulletDamage.setRadius(Shock.this.attackRadius);
                Shock.this.position.set(Shock.this.imgGunFront.getRight() - 10.0f + bulletDamage.getOriginX(), Shock.this.imgGunFront.getY() + Shock.this.imgGunFront.getHeight() / 2.0f + 10.0f);
                Shock.this.groupGun.localToStageCoordinates(Shock.this.position);
                final float n = Shock.this.curAngle + MathUtils.random(-3.0f, 3.0f);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(n), MathUtils.sinDeg(n)).nor().scl(Shock.this.bulletSpeed);
                bulletDamage.setPosition(Shock.this.position.x - bulletDamage.getOriginX(), Shock.this.position.y - bulletDamage.getOriginY());
                Global.groupBulletPlayer.addActor(bulletDamage);
                Shock.this.flashPlayers[0].play();
                SoundHandle.playForShock();
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < Shock.strPath.length; ++i) {
            Global.manager.load(Shock.strRoot + Shock.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Shock.strEffect, TextureAtlas.class);
        Global.manager.load(Shock.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Shock.strPath.length; ++i) {
            Global.manager.unload(Shock.strRoot + Shock.strPath[i] + ".xml");
        }
        Global.manager.unload(Shock.strEffect);
        Global.manager.unload(Shock.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 5.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 5.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-12"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-12"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-12"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-12"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-12"));
        this.imgDrawf.setPosition(6.0f, 12.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(50.0f, 1.0f);
        this.imgGunFront.setPosition(29.0f, -20.5f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(27.0f, 18.0f);
        this.groupGun.setPosition(45.0f, 20.0f);
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(70.0f, 128.0f);
        this.setIcon(12);
        final TextureAtlas textureAtlas = Global.manager.get(Shock.strEffect, TextureAtlas.class);
        final Array<TextureRegion> array = new Array<TextureRegion>();
        array.addAll(textureAtlas.getRegions(), 0, 34);
        this.regionBullet = textureAtlas.findRegion("paodan");
        (this.actorShot = new AnimationActor(0.025f, array)).setScale(0.8f);
        this.actorShot.setPosition(this.imgGunFront.getRight() - 10.0f - this.actorShot.getScaleX() * 108.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - 8.0f - this.actorShot.getScaleY() * 135.0f);
        this.groupGun.addActor(this.actorShot);
        MyMethods.initPoolNum(Bullet12.class, 2);
        MyMethods.initPoolNum(Effect.EffectExplosion.class, 2);
        this.initRun();
    }
    
    public void SetProperty() {
        this.bulletWidth = this.regionBullet.getRegionWidth();
        this.bulletHeight = this.regionBullet.getRegionHeight();
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 2.0f, this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.groupGun.addAction(Actions.sequence(Actions.delay(0.05f), Actions.run(this.runShot), Actions.delay(0.5f), Actions.run(this.runAttack)));
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.duralHandle();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet12.class);
        MyMethods.clearPool(Effect.EffectExplosion.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Shock.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Shock.strRoot + Shock.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(69.0f, 40.0f);
        this.endFrame = 6;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void myclear() {
        this.clearActions();
        this.lastAttackTime = 0.0f;
        this.curTime = 0.0f;
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX(), actor.getY() - 4.0f);
    }
}
