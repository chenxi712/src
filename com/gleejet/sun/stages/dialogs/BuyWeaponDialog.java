package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;
import com.badlogic.gdx.utils.*;

public class BuyWeaponDialog extends BaseDialog
{
    static final String[] strMains;
    static final String[] strSubs;
    TextureAtlas atlasEnhance;
    TextureAtlas atlasStore;
    public ButtonListener btnListener;
    int coinCost;
    CoinLackDialog coinLackDialog;
    DialogStore dialogStore;
    HelpBoard helpBoard;
    MyImage[] imgAirLevelBgs;
    MyImage[] imgAirLevels;
    MyImage[] imgBuildLevelBgs;
    MyImage[] imgBuildLevels;
    MyImage imgBuy;
    MyImage[] imgGroundLevelBgs;
    MyImage[] imgGroundLevels;
    MyImage imgType;
    MyImage imgWeapon;
    Label labelInfo;
    int mondCost;
    MondLackDialog mondLackDialog;
    String strName;
    
    static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
    
    public BuyWeaponDialog(final DialogStore dialogStore, final MondLackDialog mondLackDialog, final CoinLackDialog coinLackDialog) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    Actor imgBuy = listenerActor;
                    if (listenerActor.getName() != null) {
                        imgBuy = listenerActor;
                        if (listenerActor.getName().equals("ins_scatter")) {
                            imgBuy = BuyWeaponDialog.this.imgBuy;
                        }
                    }
                    if (imgBuy == BuyWeaponDialog.this.imgBuy) {
                        if (Global.totalCoinNum >= BuyWeaponDialog.this.coinCost && Global.totalMondNum >= BuyWeaponDialog.this.mondCost) {
                            Global.isBuyOk = true;
                            BuyWeaponDialog.this.closeDialog(BuyWeaponDialog.this);
                            return;
                        }
                        if (BuyWeaponDialog.this.coinCost > 0) {
                            DialogHandle.openDialog(BuyWeaponDialog.this.getStage(), BuyWeaponDialog.this.coinLackDialog, 0.35f);
                            return;
                        }
                        if (BuyWeaponDialog.this.mondCost > 0) {
                            DialogHandle.openDialog(BuyWeaponDialog.this.getStage(), BuyWeaponDialog.this.mondLackDialog, 0.35f);
                        }
                    }
                    else if (imgBuy == BuyWeaponDialog.this.imgClose) {
                        BuyWeaponDialog.this.close();
                    }
                }
            }
        };
        this.dialogStore = dialogStore;
        this.mondLackDialog = mondLackDialog;
        this.coinLackDialog = coinLackDialog;
        this.init();
    }
    
    private void addInstruction(final int insStep) {
        if (Global.maxGameLevelEasy == 4 && !PreferHandle.readInstruction("instructionScatter") && !Global.arrMainGunGet.contains("Scatter", false) && insStep == 44) {
            if (this.helpBoard != null) {
                this.helpBoard.remove();
            }
//            this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgBuy, "Tap to continue!", -1, 1);
            this.helpBoard = HelpBoard.createDialogBoard(this.getStage(), this.imgBuy, Language.tapToContinue(), -1, 1);
            this.helpBoard.insStep = insStep;
        }
    }
    
    private void init() {
        this.atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        (this.imgBg = this.addItem(this, Assets.atlasPauseOver, "waikuang", 0.0f, 0.0f)).setHeight(this.imgBg.getHeight() * 1.05f);
        this.initClose(this.btnListener);
        this.addItem(this, this.atlasStore, "di_xiao", 42.0f, 119.0f);
        this.imgType = this.addItem(this, this.atlasStore, "main2", 266.0f, 173.0f);
        this.imgBuy = this.addItem(this, this.atlasStore, "mond20", 148.0f, 32.0f, this.btnListener);
//        this.labelInfo = UiHandle.createRomanLabel(this, "Do you want to spend 100 diamond to buy \nthe weapon?", 40.0f, 70.0f);
        this.labelInfo = UiHandle.createRomanLabel(this, Language.toBuyWeapon(), 40.0f, 70.0f);
        this.initWeapon();
        this.setWeaponLevel(4, 5, 6);
        this.imgShade.setColor(MyShade.colorStoreTran);
        this.setSizeOrigin();
    }
    
    private void initWeapon() {
        this.imgWeapon = this.addItem(this, this.atlasStore, "zhu1", 52.0f, 126.0f);
        this.addItem(this, this.atlasEnhance, "ren", 336.0f - 193.0f, 338.0f - 152.0f);
        this.addItem(this, this.atlasEnhance, "long", 336.0f - 193.0f, 308.0f - 152.0f);
        this.addItem(this, this.atlasEnhance, "jianta", 336.0f - 193.0f, 282.0f - 152.0f);
        final float n = -193.0f + 1.0f;
        this.imgGroundLevelBgs = new MyImage[15];
        for (int i = 0; i < this.imgGroundLevelBgs.length; ++i) {
            this.imgGroundLevelBgs[i] = this.addItem(this, this.atlasEnhance, "shengji1", i * 5 + 366 + n, 345.0f - 152.0f);
        }
        this.imgGroundLevels = new MyImage[15];
        for (int j = 0; j < this.imgGroundLevels.length; ++j) {
            this.imgGroundLevels[j] = this.addItem(this, this.atlasEnhance, "shengji2", j * 5 + 366 + n, 345.0f - 152.0f);
        }
        this.imgAirLevelBgs = new MyImage[15];
        for (int k = 0; k < this.imgAirLevelBgs.length; ++k) {
            this.imgAirLevelBgs[k] = this.addItem(this, this.atlasEnhance, "shengji1", k * 5 + 366 + n, 314.0f - 152.0f);
        }
        this.imgAirLevels = new MyImage[15];
        for (int l = 0; l < this.imgAirLevels.length; ++l) {
            this.imgAirLevels[l] = this.addItem(this, this.atlasEnhance, "shengji2", l * 5 + 366 + n, 314.0f - 152.0f);
        }
        this.imgBuildLevelBgs = new MyImage[15];
        for (int n2 = 0; n2 < this.imgBuildLevelBgs.length; ++n2) {
            this.imgBuildLevelBgs[n2] = this.addItem(this, this.atlasEnhance, "shengji1", n2 * 5 + 366 + n, 287.0f - 152.0f);
        }
        this.imgBuildLevels = new MyImage[15];
        for (int n3 = 0; n3 < this.imgBuildLevels.length; ++n3) {
            this.imgBuildLevels[n3] = this.addItem(this, this.atlasEnhance, "shengji2", n3 * 5 + 366 + n, 287.0f - 152.0f);
        }
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
    
    private void setWeaponType(final boolean b) {
        String s;
        if (b) {
            s = "main2";
        }
        else {
            s = "little2";
        }
        this.imgType.setRegion(this.atlasStore.findRegion(s));
    }
    
    public void close() {
        if (this.helpBoard != null) {
            if (this.helpBoard.imgHand.getParent() != null) {
                return;
            }
            this.helpBoard.remove();
        }
        Global.isBuyOk = false;
        this.closeDialog(this);
    }
    
    public void closeDialog(final BaseDialog baseDialog) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.35f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.getShade().remove();
                baseDialog.remove();
                BuyWeaponDialog.this.dialogStore.buyWeaponOver();
            }
        })));
    }
    
    public MyImage getImgBuy() {
        return this.imgBuy;
    }
    
    public void openDialog(final Stage stage) {
        if (stage == null) {
            return;
        }
        stage.addActor(this.getShade());
        final ScaleToAction scaleTo = Actions.scaleTo(1.0f, 1.0f, 0.4f, Interpolation.swingOut);
        final RunnableAction run = Actions.run(new Runnable() {
            @Override
            public void run() {
                BuyWeaponDialog.this.addInstruction(44);
            }
        });
        this.setScale(0.0f);
        this.addAction(Actions.sequence(scaleTo, run));
        stage.addActor(this);
    }
    
    public void setWeaponName(String strName) {
        this.strName = strName;
        final boolean b = false;
        int n = 0;
        boolean weaponType;
        while (true) {
            weaponType = b;
            if (n >= BuyWeaponDialog.strMains.length) {
                break;
            }
            if (BuyWeaponDialog.strMains[n].equals(this.strName)) {
                weaponType = true;
                break;
            }
            ++n;
        }
        this.setWeaponType(weaponType);
        int n2;
        if (weaponType) {
            n2 = MyMethods.findIndex(BuyWeaponDialog.strMains, this.strName);
        }
        else {
            n2 = MyMethods.findIndex(BuyWeaponDialog.strSubs, this.strName);
        }
        final MyImage imgWeapon = this.imgWeapon;
        final TextureAtlas atlasStore = this.atlasStore;
        if (weaponType) {
            strName = "zhu" + (n2 + 1);
        }
        else {
            strName = "fu" + (n2 + 1);
        }
        imgWeapon.setRegion(atlasStore.findRegion(strName));
        final JsonValue value = Assets.jsonEnhance.get(this.strName);
        final JsonValue value2 = value.get("damageRatio");
        this.setWeaponLevel(value2.get(0).asInt(), value2.get(1).asInt(), value2.get(2).asInt());
        this.coinCost = value.getInt("unlockCoin");
        this.mondCost = value.getInt("unlockMond");
        if (this.coinCost > 0) {
            this.labelInfo.setText(value.getString("shopInfo"));
            this.imgBuy.setRegion(this.atlasStore.findRegion(StrHandle.get("gold", this.coinCost)));
        }
        else if (this.mondCost > 0) {
            this.labelInfo.setText(value.getString("shopInfo"));
            this.imgBuy.setRegion(this.atlasStore.findRegion(StrHandle.get("mond", this.mondCost)));
        }
    }
}
