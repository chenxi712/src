package com.gleejet.sun.flash;

public class Vertex4
{
    public float x0;
    public float x1;
    public float x2;
    public float x3;
    public float y0;
    public float y1;
    public float y2;
    public float y3;
    
    public Vertex4() {
        this.x0 = 0.0f;
        this.y0 = 0.0f;
        this.x1 = 0.0f;
        this.y1 = 0.0f;
        this.x2 = 0.0f;
        this.y2 = 0.0f;
        this.x3 = 0.0f;
        this.y3 = 0.0f;
    }
    
    public Vertex4(final float x0, final float y0, final float x, final float y, final float x2, final float y2, final float x3, final float y3) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x;
        this.y1 = y;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }
}
