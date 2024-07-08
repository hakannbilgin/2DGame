package com.oyun.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.oyun.myapplication.databinding.ActivityResutScreenBinding;

public class ResutScreenActivity extends AppCompatActivity {

    private ActivityResutScreenBinding activityResutScreenBinding;

    private TextView textViewTotalScore;
    private TextView textViewHighScore;

    private Button buttonTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_resut_screen);

        activityResutScreenBinding= ActivityResutScreenBinding.inflate(getLayoutInflater());
        setContentView(activityResutScreenBinding.getRoot());

        textViewHighScore= findViewById(activityResutScreenBinding.HighScore.getId());
        textViewTotalScore= findViewById(activityResutScreenBinding.totalScore.getId());

        buttonTryAgain = findViewById(activityResutScreenBinding.buttonTryAgain.getId());

        buttonTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResutScreenActivity.this, MainActivity.class));
                finish();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}