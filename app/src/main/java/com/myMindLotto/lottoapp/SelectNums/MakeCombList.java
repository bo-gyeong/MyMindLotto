package com.myMindLotto.lottoapp.SelectNums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.lottoapp.R;

import java.util.List;

// 조합 후 저장한 번호 리스트 관련 코드
public class MakeCombList extends BaseAdapter {

    List<ListItem> listData = null;
    int count = 0;

    public MakeCombList(List<ListItem> listData)
    {
        this.listData = listData;
        count = listData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return listData.get(position);
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
            convertView = layoutInflater.inflate(R.layout.comb_list_custom, parent, false);
        }

        TextView titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);

        titleTextView.setText(listData.get(position).titleTextView);
        dateTextView.setText(listData.get(position).dateTextView);

        return convertView;
    }
}

class ListItem {
    public String titleTextView;
    public String dateTextView;
}