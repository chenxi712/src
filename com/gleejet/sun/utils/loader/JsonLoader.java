package com.gleejet.sun.utils.loader;

import com.badlogic.gdx.assets.loaders.*;
import com.badlogic.gdx.files.*;
import com.badlogic.gdx.assets.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.*;

public class JsonLoader extends AsynchronousAssetLoader<JsonValue, JsonParameter>
{
    private JsonValue value;
    
    public JsonLoader(final FileHandleResolver fileHandleResolver) {
        super(fileHandleResolver);
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies(final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.JsonParameter jsonParameter) {
        return null;
    }
    
    @Override
    public void loadAsync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.JsonParameter jsonParameter) {
        try {
            this.value = new JsonReader().parse(Gdx.files.internal(s).reader("UTF-8"));
        }
        catch (Exception ex) {
            this.value = null;
        }
    }
    
    @Override
    public JsonValue loadSync(final AssetManager assetManager, final String s, final FileHandle fileHandle, final com.gleejet.sun.utils.loader.JsonParameter jsonParameter) {
        return this.value;
    }
    
    public static class JsonParameter extends AssetLoaderParameters<JsonValue>
    {
    }
}
