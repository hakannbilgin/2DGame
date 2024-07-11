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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.oyun.myapplication.databinding.ActivityGameScreenBinding;

import java.util.Random;
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
    private int mainCharacterY;

    private int yellowCircleX;
    private int yellowCircleY;

    private int blackSquareX;
    private int blackSquareY;

    private int redTriangleX;
    private int redTriangleY;


    //Boyutlar
    private int screenWidth;
    private int screenHeight;
    private int mainCharacterHeight;
    private int mainCharacterWidth;


    //Hızlar
    private int mainCharacterSpeed;
    private int blackSquareSpeed;
    private int redTriangleSpeed;
    private int yellowCircleSpeed;



    //Kontroller
    private boolean touchCheck=false;
    private boolean beginningTouchCheck=false;

    private int score=0;

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

        //Cisimleri ekranın dışında başlatalım
        blackSquare.setX(-120);
        blackSquare.setY(-120);
        yellowCircle.setX(-120);
        yellowCircle.setY(-120);
        redTriangle.setX(-120);
        redTriangle.setY(-120);



        activityGameScreenBinding.getRoot().setOnTouchListener(new View.OnTouchListener()
        {
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
                    mainCharacterY = (int)mainCharacter.getY();

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
                                    moveBlackSquare();
                                    moveRedTriangle();
                                    moveYellowCircle();
                                    collisioncheck();

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
        mainCharacterSpeed =  Math.round((float) screenHeight / 55); //hızını 40 yapıyor

        if (touchCheck){
        //mainCharacterY = mainCharacterY -40; //hız eklediğim için kapadım
            mainCharacterY = mainCharacterY -mainCharacterSpeed;


        }else {
        //mainCharacterY = mainCharacterY + 40; //hız eklediğim için kapadım
            mainCharacterY = mainCharacterY +mainCharacterSpeed;
        }



        if (mainCharacterY <= 0){
            mainCharacterY =0;

        }

        if (mainCharacterY >=(screenHeight - mainCharacterHeight)){
            mainCharacterY =screenHeight - mainCharacterHeight;
        }

        mainCharacter.setY(mainCharacterY);

    }

    public void moveBlackSquare(){
        blackSquareSpeed =  Math.round((float) screenWidth / 72); //hız 15 oluyor.

        //blackSquareX -= 15;
        blackSquareX -= blackSquareSpeed;
        if (blackSquareX<0){
            blackSquareX = screenWidth + 20;
            //blackSquareY = screenHeight/2;
            blackSquareY = Util.generateRandomNumber(screenHeight);
            //blackSquareY = (int) Math.floor(Math.random() * screenHeight);

        }

        blackSquare.setX(blackSquareX);
        blackSquare.setY(blackSquareY);


        }

    public void moveRedTriangle(){

        redTriangleSpeed =  Math.round((float) screenWidth / 44); //hız 25 oluyor.

//        redTriangleX -= 25;
        redTriangleX -= redTriangleSpeed;
        if (redTriangleX<0){
            redTriangleX = screenWidth + 20;
            //blackSquareY = screenHeight/2;
            redTriangleY = Util.generateRandomNumber(screenHeight);

        }

        redTriangle.setX(redTriangleX);
        redTriangle.setY(redTriangleY);


    }
    public void moveYellowCircle(){

        yellowCircleSpeed =  Math.round((float) screenWidth / 60); //hız 18 oluyor.

//        yellowCircleX -= 18;
        yellowCircleX -= yellowCircleSpeed;
        if (yellowCircleX<0){
            yellowCircleX = screenWidth + 20;
            //blackSquareY = screenHeight/2;
            yellowCircleY = Util.generateRandomNumber(screenHeight);

        }

        yellowCircle.setX(yellowCircleX);
        yellowCircle.setY(yellowCircleY);


    }


    public void collisioncheck(){
        int yellowcircleCentralX= yellowCircleX + yellowCircle.getWidth()/2;
        int yellowcircleCentralY= yellowCircleY + yellowCircle.getHeight()/2;

        if (0 <= yellowcircleCentralX
                && yellowCircleX<= mainCharacterWidth
                && mainCharacterY <= yellowcircleCentralY
                && yellowcircleCentralY <= mainCharacterY+ mainCharacterHeight){
            score +=20;
            yellowCircleX= -10;
        };



        int redTriangleCentralX= redTriangleX + redTriangle.getWidth()/2;
        int redTriangleCentralY= redTriangleY + redTriangle.getHeight()/2;

        if (0 <= redTriangleCentralX
                && redTriangleX<= mainCharacterWidth
                && mainCharacterY <= redTriangleCentralY
                && redTriangleCentralY <= mainCharacterY+ mainCharacterHeight){
            score +=50;
            redTriangleX= -10;
        };



        int blackSquareCentralX= blackSquareX + blackSquare.getWidth()/2;
        int blackSquareCentralY= blackSquareY + blackSquare.getHeight()/2;

        if (0 <= blackSquareCentralX
                && blackSquareX<= mainCharacterWidth
                && mainCharacterY <= blackSquareCentralY
                && blackSquareCentralY <= mainCharacterY+ mainCharacterHeight){
            blackSquareX= -10;

            //Timer DURDUR.
            timer.cancel();
            timer = null;


            Intent intent = new Intent(GameScreen.this, ResutScreenActivity.class);
            intent.putExtra("Score", score);
            startActivity(intent);
        };



        textViewScore.setText(String.valueOf(score));



    }


}