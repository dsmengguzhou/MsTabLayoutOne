package com.musi.awe.mstablayoutone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by awe on 2018/1/2.
 */

public class Fragment1 extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1, null);
        return view;
    }
}
