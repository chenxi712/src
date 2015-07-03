package com.fxb.razor.stages;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.roles.air.*;
import com.fxb.razor.roles.*;
import com.fxb.razor.roles.boss.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.utils.*;
import com.fxb.razor.stages.dialogs.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.objects.subgun.*;
import com.badlogic.gdx.*;
import com.fxb.razor.objects.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.*;
import com.fxb.razor.common.*;
import com.fxb.razor.*;
import com.fxb.razor.utils.action.*;
import com.fxb.razor.objects.maingun.*;

public class GameStage extends Stage
{
    Actor actorShake;
    public Actor actorTarget;
    float aniStartTime;
    int attackPointer;
    Vector2 attackPos;
    float blinkGapTime;
    float bossEnemyGap;
    private MyImage btnPause;
    private Circle circle;
    float comboTime;
    private float curTime;
    float curTime1;
    private DialogCountDown dialogCountDown;
    private DialogOver dialogOver;
    private DialogPause dialogPause;
    private DialogRevive dialogRevive;
    float endTouchTime;
    int endlessLevel;
    BaseEnemy enemyTarget;
    private Flag flag;
    private Group groupArrow;
    private Group groupBulletEnemy;
    private Group groupBulletPlayer;
    private Group groupEnemy;
    private Group groupPlayer;
    private HelpBoard helpBoard;
    private MyImage imgAim;
    private MyImage imgBlood;
    private MyImage imgNobullets;
    InputListener inputListener;
    int insturcStep;
    boolean isAniValid;
    boolean isBlink;
    boolean isLevelEnd;
    boolean isTouched;
    private MyLabel labelCoin;
    float lastBossEnemyTime;
    float lastBuildLength;
    float lastMoveLength;
    private float lastSortTime;
    float oriGapTime;
    float oriPauseTime;
    Vector2 pCross;
    float pauseTime;
    private Player player;
    private Vector3 point;
    int reviveCount;
    float showTime;
    int singleBlinkCount;
    float speedUpTime;
    float startAttackTime;
    float startX;
    float startY;
    int totalBlinkCount;
    
    public GameStage() {
    	super(800.0f, 480.0f, false, Global.batch);
        this.point = new Vector3();
        this.circle = new Circle();
        this.insturcStep = 0;
        this.oriGapTime = 0.1f;
        this.blinkGapTime = this.oriGapTime;
        this.oriPauseTime = 1.0f;
        this.pauseTime = 0.0f;
        this.isBlink = false;
        this.singleBlinkCount = 5;
        this.totalBlinkCount = 3;
        this.lastBossEnemyTime = 0.0f;
        this.bossEnemyGap = 0.8f;
        this.startAttackTime = 0.0f;
        this.pCross = new Vector2();
        this.aniStartTime = 0.0f;
        this.startX = 0.0f;
        this.startY = 0.0f;
        this.isAniValid = false;
        this.reviveCount = 0;
        this.isLevelEnd = false;
        this.lastMoveLength = 0.0f;
        this.lastBuildLength = 0.0f;
        this.endlessLevel = 1;
        this.comboTime = 0.0f;
        this.speedUpTime = 0.0f;
        this.showTime = 0.0f;
        this.curTime1 = 0.0f;
        this.inputListener = new InputListener() {
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.downAction());
                return true;
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.upAction());
                if (listenerActor == GameStage.this.btnPause) {
                    SoundHandle.playForButton2();
                    GameStage.this.pauseGame(false);
                }
                super.touchUp(inputEvent, n, n2, n3, n4);
            }
        };
        this.isTouched = false;
        this.endTouchTime = 0.0f;
        this.attackPointer = -1;
        this.attackPos = new Vector2();
        this.init();
    }
    
    private void checkCombo(final float n) {
        if (this.comboTime > 0.0f) {
            this.comboTime -= n;
            if (this.comboTime <= 0.0f) {
                if (Global.curCombo > Global.maxCombo) {
                    Global.maxCombo = Global.curCombo;
                }
                Global.curCombo = 0;
                Global.labelComboNumber.setVisible(false);
                Global.labelComboShow.setVisible(false);
            }
        }
    }
    
    private void checkMoveSpeed(final float n) {
        if (this.curTime > 5.0f && Global.arrEnemyCreate.size < 1 && this.groupEnemy.getChildren().size < 1) {
            if (this.speedUpTime >= 0.5f) {
                Constant.tranSpeed = 2.1f;
                return;
            }
            this.speedUpTime += n;
            Constant.tranSpeed = 0.35f * Interpolation.linear.apply(1.0f, 6.0f, this.speedUpTime / 0.5f);
        }
    }
    
    private void clearPoolNum() {
        for (int i = 0; i < this.player.getArrayMainGun().size; ++i) {
            this.player.getArrayMainGun().get(i).dispose();
        }
        MyMethods.clearPool(Coin.class);
        MyMethods.clearPool(MyLabel.class);
        MyMethods.clearPool(Effect.EffectSmoke.class);
    }
    
    private int getReviveMondNum() {
        return Math.min(10, this.reviveCount * 2);
    }
    
    private int getStarNum() {
        if (this.reviveCount > 0) {
            return 2;
        }
        int n = 3;
        if (Global.enemyKill < Global.totalEnemy) {
            Global.isAllKill = false;
        }
        if (!Global.isAllKill) {
            n = 3 - 1;
        }
        int n2 = 0;
        int n3;
        for (int i = 0; i < this.player.getArrayMainGun().size; ++i, n2 = n3) {
            n3 = n2;
            if (this.player.getArrayMainGun().get(i).getCurHp() <= 0.0f) {
                n3 = n2 + 1;
            }
        }
        return Math.max(1, n - n2);
    }
    
    private void initCountDown() {
        this.getRoot().addAction(Actions.sequence(Actions.delay(0.2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                Global.gameState = Constant.GameState.Game_PreStart;
                GameStage.this.dialogCountDown = new DialogCountDown();
                final DialogCountDown access$000 = GameStage.this.dialogCountDown;
                GameStage.this.addActor(access$000.getShade());
                access$000.setScale(0.2f);
                access$000.addAction(Actions.scaleTo(1.0f, 1.0f, 0.35f, Interpolation.swingOut));
                GameStage.this.addActor(access$000);
            }
        })));
    }
    
    private void initForEndless() {
        if (Global.isEndlessMode) {
            Global.tranLength = 0.0f;
        }
    }
    
    private void initPool() {
        MyMethods.initPoolNum(Coin.class, 12);
        MyMethods.initPoolNum(MyLabel.class, 10);
        MyMethods.initPoolNum(Effect.EffectSmoke.class, 5);
    }
    
    private void sortEnemy(final float n) {
        if (this.curTime - this.lastSortTime < 0.1f) {
            return;
        }
        this.lastSortTime = this.curTime;
        MyMethods.sortArray(Global.arrEnemyCollision);
    }
    
    public void BossCallHandle() {
        if (Global.bossCallState == -1 && Global.bossLevelState == Constant.BossLevelState.Boss_Start && this.curTime - this.lastBossEnemyTime > this.bossEnemyGap && Global.arrBossCallEnemy.size > 0) {
            final BaseEnemy baseEnemy = Global.arrBossCallEnemy.get(0);
            Global.arrBossCallEnemy.removeIndex(0);
            this.addEnemyToGroup(baseEnemy);
            this.lastBossEnemyTime = this.curTime;
        }
    }
    
    public void EnemyHandle() {
        int n;
        for (int i = 0; i < Global.arrEnemyCreate.size; i = n + 1) {
            final BaseEnemy baseEnemy = Global.arrEnemyCreate.get(i);
            baseEnemy.translate(-Constant.tranSpeed, 0.0f);
            if (baseEnemy.getX() < 1100.0f && baseEnemy instanceof Flag) {
                Global.arrEnemyCreate.removeIndex(i);
                n = i - 1;
                this.addEnemyToGroup(baseEnemy);
            }
            else {
                n = i;
                if (baseEnemy.getX() < 815.0f) {
                    Global.arrEnemyCreate.removeIndex(i);
                    n = i - 1;
                    this.addEnemyToGroup(baseEnemy);
                }
            }
        }
    }
    
    @Override
    public void act(final float blink) {
        if (Global.gameState == Constant.GameState.Game_On) {
            super.act(blink);
            this.curTime += blink;
            this.EnemyHandle();
            this.checkCollision();
            this.addPlayerBullet();
            this.checkPlayerHp();
            this.checkLevel();
            this.sortEnemy(blink);
            if (this.isBlink) {
                this.setBlink(blink);
            }
            if (Global.isEndlessMode) {
                this.addEndlessEnemy();
            }
            else {
                this.checkMoveSpeed(blink);
            }
            this.checkCombo(blink);
            this.BossCallHandle();
        }
        else if (Global.gameState == Constant.GameState.Game_Pause) {
            this.dialogPause.act(blink);
            final Actor actor = this.getRoot().findActor("fadeOutShade");
            if (actor != null) {
                actor.act(blink);
            }
        }
        else if (Global.gameState == Constant.GameState.Game_PreStart) {
            this.dialogCountDown.act(blink);
        }
        else if (Global.gameState == Constant.GameState.Game_Unlock) {
            this.flag.getGroup().act(blink);
        }
        else if (Global.gameState == Constant.GameState.Level_Lose || Global.gameState == Constant.GameState.Level_Win) {
            this.dialogOver.act(blink);
            final Actor actor2 = this.getRoot().findActor("fadeOutShade");
            if (actor2 != null) {
                actor2.act(blink);
            }
        }
        else if (Global.gameState == Constant.GameState.Game_Instruct) {
            this.helpBoard.imgShade.act(blink);
            this.helpBoard.imgHand.act(blink);
            this.helpBoard.arrowRect.act(blink);
            if (this.insturcStep == 21 || this.insturcStep == 22) {
                this.actorTarget.act(blink);
                for (int i = 0; i < this.player.getArrayMainGun().size; ++i) {
                    this.player.getArrayMainGun().get(i).act(blink);
                }
            }
            if (this.helpBoard.insStep == 91) {
                this.actorTarget.act(blink);
            }
        }
        else if (Global.gameState == Constant.GameState.Game_Revive) {
            this.dialogRevive.act(blink);
            final DialogStore dialogStore = this.dialogRevive.getDialogStore();
            if (dialogStore.getParent() != null) {
                dialogStore.act(blink);
            }
        }
        if (this.actorShake.getActions().size > 0) {
            this.getCamera().position.set(400.0f + this.actorShake.getX(), 240.0f + this.actorShake.getY(), 0.0f);
            Global.deltaY = this.actorShake.getY();
        }
    }
    
    public void addEndlessEnemy() {
        final float min = Math.min(1.0f, Global.tranLength / 5000.0f);
        final float apply = Interpolation.pow2Out.apply(25.0f, 5.0f, min);
        if (Global.tranLength - this.lastMoveLength >= MathUtils.random(apply, apply + 5.0f)) {
            Control.addEndlessEnemy(true);
            this.lastMoveLength = Global.tranLength;
        }
        final float apply2 = Interpolation.pow2Out.apply(500.0f, 150.0f, min);
        if (Global.tranLength >= 1000.0f && Global.tranLength - this.lastBuildLength >= MathUtils.random(apply2, 50.0f + apply2)) {
            Control.addEndlessEnemy(false);
            this.lastBuildLength = Global.tranLength;
        }
        if (this.endlessLevel == 1 && Global.tranLength > 1000.0f) {
            Global.endlessHashState = 2;
            this.endlessLevel = 2;
        }
        else {
            if (this.endlessLevel == 2 && Global.tranLength > 2500.0f) {
                Global.endlessHashState = 3;
                this.endlessLevel = 3;
                return;
            }
            if (this.endlessLevel == 3 && Global.tranLength > 4000.0f) {
                Global.endlessHashState = 4;
                this.endlessLevel = 4;
            }
        }
    }
    
    public void addEnemyToGroup(final BaseEnemy baseEnemy) {
        final Group groupEnemy = this.groupEnemy;
        baseEnemy.Clear();
        if (baseEnemy instanceof Air) {
            baseEnemy.setY(MathUtils.random(120, 380));
        }
        else {
            baseEnemy.setY(105.0f);
        }
        Group groupFlag = null;
        Label_0114: {
            if (baseEnemy instanceof FlashEnemy) {
                ((FlashEnemy)baseEnemy).getCurFlash().setPosition(baseEnemy.getX(), baseEnemy.getY());
                groupFlag = groupEnemy;
                if (baseEnemy instanceof BaseBoss) {
                    Global.bossLevelState = Constant.BossLevelState.Boss_Start;
                    Constant.tranSpeed = 0.0f;
                    Global.curBossType = baseEnemy.getType();
                    this.startBlink((BaseBoss)baseEnemy);
                    if (baseEnemy instanceof Bear1 || baseEnemy instanceof Bear2) {
                        ((BaseBoss)baseEnemy).getCurFlash().pause();
                        groupFlag = groupEnemy;
                    }
                    else if (baseEnemy instanceof Wasp1) {
                        baseEnemy.setY(200.0f);
                        groupFlag = groupEnemy;
                    }
                    else if (baseEnemy instanceof Wasp2) {
                        baseEnemy.setY(150.0f);
                        groupFlag = groupEnemy;
                    }
                    else if (baseEnemy instanceof BDragon1) {
                        baseEnemy.setY(84.0f);
                        groupFlag = groupEnemy;
                    }
                    else {
                        groupFlag = groupEnemy;
                        if (baseEnemy instanceof BDragon2) {
                            baseEnemy.setY(84.0f);
                            groupFlag = groupEnemy;
                        }
                    }
                }
            }
            else if (baseEnemy instanceof Flag) {
                (this.flag = (Flag)baseEnemy).setFlagPos(this.flag.getX(), 110.0f);
                groupFlag = Global.groupFlag;
            }
            else {
                if (baseEnemy.getType() != Constant.EnemyType.Box1 && baseEnemy.getType() != Constant.EnemyType.Box2) {
                    groupFlag = groupEnemy;
                    if (baseEnemy.getType() != Constant.EnemyType.Box3) {
                        break Label_0114;
                    }
                }
                baseEnemy.setY(100.0f);
                groupFlag = groupEnemy;
            }
        }
        baseEnemy.setPlayer(this.player);
        groupFlag.addActor(baseEnemy);
        Global.arrEnemyCollision.add(baseEnemy);
    }
    
    public void addInstruction() {
        if (Global.maxGameLevelEasy == Global.curGameLevelEasy && Global.maxGameLevelEasy == 1) {
            this.helpBoard = new HelpBoard();
            (this.helpBoard.imgShade = MyShade.createShade(this)).setTouchable(Touchable.disabled);
            this.helpBoard.arrowRect = ArrowRect.createArrowRect(this, 100.0f, 50.0f, 550.0f, 150.0f);
            this.helpBoard.imgHand = UiHandle.createHand(this, this.helpBoard.arrowRect);
//            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, "Tap to fire!", this.helpBoard.arrowRect, -1, 1);
            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, Language.tapToFire(), this.helpBoard.arrowRect, -1, 1);
            Global.gameState = Constant.GameState.Game_Instruct;
            this.insturcStep = 11;
        }
        if (Global.maxGameLevelEasy == Global.curGameLevelEasy && Global.maxGameLevelEasy == 2 && this.player.getArrayMainGun().size == 2 && Global.arrStrMainSelect.contains("Cannon", false)) {
            this.helpBoard = new HelpBoard();
            this.actorTarget = this.player.getArrayMainGun().get(Global.arrStrMainSelect.indexOf("Cannon", false)).getGunIcon();
            this.helpBoard.imgShade = MyShade.createShade(this);
            this.actorTarget.remove();
            this.addActor(this.actorTarget);
            this.helpBoard.arrowRect = ArrowRect.createArrowRect(this, this.actorTarget);
//            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, "Tap to switch weapon!", this.actorTarget, 1, 1);
            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, Language.tapToSwitchWeapon(), this.actorTarget, 1, 1);
            this.helpBoard.imgHand = UiHandle.createHand(this, this.actorTarget);
            Global.gameState = Constant.GameState.Game_Instruct;
            this.insturcStep = 21;
        }
        if (Global.maxGameLevelEasy == Global.curGameLevelEasy && Global.maxGameLevelEasy == 9 && Global.strSubGun.equals("Twine")) {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    (GameStage.this.actorTarget = GameStage.this.player.getSubGun().getSubGunIcon()).setTouchable(Touchable.enabled);
//                    GameStage.this.helpBoard = HelpBoard.createDialogBoard(GameStage.this, GameStage.this.actorTarget, "Tap to use skill!", 1, 1);
                    GameStage.this.helpBoard = HelpBoard.createDialogBoard(GameStage.this, GameStage.this.actorTarget, Language.tapToUseSkill(), 1, 1);
                    Global.gameState = Constant.GameState.Game_Instruct;
                    GameStage.this.helpBoard.insStep = 91;
                }
            };
            this.player.getSubGun().getSubGunIcon().setTouchable(Touchable.disabled);
            MyMethods.delayRun(this.getRoot(), runnable, 8.0f);
        }
    }
    
    public void addPlayerBullet() {
        if (this.isTouched && Global.gameState == Constant.GameState.Game_On) {
            this.player.getCurMainGun().addBullet(this.attackPos.x, this.attackPos.y);
            this.startAttackTime = this.curTime;
        }
    }
    
    public void checkCollision() {
        for (int i = this.groupBulletPlayer.getChildren().size - 1; i >= 0; --i) {
            final BulletPlayer bulletPlayer = (BulletPlayer)this.groupBulletPlayer.getChildren().get(i);
            if (bulletPlayer.isValid && bulletPlayer.bulletType != Constant.BulletPlayerType.Shock) {
                int j = 0;
                while (j < Global.arrEnemyCollision.size) {
                    final BaseEnemy baseEnemy = Global.arrEnemyCollision.get(j);
                    if (baseEnemy.getCurrentHp() > 0.0f && bulletPlayer.isOverlap(baseEnemy, this.pCross)) {
                        if (bulletPlayer.isAreaAttack()) {
                            this.circle.set(baseEnemy.getX() + baseEnemy.getWidth() / 2.0f, baseEnemy.getY() + baseEnemy.getHeight() / 2.0f, bulletPlayer.getRadius());
                            for (int k = 0; k < this.groupEnemy.getChildren().size; ++k) {
                                final BaseEnemy baseEnemy2 = (BaseEnemy)this.groupEnemy.getChildren().get(k);
                                if (baseEnemy2.getCurrentHp() > 0.0f && Collision.IsOverlap(baseEnemy2, this.circle)) {
                                    baseEnemy2.BeAttacked(bulletPlayer);
                                }
                            }
                        }
                        else {
                            baseEnemy.BeAttacked(bulletPlayer);
                        }
                        final boolean b = true;
                        boolean collisonCheck;
                        if (bulletPlayer.bulletType == Constant.BulletPlayerType.Freezefog) {
                            final BaseMainGun curMainGun = this.player.getCurMainGun();
                            collisonCheck = b;
                            if (MathUtils.random(0.0f, 1.0f) > curMainGun.freezeProb) {
                                baseEnemy.beFreeze(0.6f, curMainGun.freezeTime);
                                collisonCheck = b;
                            }
                        }
                        else if (bulletPlayer.bulletType == Constant.BulletPlayerType.Flame) {
                            collisonCheck = b;
                            if (MathUtils.random(0.0f, 1.0f) > 0.3f) {
                                baseEnemy.beFlame(30.0f, 3.0f);
                                collisonCheck = b;
                            }
                        }
                        else if (bulletPlayer.bulletType == Constant.BulletPlayerType.Acid) {
                            collisonCheck = b;
                            if (MathUtils.random(0.0f, 1.0f) > 0.3f) {
                                baseEnemy.beAcid(10.0f, 3.0f);
                                collisonCheck = b;
                            }
                        }
                        else {
                            collisonCheck = b;
                            if (bulletPlayer.bulletType == Constant.BulletPlayerType.Leap) {
                                collisonCheck = ((Bullet13)bulletPlayer).collisonCheck();
                            }
                        }
                        if (collisonCheck) {
                            bulletPlayer.remove();
                            Pools.free(bulletPlayer);
                            break;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
            }
        }
    }
    
    public void checkLevel() {
        if (!Global.isEndlessMode && !this.isLevelEnd && Global.levelRate >= 0.95f) {
            this.isLevelEnd = true;
            this.flag.levelOver();
        }
    }
    
    public void checkPlayerHp() {
        if (!this.player.isDead() && this.player.getCurMainGun().getCurHp() <= 0.0f) {
            this.player.getCurMainGun().die();
            boolean b = false;
            int n = 0;
            int mainGunCount = 0;
            int n2;
            int n3;
            for (int i = 0; i < this.player.getArrayMainGun().size; ++i, mainGunCount = n2, n = n3) {
                final BaseMainGun curGun = this.player.getArrayMainGun().get(i);
                n2 = mainGunCount;
                n3 = n;
                if (curGun.getCurHp() > 0.0f) {
                    final boolean b2 = true;
                    n2 = mainGunCount + 1;
                    b = b2;
                    if ((n3 = n) == 0) {
                        this.player.setCurGun(curGun);
                        n3 = 1;
                        b = b2;
                        n2 = n2;
                    }
                }
            }
            Global.mainGunCount = mainGunCount;
            if (!b) {
                ++this.reviveCount;
                this.dialogRevive.setMondNum(Math.min(10, this.reviveCount * 2));
                DialogHandle.openDialog(this, this.dialogRevive, 0.35f);
                Global.gameState = Constant.GameState.Game_Revive;
            }
        }
    }
    
    public void coinChanged() {
        this.labelCoin.setText(StrHandle.get(Global.inCoinGet));
    }
    
    @Override
    public void dispose() {
        super.dispose();
        Global.arrEnemyCollision.clear();
        Global.arrEnemyCreate.clear();
        Global.arrEnemyType.clear();
        Global.arrBossCallEnemy.clear();
        this.groupEnemy.clear();
        this.groupBulletEnemy.clear();
        this.groupBulletPlayer.clear();
        Global.groupFlag.clear();
        Global.groupEffectPlayer.clear();
        Global.groupEffectSmoke.clear();
        Global.groupSynMove.clear();
        Global.groupDamage.clear();
        Global.groupCoin.clear();
        this.clearPoolNum();
        if (Global.isEndlessMode) {
            if (!Global.isGameAgain) {
                Global.isEndlessMode = false;
            }
            for (int i = 0; i < Constant.EnemyType.values().length - 11; ++i) {
                final Constant.EnemyType enemyType = Constant.EnemyType.values()[i];
                if (Global.mapEnemy.get(enemyType) != null) {
                    Global.mapEnemy.get(enemyType).clear();
                }
            }
            Global.mapEnemy.clear();
            System.gc();
        }
    }
    
    public void doInstruction() {
        if (this.insturcStep == 11) {
            this.helpBoard.remove();
            Global.gameState = Constant.GameState.Game_On;
            this.insturcStep = 12;
        }
        if (this.insturcStep == 21) {
            this.actorTarget.remove();
            this.helpBoard.groupBoard.remove();
            this.groupPlayer.addActor(this.actorTarget);
            this.helpBoard.imgShade.setTouchable(Touchable.disabled);
            this.insturcStep = 22;
            for (int i = 0; i < this.groupEnemy.getChildren().size; ++i) {
                final BaseEnemy enemyTarget = (BaseEnemy)this.groupEnemy.getChildren().get(i);
                if (enemyTarget.getType() == Constant.EnemyType.Tower1) {
                    this.addActor(this.enemyTarget = enemyTarget);
                    break;
                }
            }
            this.helpBoard.arrowRect.toFront();
//            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, "Artillery can destroy enemy's \ndefensive towers effectively!", this.enemyTarget, -1, 1);
            this.helpBoard.groupBoard = UiHandle.createInsturctionBoard(this, Language.tapToDestroyTower(), this.enemyTarget, -1, 1);
            this.helpBoard.imgHand.toFront();
            this.helpBoard.arrowRect.setTarget(this.enemyTarget);
            UiHandle.setHandTarget(this.helpBoard.imgHand, this.enemyTarget);
        }
        else if (this.insturcStep == 22) {
            this.helpBoard.remove();
            Global.gameState = Constant.GameState.Game_On;
            this.groupEnemy.addActor(this.enemyTarget);
            Global.arrEnemyCollision.add(this.enemyTarget);
            this.insturcStep = 23;
        }
        if (this.helpBoard != null && this.helpBoard.insStep == 91) {
            final SubGunIcon subGunIcon = this.player.getSubGun().getSubGunIcon();
            subGunIcon.remove();
            this.groupPlayer.addActor(subGunIcon);
            this.helpBoard.remove();
            this.helpBoard = null;
            Global.gameState = Constant.GameState.Game_On;
        }
    }
    
    @Override
    public void draw() {
        super.draw();
        Global.batch.totalRenderCalls = 0;
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glEnable(3042);
        if (Global.gameState == Constant.GameState.Game_On) {
            Gdx.gl.glEnable(3553);
            Assets.textureTrace.bind();
            for (int i = 0; i < this.groupBulletEnemy.getChildren().size; ++i) {
                ((BulletEnemy)this.groupBulletEnemy.getChildren().get(i)).mesh.render(5);
            }
        }
    }
    
    public void drawFilled() {
        if (Constant.isShowEdge) {
            Global.rend.begin(ShapeRenderer.ShapeType.Filled);
            Global.rend.setColor(Color.BLUE);
            Global.rend.circle(Global.pMain1.x, Global.pMain1.y, 5.0f);
            Global.rend.setColor(Color.RED);
            Global.rend.circle(Global.pMain2.x, Global.pMain2.y, 5.0f);
            Global.rend.setColor(Color.YELLOW);
            Global.rend.circle(Global.pSub1.x, Global.pSub1.y, 5.0f);
            Global.rend.setColor(Color.GREEN);
            Global.rend.circle(Global.pSub2.x, Global.pSub2.y, 5.0f);
            Global.rend.end();
        }
    }
    
    public void drawLine() {
        Gdx.gl.glLineWidth(3.0f);
        Global.rend.begin(ShapeRenderer.ShapeType.Line);
        if (Constant.isShowEdge) {
            Global.rend.setColor(Color.BLUE);
            this.player.showEdge();
            for (int i = 0; i < this.groupEnemy.getChildren().size; ++i) {
                ((BaseEnemy)this.groupEnemy.getChildren().get(i)).showEdge();
            }
            Global.rend.setColor(Color.CYAN);
            for (int j = 0; j < this.groupBulletPlayer.getChildren().size; ++j) {
                ((BulletPlayer)this.groupBulletPlayer.getChildren().get(j)).showPolygon();
            }
            Global.rend.setColor(Color.RED);
            Global.rend.circle(this.pCross.x, this.pCross.y, 5.0f);
            if (Global.bullet != null) {
                MyMethods.showPolygon(Global.rend, Global.bullet.polygon);
            }
            if (Global.pStart != null && Global.pEnd != null) {
                Global.rend.circle(Global.pStart.x, Global.pStart.y, 3.0f);
                Global.rend.circle(Global.pEnd.x, Global.pEnd.y, 3.0f);
            }
            Global.rend.setColor(Color.GREEN);
            if (Global.pCross != null) {
                Global.rend.circle(Global.pCross.x, Global.pCross.y, 3.0f);
            }
            Global.rend.setColor(Color.RED);
            Global.rend.circle(Global.p5.x + Global.pAdd.x, Global.p5.y + Global.pAdd.y, 3.0f);
            Global.rend.setColor(Color.RED);
            Global.rend.circle(Global.ptStart.x, Global.ptStart.y, 3.0f);
            for (int k = 0; k < this.groupBulletEnemy.getChildren().size; ++k) {
                MyMethods.showEdge(Global.rend, (Actor)this.groupBulletEnemy.getChildren().get(k));
            }
        }
        Global.rend.end();
    }
    
    public void init() {
        Global.gameState = Constant.GameState.Game_On;
        Global.groupFlag = UiHandle.createGroup(this, false);
        final Group group = UiHandle.createGroup(this, false);
        this.groupBulletPlayer = group;
        Global.groupBulletPlayer = group;
        this.addActor(this.player = new Player());
        this.groupPlayer = UiHandle.createGroup(this, false);
        final Group group2 = UiHandle.createGroup(this, false);
        this.groupEnemy = group2;
        Global.groupEnemy = group2;
        final Group group3 = UiHandle.createGroup(this, false);
        this.groupBulletEnemy = group3;
        Global.groupBulletEnemy = group3;
        (this.dialogOver = new DialogOver()).setPosition((this.getWidth() - this.dialogOver.getWidth()) / 2.0f, (this.getHeight() - this.dialogOver.getHeight()) / 2.0f);
        (this.dialogPause = new DialogPause()).setPosition((this.getWidth() - this.dialogPause.getWidth()) / 2.0f, (this.getHeight() - this.dialogPause.getHeight()) / 2.0f);
        this.btnPause = UiHandle.addItem(this.getRoot(), Assets.atlasUiGame, "zanting", 754.0f, 435.0f, this.inputListener);
        Global.levelRate = 0.0f;
        for (int i = 0; i < this.player.getArrayMainGun().size; ++i) {
            this.groupPlayer.addActor(this.player.getArrayMainGun().get(i).getGunIcon());
            this.groupPlayer.addActor(this.player.getArrayMainGun().get(i).getGroupHp());
        }
        Global.mainGunCount = this.player.getArrayMainGun().size;
        if (Global.mainGunCount == 1) {
            this.player.getArrayMainGun().get(0).getGunIcon().setVisible(false);
        }
        if (this.player.getSubGun() != null) {
            this.groupPlayer.addActor(this.player.getSubGun().getSubGunIcon());
        }
        this.addActor(Global.groupEffectPlayer);
        this.addActor(Global.groupEffectSmoke);
        this.addActor(Global.groupSynMove);
        this.addActor(Global.groupCoin);
        this.addActor(Global.groupDamage);
        Global.arrEnemyCollision.clear();
        this.curTime = 0.0f;
        this.lastSortTime = 0.0f;
        Global.tranLength = 0.0f;
        Global.isAllKill = true;
        Global.bossLevelState = Constant.BossLevelState.Boss_Hide;
        Global.arrBossCallEnemy.clear();
        Constant.tranSpeed = 0.35f;
        Global.enemyKill = 0;
        Global.coinGet = 0.0f;
        Global.inCoinGet = 0;
        this.labelCoin = MyLabel.obtain(this.getRoot(), StrHandle.get((int)Global.coinGet), Constant.FontType.common, 333.0f, 439.0f);
        Global.labelComboNumber = MyLabel.obtain(Global.groupDamage, StrHandle.get(Global.curCombo), Constant.FontType.combo, 645.0f, 395.0f);
        Global.labelComboShow = MyLabel.obtain(Global.groupDamage, "*", Constant.FontType.combo, 678.0f, 393.0f);
        this.addActor(this.actorShake = new Actor());
        this.initForEndless();
        Global.curCombo = 0;
        Global.maxCombo = 0;
        this.initPool();
        this.imgAim = UiHandle.createAim(this);
        Global.imgAim = this.imgAim;
        this.imgNobullets = UiHandle.createNobullets(this);
        this.imgBlood = UiHandle.createBlood(this);
        this.dialogRevive = new DialogRevive(this);
        this.initCountDown();
        Global.isGameAgain = false;
        Global.isAutoSpesold = false;
        Global.isAutoRate = false;
        MusicHandle.playForBattle();
    }
    
    public boolean isTouchValid(final float n, final float n2) {
        return n >= 240.0f && (n <= this.btnPause.getX() || n2 <= this.btnPause.getY());
    }
    
    public void keyBack() {
        if (this.curTime < 0.5f) {
            return;
        }
        if (this.dialogRevive != null && this.dialogRevive.getParent() != null) {
            this.dialogRevive.keyBack();
            return;
        }
        this.pauseGame(true);
    }
    
    @Override
    public boolean keyTyped(final char c) {
        if (c == 'w') {
            Global.pAdd.add(0.0f, 1.0f);
        }
        else if (c == 's') {
            Global.pAdd.add(0.0f, -1.0f);
        }
        else if (c == 'a') {
            Global.pAdd.add(-1.0f, 0.0f);
        }
        else if (c == 'd') {
            Global.pAdd.add(1.0f, 0.0f);
        }
        if (Global.selectActor != null) {
            Global.selectActor.setPosition(Global.oriX + Global.pAdd.x, Global.oriY + Global.pAdd.y);
        }
        return super.keyTyped(c);
    }
    
    public void levelLose() {
        this.player.die();
        this.dialogOver.setFail();
        this.dialogOver.setY(this.getHeight());
        this.dialogOver.addAction(Actions.sequence(Actions.delay(1.0f), Actions.moveTo(this.dialogOver.getX(), (this.getHeight() - this.dialogOver.getHeight()) / 2.0f + 20.0f, 0.15f, Interpolation.bounceIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                Global.gameState = Constant.GameState.Level_Lose;
                GameStage.this.addActor(GameStage.this.dialogOver.getImgShade());
                GameStage.this.dialogOver.toFront();
                ++Global.loseCount;
                Global.isAutoStoreEnhance = (Global.maxGameLevelEasy > 8);
                ++Global.gamePlayNum;
                if (Global.gamePlayNum >= 3) {
                    PlatformHandle.showAdBig();
                    Global.gamePlayNum = 0;
                }
                PlatformHandle.showFeatureView();
            }
        })));
        this.addActor(this.dialogOver);
        PreferHandle.writeCommon();
        PreferHandle.writeTypeKill();
//        if (Global.isEndlessMode) {
//            FlurryHandle.levelChallengeEndless(Constant.PlayState.Lose, Global.getEndlessCoin());
//        }
//        else if (Global.gameMode == Constant.GameMode.Easy) {
//            FlurryHandle.levelChallengeEasy(Global.curGameLevelEasy, Constant.PlayState.Lose);
//        }
//        else if (Global.gameMode == Constant.GameMode.Hard) {
//            FlurryHandle.levelChallengeHard(Global.curGameLevelHard, Constant.PlayState.Lose);
//        }
        MusicHandle.playForLose();
    }
    
    public void levelWin() {
        boolean isAutoRate = false;
        this.dialogOver.setWin();
        final int starNum = this.getStarNum();
        this.dialogOver.setStar(starNum);
        this.dialogOver.setY(this.getHeight());
        if (Global.isUnlockWeapon()) {
            final String unlockName = Global.getUnlockName();
            int n;
            if (MyMethods.findIndex(Constant.strMains, unlockName) >= 0) {
                n = 1;
            }
            else {
                n = 0;
            }
            if (n != 0) {
                Global.arrMainGunGet.add(unlockName);
//                FlurryHandle.weaponUnlock(unlockName, true);
            }
            else {
                Global.arrSubGunGet.add(unlockName);
//                FlurryHandle.weaponUnlock(unlockName, false);
            }
        }
        if (Global.gameMode == Constant.GameMode.Easy) {
            if (Global.curGameLevelEasy == Global.maxGameLevelEasy) {
                if (Global.maxGameLevelEasy == 8 || Global.maxGameLevelEasy == 15 || Global.maxGameLevelEasy == 20) {
                    if (Global.autoRateCount < 3) {
                        isAutoRate = true;
                    }
                    Global.isAutoRate = isAutoRate;
                }
                if (Global.maxGameLevelEasy == 6) {
                    Global.isAutoSpesold = true;
                }
                if (Global.maxGameLevelEasy == 3) {
                    Global.totalCoinNum += 500.0f;
                }
                ++Global.maxGameLevelEasy;
//                FlurryHandle.maxLevelEasy(++Global.maxGameLevelEasy);
            }
            PreferHandle.writeLevelStar(Global.curGameLevelEasy, Math.max(starNum, PreferHandle.readLevelStar(Global.curGameLevelEasy, Global.gameMode)), Global.gameMode);
//            FlurryHandle.starNumEasy(Global.curGameLevelEasy, starNum);
        }
        else {
            if (Global.curGameLevelHard == Global.maxGameLevelHard) {
            	++Global.maxGameLevelHard;
//                FlurryHandle.maxLevelHard(++Global.maxGameLevelHard);
            }
            PreferHandle.writeLevelStar(Global.curGameLevelHard, Math.max(starNum, PreferHandle.readLevelStar(Global.curGameLevelHard, Global.gameMode)), Global.gameMode);
//            FlurryHandle.starNumHard(Global.curGameLevelHard, starNum);
        }
        PreferHandle.writeCommon();
        PreferHandle.writeWeaponGet();
        PreferHandle.writeTypeKill();
//        if (Global.isEndlessMode) {
//            FlurryHandle.levelChallengeEndless(Constant.PlayState.Win, Global.getEndlessCoin());
//        }
//        else if (Global.gameMode == Constant.GameMode.Easy) {
//            FlurryHandle.levelChallengeEasy(Global.curGameLevelEasy, Constant.PlayState.Win);
//        }
//        else if (Global.gameMode == Constant.GameMode.Hard) {
//            FlurryHandle.levelChallengeHard(Global.curGameLevelHard, Constant.PlayState.Win);
//        }
        this.showDialogOver();
    }
    
    public void myclear() {
        this.clear();
        this.init();
    }
    
    public void pauseGame(final boolean b) {
        if (Global.gameState == Constant.GameState.Game_On) {
            this.addActor(this.dialogPause.getImgShade());
            this.addActor(this.dialogPause);
            this.dialogPause.setY(this.getHeight());
            this.dialogPause.addAction(Actions.sequence(Actions.moveTo(this.dialogPause.getX(), (this.getHeight() - this.dialogPause.getHeight()) / 2.0f, 0.1f, Interpolation.bounceIn), Actions.run(new Runnable() {
                @Override
                public void run() {
                    Global.gameState = Constant.GameState.Game_Pause;
                }
            })));
            PlatformHandle.showFeatureView();
        }
        else if (Global.gameState == Constant.GameState.Game_Pause && b) {
            this.dialogPause.continueHandle();
        }
    }
    
    public void revive() {
        Global.gameState = Constant.GameState.Game_On;
        this.player.revive();
//        if (Global.isEndlessMode) {
//            FlurryHandle.reviveEndless(this.getReviveMondNum());
//        }
//        else {
//            if (Global.gameMode == Constant.GameMode.Easy) {
//                FlurryHandle.reviveEasy(Global.curGameLevelEasy);
//                return;
//            }
//            if (Global.gameMode == Constant.GameMode.Hard) {
//                FlurryHandle.reviveHard(Global.curGameLevelHard);
//            }
//        }
    }
    
    public void setBlink(final float n) {
        if (this.totalBlinkCount > 0) {
            if (this.pauseTime > 0.0f) {
                this.pauseTime -= n;
                if (this.pauseTime <= 0.0f) {
                    this.singleBlinkCount = 8;
                }
            }
            else {
                this.blinkGapTime -= n;
                if (this.blinkGapTime > 0.0f && this.blinkGapTime <= this.oriGapTime / 2.0f) {
                    Global.batch.setColor(Color.GRAY);
                    return;
                }
                if (this.blinkGapTime <= 0.0f) {
                    Global.batch.setColor(Color.WHITE);
                    this.blinkGapTime = this.oriGapTime;
                    --this.singleBlinkCount;
                    if (this.singleBlinkCount <= 0) {
                        --this.totalBlinkCount;
                        this.pauseTime = this.oriPauseTime;
                    }
                }
            }
        }
    }
    
    public void setCombo() {
        this.comboTime = 0.8f;
    }
    
    public void shake(final int n) {
        switch (n) {
            default: {}
            case 1: {
                this.actorShake.addAction(ShakeAction.shake1());
            }
            case 2: {
                this.actorShake.addAction(ShakeAction.shake2());
            }
            case 3: {
                this.actorShake.addAction(ShakeAction.shake3());
            }
        }
    }
    
    public void showDialogOver() {
        Global.gameState = Constant.GameState.Level_Win;
        this.dialogOver.addAction(Actions.sequence(Actions.delay(0.2f), Actions.moveTo(this.dialogOver.getX(), (this.getHeight() - this.dialogOver.getHeight()) / 2.0f + 20.0f, 0.15f, Interpolation.bounceIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                GameStage.this.addActor(GameStage.this.dialogOver.getImgShade());
                GameStage.this.dialogOver.toFront();
                ++Global.gamePlayNum;
                if (Global.gamePlayNum >= 3) {
                    PlatformHandle.showAdBig();
                    Global.gamePlayNum = 0;
                }
                PlatformHandle.showFeatureView();
            }
        })));
        this.addActor(this.dialogOver);
        MusicHandle.playForWin();
    }
    
    public void startBlink(final BaseBoss baseBoss) {
        this.isBlink = true;
        baseBoss.showWarning();
        SoundHandle.playForWarning();
        this.getRoot().addAction(Actions.sequence(Actions.delay(5.0f), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseBoss.stopWarning();
            }
        })));
    }
    
    @Override
    public boolean touchDown(final int n, final int n2, final int attackPointer, final int n3) {
        this.point.set(n, n2, 0.0f);
        this.getCamera().unproject(this.point);
        if (this.isTouchValid(this.point.x, this.point.y)) {
            this.player.getCurMainGun().setAngle(this.point.x, this.point.y);
            MyMethods.setCenter(this.imgAim, this.point.x, this.point.y);
            this.imgAim.setVisible(Global.gameState == Constant.GameState.Game_On);
            if (this.insturcStep == 11 || this.insturcStep == 22) {
                this.doInstruction();
            }
            this.attackPointer = attackPointer;
            this.isTouched = true;
            this.attackPos.set(this.point.x, this.point.y);
        }
        return super.touchDown(n, n2, attackPointer, n3);
    }
    
    @Override
    public boolean touchDragged(final int n, final int n2, final int n3) {
        this.point.set(n, n2, 0.0f);
        this.getCamera().unproject(this.point);
        if (this.isTouchValid(this.point.x, this.point.y) && n3 == this.attackPointer) {
            this.player.getCurMainGun().setAngle(this.point.x, this.point.y);
            MyMethods.setCenter(this.imgAim, this.point.x, this.point.y);
            this.imgAim.setVisible(Global.gameState == Constant.GameState.Game_On);
            this.attackPos.set(this.point.x, this.point.y);
            if (this.insturcStep == 22) {
                this.doInstruction();
            }
        }
        return super.touchDragged(n, n2, n3);
    }
    
    @Override
    public boolean touchUp(final int n, final int n2, final int n3, final int n4) {
        this.point.set(n, n2, 0.0f);
        this.getCamera().unproject(this.point);
        if (n3 == this.attackPointer) {
            if (Global.gameState == Constant.GameState.Game_On) {
                this.endTouchTime = this.curTime;
                if (this.player.getCurMainGun() instanceof Laser && this.endTouchTime - this.startAttackTime < 0.8f) {
                    ((Laser)this.player.getCurMainGun()).cancelBullet();
                }
                if (this.player.getCurMainGun() instanceof Electricity && this.endTouchTime - this.startAttackTime < 0.6f) {
                    ((Electricity)this.player.getCurMainGun()).cancelBullet();
                }
            }
            this.isTouched = false;
        }
        this.imgAim.setVisible(false);
        return super.touchUp(n, n2, n3, n4);
    }
}
