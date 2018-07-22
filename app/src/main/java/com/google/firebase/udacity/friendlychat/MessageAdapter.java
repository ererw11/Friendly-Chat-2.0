package com.google.firebase.udacity.friendlychat;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<FriendlyMessage> {

    private static final String TAG = MessageAdapter.class.getCanonicalName();
    String signedInUser;

    public MessageAdapter(Context context, int resource, List<FriendlyMessage> objects, String username) {
        super(context, resource, objects);
        signedInUser = username;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        CardView messageCardView = (CardView) convertView.findViewById(R.id.messageCardView);

        FriendlyMessage message = getItem(position);

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }
        nameTextView.setText(message.getName());
        dateTextView.setText(message.getDate());

        // this moves the messages on the right if message is from user
        Log.i(TAG, signedInUser);
        Log.i(TAG, message.getName());
        if (message.getName().equals(signedInUser)) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            messageCardView.setLayoutParams(layoutParams);
        }

        return convertView;
    }
}
