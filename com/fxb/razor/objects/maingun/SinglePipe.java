package com.fxb.razor.objects.maingun;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.roles.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.flash.*;

public class SinglePipe extends BaseMainGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    AnimationActor actorShot;
    private Array<TextureAtlas.AtlasRegion> arrRegionClick;
    private Array<TextureAtlas.AtlasRegion> arrRegionShot;
    MyImage imgShot;
    boolean isBulletValid;
    float lastSoundTime;
    Vector2 pCross;
    Vector2 posBulletEnd;
    Vector2 posBulletStart;
    Vector2 posGroundLeft;
    Vector2 posGroundRight;
    float randAngle;
    private Runnable runAttack;
    
    static {
        SinglePipe.PlayerNum = 1;
        SinglePipe.strRoot = "maingun/xml/";
        SinglePipe.strPath = new String[] { "maingun_total_1" };
        SinglePipe.strEffect = "effect/pipe.pack";
        SinglePipe.strSound = "sound/weapon_pipe.ogg";
    }
    
    public SinglePipe() {
        this.posBulletStart = new Vector2();
        this.posBulletEnd = new Vector2();
        this.pCross = new Vector2();
        this.isBulletValid = false;
        this.randAngle = 0.0f;
        this.lastSoundTime = 0.0f;
        this.posGroundLeft = new Vector2(100.0f, 100.0f);
        this.posGroundRight = new Vector2(800.0f, 100.0f);
        this.AddItems();
        this.SetProperty();
        this.myclear();
    }
    
    private void initRun() {
        this.runAttack = new Runnable() {
            @Override
            public void run() {
                SinglePipe.this.randAngle = SinglePipe.this.curAngle + MathUtils.random(-3.5f, 3.5f);
                SinglePipe.this.actorShot.setStart();
                SinglePipe.this.imgShot.setRotation(SinglePipe.this.randAngle - SinglePipe.this.curAngle);
                SinglePipe.this.isBulletValid = true;
                SinglePipe.this.imgShot.setWidth(600.0f);
                SoundHandle.playForPipe();
            }
        };
    }
    
    private boolean isOverlap(final BaseEnemy baseEnemy) {
        return baseEnemy.polygon != null && MyMethods.isSegPolyOverlap(this.posBulletStart, this.posBulletEnd, baseEnemy.polygon, this.pCross);
    }
    
    public static void loadElements() {
        for (int i = 0; i < SinglePipe.strPath.length; ++i) {
            Global.manager.load(SinglePipe.strRoot + SinglePipe.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(SinglePipe.strEffect, TextureAtlas.class);
        Global.manager.load(SinglePipe.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < SinglePipe.strPath.length; ++i) {
            Global.manager.unload(SinglePipe.strRoot + SinglePipe.strPath[i] + ".xml");
        }
        Global.manager.unload(SinglePipe.strEffect);
        Global.manager.unload(SinglePipe.strSound);
    }
    
    @Override
    public void AddGunRecoil() {
        final float n = 6.0f * (float)Math.cos(this.curAngle * 3.141592653589793 / 180.0);
        final float n2 = (float)Math.sin(this.curAngle * 3.141592653589793 / 180.0) * 6.0f / 5.0f;
        this.imgGunFront.addAction(Actions.sequence(Actions.moveBy(-n, -n2, 0.045f), Actions.moveBy(n, n2, 0.045f)));
    }
    
    public void AddItems() {
        this.imgDrawf = new MyImage(Assets.atlasMainGun.findRegion("airen-1"));
        this.imgPlatform = new MyImage(Assets.atlasMainGun.findRegion("taizi-1"));
        this.imgGunBody = new MyImage(Assets.atlasMainGun.findRegion("qiangshen-1"));
        this.imgGunFront = new MyImage(Assets.atlasMainGun.findRegion("qiangtou-1"));
        this.imgGunShelf = new MyImage(Assets.atlasMainGun.findRegion("qiangjia-1"));
        this.imgDrawf.setPosition(5.0f, 17.0f);
        this.imgPlatform.setPosition(0.0f, 0.0f);
        this.imgGunShelf.setPosition(49.0f, 3.0f);
        this.imgGunFront.setPosition(60.0f, 12.0f);
        this.imgGunBody.setPosition(0.0f, 0.0f);
        this.groupGun.addActor(this.imgGunFront);
        this.groupGun.addActor(this.imgGunBody);
        this.groupGun.setSize(this.imgGunFront.getRight(), this.imgGunBody.getHeight());
        this.groupGun.setOrigin(24.0f, 12.0f);
        this.groupGun.setPosition(36.0f, 18.0f);
        this.regionBullet = Assets.atlasMainGun.findRegion("paodan-1");
        this.addActor(this.imgPlatform);
        this.addActor(this.groupGun);
        this.addActor(this.imgGunShelf);
        this.addActor(this.imgDrawf);
        this.setSize(120.0f, 70.0f);
        this.setPosition(80.0f, 141.0f);
        this.setIcon(1);
        final TextureAtlas textureAtlas = Global.manager.get(SinglePipe.strEffect, TextureAtlas.class);
        (this.arrRegionShot = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 7, 2);
        (this.arrRegionClick = new Array<TextureAtlas.AtlasRegion>()).addAll((Array<? extends TextureAtlas.AtlasRegion>)textureAtlas.getRegions(), 0, 7);
        Effect.setArrRegion(this.arrRegionClick);
        this.actorShot = AnimationActor.createAnimation(this.groupGun, 0.045454547f, this.arrRegionShot, 60.0f, -50.0f, 0.7f);
        (this.imgShot = new MyImage(Assets.atlasPipeTrace.findRegion("2"))).setSize(0.0f, 40.0f);
        this.imgShot.setPosition(this.imgGunFront.getRight(), this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f - this.imgShot.getHeight() / 2.0f);
        this.imgShot.setRotation(this.curAngle);
        this.imgShot.setColor(1.0f, 1.0f, 1.0f, 0.75f);
        this.groupGun.addActor(this.imgShot);
        MyMethods.initPoolNum(Effect.EffectPipeClick.class, 6);
        this.initRun();
    }
    
    public void SetProperty() {
        this.setJsonValue();
        this.setEnhanceLevel();
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkCollision();
        if (this.curTime - this.lastAttackTime >= this.attackInterval) {
            this.isBulletValid = false;
            this.imgShot.setWidth(0.0f);
        }
        if (this.isBulletValid) {
            return;
        }
    }
    
    @Override
    public void addBullet(final float n, final float n2) {
        if (!this.isCanAttack()) {
            return;
        }
        MyMethods.delayRun(this.groupGun, this.runAttack, 0.05f);
        this.AddGunRecoil();
        this.lastAttackTime = this.curTime;
        this.isInputValid = false;
        this.duralHandle();
    }
    
    public void checkCollision() {
        if (!this.isBulletValid) {
            return;
        }
        Global.pStart = this.posBulletStart;
        Global.pEnd = this.posBulletEnd;
        this.posBulletStart.set(this.imgGunFront.getRight(), this.imgGunFront.getY() + this.imgGunFront.getHeight() / 2.0f);
        this.groupGun.localToStageCoordinates(this.posBulletStart);
        this.posBulletEnd.set(this.posBulletStart.x + MathUtils.cosDeg(this.randAngle) * 600.0f, this.posBulletStart.y + MathUtils.sinDeg(this.randAngle) * 600.0f);
        final boolean b = false;
        final boolean b2 = false;
        int n = 0;
        boolean b3;
        while (true) {
            b3 = b;
            if (n >= Global.arrEnemyCollision.size) {
                break;
            }
            final BaseEnemy baseEnemy = Global.arrEnemyCollision.get(n);
            if (baseEnemy.getCurrentHp() > 0.0f && this.isOverlap(baseEnemy)) {
                this.enemyBeAttack(baseEnemy);
                b3 = true;
                Effect.addPipeClick(this.pCross.x, this.pCross.y, 90.0f);
                break;
            }
            ++n;
        }
        boolean b4 = b2;
        if (!b3) {
            b4 = b2;
            if (Intersector.intersectSegments(this.posBulletStart, this.posBulletEnd, this.posGroundLeft, this.posGroundRight, this.pCross)) {
                Effect.addPipeClick(this.pCross.x, this.pCross.y, 0.0f);
                b4 = true;
            }
        }
        if (b3 || b4) {
            Global.pCross = this.pCross;
            float dis = MyMethods.getDis(this.posBulletStart, this.pCross);
            if (b3) {
                dis += 5.0f;
            }
            this.imgShot.setWidth(dis);
            this.isBulletValid = false;
            return;
        }
        this.imgShot.setWidth(600.0f);
    }
    
    @Override
    public void checkState() {
        if (this.flashPlayers[this.curIndex].getFrameIndex() >= 3) {
            this.flashPlayers[this.curIndex].setFrameIndex(0);
            this.flashPlayers[this.curIndex].pause();
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        MyMethods.clearPool(Effect.EffectPipeClick.class);
    }
    
    @Override
    public void draw(final SpriteBatch spriteBatch, final float n) {
        super.draw(spriteBatch, n);
    }
    
    public void initFlash() {
        this.flashPlayers = new FlashPlayer[SinglePipe.PlayerNum];
        this.scale = 0.3f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(SinglePipe.strRoot + SinglePipe.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { false })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(85.0f, 40.0f);
        this.setGunOrigin(85.0f, 40.0f);
        this.flashPlayers[0].play();
        this.flashPlayers[0].pause();
        this.flashPlayers[0].SetTimeScale(3.0f);
    }
    
    @Override
    public void myclear() {
        this.clearActions();
        this.lastAttackTime = 0.0f;
        this.curTime = 0.0f;
    }
    
    @Override
    public void setAngle(final float n, final float n2) {
        super.setAngle(n, n2);
        this.posBulletEnd.set(this.posBulletStart.x + MathUtils.cosDeg(this.curAngle) * 600.0f, this.posBulletStart.y + MathUtils.sinDeg(this.curAngle) * 600.0f);
    }
    
    @Override
    public void setPos(final Actor actor, final Vector2 v1, final Vector2 v2) {
        super.setPos(actor, v1, v2);
        actor.setPosition(actor.getX() + 2.0f, actor.getY() - 5.0f);
    }
}
