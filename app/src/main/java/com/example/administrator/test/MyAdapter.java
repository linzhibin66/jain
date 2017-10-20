package com.example.administrator.test;



import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/10/12 0012.
 */

public class MyAdapter extends BaseQuickAdapter <String,BaseViewHolder>{


    public MyAdapter(int item, ArrayList<String> data) {
        super(item,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.tv_name,item)
                .addOnClickListener(R.id.bt_test);

    }
}
