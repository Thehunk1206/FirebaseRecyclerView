package com.hunk.firebase.firebaserecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class UserListActivity extends AppCompatActivity {

    private RecyclerView mUserslist;

    private DatabaseReference mUserdatabase;

    private FirebaseRecyclerAdapter<User,UserListActivity.UserViewHolder> mFirebaseRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mUserslist = findViewById(R.id.users_list_RV);
        mUserslist.setHasFixedSize(true);
        mUserslist.setLayoutManager(new LinearLayoutManager(this));

        //Firebase real time database
        mUserdatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserdatabase.keepSynced(true);

        //Query
        Query query = mUserdatabase.orderByKey();

        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(query,User.class).build();

        mFirebaseRVAdapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
                holder.setName(model.getName());
                holder.setAge(model.getAge());
                holder.setEmail(model.getEmail());
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_list_single_layout,viewGroup,false);

                return new UserViewHolder(view);
            }
        };

        mUserslist.setAdapter(mFirebaseRVAdapter);




    }







    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseRVAdapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseRVAdapter.stopListening();
    }




    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name) {

            TextView nameTV = mView.findViewById(R.id.RV_name);
            nameTV.setText(name);
        }

        public void setAge(String age) {
            TextView ageTV = mView.findViewById(R.id.RV_age);
            ageTV.setText("age: " + age);
        }

        public void setEmail(String email) {
            TextView emailTV = mView.findViewById(R.id.RV_email);
            emailTV.setText("Email: " + email);

        }
    }
}
