package com.example.ideaapp.ui.ideagroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ideaapp.MiniAdapter;
import com.example.ideaapp.R;
import com.example.ideaapp.model.Appuser;
import com.example.ideaapp.model.Comments;
import com.example.ideaapp.model.IdeaCategory;
import com.example.ideaapp.model.IdeaGroup;
import com.example.ideaapp.ui.groupGallery.GroupGalleryViewModel;
import com.example.ideaapp.ui.groupGallery.RecyclerItemClickListener;
import com.example.ideaapp.ws.IllegalCreateException;
import com.example.ideaapp.ws.InfrastructureWebservice;
import com.example.ideaapp.ws.NoSuchRowException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutRv;
    private ArrayList<IdeaCategory> content;
    private ArrayList<Comments> content2;
    private MiniAdapter2 adapter;
    private MiniAdapter3 adapter2;
    private InfrastructureWebservice service;
    private IdeaGroup group;
    private TextView categoryview;
    private String email;
    private FloatingActionButton fab;
    private EditText editCommment;
    private Button postComment;
    private IdeaCategory cat;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        //final TextView textView = root.findViewById(R.id.textGallery);
        recyclerView = root.findViewById(R.id.rec_view);
        categoryview = root.findViewById(R.id.categoryview);
        fab = root.findViewById(R.id.fab);
        postComment = root.findViewById(R.id.postComment);
        editCommment = root.findViewById(R.id.editNewComment);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        //userid = Integer.parseInt(acct.getId().substring(0, 8));
        email = (acct.getEmail());
        layoutRv = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutRv);
        group = (IdeaGroup) getArguments().getSerializable("Gruppe");
        service = new InfrastructureWebservice();
        content = new ArrayList<IdeaCategory>();
        System.out.println(group.toString());
        int id = group.getGroupid();
        ArrayList<IdeaCategory> c = (ArrayList<IdeaCategory>) service.getCategoryByGroupId(id);
        content.addAll(c);
        adapter = new MiniAdapter2(content);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //content.clear();
                        //adapter.notifyDataSetChanged();
                        System.out.println(content.get(position).getCategoryid());
                        cat = content.get(position);
                        int categoryid = cat.getCategoryid();
                        ArrayList<Comments> c = (ArrayList<Comments>) service.getCommentsByCategoryId(categoryid);
                        content.clear();
                        categoryview.setVisibility(View.VISIBLE);
                        categoryview.setText("Comments to Category: \n" + cat.getCategorytitle());
                        MiniAdapter3 adapter3 = new MiniAdapter3(c);
                        recyclerView.setAdapter(adapter3);
                        System.out.println(email);
                        postComment.setVisibility(View.VISIBLE);
                        editCommment.setVisibility(View.VISIBLE);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));
        postComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ccont = editCommment.getText().toString();
                try {
                    Appuser a = service.getUserByEmail(email);
                    cat.setIdeagroup(group);
                    Comments c = new Comments(ccont, a, cat);
                    System.out.println(cat.toString());
                    System.out.println("Comment: " + c.toString());
                    service.postCommentToCategory(c, cat.getCategorytitle());
                    editCommment.setText("");
                } catch (NoSuchRowException e) {
                    e.printStackTrace();
                } catch (IllegalCreateException e) {
                    e.printStackTrace();
                }
                ArrayList<Comments> c = (ArrayList<Comments>) service.getCommentsByCategoryId(cat.getCategoryid());
                MiniAdapter3 adapter3 = new MiniAdapter3(c);
                recyclerView.setAdapter(adapter3);

                //Comments c = new Comments(ccont,)
            }
        });
    }
}
