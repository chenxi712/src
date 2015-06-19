package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.fxb.razor.utils.ui.*;
import com.fxb.razor.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class BuyCoinDialog extends BaseDialog
{
    TextureAtlas atlasEnhance;
    TextureAtlas atlasStore;
    private ButtonListener btnListener;
    float coinNum;
    DialogStore dialogStore;
    MyImage imgYes;
    Label labelInfo1;
    Label labelInfo2;
    int level;
    float mondNum;
    
    public BuyCoinDialog(final DialogStore dialogStore) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == BuyCoinDialog.this.imgYes) {
                        BuyCoinDialog.this.buyCoin();
                        BuyCoinDialog.this.closeDialog(BuyCoinDialog.this);
                        return;
                    }
                    if (listenerActor == BuyCoinDialog.this.imgClose) {
                        BuyCoinDialog.this.close();
                    }
                }
            }
        };
        this.dialogStore = dialogStore;
        this.init();
        this.imgShade.setColor(MyShade.colorLittleTran);
    }
    
    private void buyCoin() {
        Global.totalMondNum -= this.mondNum;
        Global.totalCoinNum += this.coinNum;
        if (this.dialogStore != null) {
            this.dialogStore.coinMondChange();
        }
        PreferHandle.writeCommon();
//        FlurryHandle.buyCoin(this.level);
    }
    
    private void init() {
        this.atlasEnhance = Global.manager.get("ui/ui_weapon_enhance.pack", TextureAtlas.class);
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        (this.imgBg = this.addItem(this, Assets.atlasPauseOver, "waikuang", 0.0f, 0.0f)).setSize(this.imgBg.getWidth() * 0.8f, this.imgBg.getHeight() * 0.8f);
        this.initClose(this.btnListener);
        this.labelInfo1 = UiHandle.createRomanLabel(this, "", 48.0f, 140.0f);
        this.labelInfo2 = UiHandle.createRomanLabel(this, "", 48.0f, 110.0f);
        this.imgYes = this.addItem(this, this.atlasStore, "yes", 100.0f, 30.0f, this.btnListener);
        this.imgShade.setColor(MyShade.colorStoreTran);
        this.setSizeOrigin();
    }
    
    public void close() {
        this.closeDialog(this);
    }
    
    public void closeDialog(final BaseDialog baseDialog) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.35f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.getShade().remove();
                baseDialog.remove();
            }
        })));
    }
    
    public void setMondCoin(final float mondNum, final float coinNum, final int level) {
        this.mondNum = mondNum;
        this.coinNum = coinNum;
        this.level = level;
        this.labelInfo1.setText("Do you want to spend " + (int)this.mondNum);
        this.labelInfo2.setText("diamond to buy " + (int)this.coinNum + " gold?");
    }
}
