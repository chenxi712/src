package com.gleejet.sun.flash;

public class FlashElements
{
    public float endTime;
    public float height;
    public Layer[] layers;
    public int layersNumer;
    public String name;
    public int state;
    public float width;
    
    public FlashElements(final int layersNumer, final float width, final float height, final float endTime, final String name) {
        this.layersNumer = 0;
        this.width = 0.0f;
        this.height = 0.0f;
        this.endTime = 0.0f;
        this.layersNumer = layersNumer;
        this.layers = new Layer[layersNumer];
        this.width = width;
        this.height = height;
        this.endTime = endTime;
        this.name = name;
    }
}
