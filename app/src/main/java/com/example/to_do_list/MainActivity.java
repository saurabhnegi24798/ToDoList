package com.example.to_do_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private SQLiteDatabase database;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TaskDbHelper dbHelper = new TaskDbHelper(this);
        database = dbHelper.getWritableDatabase();

        editText = findViewById(R.id.editText_task);
        button = findViewById(R.id.add_button);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TaskAdapter(this,getAllItems());
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long)viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

    }

    private void removeItem(long id) {
        database.delete(TaskContract.TABLE_NAME,
                TaskContract.id + " = "+ id ,
                null
                );
        adapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems() {
        return database.query(
                TaskContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public void addData(){
        if(editText.getText().toString().trim().length() == 0) {
            editText.getText().clear();
            return ;
        }
        String task = editText.getText().toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.COLUMN_TASK,task);

        database.insert(TaskContract.TABLE_NAME,null,contentValues);
        adapter.swapCursor(getAllItems());

        editText.getText().clear();
    }
}