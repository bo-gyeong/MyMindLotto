package com.myMindLotto.lottoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lottoapp.R;

import java.util.ArrayList;

// main페이지 하단 리스트 관련 코드
public class MakeMainList extends BaseAdapter {

    ArrayList<MainItem> mainItemData = null;
    int count = 0;

    public MakeMainList(ArrayList<MainItem> mainItemData)
    {
        this.mainItemData = mainItemData ;
        count = mainItemData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return mainItemData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Context context = parent.getContext();
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.main_list_custom, parent, false);
        }

        ImageView listIconImgView = (ImageView) convertView.findViewById(R.id.listIconImgView);
        TextView listItemTextView = (TextView) convertView.findViewById(R.id.listItemTextView);

        listIconImgView.setImageResource(mainItemData.get(position).listIconImgView);
        listItemTextView.setText(mainItemData.get(position).listItemTextView);

        return convertView;
    }
}

class MainItem {
    public int listIconImgView;
    public String listItemTextView;
}