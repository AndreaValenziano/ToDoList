package com.example.andreavalenziano.todolist.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andreavalenziano.todolist.R;
import com.example.andreavalenziano.todolist.models.Note;

import java.util.ArrayList;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {

    private ArrayList<Note> dataSet=new ArrayList<>();
    private static NoteAdapter instance=new NoteAdapter();
    public static NoteAdapter getInstance(){
        return instance;
    }

    public void addNote(Note note){
        dataSet.add(0,note);
        notifyItemInserted(0);
    }

    public void setDataSet(ArrayList<Note> dataSet){
        this.dataSet=dataSet;
        notifyDataSetChanged();
    }

    public ArrayList<Note> getDataSet(){
        return dataSet;
    }



    @Override
    public NoteAdapter.NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new NoteVH(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.NoteVH holder, int position) {

        Note note = dataSet.get(position);
        holder.titleTV.setText(note.getTitle());
        holder.expDateTV.setText(note.getDateExpired());
        holder.lastEditDateTV.setText(note.getDateLastEdit());
        holder.textBodyTV.setText(note.getTextBody());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class NoteVH extends RecyclerView.ViewHolder{
        TextView titleTV, expDateTV, lastEditDateTV,textBodyTV;

        public NoteVH(View itemView) {
            super(itemView);
            titleTV=(TextView)itemView.findViewById(R.id.title_note_tv);
            expDateTV=(TextView)itemView.findViewById(R.id.exp_date_note_tv);
            lastEditDateTV=(TextView)itemView.findViewById(R.id.last_edit_daet_note_tv);
            textBodyTV=(TextView)itemView.findViewById(R.id.text_body_note_tv);

        }


    }

}
