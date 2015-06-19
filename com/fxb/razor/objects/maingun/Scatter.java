package com.fxb.razor.objects.maingun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.objects.*;
import com.fxb.razor.common.*;
import com.fxb.razor.flash.*;
import com.badlogic.gdx.math.*;

public class Scatter extends BaseMainGun
{
    private static int PlayerNum;
    private static Array<TextureAtlas.AtlasRegion> arrRegionClick;
    private static String strEffect1;
    private static String strEffect2;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    Array<Float> arrAngle;
    
    static {
        Scatter.PlayerNum = 1;
        Scatter.strRoot = "maingun/xml/";
        Scatter.strPath = new String[] { "maingun_total_3" };
        Scatter.strEffect1 = "effect/scatter.pack";
        Scatter.strEffect2 = "effect/pipe.pack";
        Scatter.strSound = "sound/weapon_scatter.ogg";
    }
    
    public Scatter() {
        this.arrAngle = new Array<Float>();
        this.AddItems();
        this.SetProperty();
        this.initFlash();
        this.setGunVisible(false);
    }
    
    public static Array<TextureAtlas.AtlasRegion> getArrRegionClick() {
        return Scatter.arrRegionClick;
    }
    
    public static void loadElements() {
        for (int i = 0; i < Scatter.strPath.length; ++i) {
            Global.manager.load(Scatter.strRoot + Scatter.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Scatter.strEffect1, TextureAtlas.class);
        Global.manager.load(Scatter.strEffect2, TextureAtlas.class);
        Global.manager.load(Scatter.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Scatter.strPath.length; ++i) {
            Global.manager.unload(Scatter.strRoot + Scatter.strPath[i] + ".xml");
        }
        Global.manager.unload(Scatter.strEffect1);
        Global.manager.unload(Scatter.strEffect2);
        Global.manager.unload(Scatter.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 8.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 8.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-3"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-3"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-3"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-3"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("zhijia-3"));
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-3");
        this.imgDrawf.setPosition(18.0f, 14.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(49.0f, 3.0f);
        this.imgGunFront.setPosition(55.0f, 13.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(21.0f, 18.0f);
        this.groupGun.setPosition(52.0f, 10.0f);
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.addActor(this.imgDrawf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(63.0f, 130.0f);
        this.setIcon(3);
        final TextureAtlas textureAtlas = Global.manager.get(Scatter.strEffect1, TextureAtlas.class);
        (Scatter.arrRegionClick = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)Global.manager.get(Scatter.strEffect2, TextureAtlas.class).getRegions(), 0, 7);
        Effect.setArrRegion(Scatter.arrRegionClick);
        (this.actorShot = new AnimationActor(0.033333335f, textureAtlas.getRegions())).setPosition(-28.0f, -75.0f);
        this.groupGun.addActor(this.actorShot);
        MyMethods.initPoolNum(Bullet3.class, 6);
        MyMethods.initPoolNum(Effect.EffectPipeClick.class, 6);
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
        this.flashPlayers[this.curIndex].setPosition(this.getX() + 9.0f, this.getY() + 3.0f);
    }
    
    @Override
    public void addBullet(float n, float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        this.arrAngle.clear();
        n = this.curAngle + MathUtils.random(-2, 2);
        this.arrAngle.add(n);
        n2 = n;
        for (int n3 = 0; n3 < 5.0f; ++n3) {
            n2 += MathUtils.random(0, 10);
            this.arrAngle.add(n2);
        }
        for (int n4 = 0; n4 < 5.0f; ++n4) {
            n -= MathUtils.random(0, 10);
            this.arrAngle.add(n);
        }
        this.groupGun.addAction(Actions.sequence(Actions.delay(0.05f), Actions.run(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < Scatter.this.arrAngle.size; ++i) {
                    final Bullet3 bulletDamage = new Bullet3();
                    bulletDamage.Clear();
                    Scatter.this.setBulletDamage(bulletDamage);
                    bulletDamage.setRegion(Scatter.this.regionBullet);
                    bulletDamage.setRadius(Scatter.this.attackRadius);
                    bulletDamage.setYIncrease(Scatter.this.yIncrease);
                    bulletDamage.setSize(1.0f, 1.0f);
                    Scatter.this.position.set(Scatter.this.imgGunFront.getRight(), Scatter.this.imgGunFront.getY() + Scatter.this.imgGunFront.getHeight() / 2.0f - Scatter.this.bulletHeight / 2.0f);
                    Scatter.this.groupGun.localToStageCoordinates(Scatter.this.position);
                    final float floatValue = Scatter.this.arrAngle.get(i);
                    Scatter.this.bulletSpeed = MathUtils.random(80, 100);
                    bulletDamage.GetSpeed().set(MathUtils.cosDeg(floatValue), MathUtils.sinDeg(floatValue)).nor().scl(Scatter.this.bulletSpeed);
                    final float random = MathUtils.random(0.2f, 0.6f);
                    bulletDamage.setPosition(Scatter.this.position.x - bulletDamage.GetSpeed().x * random, Scatter.this.position.y + MathUtils.random(-6.0f, 6.0f) - bulletDamage.GetSpeed().y * random);
                    Global.groupBulletPlayer.addActor(bulletDamage);
                    bulletDamage.setVisible(false);
                    bulletDamage.ptStart.set(bulletDamage.getX(), bulletDamage.getY());
                    Global.ptStart.set(bulletDamage.ptStart);
                }
                Scatter.this.actorShot.setStart();
                Scatter.this.flashPlayers[0].play();
                SoundHandle.playForScatter();
            }
        })));
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.duralHandle();
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Bullet3.class);
        MyMethods.clearPool(Effect.EffectPipeClick.class);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[Scatter.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Scatter.strRoot + Scatter.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(62.0f, 23.0f);
        this.endFrame = 4;
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
    }
    
    @Override
    public void setAngle(final float n, final float n2) {
        super.setAngle(n, n2);
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX() - 5.0f, actor.getY() - 8.0f);
    }
}
