package com.fxb.razor.utils.ui;

import com.badlogic.gdx.graphics.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class MyShade extends MyImage
{
    public static Color colorBlack;
    public static Color colorHalfTran;
    public static Color colorLackTran;
    public static Color colorLittleTran;
    public static Color colorStoreTran;
    public static Color colorTran;
    public static Color colorTran1;
    public static Color colorTran2;
    public static Color colorTran3;
    public static Color colorTran4;
    public static Color colorTran5;
    
    static {
        MyShade.colorTran = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        MyShade.colorLittleTran = new Color(0.0f, 0.0f, 0.0f, 0.1f);
        MyShade.colorStoreTran = new Color(0.0f, 0.0f, 0.0f, 0.2f);
        MyShade.colorLackTran = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        MyShade.colorBlack = new Color(0.0f, 0.0f, 0.0f, 0.84f);
        MyShade.colorHalfTran = new Color(0.0f, 0.0f, 0.0f, 0.65f);
        MyShade.colorTran1 = new Color(0.0f, 0.0f, 0.0f, 0.1f);
        MyShade.colorTran2 = new Color(0.0f, 0.0f, 0.0f, 0.2f);
        MyShade.colorTran3 = new Color(0.0f, 0.0f, 0.0f, 0.3f);
        MyShade.colorTran4 = new Color(0.0f, 0.0f, 0.0f, 0.4f);
        MyShade.colorTran5 = new Color(0.0f, 0.0f, 0.0f, 0.5f);
    }
    
    public MyShade() {
        super(Assets.regionWhite1);
        this.setSize(1000.0f, 680.0f);
        this.setPosition(-100.0f, -100.0f);
    }
    
    public static MyShade createShade(final Group group) {
        final MyShade myShade = new MyShade();
        myShade.setColor(MyShade.colorHalfTran);
        myShade.translate(-group.getX(), -group.getY());
        group.addActor(myShade);
        return myShade;
    }
    
    public static MyShade createShade(final Stage stage) {
        final MyShade myShade = new MyShade();
        myShade.setColor(MyShade.colorHalfTran);
        stage.addActor(myShade);
        return myShade;
    }
    
    public void fadeInRemove(final float n) {
        final ColorAction color = Actions.color(MyShade.colorTran, n, Interpolation.pow2Out);
        final RemoveActorAction removeActor = Actions.removeActor();
        this.setColor(MyShade.colorBlack);
        this.addAction(Actions.sequence(color, removeActor));
    }
    
    public void fadeOutRemove(final float n) {
        final ColorAction color = Actions.color(MyShade.colorBlack, n, Interpolation.pow2In);
        this.setColor(MyShade.colorTran);
        this.addAction(color);
    }
    
    public void halfTran() {
        this.setColor(MyShade.colorHalfTran);
    }
}
