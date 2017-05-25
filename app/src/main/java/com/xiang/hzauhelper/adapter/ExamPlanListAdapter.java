package com.xiang.hzauhelper.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiang.hzauhelper.R;
import com.xiang.hzauhelper.entities.ExamTerm;

import java.util.List;

/**
 * Created by xiang on 2017/3/28.
 *
 */

public class ExamPlanListAdapter extends RecyclerView.Adapter<ExamPlanListAdapter.ExamPlanHolder> {

    private Activity activity;
    private List<ExamTerm> list;

    public ExamPlanListAdapter(Activity activity, List<ExamTerm> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public ExamPlanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.item_exam_plan, parent, false);
        return new ExamPlanHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamPlanHolder holder, int position) {
        ExamTerm examTerm = list.get(position);
        holder.examName.setText(examTerm.courseName);
        holder.examPlace.setText(examTerm.examPlace);
        holder.examTime.setText(examTerm.examTime);
//        holder.seatNum.setText(examTerm.seatNum);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ExamPlanHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView examName, examTime, examPlace, seatNum;
        ExamPlanHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.item_exam_plan);
            examName = (TextView) itemView.findViewById(R.id.exam_name);
            examTime = (TextView) itemView.findViewById(R.id.exam_time);
            examPlace = (TextView) itemView.findViewById(R.id.exam_place);
//            seatNum = (TextView) itemView.findViewById(R.id.seat_num);
        }
    }
}
