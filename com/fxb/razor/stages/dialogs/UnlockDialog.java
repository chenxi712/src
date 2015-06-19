package com.fxb.razor.stages.dialogs;

import com.fxb.razor.utils.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.stages.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.utils.*;

public class UnlockDialog extends BaseDialog
{
    static final String[] strMains;
    static final String[] strSubs;
    TextureAtlas atlasEnhance;
    TextureAtlas atlasStore;
    private ButtonListener btnListener;
    int coinCost;
    MyImage[] imgAirLevelBgs;
    MyImage[] imgAirLevels;
    MyImage[] imgBuildLevelBgs;
    MyImage[] imgBuildLevels;
    MyImage[] imgGroundLevelBgs;
    MyImage[] imgGroundLevels;
    MyImage imgType;
    MyImage imgWeapon;
    MyImage imgYes;
    Label labelInfo1;
    Label labelInfo2;
    int mondCost;
    String strName;
    
    static {
        strMains = Constant.strMains;
        strSubs = Constant.strSubs;
    }
    
    public UnlockDialog() {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == UnlockDialog.this.imgYes) {
                        UnlockDialog.this.closeDialog(UnlockDialog.this);
                    }
                }
            }
        };
        this.atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        (this.imgBg = this.addItem(this, Assets.atlasPauseOver, "waikuang", 0.0f, 0.0f)).setHeight(this.imgBg.getHeight() * 1.05f);
        this.addItem(this, this.atlasStore, "di_xiao", 42.0f, 109.0f - 10.0f);
        this.imgType = this.addItem(this, this.atlasStore, "main2", 266.0f, 163.0f - 10.0f);
        this.imgYes = this.addItem(this, this.atlasStore, "yes", 138.0f, 22.0f, this.btnListener);
        this.labelInfo1 = UiHandle.createRomanLabel(this, "You have unlocked a new main weapon.", 40.0f, 201.0f);
        this.labelInfo2 = UiHandle.createRomanLabel(this, "Easy to make explosion\nin certain range.", 40.0f, 68.0f);
        this.initWeapon();
        this.setWeaponLevel(4, 5, 6);
        this.setSizeOrigin();
    }
    
    private void initWeapon() {
        this.imgWeapon = this.addItem(this, this.atlasStore, "zhu1", 52.0f, 106.0f);
        this.addItem(this, this.atlasEnhance, "ren", 336.0f - 206.0f, 338.0f - 172.0f);
        this.addItem(this, this.atlasEnhance, "long", 336.0f - 206.0f, 308.0f - 172.0f);
        this.addItem(this, this.atlasEnhance, "jianta", 336.0f - 206.0f, 282.0f - 172.0f);
        final float n = -206.0f + 8.0f;
        this.imgGroundLevelBgs = new MyImage[15];
        for (int i = 0; i < this.imgGroundLevelBgs.length; ++i) {
            this.imgGroundLevelBgs[i] = this.addItem(this, this.atlasEnhance, "shengji1", i * 5 + 366 + n, 345.0f - 172.0f);
        }
        this.imgGroundLevels = new MyImage[15];
        for (int j = 0; j < this.imgGroundLevels.length; ++j) {
            this.imgGroundLevels[j] = this.addItem(this, this.atlasEnhance, "shengji2", j * 5 + 366 + n, 345.0f - 172.0f);
        }
        this.imgAirLevelBgs = new MyImage[15];
        for (int k = 0; k < this.imgAirLevelBgs.length; ++k) {
            this.imgAirLevelBgs[k] = this.addItem(this, this.atlasEnhance, "shengji1", k * 5 + 366 + n, 314.0f - 172.0f);
        }
        this.imgAirLevels = new MyImage[15];
        for (int l = 0; l < this.imgAirLevels.length; ++l) {
            this.imgAirLevels[l] = this.addItem(this, this.atlasEnhance, "shengji2", l * 5 + 366 + n, 314.0f - 172.0f);
        }
        this.imgBuildLevelBgs = new MyImage[15];
        for (int n2 = 0; n2 < this.imgBuildLevelBgs.length; ++n2) {
            this.imgBuildLevelBgs[n2] = this.addItem(this, this.atlasEnhance, "shengji1", n2 * 5 + 366 + n, 287.0f - 172.0f);
        }
        this.imgBuildLevels = new MyImage[15];
        for (int n3 = 0; n3 < this.imgBuildLevels.length; ++n3) {
            this.imgBuildLevels[n3] = this.addItem(this, this.atlasEnhance, "shengji2", n3 * 5 + 366 + n, 287.0f - 172.0f);
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
        if (b) {
            this.labelInfo1.setText("You have unlocked a new main weapon.");
            return;
        }
        this.labelInfo1.setText("You have unlocked a new light weapon.");
    }
    
    public void closeDialog(final BaseDialog baseDialog) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.35f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.getShade().remove();
                ((GameStage)UnlockDialog.this.getStage()).showDialogOver();
                baseDialog.remove();
            }
        })));
    }
    
    public void setWeaponName(String strName) {
        this.strName = strName;
        final boolean weaponType = MyMethods.findIndex(UnlockDialog.strMains, this.strName) >= 0;
        this.setWeaponType(weaponType);
        int n;
        if (weaponType) {
            n = MyMethods.findIndex(UnlockDialog.strMains, this.strName);
        }
        else {
            n = MyMethods.findIndex(UnlockDialog.strSubs, this.strName);
        }
        final MyImage imgWeapon = this.imgWeapon;
        final TextureAtlas atlasStore = this.atlasStore;
        if (weaponType) {
            strName = "zhu" + (n + 1);
        }
        else {
            strName = "fu" + (n + 1);
        }
        imgWeapon.setRegion(atlasStore.findRegion(strName));
        final JsonValue value = Assets.jsonEnhance.get(this.strName);
        final JsonValue value2 = value.get("damageRatio");
        this.setWeaponLevel(value2.get(0).asInt(), value2.get(1).asInt(), value2.get(2).asInt());
        this.labelInfo2.setText(value.getString("shopInfo"));
        if (weaponType) {
            Global.arrMainGunGet.add(this.strName);
            return;
        }
        Global.arrSubGunGet.add(this.strName);
    }
}
