package com.fxb.razor.objects.maingun;

import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.math.*;

public class DoublePipe extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private AnimationActor actorShot1;
    private AnimationActor actorShot2;
    private AnimationActor[] actorShots;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    private int frontIndex;
    private MyImage[] imgFronts;
    private MyImage imgGunFront2;
    private Runnable runAttack;
    
    static {
        DoublePipe.PlayerNum = 1;
        DoublePipe.strRoot = "maingun/xml/";
        DoublePipe.strPath = new String[] { "maingun_total_7" };
        DoublePipe.strEffect = "effect/pipe.pack";
        DoublePipe.strSound = "sound/weapon_pipe.ogg";
    }
    
    public DoublePipe() {
        this.frontIndex = 0;
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.myclear();
        this.setGunVisible(false);
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                final Bullet7 bulletDamage = Pools.obtain(Bullet7.class);
                bulletDamage.Clear();
                bulletDamage.setRegion(DoublePipe.this.regionBullet);
                bulletDamage.setScale(0.7f, 0.5f);
                bulletDamage.setSize(DoublePipe.this.bulletWidth, DoublePipe.this.bulletHeight);
                bulletDamage.setOrigin(DoublePipe.this.bulletWidth / 2.0f, DoublePipe.this.bulletHeight / 2.0f);
                DoublePipe.this.setBulletDamage(bulletDamage);
                float yIncrease;
                if (DoublePipe.this.frontIndex == 0) {
                    yIncrease = DoublePipe.this.yIncrease * 0.9f;
                }
                else {
                    yIncrease = DoublePipe.this.yIncrease * 1.1f;
                }
                float n;
                if (DoublePipe.this.frontIndex == 0) {
                    n = DoublePipe.this.curAngle + 4.0f;
                }
                else {
                    n = DoublePipe.this.curAngle - 4.0f;
                }
                bulletDamage.setYIncrease(yIncrease);
                DoublePipe.this.position.set(DoublePipe.this.imgFronts[DoublePipe.this.frontIndex].getRight(), DoublePipe.this.imgFronts[DoublePipe.this.frontIndex].getY() + DoublePipe.this.imgFronts[DoublePipe.this.frontIndex].getHeight() / 2.0f - DoublePipe.this.bulletHeight / 2.0f - 5.0f);
                DoublePipe.this.groupGun.localToStageCoordinates(DoublePipe.this.position);
                final float n2 = n + MathUtils.random(-2.0f, 2.0f);
                bulletDamage.GetSpeed().set(MathUtils.cosDeg(n2), MathUtils.sinDeg(n2)).nor().scl(DoublePipe.this.bulletSpeed);
                bulletDamage.setPosition(DoublePipe.this.position.x, DoublePipe.this.position.y + 5.0f);
                Global.groupBulletPlayer.addActor(bulletDamage);
                DoublePipe.this.actorShots[DoublePipe.this.frontIndex].setStart();
                DoublePipe.this.frontIndex = ++DoublePipe.this.frontIndex % DoublePipe.this.imgFronts.length;
                if (DoublePipe.this.frontIndex == 0) {
                    DoublePipe.this.flashPlayers[0].play();
                }
            }
        };
    }
    
    public static void loadElements() {
        for (int i = 0; i < DoublePipe.strPath.length; ++i) {
            Global.manager.load(DoublePipe.strRoot + DoublePipe.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(DoublePipe.strEffect, TextureAtlas.class);
        Global.manager.load(DoublePipe.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < DoublePipe.strPath.length; ++i) {
            Global.manager.unload(DoublePipe.strRoot + DoublePipe.strPath[i] + ".xml");
        }
        Global.manager.unload(DoublePipe.strEffect);
        Global.manager.unload(DoublePipe.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 8.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 8.0f / 5.0f;
        this.imgFronts[this.frontIndex].addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-7"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-7"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-7"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-7"));
        this.imgGunFront2 = new MyImage(Assets.atlasMainGun.findRegion("qiangtou2-7"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-7"));
        this.imgDrawf.setPosition(12.0f, 12.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(53.0f, 0.0f);
        this.imgGunFront.setPosition(60.0f, 5.0f);
        this.imgGunFront2.setPosition(60.0f, 25.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.imgGunFront.setVisible(false);
        this.imgGunFront2.setVisible(false);
        this.imgGunBody.setVisible(false);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunFront2);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(52.0f, 18.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-7");
        this.addActor(this.imgDrawf);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(55.0f, 130.0f);
        this.setIcon(7);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)Global.manager.get(DoublePipe.strEffect, TextureAtlas.class).getRegions(), 9, 2);
        (this.actorShot1 = new AnimationActor(0.05f, this.arrRegionShot)).setScale(0.7f);
        this.actorShot1.setPosition(this.imgGunFront.getRight() - this.actorShot1.getScaleX() * 55.0f + 5.0f, this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.actorShot1.getScaleY() * 98.0f);
        this.groupGun.addActor(this.actorShot1);
        (this.actorShot2 = new AnimationActor(0.05f, this.arrRegionShot)).setScale(0.7f);
        this.actorShot2.setPosition(this.imgGunFront2.getRight() - this.actorShot2.getScaleX() * 55.0f + 5.0f, this.imgGunFront2.getY() + this.imgGunFront2.getHeight() / 2.0f - this.actorShot2.getScaleY() * 98.0f);
        this.groupGun.addActor(this.actorShot2);
        this.imgFronts = new MyImage[] { this.imgGunFront, this.imgGunFront2 };
        this.actorShots = new AnimationActor[] { this.actorShot1, this.actorShot2 };
        MyMethods.initPoolNum(Bullet7.class, 6);
        MyMethods.initPoolNum(Effect.EffectSmoke.class, 5);
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
        this.flashPlayers[0].setPosition(this.getX() - 3.0f, this.getY());
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.groupGun.addAction(Actions.sequence(Actions.delay(0.05f), Actions.run(this.runAttack), Actions.delay(0.06f), Actions.run(this.runAttack)));
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.duralHandle();
        SoundHandle.playForPipe();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet7.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[DoublePipe.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(DoublePipe.strRoot + DoublePipe.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.flashPlayers[0].SetTimeScale(1.23f);
        this.setGunOrigin(80.0f, 32.0f);
        this.endFrame = 5;
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
        actor.setPosition(actor.getX() - 8.0f, actor.getY() - 3.0f);
    }
}
