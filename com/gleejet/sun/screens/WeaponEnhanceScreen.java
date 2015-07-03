package com.gleejet.sun.screens;

import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.stages.dialogs.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.action.*;
import com.gleejet.sun.utils.ui.*;

public class WeaponEnhanceScreen extends BaseScreen
{
    static final String[] strMains;
    static final String[] strSubs;
    Array<WeaponItem> arrMainIdle;
    Array<WeaponItem> arrSubIdle;
    TextureAtlas atlasEnhance;
    TextureAtlas atlasLevelSelect;
    TextureAtlas atlasScene;
    TextureAtlas atlasStore;
    TextureAtlas atlasWeaponBg;
    BackScene backScene;
    private ButtonListener btnListener;
    CoinLackDialog coinLackDialog;
    int curEnhanceLevel;
    WeaponItem curItem;
    int curSelectIndiLevel;
    DialogEnemyKill dialogEnemyKill;
    DialogHero dialogHero;
    DialogSet dialogSet;
    DialogStore dialogStore;
    CoinMond.GroupCoinHead groupCoinHead;
    GroupEnhance groupEnhance;
    CoinMond.GroupMondHead groupMondHead;
    HelpBoard helpBoard;
    private InputListener idleListener;
    MyImage[] imgAirLevelBgs;
    MyImage[] imgAirLevels;
    MyImage imgBack;
    MyImage[] imgBuildLevelBgs;
    MyImage[] imgBuildLevels;
    MyImage imgEnemyKill;
    MyImage[] imgGroundLevelBgs;
    MyImage[] imgGroundLevels;
    MyImage imgHero;
    MyImage imgIndicator;
    MyImage[] imgMainLevels;
    MyImage imgMainMark;
    MyImage imgMax;
    MyImage imgSet;
    MyImage imgStore;
    MyImage[] imgSubLevels;
    MyImage imgSubMark;
    InfoBoard infoBoard;
    boolean isCurMainSelect;
    JsonValue jsonMain;
    JsonValue jsonSub;
    Label labelIndicator;
    private InputListener levelListener;
    MyScrollPane panMain;
    MyScrollPane panSub;
    Stage stage;
    Table table1;
    Table table2;
    
    static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
    
    public WeaponEnhanceScreen(final MainGame mainGame) {
        super(mainGame);
        this.curEnhanceLevel = 0;
        this.curSelectIndiLevel = 0;
        this.isCurMainSelect = true;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == WeaponEnhanceScreen.this.imgBack) {
                        WeaponEnhanceScreen.this.keyBack();
                        return;
                    }
                    if (listenerActor == WeaponEnhanceScreen.this.imgHero) {
                        DialogHandle.openDialog(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.dialogHero);
                        return;
                    }
                    if (listenerActor == WeaponEnhanceScreen.this.imgEnemyKill) {
                        DialogHandle.openDialog(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.dialogEnemyKill);
                        return;
                    }
                    if (listenerActor == WeaponEnhanceScreen.this.imgSet) {
                        DialogHandle.openDialog(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.dialogSet);
                        return;
                    }
                    if (listenerActor == WeaponEnhanceScreen.this.imgStore) {
                        WeaponEnhanceScreen.this.openStore();
                        return;
                    }
                    if (listenerActor == WeaponEnhanceScreen.this.groupEnhance) {
                        if (WeaponEnhanceScreen.this.isCurMainSelect) {
                            if (WeaponEnhanceScreen.this.curEnhanceLevel >= WeaponEnhanceScreen.this.imgMainLevels.length) {
                                return;
                            }
                            if (!WeaponEnhanceScreen.this.groupEnhance.isEnhanceValid()) {
                                WeaponEnhanceScreen.this.setMainImageIndicator(WeaponEnhanceScreen.this.curEnhanceLevel);
                                return;
                            }
                        }
                        else {
                            if (WeaponEnhanceScreen.this.curEnhanceLevel >= WeaponEnhanceScreen.this.imgSubLevels.length) {
                                return;
                            }
                            if (!WeaponEnhanceScreen.this.groupEnhance.isEnhanceValid()) {
                                WeaponEnhanceScreen.this.setSubImageIndicator(WeaponEnhanceScreen.this.curEnhanceLevel);
                                return;
                            }
                        }
                        if (Global.totalCoinNum >= WeaponEnhanceScreen.this.groupEnhance.getCoinNum()) {
                            Global.totalCoinNum -= WeaponEnhanceScreen.this.groupEnhance.getCoinNum();
                            WeaponEnhanceScreen.this.groupCoinHead.setCoinNum((int)Global.totalCoinNum);
                            PreferHandle.writeGold();
                            final WeaponEnhanceScreen this$0 = WeaponEnhanceScreen.this;
                            ++this$0.curEnhanceLevel;
                            SoundHandle.playForLevelup();
                            if (WeaponEnhanceScreen.this.isCurMainSelect) {
                                PreferHandle.writeWeaponEnhance(Global.strCurWeaponName, WeaponEnhanceScreen.this.curEnhanceLevel);
                                WeaponEnhanceScreen.this.setMainEnhanceLevel(WeaponEnhanceScreen.this.curEnhanceLevel);
                            }
                            else {
                                PreferHandle.writeWeaponEnhance(Global.strCurWeaponName, WeaponEnhanceScreen.this.curEnhanceLevel);
                                WeaponEnhanceScreen.this.setSubEnhanceLevel(WeaponEnhanceScreen.this.curEnhanceLevel);
                            }
                        }
                        else {
                            DialogHandle.openDialog(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.coinLackDialog, 0.35f);
                        }
                        if (WeaponEnhanceScreen.this.helpBoard != null) {
                            WeaponEnhanceScreen.this.helpBoard.remove();
                            PreferHandle.writeInstruction("instructionEnhance", true);
                        }
                    }
                    else {
                        if (listenerActor == WeaponEnhanceScreen.this.groupCoinHead) {
                            WeaponEnhanceScreen.this.groupCoinHead.touchHandle(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.dialogStore);
                            return;
                        }
                        if (listenerActor == WeaponEnhanceScreen.this.groupMondHead) {
                            WeaponEnhanceScreen.this.groupMondHead.touchHandle(WeaponEnhanceScreen.this.stage, WeaponEnhanceScreen.this.dialogStore);
                            return;
                        }
                        if (listenerActor == WeaponEnhanceScreen.this.imgMainMark) {
                            WeaponEnhanceScreen.this.selectItem(true);
                            return;
                        }
                        if (listenerActor == WeaponEnhanceScreen.this.imgSubMark) {
                            WeaponEnhanceScreen.this.selectItem(false);
                        }
                    }
                }
            }
        };
        this.idleListener = new InputListener() {
            boolean isTouchValid = false;
            Vector2 pos = new Vector2();
            float startX = 0.0f;
            float startY = 0.0f;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float startX, final float startY, final int n, final int n2) {
                this.startX = startX;
                this.startY = startY;
                this.isTouchValid = true;
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(TouchAction.downAction());
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
//                while (true) {
//                    if (this.isTouchValid) {
//                        final Actor listenerActor = inputEvent.getListenerActor();
//                        if (Math.abs(n - this.startX) > 15.0f || Math.abs(n2 - this.startY) > 15.0f) {
//                            this.isTouchValid = false;
//                            listenerActor.addAction(TouchAction.upAction());
//                        }
//                        return;
//                    }
//                    continue;
//                }
                if (this.isTouchValid) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    if (Math.abs(n - this.startX) > 15.0f || Math.abs(n2 - this.startY) > 15.0f) {
                        this.isTouchValid = false;
                        listenerActor.addAction(TouchAction.upAction());
                    }
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int i, final int n3) {
                if (this.isTouchValid) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    listenerActor.addAction(TouchAction.upAction());
                    this.isTouchValid = false;
                    SoundHandle.playForButton2();
                    for (i = 0; i < WeaponEnhanceScreen.this.arrMainIdle.size; ++i) {
                        if (WeaponEnhanceScreen.this.arrMainIdle.get(i) == listenerActor) {
                            i = Integer.parseInt(((WeaponItem)WeaponEnhanceScreen.this.arrMainIdle.get(i)).getName().substring(3));
                            Global.strCurWeaponName = WeaponEnhanceScreen.strMains[i - 1];
                            WeaponEnhanceScreen.this.selectMainWeapon(Global.strCurWeaponName);
                            WeaponEnhanceScreen.this.selectWeapon((WeaponItem)listenerActor);
                            return;
                        }
                    }
                    for (i = 0; i < WeaponEnhanceScreen.this.arrSubIdle.size; ++i) {
                        if (WeaponEnhanceScreen.this.arrSubIdle.get(i) == listenerActor) {
                            i = Integer.parseInt(((WeaponItem)WeaponEnhanceScreen.this.arrSubIdle.get(i)).getName().substring(2));
                            Global.strCurWeaponName = WeaponEnhanceScreen.strSubs[i - 1];
                            WeaponEnhanceScreen.this.selectSubWeapon(Global.strCurWeaponName);
                            WeaponEnhanceScreen.this.selectWeapon((WeaponItem)listenerActor);
                            return;
                        }
                    }
                }
            }
        };
        this.levelListener = new InputListener() {
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                return true;
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int i, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                SoundHandle.playForButton2();
                if (WeaponEnhanceScreen.this.isCurMainSelect) {
                    for (i = 0; i < WeaponEnhanceScreen.this.imgMainLevels.length; ++i) {
                        if (WeaponEnhanceScreen.this.imgMainLevels[i] == listenerActor) {
                            WeaponEnhanceScreen.this.setMainImageIndicator(i);
                            break;
                        }
                    }
                }
                else {
                    for (i = 0; i < WeaponEnhanceScreen.this.imgSubLevels.length; ++i) {
                        if (WeaponEnhanceScreen.this.imgSubLevels[i] == listenerActor) {
                            WeaponEnhanceScreen.this.setSubImageIndicator(i);
                            return;
                        }
                    }
                }
            }
        };
        this.stage = new Stage(800.0f, 480.0f, false, Global.batch);
        this.atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        this.atlasWeaponBg = Global.manager.get("ui/ui_weapon_bg.pack", TextureAtlas.class);
        this.atlasLevelSelect = Global.manager.get("ui/ui_level_select.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.setRegion();
        this.init();
        BaseScreen.addFadeInAction(this.stage, 0.3f);
        this.multiplexer.addProcessor(0, this.stage);
    }
    
    private void addInstrction() {
        if (Global.maxGameLevelEasy == 8 && PreferHandle.readWeaponEnhance("SinglePipe") == 0 && Global.totalCoinNum >= 300.0f) {
//            this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupEnhance, "Tap to upgrade \nmachine-gun!", -1, 1);
            this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupEnhance, Language.tapUpgradeGun(), -1, 1);
            this.helpBoard.insStep = 81;
        }
    }
    
    private void enhanceClear() {
        for (int i = 0; i < WeaponEnhanceScreen.strMains.length; ++i) {
            PreferHandle.writeWeaponEnhance(WeaponEnhanceScreen.strMains[i], 0);
        }
        for (int j = 0; j < WeaponEnhanceScreen.strSubs.length; ++j) {
            PreferHandle.writeWeaponEnhance(WeaponEnhanceScreen.strSubs[j], 0);
        }
    }
    
    private void hideEnhance() {
        for (int i = 0; i < this.imgMainLevels.length; ++i) {
            this.imgMainLevels[i].setVisible(false);
        }
        this.infoBoard.hide();
        this.imgIndicator.setVisible(false);
        this.labelIndicator.setVisible(false);
        this.groupEnhance.setVisible(false);
    }
    
    private void init() {
        this.arrMainIdle = new Array<WeaponItem>();
        this.arrSubIdle = new Array<WeaponItem>();
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangzuo", 0.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangyou", 755.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangshang", 44.0f, 463.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangxia", 44.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "dibu", 0.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "waikuang1", 146.0f, 159.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "hengfu", 229.0f, 362.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "anniudi", 11.0f, 3.0f);
        this.imgMainMark = UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "mainkai", 41.0f, 332.0f, this.btnListener);
        this.imgSubMark = UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "lightguan", 41.0f, 265.0f, this.btnListener);
        (this.imgIndicator = this.addItem("jiantou", 174.0f, 72.0f)).addAction(Actions.forever(Actions.sequence(Actions.moveBy(0.0f, -5.0f, 0.18f), Actions.moveBy(0.0f, 5.0f, 0.18f))));
        this.imgMainLevels = new MyImage[7];
        for (int i = 0; i < this.imgMainLevels.length; ++i) {
            MyMethods.setActorOrigin(this.imgMainLevels[i] = UiHandle.addItem(this.stage.getRoot(), this.atlasEnhance, "freezeTime_b", i * 71 + 159, 91.0f, this.levelListener, "freezeTime_b"), 0.5f, 0.5f);
        }
        this.imgSubLevels = new MyImage[3];
        for (int j = 0; j < this.imgSubLevels.length; ++j) {
            this.imgSubLevels[j] = this.imgMainLevels[j];
        }
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this.stage, this.btnListener);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this.stage, this.btnListener);
        this.imgStore = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shangdian", 643.0f, 401.0f, this.btnListener);
        this.imgHero = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "renwu", 103.0f, 401.0f, this.btnListener);
        this.imgEnemyKill = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "chengjiu", 26.0f, 401.0f, this.btnListener);
        this.imgSet = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shezhi", 718.0f, 401.0f, this.btnListener);
        this.imgBack = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "fanhui", 22.0f, 8.0f, this.btnListener);
        this.groupEnhance = new GroupEnhance(this.stage);
        this.infoBoard = new InfoBoard(this.stage);
        (this.imgMax = UiHandle.addItem(this.stage.getRoot(), this.atlasEnhance, "max", 665.0f, 20.0f)).setVisible(false);
        this.labelIndicator = UiHandle.createRomanLabel(this.stage.getRoot(), "", 207.0f, 40.0f);
        this.coinLackDialog = new CoinLackDialog(this);
        this.dialogHero = new DialogHero();
        this.dialogEnemyKill = new DialogEnemyKill();
        this.dialogSet = new DialogSet();
        this.dialogStore = new DialogStore(this, this.groupCoinHead, this.groupMondHead);
        this.initWeapon();
        final int index = MyMethods.findIndex(WeaponEnhanceScreen.strMains, Global.strCurWeaponName);
        final int index2 = MyMethods.findIndex(WeaponEnhanceScreen.strSubs, Global.strCurWeaponName);
        if (index >= 0) {
            this.selectItem(true);
        }
        else if (index2 >= 0) {
            this.selectItem(false);
        }
        else {
            Global.strCurWeaponName = "SinglePipe";
            this.selectItem(true);
        }
        this.addInstrction();
    }
    
    private void initWeapon() {
        this.initArrMainIdle();
        this.initPanMain();
        this.tableMainPack();
        this.initArrSubIdle();
        this.initPanSub();
        this.tableSubPack();
    }
    
    private void selectItem(final boolean b) {
        final int index = Global.arrMainGunGet.indexOf(Global.strCurWeaponName, false);
        final int index2 = Global.arrSubGunGet.indexOf(Global.strCurWeaponName, false);
        if (b) {
            this.imgMainMark.setRegion(this.atlasWeaponBg.findRegion("mainkai"));
            this.imgSubMark.setRegion(this.atlasWeaponBg.findRegion("lightguan"));
            this.panMain.setVisible(true);
            this.panSub.setVisible(false);
            if (index >= 0) {
                this.selectMainWeapon(Global.strCurWeaponName);
                this.selectWeapon(this.arrMainIdle.get(index));
                this.showEnhance();
                return;
            }
            if (Global.arrMainGunGet.size > 0 && this.arrMainIdle.size > 0) {
                this.selectMainWeapon(Global.strCurWeaponName = Global.arrMainGunGet.get(0));
                this.selectWeapon(this.arrMainIdle.get(0));
                this.showEnhance();
                return;
            }
            this.hideEnhance();
        }
        else {
            this.imgMainMark.setRegion(this.atlasWeaponBg.findRegion("mainguan"));
            this.imgSubMark.setRegion(this.atlasWeaponBg.findRegion("lightkai"));
            this.panMain.setVisible(false);
            this.panSub.setVisible(true);
            if (index2 >= 0) {
                this.selectSubWeapon(Global.strCurWeaponName);
                this.selectWeapon(this.arrSubIdle.get(index2));
                this.showEnhance();
                return;
            }
            if (Global.arrSubGunGet.size > 0 && this.arrSubIdle.size > 0) {
                this.selectSubWeapon(Global.strCurWeaponName = Global.arrSubGunGet.get(0));
                this.selectWeapon(this.arrSubIdle.get(0));
                this.showEnhance();
                return;
            }
            this.hideEnhance();
        }
    }
    
    private void selectMainWeapon(final String s) {
        MyMethods.findIndex(WeaponEnhanceScreen.strMains, s);
        this.jsonMain = Assets.jsonEnhance.get(s);
        final JsonValue value = this.jsonMain.get("types");
        this.isCurMainSelect = true;
        for (int i = 0; i < this.imgMainLevels.length; ++i) {
            this.imgMainLevels[i].setVisible(true);
        }
        for (int j = 0; j < value.size; ++j) {
            final String string = value.get(j).asString();
            for (int k = 0; k < this.atlasEnhance.getRegions().size; ++k) {
                final String name = ((TextureAtlas.AtlasRegion)this.atlasEnhance.getRegions().get(k)).name;
                if (string.toUpperCase().equals(name.toUpperCase())) {
                    final TextureAtlas.AtlasRegion region = this.atlasEnhance.findRegion(name + "_b");
                    this.imgMainLevels[j].setRegion(region);
                    this.imgMainLevels[j].setVisible(region != null);
                    this.imgMainLevels[j].setName(name + "_b");
                    break;
                }
                if (false) {}
            }
        }
        this.setMainEnhanceLevel(this.curEnhanceLevel = PreferHandle.readWeaponEnhance(s));
        this.infoBoard.setWeaponName(s);
    }
    
    private void selectSubWeapon(final String s) {
        MyMethods.findIndex(WeaponEnhanceScreen.strSubs, s);
        this.jsonSub = Assets.jsonEnhance.get(s);
        final JsonValue value = this.jsonSub.get("types");
        this.isCurMainSelect = false;
        for (int i = 0; i < this.imgMainLevels.length; ++i) {
            this.imgMainLevels[i].setVisible(false);
        }
        for (int j = 0; j < this.imgSubLevels.length; ++j) {
            this.imgSubLevels[j].setVisible(true);
        }
        for (int k = 0; k < value.size; ++k) {
            final String string = value.get(k).asString();
            for (int l = 0; l < this.atlasEnhance.getRegions().size; ++l) {
                final String name = ((TextureAtlas.AtlasRegion)this.atlasEnhance.getRegions().get(l)).name;
                if (string.toUpperCase().equals(name.toUpperCase())) {
                    this.imgSubLevels[k].setRegion(this.atlasEnhance.findRegion(name + "_b"));
                    this.imgSubLevels[k].setName(name + "_b");
                    break;
                }
                if (false) {}
            }
        }
        this.setSubEnhanceLevel(this.curEnhanceLevel = PreferHandle.readWeaponEnhance(s));
        this.infoBoard.setWeaponName(s);
    }
    
    private void selectWeapon(final WeaponItem curItem) {
        if (this.curItem != null) {
            this.curItem.hideShake();
        }
        (this.curItem = curItem).showShake();
    }
    
    private void setMainEnhanceLevel(final int mainImageIndicator) {
        final boolean b = true;
        for (int i = 0; i < mainImageIndicator; ++i) {
            final String name = this.imgMainLevels[i].getName();
            if (name.endsWith("_b")) {
                final String substring = name.substring(0, name.length() - 2);
                final TextureAtlas.AtlasRegion region = this.atlasEnhance.findRegion(substring);
                this.imgMainLevels[i].setRegion(region);
                this.imgMainLevels[i].setVisible(region != null);
                this.imgMainLevels[i].setName(substring);
            }
        }
        this.setMainImageIndicator(mainImageIndicator);
        this.imgIndicator.setVisible(mainImageIndicator < this.imgMainLevels.length);
        this.groupEnhance.setVisible(mainImageIndicator < this.imgMainLevels.length);
        this.imgMax.setVisible(!this.groupEnhance.isVisible() && b);
        this.infoBoard.setWeaponName(this.jsonMain.name);
    }
    
    private void setMainImageIndicator(final int curSelectIndiLevel) {
        for (int i = 0; i < this.imgMainLevels.length; ++i) {
            if (i == curSelectIndiLevel) {
                this.imgMainLevels[i].setScale(0.65f);
                this.imgMainLevels[i].setY(97.0f);
                this.imgMainLevels[i].addAction(Actions.scaleTo(1.15f, 1.15f, 0.15f, Interpolation.swingOut));
            }
            else {
                this.imgMainLevels[i].setY(91.0f);
                this.imgMainLevels[i].setScale(1.0f);
            }
        }
        if (curSelectIndiLevel < this.imgMainLevels.length) {
            this.imgIndicator.clearActions();
            this.imgIndicator.addAction(Actions.sequence(Actions.moveTo(this.imgMainLevels[curSelectIndiLevel].getX() + this.imgMainLevels[curSelectIndiLevel].getWidth() / 2.0f - this.imgIndicator.getWidth() / 2.0f, 67.0f, 0.1f), Actions.forever(Actions.sequence(Actions.moveBy(0.0f, -5.0f, 0.18f), Actions.moveBy(0.0f, 5.0f, 0.18f)))));
            if (curSelectIndiLevel < this.curEnhanceLevel) {
                this.groupEnhance.setUnlock();
            }
            else {
                this.groupEnhance.setCoinNum(this.jsonMain.get("coinCost").get(curSelectIndiLevel).asInt());
            }
            this.labelIndicator.setText(this.jsonMain.get("strShow").get(curSelectIndiLevel).asString() + ".");
        }
        this.curSelectIndiLevel = curSelectIndiLevel;
    }
    
    private void setRegion() {
        this.backScene = new BackScene();
    }
    
    private void setSubEnhanceLevel(final int subImageIndicator) {
        final boolean b = true;
        for (int i = 0; i < subImageIndicator; ++i) {
            final String name = this.imgSubLevels[i].getName();
            if (name.endsWith("_b")) {
                final String substring = name.substring(0, name.length() - 2);
                this.imgSubLevels[i].setRegion(this.atlasEnhance.findRegion(substring));
                this.imgSubLevels[i].setName(substring);
            }
        }
        this.setSubImageIndicator(subImageIndicator);
        this.imgIndicator.setVisible(subImageIndicator < 3);
        this.groupEnhance.setVisible(subImageIndicator < 3);
        this.imgMax.setVisible(!this.groupEnhance.isVisible() && b);
        this.infoBoard.setWeaponName(this.jsonSub.name);
    }
    
    private void setSubImageIndicator(final int curSelectIndiLevel) {
        for (int i = 0; i < this.imgSubLevels.length; ++i) {
            if (i == curSelectIndiLevel) {
                this.imgSubLevels[i].setScale(0.65f);
                this.imgSubLevels[i].setY(97.0f);
                this.imgSubLevels[i].addAction(Actions.scaleTo(1.15f, 1.15f, 0.15f, Interpolation.swingOut));
            }
            else {
                this.imgSubLevels[i].setY(91.0f);
                this.imgSubLevels[i].setScale(1.0f);
            }
        }
        if (curSelectIndiLevel < this.imgSubLevels.length) {
            this.imgIndicator.clearActions();
            this.imgIndicator.addAction(Actions.sequence(Actions.moveTo(this.imgSubLevels[curSelectIndiLevel].getX() + this.imgSubLevels[curSelectIndiLevel].getWidth() / 2.0f - this.imgIndicator.getWidth() / 2.0f, 67.0f, 0.1f), Actions.forever(Actions.sequence(Actions.moveBy(0.0f, -5.0f, 0.18f), Actions.moveBy(0.0f, 5.0f, 0.18f)))));
            if (curSelectIndiLevel < this.curEnhanceLevel) {
                this.groupEnhance.setUnlock();
            }
            else {
                this.groupEnhance.setCoinNum(this.jsonSub.get("coinCost").get(curSelectIndiLevel).asInt());
            }
            this.labelIndicator.setText(this.jsonSub.get("strShow").get(curSelectIndiLevel).asString() + ".");
        }
        this.curSelectIndiLevel = curSelectIndiLevel;
    }
    
    private void showEnhance() {
        this.imgIndicator.setVisible(true);
        this.labelIndicator.setVisible(true);
        this.groupEnhance.setVisible(true);
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
        return this.addItem(this.atlasEnhance, s, n, n2);
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.stage.clear();
        this.stage.dispose();
    }
    
    public void fadeOut() {
        BaseScreen.addFadeOutAction(this.stage, 0.2f);
    }
    
    public void initArrMainIdle() {
        this.arrMainIdle.clear();
        for (int i = 0; i < Global.arrMainGunGet.size; ++i) {
            final int index = MyMethods.findIndex(WeaponEnhanceScreen.strMains, Global.arrMainGunGet.get(i));
            this.arrMainIdle.add(new WeaponItem(StrHandle.get("ren", index + 1), String.format("ren%02d", index + 1)));
        }
        for (int j = Global.arrMainGunGet.size; j < 13; ++j) {
            final WeaponItem weaponItem = new WeaponItem("empty", String.format("zero%02d", j + 1));
            weaponItem.setTouchable(Touchable.disabled);
            this.arrMainIdle.add(weaponItem);
        }
    }
    
    public void initArrSubIdle() {
        this.arrSubIdle.clear();
        for (int i = 0; i < Global.arrSubGunGet.size; ++i) {
            final int index = MyMethods.findIndex(WeaponEnhanceScreen.strSubs, Global.arrSubGunGet.get(i));
            this.arrSubIdle.add(new WeaponItem(StrHandle.get("fu", index + 1), String.format("fu%d", index + 1)));
        }
        for (int j = Global.arrSubGunGet.size; j < 7; ++j) {
            final WeaponItem weaponItem = new WeaponItem("empty", String.format("zero%d", j + 1));
            weaponItem.setTouchable(Touchable.disabled);
            this.arrSubIdle.add(weaponItem);
        }
    }
    
    public void initPanMain() {
        this.table1 = new Table();
        this.table1.defaults().space(2.0f);
        this.table1.defaults().padRight(3.0f);
        this.table1.defaults().padBottom(-2.0f);
        final MyScrollPane.ScrollPaneStyle scrollPaneStyle = new MyScrollPane.ScrollPaneStyle();
        scrollPaneStyle.hScroll = new TextureRegionDrawable(this.atlasWeaponBg.findRegion("huadongtiao"));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(this.atlasWeaponBg.findRegion("huadongtiao"));
        final NinePatch ninePatch = new NinePatch(Assets.atlasWeaponSelect.findRegion("huadonganniu"), 5, 5, 15, 15);
        scrollPaneStyle.hScrollKnob = new NinePatchDrawable(ninePatch);
        scrollPaneStyle.vScrollKnob = new NinePatchDrawable(ninePatch);
        (this.panMain = new MyScrollPane(this.table1, scrollPaneStyle)).setFadeScrollBars(false);
        this.panMain.setScrollbarsOnTop(true);
        this.panMain.setScrollingDisabled(true, false);
        this.panMain.setSize(350.0f, 165.0f);
        this.panMain.setPosition(152.0f, 193.0f);
        this.stage.addActor(this.panMain);
    }
    
    public void initPanSub() {
        this.table2 = new Table();
        this.table2.defaults().space(2.0f);
        this.table2.defaults().padRight(3.0f);
        this.table2.defaults().padBottom(-2.0f);
        final MyScrollPane.ScrollPaneStyle scrollPaneStyle = new MyScrollPane.ScrollPaneStyle();
        scrollPaneStyle.hScroll = new TextureRegionDrawable(this.atlasWeaponBg.findRegion("huadongtiao"));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(this.atlasWeaponBg.findRegion("huadongtiao"));
        final NinePatch ninePatch = new NinePatch(Assets.atlasWeaponSelect.findRegion("huadonganniu"), 5, 5, 15, 15);
        scrollPaneStyle.hScrollKnob = new NinePatchDrawable(ninePatch);
        scrollPaneStyle.vScrollKnob = new NinePatchDrawable(ninePatch);
        (this.panSub = new MyScrollPane(this.table2, scrollPaneStyle)).setFadeScrollBars(false);
        this.panSub.setScrollingDisabled(true, false);
        this.panSub.setSize(350.0f, 165.0f);
        this.panSub.setPosition(152.0f, 193.0f);
        this.stage.addActor(this.panSub);
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
        Global.nextScreen = Global.preScreen;
        Global.preScreen = Constant.NextScreen.Weapon_Enhance;
        BaseScreen.addFadeOutAction(this.stage, 0.25f);
    }
    
    public void openStore() {
        this.dialogStore.openDialog(this.stage);
    }
    
    public void openStore(final int n) {
        this.dialogStore.selectItem(n);
        this.dialogStore.openDialog(this.stage);
    }
    
    public void refreshWeapon() {
        this.initArrMainIdle();
        this.tableMainPack();
        this.initArrSubIdle();
        this.tableSubPack();
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
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.closeFeatureView();
    }
    
    public void tableMainPack() {
        this.table1.clear();
        int n = 0;
        for (int i = 0; i < this.arrMainIdle.size; ++i) {
            this.table1.add(this.arrMainIdle.get(i));
            ++n;
            if (n % 4 == 0 && i < this.arrMainIdle.size) {
                this.table1.row();
            }
        }
    }
    
    public void tableSubPack() {
        this.table2.clear();
        int n = 0;
        for (int i = 0; i < this.arrSubIdle.size; ++i) {
            this.table2.add(this.arrSubIdle.get(i));
            ++n;
            if (n % 4 == 0 && i < this.arrSubIdle.size) {
                this.table2.row();
            }
        }
    }
    
    private class GroupEnhance extends MyGroup
    {
        int coinNum;
        MyImage imgBg;
        MyLabel labelCoin;
        Label labelUnlock;
        
        public GroupEnhance(final Stage stage) {
            this.coinNum = 1234;
            this.imgBg = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "anniu", 0.0f, 0.0f);
            this.labelCoin = MyLabel.createLabel(this, StrHandle.get("1234"), 67.0f, 27.0f);
            this.labelUnlock = UiHandle.createRomanLabel(this, StrHandle.get("Unlocked"), 58.0f, 25.0f);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
            this.setPosition(644.0f, 5.0f);
            this.addListener(WeaponEnhanceScreen.this.btnListener);
            stage.addActor(this);
        }
        
        public int getCoinNum() {
            return this.coinNum;
        }
        
        public boolean isEnhanceValid() {
            return WeaponEnhanceScreen.this.curEnhanceLevel == WeaponEnhanceScreen.this.curSelectIndiLevel;
        }
        
        public void setCoinNum(final int coinNum) {
            this.coinNum = coinNum;
            this.labelCoin.setText(StrHandle.get(this.coinNum));
            this.labelCoin.setVisible(true);
            this.labelUnlock.setVisible(false);
        }
        
        public void setUnlock() {
            this.labelCoin.setVisible(false);
            this.labelUnlock.setVisible(true);
        }
    }
    
    private class InfoBoard extends MyGroup
    {
        MyImage[] imgAirLevelBgs;
        MyImage[] imgAirLevels;
        MyImage imgBg;
        MyImage[] imgBuildLevelBgs;
        MyImage[] imgBuildLevels;
        MyImage[] imgGroundLevelBgs;
        MyImage[] imgGroundLevels;
        MyImage imgWeapon;
        AutoLineLabel labelInfo;
        String strName;
        
        public InfoBoard(final Stage stage) {
            final TextureAtlas textureAtlas = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
            this.imgBg = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasWeaponBg, "waikuang2", 0.0f, 0.0f);
            this.imgWeapon = UiHandle.addItem(this, Assets.atlasUiGame, "ren0", 14.0f, 119.0f);
            UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "ren", 93.0f, 174.0f);
            UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "long", 93.0f, 145.0f);
            UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "jianta", 93.0f, 119.0f);
            this.imgGroundLevelBgs = new MyImage[15];
            this.imgGroundLevels = new MyImage[15];
            this.imgAirLevelBgs = new MyImage[15];
            this.imgAirLevels = new MyImage[15];
            this.imgBuildLevelBgs = new MyImage[15];
            this.imgBuildLevels = new MyImage[15];
            for (int i = 0; i < this.imgGroundLevelBgs.length; ++i) {
                this.imgGroundLevelBgs[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgGroundLevels[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgAirLevelBgs[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgAirLevels[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgBuildLevelBgs[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 135.0f - 12.0f);
                this.imgBuildLevels[i] = UiHandle.addItem(this, WeaponEnhanceScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 135.0f - 12.0f);
            }
            this.labelInfo = AutoLineLabel.createRoman16(this, "Continious and rapid fire, low damage to defensive structures.", 30.0f, 65.0f, 162.0f);
            this.setPosition(538.0f, 161.0f);
            this.setWeaponLevel(7, 8, 9);
            stage.addActor(this);
        }
        
        private void setWeaponLevel(int i, final int n, final int n2) {
            for (int j = 0; j < this.imgGroundLevels.length; ++j) {
                this.imgGroundLevels[j].setVisible(j < i);
            }
            for (i = 0; i < this.imgAirLevels.length; ++i) {
                this.imgAirLevels[i].setVisible(i < n);
            }
            for (i = 0; i < this.imgBuildLevels.length; ++i) {
                this.imgBuildLevels[i].setVisible(i < n2);
            }
        }
        
        private void setWeaponName(final String strName) {
            this.strName = strName;
            final JsonValue value = Assets.jsonEnhance.get(this.strName);
            final int weaponEnhance = PreferHandle.readWeaponEnhance(this.strName);
            float n = 100.0f;
            float n2 = 100.0f;
            float n3 = 100.0f;
            final JsonValue value2 = value.get("types");
            final JsonValue value3 = value.get("addValue");
            float n4;
            float n5;
            float n6;
            for (int i = 0; i < weaponEnhance; ++i, n = n4, n2 = n5, n3 = n6) {
                final String string = value2.get(i).asString();
                if (string.equals("AllDamage")) {
                    n4 = n + value3.get(i).asFloat();
                    n5 = n2 + value3.get(i).asFloat();
                    n6 = n3 + value3.get(i).asFloat();
                }
                else if (string.equals("AirDamage")) {
                    n5 = n2 + value3.get(i).asFloat();
                    n4 = n;
                    n6 = n3;
                }
                else if (string.equals("GroundDamage")) {
                    n4 = n + value3.get(i).asFloat();
                    n5 = n2;
                    n6 = n3;
                }
                else {
                    n4 = n;
                    n5 = n2;
                    n6 = n3;
                    if (string.equals("BuildDamge")) {
                        n6 = n3 + value3.get(i).asFloat();
                        n4 = n;
                        n5 = n2;
                    }
                }
            }
            final JsonValue value4 = value.get("damageRatio");
            this.setWeaponLevel((int)(value4.get(0).asInt() * n / 100.0f), (int)(value4.get(1).asInt() * n2 / 100.0f), (int)(value4.get(2).asInt() * n3 / 100.0f));
            this.labelInfo.setInfo(value.getString("selInfo"));
            final int n7 = MyMethods.findIndex(WeaponEnhanceScreen.strMains, this.strName) + 1;
            if (n7 > 0) {
                this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", n7)));
                return;
            }
            this.imgWeapon.setRegion(WeaponEnhanceScreen.this.atlasStore.findRegion(StrHandle.get("fu", MyMethods.findIndex(WeaponEnhanceScreen.strSubs, this.strName) + 1)));
            this.imgWeapon.setSize(94.0f, 116.0f);
        }
        
        public void hide() {
            this.setWeaponLevel(0, 0, 0);
            this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion("ren0"));
            this.labelInfo.setInfo("");
        }
    }
    
    class WeaponItem extends Group
    {
        MyImage imgBg;
        MyImage imgWeapon;
        
        public WeaponItem(final String s, final String name) {
            MyMethods.setActorOrigin(this.imgBg = UiHandle.addItem(this, Assets.atlasWeaponSelect, "guang2", -6.0f, -5.5f), 0.5f, 0.5f);
            this.imgBg.addAction(BlinkAction.scaleUpDown(0.97f, 1.05f, 0.5f));
            this.imgBg.setTouchable(Touchable.disabled);
            this.imgBg.setVisible(false);
            this.imgWeapon = UiHandle.addItem(this, Assets.atlasWeaponSelect, s, 0.0f, 0.0f);
            this.addListener(WeaponEnhanceScreen.this.idleListener);
            this.setName(name);
            this.setSize(this.imgWeapon.getWidth(), this.imgBg.getHeight());
            this.setTransform(false);
        }
        
        public void hideShake() {
            this.imgBg.setVisible(false);
        }
        
        public void showShake() {
            this.imgBg.setVisible(true);
        }
    }
}
