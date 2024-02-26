package com.example.cis4280project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

public class MemoList extends AppCompatActivity {

    private ArrayList<Memo> memos;
    MemoAdapter memoAdapter;

    RecyclerView memoList;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int memoID = memos.get(position).getMemoID();
            Intent intent = new Intent(MemoList.this, MainActivity.class);
            intent.putExtra("memoID", memoID);
            startActivity(intent);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_list);
        initAddMemo();
        initDelSwitch();

        MemoDataSource ds = new MemoDataSource(this);
        try {
            ds.open();
            memos = ds.getMemos();
            ds.close();
            memoList = findViewById(R.id.rvMemos);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            memoAdapter = new MemoAdapter(memos, MemoList.this);
            memoAdapter.setOnItemClickerListener(onItemClickListener);
            memoList.setAdapter(memoAdapter);
        }
        catch (Exception e) {
            Toast.makeText(this, "Error loading memos.", Toast.LENGTH_LONG).show();
        }
    }
    //Add a new memo
    private void initAddMemo() {
        Button newMemo = findViewById(R.id.buttonadd);
        newMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initDelSwitch() {
        Switch s = findViewById(R.id.switchdelete);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(memoAdapter != null) {
                    boolean status = buttonView.isChecked();
                    memoAdapter.setDeleting(status);
                    memoAdapter.notifyDataSetChanged();
                }
                else {

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        MemoDataSource ds = new MemoDataSource(this);
        try {
            ds.open();
            memos = ds.getMemos();
            ds.close();
            if (memos.size() > 0) {
                memoList = findViewById(R.id.rvMemos);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                memoList.setLayoutManager(layoutManager);

                memoAdapter = new MemoAdapter(memos, MemoList.this);
                memoAdapter.setOnItemClickerListener(onItemClickListener);
                memoList.setAdapter(memoAdapter);

            } else {
                Intent intent = new Intent(MemoList.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error retrieving memos", Toast.LENGTH_LONG).show();
        }
    }

}