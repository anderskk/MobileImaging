package com.mobileimaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<>();
    private final LayoutInflater mInflater;

    public GridViewAdapter(Context context, List<Bitmap> bitmaps) {
        mInflater = LayoutInflater.from(context);
        mItems.clear();

        int j = 1;
        for (Bitmap bitmap : bitmaps) {
            mItems.add(new Item("Image # " + j, bitmap));
            j++;
        }
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public Bitmap getPhoto(int i) {
        return mItems.get(i).bitmap;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageBitmap(item.bitmap);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final Bitmap bitmap;

        Item(String name, Bitmap bitmap) {
            this.name = name;
            this.bitmap = bitmap;
        }
    }
}