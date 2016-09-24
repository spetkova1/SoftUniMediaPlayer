package homeworks.second.softuni.bg.softunisecondhomework;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import homeworks.second.softuni.bg.softunisecondhomework.entity.ListModel;


/**
 * Created by spetkova on 9/6/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<ListModel> mDataSet;
    private OnItemClickListener listener;
    public static int selectedPos = 0;


    public interface OnItemClickListener {
        void onItemClick(int i, String songTitle, int position);
    }


    public RecyclerViewAdapter(ArrayList<ListModel> dataSet, OnItemClickListener listener) {
        this.mDataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (holder != null) {

            holder.mTitle.setText(mDataSet.get(position).getmTitle());
            holder.mSinger.setText(mDataSet.get(position).getmSinger());
            holder.mSongId = mDataSet.get(position).getmFileName();
            holder.position = position;
            holder.mTitle.setTextColor(Color.parseColor("#A89282"));
            holder.bind(holder, listener);
            holder.itemView.setTag(position);


//            if ((position % 2) == 0) {
//                holder.mDownloadIcon.setVisibility(View.VISIBLE);
//                holder.mTitle.setTextColor(Color.parseColor("#3A7D35"));
//                // number is even
//            }
//
//            else {
//                // number is odd
//                holder.mDownloadIcon.setVisibility(View.GONE);
//            }


        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        ImageView mDownloadIcon;
        TextView mExplicit;
        TextView mSinger;
        RelativeLayout mBackground;
        int mSongId;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.textView);
            mDownloadIcon = (ImageView) itemView.findViewById(R.id.imageView);
            mExplicit = (TextView) itemView.findViewById(R.id.textViewExplicit);
            mSinger = (TextView) itemView.findViewById(R.id.singerName);
            mBackground = (RelativeLayout) itemView.findViewById(R.id.template_container);
        }


        public void bind(final ViewHolder holder, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedPos);
                    selectedPos = holder.position;
                    notifyItemChanged(selectedPos);
                    listener.onItemClick(holder.mSongId, holder.mTitle.getText().toString(), position);
                }
            });

            if(holder.position == selectedPos) {
                holder.mBackground.setBackgroundColor(Color.parseColor("#3A7D35"));
            }else{
                holder.mBackground.setBackgroundColor(Color.parseColor("#311B0B"));
            }

        }

    }
}
