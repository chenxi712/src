package com.fxb.razor.flash;

public class Frame
{
  public Element[] elements;
  public int elementsNumber = 0;
  public float endTime = 0.0F;
  public float startTime = 0.0F;
  
  public Frame(float paramFloat1, float paramFloat2, int paramInt)
  {
    this.startTime = paramFloat1;
    this.endTime = paramFloat2;
    this.elementsNumber = paramInt;
    this.elements = new Element[paramInt];
  }
}


/* Location:              E:\Dex\dex2jar-2.0\classes-dex2jar.jar!\com\fxb\razor\flash\Frame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */