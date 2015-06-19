package com.fxb.razor.utils.loader;

import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.assets.*;
import com.fxb.razor.common.*;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.*;

public class MyTextureLoader extends AsynchronousAssetLoader<Texture, TextureLoader.TextureParameter>
{
    TextureLoaderInfo info;
    
    public MyTextureLoader(final FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
        this.info = new TextureLoaderInfo();
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies(final String s, final FileHandle fileHandle, final TextureLoader.TextureParameter textureParameter) {
        return null;
    }
    
    @Override
    public void loadAsync(final AssetManager assetManager, final String filename, final FileHandle fileHandle, final TextureLoader.TextureParameter textureParameter) {
        this.info.filename = filename;
        if (textureParameter != null && textureParameter.textureData != null) {
            this.info.data = textureParameter.textureData;
            if (!this.info.data.isPrepared()) {
                this.info.data.prepare();
            }
            this.info.texture = textureParameter.texture;
            return;
        }
        Enum<Pixmap.Format> enum1 = null;
        boolean genMipMaps = false;
        this.info.texture = null;
        if (textureParameter != null) {
            if (Global.isUseRGBA4444) {
                enum1 = Pixmap.Format.RGBA4444;
            }
            else {
                enum1 = textureParameter.format;
            }
            genMipMaps = textureParameter.genMipMaps;
            this.info.texture = textureParameter.texture;
        }
        if (!filename.contains(".etc1")) {
            Pixmap cim;
            if (filename.contains(".cim")) {
                cim = PixmapIO.readCIM(fileHandle);
            }
            else {
                cim = new Pixmap(fileHandle);
            }
            this.info.data = new FileTextureData(fileHandle, cim, (Pixmap.Format)enum1, genMipMaps);
            return;
        }
        this.info.data = new ETC1TextureData(fileHandle, genMipMaps);
    }
    
    @Override
    public Texture loadSync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final TextureLoader.TextureParameter textureParameter) {
        Texture texture;
        if (this.info == null) {
            texture = null;
        }
        else {
            Texture texture2 = this.info.texture;
            if (texture2 != null) {
                texture2.load(this.info.data);
            }
            else {
                texture2 = new Texture(this.info.data);
            }
            texture = texture2;
            if (textureParameter != null) {
                texture2.setFilter(textureParameter.minFilter, textureParameter.magFilter);
                texture2.setWrap(textureParameter.wrapU, textureParameter.wrapV);
                return texture2;
            }
        }
        return texture;
    }
    
    public static class TextureLoaderInfo
    {
        TextureData data;
        String filename;
        Texture texture;
    }
}
