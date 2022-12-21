package com.myMindLotto.lottoapp.SeeLottoNumListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lottoapp.R;
import com.myMindLotto.lottoapp.common.SetColor;

import java.util.ArrayList;

// main 페이지에서 저장한 번호 리스트 관련 코드
public class MakeLottoNumList extends BaseAdapter {

    ArrayList<Nums> numsData = null;
    int count = 0;

    public MakeLottoNumList(ArrayList<Nums> numsData)
    {
        this.numsData = numsData;
        count = numsData.size();
    }

    @Override
    public int getCount()
    {
        return count;
    }

    @Override
    public Object getItem(int position)
    {
        return numsData.get(position);
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
            convertView = layoutInflater.inflate(R.layout.lottonum_list_custom, parent, false);
        }

        ImageView[] numImg = new ImageView[6];
        Integer[] numImgId = new Integer[]{R.id.num1ImgView, R.id.num2ImgView
                , R.id.num3ImgView, R.id.num4ImgView, R.id.num5ImgView, R.id.num6ImgView};

        TextView[] numTxt = new TextView[6];
        Integer[] numTxtId = new Integer[]{R.id.num1TextView, R.id.num2TextView
                , R.id.num3TextView, R.id.num4TextView, R.id.num5TextView, R.id.num6TextView};

        for (int i=0; i<numImg.length; i++) {
            numImg[i] = (ImageView) convertView.findViewById(numImgId[i]);
            numTxt[i] = (TextView) convertView.findViewById(numTxtId[i]);
        }

        numTxt[0].setText(String.valueOf(numsData.get(position).num1TextView));
        numTxt[1].setText(String.valueOf(numsData.get(position).num2TextView));
        numTxt[2].setText(String.valueOf(numsData.get(position).num3TextView));
        numTxt[3].setText(String.valueOf(numsData.get(position).num4TextView));
        numTxt[4].setText(String.valueOf(numsData.get(position).num5TextView));
        numTxt[5].setText(String.valueOf(numsData.get(position).num6TextView));

        SetColor setColor = new SetColor();

        for (int i=0; i<numImg.length; i++){
            setColor.changeColor(numImg, i, Integer.parseInt(numTxt[i].getText().toString()));
        }

        return convertView;
    }
}

class Nums {
    public int num1TextView;
    public int num2TextView;
    public int num3TextView;
    public int num4TextView;
    public int num5TextView;
    public int num6TextView;
}
