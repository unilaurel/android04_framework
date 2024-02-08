package com.example.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 1, RecyclerView.Adapterを継承する
 * ２、ViewHolderをバインドする
 * ３、Adapterの関連メソッドを実行する
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    private List<String> dataSource;
    private Context mContext;
    private RecyclerView mRv;
    private int addDataPosition = -1;
    private OnItemClickListener mOnItemClickListener;

    /**
     * ItemView　click callback interface
     */
    interface OnItemClickListener {
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View mItemView;
        private ImageView mIv;
        private TextView mTv;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.iv);
            mTv = itemView.findViewById(R.id.tv);
            mItemView = itemView;
        }
    }

    public void setDataSource(List<String> dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public MyRecyclerViewAdapter(Context context, RecyclerView recyclerView) {
        this.mContext = context;
        this.dataSource = new ArrayList<>();
        mRv = recyclerView;
    }


    @NonNull
    @Override
    //ViewHolder を作成して返す
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int postion) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent, false));
    }

    //ViewHolder にデータをバインドする
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.mIv.setImageResource(getIcon(position));
        holder.mTv.setText(dataSource.get(position));

        //StaggeredGridLayoutManagerでランダムな高さを使用する
        if (mRv.getLayoutManager().getClass() == StaggeredGridLayoutManager.class) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getRandomHeight());
            holder.mTv.setLayoutParams(params);
        } else {
            //回復必要がある
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.mTv.setLayoutParams(params);
        }

        holder.mItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public int getIcon(int position) {
        switch (position % 5) {
            case 0:
                return R.mipmap.a;
            case 1:
                return R.mipmap.b;
            case 2:
                return R.mipmap.c;
            case 3:
                return R.mipmap.d;
            case 4:
                return R.mipmap.e;
        }
        return 0;
    }

    //異なるItemView 高さを返す
    private int getRandomHeight() {
        return (int) (Math.random() * 1000);
    }

    //add item
    public void addData(int position) {
        addDataPosition = position;
        dataSource.add(position, "插入的数据");
        notifyItemInserted(position);

//        ItemViewを更新する
        notifyItemRangeChanged(position, dataSource.size() - position);
    }

    //delete item
    public void removeData(int position) {
        addDataPosition = -1;
        dataSource.remove(position);
        notifyItemRemoved(position);

//        刷新ItemView
        notifyItemRangeChanged(position, dataSource.size() - position);
    }
}
