package com.example.sofuser.Adapter;

import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sofuser.Model.Root;
import com.example.sofuser.Model.User;
import com.example.sofuser.ProfilePage;
import com.example.sofuser.R;

import java.util.ArrayList;
import java.util.List;


public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {


    private Context context;
    private List<User> userList;

    public UsersListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.users_list_item, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, final int i) {
        final User currentUser = userList.get(i);

        userViewHolder.txtName.setText(currentUser.getDisplayName());
        userViewHolder.website.setText(currentUser.getWebsiteUrl());

        userViewHolder.txtReputation.setText(currentUser.getReputation().toString());

        Glide.with(context).load(currentUser.getProfileImage()).into(userViewHolder.profileImage);


        userViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilePage.class);

                intent.putExtra("name", currentUser.getDisplayName());

                intent.putExtra("reputation", currentUser.getReputation().toString());

                intent.putExtra("profileImage", currentUser.getProfileImage());

                intent.putExtra("bronz", currentUser.getBadgeCounts().getBronze().toString());

                intent.putExtra("silver", currentUser.getBadgeCounts().getSilver().toString());

                intent.putExtra("gold", currentUser.getBadgeCounts().getGold().toString());

                intent.putExtra("location", currentUser.getLocation());

                intent.putExtra("date", currentUser.getCreationDate().toString());

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
        this.notifyDataSetChanged();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtReputation,website;
        ImageView profileImage;
        CardView cardView;

        public UserViewHolder(@NonNull final View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtReputation = (TextView) itemView.findViewById(R.id.txtReputation);
            profileImage = (ImageView) itemView.findViewById(R.id.profileImage);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            website = (TextView) itemView.findViewById(R.id.txtWebsite);

        }
    }


}
