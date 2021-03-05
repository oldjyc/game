package com.example.jycpuzzle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.Map;

public class JycStaggerAdapter extends RecyclerView.Adapter <JycStaggerAdapter.LinearViewHolder> {
//全局变量
private Context mContext;
private Map<Integer, Integer> Jyc_check;
private OnItemClickListener listener;

//适配器构造函数（上下文，监听器）
public JycStaggerAdapter(Context context, OnItemClickListener listener){
        Jyc_check = new HashMap<Integer, Integer>();
        Jyc_check.put(0, R.drawable.p0);
        this.listener=listener;
        this.mContext=context;
        }
@NonNull
@Override
//返回视图
public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.jycstaggeritem,viewGroup,false));

        }
//处理子项
@Override
public void onBindViewHolder(@NonNull LinearViewHolder viewHolder, final int i) {
        viewHolder.imageView.setImageResource(Jyc_check.get(i));
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
@Override
            public void onClick(View v) {
                listener.onClick(i);
            }
        });

}
//项目数
@Override
public int getItemCount() {
        return Jyc_check.size();
        }

class LinearViewHolder extends RecyclerView.ViewHolder{

    private final ImageView imageView;

    public LinearViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView= (ImageView) itemView.findViewById(R.id.photo_title);
    }
}
public interface OnItemClickListener{
    void onClick(int i);
}

}
