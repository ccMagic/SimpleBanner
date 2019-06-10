package com.github.ccmagic.banner.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ccmagic.banner.R;
import com.github.ccmagic.banner.banner.BannerAdapter;
import com.github.ccmagic.banner.banner.BannerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BannerView bannerView = findViewById(R.id.bannerView);
        bannerView.setAdapter(new BannerAdapter(this) {
            @Override
            public int pageCount() {
                return 3;
            }

            @Override
            protected View instantiateItem(Context context, int position) {
                TextView textView = new TextView(context);
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                textView.setGravity(Gravity.CENTER);
                textView.setText((position + ""));
                textView.setTextSize(50);
                return textView;
            }

            @Override
            public RadioButton indicator(Context context, int position) {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(("" + position));

                radioButton.setTextColor(Color.WHITE);
                return radioButton;
            }
        });
        bannerView.setOnItemClickListener(position -> Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show());
    }
}
