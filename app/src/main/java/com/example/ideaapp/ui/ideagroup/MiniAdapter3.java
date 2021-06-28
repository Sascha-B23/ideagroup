package com.example.ideaapp.ui.ideagroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ideaapp.R;
import com.example.ideaapp.model.Comments;
import com.example.ideaapp.model.IdeaCategory;

import java.util.ArrayList;

public class MiniAdapter3 extends RecyclerView.Adapter<MiniAdapter3.FirstViewHolder>{
    private ArrayList<Comments> content;
    private View.OnClickListener mOnClickListener = null;

    public class FirstViewHolder extends RecyclerView.ViewHolder {
        // "Hält" die Views
        // Textview aus score_list_item
        private TextView groupname;
        private TextView created;
        private TextView description;
        private TextView owner;
        private Button btn;


        public FirstViewHolder(View v) {
            super(v);
            //mOnClickListener = new ClickGroupListener((AppCompatActivity) v.getContext());
            groupname = v.findViewById(R.id.groupname);
            created = v.findViewById(R.id.created);
            description = v.findViewById(R.id.description);
            owner = v.findViewById(R.id.owner);
        }

        public void bind(final Comments group) {
            groupname.setText(group.getCommentcontent());
            description.setText(group.getAuthor().getUsername());
            created.setText("");
            owner.setText("Created at: " + group.getCommenttime().toString());
        }
    }

    public MiniAdapter3(ArrayList<Comments> content){
        this.content = content;
    }

    public FirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Holt die Darstellung
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.listitem,parent,false);
        //v.setOnClickListener(mOnClickListener);
        FirstViewHolder viewHolder = new FirstViewHolder(v);
        return viewHolder;
    }

    public void onBindViewHolder(FirstViewHolder holder,int position) {
        final Comments s = content.get(position);
        holder.bind(s);
    }

    public int getItemCount() {
        return content.size();
    }
}
