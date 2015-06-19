package com.fxb.razor.screens;

import com.fxb.razor.stages.dialogs.*;
import com.badlogic.gdx.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.utils.action.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class LevelSmallScreen extends BaseScreen
{
    TextureAtlas atlasLevelBg;
    TextureAtlas atlasLevelSelect;
    TextureAtlas atlasScene;
    TextureAtlas atlasWeaponSelect;
    BackScene backScene;
    ButtonListener btnListener;
    DialogEnemyKill dialogEnemyKill;
    DialogHero dialogHero;
    DialogRate dialogRate;
    DialogSet dialogSet;
    DialogSpeSold dialogSpeSold;
    DialogStore dialogStore;
    GoldGet goldGet;
    GoldGetDialog goldGetDialog;
    CoinMond.GroupCoinHead groupCoinHead;
    MyGroup[] groupFlags;
    CoinMond.GroupMondHead groupMondHead;
    HelpBoard helpBoard;
    MyImage imgBack;
    MyImage imgEnemyKill;
    MyImage imgEnhance;
    MyImage imgHero;
    MyImage imgSet;
    MyImage imgStore;
    MyImage imgWorld;
    MondGet mondGet;
    MondGetDialog mondGetDialog;
    MondLackDialog mondLackDialog;
    float[] poss;
    InputListener selectListener;
    Stage stage;
    
    public LevelSmallScreen(final MainGame mainGame) {
        super(mainGame);
        this.poss = new float[] { 102.0f, 219.0f, 217.0f, 219.0f, 333.0f, 219.0f, 448.0f, 219.0f, 563.0f, 219.0f, 102.0f, 111.0f, 217.0f, 111.0f, 333.0f, 111.0f, 448.0f, 111.0f, 566.0f, 125.0f };
        this.goldGetDialog = new GoldGetDialog(this);
        this.mondGetDialog = new MondGetDialog(this);
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int i, final int n3) {
                super.touchUp(inputEvent, n, n2, i, n3);
                if (!this.isDown) {
                    return;
                }
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == LevelSmallScreen.this.imgBack) {
                    LevelSmallScreen.this.keyBack();
                }
                else if (listenerActor == LevelSmallScreen.this.imgHero) {
                    DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogHero);
                }
                else if (listenerActor == LevelSmallScreen.this.imgEnemyKill) {
                    DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogEnemyKill);
                }
                else if (listenerActor == LevelSmallScreen.this.imgSet) {
                    DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogSet);
                }
                else if (listenerActor == LevelSmallScreen.this.imgStore) {
                    if (LevelSmallScreen.this.helpBoard != null) {
                        LevelSmallScreen.this.helpBoard.remove();
                    }
                    LevelSmallScreen.this.dialogStore.openDialog(LevelSmallScreen.this.stage);
                }
                else if (listenerActor == LevelSmallScreen.this.imgEnhance) {
                    Global.preScreen = Constant.NextScreen.Level_Small;
                    Global.nextScreen = Constant.NextScreen.Weapon_Enhance;
                    BaseScreen.addFadeOutAction(LevelSmallScreen.this.stage, 0.3f);
                }
                else if (listenerActor == LevelSmallScreen.this.groupCoinHead) {
                    LevelSmallScreen.this.groupCoinHead.touchHandle(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogStore);
                }
                else if (listenerActor == LevelSmallScreen.this.groupMondHead) {
                    LevelSmallScreen.this.groupMondHead.touchHandle(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogStore);
                }
                if (LevelSmallScreen.this.goldGet != null) {
                    for (i = 0; i < LevelSmallScreen.this.goldGet.groupGolds.length; ++i) {
                        if (listenerActor == LevelSmallScreen.this.goldGet.groupGolds[i]) {
                            LevelSmallScreen.this.goldGetDialog.setGoldLevel(i + 1);
                            DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.goldGetDialog);
                            LevelSmallScreen.this.goldGet.setStarNum(LevelSmallScreen.this.goldGet.starNum);
                        }
                    }
                }
                if (LevelSmallScreen.this.mondGet != null) {
                    for (i = 0; i < LevelSmallScreen.this.mondGet.groupMonds.length; ++i) {
                        if (listenerActor == LevelSmallScreen.this.mondGet.groupMonds[i]) {
                            LevelSmallScreen.this.mondGetDialog.setmondLevel(i + 1);
                            DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.mondGetDialog);
                            LevelSmallScreen.this.mondGet.setStarNum(LevelSmallScreen.this.mondGet.starNum);
                        }
                    }
                }
                SoundHandle.playForButton2();
            }
        };
        this.selectListener = new InputListener() {
            boolean isDown = false;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(Actions.scaleTo(1.05f, 1.05f, 0.05f));
                return this.isDown = true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor.hit(n, n2, true) == null) {
                    this.isDown = false;
                    listenerActor.addAction(Actions.scaleTo(1.0f, 1.0f, 0.05f));
                }
                super.touchDragged(inputEvent, n, n2, n3);
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int i, final int n3) {
                if (!this.isDown) {
                    return;
                }
                inputEvent.getListenerActor().addAction(Actions.scaleTo(1.0f, 1.0f, 0.05f));
                this.isDown = false;
                for (i = 0; i < LevelSmallScreen.this.groupFlags.length; ++i) {
                    if (inputEvent.getListenerActor() == LevelSmallScreen.this.groupFlags[i]) {
                        if (Global.gameMode == Constant.GameMode.Easy) {
                            Global.curGameLevelEasy = (Global.sceneLevel - 1) * 10 + i + 1;
                        }
                        else {
                            Global.curGameLevelHard = (Global.sceneLevel - 1) * 10 + i + 1;
                        }
                        Global.preScreen = Constant.NextScreen.Level_Small;
                        Global.nextScreen = Constant.NextScreen.Weapon_Screen;
                        BaseScreen.addFadeOutAction(LevelSmallScreen.this.stage, 0.3f);
                        break;
                    }
                }
                SoundHandle.playForButton2();
            }
        };
        this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
        this.atlasLevelSelect = Global.manager.get("ui/ui_level_select.pack", TextureAtlas.class);
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        this.atlasWeaponSelect = Global.manager.get("ui/ui_weapon_select.pack", TextureAtlas.class);
        this.stage = new Stage(800.0f, 480.0f, false, Global.batch);
        this.init();
        this.setSceneLevel();
        BaseScreen.addFadeInAction(this.stage, 0.3f);
        this.multiplexer.addProcessor(0, this.stage);
        MusicHandle.playForMenu();
    }
    
    private void init() {
        this.addItem(this.atlasLevelSelect, "waikuangzuo", 0.0f, 0.0f);
        this.addItem(this.atlasLevelSelect, "waikuangyou", 755.0f, 0.0f);
        this.addItem(this.atlasLevelSelect, "waikuangshang", 44.0f, 463.0f);
        this.addItem(this.atlasLevelSelect, "waikuangxia", 44.0f, 0.0f);
        if (Global.gameMode == Constant.GameMode.Easy) {
            this.addItem(Assets.atlasStart, "waikuang", 65.0f, 98.0f);
            this.addItem("hengfu1", 231.0f, 330.0f);
            this.goldGet = new GoldGet(this.stage);
            int starNum = 0;
            for (int i = 0; i < 10; ++i) {
                starNum += PreferHandle.readLevelStar((Global.sceneLevel - 1) * 10 + (i + 1), Constant.GameMode.Easy);
            }
            this.goldGet.setStarNum(starNum);
        }
        else {
            this.addItem(this.atlasWeaponSelect, "waikuang2", 50.0f, 95.0f);
            this.addItem("hengfu2", 231.0f, 330.0f);
            this.mondGet = new MondGet(this.stage);
            int starNum2 = 0;
            for (int j = 0; j < 10; ++j) {
                starNum2 += PreferHandle.readLevelStar((Global.sceneLevel - 1) * 10 + (j + 1), Constant.GameMode.Hard);
            }
            this.mondGet.setStarNum(starNum2);
        }
        this.addItem("chapter", 321.0f, 357.0f);
        this.imgWorld = this.addItem("number1", 442.0f, 365.0f);
        this.imgStore = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shangdian", 643.0f, 401.0f, this.btnListener);
        this.imgHero = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "renwu", 103.0f, 401.0f, this.btnListener);
        this.imgEnemyKill = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "chengjiu", 26.0f, 401.0f, this.btnListener);
        this.imgSet = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shezhi", 718.0f, 401.0f, this.btnListener);
        this.imgBack = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "fanhui", 32.0f, 17.0f, this.btnListener);
        this.imgEnhance = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shengji", 692.0f, 20.0f, this.btnListener);
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this.stage, this.btnListener);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this.stage, this.btnListener);
        this.dialogHero = new DialogHero();
        this.dialogEnemyKill = new DialogEnemyKill();
        this.dialogSet = new DialogSet();
        this.dialogStore = new DialogStore(this, this.groupCoinHead, this.groupMondHead);
        this.groupFlags = new MyGroup[10];
        this.setFlags();
        if (Global.isAutoRate) {
            this.dialogRate = new DialogRate();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogRate);
                    Global.isAutoRate = false;
                }
            };
            ++Global.autoRateCount;
            MyMethods.delayRun(this.stage.getRoot(), runnable, 0.3f);
        }
        if (Global.isAutoSpesold && Global.isSpesoldValid()) {
            this.mondLackDialog = new MondLackDialog(this);
            this.dialogSpeSold = new DialogSpeSold(this, this.mondLackDialog);
            MyMethods.delayRun(this.stage.getRoot(), new Runnable() {
                @Override
                public void run() {
                    DialogHandle.openDialog(LevelSmallScreen.this.stage, LevelSmallScreen.this.dialogSpeSold);
                    Global.isAutoSpesold = false;
                }
            }, 0.3f);
        }
        if (Global.isAutoStoreEnhance) {
            final SequenceAction sequence = Actions.sequence(Actions.moveBy(0.0f, 10.0f, 0.22f), Actions.moveBy(0.0f, -10.0f, 0.22f));
            if (Global.loseCount % 2 == 1) {
                UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "jiantou", this.imgEnhance.getX() + 20.0f, this.imgEnhance.getTop()).addAction(Actions.forever(sequence));
            }
            else {
                final MyImage addItem = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "jiantou", this.imgStore.getX() + 12.0f, this.imgStore.getY() - 45.0f);
                addItem.flip(false, true);
                addItem.addAction(Actions.forever(sequence));
            }
            Global.isAutoStoreEnhance = false;
        }
        this.addInstruction();
    }
    
    private void setFlags() {
        int n;
        if (Global.gameMode == Constant.GameMode.Easy) {
            n = Global.maxGameLevelEasy;
        }
        else {
            n = Global.maxGameLevelHard;
        }
        final int n2 = ((n - 1) / 10 + 1) * 10 - 1;
        for (int i = 0; i < this.groupFlags.length; ++i) {
            if (i == 0) {
                final GroupAttack groupAttack = new GroupAttack(i + 1);
                groupAttack.addListener(this.selectListener);
                this.groupFlags[i] = groupAttack;
            }
            else {
                this.groupFlags[i] = new GroupLock(i + 1);
            }
        }
        for (int j = 0; j < this.groupFlags.length; ++j) {
            final int n3 = (Global.sceneLevel - 1) * 10 + j;
            if (j == 9) {
                if (n3 <= n - 1) {
                    final GroupBossConquer groupBossConquer = new GroupBossConquer();
                    groupBossConquer.setStarNum(PreferHandle.readLevelStar(n3 + 1, Global.gameMode));
                    groupBossConquer.addListener(this.selectListener);
                    this.groupFlags[j] = groupBossConquer;
                }
                else {
                    this.groupFlags[j] = new GroupBossLock();
                }
            }
            else if (n3 < n - 1) {
                final GroupConquer groupConquer = new GroupConquer(j + 1);
                groupConquer.setStarNum(PreferHandle.readLevelStar(n3 + 1, Global.gameMode));
                groupConquer.addListener(this.selectListener);
                this.groupFlags[j] = groupConquer;
            }
            else if (n3 == n - 1 && n3 != n2) {
                final GroupAttack groupAttack2 = new GroupAttack(j + 1);
                groupAttack2.addListener(this.selectListener);
                this.groupFlags[j] = groupAttack2;
            }
            else if (n3 < n2) {
                this.groupFlags[j] = new GroupLock(j + 1);
            }
            if (this.groupFlags[j] != null) {
                this.groupFlags[j].setPosition(this.poss[j * 2] + 14.0f, this.poss[j * 2 + 1]);
                this.stage.addActor(this.groupFlags[j]);
            }
        }
    }
    
    private void startShakeItem(final Group group) {
        group.clearActions();
        group.setTransform(true);
        group.addAction(BlinkAction.scaleUpDown(0.98f, 1.1f, 0.35f));
    }
    
    private void stopShakeItem(final Group group) {
        group.clearActions();
        group.setTransform(false);
        group.setScale(1.0f);
    }
    
    public void addInstruction() {
        if (Global.maxGameLevelEasy == 1) {
            HelpBoard.createHelpBoard(this.stage, this.groupFlags[0], "Tap to select level 1!", 1, -1);
        }
        else {
            if (Global.maxGameLevelEasy == 2) {
                HelpBoard.createHelpBoard(this.stage, this.groupFlags[1], "Next level!", 1, -1);
                return;
            }
            if (Global.maxGameLevelEasy == 4 && !PreferHandle.readInstruction("instructionScatter")) {
                if (!Global.arrMainGunGet.contains("Scatter", false)) {
                    this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.imgStore, "Tap store!", -1, -1);
                    return;
                }
                this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupFlags[3], "Tap to continue!", -1, -1);
            }
            else if (Global.maxGameLevelEasy == 8 && !PreferHandle.readInstruction("instructionEnhance") && PreferHandle.readWeaponEnhance("SinglePipe") == 0) {
                HelpBoard.createHelpBoard(this.stage, this.imgEnhance, "Tap to open weapon storage!", -1, 1);
            }
        }
    }
    
    public MyImage addItem(final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        this.stage.addActor(myImage);
        return myImage;
    }
    
    @Override
    public MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        group.addActor(myImage);
        return myImage;
    }
    
    public MyImage addItem(final String s, final float n, final float n2) {
        return this.addItem(this.atlasLevelBg, s, n, n2);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.stage.clear();
        this.stage.dispose();
    }
    
    public void getCoin(final float n, final int n2) {
        Global.totalCoinNum = (int)Global.totalCoinNum + 0.2f;
        Coin.addCoin(this.stage.getRoot(), this.goldGet.groupGolds[n2].getX() + 15.0f, this.goldGet.groupGolds[n2].getY() + 15.0f, MathUtils.random(80, 120), MathUtils.random(15, 20), n, this);
    }
    
    public void getMond(final float n, final int n2) {
        Global.totalMondNum = (int)Global.totalMondNum + 0.2f;
        Coin.addMond(this.stage.getRoot(), this.mondGet.groupMonds[n2].getX() + 15.0f, this.mondGet.groupMonds[n2].getY() + 15.0f, MathUtils.random(80, 120), MathUtils.random(15, 20), n, this);
    }
    
    @Override
    protected void keyBack() {
        super.keyBack();
        if (PlatformHandle.isShowingAd()) {
            PlatformHandle.closeAd();
            return;
        }
        if (this.dialogHero.getParent() != null) {
            DialogHandle.closeDialog(this.dialogHero);
            return;
        }
        if (this.dialogStore.getParent() != null) {
            this.dialogStore.keyBack();
            return;
        }
        if (this.dialogEnemyKill.getParent() != null) {
            DialogHandle.closeDialog(this.dialogEnemyKill);
            return;
        }
        if (this.dialogSet.getParent() != null) {
            this.dialogSet.keyBack();
            return;
        }
        if (this.dialogRate != null && this.dialogRate.getParent() != null) {
            this.dialogRate.closeHandle();
            return;
        }
        if (this.dialogSpeSold != null && this.dialogSpeSold.getParent() != null) {
            this.dialogSpeSold.closeHandle();
            return;
        }
        Global.preScreen = Constant.NextScreen.Level_Small;
        Global.nextScreen = Constant.NextScreen.Level_Screen;
        BaseScreen.addFadeOutAction(this.stage, 0.3f);
    }
    
    public void refresh() {
        this.groupCoinHead.setCoinNum((int)Global.totalCoinNum);
        this.groupMondHead.setMondNum((int)Global.totalMondNum);
        if (this.goldGet != null) {
            this.goldGet.setStarNum(this.goldGet.starNum);
        }
        if (this.mondGet != null) {
            this.mondGet.setStarNum(this.mondGet.starNum);
        }
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        this.backScene.act(n);
        this.backScene.draw();
        this.stage.act();
        this.stage.draw();
        this.showFps(n);
    }
    
    public void setSceneLevel() {
        final int sceneLevel = Global.sceneLevel;
        this.backScene = new BackScene();
        this.imgWorld.setRegion(this.atlasLevelBg.findRegion(StrHandle.get("number", sceneLevel)));
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.showFeatureView();
    }
    
    class GoldGet extends Group
    {
        GroupGold[] groupGolds;
        MyImage imgProgress;
        int starNum;
        LevelSmallScreen levelSmallScreen;
        
        public GoldGet(final Stage stage) {
            UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindudi", 209.0f, 40.0f);
            UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindukuang", 191.0f, 87.0f);
            this.imgProgress = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindutiao", 225.0f, 101.0f);
            final float[] array2;
            final float[] array = array2 = new float[6];
            array2[0] = 300.0f;
            array2[1] = 85.0f;
            array2[2] = 413.0f;
            array2[3] = 85.0f;
            array2[4] = 536.0f;
            array2[5] = 85.0f;
            this.groupGolds = new GroupGold[3];
            for (int i = 0; i < this.groupGolds.length; ++i) {
                this.groupGolds[i] = new GroupGold(this, i + 1, array[i * 2], array[i * 2 + 1]);
            }
            this.setTransform(false);
            LevelSmallScreen.this.stage.addActor(this);
        }
        
        public void setStarNum(int i) {
            this.starNum = i;
            for (i = 0; i < this.groupGolds.length; ++i) {
                this.groupGolds[i].setStarNum(this.starNum);
            }
            this.imgProgress.setWidth(334.0f * this.starNum / 30.0f);
        }
        
        private class GroupGold extends Group
        {
            MyImage imgGold;
            MyImage imgStar;
            int level;
            
            public GroupGold(final Group group, final int level, final float n, final float n2) {
                this.level = level;
                this.imgGold = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, StrHandle.get("jinbi", this.level, "_b"), 0.0f, 0.0f);
                this.imgStar = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, StrHandle.get("xx", this.level, "_b"), -1.0f, 2.0f);
                this.setSize(43.0f, 43.0f);
                MyMethods.setActorOrigin(this, 0.5f, 0.5f);
                this.setPosition(n, n2);
                this.addListener(LevelSmallScreen.this.btnListener);
                this.setTouchable(Touchable.disabled);
                this.setTransform(false);
                group.addActor(this);
            }
            
            public void setStarNum(final int n) {
                if (n >= this.level * 10) {
                    this.imgStar.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("xx", this.level)));
                    if (!PreferHandle.readLevelGoldGet(Global.sceneLevel, this.level)) {
                        this.imgGold.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("jinbi", this.level)));
                        this.setTouchable(Touchable.enabled);
                        LevelSmallScreen.this.startShakeItem(this);
                        return;
                    }
                    this.imgGold.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("kong", this.level)));
                    this.setTouchable(Touchable.disabled);
                    LevelSmallScreen.this.stopShakeItem(this);
                }
            }
        }
    }
    
    private class GroupAttack extends MyGroup
    {
        MyImage imgFlag;
        MyImage imgNumber;
        MyImage imgStarBg;
        
        public GroupAttack(final int n) {
            this.imgFlag = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jingong", 0.0f, 0.0f);
            this.imgStarBg = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xx", 16.0f, 25.0f);
            this.imgNumber = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, StrHandle.get("1-", n), 44.0f, 64.0f);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
        
        public void setLevel(final int n) {
            this.imgNumber.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("1-", n)));
        }
    }
    
    private class GroupBossConquer extends MyGroup
    {
        MyImage imgBoss;
        MyImage imgStarBg;
        MyImage[] imgStars;
        
        public GroupBossConquer() {
            this.imgBoss = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "boss_unlock", 0.0f, 0.0f);
            this.imgStarBg = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xx", 15.0f, 9.0f);
            (this.imgStars = new MyImage[3])[0] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 18.0f, 18.0f);
            this.imgStars[1] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 42.5f, 12.0f);
            this.imgStars[2] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 66.0f, 18.0f);
            this.setSize(this.imgBoss.getWidth(), this.imgBoss.getHeight());
        }
        
        public void setStarNum(final int n) {
            for (int i = 0; i < this.imgStars.length; ++i) {
                this.imgStars[i].setVisible(i < n);
            }
        }
    }
    
    private class GroupBossLock extends MyGroup
    {
        MyImage imgBoss;
        
        public GroupBossLock() {
            this.imgBoss = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "boss_lock", 0.0f, 0.0f);
            this.setSize(this.imgBoss.getWidth(), this.imgBoss.getHeight());
        }
    }
    
    private class GroupConquer extends MyGroup
    {
        MyImage imgFlag;
        MyImage imgNumber;
        MyImage imgStarBg;
        MyImage[] imgStars;
        
        public GroupConquer(final int n) {
            this.imgFlag = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jingong", 0.0f, 0.0f);
            this.imgStarBg = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xx", 16.0f, 25.0f);
            (this.imgStars = new MyImage[3])[0] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 19.0f, 34.0f);
            this.imgStars[1] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 43.5f, 28.0f);
            this.imgStars[2] = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "xingxing", 68.0f, 34.0f);
            this.imgNumber = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, StrHandle.get("1-", n), 44.0f, 64.0f);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
        
        public void setGameLevel(final int n) {
            this.imgNumber.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion("1-" + n));
        }
        
        public void setStarNum(final int n) {
            for (int i = 0; i < this.imgStars.length; ++i) {
                this.imgStars[i].setVisible(i < n);
            }
        }
    }
    
    private class GroupLock extends MyGroup
    {
        MyImage imgFlag;
        MyImage imgNumber;
        
        public GroupLock(final int n) {
            this.imgFlag = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "locked", 0.0f, 0.0f);
            this.imgNumber = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, StrHandle.get(n), 44.0f, 64.0f);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
        
        public void setLevel(final int n) {
            this.imgNumber.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get(n)));
        }
    }
    
    class MondGet extends Group
    {
        GroupMond[] groupMonds;
        MyImage imgProgress;
        int starNum;
        LevelSmallScreen levelSmallScreen;
        
        public MondGet(final Stage stage) {
            UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindudi", 209.0f, 40.0f);
            UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindukuang", 191.0f, 87.0f);
            this.imgProgress = UiHandle.addItem(this, LevelSmallScreen.this.atlasLevelBg, "jindutiao", 225.0f, 101.0f);
            final float[] array2;
            final float[] array = array2 = new float[6];
            array2[0] = 291.0f;
            array2[1] = 85.0f;
            array2[2] = 404.0f;
            array2[3] = 85.0f;
            array2[4] = 527.0f;
            array2[5] = 85.0f;
            this.groupMonds = new GroupMond[3];
            for (int i = 0; i < this.groupMonds.length; ++i) {
                this.groupMonds[i] = new GroupMond(this, i + 1, array[i * 2], array[i * 2 + 1]);
            }
            this.setTransform(false);
            LevelSmallScreen.this.stage.addActor(this);
        }
        
        public void setStarNum(int i) {
            this.starNum = i;
            for (i = 0; i < this.groupMonds.length; ++i) {
                this.groupMonds[i].setStarNum(this.starNum);
            }
            this.imgProgress.setWidth(334.0f * this.starNum / 30.0f);
        }
        
        private class GroupMond extends Group
        {
            MyImage imgMond;
            MyImage imgStar;
            int level;
            
            public GroupMond(final Group group, final int level, final float n, final float n2) {
                this.level = level;
                this.imgMond = UiHandle.addItem(this, MondGet.this.levelSmallScreen.atlasLevelBg, StrHandle.get("baoshi", this.level, "_b"), 0.0f, 0.0f);
                this.imgStar = UiHandle.addItem(this, MondGet.this.levelSmallScreen.atlasLevelBg, StrHandle.get("xx", this.level, "_b"), -1.0f, 2.0f);
                this.setSize(43.0f, 43.0f);
                this.setPosition(n, n2);
                this.addListener(MondGet.this.levelSmallScreen.btnListener);
                this.setTouchable(Touchable.disabled);
                MyMethods.setActorOrigin(this, 0.5f, 0.5f);
                this.setTransform(false);
                group.addActor(this);
            }
            
            public void setStarNum(final int n) {
                if (n >= this.level * 10) {
                    this.imgStar.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("xx", this.level)));
                    if (!PreferHandle.readLevelMondGet(Global.sceneLevel, this.level)) {
                        this.imgMond.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("baoshi", this.level)));
                        this.setTouchable(Touchable.enabled);
                        LevelSmallScreen.this.startShakeItem(this);
                        return;
                    }
                    this.imgMond.setRegion(LevelSmallScreen.this.atlasLevelBg.findRegion(StrHandle.get("kong", this.level)));
                    this.setTouchable(Touchable.disabled);
                    LevelSmallScreen.this.stopShakeItem(this);
                }
            }
        }
    }
}
