package com.example.ideaapp.ui.ideagroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ideaapp.R;
import com.example.ideaapp.model.IdeaGroup;

import org.jetbrains.annotations.NotNull;

public class GroupFragment extends Fragment {
    private IdeaGroup group;
    private TextView title;
    private TextView description;
    private TextView owner;
    private TextView created;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);
        title = root.findViewById(R.id.GroupTitle);
        description = root.findViewById(R.id.GroupDescription);
        owner = root.findViewById(R.id.Owner);
        created = root.findViewById(R.id.created);

        IdeaGroup group = (IdeaGroup) getArguments().getSerializable("Gruppe");
        title.setText(group.getGroupname());
        description.setText(group.getGroupdescr());
        owner.setText(group.getGroupowner().getUsername() + "\n" + group.getGroupowner().getUseremail());
        created.setText("Created: " + group.getCreateLocalDate());

        return root;
    }
}
