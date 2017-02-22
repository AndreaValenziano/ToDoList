package com.example.andreavalenziano.todolist.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andreavalenziano.todolist.R;
import com.example.andreavalenziano.todolist.activities.AddActivity;
import com.example.andreavalenziano.todolist.activities.MainActivity;
import com.example.andreavalenziano.todolist.models.Note;

import java.util.ArrayList;

/**
 * Created by AndreaValenziano on 20/02/17.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {

    private ArrayList<Note> dataSet=new ArrayList<>();
    private int position;
    private Context context;

    public NoteAdapter() {

    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void addNote(Note note){
        dataSet.add(0,note);
        notifyItemInserted(0);
    }

    public void modifyNote(Note note, int index) {
        dataSet.set(index,note);
        notifyDataSetChanged();
    }

    public void deleteNote(int index) {
        dataSet.remove(index);
        notifyDataSetChanged();
    }

    public void setDataSet(ArrayList<Note> dataSet){
        this.dataSet=dataSet;
        notifyDataSetChanged();
    }

    public NoteAdapter(Context c) {
        context = c;
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
    public void onBindViewHolder(NoteAdapter.NoteVH holder,  final int position) {

        Note note = dataSet.get(position);
        holder.titleTV.setText(note.getTitle());
        holder.expDateTV.setText(note.getDateExpired());
        holder.lastEditDateTV.setText(note.getDateLastEdit());
        holder.textBodyTV.setText(note.getTextBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note=dataSet.get(position);
                Intent i=new Intent(v.getContext(), AddActivity.class);
                i.putExtra(MainActivity.EDIT,true);
                i.putExtra(MainActivity.TITLE,note.getTitle());
                i.putExtra(MainActivity.DATE_EXP,note.getDateExpired());
                i.putExtra(MainActivity.TEXT_BODY,note.getTextBody());
                i.putExtra(MainActivity.ID,position);
                Activity context=(Activity) v.getContext();
                context.startActivityForResult(i,MainActivity.EDIT_REQUEST_CODE);
            }
        });



    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }




    public class NoteVH extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView titleTV, expDateTV, lastEditDateTV,textBodyTV;

        public NoteVH(View itemView) {
            super(itemView);
            titleTV=(TextView)itemView.findViewById(R.id.title_note_tv);
            expDateTV=(TextView)itemView.findViewById(R.id.exp_date_note_tv);
            lastEditDateTV=(TextView)itemView.findViewById(R.id.last_edit_daet_note_tv);
            textBodyTV=(TextView)itemView.findViewById(R.id.text_body_note_tv);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    setPosition(getAdapterPosition());
                    return false;
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater inflater = ((MainActivity)context).getMenuInflater();
            inflater.inflate(R.menu.menu_note, contextMenu);
        }



        }




}
