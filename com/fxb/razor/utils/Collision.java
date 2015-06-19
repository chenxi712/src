package com.fxb.razor.utils;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class Collision
{
    public static float Distance(final float n, final float n2, final float n3, final float n4) {
        return (float)Math.sqrt((n3 - n) * (n3 - n) + (n4 - n2) * (n4 - n2));
    }
    
    public static boolean IsInside(float n, float n2, final Circle circle) {
        n -= circle.x;
        n2 -= circle.y;
        return n * n + n2 * n2 <= circle.radius * circle.radius;
    }
    
    public static boolean IsOverlap(final Actor actor, final Circle circle) {
        final float x = actor.getX();
        final float y = actor.getY();
        final float width = actor.getWidth();
        final float height = actor.getHeight();
        final float x2 = circle.x;
        final float y2 = circle.y;
        final float radius = circle.radius;
        final float n = x + width / 2.0f;
        final float n2 = y + height / 2.0f;
        return IsInside(x, y, circle) || IsInside(x + width, y, circle) || IsInside(x, y + height, circle) || IsInside(x + width, y + height, circle) || (Distance(n, n2, x2, y2) < width / 2.0f + radius && Math.abs(n2 - y2) < height / 2.0f) || (Distance(n, n2, x2, y2) < height / 2.0f + radius && Math.abs(n - x2) < width / 2.0f);
    }
    
    public static boolean IsOverlap(final Actor actor, final Actor actor2) {
        if (actor != null && actor2 != null) {
            final float x = actor.getX();
            final float y = actor.getY();
            final float width = actor.getWidth();
            final float height = actor.getHeight();
            final float x2 = actor2.getX();
            final float y2 = actor2.getY();
            final float width2 = actor2.getWidth();
            final float height2 = actor2.getHeight();
            if (x < x2 + width2 && x2 < x + width && y < y2 + height2 && y2 < y + height) {
                return true;
            }
        }
        return false;
    }
}
