package com.sahil.tmailapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sahil.tmailapp.Activity.DetailsActivity;
import com.sahil.tmailapp.Model.EmailMessage;
import com.sahil.tmailapp.R;
import com.sahil.tmailapp.Utils.AdsManager;
import com.sahil.tmailapp.Utils.AdsPref;
import com.sahil.tmailapp.Utils.PrefManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EmailAdapter extends RecyclerView.Adapter<EmailAdapter.ViewHolder> {

    private List<EmailMessage> emailMessages;
    private Context context;
    private AdsPref adsPref;
    private AdsManager adsManager;
    private PrefManager prefManager;

    public EmailAdapter(Context context, List<EmailMessage> emailMessages) {
        this.context = context;
        this.emailMessages = emailMessages;

        prefManager = new PrefManager(context);
        adsPref = new AdsPref(context);
        adsManager = new AdsManager((Activity) context);
        adsManager.loadBannerAd(adsPref.getIsBannerHome());
        adsManager.loadInterstitialAd(adsPref.getIsInterstitialPostList(), adsPref.getInterstitialAdInterval());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_email, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmailMessage emailMessage = emailMessages.get(position);

        holder.emailSubject.setText(emailMessage.getSubject());
        holder.emailDate.setText(getFormattedReceivedAt(emailMessage.getReceivedAt()));
        holder.emailBody.setText(emailMessage.getBodyPreview().trim());

        holder.emailAttachment.setVisibility(View.GONE);

        if (emailMessage.getId().equals(prefManager.getString(emailMessage.getId()))){
            holder.emailStatus.setImageTintList(ColorStateList.valueOf(Color.GRAY));
        }else{
            holder.emailStatus.setImageTintList(ColorStateList.valueOf(Color.GREEN));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("emailId", emailMessage.getId());
                context.startActivity(intent);
                showInterstitialAd();
                prefManager.setString(emailMessage.getId(),emailMessage.getId());
                notifyDataSetChanged();
            }
        });

    }

    private void showInterstitialAd() {
        if (adsPref.getInterstitialAdCounter() >= adsPref.getInterstitialAdInterval()) {
            adsPref.updateInterstitialAdCounter(1);
            adsManager.showInterstitialAd();
        } else {
            adsPref.updateInterstitialAdCounter(adsPref.getInterstitialAdCounter() + 1);
        }
    }


    public String getFormattedReceivedAt(long receivedAt) {
        // Convert timestamp to human-readable date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
        return dateFormat.format(new Date(receivedAt * 1000));
    }

    @Override
    public int getItemCount() {
        return emailMessages.size();
    }
    public void setEmailMessages(List<EmailMessage> emailMessages) {
        this.emailMessages = emailMessages;
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emailSubject;
        public TextView emailDate;
        public TextView emailBody;
        public ImageView emailAttachment;
        public ImageView emailStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            emailSubject = itemView.findViewById(R.id.emailSubject);
            emailDate = itemView.findViewById(R.id.emailDate);
            emailBody = itemView.findViewById(R.id.emailBody);
            emailAttachment = itemView.findViewById(R.id.emailAttechment);
            emailStatus = itemView.findViewById(R.id.emailStatus);
        }
    }
}
