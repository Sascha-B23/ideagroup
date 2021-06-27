package com.example.ideaapp.ui.newCategories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ideaapp.R;
import com.example.ideaapp.ui.newIdeaGroup.CreateIdeaAdapter;
import com.example.ideaapp.ui.newIdeaGroup.NewIdeaGroup;

import java.util.ArrayList;

public class NewCategories extends AppCompatActivity {

    private RecyclerView rv;
    private ArrayList<String> content;
    private CreateCategories adapter;
    private EditText ed;
    private EditText ideaDescription;
    private RecyclerView.LayoutManager layoutRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_categories);

        rv = findViewById(R.id.rec_category);
        ed = findViewById(R.id.inputCategory);
        layoutRV = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutRV);

        content = new ArrayList<String>();
        adapter = new CreateCategories(content);
        rv.setAdapter(adapter);

            ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {


                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    Toast.makeText(NewCategories.this, "on Move", Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    Toast.makeText(NewCategories.this, "on Swiped ", Toast.LENGTH_SHORT).show();
                    //Remove swiped item from list and notify the RecyclerView
                    int position = viewHolder.getAdapterPosition();
                    content.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyDataSetChanged();

                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
            itemTouchHelper.attachToRecyclerView(rv);

            ed.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        content.add(ed.getText().toString());
                        ed.setText("");
                        InputMethodManager imm = (InputMethodManager)NewCategories.this.getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);
                        ed.clearFocus();
                        adapter.notifyDataSetChanged();
                        return false;
                    } else {
                        return false;
                    }
                }
            });
    }
}