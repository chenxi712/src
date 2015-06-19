package com.fxb.razor.stages.dialogs;

import com.fxb.razor.utils.ui.*;
import com.fxb.razor.screens.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.fxb.razor.common.*;

public abstract class BaseDialog extends Group
{
    protected MyImage imgBg;
    protected MyImage imgClose;
    protected MyShade imgShade;
    
    public BaseDialog() {
        (this.imgShade = new MyShade()).setColor(MyShade.colorHalfTran);
    }
    
    public void addFadeOutAction(final BaseDialog baseDialog, final float n) {
        if (baseDialog == null) {
            Global.game.setNextScreen(new LoadingScreen(Global.game));
            return;
        }
        if (baseDialog.getStage() == null) {
            Global.game.setNextScreen(new LoadingScreen(Global.game));
            return;
        }
        final MyShade myShade = new MyShade();
        myShade.fadeOutRemove(n);
        myShade.setTouchable(Touchable.disabled);
        myShade.setName("fadeOutShade");
        baseDialog.getStage().addActor(myShade);
        MyMethods.delayRun(baseDialog, new Runnable() {
            @Override
            public void run() {
                Global.game.setNextScreen(new LoadingScreen(Global.game));
            }
        }, 0.05f + n);
    }
    
    public MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2) {
        return this.addItem(group, textureAtlas, s, n, n2, null);
    }
    
    public MyImage addItem(final Group group, final TextureAtlas textureAtlas, final String s, final float n, final float n2, final InputListener inputListener) {
        final MyImage myImage = new MyImage(textureAtlas.findRegion(s));
        myImage.setPosition(n, n2);
        if (inputListener != null) {
            myImage.addListener(inputListener);
        }
        group.addActor(myImage);
        return myImage;
    }
    
    public void afterClose() {
    }
    
    public void closeHandle() {
        this.closeHandle(0.35f);
    }
    
    public void closeHandle(final float n) {
        DialogHandle.closeDialog(this, n);
    }
    
    public final MyImage getShade() {
        return this.imgShade;
    }
    
    protected void initClose(final InputListener inputListener) {
        this.imgClose = this.addItem(this, Assets.atlasStore, "guanbi", this.imgBg.getWidth() - 38.0f - 5.0f, this.imgBg.getHeight() - 38.0f - 5.0f);
        if (inputListener != null) {
            this.imgClose.addListener(inputListener);
        }
    }
    
    public void setSizeOrigin() {
        this.setSize(this.imgBg.getWidth(), this.imgBg.getHeight());
        this.setOrigin(this.getWidth() / 2.0f, this.getHeight() / 2.0f);
        this.setPosition((800.0f - this.getWidth()) / 2.0f, (480.0f - this.getHeight()) / 2.0f);
    }
}
