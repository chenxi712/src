package com.gleejet.sun.screens;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.gleejet.sun.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.objects.maingun.*;
import com.gleejet.sun.stages.dialogs.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.action.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class WeaponScreen extends BaseScreen
{
    static final String[] strMains;
    static final String[] strSubs;
    Array<WeaponItem> arrMainIdle;
    Array<MyImage> arrMainSelect;
    Array<WeaponItem> arrSubIdle;
    Array<MyImage> arrSubSelect;
    TextureAtlas atlasEnhance;
    TextureAtlas atlasLevelSelect;
    TextureAtlas atlasScene;
    TextureAtlas atlasStore;
    TextureAtlas atlasWeaponBg;
    BackScene backScene;
    InputListener btnListener;
    WeaponItem curItem;
    MyImage curSelImg;
    DialogEnemyKill dialogEnemyKill;
    DialogHero dialogHero;
    DialogSet dialogSet;
    DialogStore dialogStore;
    float downTime;
    CoinMond.GroupCoinHead groupCoinHead;
    CoinMond.GroupMondHead groupMondHead;
    GroupProba groupProba;
    MainGunShow gunShow;
    HelpBoard helpBoard;
    MyImage imgBack;
    MyImage imgEnemyKill;
    MyImage imgHero;
    MyImage[] imgMainEmptys;
    MyImage imgMainMark;
    MyImage[] imgMainSelects;
    MyImage[] imgMainShakes;
    MyImage imgNoweapon;
    MyImage imgPlay;
    MyImage imgProfile;
    MyImage imgSelect;
    MyImage imgSet;
    MyImage imgStore;
    MyImage imgSubEmpty;
    MyImage imgSubMark;
    MyImage imgSubSelect;
    MyImage imgSubShake;
    MyImage imgSubgunShow;
    MyImage imgUpgrade;
    InfoBoard infoBoard;
    AutoLineLabel infoSub;
    boolean isDown;
    boolean isLongTouch;
    boolean isTouchMain;
    InputListener mainIdleListener;
    private InputListener mainSelListener;
    boolean[] overlaps;
    MyScrollPane panMain;
    MyScrollPane panSub;
    private InputListener probIdleListener;
    private InputListener probSeListener;
    Stage stage;
    String strCurWeapon;
    private InputListener subIdleListener;
    boolean subOverlap;
    private InputListener subSelListener;
    Table table1;
    Table table2;
    Actor touchActor;
    
    static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
    
    public WeaponScreen(final MainGame mainGame) {
        super(mainGame);
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    SoundHandle.playForButton2();
                    final Actor listenerActor = inputEvent.getListenerActor();
                    if (listenerActor == WeaponScreen.this.imgPlay) {
                        WeaponScreen.this.playHandle();
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgBack) {
                        WeaponScreen.this.keyBack();
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgUpgrade) {
                        WeaponScreen.this.writeConfig();
                        Global.strCurWeaponName = WeaponScreen.this.infoBoard.strName;
                        Global.preScreen = Constant.NextScreen.Weapon_Screen;
                        Global.nextScreen = Constant.NextScreen.Weapon_Enhance;
                        BaseScreen.addFadeOutAction(WeaponScreen.this.stage, 0.25f);
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgHero) {
                        DialogHandle.openDialog(WeaponScreen.this.stage, WeaponScreen.this.dialogHero);
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgEnemyKill) {
                        DialogHandle.openDialog(WeaponScreen.this.stage, WeaponScreen.this.dialogEnemyKill);
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgSet) {
                        DialogHandle.openDialog(WeaponScreen.this.stage, WeaponScreen.this.dialogSet);
                        return;
                    }
                    if (listenerActor == WeaponScreen.this.imgStore) {
                        WeaponScreen.this.writeConfig();
                        WeaponScreen.this.dialogStore.openDialog(WeaponScreen.this.stage);
                        if (WeaponScreen.this.helpBoard != null) {
                            WeaponScreen.this.helpBoard.remove();
                        }
                    }
                    else {
                        if (listenerActor == WeaponScreen.this.groupCoinHead) {
                            WeaponScreen.this.writeConfig();
                            WeaponScreen.this.groupCoinHead.touchHandle(WeaponScreen.this.stage, WeaponScreen.this.dialogStore);
                            return;
                        }
                        if (listenerActor == WeaponScreen.this.groupMondHead) {
                            WeaponScreen.this.writeConfig();
                            WeaponScreen.this.groupMondHead.touchHandle(WeaponScreen.this.stage, WeaponScreen.this.dialogStore);
                            return;
                        }
                        if (listenerActor == WeaponScreen.this.imgMainMark) {
                            WeaponScreen.this.selectItem(true);
                            return;
                        }
                        if (listenerActor == WeaponScreen.this.imgSubMark) {
                            WeaponScreen.this.selectItem(false);
                            if (WeaponScreen.this.helpBoard != null && WeaponScreen.this.helpBoard.insStep == 91) {
                                WeaponScreen.this.helpBoard.remove();
                                WeaponScreen.this.addInstruction(92);
                            }
                        }
                    }
                }
            }
        };
        this.overlaps = new boolean[] { false, false, false };
        this.isDown = false;
        this.downTime = 0.0f;
        this.isLongTouch = false;
        this.touchActor = null;
        this.isTouchMain = false;
        this.mainIdleListener = new InputListener() {
            boolean isOut = false;
            float lastX = 0.0f;
            float lastY = 0.0f;
            Vector2 pos = new Vector2();
            float startX = 0.0f;
            float startY = 0.0f;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, int int1, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastY = n2;
                this.isOut = false;
                WeaponScreen.this.panMain.isOut = false;
                WeaponScreen.this.resetOverlap();
                WeaponScreen.this.initDown(listenerActor, true);
                WeaponScreen.this.touchIdleWeapon((WeaponItem)listenerActor);
                int1 = Integer.parseInt(listenerActor.getName().substring(3));
                WeaponScreen.this.infoBoard.setWeaponName(WeaponScreen.strMains[int1 - 1]);
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, int int1) {
                Actor listenerActor;
                final Actor actor = listenerActor = inputEvent.getListenerActor();
                if (WeaponScreen.this.helpBoard != null && (listenerActor = actor) == WeaponScreen.this.helpBoard.tempTarget) {
                    listenerActor = WeaponScreen.this.arrMainIdle.get(0);
                }
                if (Math.abs(lastY - this.startY) > 18.0f) {
                    WeaponScreen.this.isDown = false;
                }
                if (WeaponScreen.this.panMain.isOut && !WeaponScreen.this.isDown) {
                    this.isOut = true;
                }
                if (!this.isOut && Math.abs(lastX - this.startX) > 25.0f) {
                    this.isOut = true;
                    WeaponScreen.this.panMain.isOut = true;
                    listenerActor.localToStageCoordinates(this.pos.set(5.0f, 0.0f));
                    int1 = Integer.parseInt(listenerActor.getName().substring(3));
                    WeaponScreen.this.imgSelect.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", int1)));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    WeaponScreen.this.imgSelect.toFront();
                    WeaponScreen.this.imgSelect.setVisible(true);
                    WeaponScreen.this.showAllMainShake();
                }
                if (this.isOut && (Math.abs(lastX - this.lastX) > 6.0f || Math.abs(lastY - this.lastY) > 6.0f)) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX + 5.0f - this.startX, lastY - this.startY));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkOverlap(WeaponScreen.this.imgSelect);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int n3, int int1) {
                SoundHandle.playForButton3();
                WeaponScreen.this.hideMainShake();
                WeaponScreen.this.imgSelect.setVisible(false);
                WeaponScreen.this.resetDown();
                if (this.isOut) {
                    WeaponItem listenerActor;
                    final Actor actor = listenerActor = (WeaponItem)inputEvent.getListenerActor();
                    if (WeaponScreen.this.helpBoard != null && (listenerActor = (WeaponItem)actor) == WeaponScreen.this.helpBoard.tempTarget) {
                        listenerActor = WeaponScreen.this.arrMainIdle.get(0);
                    }
                    final int n4 = 0;
                    final int n5 = -1;
                    n3 = 0;
                    while (true) {
                        MyImage myImage = null;
                        Label_0331: {
                            int n6;
                            while (true) {
                                int1 = n4;
                                n6 = n5;
                                if (n3 >= WeaponScreen.this.imgMainEmptys.length) {
                                    break;
                                }
                                if (isImgOverlap(WeaponScreen.this.imgMainEmptys[n3], WeaponScreen.this.imgSelect)) {
                                    final int n7 = int1 = 1;
                                    n6 = n3;
                                    if (WeaponScreen.this.imgMainSelects[n3] == null) {
                                        break;
                                    }
                                    myImage = WeaponScreen.this.imgMainSelects[n3];
                                    myImage.remove();
                                    WeaponScreen.this.arrMainSelect.removeValue(myImage, true);
                                    WeaponScreen.this.showMainGun();
                                    WeaponScreen.this.imgMainSelects[n3] = null;
                                    if (myImage.getName().contains("_prob")) {
                                        WeaponScreen.this.groupProba.isPack = true;
                                        WeaponScreen.this.tableMainPack();
                                        n6 = n3;
                                        int1 = n7;
                                        break;
                                    }
                                    break Label_0331;
                                }
                                else {
                                    ++n3;
                                }
                            }
                            if (int1 == 0) {
                                return;
                            }
                            WeaponScreen.this.selectMainImg((WeaponItem)listenerActor, n6);
                            WeaponScreen.this.showMainShake(n6);
                            if (WeaponScreen.this.helpBoard != null) {
                                WeaponScreen.this.helpBoard.remove();
//                                WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, "Battle start!", -1, 1);
                                WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, Language.battleStart(), -1, 1);
                                return;
                            }
                            return;
                        }
                        int1 = Integer.parseInt(myImage.getName().substring(3));
                        final WeaponItem weaponItem = new WeaponItem(StrHandle.get("ren", int1), String.format("ren%02d", int1));
                        weaponItem.addListener(WeaponScreen.this.mainIdleListener);
                        WeaponScreen.this.arrMainIdle.add(weaponItem);
                        continue;
                    }
                }
            }
        };
        this.probIdleListener = new InputListener() {
            boolean isOut = false;
            float lastX;
            float lastY;
            Vector2 pos = new Vector2();
            float startX;
            float startY;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, int int1, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastX = n2;
                this.isOut = false;
                WeaponScreen.this.panMain.isOut = false;
                WeaponScreen.this.resetOverlap();
                WeaponScreen.this.initDown(listenerActor, true);
                int1 = Integer.parseInt(listenerActor.getName().substring(3));
                WeaponScreen.this.infoBoard.setWeaponName(WeaponScreen.strMains[int1 - 1]);
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, int int1) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (Math.abs(lastY - this.startY) > 18.0f) {
                    WeaponScreen.this.isDown = false;
                }
                if (WeaponScreen.this.panMain.isOut && !WeaponScreen.this.isDown) {
                    this.isOut = true;
                }
                if (!this.isOut && Math.abs(lastX - this.startX) > 25.0f) {
                    this.isOut = true;
                    WeaponScreen.this.panMain.isOut = true;
                    listenerActor.localToStageCoordinates(this.pos.set(5.0f, 0.0f));
                    int1 = Integer.parseInt(listenerActor.getName().substring(3));
                    WeaponScreen.this.imgSelect.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", int1)));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    WeaponScreen.this.imgSelect.toFront();
                    WeaponScreen.this.imgSelect.setVisible(true);
                    WeaponScreen.this.showAllMainShake();
                }
                if (this.isOut && (Math.abs(lastX - this.lastX) > 6.0f || Math.abs(lastY - this.lastY) > 6.0f)) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX + 5.0f - this.startX, lastY - this.startY));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkOverlap(WeaponScreen.this.imgSelect);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int int1, int int2) {
                SoundHandle.playForButton3();
                WeaponScreen.this.imgSelect.setVisible(false);
                WeaponScreen.this.hideMainShake();
                WeaponScreen.this.resetDown();
                if (this.isOut) {
                    if (Math.abs(n - this.startX) > 150.0f && !WeaponScreen.this.groupProba.isValid()) {
                        WeaponScreen.this.dialogStore.selectItem(3);
                        WeaponScreen.this.dialogStore.openDialog(WeaponScreen.this.stage);
                        return;
                    }
                    final Actor listenerActor = inputEvent.getListenerActor();
                    final int n3 = -1;
                    final boolean b = false;
                    int1 = 0;
                    boolean b2;
                    while (true) {
                        b2 = b;
                        int2 = n3;
                        if (int1 >= WeaponScreen.this.imgMainEmptys.length) {
                            break;
                        }
                        if (isImgOverlap(WeaponScreen.this.imgMainEmptys[int1], WeaponScreen.this.imgSelect)) {
                            b2 = true;
                            int2 = int1;
                            if (WeaponScreen.this.imgMainSelects[int1] != null) {
                                final MyImage myImage = WeaponScreen.this.imgMainSelects[int1];
                                myImage.remove();
                                WeaponScreen.this.arrMainSelect.removeValue(myImage, true);
                                WeaponScreen.this.showMainGun();
                                WeaponScreen.this.imgMainSelects[int1] = null;
                                int2 = Integer.parseInt(myImage.getName().substring(3));
                                final WeaponItem weaponItem = new WeaponItem(StrHandle.get("ren", int2), String.format("ren%02d", int2));
                                weaponItem.addListener(WeaponScreen.this.mainIdleListener);
                                WeaponScreen.this.arrMainIdle.add(weaponItem);
                                WeaponScreen.this.tableMainPack();
                                int2 = int1;
                                b2 = b2;
                                break;
                            }
                            break;
                        }
                        else {
                            ++int1;
                        }
                    }
                    if (b2) {
                        int1 = Integer.parseInt(listenerActor.getName().substring(3));
                        final MyImage addItem = UiHandle.addItem(WeaponScreen.this.stage.getRoot(), Assets.atlasUiGame, StrHandle.get("ren", int1), int2 * 92 + 221, 7.0f, WeaponScreen.this.probSeListener, listenerActor.getName() + "_prob");
                        WeaponScreen.this.arrMainSelect.add(addItem);
                        WeaponScreen.this.imgMainSelects[int2] = addItem;
                        listenerActor.remove();
                        WeaponScreen.this.groupProba.setPack(false);
                        WeaponScreen.this.tableMainPack();
                        WeaponScreen.this.showMainGun();
                        WeaponScreen.this.showMainShake(int2);
                        if (WeaponScreen.this.helpBoard != null && WeaponScreen.this.helpBoard.insStep == 62) {
                            WeaponScreen.this.helpBoard.remove();
//                            WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, "Battle start!", -1, 1);
                            WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, Language.battleStart(), -1, 1);
                        }
                    }
                }
            }
        };
        this.subOverlap = false;
        this.subIdleListener = new InputListener() {
            boolean isOut = false;
            float lastX;
            float lastY;
            Vector2 pos = new Vector2();
            float startX;
            float startY;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, int int1, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastY = n2;
                this.isOut = false;
                WeaponScreen.this.resetSubOverlap();
                WeaponScreen.this.initDown(listenerActor, false);
                WeaponScreen.this.touchIdleWeapon((WeaponItem)listenerActor);
                int1 = Integer.parseInt(listenerActor.getName().substring(2));
                WeaponScreen.this.infoBoard.setWeaponName(WeaponScreen.strSubs[int1 - 1]);
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, int int1) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (Math.abs(lastY - this.startY) > 18.0f) {
                    WeaponScreen.this.isDown = false;
                }
                if (WeaponScreen.this.panSub.isOut && !WeaponScreen.this.isDown) {
                    this.isOut = true;
                }
                if (!this.isOut && Math.abs(lastX - this.startX) > 25.0f) {
                    this.isOut = true;
                    WeaponScreen.this.panSub.isOut = true;
                    listenerActor.localToStageCoordinates(this.pos.set(5.0f, 0.0f));
                    int1 = Integer.parseInt(listenerActor.getName().substring(2));
                    WeaponScreen.this.imgSelect.setRegion(Assets.atlasWeaponSelect.findRegion(StrHandle.get("fu", int1)));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    WeaponScreen.this.imgSelect.toFront();
                    WeaponScreen.this.imgSelect.setVisible(true);
                    WeaponScreen.this.showSubShake();
                }
                if (this.isOut && (Math.abs(lastX - this.lastX) > 6.0f || Math.abs(lastY - this.lastY) > 6.0f)) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX + 5.0f - this.startX, lastY - this.startY));
                    WeaponScreen.this.imgSelect.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkSubOverlap(WeaponScreen.this.imgSelect);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int int1, final int n3) {
                SoundHandle.playForButton3();
                WeaponScreen.this.hideSubShake();
                WeaponScreen.this.imgSelect.setVisible(false);
                WeaponScreen.this.resetDown();
                if (this.isOut) {
                    WeaponItem listenerActor;
                    final Actor actor = listenerActor = (WeaponItem)inputEvent.getListenerActor();
                    if (WeaponScreen.this.helpBoard != null && (listenerActor = (WeaponItem)actor) == WeaponScreen.this.helpBoard.tempTarget) {
                        listenerActor = WeaponScreen.this.arrSubIdle.get(0);
                    }
                    if (isImgOverlap(WeaponScreen.this.imgSubEmpty, WeaponScreen.this.imgSelect)) {
                        if (WeaponScreen.this.imgSubSelect != null) {
                            final MyImage imgSubSelect = WeaponScreen.this.imgSubSelect;
                            imgSubSelect.remove();
                            WeaponScreen.this.arrSubSelect.removeValue(imgSubSelect, true);
                            WeaponScreen.this.showSubGun();
                            WeaponScreen.this.imgSubSelect = null;
                            int1 = Integer.parseInt(imgSubSelect.getName().substring(2));
                            final WeaponItem weaponItem = new WeaponItem(StrHandle.get("fu", int1), String.format("fu%d", int1));
                            weaponItem.addListener(WeaponScreen.this.subIdleListener);
                            WeaponScreen.this.arrSubIdle.add(weaponItem);
                            WeaponScreen.this.tableSubPack();
                        }
                        WeaponScreen.this.selectSubImg((WeaponItem)listenerActor);
                        if (WeaponScreen.this.helpBoard != null && WeaponScreen.this.helpBoard.insStep == 92) {
                            WeaponScreen.this.helpBoard.remove();
//                            WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, "Battle start!", -1, 1);
                            WeaponScreen.this.helpBoard = HelpBoard.createHelpBoard(WeaponScreen.this.stage, WeaponScreen.this.imgPlay, Language.battleStart(), -1, 1);
                        }
                    }
                }
            }
        };
        this.mainSelListener = new InputListener() {
            float lastX;
            float lastY;
            Vector2 pos = new Vector2();
            float startX;
            float startY;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, int index, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastY = n2;
                index = WeaponScreen.this.arrMainSelect.indexOf((MyImage)listenerActor, true);
                if (index >= 0) {
                    WeaponScreen.this.arrMainSelect.swap(index, WeaponScreen.this.arrMainSelect.size - 1);
                }
                WeaponScreen.this.showMainGun();
                WeaponScreen.this.showAllMainShake();
                listenerActor.toFront();
                WeaponScreen.this.resetOverlap();
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, final int n) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (Math.abs(lastX - this.lastX) > 5.0f || Math.abs(lastY - this.lastY) > 5.0f) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX - this.startX, lastY - this.startY));
                    listenerActor.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkOverlap(listenerActor);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int int1, int n3) {
                SoundHandle.playForButton3();
                final Actor listenerActor = inputEvent.getListenerActor();
                final int index = MyMethods.findIndex(WeaponScreen.this.imgMainSelects, listenerActor, true);
                WeaponScreen.this.hideMainShake();
                final int n4 = 0;
                int1 = 0;
                while (true) {
                    n3 = n4;
                    if (int1 >= WeaponScreen.this.imgMainEmptys.length) {
                        break;
                    }
                    if (isImgOverlap(WeaponScreen.this.imgMainEmptys[int1], listenerActor)) {
                        final int n5 = 1;
                        listenerActor.setPosition(int1 * 92 + 221, 7.0f);
                        WeaponScreen.this.showMainShake(int1);
                        n3 = n5;
                        if (index >= 0) {
                            if (WeaponScreen.this.imgMainSelects[int1] != null) {
                                WeaponScreen.this.imgMainSelects[int1].setPosition(index * 92 + 221, 7.0f);
                            }
                            final MyImage myImage = WeaponScreen.this.imgMainSelects[int1];
                            WeaponScreen.this.imgMainSelects[int1] = WeaponScreen.this.imgMainSelects[index];
                            WeaponScreen.this.imgMainSelects[index] = myImage;
                            n3 = n5;
                            break;
                        }
                        break;
                    }
                    else {
                        ++int1;
                    }
                }
                if (n3 == 0 && index >= 0) {
                    listenerActor.remove();
                    WeaponScreen.this.arrMainSelect.removeValue((MyImage)listenerActor, true);
                    WeaponScreen.this.imgMainSelects[index] = null;
                    WeaponScreen.this.showMainGun();
                    int1 = Integer.parseInt(listenerActor.getName().substring(3));
                    final WeaponItem weaponItem = new WeaponItem(StrHandle.get("ren", int1), String.format("ren%02d", int1));
                    weaponItem.addListener(WeaponScreen.this.mainIdleListener);
                    WeaponScreen.this.arrMainIdle.add(weaponItem);
                    WeaponScreen.this.tableMainPack();
                    if (HelpBoard.getInsStep(WeaponScreen.this.helpBoard) == 61) {
                        WeaponScreen.this.helpBoard.remove();
                        WeaponScreen.this.addInstruction(62);
                    }
                }
            }
        };
        this.probSeListener = new InputListener() {
            float lastX;
            float lastY;
            Vector2 pos = new Vector2();
            float startX;
            float startY;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, int index, final int n3) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastY = n2;
                WeaponScreen.this.showAllMainShake();
                index = WeaponScreen.this.arrMainSelect.indexOf((MyImage)listenerActor, true);
                if (index >= 0) {
                    WeaponScreen.this.arrMainSelect.swap(index, WeaponScreen.this.arrMainSelect.size - 1);
                }
                WeaponScreen.this.showMainGun();
                listenerActor.toFront();
                WeaponScreen.this.resetOverlap();
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, final int n) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (Math.abs(lastX - this.lastX) > 5.0f || Math.abs(lastY - this.lastY) > 5.0f) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX - this.startX, lastY - this.startY));
                    listenerActor.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkOverlap(listenerActor);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int n3, int n4) {
                SoundHandle.playForButton3();
                final Actor listenerActor = inputEvent.getListenerActor();
                final int index = MyMethods.findIndex(WeaponScreen.this.imgMainSelects, listenerActor, true);
                WeaponScreen.this.hideMainShake();
                final int n5 = 0;
                n3 = 0;
                while (true) {
                    n4 = n5;
                    if (n3 >= WeaponScreen.this.imgMainEmptys.length) {
                        break;
                    }
                    if (isImgOverlap(WeaponScreen.this.imgMainEmptys[n3], listenerActor)) {
                        final int n6 = 1;
                        listenerActor.setPosition(n3 * 92 + 221, 7.0f);
                        WeaponScreen.this.showMainShake(n3);
                        n4 = n6;
                        if (index >= 0) {
                            if (WeaponScreen.this.imgMainSelects[n3] != null) {
                                WeaponScreen.this.imgMainSelects[n3].setPosition(index * 92 + 221, 7.0f);
                            }
                            final MyImage myImage = WeaponScreen.this.imgMainSelects[n3];
                            WeaponScreen.this.imgMainSelects[n3] = WeaponScreen.this.imgMainSelects[index];
                            WeaponScreen.this.imgMainSelects[index] = myImage;
                            n4 = n6;
                            break;
                        }
                        break;
                    }
                    else {
                        ++n3;
                    }
                }
                if (n4 == 0 && index >= 0) {
                    listenerActor.remove();
                    WeaponScreen.this.arrMainSelect.removeValue((MyImage)listenerActor, true);
                    if (index >= 0) {
                        WeaponScreen.this.imgMainSelects[index] = null;
                    }
                    WeaponScreen.this.showMainGun();
                    WeaponScreen.this.groupProba.setPack(true);
                    WeaponScreen.this.tableMainPack();
                }
            }
        };
        this.subSelListener = new InputListener() {
            float lastX;
            float lastY;
            Vector2 pos = new Vector2();
            float startX;
            float startY;
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                final Actor listenerActor = inputEvent.getListenerActor();
                this.startX = n;
                this.lastX = n;
                this.startY = n2;
                this.lastY = n2;
                WeaponScreen.this.showSubShake();
                listenerActor.toFront();
                WeaponScreen.this.resetSubOverlap();
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float lastX, final float lastY, final int n) {
                final Actor listenerActor = inputEvent.getListenerActor();
                if (Math.abs(lastX - this.lastX) > 5.0f || Math.abs(lastY - this.lastY) > 5.0f) {
                    listenerActor.localToStageCoordinates(this.pos.set(lastX - this.startX, lastY - this.startY));
                    listenerActor.setPosition(this.pos.x, this.pos.y);
                    this.lastX = lastX;
                    this.lastY = lastY;
                    WeaponScreen.this.checkSubOverlap(listenerActor);
                }
            }
            
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, int int1, final int n3) {
                SoundHandle.playForButton3();
                final Actor listenerActor = inputEvent.getListenerActor();
                if (!isImgOverlap(WeaponScreen.this.imgSubEmpty, listenerActor)) {
                    listenerActor.remove();
                    WeaponScreen.this.arrSubSelect.removeValue((MyImage)listenerActor, true);
                    WeaponScreen.this.imgSubSelect = null;
                    WeaponScreen.this.showSubGun();
                    int1 = Integer.parseInt(listenerActor.getName().substring(2));
                    final WeaponItem weaponItem = new WeaponItem(StrHandle.get("fu", int1), String.format("fu%d", int1));
                    weaponItem.addListener(WeaponScreen.this.subIdleListener);
                    WeaponScreen.this.arrSubIdle.add(weaponItem);
                    WeaponScreen.this.tableSubPack();
                    return;
                }
                WeaponScreen.this.imgSubSelect.setPosition(507.0f, 7.0f);
                WeaponScreen.this.showSubGun();
            }
        };
        this.atlasScene = Global.manager.get("scene/scene" + Global.sceneLevel + ".pack", TextureAtlas.class);
        this.atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        this.atlasWeaponBg = Global.manager.get("ui/ui_weapon_bg.pack", TextureAtlas.class);
        this.atlasLevelSelect = Global.manager.get("ui/ui_level_select.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.stage = new Stage(800.0f, 480.0f, false, Global.batch);
        this.setRegion();
        this.init();
        BaseScreen.addFadeInAction(this.stage, 0.3f);
        this.multiplexer.addProcessor(0, this.stage);
    }
    
    private void addInstruction(final int insStep) {
        if (insStep == 11) {
            int maxGameLevelEasy;
            if (Global.maxGameLevelEasy == 4) {
                maxGameLevelEasy = 3;
            }
            else {
                maxGameLevelEasy = Global.maxGameLevelEasy;
            }
            int n2;
            final int n = n2 = 0;
            if (Global.maxGameLevelEasy == 1) {
                n2 = n;
                if (this.arrMainIdle.size > 0) {
                    n2 = n;
                    if (this.arrMainIdle.get(0).getName().equals("ren01")) {
                        n2 = 1;
                    }
                }
            }
//            final String s = "Tap and hold to select the \nweapon!";
            final String s = Language.tapAndSelectWeapon();
            int n3;
            String s2;
            if (Global.maxGameLevelEasy == 2) {
                n3 = n2;
                s2 = s;
                if (this.arrMainIdle.size > 0) {
                    n3 = n2;
                    s2 = s;
                    if (this.arrMainIdle.get(0).getName().equals("ren02")) {
//                        s2 = "Tap and hold to add artillery.";
                        s2 = Language.tapToAddArtillery();
                        n3 = 1;
                    }
                }
            }
            else {
                n3 = n2;
                s2 = s;
                if (Global.maxGameLevelEasy == 4) {
                    n3 = n2;
                    s2 = s;
                    if (this.arrMainIdle.size > 0) {
                        n3 = n2;
                        s2 = s;
                        if (this.arrMainIdle.get(0).getName().equals("ren03")) {
//                            s2 = "Tap to arm with shotgun. \n(Designed for attacking \nairforce)";
                            s2 = Language.tapToArm();
                            n3 = 1;
                        }
                    }
                }
            }
            if (n3 != 0) {
                final WeaponItem tempTarget = new WeaponItem(StrHandle.get("ren", maxGameLevelEasy), String.format("ren%02d", maxGameLevelEasy));
                tempTarget.setPosition(172.0f, 254.0f);
                tempTarget.addListener(this.mainIdleListener);
                this.stage.addActor(tempTarget);
                this.helpBoard = HelpBoard.createHelpBoard(this.stage, tempTarget, s2, 1, 1);
                this.helpBoard.tempTarget = tempTarget;
                this.helpBoard.imgHand.clearActions();
                this.helpBoard.imgHand.addAction(Actions.forever(Actions.sequence(Actions.delay(0.3f), Actions.moveTo(this.arrMainSelect.size * 92 + 245, -35.0f, 1.2f), Actions.delay(0.2f), Actions.moveTo(197.0f, 206.0f, 0.6f))));
                this.helpBoard.insStep = insStep;
            }
        }
        if (insStep == 61) {
//            this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.arrMainSelect.get(2), "Tap and hold to remove the \nweapon!", -1, 1);
            this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.arrMainSelect.get(2), Language.tapAndRemoveWeapon(), -1, 1);
            this.helpBoard.imgHand.clearActions();
            this.helpBoard.imgHand.addAction(Actions.forever(Actions.sequence(Actions.delay(0.3f), Actions.moveTo(510.0f, 180.0f, 1.2f), Actions.delay(0.2f), Actions.moveTo(424.0f, -25.0f, 0.6f))));
            this.helpBoard.insStep = insStep;
        }
        else {
            if (insStep == 62) {
//                this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupProba, "Equip this powerful weapon \nfor every 30 minutes!", 1, 1);
                this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.groupProba, Language.equipWeapon(), 1, 1);
                this.helpBoard.imgHand.clearActions();
                this.helpBoard.imgHand.addAction(Actions.forever(Actions.sequence(Actions.delay(0.3f), Actions.moveTo(this.arrMainSelect.size * 92 + 245, -35.0f, 1.2f), Actions.delay(0.2f), Actions.moveTo(68.0f, 100.0f, 0.6f))));
                this.helpBoard.insStep = insStep;
                return;
            }
            if (insStep == 91) {
//                this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.imgSubMark, "Touch to switch to \nlight weapon label!", 1, -1);
                this.helpBoard = HelpBoard.createHelpBoard(this.stage, this.imgSubMark, Language.touchToLightLabel(), 1, -1);
                this.helpBoard.insStep = insStep;
                return;
            }
            if (insStep == 92) {
                int n4;
                if (Global.maxGameLevelEasy == 9) {
                    n4 = 3;
                }
                else {
                    n4 = 1;
                }
                final WeaponItem tempTarget2 = new WeaponItem(StrHandle.get("fu", n4), String.format("fu%d", n4));
                tempTarget2.setPosition(172.0f, 254.0f);
                tempTarget2.addListener(this.subIdleListener);
                this.stage.addActor(tempTarget2);
//                this.helpBoard = HelpBoard.createHelpBoard(this.stage, tempTarget2, "Tap and hold to select the \nweapon!", 1, 1);
                this.helpBoard = HelpBoard.createHelpBoard(this.stage, tempTarget2, Language.tapAndSelectWeapon(), 1, 1);
                this.helpBoard.tempTarget = tempTarget2;
                this.helpBoard.imgHand.clearActions();
                this.helpBoard.imgHand.addAction(Actions.forever(Actions.sequence(Actions.delay(0.3f), Actions.moveTo(520.0f, -40.0f, 1.2f), Actions.delay(0.2f), Actions.moveTo(197.0f, 206.0f, 0.6f))));
                this.helpBoard.insStep = insStep;
            }
        }
    }
    
    private void bigMainShake(final int n) {
        this.imgMainShakes[n].clearActions();
        this.imgMainShakes[n].setScale(1.2f);
        this.imgMainShakes[n].addAction(BlinkAction.scaleUpDown(1.15f, 1.35f, 0.3f));
    }
    
    private void bigSubShake() {
        this.imgSubShake.clearActions();
        this.imgSubShake.setScale(1.2f);
        this.imgSubShake.addAction(BlinkAction.scaleUpDown(1.15f, 1.35f, 0.3f));
    }
    
    private void checkDown(final float n) {
        if (this.isDown) {
            this.downTime += n;
            if (this.downTime > 0.25f) {
                this.isLongTouch = true;
                this.startOut(this.touchActor, this.isTouchMain);
            }
        }
    }
    
    private void checkOverlap(final Actor actor) {
        for (int i = 0; i < this.imgMainEmptys.length; ++i) {
            final boolean imgOverlap = isImgOverlap(this.imgMainEmptys[i], actor);
            if (this.overlaps[i] != imgOverlap) {
                this.overlaps[i] = imgOverlap;
                if (imgOverlap) {
                    this.bigMainShake(i);
                }
                else {
                    this.normalMainShake(i);
                }
            }
        }
    }
    
    private void checkSubOverlap(final Actor actor) {
        final boolean imgOverlap = isImgOverlap(this.imgSubEmpty, actor);
        if (this.subOverlap != imgOverlap) {
            this.subOverlap = imgOverlap;
            if (!this.subOverlap) {
                this.normalSubShake();
                return;
            }
            this.bigSubShake();
        }
    }
    
    private int getMainValidSize() {
        int n = 0;
        int n2;
        for (int i = 0; i < this.arrMainIdle.size; ++i, n = n2) {
            n2 = n;
            if (this.arrMainIdle.get(i).getName().contains("ren")) {
                n2 = n + 1;
            }
        }
        return n;
    }
    
    private int getSubValidSize() {
        int n = 0;
        int n2;
        for (int i = 0; i < this.arrSubIdle.size; ++i, n = n2) {
            n2 = n;
            if (this.arrSubIdle.get(i).getName().contains("fu")) {
                n2 = n + 1;
            }
        }
        return n;
    }
    
    private void hideMainShake() {
        for (int i = 0; i < this.imgMainShakes.length; ++i) {
            this.imgMainShakes[i].setVisible(false);
        }
    }
    
    private void hideSubShake() {
        this.imgSubShake.setVisible(false);
    }
    
    private void init() {
        this.arrMainIdle = new Array<WeaponItem>();
        this.arrSubIdle = new Array<WeaponItem>();
        this.arrMainSelect = new Array<MyImage>();
        this.arrSubSelect = new Array<MyImage>();
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangzuo", 0.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangyou", 755.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangshang", 44.0f, 463.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "waikuangxia", 44.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "team", 0.0f, 0.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "waikuang1", 142.0f, 144.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "waikuang2", 554.0f, 153.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "hengfu", 229.0f, 349.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "anniudi", 11.0f, 3.0f);
        UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "anniudi", 689.0f, 3.0f);
        this.imgMainMark = UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "mainkai", 30.0f, 310.0f, this.btnListener);
        this.imgSubMark = UiHandle.addItem(this.stage.getRoot(), this.atlasWeaponBg, "lightguan", 30.0f, 245.0f, this.btnListener);
        this.imgProfile = UiHandle.addItem(this.stage.getRoot(), Assets.atlasUiGame, "ren0", 569.0f, 269.0f, this.btnListener);
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this.stage, this.btnListener);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this.stage, this.btnListener);
        this.imgStore = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shangdian", 643.0f, 401.0f, this.btnListener);
        this.imgHero = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "renwu", 103.0f, 401.0f, this.btnListener);
        this.imgEnemyKill = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "chengjiu", 26.0f, 401.0f, this.btnListener);
        this.imgSet = UiHandle.addItem(this.stage.getRoot(), this.atlasLevelSelect, "shezhi", 718.0f, 401.0f, this.btnListener);
        this.dialogHero = new DialogHero();
        this.dialogEnemyKill = new DialogEnemyKill();
        this.dialogSet = new DialogSet();
        this.dialogStore = new DialogStore(this, this.groupCoinHead, this.groupMondHead);
        (this.imgNoweapon = this.addItem("noweapon", 250.0f, 100.0f)).setVisible(false);
        this.imgMainSelects = new MyImage[3];
        for (int i = 0; i < this.imgMainSelects.length; ++i) {
            this.imgMainSelects[i] = null;
        }
        this.imgMainEmptys = new MyImage[3];
        for (int j = 0; j < this.imgMainEmptys.length; ++j) {
            this.imgMainEmptys[j] = UiHandle.addItem(this.stage.getRoot(), Assets.atlasUiGame, "ren0", j * 92 + 221, 6.0f);
        }
        this.imgSubEmpty = this.addItem("fu0", 507.0f, 7.0f);
        this.imgMainShakes = new MyImage[3];
        for (int k = 0; k < this.imgMainShakes.length; ++k) {
            MyMethods.setActorOrigin(this.imgMainShakes[k] = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "guang1", k * 92 + 215, 1.0f), 0.5f, 0.5f);
            this.imgMainShakes[k].addAction(BlinkAction.scaleUpDown(0.98f, 1.05f, 0.5f));
            this.imgMainShakes[k].setVisible(false);
            this.imgMainShakes[k].setTouchable(Touchable.disabled);
        }
        this.imgSubSelect = null;
        MyMethods.setActorOrigin(this.imgSubShake = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "guang2", 501.0f, 2.0f), 0.5f, 0.5f);
        this.imgSubShake.addAction(BlinkAction.scaleUpDown(0.98f, 1.05f, 0.5f));
        this.imgSubShake.setVisible(false);
        this.imgSubShake.setTouchable(Touchable.disabled);
        this.imgBack = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "fanhui", 22.0f, 8.0f, this.btnListener);
        this.imgPlay = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "jingong", 701.0f, 8.0f, this.btnListener);
        (this.imgSelect = UiHandle.addItem(this.stage.getRoot(), Assets.atlasUiGame, "ren1", 0.0f, 0.0f)).setVisible(false);
        this.infoBoard = new InfoBoard(this.stage);
        this.initWeapon();
        (this.infoSub = AutoLineLabel.createRoman(this.stage.getRoot(), "Recover more energy instantly by using skills.", 395.0f, 100.0f, 250.0f)).setVisible(false);
        this.imgUpgrade = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, "upgrade", 592.0f, 137.0f, this.btnListener);
        if (Global.maxGameLevelEasy == Global.curGameLevelEasy) {
            if ((Global.maxGameLevelEasy == 1 || Global.maxGameLevelEasy == 2 || Global.maxGameLevelEasy == 4) && this.getMainValidSize() == 1) {
                this.addInstruction(11);
            }
            if (Global.maxGameLevelEasy == 6 && this.groupProba != null && this.groupProba.isValid()) {
                if (this.arrMainSelect.size == 3) {
                    this.addInstruction(61);
                }
                else {
                    this.addInstruction(62);
                }
            }
            if (Global.maxGameLevelEasy == 9 && Global.arrSubGunGet.contains("Twine", false) && this.arrSubIdle.size > 0 && this.getSubValidSize() == 1) {
                this.addInstruction(91);
            }
        }
    }
    
    private void initDown(final Actor touchActor, final boolean isTouchMain) {
        this.isLongTouch = false;
        this.isDown = true;
        this.downTime = 0.0f;
        this.touchActor = touchActor;
        this.isTouchMain = isTouchMain;
    }
    
    private void initWeapon() {
        this.initArrMainIdle();
        this.initPanMain();
        this.tableMainPack();
        this.initArrSubIdle();
        this.initPanSub();
        this.tableSubPack();
        this.selectItem(true);
        if (Global.maxGameLevelEasy != 1) {
            this.readWeaponSelect();
        }
    }
    
    private static boolean isCreateProba() {
        if (Global.maxGameLevelEasy >= 6) {
            final int n = -1;
            int n2 = 7;
            int n3;
            while (true) {
                n3 = n;
                if (n2 >= Constant.strMainOrders.length) {
                    break;
                }
                if (!Global.arrMainGunGet.contains(Constant.strMainOrders[n2], false)) {
                    n3 = MyMethods.findIndex(WeaponScreen.strMains, Constant.strMainOrders[n2]) + 1;
                    break;
                }
                ++n2;
            }
            if (n3 >= 0) {
                return true;
            }
        }
        return false;
    }
    
    private static boolean isImgOverlap(final Actor actor, final Actor actor2) {
        final float x = actor.getX();
        final float n = actor.getWidth() / 2.0f;
        final float y = actor.getY();
        final float n2 = actor.getHeight() / 2.0f;
        final float x2 = actor2.getX();
        final float n3 = actor2.getWidth() / 2.0f;
        final float y2 = actor2.getY();
        final float n4 = actor2.getHeight() / 2.0f;
        return Math.abs(x + n - (x2 + n3)) < 35.0f && Math.abs(y + n2 - (y2 + n4)) < 40.0f;
    }
    
    private void normalMainShake(final int n) {
        this.imgMainShakes[n].clearActions();
        this.imgMainShakes[n].setScale(0.98f);
        this.imgMainShakes[n].addAction(BlinkAction.scaleUpDown(0.98f, 1.05f, 0.5f));
    }
    
    private void normalSubShake() {
        this.imgSubShake.clearActions();
        this.imgSubShake.setScale(0.98f);
        this.imgSubShake.addAction(BlinkAction.scaleUpDown(0.98f, 1.05f, 0.5f));
    }
    
    private void playHandle() {
    	if (Global.gameCount > 0 && Global.gameCount % 4 == 0 && DialogGiftBox.HasGifts() > 0) {
    		DialogGiftBox gift = new DialogGiftBox(this);
    		gift.openDialog(this.stage);
    		return;
    	}
    	++Global.gameCount;
    	
        this.writeConfig();
        if (this.arrMainSelect.size > 0) {
            Global.gameState = Constant.GameState.Game_On;
            Global.preScreen = Constant.NextScreen.Weapon_Screen;
            Global.nextScreen = Constant.NextScreen.Game_Screen;
            BaseScreen.addFadeOutAction(this.stage, 0.2f);
            Global.inCoinGet = 0;
//            if (Global.isEndlessMode) {
//                FlurryHandle.endlessPlay(Constant.PlayState.Play, Global.maxGameLevelEasy);
//            }
//            else if (Global.gameMode == Constant.GameMode.Easy) {
//                FlurryHandle.levelChallengeEasy(Global.curGameLevelEasy, Constant.PlayState.Play);
//            }
//            else if (Global.gameMode == Constant.GameMode.Hard) {
//                FlurryHandle.levelChallengeHard(Global.curGameLevelHard, Constant.PlayState.Play);
//            }
        }
        else {
            this.imgNoweapon.clearActions();
            this.imgNoweapon.setVisible(true);
            this.imgNoweapon.setColor(Color.WHITE);
            this.imgNoweapon.setY(100.0f);
            this.imgNoweapon.toFront();
            this.imgNoweapon.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0.0f, 150.0f, 1.0f), Actions.sequence(Actions.delay(0.5f, Actions.fadeOut(0.5f)))), Actions.visible(false)));
        }
        if (this.groupProba != null && !this.groupProba.isPack) {
            this.groupProba.labelTime.writeProbaTime();
        }
    }
    
    private void resetDown() {
        this.isDown = false;
        this.downTime = 0.0f;
        this.isLongTouch = false;
    }
    
    private void resetOverlap() {
        for (int i = 0; i < this.overlaps.length; ++i) {
            this.overlaps[i] = false;
        }
    }
    
    private void resetSubOverlap() {
        this.subOverlap = false;
    }
    
    private void selectItem(final boolean b) {
        if (b) {
            this.imgMainMark.setRegion(this.atlasWeaponBg.findRegion("mainkai"));
            this.imgSubMark.setRegion(this.atlasWeaponBg.findRegion("lightguan"));
            this.panMain.setVisible(true);
            this.panSub.setVisible(false);
            this.showMainGun();
            return;
        }
        this.imgMainMark.setRegion(this.atlasWeaponBg.findRegion("mainguan"));
        this.imgSubMark.setRegion(this.atlasWeaponBg.findRegion("lightkai"));
        this.panMain.setVisible(false);
        this.panSub.setVisible(true);
        this.showSubGun();
    }
    
    private void selectMainImg(final WeaponItem weaponItem, final int n) {
        final MyImage addItem = UiHandle.addItem(this.stage.getRoot(), Assets.atlasUiGame, StrHandle.get("ren", Integer.parseInt(weaponItem.getName().substring(3))), n * 92 + 221, 7.0f, this.mainSelListener, weaponItem.getName());
        this.arrMainSelect.add(addItem);
        this.imgMainSelects[n] = addItem;
        this.arrMainIdle.removeValue(weaponItem, true);
        weaponItem.remove();
        this.tableMainPack();
        this.showMainGun();
    }
    
    private void selectSubImg(final WeaponItem weaponItem) {
        final MyImage addItem = UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, StrHandle.get("fu", Integer.parseInt(weaponItem.getName().substring(2))), 507.0f, 7.0f, this.subSelListener, weaponItem.getName());
        weaponItem.remove();
        this.arrSubIdle.removeValue(weaponItem, true);
        this.arrSubSelect.add(addItem);
        this.imgSubSelect = addItem;
        this.tableSubPack();
        this.showSubGun();
    }
    
    private void setCurWeapon(final String strCurWeapon) {
        this.strCurWeapon = strCurWeapon;
        this.infoBoard.setWeaponName(this.strCurWeapon);
    }
    
    private void setRegion() {
        final int sceneLevel = Global.sceneLevel;
        this.backScene = new BackScene();
    }
    
    private void showAllMainShake() {
        for (int i = 0; i < this.imgMainShakes.length; ++i) {
            this.imgMainShakes[i].setVisible(true);
            this.imgMainShakes[i].toFront();
            this.normalMainShake(i);
        }
        if (this.imgSelect != null) {
            this.imgSelect.toFront();
        }
    }
    
    private void showInfoSub(final String s) {
        this.infoSub.clearActions();
        this.infoSub.toFront();
        this.infoSub.setY(100.0f);
        this.infoSub.setVisible(true);
        this.infoSub.setColor(Color.WHITE);
        this.infoSub.setInfo(Assets.jsonEnhance.get(s).getString("selInfo"));
        this.infoSub.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0.0f, 200.0f, 2.0f), Actions.sequence(Actions.delay(1.0f), Actions.fadeOut(1.0f))), Actions.visible(false)));
    }
    
    private void showMainShake(final int n) {
        this.imgMainShakes[n].setVisible(true);
        this.imgMainShakes[n].toFront();
        this.normalMainShake(n);
        if (this.imgSelect != null) {
            this.imgSelect.toFront();
        }
    }
    
    private void showSubShake() {
        this.imgSubShake.setVisible(true);
        this.imgSubShake.toFront();
        this.normalSubShake();
        if (this.imgSelect != null) {
            this.imgSelect.toFront();
        }
    }
    
    private void startOut(final Actor actor, final boolean b) {
        final Vector2 vector2 = new Vector2();
        if (b) {
            this.panMain.isOut = true;
            actor.localToStageCoordinates(vector2.set(15.0f, 0.0f));
            this.imgSelect.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", Integer.parseInt(actor.getName().substring(3)))));
            this.imgSelect.setPosition(vector2.x, vector2.y);
            this.imgSelect.toFront();
            this.imgSelect.setVisible(true);
            this.showAllMainShake();
        }
        else {
            this.panSub.isOut = true;
            actor.localToStageCoordinates(vector2.set(15.0f, 0.0f));
            this.imgSelect.setRegion(Assets.atlasWeaponSelect.findRegion(StrHandle.get("fu", Integer.parseInt(actor.getName().substring(2)))));
            this.imgSelect.setPosition(vector2.x, vector2.y);
            this.imgSelect.toFront();
            this.imgSelect.setVisible(true);
            this.showSubShake();
        }
        this.isDown = false;
    }
    
    private void touchIdleWeapon(final WeaponItem curItem) {
        if (this.curItem != null) {
            this.curItem.hideShake();
        }
        this.hideMainShake();
        this.hideSubShake();
        (this.curItem = curItem).showShake();
    }
    
    private void touchSelWeapon(final MyImage myImage) {
        if (this.curItem != null) {
            this.curItem.hideShake();
            this.curItem = null;
        }
        this.hideMainShake();
        this.hideSubShake();
        for (int i = 0; i < this.imgMainSelects.length; ++i) {
            if (myImage == this.imgMainSelects[i]) {
                this.showMainShake(i);
            }
        }
        if (myImage == this.imgSubSelect) {
            this.showSubShake();
        }
    }
    
    public MyImage addItem(final String s, final float n, final float n2) {
        return UiHandle.addItem(this.stage.getRoot(), Assets.atlasWeaponSelect, s, n, n2);
    }
    
    public void arrMainSelectPack() {
        for (int i = 0; i < this.arrMainSelect.size; ++i) {
            this.arrMainSelect.get(i).setPosition(i * 92 + 208, 19.0f);
        }
        for (int j = 0; j < this.imgMainEmptys.length; ++j) {
            this.imgMainEmptys[j].setVisible(j >= this.arrMainSelect.size);
        }
    }
    
    @Override
    public void dispose() {
        super.dispose();
        this.stage.clear();
        this.stage.dispose();
        if (Global.nextScreen == Constant.NextScreen.Game_Screen) {
            Control.unloadForMenu();
            System.gc();
        }
    }
    
    public void initArrMainIdle() {
        this.arrMainIdle.clear();
        if (isCreateProba()) {
            this.groupProba = new GroupProba();
        }
        for (int i = 0; i < Global.arrMainGunGet.size; ++i) {
            final int index = MyMethods.findIndex(WeaponScreen.strMains, Global.arrMainGunGet.get(i));
            final WeaponItem weaponItem = new WeaponItem(StrHandle.get("ren", index + 1), String.format("ren%02d", index + 1));
            weaponItem.addListener(this.mainIdleListener);
            this.arrMainIdle.add(weaponItem);
        }
        for (int j = Global.arrMainGunGet.size; j < 13; ++j) {
            this.arrMainIdle.add(new WeaponItem("empty", String.format("zero%02d", j + 1)));
        }
    }
    
    public void initArrSubIdle() {
        this.arrSubIdle.clear();
        for (int i = 0; i < Global.arrSubGunGet.size; ++i) {
            final int index = MyMethods.findIndex(WeaponScreen.strSubs, Global.arrSubGunGet.get(i));
            final WeaponItem weaponItem = new WeaponItem(StrHandle.get("fu", index + 1), String.format("fu%d", index + 1));
            weaponItem.addListener(this.subIdleListener);
            this.arrSubIdle.add(weaponItem);
        }
        for (int j = Global.arrSubGunGet.size; j < 7; ++j) {
            this.arrSubIdle.add(new WeaponItem("empty", String.format("zero%d", j + 1)));
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
        this.panMain.setSize(350.0f, 158.0f);
        this.panMain.setPosition(148.0f, 180.0f);
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
        this.panSub.setSize(350.0f, 158.0f);
        this.panSub.setPosition(148.0f, 180.0f);
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
        this.writeConfig();
        Global.preScreen = Constant.NextScreen.Weapon_Screen;
        Constant.NextScreen nextScreen;
        if (Global.isEndlessMode) {
            nextScreen = Constant.NextScreen.Start_Screen;
        }
        else {
            nextScreen = Constant.NextScreen.Level_Small;
        }
        Global.nextScreen = nextScreen;
        BaseScreen.addFadeOutAction(this.stage, 0.2f);
    }
    
    public void readWeaponSelect() {
        for (int i = 0; i < Global.arrStrMainSelect.size; ++i) {
            final String format = String.format("ren%02d", MyMethods.findIndex(WeaponScreen.strMains, Global.arrStrMainSelect.get(i)) + 1);
            for (int j = 0; j < this.arrMainIdle.size; ++j) {
                if (this.arrMainIdle.get(j).getName().equals(format)) {
                    this.selectMainImg(this.arrMainIdle.get(j), i);
                    break;
                }
            }
        }
        if (Global.strSubGun != null && Global.strSubGun != "") {
            final int index = MyMethods.findIndex(WeaponScreen.strSubs, Global.strSubGun);
            if (index != -1) {
                final String format2 = String.format("fu%d", index + 1);
                for (int k = 0; k < this.arrSubIdle.size; ++k) {
                    if (this.arrSubIdle.get(k).getName().equals(format2)) {
                        this.selectSubImg(this.arrSubIdle.get(k));
                        break;
                    }
                }
            }
        }
    }
    
    public void refreshWeapon() {
        for (int i = 0; i < this.imgMainSelects.length; ++i) {
            if (this.imgMainSelects[i] != null) {
                this.imgMainSelects[i].remove();
                this.imgMainSelects[i] = null;
            }
        }
        if (this.imgSubSelect != null) {
            this.imgSubSelect.remove();
            this.imgSubSelect = null;
        }
        this.arrMainSelect.clear();
        this.arrSubSelect.clear();
        if (this.groupProba != null) {
            this.groupProba.remove();
            this.groupProba = null;
        }
        this.initArrMainIdle();
        this.tableMainPack();
        this.initArrSubIdle();
        this.tableSubPack();
        if (Global.maxGameLevelEasy != 1) {
            this.readWeaponSelect();
        }
        this.showMainGun();
        this.showSubGun();
    }
    
    @Override
    public void render(final float n) {
        super.render(n);
        this.backScene.act(n);
        this.backScene.draw();
        this.stage.act();
        this.stage.draw();
        this.showFps(n);
        this.checkDown(n);
    }
    
    @Override
    public void show() {
        super.show();
        PlatformHandle.closeFeatureView();
    }
    
    public void showMainGun() {
        if (this.arrMainSelect.size > 0) {
            final MyImage myImage = this.arrMainSelect.peek();
            this.hideMainShake();
            this.touchSelWeapon(myImage);
            String s2;
            final String s = s2 = myImage.getName();
            if (s.contains("_prob")) {
                s2 = s.substring(0, s.length() - 5);
            }
            this.infoBoard.setWeaponName(WeaponScreen.strMains[Integer.parseInt(s2.substring(3)) - 1]);
            this.infoBoard.setVisible(true);
            return;
        }
        this.infoBoard.hide();
    }
    
    public void showSubGun() {
        this.hideSubShake();
        if (this.arrSubSelect.size > 0) {
            final MyImage myImage = this.arrSubSelect.peek();
            this.touchSelWeapon(myImage);
            this.infoBoard.setWeaponName(WeaponScreen.strSubs[Integer.parseInt(myImage.getName().substring(2)) - 1]);
            this.infoBoard.setVisible(true);
            return;
        }
        this.infoBoard.hide();
    }
    
    public void sortArray(final Array<WeaponItem> array) {
        for (int i = 0; i < array.size - 1; ++i) {
            for (int j = i + 1; j < array.size; ++j) {
                if (array.get(i).getName().compareTo(array.get(j).getName()) > 0) {
                    array.swap(i, j);
                }
            }
        }
    }
    
    public void tableMainPack() {
        this.sortArray(this.arrMainIdle);
        this.table1.clear();
        int n = 0;
        if (this.groupProba != null) {
            this.groupProba.remove();
            if (this.groupProba.isPack) {
                this.stage.addActor(this.groupProba);
            }
        }
        for (int i = 0; i < this.arrMainIdle.size; ++i) {
            this.table1.add(this.arrMainIdle.get(i));
            ++n;
            if (n % 4 == 0 && i < this.arrMainIdle.size) {
                this.table1.row();
            }
        }
    }
    
    public void tableSubPack() {
        this.sortArray(this.arrSubIdle);
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
    
    public void writeConfig() {
        Global.arrStrMainSelect.clear();
        for (int i = 0; i < this.imgMainSelects.length; ++i) {
            if (this.imgMainSelects[i] != null) {
                String s2;
                final String s = s2 = this.imgMainSelects[i].getName();
                if (s.contains("_prob")) {
                    s2 = s.substring(0, s.length() - 5);
                }
                Global.arrStrMainSelect.add(WeaponScreen.strMains[Integer.parseInt(s2.substring(3)) - 1]);
            }
        }
        if (this.arrSubSelect.size > 0) {
            for (int j = 0; j < this.arrSubSelect.size; ++j) {
                Global.strSubGun = WeaponScreen.strSubs[Integer.parseInt(this.arrSubSelect.get(j).getName().substring(2)) - 1];
            }
        }
        else {
            Global.strSubGun = "";
        }
        PreferHandle.writeWeaponSelect();
    }
    
    class GroupProba extends MyGroup
    {
        int gunLevel;
        MyImage imgBg;
        boolean isPack;
        LabelTime labelTime;
        
        public GroupProba() {
            (this.labelTime = new LabelTime()).setPosition(15.0f, 5.0f);
            this.gunLevel = -1;
            for (int i = 7; i < Constant.strMainOrders.length; ++i) {
                if (!Global.arrMainGunGet.contains(Constant.strMainOrders[i], false)) {
                    this.gunLevel = MyMethods.findIndex(WeaponScreen.strMains, Constant.strMainOrders[i]) + 1;
                    break;
                }
            }
            String s;
            if (this.labelTime.isValid()) {
                s = StrHandle.get("shi", this.gunLevel);
            }
            else {
                s = StrHandle.get("shi", this.gunLevel, "_b");
            }
            this.imgBg = UiHandle.addItem(this, Assets.atlasWeaponSelect, s, 0.0f, 0.0f);
            this.addActor(this.labelTime);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
            this.addListener(WeaponScreen.this.probIdleListener);
            this.isPack = (Global.maxGameLevelEasy >= 5);
            this.setName(String.format("ren%02d", this.gunLevel));
            this.setPosition(36.0f, 145.0f);
        }
        
        public int getGunlevel() {
            return this.gunLevel;
        }
        
        public boolean isValid() {
            return this.labelTime.isValid();
        }
        
        public void setPack(final boolean isPack) {
            this.isPack = isPack;
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
            this.imgBg = UiHandle.addItem(this, WeaponScreen.this.atlasWeaponBg, "waikuang2", 0.0f, 0.0f);
            this.imgWeapon = UiHandle.addItem(this, Assets.atlasUiGame, "ren0", 14.0f, 119.0f);
            UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "ren", 93.0f, 174.0f);
            UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "long", 93.0f, 145.0f);
            UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "jianta", 93.0f, 119.0f);
            this.imgGroundLevelBgs = new MyImage[15];
            this.imgGroundLevels = new MyImage[15];
            this.imgAirLevelBgs = new MyImage[15];
            this.imgAirLevels = new MyImage[15];
            this.imgBuildLevelBgs = new MyImage[15];
            this.imgBuildLevels = new MyImage[15];
            for (int i = 0; i < this.imgGroundLevelBgs.length; ++i) {
                this.imgGroundLevelBgs[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgGroundLevels[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 193.0f - 12.0f);
                this.imgAirLevelBgs[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgAirLevels[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 165.0f - 12.0f);
                this.imgBuildLevelBgs[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji1", i * 5 + 121.0f, 135.0f - 12.0f);
                this.imgBuildLevels[i] = UiHandle.addItem(this, WeaponScreen.this.atlasEnhance, "shengji2", i * 5 + 121.0f, 135.0f - 12.0f);
            }
            this.labelInfo = AutoLineLabel.createRoman16(this, "Continious and rapid fire, low damage to defensive structures.", 30.0f, 65.0f, 162.0f);
            this.setPosition(554.0f, 153.0f);
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
            final int n7 = MyMethods.findIndex(WeaponScreen.strMains, this.strName) + 1;
            if (n7 > 0) {
                this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion(StrHandle.get("ren", n7)));
                return;
            }
            this.imgWeapon.setRegion(WeaponScreen.this.atlasStore.findRegion(StrHandle.get("fu", MyMethods.findIndex(WeaponScreen.strSubs, this.strName) + 1)));
            this.imgWeapon.setSize(94.0f, 116.0f);
        }
        
        public void hide() {
            this.setWeaponLevel(0, 0, 0);
            this.imgWeapon.setRegion(Assets.atlasUiGame.findRegion("ren0"));
            this.labelInfo.setInfo("");
        }
    }
    
    class LabelTime extends MyLabel
    {
        static final float totalTime = 1800.0f;
        float leftTime;
        
        public LabelTime() {
            this.leftTime = 3000.0f;
            this.leftTime = 1800.0f - (System.currentTimeMillis() / 1000L - Global.lastProbaTime);
            if (this.leftTime <= 0.0f) {
                this.setText("00 : 00");
            }
        }
        
        @Override
        public void act(final float n) {
            super.act(n);
            if (this.leftTime > 0.0f) {
                this.leftTime = 1800.0f - (System.currentTimeMillis() / 1000L - Global.lastProbaTime);
                this.setShow();
            }
        }
        
        public boolean isValid() {
            return this.leftTime <= 0.0f;
        }
        
        public void setShow() {
            final int n = (int)this.leftTime;
            if (this.leftTime > 0.0f) {
                this.setText(String.format("%02d : %02d", n / 60, n % 60));
                return;
            }
            this.setText("00 : 00");
            WeaponScreen.this.groupProba.imgBg.setRegion(Assets.atlasWeaponSelect.findRegion(StrHandle.get("shi", Integer.parseInt(WeaponScreen.this.groupProba.getName().substring(3, 5)))));
        }
        
        public void writeProbaTime() {
            Global.lastProbaTime = System.currentTimeMillis() / 1000L;
            PreferHandle.writeCommon();
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
