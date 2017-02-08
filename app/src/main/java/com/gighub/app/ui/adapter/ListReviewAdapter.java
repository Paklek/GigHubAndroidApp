package com.gighub.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gighub.app.R;
import com.gighub.app.model.SearchResultModel;
import com.gighub.app.model.YourReview;
import com.gighub.app.util.CloudinaryUrl;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 08/02/2017.
 */
public class ListReviewAdapter extends BaseAdapter {
    private Context mContext;
    private List<YourReview> mYourReview;

    private static LayoutInflater inflater=null;

    public ListReviewAdapter(Context mContext, List<YourReview> mYourReview)
    {
//       this.mListMusician = mListMusician;
        this.mYourReview = mYourReview;
        this.mContext = mContext;
        inflater = (LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mYourReview.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class Holder{
        TextView mTextViewUserName,mTextViewTanggalReview, mTextViewMessageReview;
        RatingBar mRatingBarReview;
        CircularImageView mImageViewPhotoUserReview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View rowView;
        rowView = inflater.inflate(R.layout.lv_review_musician,null);
        Holder holder = new Holder();

        holder.mImageViewPhotoUserReview = (CircularImageView)rowView.findViewById(R.id.img_user_reviewer_lvreviewmusician);
        holder.mRatingBarReview = (RatingBar)rowView.findViewById(R.id.ratbar_lvreviewmusician);
        holder.mTextViewMessageReview = (TextView)rowView.findViewById(R.id.tv_pesan_review_lvreviewmusician);
        holder.mTextViewUserName = (TextView)rowView.findViewById(R.id.tv_nama_reviewer_lvreviewmusician);
        holder.mTextViewTanggalReview = (TextView)rowView.findViewById(R.id.tv_tanggal_review_lvreviewmusician);

        CloudinaryUrl cloudinaryUrl = new CloudinaryUrl();
        cloudinaryUrl.buildCloudinaryUrl();
        if(mYourReview.get(position).getPhoto()!=null && !mYourReview.get(position).getPhoto().equals("")) {
            Picasso.with(mContext).load(cloudinaryUrl.cloudinary.url().format("jpg").generate(mYourReview.get(position).getPhoto())).into(holder.mImageViewPhotoUserReview);
        }
        holder.mRatingBarReview.setRating(Float.parseFloat(mYourReview.get(position).getNilai()+".0"));
        holder.mRatingBarReview.setIsIndicator(true);
        holder.mTextViewUserName.setText(mYourReview.get(position).getFirst_name()+" "+mYourReview.get(position).getLast_name());
        holder.mTextViewMessageReview.setText(mYourReview.get(position).getPesan());
        holder.mTextViewTanggalReview.setText(mYourReview.get(position).getCreated_at());

        return rowView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
