package com.fxb.razor.utils.action;

import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.actions.*;

public class CircleAction
{
    public static Action blink() {
        return Actions.forever(Actions.sequence(Actions.scaleTo(0.85f, 0.85f, 0.3f, Interpolation.linear), Actions.scaleTo(1.15f, 1.15f, 0.3f, Interpolation.linear)));
    }
}
