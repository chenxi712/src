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
	private MyImage[] imgItems;
	private MyImage imgBuy;
	
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
	     this.imgBg = this.addItem(this, this.atlasStart, "waikuang", 0.0f, 0.0f);
	     imgBg.setScale(2f);
	     imgBuy = this.addItem(this, this.atlasStart, "receive_on", 286, 47);
	     imgBuy.addListener(this.btnListener);
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
