package com.example.animal.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animal.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {
    Context context;
    private List<HomeItem> list;
    private onRecyclerItemClickerListener mListener;

    public HomeAdapter(Context context, List<HomeItem> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 增加点击监听
     */
    public void setItemListener(onRecyclerItemClickerListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        HomeItem item = list.get(position);
        String title=item.getTitle();
        holder.knowledge_title.setText(title);
        String content=item.getShort_content();
        holder.knowledge_content.setText(content);
        holder.knowledge_content.setOnClickListener(getOnClickListener(position));//存疑
        holder.knowledge_title.setOnClickListener(getOnClickListener(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener(){
//            @Override public void onClick(View v){
//                Intent intent=new Intent(getActivity(), MainActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener && null != v) {
                    mListener.onRecyclerItemClick(v, list.get(position), position);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 点击监听回调接口
     */
    public interface onRecyclerItemClickerListener {
        void onRecyclerItemClick(View view, Object data, int position);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView knowledge_title;
        TextView knowledge_content;

        public MyHolder(View itemView) {
            super(itemView);
            knowledge_title = itemView.findViewById(R.id.knowledge_title);
            knowledge_content = itemView.findViewById(R.id.knowledge_content);
        }
    }
}
