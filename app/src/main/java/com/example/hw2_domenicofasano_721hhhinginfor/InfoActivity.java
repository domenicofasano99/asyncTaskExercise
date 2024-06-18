package com.example.hw2_domenicofasano_721hhhinginfor;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        TextView infoTextView = findViewById(R.id.infoTextView);
        infoTextView.setText(getString(R.string.info_text));
    }
}
