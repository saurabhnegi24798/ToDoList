package com.example.to_do_list;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.viewholder> {

    private Context context;
    private Cursor cursor;

    public TaskAdapter(Context context , Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        if(!cursor.moveToPosition(position)){
            return ;
        }
        String newTask = cursor.getString(cursor.getColumnIndex(TaskContract.COLUMN_TASK));
        long id = cursor.getLong(cursor.getColumnIndex(TaskContract.id));
        holder.task.setText(newTask);
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if(cursor!=null){
            cursor.close();
        }
        cursor=newCursor;
        if(newCursor!=null){
            notifyDataSetChanged();
        }
    }

    public class viewholder extends RecyclerView.ViewHolder{
        private TextView task;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.each_task);
        }
    }
}
