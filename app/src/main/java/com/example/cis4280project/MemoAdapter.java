package com.example.cis4280project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MemoAdapter extends RecyclerView.Adapter {

    private final ArrayList<Memo> memoData;
    private boolean isDeleting;

    private final Context parentContext;
    private View.OnClickListener mOnItemClickerListener;

    public class memoViewHolder extends RecyclerView.ViewHolder{

        public TextView textSubject;
        public TextView textDate;
        public TextView textLevel;
        public Button deleteButton;


        public memoViewHolder(@NonNull View itemView) {
            super(itemView);
            textSubject = itemView.findViewById(R.id.textSubject);
            textDate = itemView.findViewById(R.id.textDate);
            textLevel = itemView.findViewById(R.id.textLevel);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickerListener);
        }
        public TextView getTextSubject() {
            return textSubject;
        }
        public  TextView getTextDate() {
            return textDate;
        }
        public TextView getTextLevel(){
            return textLevel;
        }
        public Button getDeleteBution() {
            return deleteButton;
        }

    }
    public MemoAdapter(ArrayList<Memo> arrayList, Context context) {
        memoData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickerListener(View.OnClickListener itemClickerListener){
        mOnItemClickerListener = itemClickerListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent,false);
        return new memoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        memoViewHolder cvh = (memoViewHolder) holder;
        cvh.getTextSubject().setText(memoData.get(position).getSubject());

        Calendar calendar = memoData.get(position).getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        CharSequence formattedDate = dateFormat.format(calendar.getTime());
        cvh.getTextDate().setText(formattedDate);

        cvh.getTextLevel().setText(memoData.get(position).getLevel());

        if (isDeleting) {
            cvh.getDeleteBution().setVisibility(View.VISIBLE);
            cvh.getDeleteBution().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });
        }
        else {
            cvh.getDeleteBution().setVisibility(View.INVISIBLE);

        }
    }
    public void setDeleting(boolean b){
        isDeleting = b;
    }
    private void deleteItem(int position) {
        Memo memo = memoData.get(position);
        MemoDataSource ds = new MemoDataSource(parentContext);
        try{
            ds.open();
            boolean didDelete = ds.deleteMemo(memo.getMemoID());
            ds.close();
            if (didDelete) {
                memoData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public int getItemCount() {
        return memoData.size();
    }
}
