package com.fxb.razor.utils.action;

import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.scenes.scene2d.*;

public class LineAction extends TemporalAction
{
    int count;
    int end;
    float length;
    float[] percents;
    Vector2[] points;
    int start;
    int step;
    
    public LineAction() {
        this.length = 0.0f;
    }
    
    public static LineAction create(final Vector2[] points, final float duration) {
        final Pool<LineAction> value = Pools.get(LineAction.class);
        final LineAction lineAction = value.obtain();
        lineAction.setPool(value);
        lineAction.setDuration(duration);
        lineAction.setPoints(points);
        lineAction.setStartIndex(0);
        lineAction.setEndIndex(points.length - 1);
        return lineAction;
    }
    
    public static LineAction create(final Vector2[] points, final int startIndex, final int endIndex, final float duration) {
        final Pool<LineAction> value = Pools.get(LineAction.class);
        final LineAction lineAction = value.obtain();
        lineAction.setPool(value);
        lineAction.setDuration(duration);
        lineAction.setPoints(points);
        lineAction.setStartIndex(startIndex);
        lineAction.setEndIndex(endIndex);
        return lineAction;
    }
    
    private float getDis(final Vector2 v1, final Vector2 v2) {
        final float n = v2.x - v1.x;
        final float n2 = v2.y - v1.y;
        return (float)Math.sqrt(n * n + n2 * n2);
    }
    
    private int getIndex(final float n) {
        for (int i = 0; i < this.percents.length - 1; ++i) {
            if (n >= this.percents[i] && n <= this.percents[i + 1]) {
                return i;
            }
        }
        return -1;
    }
    
    public static void setCenter(final Actor actor, final float n, final float n2) {
        actor.setPosition(n - actor.getWidth() / 2.0f, n2 - actor.getHeight() / 2.0f);
    }
    
    @Override
    protected void begin() {
        super.begin();
        this.length = 0.0f;
        this.count = Math.abs(this.end - this.start) + 1;
        int step;
        if (this.start < this.end) {
            step = 1;
        }
        else {
            step = -1;
        }
        this.step = step;
        for (int i = 0; i < this.count - 1; ++i) {
            this.length += this.getDis(this.points[this.start + this.step * i], this.points[this.start + (i + 1) * this.step]);
        }
        (this.percents = new float[this.count])[0] = 0.0f;
        for (int j = 1; j < this.count; ++j) {
            this.percents[j] = this.percents[j - 1] + this.getDis(this.points[this.start + (j - 1) * this.step], this.points[this.start + this.step * j]) / this.length;
        }
        this.percents[this.count - 1] = 1.0f;
    }
    
    public void setEndIndex(final int end) {
        this.end = end;
    }
    
    public void setPoints(final Vector2[] points) {
        this.points = points;
    }
    
    public void setStartIndex(final int start) {
        this.start = start;
    }
    
    @Override
    protected void update(float n) {
        final int index = this.getIndex(n);
        n = (n - this.percents[index]) / (this.percents[index + 1] - this.percents[index]);
        final int n2 = this.start + this.step * index;
        final int n3 = this.start + (index + 1) * this.step;
        setCenter(this.actor, this.points[n2].x + (this.points[n3].x - this.points[n2].x) * n, this.points[n2].y + (this.points[n3].y - this.points[n2].y) * n);
    }
}
