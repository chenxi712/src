package com.gleejet.sun.utils.action;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class TouchAction
{
    public static Color colorDown;
    public static Color colorDown1;
    public static Color colorUp;
    public static float scaleDown;
    public static float scaleUp;
    
    static {
        TouchAction.scaleDown = 0.93f;
        TouchAction.scaleUp = 1.0f;
        TouchAction.colorDown = new Color(0.6f, 0.6f, 0.6f, 0.9f);
        TouchAction.colorDown1 = new Color(0.9f, 0.9f, 0.9f, 0.9f);
        TouchAction.colorUp = Color.WHITE;
    }
    
    public static Action downAction() {
        return downAction(0.05f);
    }
    
    public static Action downAction(final float n) {
        return downAction(n, TouchAction.colorDown);
    }
    
    public static Action downAction(final float n, final Color color) {
        return Actions.parallel(Actions.parallel(Actions.scaleTo(TouchAction.scaleDown, TouchAction.scaleDown, n), Actions.color(color, n)));
    }
    
    public static Action downAction(final Color color) {
        return downAction(0.05f, color);
    }
    
    public static Action upAction() {
        return upAction(0.05f);
    }
    
    public static Action upAction(final float n) {
        return upAction(n, TouchAction.colorUp);
    }
    
    public static Action upAction(final float n, final Color color) {
        return Actions.parallel(Actions.parallel(Actions.scaleTo(TouchAction.scaleUp, TouchAction.scaleUp, n), Actions.color(color, n)));
    }
    
    public static Action upAction(final Color color) {
        return upAction(0.05f, color);
    }
}
