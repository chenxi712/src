package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gleejet.sun.common.DialogHandle;
import com.gleejet.sun.common.Global;
import com.gleejet.sun.common.PreferHandle;
import com.gleejet.sun.common.SoundHandle;
import com.gleejet.sun.screens.BaseScreen;
import com.gleejet.sun.utils.ButtonListener;
import com.gleejet.sun.utils.action.TouchAction;
import com.gleejet.sun.utils.ui.MyImage;

public class DialogGiftBox extends BaseDialog 
{
	private ButtonListener btnListener;
	private TextureAtlas atlasStart;
	private TextureAtlas atlasLibao;
	private TextureAtlas atlasLevelBg;
	private MyImage[] imgItems;
	private MyImage imgBuy;
	private MyImage imgTitleBg;
	private MyImage imgTitle;
	
	public DialogGiftBox (BaseScreen baseScreen) {
		this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == imgClose) {
                        close();
                    }
                    else if (listenerActor == imgBuy) {
                    	buy();
                    }
                }
            }
        };
		init();
	}
	
	private void init() {
		 this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
		 this.atlasLibao = Global.manager.get("ui/ui_libao.pack", TextureAtlas.class);
		 this.atlasLevelBg = Global.manager.get("ui/ui_level_bg.pack", TextureAtlas.class);
	     this.imgBg = this.addItem(this, this.atlasStart, "waikuang", 0.0f, 0.0f);
	     imgBuy = this.addItem(this, this.atlasLibao, "lingqu", 450, 0);
	     imgBuy.addListener(this.btnListener);
	     imgTitleBg = this.addItem(this, this.atlasLevelBg, "hengfu1", 170, 250);
	     imgTitle = this.addItem(this, this.atlasLibao, "libao_biaoti", 286, 278);
		 this.initClose(this.btnListener);
		 this.setPosition(61.0f, 80.0f);
	     this.setSizeOrigin();
	}
	
	public void close() {
        DialogHandle.closeDialog(this, 0.35f);
    }
	
	public void openDialog(Stage stage) {
         DialogHandle.openDialog(stage, this);
    }
	
	private void buy() {
		/*
		 *             Global.arrMainGunGet.add(groupWeapon.getName());
            PreferHandle.checkSame(Global.arrMainGunGet);
            PreferHandle.writeWeaponGet();
            PreferHandle.writeCommon();
		 * */
	}
	
	private void initItems() {
		this.imgItems = new MyImage[5];
	}
}
