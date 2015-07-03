package com.gleejet.sun.utils.ui;

import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;

public class AlphaPoint extends Vector2 implements Pool.Poolable
{
    public float alpha;
    
    public AlphaPoint() {
        this.alpha = 1.0f;
    }
    
    public AlphaPoint(final float n, final float n2) {
        super(n, n2);
        this.alpha = 1.0f;
    }
    
    @Override
    public void reset() {
        this.alpha = 1.0f;
    }
    
    @Override
    public AlphaPoint set(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
