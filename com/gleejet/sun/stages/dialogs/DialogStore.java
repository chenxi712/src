package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.gleejet.sun.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.action.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.bx.pay.BXPay;

public class DialogStore extends BaseDialog
{
    static final String[] strMains;
    static final String[] strSubs;
    Array<GroupWeapon> arrMains;
    Array<GroupWeapon> arrSubs;
    TextureAtlas atlasStore;
    BaseScreen baseScreen;
    private ButtonListener btnListener;
    BuyCoinDialog buyCoinDialog;
    BuyWeaponDialog buyWeaponDialog;
    CoinLackDialog coinLackDialog;
    GroupWeapon curBuyWeapon;
    DialogRevive dialogRevive;
    CoinMond.GroupCoinHead groupCoinHead;
    GroupCoin[] groupCoins;
    CoinMond.GroupMondHead groupMondHead;
    GroupMond[] groupMonds;
    HelpBoard helpBoard;
    MyImage imgCoin;
    MyImage imgCoinBg;
    MyImage imgMond;
    MyImage imgMondBg;
    MyImage imgWeapon;
    MyImage imgWeaponBg;
    MondLackDialog mondLackDialog;
    InputListener panListener;
    ScrollPane[] pans;
    Table[] tables;
    
    static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
    
    public DialogStore(final BaseScreen baseScreen) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogStore.this.imgMond) {
                        DialogStore.this.selectItem(1);
                    }
                    if (listenerActor == DialogStore.this.imgCoin && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(2);
                        return;
                    }
                    if (listenerActor == DialogStore.this.imgWeapon && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(3);
                        if (DialogStore.this.helpBoard != null && DialogStore.this.helpBoard.insStep == 42) {
                            DialogStore.this.helpBoard.remove();
                            DialogStore.this.addInstruction(43);
                        }
                    }
                    else if (listenerActor == DialogStore.this.imgClose) {
                        DialogStore.this.close();
                    }
                }
            }
        };
        this.panListener = new InputListener() {
            boolean isTouchValid;
            Vector2 pos;
            float startX;
            float startY;
            
            {
                this.pos = new Vector2();
                this.startX = 0.0f;
                this.startY = 0.0f;
                this.isTouchValid = false;
            }
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float startX, final float startY, final int n, final int n2) {
                this.startX = startX;
                this.startY = startY;
                this.isTouchValid = true;
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(Actions.scaleTo(1.07f, 1.07f, 0.05f));
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
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
                    SoundHandle.playForButton2();
                    final Actor listenerActor = inputEvent.getListenerActor();
                    listenerActor.addAction(TouchAction.upAction());
                    this.isTouchValid = false;
                    i = 0;
                    while (i < DialogStore.this.groupCoins.length) {
                        if (listenerActor == DialogStore.this.groupCoins[i]) {
                            if (Global.totalMondNum >= DialogStore.this.groupCoins[i].getMondNum()) {
                                DialogStore.this.buyCoinDialog.setMondCoin(DialogStore.this.groupCoins[i].mondNum, DialogStore.this.groupCoins[i].coinNum, i + 1);
                                DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.buyCoinDialog, 0.35f);
                                return;
                            }
                            DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.mondLackDialog, 0.35f);
                            return;
                        }
                        else {
                            ++i;
                        }
                    }
                    Runnable purchaseOkCallback;
                    for (i = 0; i < DialogStore.this.groupMonds.length; ++i) {
                    	// TODO: add purchase sdk
                        if (listenerActor == DialogStore.this.groupMonds[i]) {
                            purchaseOkCallback = new Runnable() {
                                @Override
                                public void run() {
                                    DialogStore.this.coinMondChange();
                                }
                            };
                            PlatformHandle.buyGoods(i);
                            PlatformHandle.setPurchaseOkCallback(purchaseOkCallback);
                            return;
                        }
                    }
                    Group group = (Group)listenerActor;
                    if (DialogStore.this.helpBoard != null && (group = (Group)listenerActor) == DialogStore.this.helpBoard.tempTarget) {
                        group = DialogStore.this.arrMains.get(0);
                    }
                    for (i = 0; i < DialogStore.this.arrMains.size; ++i) {
                        if (group == DialogStore.this.arrMains.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrMains.get(i));
                            return;
                        }
                    }
                    for (i = 0; i < DialogStore.this.arrSubs.size; ++i) {
                        if (group == DialogStore.this.arrSubs.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrSubs.get(i));
                            return;
                        }
                    }
                }
            }
        };
        this.dialogRevive = null;
        this.baseScreen = baseScreen;
        this.init();
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this);
    }
    
    public DialogStore(final BaseScreen baseScreen, final CoinMond.GroupCoinHead groupCoinHead, final CoinMond.GroupMondHead groupMondHead) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogStore.this.imgMond) {
                        DialogStore.this.selectItem(1);
                    }
                    if (listenerActor == DialogStore.this.imgCoin && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(2);
                        return;
                    }
                    if (listenerActor == DialogStore.this.imgWeapon && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(3);
                        if (DialogStore.this.helpBoard != null && DialogStore.this.helpBoard.insStep == 42) {
                            DialogStore.this.helpBoard.remove();
                            DialogStore.this.addInstruction(43);
                        }
                    }
                    else if (listenerActor == DialogStore.this.imgClose) {
                        DialogStore.this.close();
                    }
                }
            }
        };
        this.panListener = new InputListener() {
            boolean isTouchValid;
            Vector2 pos;
            float startX;
            float startY;
            
            {
                this.pos = new Vector2();
                this.startX = 0.0f;
                this.startY = 0.0f;
                this.isTouchValid = false;
            }
            
            @Override
            public boolean touchDown(final InputEvent inputEvent, final float startX, final float startY, final int n, final int n2) {
                this.startX = startX;
                this.startY = startY;
                this.isTouchValid = true;
                final Actor listenerActor = inputEvent.getListenerActor();
                listenerActor.setOrigin(listenerActor.getWidth() / 2.0f, listenerActor.getHeight() / 2.0f);
                listenerActor.addAction(Actions.scaleTo(1.07f, 1.07f, 0.05f));
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
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
                    SoundHandle.playForButton2();
                    final Actor listenerActor = inputEvent.getListenerActor();
                    listenerActor.addAction(TouchAction.upAction());
                    this.isTouchValid = false;
                    i = 0;
                    while (i < DialogStore.this.groupCoins.length) {
                        if (listenerActor == DialogStore.this.groupCoins[i]) {
                            if (Global.totalMondNum >= DialogStore.this.groupCoins[i].getMondNum()) {
                                DialogStore.this.buyCoinDialog.setMondCoin(DialogStore.this.groupCoins[i].mondNum, DialogStore.this.groupCoins[i].coinNum, i + 1);
                                DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.buyCoinDialog, 0.35f);
                                return;
                            }
                            DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.mondLackDialog, 0.35f);
                            return;
                        }
                        else {
                            ++i;
                        }
                    }
                    Runnable purchaseOkCallback;
                    for (i = 0; i < DialogStore.this.groupMonds.length; ++i) {
                    	// TODO: add purchase sdk
                        if (listenerActor == DialogStore.this.groupMonds[i]) {
                            purchaseOkCallback = new Runnable() {
                                @Override
                                public void run() {
                                    DialogStore.this.coinMondChange();
                                }
                            };
                            PlatformHandle.buyGoods(i);
                            PlatformHandle.setPurchaseOkCallback(purchaseOkCallback);
                            return;
                        }
                    }
                    Group group = (Group)listenerActor;
                    if (DialogStore.this.helpBoard != null && (group = (Group)listenerActor) == DialogStore.this.helpBoard.tempTarget) {
                        group = DialogStore.this.arrMains.get(0);
                    }
                    for (i = 0; i < DialogStore.this.arrMains.size; ++i) {
                        if (group == DialogStore.this.arrMains.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrMains.get(i));
                            return;
                        }
                    }
                    for (i = 0; i < DialogStore.this.arrSubs.size; ++i) {
                        if (group == DialogStore.this.arrSubs.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrSubs.get(i));
                            return;
                        }
                    }
                }
            }
        };
        this.dialogRevive = null;
        this.baseScreen = baseScreen;
        this.init();
        this.groupCoinHead = groupCoinHead;
        this.groupMondHead = groupMondHead;
    }
    
    public DialogStore(final DialogRevive dialogRevive) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogStore.this.imgMond) {
                        DialogStore.this.selectItem(1);
                    }
                    if (listenerActor == DialogStore.this.imgCoin && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(2);
                        return;
                    }
                    if (listenerActor == DialogStore.this.imgWeapon && DialogStore.this.dialogRevive == null) {
                        DialogStore.this.selectItem(3);
                        if (DialogStore.this.helpBoard != null && DialogStore.this.helpBoard.insStep == 42) {
                            DialogStore.this.helpBoard.remove();
                            DialogStore.this.addInstruction(43);
                        }
                    }
                    else if (listenerActor == DialogStore.this.imgClose) {
                        DialogStore.this.close();
                    }
                }
            }
        };
        this.panListener = new InputListener() {
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
                listenerActor.addAction(Actions.scaleTo(1.07f, 1.07f, 0.05f));
                return true;
            }
            
            @Override
            public void touchDragged(final InputEvent inputEvent, final float n, final float n2, final int n3) {
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
                    SoundHandle.playForButton2();
                    final Actor listenerActor = inputEvent.getListenerActor();
                    listenerActor.addAction(TouchAction.upAction());
                    this.isTouchValid = false;
                    i = 0;
                    while (i < DialogStore.this.groupCoins.length) {
                        if (listenerActor == DialogStore.this.groupCoins[i]) {
                            if (Global.totalMondNum >= DialogStore.this.groupCoins[i].getMondNum()) {
                                DialogStore.this.buyCoinDialog.setMondCoin(DialogStore.this.groupCoins[i].mondNum, DialogStore.this.groupCoins[i].coinNum, i + 1);
                                DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.buyCoinDialog, 0.35f);
                                return;
                            }
                            DialogHandle.openDialog(DialogStore.this.getStage(), DialogStore.this.mondLackDialog, 0.35f);
                            return;
                        }
                        else {
                            ++i;
                        }
                    }
                    Runnable purchaseOkCallback;
                    for (i = 0; i < DialogStore.this.groupMonds.length; ++i) {
                    	// TODO: add purchase sdk
                        if (listenerActor == DialogStore.this.groupMonds[i]) {
                        	MainActivity.buyGoods(i);
                            purchaseOkCallback = new Runnable() {
                                @Override
                                public void run() {
                                    DialogStore.this.coinMondChange();
                                }
                            };
                            PlatformHandle.buyGoods(i);
                            PlatformHandle.setPurchaseOkCallback(purchaseOkCallback);
                            return;
                        }
                    }
                    Group group = (Group)listenerActor;
                    if (DialogStore.this.helpBoard != null && (group = (Group)listenerActor) == DialogStore.this.helpBoard.tempTarget) {
                        group = DialogStore.this.arrMains.get(0);
                    }
                    for (i = 0; i < DialogStore.this.arrMains.size; ++i) {
                        if (group == DialogStore.this.arrMains.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrMains.get(i));
                            return;
                        }
                    }
                    for (i = 0; i < DialogStore.this.arrSubs.size; ++i) {
                        if (group == DialogStore.this.arrSubs.get(i)) {
                            DialogStore.this.checkBuyWeapon((GroupWeapon)DialogStore.this.arrSubs.get(i));
                            return;
                        }
                    }
                }
            }
        };
        this.dialogRevive = dialogRevive;
        this.baseScreen = null;
        this.init();
        this.groupCoinHead = CoinMond.GroupCoinHead.createCoinHead(this);
        this.groupMondHead = CoinMond.GroupMondHead.createMondHead(this);
    }
    
    private void addInstruction(final int insStep) {
        if (Global.maxGameLevelEasy == 4 && !PreferHandle.readInstruction("instructionScatter") && !Global.arrMainGunGet.contains("Scatter", false) && insStep != 41) {
            if (insStep == 42) {
                if (this.helpBoard != null) {
                    this.helpBoard.remove();
                }
//                this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgWeapon, "Choose store of weapon!", 1, 1);
                this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgWeapon, Language.chooseStoreWeapon(), 1, 1);
                
                this.helpBoard.insStep = insStep;
            }
            else if (insStep == 43) {
                if (this.helpBoard != null) {
                    this.helpBoard.remove();
                }
                final GroupWeapon tempTarget = new GroupWeapon(3, true);
                tempTarget.setPosition(144.0f, 215.0f);
                tempTarget.addListener(this.panListener);
                this.addActor(tempTarget);
//                this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), tempTarget, "Touch to buy a new weapon!", 1, -1);
                this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), tempTarget, Language.touchToBuyWeapon(), 1, -1);
                this.helpBoard.tempTarget = tempTarget;
                this.helpBoard.insStep = insStep;
            }
        }
        if (insStep == 44 && this.helpBoard != null && this.helpBoard.insStep == 43) {
            this.helpBoard.remove();
//            this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgClose, "Return!", -1, -1);
            this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgClose, Language.tapToReturn(), -1, -1);
            this.helpBoard.insStep = insStep;
        }
    }
    
    private void arrayPack() {
        for (int i = this.arrMains.size - 1; i >= 0; --i) {
            for (int j = 0; j < Global.arrMainGunGet.size; ++j) {
                if (this.arrMains.get(i).getName().equals(Global.arrMainGunGet.get(j))) {
                    this.arrMains.removeIndex(i);
                    break;
                }
            }
        }
        for (int k = this.arrSubs.size - 1; k >= 0; --k) {
            for (int l = 0; l < Global.arrSubGunGet.size; ++l) {
                if (this.arrSubs.get(k).getName().equals(Global.arrSubGunGet.get(l))) {
                    this.arrSubs.removeIndex(k);
                    break;
                }
            }
        }
    }
    
    private void checkBuyWeapon(final GroupWeapon curBuyWeapon) {
        final JsonValue value = Assets.jsonEnhance.get(curBuyWeapon.getName());
        value.getInt("unlockCoin");
        value.getInt("unlockMond");
        this.buyWeaponDialog.setWeaponName(curBuyWeapon.getName());
        if (this.helpBoard != null) {
            this.helpBoard.remove();
        }
        this.buyWeaponDialog.openDialog(this.getStage());
        this.curBuyWeapon = curBuyWeapon;
    }
    
    private void freeBuyMond(final int n) {
        Global.totalMondNum += (new int[] { 10, 30, 80, 200, 600, 1600 })[n];
        PreferHandle.writeCommon();
//        FlurryHandle.buyMond(n + 1);
    }
    
    private void init() {
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.imgBg = UiHandle.addItem(this, this.atlasStore, "waikuangda", 0.0f, 0.0f);
        UiHandle.addItem(this, this.atlasStore, "di_da", 132.0f, 29.0f);
        UiHandle.addItem(this, this.atlasStore, "shop", 129.0f, 283.0f);
        this.initClose(this.btnListener);
        this.imgMondBg = UiHandle.addItem(this, this.atlasStore, "mond_border", 29.0f, 210.0f);
        this.imgCoinBg = UiHandle.addItem(this, this.atlasStore, "gold_border", 28.5f, 122.5f);
        this.imgWeaponBg = UiHandle.addItem(this, this.atlasStore, "weapon_border", 29.0f, 42.5f);
        this.imgMond = UiHandle.addItem(this, this.atlasStore, "baoshi_sel", 32.0f, 214.0f, this.btnListener);
        this.imgCoin = UiHandle.addItem(this, this.atlasStore, "jinbi_sel", 32.0f, 127.0f, this.btnListener);
        this.imgWeapon = UiHandle.addItem(this, this.atlasStore, "renwu_sel", 32.0f, 45.0f, this.btnListener);
        this.initPan();
        this.initTable();
        this.tablePack();
        this.coinLackDialog = new CoinLackDialog(this);
        this.mondLackDialog = new MondLackDialog(this);
        this.buyCoinDialog = new BuyCoinDialog(this);
        this.buyWeaponDialog = new BuyWeaponDialog(this, this.mondLackDialog, this.coinLackDialog);
        this.setSizeOrigin();
        this.translate(0.0f, -23.0f);
    }
    
    private void initPan() {
        this.tables = new Table[3];
        for (int i = 0; i < this.tables.length; ++i) {
            this.tables[i] = new Table();
            this.tables[i].defaults().space(4.0f);
            this.tables[i].defaults().padRight(10.0f);
        }
        final ScrollPane.ScrollPaneStyle scrollPaneStyle = new ScrollPane.ScrollPaneStyle();
        scrollPaneStyle.hScroll = new TextureRegionDrawable(this.atlasStore.findRegion("huadongtiao"));
        scrollPaneStyle.vScroll = new TextureRegionDrawable(this.atlasStore.findRegion("huadongtiao"));
        final NinePatch ninePatch = new NinePatch(this.atlasStore.findRegion("huadonganniu"), 5, 5, 15, 15);
        scrollPaneStyle.hScrollKnob = new NinePatchDrawable(ninePatch);
        scrollPaneStyle.vScrollKnob = new NinePatchDrawable(ninePatch);
        this.pans = new ScrollPane[3];
        for (int j = 0; j < this.pans.length; ++j) {
            (this.pans[j] = new ScrollPane(this.tables[j], scrollPaneStyle)).setScrollingDisabled(true, false);
            this.pans[j].setFadeScrollBars(false);
            this.pans[j].setSize(436.0f, 267.0f);
            this.pans[j].setPosition(134.0f, 33.0f);
            this.pans[j].setVisible(false);
            this.addActor(this.pans[j]);
        }
        this.selectItem(1);
    }
    
    private void initTable() {
        this.groupCoins = new GroupCoin[6];
        for (int i = 0; i < this.groupCoins.length; ++i) {
            (this.groupCoins[i] = new GroupCoin(i + 1)).addListener(this.panListener);
            this.tables[0].add(this.groupCoins[i]);
            if (i % 2 == 1 && i != this.groupCoins.length) {
                this.tables[0].row();
            }
        }
        this.groupMonds = new GroupMond[6];
        for (int j = 0; j < this.groupMonds.length; ++j) {
            (this.groupMonds[j] = new GroupMond(j + 1)).addListener(this.panListener);
            this.tables[1].add(this.groupMonds[j]);
            if (j % 2 == 1 && j != this.groupMonds.length) {
                this.tables[1].row();
            }
        }
        this.arrMains = new Array<GroupWeapon>();
        for (int k = 0; k < 11; ++k) {
            final GroupWeapon groupWeapon = new GroupWeapon(MyMethods.findIndex(DialogStore.strMains, Constant.strMainOrders[k + 2]) + 1, true);
            groupWeapon.addListener(this.panListener);
            this.arrMains.add(groupWeapon);
        }
        this.arrSubs = new Array<GroupWeapon>();
        for (int l = 2; l < 7; ++l) {
            final GroupWeapon groupWeapon2 = new GroupWeapon(MyMethods.findIndex(DialogStore.strSubs, Constant.strSubOrders[l]) + 1, false);
            groupWeapon2.addListener(this.panListener);
            this.arrSubs.add(groupWeapon2);
        }
    }
    
    private void tablePack() {
        this.arrayPack();
        this.tables[2].clear();
        int n = 0;
        for (int i = 0; i < this.arrMains.size; ++i) {
            this.tables[2].add(this.arrMains.get(i));
            ++n;
            if (n % 2 == 0) {
                this.tables[2].row();
            }
        }
        for (int j = 0; j < this.arrSubs.size; ++j) {
            this.tables[2].add(this.arrSubs.get(j));
            ++n;
            if (n % 2 == 0) {
                this.tables[2].row();
            }
        }
    }
    
    private void zoomInOut(final MyImage myImage) {
        myImage.clearActions();
        myImage.setOrigin(myImage.getWidth() / 2.0f, myImage.getHeight() / 2.0f);
        myImage.setScale(1.0f, 1.0f);
        myImage.addAction(Actions.forever(Actions.sequence(Actions.scaleTo(0.98f, 0.98f, 0.5f), Actions.scaleTo(1.07f, 1.07f, 0.5f))));
    }
    
    @Override
    public void afterClose() {
        if (this.dialogRevive != null) {
            this.dialogRevive.checkMondEnough();
        }
        if (this.baseScreen instanceof LevelSmallScreen) {
            ((LevelSmallScreen)this.baseScreen).addInstruction();
        }
        if (this.baseScreen instanceof WeaponScreen) {
            ((WeaponScreen)this.baseScreen).refreshWeapon();
        }
        if (this.baseScreen instanceof WeaponEnhanceScreen) {
            ((WeaponEnhanceScreen)this.baseScreen).refreshWeapon();
        }
    }
    
    public boolean buyMainWeapon(final GroupWeapon groupWeapon) {
        final boolean b = true;
        boolean b2;
        if (Global.totalCoinNum < groupWeapon.getCoinCost() || Global.totalMondNum < groupWeapon.getMondCost()) {
            b2 = false;
        }
        else {
            Global.totalCoinNum -= groupWeapon.getCoinCost();
            Global.totalMondNum -= groupWeapon.getMondCost();
            this.groupCoinHead.setCoinNum((int)Global.totalCoinNum);
            this.groupMondHead.setMondNum((int)Global.totalMondNum);
            Global.arrMainGunGet.add(groupWeapon.getName());
            PreferHandle.checkSame(Global.arrMainGunGet);
            PreferHandle.writeWeaponGet();
            PreferHandle.writeCommon();
//            FlurryHandle.buyMainWeapon(groupWeapon.getName());
//            FlurryHandle.weaponUnlock(groupWeapon.getName(), true);
            this.arrMains.removeValue(groupWeapon, true);
            this.tablePack();
            b2 = b;
            if (this.helpBoard != null) {
                b2 = b;
                if (this.helpBoard.insStep == 43) {
                    this.helpBoard.remove();
                    this.addInstruction(44);
                    return true;
                }
            }
        }
        return b2;
    }
    
    public boolean buySubWeapon(final GroupWeapon groupWeapon) {
        if (Global.totalCoinNum < groupWeapon.getCoinCost() || Global.totalMondNum < groupWeapon.getMondCost()) {
            return false;
        }
        Global.totalCoinNum -= groupWeapon.getCoinCost();
        Global.totalMondNum -= groupWeapon.getMondCost();
        this.groupCoinHead.setCoinNum((int)Global.totalCoinNum);
        this.groupMondHead.setMondNum((int)Global.totalMondNum);
        Global.arrSubGunGet.add(groupWeapon.getName());
        PreferHandle.checkSame(Global.arrSubGunGet);
        PreferHandle.writeWeaponGet();
        PreferHandle.writeCommon();
//        FlurryHandle.buyLightWeapon(groupWeapon.getName());
//        FlurryHandle.weaponUnlock(groupWeapon.getName(), false);
        this.arrSubs.removeValue(groupWeapon, true);
        this.tablePack();
        return true;
    }
    
    public void buyWeaponOver() {
        if (Global.isBuyOk) {
            if (this.arrMains.contains(this.curBuyWeapon, true)) {
                this.buyMainWeapon(this.curBuyWeapon);
            }
            else if (this.arrSubs.contains(this.curBuyWeapon, true)) {
                this.buySubWeapon(this.curBuyWeapon);
            }
        }
    }
    
    public void close() {
        if (this.helpBoard != null) {
            this.helpBoard.remove();
        }
        DialogHandle.closeDialog(this, 0.35f);
    }
    
    public void closeBuyWeaponDialog() {
        if (this.buyWeaponDialog.getParent() != null) {
            DialogHandle.closeDialog(this.buyWeaponDialog, 0.25f);
        }
    }
    
    public void coinMondChange() {
        this.groupCoinHead.setCoinNum((int)Global.totalCoinNum);
        this.groupMondHead.setMondNum((int)Global.totalMondNum);
    }
    
    public void keyBack() {
        if (this.mondLackDialog.getParent() != null) {
            this.mondLackDialog.close();
            return;
        }
        if (this.coinLackDialog.getParent() != null) {
            this.coinLackDialog.close();
            return;
        }
        if (this.buyCoinDialog.getParent() != null) {
            this.buyCoinDialog.close();
            return;
        }
        if (this.buyWeaponDialog.getParent() != null) {
            this.buyWeaponDialog.close();
            return;
        }
        this.close();
    }
    
    public void openDialog(Stage stage) {
        this.coinMondChange();
        if (this.baseScreen == null) {
            DialogHandle.openDialog(stage, this);
            return;
        }
        stage.addActor(this.getShade());
        this.groupCoinHead.toFront();
        this.groupMondHead.toFront();
        this.setScale(0.0f);
        this.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.35f, Interpolation.swingOut), Actions.run(new Runnable() {
            @Override
            public void run() {
                DialogStore.this.addInstruction(42);
            }
        })));
        stage.addActor(this);
    }
    
    public void selectItem(final int n) {
        switch (n) {
            case 1: {
                this.pans[0].setVisible(false);
                this.pans[1].setVisible(true);
                this.pans[2].setVisible(false);
                this.imgMondBg.setVisible(true);
                this.imgCoinBg.setVisible(false);
                this.imgWeaponBg.setVisible(false);
                this.zoomInOut(this.imgMondBg);
                this.imgMond.toFront();
                break;
            }
            case 2: {
                this.pans[0].setVisible(true);
                this.pans[1].setVisible(false);
                this.pans[2].setVisible(false);
                this.imgMondBg.setVisible(false);
                this.imgCoinBg.setVisible(true);
                this.imgWeaponBg.setVisible(false);
                this.zoomInOut(this.imgCoinBg);
                this.imgCoin.toFront();
                break;
            }
            case 3: {
                this.pans[0].setVisible(false);
                this.pans[1].setVisible(false);
                this.pans[2].setVisible(true);
                this.imgMondBg.setVisible(false);
                this.imgCoinBg.setVisible(false);
                this.imgWeaponBg.setVisible(true);
                this.zoomInOut(this.imgWeaponBg);
                this.imgWeapon.toFront();
                break;
            }
        }
    }
    
    private class GroupCoin extends Group
    {
        private int coinNum;
        int[] coins;
        MyImage imgBg;
        MyImage imgCoin;
        MyImage imgMondPrice;
        private int mondNum;
        int[] monds;
        
        public GroupCoin(final int n) {
            this.monds = new int[] { 20, 50, 100, 200, 500, 1000 };
            this.coins = new int[] { 5000, 15000, 30000, 70000, 200000, 450000 };
            this.imgBg = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "huojia", 0.0f, 0.0f);
            this.imgCoin = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "g" + n, 17.0f, 8.0f);
            this.coinNum = this.coins[n - 1];
            this.mondNum = this.monds[n - 1];
            this.imgMondPrice = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "mond" + this.mondNum, 100.0f, 11.0f);
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
        }
        
        public int getCoinNum() {
            return this.coinNum;
        }
        
        public int getMondNum() {
            return this.mondNum;
        }
    }
    
    private class GroupMond extends Group
    {
        MyImage imgAdfree;
        MyImage imgBg;
        MyImage imgMond;
        MyImage imgPrice;
        private int mondNum;
        int[] monds;
        private float priceNum;
        float[] prices;
        
        public GroupMond(final int n) {
            this.prices = new float[] { 1.99f, 4.99f, 9.99f, 19.99f, 49.99f, 99.99f };
            this.monds = new int[] { 10, 30, 80, 200, 600, 1600 };
            this.imgBg = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "huojia", 0.0f, 0.0f);
            this.priceNum = this.prices[n - 1];
            this.mondNum = this.monds[n - 1];
            this.imgMond = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "b" + n, 17.0f, 8.0f);
            this.imgPrice = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "price" + n, 100.0f, 11.0f);
            if (n > 1) {
                this.imgAdfree = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "adfree", 140.0f, 30.0f);
            }
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
        }
        
        public int getMondNum() {
            return this.mondNum;
        }
        
        public float getPriceNum() {
            return this.priceNum;
        }
    }
    
    private class GroupWeapon extends Group
    {
        int coinCost;
        MyImage imgBg;
        MyImage imgPrice;
        MyImage imgType;
        MyImage imgWeapon;
        int mondCost;
        
        public GroupWeapon(final int n, final boolean b) {
            MyImage imgBg;
            if (b) {
                imgBg = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "huojia_zhu", 0.0f, 0.0f);
            }
            else {
                imgBg = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "huojia", 0.0f, 0.0f);
            }
            this.imgBg = imgBg;
            if (b) {
                this.imgWeapon = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "zhu" + n, 17.0f, 5.0f);
                this.imgType = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "zhuwuqi", 140.0f, 30.0f);
                this.setName(DialogStore.strMains[n - 1]);
            }
            else {
                this.imgWeapon = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "fu" + n, 17.0f, 5.0f);
                this.imgType = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "fuwuqi", 140.0f, 30.0f);
                this.setName(DialogStore.strSubs[n - 1]);
            }
            this.imgWeapon.setScale(0.8f);
            this.coinCost = Assets.jsonEnhance.get(this.getName()).getInt("unlockCoin");
            this.mondCost = Assets.jsonEnhance.get(this.getName()).getInt("unlockMond");
            if (this.coinCost > 0) {
                this.imgPrice = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "gold" + this.coinCost, 100.0f, 11.0f);
            }
            else if (this.mondCost > 0) {
                this.imgPrice = DialogStore.this.addItem(this, DialogStore.this.atlasStore, "mond" + this.mondCost, 100.0f, 11.0f);
            }
            this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
        }
        
        public int getCoinCost() {
            return this.coinCost;
        }
        
        public int getMondCost() {
            return this.mondCost;
        }
    }
}
