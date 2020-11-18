package com.example.vintest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SubjectsRecyclerViewAdapter extends RecyclerView.Adapter<SubjectsRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> subjectname;
    ArrayList<String> subjectdesc;
    ArrayList<Integer> subjectimage;

    public SubjectsRecyclerViewAdapter(Context context, ArrayList<String> subjectname, ArrayList<String> subjectdesc, ArrayList<Integer> subjectimage) {
        this.context = context;
        this.subjectname = subjectname;
        this.subjectdesc = subjectdesc;
        this.subjectimage = subjectimage;
    }

    @NonNull
    @Override
    public SubjectsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjects_recyclerview_items, parent, false);
        return new SubjectsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.sname.setText(subjectname.get(position));
        holder.sdesc.setText(subjectdesc.get(position));
        holder.image.setImageResource(subjectimage.get(position));
        holder.r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Quiz.class);
                i.putExtra("sub", subjectname.get(position));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView sname, sdesc;
        CardView r1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            sname = itemView.findViewById(R.id.subject);
            sdesc = itemView.findViewById(R.id.subdesc);
            r1 = itemView.findViewById(R.id.cardview);

        }
    }
}
