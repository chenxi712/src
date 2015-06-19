package com.fxb.razor.screens;

import com.fxb.razor.stages.dialogs.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;

public class LevelScreen extends BaseScreen
{
    Actor actorAlpha;
    ArrowIndi arrowIndi;
    TextureAtlas atlasLevelBg;
    TextureAtlas atlasLevelSelect;
    ButtonListener btnListener;
    DialogEnemyKill dialogEnemyKill;
    DialogHero dialogHero;
    DialogSet dialogSet;
    DialogStore dialogStore;
    CoinMond.GroupCoinHead groupCoinHead;
    MyGroup[] groupFlags;
    CoinMond.GroupMondHead groupMondHead;
    HelpBoard helpBoard;
    MyImage imgBack;
    MyImage imgEnemyKill;
    MyImage imgEnhance;
    MyImage imgHero;
    MyImage[] imgLevels;
    MyImage imgSet;
    MyImage imgStore;
    int insStep;
    ItemMode itemMode;
    Vector2[] poss;
    int selectLevel;
    InputListener selectListener;
    Stage stage;
    
    public LevelScreen(final MainGame mainGame) {
        super(mainGame);
        this.poss = new Vector2[] { new Vector2(153.0f, 148.0f), new Vector2(343.0f, 102.0f), new Vector2(512.0f, 148.0f), new Vector2(614.0f, 229.0f), new Vector2(338.0f, 279.0f) };
        this.insStep = 0;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (!this.isDown) {
                    return;
                }
                final Actor listenerActor = inputEvent.getListenerActor();
                if (listenerActor == LevelScreen.this.imgBack) {
                    LevelScreen.this.keyBack();
                }
                else if (listenerActor == LevelScreen.this.imgHero) {
                    DialogHandle.openDialog(LevelScreen.this.stage, LevelScreen.this.dialogHero);
                }
                else if (listenerActor == LevelScreen.this.imgSet) {
                    DialogHandle.openDialog(LevelScreen.this.stage, LevelScreen.this.dialogSet, 0.35f);
                }
                else if (listenerActor == LevelScreen.this.imgEnemyKill) {
                    DialogHandle.openDialog(LevelScreen.this.stage, LevelScreen.this.dialogEnemyKill);
                }
                else if (listenerActor == LevelScreen.this.imgStore) {
                    LevelScreen.this.dialogStore.openDialog(LevelScreen.this.stage);
                }
                else if (listenerActor == LevelScreen.this.groupCoinHead) {
                    LevelScreen.this.groupCoinHead.touchHandle(LevelScreen.this.stage, LevelScreen.this.dialogStore);
                }
                else if (listenerActor == LevelScreen.this.groupMondHead) {
                    LevelScreen.this.groupMondHead.touchHandle(LevelScreen.this.stage, LevelScreen.this.dialogStore);
                }
                else if (listenerActor == LevelScreen.this.imgEnhance) {
                    Global.preScreen = Constant.NextScreen.Level_Screen;
                    Global.nextScreen = Constant.NextScreen.Weapon_Enhance;
                    BaseScreen.addFadeOutAction(LevelScreen.this.stage, 0.3f);
                }
                SoundHandle.playForButton2();
            }
        };
        this.selectLevel = 0;
        this.selectListener = new InputListener() {
            boolean isDown = false;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(Actions.scaleTo(1.1f, 1.1f, 0.05f));
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
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.addAction(Actions.scaleTo(1.0f, 1.0f, 0.05f));
                this.isDown = false;
                if (listenerActor == LevelScreen.this.itemMode.getImgEasy()) {
                    Global.gameMode = Constant.GameMode.Easy;
                    LevelScreen.this.enterSmall();
                }
                else if (listenerActor == LevelScreen.this.itemMode.getImgHard()) {
                    Global.gameMode = Constant.GameMode.Hard;
                    LevelScreen.this.enterSmall();
                }
                i = 0;
                while (i < LevelScreen.this.groupFlags.length) {
                    if (listenerActor == LevelScreen.this.groupFlags[i] && !(listenerActor instanceof GroupLock)) {
                        if (!LevelScreen.this.arrowIndi.isShowGroup) {
                            LevelScreen.this.arrowIndi.setMainIndicator(LevelScreen.this.arrowIndi.curGroup);
                        }
                        if (LevelScreen.this.itemMode.curFlag == LevelScreen.this.groupFlags[i]) {
                            LevelScreen.this.itemMode.hide();
                            LevelScreen.this.itemMode.curFlag = null;
                            break;
                        }
                        Global.sceneLevel = i + 1;
                        LevelScreen.this.itemMode.show(LevelScreen.this.groupFlags[i]);
                        if (LevelScreen.this.groupFlags[i] instanceof GroupConquer && Global.maxGameLevelHard >= i * 10) {
                            LevelScreen.this.itemMode.setHardValid(true);
                        }
                        else {
                            LevelScreen.this.itemMode.setHardValid(false);
                        }
                        if (LevelScreen.this.groupFlags[i] == LevelScreen.this.arrowIndi.curGroup) {
                            if (LevelScreen.this.arrowIndi.curGroup instanceof GroupAttack) {
                                LevelScreen.this.arrowIndi.setSubIndicator(LevelScreen.this.itemMode.getImgEasy());
                            }
                            else {
                                LevelScreen.this.arrowIndi.setSubIndicator(LevelScreen.this.itemMode.getImgHard());
                            }
                        }
                        if (LevelScreen.this.insStep == 11) {
                            LevelScreen.this.helpBoard.remove();
                            MyMethods.delayRun(LevelScreen.this.stage.getRoot(), new Runnable() {
                                @Override
                                public void run() {
                                    HelpBoard.createHelpBoard(LevelScreen.this.stage, LevelScreen.this.itemMode.getImgEasy(), "Tap to choose easy mode!", 1, 1);
                                }
                            }, 0.4f);
                            break;
                        }
                        break;
                    }
                    else {
                        ++i;
                    }
                }
                SoundHandle.playForButton2();
            }
        };
        this.stage = new Stage(800.0f, 480.0f, false, Global.batch);
        this.atlasLevelSelect = Global.manager.get("ui/ui_level_select.pack", TextureAtlas.class);
        this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
        this.init();
        BaseScreen.addFadeInAction(this.stage, 0.4f);
        this.multiplexer.addProcessor(0, this.stage);
        MusicHandle.playForMenu();
    }
    
    private void enterSmall() {
        Global.preScreen = Constant.NextScreen.Level_Screen;
        Global.nextScreen = Constant.NextScreen.Level_Small;
        BaseScreen.addFadeOutAction(this.stage, 0.3f);
    }
    
    private int getStarNum(final int n) {
        int n2 = 0;
        for (int i = n * 10 - 9; i <= n * 10; ++i) {
            n2 = n2 + PreferHandle.readLevelStar(i, Constant.GameMode.Easy) + PreferHandle.readLevelStar(i, Constant.GameMode.Hard);
        }
        return n2;
    }
    
    private void init() {
        this.addItem(this.atlasLevelBg, "beijing", 0.0f, 0.0f);
        this.addItem("waikuangzuo", 0.0f, 0.0f);
        this.addItem("waikuangyou", 755.0f, 0.0f);
        this.addItem("waikuangshang", 44.0f, 463.0f);
        this.addItem("waikuangxia", 44.0f, 0.0f);
        (this.imgLevels = new MyImage[5])[4] = this.addItem("daguan5.2", 231.0f, 166.0f);
        this.imgLevels[0] = this.addItem("daguan1", 12.0f, 46.0f);
        this.imgLevels[1] = this.addItem("daguan2.2", 276.0f, 17.0f);
        this.imgLevels[2] = this.addItem("daguan3.2", 445.0f, 86.0f);
        this.imgLevels[3] = this.addItem("daguan4.2", 505.0f, 93.0f);
        this.setLevelEnable();
        this.arrowIndi = new ArrowIndi();
        this.groupFlags = new MyGroup[5];
        this.setFlags();
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this.stage, this.btnListener);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this.stage, this.btnListener);
        this.imgStore = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shangdian", 643.0f, 401.0f, this.btnListener);
        this.imgHero = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "renwu", 103.0f, 401.0f, this.btnListener);
        this.imgEnemyKill = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "chengjiu", 26.0f, 401.0f, this.btnListener);
        this.imgSet = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shezhi", 718.0f, 401.0f, this.btnListener);
        this.imgBack = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "fanhui", 32.0f, 17.0f, this.btnListener);
        this.imgEnhance = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shengji", 692.0f, 20.0f, this.btnListener);
        this.dialogHero = new DialogHero();
        this.dialogEnemyKill = new DialogEnemyKill();
        this.dialogSet = new DialogSet();
        this.itemMode = new ItemMode();
        if (Global.maxGameLevelEasy == 1) {
            this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupFlags[0], "Tap to continue!", 1, 1);
            this.insStep = 11;
        }
        this.dialogStore = new DialogStore(this, this.groupCoinHead, this.groupMondHead);
    }
    
    public Group addGroup(final float n, final float n2) {
        final Group group = new Group();
        group.setPosition(n, n2);
        this.stage.addActor(group);
        return group;
    }
    
    public MyImage addItem(final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        this.stage.addActor(myImage);
        return myImage;
    }
    
    public MyImage addItem(final String s, final float n, final float n2) {
        return this.addItem(this.atlasLevelSelect, s, n, n2);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.stage.clear();
        this.stage.dispose();
    }
    
    @Override
    protected void keyBack() {
        super.keyBack();
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
        Global.preScreen = Constant.NextScreen.Level_Screen;
        Global.nextScreen = Constant.NextScreen.Start_Screen;
        BaseScreen.addFadeOutAction(this.stage, 0.2f);
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        this.stage.act();
        this.stage.draw();
        this.showFps(n);
    }
    
    public void setFlags() {
        final int n = (Global.maxGameLevelEasy - 1) / 10;
        for (int i = 0; i < this.groupFlags.length; ++i) {
            if (i < n) {
                final int n2 = (Global.maxGameLevelHard - 1) / 10;
                final GroupConquer groupConquer = new GroupConquer(i < n2);
                groupConquer.setStarNum(this.getStarNum(i + 1));
                (this.groupFlags[i] = groupConquer).addListener(this.selectListener);
                if (Global.maxGameLevelEasy > 50 && n2 < 5) {
                    this.arrowIndi.setCurGroup(this.groupFlags[n2]);
                }
            }
            else if (i == n) {
                final GroupAttack groupAttack = new GroupAttack();
                groupAttack.setStarNum(this.getStarNum(i + 1));
                (this.groupFlags[i] = groupAttack).addListener(this.selectListener);
                if (Global.maxGameLevelEasy <= 50) {
                    this.arrowIndi.setCurGroup(this.groupFlags[i]);
                }
            }
            else {
                (this.groupFlags[i] = new GroupLock()).addListener(this.selectListener);
            }
            this.groupFlags[i].setPosition(this.poss[i].x, this.poss[i].y);
            this.stage.addActor(this.groupFlags[i]);
        }
        if (this.arrowIndi.curGroup != null) {
            this.arrowIndi.setMainIndicator(this.arrowIndi.curGroup);
            return;
        }
        this.arrowIndi.setShow(false);
    }
    
    public void setLevelEnable() {
        final int n = (Global.maxGameLevelEasy - 1) / 10;
        for (int i = 0; i < this.imgLevels.length; ++i) {
            if (i <= n) {
                this.imgLevels[i].setRegion(this.atlasLevelSelect.findRegion(StrHandle.get("daguan", i + 1)));
            }
            else {
                this.imgLevels[i].setRegion(this.atlasLevelSelect.findRegion(StrHandle.get("daguan", i + 1, ".2")));
            }
        }
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.showFeatureView();
    }
    
    private class ArrowIndi
    {
        MyGroup curGroup;
        MyImage imgArrow;
        boolean isShowGroup;
        
        public ArrowIndi() {
            this.isShowGroup = true;
            this.shakeActor(this.imgArrow = UiHandle.addItem(LevelScreen.this.stage.getRoot(), LevelScreen.this.atlasLevelSelect, "jiantou", 0.0f, 0.0f));
            this.curGroup = null;
        }
        
        public void setCurGroup(final MyGroup curGroup) {
            this.curGroup = curGroup;
        }
        
        public void setMainIndicator(final Actor actor) {
            this.imgArrow.clearActions();
            this.imgArrow.toFront();
            this.imgArrow.setPosition(actor.getX() + (actor.getWidth() - this.imgArrow.getWidth()) / 2.0f, actor.getTop());
            this.shakeActor(this.imgArrow);
            this.isShowGroup = true;
        }
        
        public void setShow(final boolean visible) {
            this.imgArrow.setVisible(visible);
        }
        
        public void setSubIndicator(final Actor actor) {
            this.imgArrow.clearActions();
            this.imgArrow.setPosition(actor.getX() + (actor.getWidth() - this.imgArrow.getWidth()) / 2.0f, actor.getTop() + 5.0f);
            this.shakeActor(this.imgArrow);
            this.isShowGroup = false;
        }
        
        public void shakeActor(final Actor actor) {
            actor.clearActions();
            actor.addAction(Actions.forever(Actions.sequence(Actions.moveBy(0.0f, 10.0f, 0.22f), Actions.moveBy(0.0f, -10.0f, 0.22f))));
        }
    }
    
    private class GroupAttack extends MyGroup
    {
        MyImage imgFlag;
        MyLabel labelStar;
        
        public GroupAttack() {
            this.imgFlag = UiHandle.addItem(this, LevelScreen.this.atlasLevelSelect, "jingong", 0.0f, 0.0f);
            this.labelStar = MyLabel.createLabel(this, "21160", 20.0f, 12.0f);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
        
        public void setStarNum(final int n) {
            this.labelStar.setText(Integer.toString(n) + "/60");
            final MyLabel labelStar = this.labelStar;
            float x;
            if (n < 10) {
                x = 28.0f;
            }
            else {
                x = 18.0f;
            }
            labelStar.setX(x);
        }
    }
    
    private class GroupConquer extends MyGroup
    {
        MyImage imgFlag;
        MyLabel labelStar;
        
        public GroupConquer(final boolean b) {
            MyImage imgFlag;
            if (b) {
                imgFlag = UiHandle.addItem(this, LevelScreen.this.atlasLevelSelect, "zhanling2", 0.0f, 0.0f);
            }
            else {
                imgFlag = UiHandle.addItem(this, LevelScreen.this.atlasLevelSelect, "zhanling1", 0.0f, 0.0f);
            }
            this.imgFlag = imgFlag;
            float n;
            if (b) {
                n = 12.0f;
            }
            else {
                n = 10.0f;
            }
            this.labelStar = MyLabel.createLabel(this, "21160", 18.0f, n);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
        
        public void setStarNum(final int n) {
            this.labelStar.setText(Integer.toString(n) + "/60");
            final MyLabel labelStar = this.labelStar;
            float x;
            if (n < 10) {
                x = 28.0f;
            }
            else {
                x = 18.0f;
            }
            labelStar.setX(x);
        }
    }
    
    private class GroupLock extends MyGroup
    {
        MyImage imgFlag;
        
        public GroupLock() {
            this.imgFlag = UiHandle.addItem(this, LevelScreen.this.atlasLevelSelect, "locked", 0.0f, 0.0f);
            this.setSize(this.imgFlag.getWidth(), this.imgFlag.getHeight());
        }
    }
    
    private class ItemMode
    {
        Actor curFlag;
        private MyImage imgEasy;
        private MyImage imgHard;
        
        public ItemMode() {
            this.imgEasy = UiHandle.addItem(LevelScreen.this.stage.getRoot(), LevelScreen.this.atlasLevelSelect, "easy", 3.0f, 0.0f, LevelScreen.this.selectListener);
            (this.imgHard = UiHandle.addItem(LevelScreen.this.stage.getRoot(), LevelScreen.this.atlasLevelSelect, "hard_b", -136.0f, 0.0f, LevelScreen.this.selectListener)).setTouchable(Touchable.disabled);
            MyMethods.setActorOrigin(this.imgEasy, 1.0f, 0.5f);
            MyMethods.setActorOrigin(this.imgHard, 0.0f, 0.5f);
            this.imgEasy.setVisible(false);
            this.imgHard.setVisible(false);
        }
        
        public final MyImage getImgEasy() {
            return this.imgEasy;
        }
        
        public final MyImage getImgHard() {
            return this.imgHard;
        }
        
        public void hide() {
            this.imgEasy.clearActions();
            this.imgEasy.setScale(1.0f, 1.0f);
            this.imgEasy.addAction(Actions.sequence(Actions.scaleTo(0.0f, 1.0f, 0.25f, Interpolation.swingIn), Actions.visible(false)));
            this.imgHard.clearActions();
            this.imgHard.setScale(1.0f, 1.0f);
            this.imgHard.addAction(Actions.sequence(Actions.scaleTo(0.0f, 1.0f, 0.25f, Interpolation.swingIn), Actions.visible(false)));
            this.curFlag = null;
        }
        
        public void setHardValid(final boolean b) {
            if (b) {
                this.imgHard.setRegion(LevelScreen.this.atlasLevelSelect.findRegion("hard"));
                this.imgHard.setTouchable(Touchable.enabled);
                return;
            }
            this.imgHard.setRegion(LevelScreen.this.atlasLevelSelect.findRegion("hard_b"));
            this.imgHard.setTouchable(Touchable.disabled);
        }
        
        public void show(final Actor curFlag) {
            this.imgEasy.clearActions();
            this.imgEasy.setScale(0.0f, 1.0f);
            this.imgEasy.setVisible(true);
            this.imgEasy.setPosition(curFlag.getX() - this.imgEasy.getWidth() + 10.0f, curFlag.getY() + 8.0f);
            this.imgEasy.addAction(Actions.scaleTo(1.0f, 1.0f, 0.4f, Interpolation.swingOut));
            this.imgHard.clearActions();
            this.imgHard.setScale(0.0f, 1.0f);
            this.imgHard.setVisible(true);
            this.imgHard.setPosition(curFlag.getRight() - 10.0f, curFlag.getY() + 8.0f);
            this.imgHard.addAction(Actions.scaleTo(1.0f, 1.0f, 0.4f, Interpolation.swingOut));
            this.curFlag = curFlag;
        }
    }
}
