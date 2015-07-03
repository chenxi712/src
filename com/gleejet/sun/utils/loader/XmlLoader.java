package com.gleejet.sun.utils.loader;

import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.*;

public class XmlLoader extends AsynchronousAssetLoader<XmlReader.Element, XmlParameter>
{
    private XmlReader.Element element;
    
    public XmlLoader(final FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies(final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.XmlParameter xmlParameter) {
        return null;
    }
    
    @Override
    public void loadAsync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.XmlParameter xmlParameter) {
        try {
            this.element = new XmlReader().parse(Gdx.files.internal(s));
        }
        catch (Exception ex) {
            this.element = null;
        }
    }
    
    @Override
    public XmlReader.Element loadSync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.XmlParameter xmlParameter) {
        return this.element;
    }
    
    public static class XmlParameter extends AssetLoaderParameters<XmlReader.Element>
    {
    }
}
