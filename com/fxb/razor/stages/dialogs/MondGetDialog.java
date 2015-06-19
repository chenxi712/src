package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.fxb.razor.screens.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.fxb.razor.common.*;
import com.fxb.razor.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.*;

public class MondGetDialog extends BaseDialog
{
    static final int[][] mondss;
    TextureAtlas atlasLevelBg;
    TextureAtlas atlasStore;
    private ButtonListener btnListener;
    MyImage imgYes;
    Label labelInfo;
    LevelSmallScreen levelSmallScreen;
    int mondLevel;
    int mondNum;
    
    static {
        mondss = new int[][] { { 3, 5, 10 }, { 5, 10, 15 }, { 10, 15, 20 }, { 15, 20, 30 }, { 20, 30, 50 } };
    }
    
    public MondGetDialog(final LevelSmallScreen levelSmallScreen) {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == MondGetDialog.this.imgYes) {
                        MondGetDialog.this.yesHandle();
                        return;
                    }
                    if (listenerActor == MondGetDialog.this.imgClose) {
                        MondGetDialog.this.closeHandle(0.25f);
                    }
                }
            }
        };
        this.levelSmallScreen = levelSmallScreen;
        this.atlasStore = Global.manager.get("ui/ui_store.pack", TextureAtlas.class);
        this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
        this.imgBg = this.addItem(this, this.atlasLevelBg, "waikuang", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.labelInfo = UiHandle.createRomanLabel(this, "Congratulations, You can get \n500 diamonds!", 50.0f, 85.0f);
        this.imgYes = UiHandle.addItem(this, this.atlasStore, "yes", 118.0f, 25.0f, this.btnListener);
        this.setSizeOrigin();
    }
    
    private int getRewardLevel() {
        return (Global.sceneLevel - 1) * 3 + this.mondLevel;
    }
    
    private void yesHandle() {
        this.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, 0.25f, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                PreferHandle.writeLevelMondGet(Global.sceneLevel, MondGetDialog.this.mondLevel, true);
                MondGetDialog.this.levelSmallScreen.refresh();
                MondGetDialog.this.levelSmallScreen.getMond(MondGetDialog.this.mondNum, MondGetDialog.this.mondLevel - 1);
//                FlurryHandle.boxMond(MondGetDialog.this.getRewardLevel());
                PreferHandle.writeCommon();
                MondGetDialog.this.getShade().remove();
                MondGetDialog.this.remove();
            }
        })));
    }
    
    public void setmondLevel(final int mondLevel) {
        this.mondLevel = mondLevel;
        this.mondNum = MondGetDialog.mondss[Global.sceneLevel - 1][this.mondLevel - 1];
        this.labelInfo.setText(StrHandle.get("Congratulations, You can get \n", this.mondNum, " diamonds!"));
    }
}
