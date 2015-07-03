package com.gleejet.sun.objects.subgun;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.flash.*;
import com.gleejet.sun.roles.*;
import com.gleejet.sun.roles.boss.*;
import com.gleejet.sun.utils.ui.*;

public class Twine extends BaseSubGun
{
    private static int PlayerNum;
    private static String strEffect;
    private static String[] strPath;
    private static String strRoot;
    private static String strSound;
    private Array<BaseEnemy> arrTarget;
    private TextureAtlas atlasEffect;
    private float duralTime;
    private int maxTwineCount;
    
    static {
        Twine.PlayerNum = 1;
        Twine.strRoot = "subgun/xml/";
        Twine.strPath = new String[] { "twine_total_1" };
        Twine.strEffect = "subgun/pack/twine.pack";
        Twine.strSound = "sound/weapon_tree.ogg";
    }
    
    public Twine() {
        this.arrTarget = new Array<BaseEnemy>();
        this.flashPlayers = new FlashPlayer[Twine.PlayerNum];
        this.scale = 0.35f;
        for (int i = 0; i < this.flashPlayers.length; ++i) {
            (this.flashPlayers[i] = new FlashPlayer(Global.manager.get(Twine.strRoot + Twine.strPath[i] + ".xml", FlashElements.class), Global.manager.get("game/archer.pack", TextureAtlas.class), this.pos, (new boolean[] { true })[i])).setScale(this.scale);
            this.flashPlayers[i].SetFlipX(false);
        }
        this.flashPlayers[Twine.PlayerNum - 1].setAlphaTime(1.5f);
        this.curIndex = 0;
        this.setSize(this.flashPlayers[this.curIndex].getWidth() * this.scale, this.flashPlayers[this.curIndex].getHeight() * this.scale);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.flashPlayers[0].SetOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setFrame(0, 39, 40, 79);
        this.flashPlayers[this.curIndex].play();
        this.flashPlayers[this.curIndex].setFrameIndex(this.idleStart);
        this.effectFrame = 49;
        this.setPosition(90.0f, 67.0f);
        this.atlasEffect = Global.manager.get(Twine.strEffect, TextureAtlas.class);
        (this.actorEffect = new AnimationActor(0.05f, this.atlasEffect.findRegions("twine1"))).setOrigin(30.0f, 137.0f);
        this.actorEffect.setScale(1.0f);
        this.actorEffect.setPosition(158.0f - this.actorEffect.getOriginX(), 102.0f - this.actorEffect.getOriginY());
        (this.actorEffect2 = new AnimationActor(0.05f, this.atlasEffect.findRegions("twine2"))).setOrigin(36.0f, 5.0f);
        this.actorEffect2.setScale(1.0f);
        this.actorEffect2.setLoop(true);
        this.actorEffect2.setPlayCount(12);
        this.subGunIcon.setIcon(Assets.atlasUiGame.findRegion("canrao"));
        this.attackInterval = 10.0f;
        this.maxTwineCount = 3;
        this.duralTime = 4.0f;
        this.setEnhanceLevel();
    }
    
    public static void loadElements() {
        for (int i = 0; i < Twine.strPath.length; ++i) {
            Global.manager.load(Twine.strRoot + Twine.strPath[i] + ".xml", FlashElements.class);
        }
        Global.manager.load(Twine.strEffect, TextureAtlas.class);
        Global.manager.load(Twine.strSound, Sound.class);
    }
    
    public static void unloadElements() {
        for (int i = 0; i < Twine.strPath.length; ++i) {
            Global.manager.unload(Twine.strRoot + Twine.strPath[i] + ".xml");
        }
        Global.manager.unload(Twine.strEffect);
        Global.manager.unload(Twine.strSound);
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        this.checkState();
        for (int i = 0; i < Global.groupSynMove.getChildren().size; ++i) {
            ((AnimationActor)Global.groupSynMove.getChildren().get(i)).translate(-Constant.tranSpeed, 0.0f);
        }
    }
    
    public void addTwine(final float n, final float n2, final float n3) {
        final AnimationActor animationActor = new AnimationActor(0.083333336f, this.atlasEffect.findRegions("twine2"));
        animationActor.setOrigin(36.0f, 5.0f);
        animationActor.setScale(0.65f, 1.0f);
        animationActor.setLoop(true);
        animationActor.setPlayCount((int)(this.duralTime * n3 / 0.5f));
        animationActor.setPosition(n - this.actorEffect2.getOriginX(), n2 - this.actorEffect2.getOriginY());
        animationActor.setStart();
        Global.groupSynMove.addActor(animationActor);
    }
    
    @Override
    public void attack() {
        super.attack();
        this.setTargetArray();
        if (this.arrTarget.size > 0) {
            this.actorEffect2.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    Twine.this.setTargetArray();
                    for (int n = 0; n < Twine.this.arrTarget.size && n < Twine.this.maxTwineCount; ++n) {
                        final BaseEnemy baseEnemy = Twine.this.arrTarget.get(n);
                        float n2;
                        if (baseEnemy instanceof BaseBoss) {
                            n2 = 0.5f;
                        }
                        else {
                            n2 = 1.0f;
                        }
                        baseEnemy.slowSpeed(0.0f, Twine.this.duralTime * n2);
                        Twine.this.addTwine(baseEnemy.getX() + baseEnemy.getWidth() * 0.5f, baseEnemy.getY(), n2);
                    }
                }
            })));
        }
        SoundHandle.playForTree();
    }
    
    public float disToEnmey(final BaseEnemy baseEnemy) {
        return MyMethods.getDis(this.getX(), this.getY(), baseEnemy.getX(), baseEnemy.getY());
    }
    
    @Override
    protected void setEnhanceLevel() {
        super.setEnhanceLevel();
        final int weaponEnhance = PreferHandle.readWeaponEnhance("Twine");
        if (weaponEnhance >= 1) {
            ++this.maxTwineCount;
        }
        if (weaponEnhance >= 2) {
            this.maxTwineCount += 2;
        }
        if (weaponEnhance >= 3) {
            this.maxTwineCount += 3;
        }
    }
    
    public void setTargetArray() {
        this.arrTarget.clear();
        for (int i = 0; i < Global.groupEnemy.getChildren().size; ++i) {
            final BaseEnemy baseEnemy = (BaseEnemy)Global.groupEnemy.getChildren().get(i);
            if (baseEnemy.GetCategray() == Constant.EnemyCategray.Ground && baseEnemy.getCurrentHp() > 0.0f) {
                this.arrTarget.add(baseEnemy);
            }
        }
        this.sortArray();
    }
    
    public void sortArray() {
        for (int i = 0; i < this.arrTarget.size - 1; ++i) {
            for (int j = i + 1; j < this.arrTarget.size; ++j) {
                if (this.disToEnmey(this.arrTarget.get(j)) < this.disToEnmey(this.arrTarget.get(i))) {
                    this.arrTarget.swap(i, j);
                }
            }
        }
    }
}
