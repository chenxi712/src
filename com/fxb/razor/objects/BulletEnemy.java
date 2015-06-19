package com.fxb.razor.objects;

import com.badlogic.gdx.utils.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;

public class BulletEnemy extends Bullet
{
    Array<Vector2> arrPoint;
    public Constant.BulletEnemyType bulletType;
    float damage;
    public Mesh mesh;
    int tracePointNum;
    
    public BulletEnemy() {
        this.tracePointNum = 12;
        this.mesh = new Mesh(false, 600, 600, new VertexAttribute[] { new VertexAttribute(1, 2, "a_position"), new VertexAttribute(16, 2, "a_texCoord") });
        this.arrPoint = new Array<Vector2>();
    }
    
    @Override
    public void Clear() {
        super.Clear();
        this.bulletType = Constant.BulletEnemyType.Common;
        this.arrPoint.clear();
        this.mesh.setVertices(new float[] { 200.0f, 200.0f, 0.0f, 1.0f });
        this.mesh.setIndices(new short[] { 0 });
    }
    
    @Override
    public void act(final float n) {
        super.act(n);
        if (this.isAddTrace) {
            this.traceHandle();
        }
    }
    
    public float[] createInputVertice() {
        float[] array;
        if (this.arrPoint.size < 1) {
            array = null;
        }
        else {
            final float[] array2 = new float[(this.arrPoint.size - 1) * 8];
            final int n = array2.length / 8;
            int n2 = 0;
            while (true) {
                array = array2;
                if (n2 >= n) {
                    break;
                }
                Vector2 vector2 = this.arrPoint.get(n2 + 1);
                vector2 = this.arrPoint.get(n2);
                final float n3 = MathUtils.atan2(vector2.y - vector2.y, vector2.x - vector2.x) * 57.295776f;
                array2[n2 * 8] = vector2.x + MathUtils.sinDeg(n3) * 12.0f;
                array2[n2 * 8 + 1] = vector2.y - MathUtils.cosDeg(n3) * 12.0f;
                array2[n2 * 8 + 2] = n2 * 1.0f / n;
                array2[n2 * 8 + 3] = 1.0f;
                array2[n2 * 8 + 4] = vector2.x - MathUtils.sinDeg(n3) * 12.0f;
                array2[n2 * 8 + 5] = vector2.y + MathUtils.cosDeg(n3) * 12.0f;
                array2[n2 * 8 + 6] = n2 * 1.0f / n;
                array2[n2 * 8 + 7] = 0.0f;
                ++n2;
            }
        }
        return array;
    }
    
    public float getDamage() {
        return this.damage;
    }
    
    @Override
    protected boolean isSetAngle() {
        return this.bulletType != Constant.BulletEnemyType.Freezecar && this.bulletType != Constant.BulletEnemyType.Machine;
    }
    
    public void setDamage(final float damage) {
        this.damage = damage;
    }
    
    public void setTracePointNum(final int tracePointNum) {
        this.tracePointNum = tracePointNum;
    }
    
    public void setVertice(final float[] vertices) {
        if (vertices.length < 8) {
            return;
        }
        this.mesh.setVertices(vertices);
        final short[] indices = new short[vertices.length / 4 - 1];
        for (short n = 0; n < indices.length; ++n) {
            indices[n] = n;
        }
        this.mesh.setIndices(indices);
    }
    
    public void traceHandle() {
        while (this.arrPoint.size > this.tracePointNum) {
            this.arrPoint.removeIndex(0);
        }
        final float rotation = this.getRotation();
        final float n = this.getHeight() / 2.0f;
        final float cosDeg = MathUtils.cosDeg(rotation);
        final float sinDeg = MathUtils.sinDeg(rotation);
        this.arrPoint.add(new Vector2(this.getX() + (0.0f * cosDeg - n * sinDeg), this.getY() + (0.0f * sinDeg + n * cosDeg)));
        this.setVertice(this.createInputVertice());
    }
}
