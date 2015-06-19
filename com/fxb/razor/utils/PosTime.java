package com.fxb.razor.utils;

public class PosTime
{
    public float time;
    public float x;
    public float y;
    
    public PosTime() {
        this.set(0.0f, 0.0f, 0.0f);
    }
    
    public PosTime(final float n, final float n2, final float n3) {
        this.set(n, n2, n3);
    }
    
    public void set(final float x, final float y, final float time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}
