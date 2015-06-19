package com.fxb.razor.common;

import com.fxb.razor.stages.dialogs.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class DialogHandle
{
    public static void closeDialog(final BaseDialog baseDialog) {
        closeDialog(baseDialog, 0.35f);
    }
    
    public static void closeDialog(final BaseDialog baseDialog, final float n) {
        baseDialog.addAction(Actions.sequence(Actions.scaleTo(0.0f, 0.0f, n, Interpolation.swingIn), Actions.run(new Runnable() {
            @Override
            public void run() {
                baseDialog.afterClose();
                baseDialog.getShade().remove();
                baseDialog.remove();
            }
        })));
    }
    
    public static void openDialog(final Stage stage, final BaseDialog baseDialog) {
        openDialog(stage, baseDialog, 0.4f);
    }
    
    public static void openDialog(final Stage stage, final BaseDialog baseDialog, final float n) {
        if (stage != null && baseDialog != null) {
            stage.addActor(baseDialog.getShade());
            final ScaleToAction scaleTo = Actions.scaleTo(1.0f, 1.0f, n, Interpolation.swingOut);
            baseDialog.setScale(0.0f);
            baseDialog.addAction(scaleTo);
            stage.addActor(baseDialog);
        }
    }
}
