package com.myMindLotto.lottoapp.prizeNums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lottoapp.R;

import java.util.ArrayList;

//회차별 당첨번호 띄우는 리사이클러뷰 관련 코드 & 아이템 클릭 시 이벤트 주는 코드
public class MakePrizeNumsRecycler extends RecyclerView.Adapter<MakePrizeNumsRecycler.RViewHolder> {

    ArrayList<PrizeNumItem> prizeNumItemList;
    Context context;
    LayoutInflater layoutInflater;
    OnRItemClickListener onRItemClickListener;

    public MakePrizeNumsRecycler(ArrayList<PrizeNumItem> prizeNumItemList, Context context) {
        this.prizeNumItemList = prizeNumItemList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prize_nums_list_custom, parent, false);

        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        holder.drwNoTextView.setText(prizeNumItemList.get(position).getDrwNoTextView());
    }

    @Override
    public int getItemCount() {
        return prizeNumItemList.size();
    }

    public interface OnRItemClickListener {
        void onRItemClickListener(View v, int position);  //PrizeNumItem prizeNumItem
    }

    public void setOnRItemClickListener(OnRItemClickListener onRItemClickListener){
        this.onRItemClickListener = onRItemClickListener;
    }

    class RViewHolder extends RecyclerView.ViewHolder{

        TextView drwNoTextView;

        public RViewHolder(@NonNull View itemView) {
            super(itemView);
/*****  https://happysuzy.tistory.com/15?category=726888  https://onlyfor-me-blog.tistory.com/40 ****/
            drwNoTextView = itemView.findViewById(R.id.drwNoTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getBindingAdapterPosition();

                    if (position != RecyclerView.NO_POSITION)
                    {
                        onRItemClickListener.onRItemClickListener(v, position);
                    }

                }
            });
        }
    }
}


