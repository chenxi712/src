package com.gleejet.sun.utils.action;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class BlinkAction
{
    public static Action blink(final float n) {
        return Actions.forever(Actions.sequence(Actions.delay(n), Actions.visible(false), Actions.delay(n), Actions.visible(true)));
    }
    
    public static Action scaleUpDown(final float n, final float n2, final float n3) {
        return Actions.forever(Actions.sequence(Actions.scaleTo(n2, n2, n3), Actions.scaleTo(n, n, n3)));
    }
}
