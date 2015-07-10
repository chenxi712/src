package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.gleejet.sun.*;
import com.gleejet.sun.common.*;
import com.gleejet.sun.screens.*;
import com.gleejet.sun.utils.*;
import com.gleejet.sun.utils.ui.*;

public class GoldGetDialog extends BaseDialog
{
    static final int[][] goldss;
    TextureAtlas atlasLevelBg;
    TextureAtlas atlasStore;
    private ButtonListener btnListener;
    int goldLevel;
    int goldNum;
    MyImage imgYes;
    Label labelInfo;
    LevelSmallScreen levelSmallScreen;
    
    static {
        goldss = new int[][] { { 500, 750, 1000 }, { 500, 1000, 1500 }, { 1000, 1500, 2000 }, { 1500, 2000, 3000 }, { 2000, 3000, 5000 } };
    }
    
    public GoldGetDialog(final LevelSmallScreen levelSmallScreen) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == GoldGetDialog.this.imgYes) {
                        GoldGetDialog.this.yesHandle();
                        return;
                    }
                    if (listenerActor == GoldGetDialog.this.imgClose) {
                        GoldGetDialog.this.closeHandle(0.25f);
                    }
                }
            }
        };
        this.levelSmallScreen = levelSmallScreen;
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.atlasLevelBg, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
//        this.labelInfo = UiHandle.createRomanLabel(this, "Congratulations, You can get \n500 gold!", 50.0f, 85.0f);
        this.labelInfo = UiHandle.createRomanLabel(this, Language.get500Gold(), 50.0f, 85.0f);
        this.imgYes = UiHandle.addItem(this, this.atlasStore, "yes", 118.0f, 25.0f, this.btnListener);
        this.setSizeOrigin();
    }
    
    private int getRewardLevel() {
        return (Global.sceneLevel - 1) * 3 + this.goldLevel;
    }
    
    private void yesHandle() {
        this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                PreferHandle.writeLevelGoldGet(Global.sceneLevel, GoldGetDialog.this.goldLevel, true);
                GoldGetDialog.this.levelSmallScreen.refresh();
                GoldGetDialog.this.levelSmallScreen.getCoin(GoldGetDialog.this.goldNum, GoldGetDialog.this.goldLevel - 1);
//                FlurryHandle.boxCoin(GoldGetDialog.this.getRewardLevel());
                PreferHandle.writeCommon();
                GoldGetDialog.this.getShade().remove();
                GoldGetDialog.this.remove();
            }
        })));
    }
    
    public void setGoldLevel(final int goldLevel) {
        this.goldLevel = goldLevel;
        this.goldNum = GoldGetDialog.goldss[Global.sceneLevel - 1][this.goldLevel - 1];
//        this.labelInfo.setText(StrHandle.get("Congratulations, You can get \n", this.goldNum, " golds!"));
        this.labelInfo.setText(StrHandle.get(Language.getGold1(), this.goldNum, Language.getGold2()));
    }
}
