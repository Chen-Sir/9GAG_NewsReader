package com.chen.jokesreader.utils.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

import com.chen.jokesreader.utils.typeface.FactoryTypeface;


/**
 * This manager class is on charge for typeface caching and dispatching.
 *
 * Uses a factory to create the typefaces that are not created yet and store them as soon as they are created.
 *
 */
public class ManagerTypeface
{
    private static final SparseArray<Typeface> typefacesCache = new SparseArray<>();

    public static Typeface getTypeface(Context context, int typeface)
    {
        synchronized(typefacesCache)
        {
            if(typefacesCache.indexOfKey(typeface) < 0)
            {
                typefacesCache.put(typeface, FactoryTypeface.createTypeface(context, typeface));
            }

            return typefacesCache.get(typeface);
        }
    }
}
