package com.gleejet.sun.flash;

import com.badlogic.gdx.math.*;

public interface Flash
{
    Vector2 getPosition();
    
    float getTimePosition();
    
    boolean isLooping();
    
    boolean isPlaying();
    
    void pause();
    
    void play();
    
    void setLooping(boolean p0);
    
    void setPosition(Vector2 p0);
    
    void stop();
}
