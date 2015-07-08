package com.gleejet.sun.stages.dialogs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.gleejet.sun.common.DialogHandle;
import com.gleejet.sun.common.Global;
import com.gleejet.sun.common.PreferHandle;
import com.gleejet.sun.common.SoundHandle;
import com.gleejet.sun.screens.BaseScreen;
import com.gleejet.sun.utils.ButtonListener;

public class DialogGiftBox extends BaseDialog 
{
	private ButtonListener btnListener;
	
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
                }
            }
        };
		init ();
	}
	
	private void init () {
		 this.initClose(this.btnListener);
	}
	
	public void close() {
        DialogHandle.closeDialog(this, 0.35f);
    }
	
	private void buy () {
		/*
		 *             Global.arrMainGunGet.add(groupWeapon.getName());
            PreferHandle.checkSame(Global.arrMainGunGet);
            PreferHandle.writeWeaponGet();
            PreferHandle.writeCommon();
		 * */
	}
}
