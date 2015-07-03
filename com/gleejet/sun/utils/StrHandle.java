package com.gleejet.sun.utils;

import java.io.*;

public class StrHandle
{
    public static StringBuilder builder;
    
    static {
        StrHandle.builder = new StringBuilder();
    }
    
    public static String get(final int n) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(n);
        return StrHandle.builder.toString();
    }
    
    public static String get(final int n, final int n2) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(n).append(" , ").append(n2);
        return StrHandle.builder.toString();
    }
    
    public static String get(final int n, final String s) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(n).append(s);
        return StrHandle.builder.toString();
    }
    
    public static String get(final int n, final String s, final int n2) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(n).append(s).append(n2);
        return StrHandle.builder.toString();
    }
    
    public static String get(final String s, final int n) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(s).append(n);
        return StrHandle.builder.toString();
    }
    
    public static String get(final String s, final int n, final String s2) {
        StrHandle.builder.setLength(0);
        StrHandle.builder.append(s).append(n).append(s2);
        return StrHandle.builder.toString();
    }
    
    public static String get(final Object... array) {
        StrHandle.builder.setLength(0);
        for (int i = 0; i < array.length; ++i) {
            StrHandle.builder.append(array[i]);
        }
        return StrHandle.builder.toString();
    }
}
