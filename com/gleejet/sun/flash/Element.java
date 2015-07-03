package com.gleejet.sun.flash;

public class Element
{
    public float alphaMultiplier;
    public String textureName;
    public Vertex4 vertex4;
    
    public Element(final String textureName, final Vertex4 vertex4, final float alphaMultiplier) {
        this.alphaMultiplier = 1.0f;
        this.textureName = textureName;
        this.vertex4 = vertex4;
        this.alphaMultiplier = alphaMultiplier;
    }
}
