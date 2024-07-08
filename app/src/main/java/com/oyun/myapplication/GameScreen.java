package com.oyun.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.oyun.myapplication.databinding.ActivityGameScreenBinding;

import java.util.Timer;
import java.util.TimerTask;

public class GameScreen extends AppCompatActivity {

    //private ConstraintLayout constraintLayout;
    private TextView textViewScore;
    private TextView textViewStartGame;
    private ImageView yellowCircle;
    private ImageView blackSquare;
    private ImageView redTriangle;
    private ImageView mainCharacter;

    private Timer timer = new Timer();

    private Handler handler = new Handler(Looper.getMainLooper());

    //Pozisyonlar
    private int mainCharacterX;
    private int mainCharactery;


    //Boyutlar
    private int screenWidth;
    private int screenHeight;
    private int mainCharacterHeight;
    private int mainCharacterWidth;



    //Kontroller
    private boolean touchCheck=false;
    private boolean beginningTouchCheck=false;

    private ActivityGameScreenBinding activityGameScreenBinding;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //setContentView(R.layout.activity_game_screen);
        activityGameScreenBinding = ActivityGameScreenBinding.inflate(getLayoutInflater());
        setContentView(activityGameScreenBinding.getRoot());


        //constraintLayout = findViewById(activityGameScreenBinding.cl.getId());
        textViewScore = findViewById(activityGameScreenBinding.textViewScore.getId());
        textViewStartGame = findViewById(activityGameScreenBinding.StartToGame.getId());
        yellowCircle = findViewById(activityGameScreenBinding.yellowCircle.getId());
        blackSquare = findViewById(activityGameScreenBinding.blackSquare.getId());
        redTriangle = findViewById(activityGameScreenBinding.redTriangle.getId());
        mainCharacter = findViewById(activityGameScreenBinding.mainCharacter.getId());

        textViewStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(GameScreen.this, ResutScreenActivity.class));
                //finish();
            }
        });

        activityGameScreenBinding.getRoot().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (beginningTouchCheck){
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        Log.i("MotionEvent","Ekrana Dokunuldu");
                        touchCheck=true;

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        Log.i("MotionEvent","Ekranı Bıraktı");
                        touchCheck=false;

                    }
                }else {
                    beginningTouchCheck=true;
                    textViewStartGame.setVisibility(View.INVISIBLE); //Görünmez yaptık

                    mainCharacterX= (int)mainCharacter.getX();
                    mainCharactery = (int)mainCharacter.getY();

                    mainCharacterWidth = mainCharacter.getWidth();
                    mainCharacterHeight = mainCharacter.getHeight();
                    screenHeight = activityGameScreenBinding.getRoot().getHeight(); //Ekrannın genişliği aldık.
                    screenWidth = activityGameScreenBinding.getRoot().getWidth(); //Ekrannın enini aldık.





                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    mainCharacterMove();
                                }
                            });

                        }
                    },0,20);

                }



                return true;
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

public void mainCharacterMove(){
    if (touchCheck){
        mainCharactery= mainCharactery-40;

    }else {
        mainCharactery = mainCharactery + 40;

    }
    //mainCharacter.animateTransform(mainCharacter.setY(mainCharactery));
    //mainCharacter.animate().x()
    /// mainCharacter.setScrollY(mainCharactery);


    if (mainCharactery<= 0){
        mainCharactery=0;

    }

    if (mainCharactery>=(screenHeight - mainCharacterHeight)){
        mainCharactery=screenHeight - mainCharacterHeight;
    }

    mainCharacter.setY(mainCharactery);

}
}