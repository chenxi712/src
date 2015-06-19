package com.fxb.razor.stages.dialogs;

import com.badlogic.gdx.graphics.g2d.*;
import com.fxb.razor.utils.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.utils.ui.*;

public class DialogCredits extends BaseDialog
{
    TextureAtlas atlasStart;
    private ButtonListener btnListener;
    
    public DialogCredits() {
        this.btnListener = new ButtonListener() {
            @Override
            public void touchUp(final InputEvent inputEvent, final float n, final float n2, final int n3, final int n4) {
                super.touchUp(inputEvent, n, n2, n3, n4);
                if (this.isDown) {
                    final Actor listenerActor = inputEvent.getListenerActor();
                    SoundHandle.playForButton2();
                    if (listenerActor == DialogCredits.this.imgClose) {
                        DialogCredits.this.closeHandle();
                    }
                }
            }
        };
        this.atlasStart = Global.manager.get("ui/ui_start.pack", TextureAtlas.class);
        this.imgBg = UiHandle.addItem(this, this.atlasStart, "credits", 0.0f, 0.0f);
        this.initClose(this.btnListener);
        this.imgClose.translate(0.0f, -47.0f);
        this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight() - 50.0f);
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setPosition((800.0f - this.getWidth()) / 2.0f, (480.0f - this.getHeight()) / 2.0f);
        this.imgShade.setColor(MyShade.colorLackTran);
    }
}
