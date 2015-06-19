package com.fxb.razor.flash;

public class Layer
{
    public Frame[] frames;
    public int framesNumber;
    public String layerName;
    
    public Layer(final String layerName, final int framesNumber) {
        this.layerName = layerName;
        this.framesNumber = framesNumber;
        this.frames = new Frame[framesNumber];
    }
}
