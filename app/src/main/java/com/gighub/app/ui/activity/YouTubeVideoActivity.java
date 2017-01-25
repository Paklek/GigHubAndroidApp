package com.gighub.app.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gighub.app.R;
import com.gighub.app.util.YouTubeHelper;
import com.gighub.app.util.YoutubeVideoConfig;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.w3c.dom.Text;

public class YouTubeVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private String mYouTubeVideoUrl, mVideoId;
    private TextView mTextViewVideoIsNotAvailable;
    // YouTube player view
    private YouTubePlayerView youTubeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video);

        Intent intent = getIntent();

        mTextViewVideoIsNotAvailable = (TextView)findViewById(R.id.tv_video_is_not_available);
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        mYouTubeVideoUrl = intent.getStringExtra("youtube_video");
        // Initializing video player with developer key
        youTubeView.initialize(YoutubeVideoConfig.YOUTUBE_API_KEY, this);

        YouTubeHelper youTubeHelper = new YouTubeHelper();

        if (!mYouTubeVideoUrl.equals("")) {
            mVideoId = youTubeHelper.extractVideoIdFromUrl(mYouTubeVideoUrl);
            mTextViewVideoIsNotAvailable.setVisibility(View.GONE);
        }
        else {
            youTubeView.setVisibility(View.GONE);
            mTextViewVideoIsNotAvailable.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {
            youTubePlayer.cueVideo(mVideoId); // Plays https://www.youtube.com/watch?v=blablabla
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(YoutubeVideoConfig.YOUTUBE_API_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }
}
