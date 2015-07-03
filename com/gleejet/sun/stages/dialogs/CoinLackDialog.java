package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class CoinLackDialog extends BaseDialog
{
    TextureAtlas atlasStore;
    BaseScreen baseScreen;
    private ButtonListener btnListener;
    DialogStore dialogStore;
    MyImage imgYes;
    boolean isSwitch;
    Label labelInfo;
    
    public CoinLackDialog(final BaseScreen baseScreen) {
        this.isSwitch = false;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == CoinLackDialog.this.imgYes) {
                        CoinLackDialog.this.isSwitch = true;
                        CoinLackDialog.this.closeDialog(CoinLackDialog.this);
                        if (CoinLackDialog.this.dialogStore != null) {
                            CoinLackDialog.this.dialogStore.closeBuyWeaponDialog();
                        }
                    }
                    else if (listenerActor == CoinLackDialog.this.imgClose) {
                        CoinLackDialog.this.close();
                    }
                }
            }
        };
        this.baseScreen = baseScreen;
        this.init();
    }
    
    public CoinLackDialog(final DialogStore dialogStore) {
        this.isSwitch = false;
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == CoinLackDialog.this.imgYes) {
                        CoinLackDialog.this.isSwitch = true;
                        CoinLackDialog.this.closeDialog(CoinLackDialog.this);
                        if (CoinLackDialog.this.dialogStore != null) {
                            CoinLackDialog.this.dialogStore.closeBuyWeaponDialog();
                        }
                    }
                    else if (listenerActor == CoinLackDialog.this.imgClose) {
                        CoinLackDialog.this.close();
                    }
                }
            }
        };
        this.dialogStore = dialogStore;
        this.baseScreen = null;
        this.init();
    }
    
    private void closeDialog(final BaseDialog baseDialog) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.getShade().remove();
                baseDialog.remove();
                CoinLackDialog.this.switchItem();
            }
        })));
    }
    
    private void init() {
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, Assets.atlasPauseOver, "anniukuang2", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.imgYes = this.addItem(this, this.atlasStore, "yes", 90.0f, 15.0f, this.btnListener);
//        this.labelInfo = UiHandle.createRomanLabel(this, "Gold is not enough. Would \nyou like to buy more?", 30.0f, 85.0f);
        this.labelInfo = UiHandle.createRomanLabel(this, Language.goldIsNotEnough(), 30.0f, 85.0f);
        this.imgShade.setColor(MyShade.colorLackTran);
        this.setSizeOrigin();
    }
    
    private void switchItem() {
        if (this.isSwitch) {
            if (this.baseScreen instanceof WeaponEnhanceScreen) {
                ((WeaponEnhanceScreen)this.baseScreen).openStore(2);
            }
            else if (this.dialogStore != null) {
                this.dialogStore.selectItem(2);
            }
        }
    }
    
    public void close() {
        this.isSwitch = false;
        this.closeDialog(this);
    }
}
