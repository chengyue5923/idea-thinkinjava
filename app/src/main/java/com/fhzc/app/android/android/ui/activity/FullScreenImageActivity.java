package com.fhzc.app.android.android.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fhzc.app.android.R;
import com.fhzc.app.android.utils.android.Logger;
import com.fhzc.app.android.utils.im.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/8/4.
 */
public class FullScreenImageActivity extends Activity implements View.OnTouchListener {
    @Bind(R.id.image)
    ImageView image;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_full_image);
        ButterKnife.bind(this);
        image.setOnTouchListener(this);
        url = getIntent().getStringExtra("url");
        Logger.e("urlsss"+url);
        ImageLoader.getInstance(this, R.drawable.default_error).displayImage(url, image);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        finish();
        return true;
    }

}
