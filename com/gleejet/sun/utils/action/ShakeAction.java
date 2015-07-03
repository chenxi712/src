package com.gleejet.sun.utils.action;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class ShakeAction
{
    public static Action shake1() {
        final Action[] array = new Action[8];
        array[0] = Actions.moveBy(0.0f, -5.0f / 2.0f, 0.03f);
        for (int i = 1; i < array.length - 1; ++i) {
            float n;
            if (i % 2 == 0) {
                n = -1.0f;
            }
            else {
                n = 1.0f;
            }
            array[i] = Actions.moveBy(0.0f, (i * 1.0f + 5.0f) * n, 0.03f);
        }
        array[7] = Actions.moveTo(0.0f, 0.0f, 0.03f);
        return Actions.sequence(array);
    }
    
    public static Action shake2() {
        final Action[] array = new Action[12];
        array[0] = Actions.moveBy(0.0f, 5.0f / 2.0f, 0.03f);
        for (int i = 1; i < array.length - 1; ++i) {
            float n;
            if (i % 2 == 0) {
                n = -1.0f;
            }
            else {
                n = 1.0f;
            }
            array[i] = Actions.moveBy(0.0f, (i * 1.0f + 5.0f) * n, 0.03f);
        }
        array[11] = Actions.moveTo(0.0f, 0.0f, 0.03f);
        return Actions.sequence(array);
    }
    
    public static Action shake3() {
        final Action[] array = new Action[22];
        array[0] = Actions.moveBy(0.0f, 5.0f / 2.0f, 0.03f);
        for (int i = 1; i < array.length - 1; ++i) {
            float n;
            if (i % 2 == 0) {
                n = -1.0f;
            }
            else {
                n = 1.0f;
            }
            array[i] = Actions.moveBy(0.0f, (i * 1.0f + 5.0f) * n, 0.03f);
        }
        array[21] = Actions.moveTo(0.0f, 0.0f, 0.03f);
        return Actions.sequence(array);
    }
}
