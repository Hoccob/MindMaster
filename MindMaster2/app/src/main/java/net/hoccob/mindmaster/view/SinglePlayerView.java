package net.hoccob.mindmaster.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder;

import net.hoccob.mindmaster.Operation;
import net.hoccob.mindmaster.R;

import java.util.Random;

public class SinglePlayerView extends SurfaceView implements Runnable {

    Paint black_paint_fill, pink_paint_fill, answer_text;
    public int ScreenX;
    public int ScreenY;
    Bitmap a1;
    Bitmap a2;
    Bitmap a3;
    Bitmap a4;
    Bitmap a5;
    Bitmap a6;
    Bitmap a7;
    Bitmap a8;
    Bitmap a9;
    Bitmap a0;
    Bitmap c;
    Bitmap backspace_x;
    Bitmap ent;
    int answer = 0;
    int correctAnswer = 0;
    public Canvas canvas;
    private int score = 0;
    private int level = 1;
    private int pool = 0;
    private int progress = 0;

    private boolean play = false;

    private Thread gameThread = null;

    private SurfaceHolder ourHolder;

    private Operation operation;

    private long timer = 0;
    private String timerSec;
    private String timerMilli;
    private long endTime;
    private boolean gameOver = false;

    Random generator = new Random();

    private String currentOperation;
    //private double currentAnswer;


    public SinglePlayerView(Context context, int x, int y) {
        super(context);

        play = false;

        //setBackgroundColor(0xFF000000);
        ourHolder = getHolder();

        ScreenX = x;
        ScreenY = y;



        a1 = BitmapFactory.decodeResource(getResources(), R.drawable.a1);

        a1 = Bitmap.createScaledBitmap(a1,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a2 = BitmapFactory.decodeResource(getResources(), R.drawable.a2);

        a2 = Bitmap.createScaledBitmap(a2,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a3 = BitmapFactory.decodeResource(getResources(), R.drawable.a3);

        a3 = Bitmap.createScaledBitmap(a3,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);


        a4 = BitmapFactory.decodeResource(getResources(), R.drawable.a4);

        a4 = Bitmap.createScaledBitmap(a4,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a5 = BitmapFactory.decodeResource(getResources(), R.drawable.a5);

        a5 = Bitmap.createScaledBitmap(a5,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a6 = BitmapFactory.decodeResource(getResources(), R.drawable.a6);

        a6 = Bitmap.createScaledBitmap(a6,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a7 = BitmapFactory.decodeResource(getResources(), R.drawable.a7);

        a7 = Bitmap.createScaledBitmap(a7,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a8 = BitmapFactory.decodeResource(getResources(), R.drawable.a8);

        a8 = Bitmap.createScaledBitmap(a8,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a9 = BitmapFactory.decodeResource(getResources(), R.drawable.a9);

        a9 = Bitmap.createScaledBitmap(a9,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        a0 = BitmapFactory.decodeResource(getResources(), R.drawable.a0);

        a0 = Bitmap.createScaledBitmap(a0,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        c = BitmapFactory.decodeResource(getResources(), R.drawable.c);

        c = Bitmap.createScaledBitmap(c,
                (int) (ScreenX/4 + ScreenX/6),
                (int) (ScreenY/100 * 10),
                false);
        backspace_x = BitmapFactory.decodeResource(getResources(), R.drawable.backspace_x);

        backspace_x = Bitmap.createScaledBitmap(backspace_x,
                (int) (ScreenX/4 + ScreenX/6),
                (int) (ScreenY/100 * 10),
                false);
        ent = BitmapFactory.decodeResource(getResources(), R.drawable.ent);

        ent = Bitmap.createScaledBitmap(ent,
                (int) (ScreenX/4),
                (int) (ScreenY/100 * 15),
                false);
        //Paint answer_text = new Paint();
        //answer_text.setColor(0xFF000000);
        //answer_text.setTextSize(50);

        //gameThread = new Thread(this);

        operation = new Operation();

        //currentOperation = operation.getOperation();
        //currentAnswer = operation.getAnswer();

        //gameThread = new Thread(this);

        operation.createPools();

        startGame();

    }

    @Override
    public void run(){
        while(play) {
            if(timer > 130) {
                timer = endTime - System.currentTimeMillis();
                timerSec = String.valueOf(timer / 1000);
                timerMilli = String.valueOf(timer / 100).substring(String.valueOf(timer /10).length() - 2);
            }else{
                gameOver = true;
            }
        draw();
            try {
                gameThread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void newEquation(){
        pool = generator.nextInt(2);
        //if(level == 1){
        //    currentOperation = operation.getOperation(pool);
        //    correctAnswer = operation.getAnswer(pool);
        //}else if (level == 2) {
        //    currentOperation = operation.getOperation(pool + 4);
        //    correctAnswer = operation.getAnswer(pool + 4);
        //}else if (level == 3) {
        //    currentOperation = operation.getOperation(pool + 8);
        //    correctAnswer = operation.getAnswer(pool + 8);
        //}

        currentOperation = operation.getOperation(((level - 1) * 2) + pool);
        correctAnswer = operation.getAnswer(((level - 1) * 2) + pool);
        operation.addToPool(((level - 1) * 2) + pool);
    }

    public void checkAnswer(){
        if(answer == correctAnswer){
            score = score + 10 * level;
            endTime = endTime + 1000;
            progress++;
        }else{
            score = score - 5;
            progress = progress - 2;
            endTime = endTime - 1500;
        }
        answer = 0;

        if(progress < 3){
            level = 1;
        }else if(progress < 6){
            level = 2;
        }else if(progress < 9){
            level = 3;
        }else if(progress < 12){
            level = 4;
        }else if(progress < 15){
            level = 5;
        }else{
            level = 6;
        }

        newEquation();
    }

    public void startGame(){
        answer = 0;
        play = true;
        gameOver = false;
        gameThread = new Thread(this);
        gameThread.start();
        endTime = System.currentTimeMillis() + 20000;
        timer = endTime - System.currentTimeMillis();
        newEquation();
    }

    public void pause(){
        play = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean getGameOver(){
        return(gameOver);
    }
    public int getScore(){
        return(score);
    }


    public void draw() {

        if(ourHolder.getSurface().isValid()) {
            String answer_str;
            if (answer != 0) {
                answer_str = String.valueOf(answer);
            }else{
                answer_str = "";
            }
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,255,0));
            black_paint_fill = new Paint();
            black_paint_fill.setColor(0xFF9F9FB5);
            black_paint_fill.setStyle(Paint.Style.FILL);

            pink_paint_fill = new Paint();
            pink_paint_fill.setColor(0xFFFF00FF);
            pink_paint_fill.setStyle(Paint.Style.FILL);

            Rect rectangle1 = new Rect();
            rectangle1.set(0, 0, ScreenX, ScreenY / 3);
            canvas.drawRect(rectangle1, black_paint_fill);

            canvas.drawBitmap(a7, (float)ScreenX / 100 * 5, (float)(ScreenY - ScreenY / 100 * 22.5), null);
            canvas.drawBitmap(a8, (float) (ScreenX/100 * 40), (float)(ScreenY - ScreenY / 100 * 22.5), null);
            canvas.drawBitmap(a9, (float)(ScreenX/100 * 72.5), (float)(ScreenY - ScreenY / 100 * 22.5), null);
            canvas.drawBitmap(a4, (float)ScreenX / 100 * 5, (float)(ScreenY - ScreenY / 100 * 37.5), null);
            canvas.drawBitmap(a5, (float) (ScreenX/100 * 40), (float)(ScreenY - ScreenY / 100 * 37.5), null);
            canvas.drawBitmap(a6, (float)(ScreenX/100 * 72.5), (float)(ScreenY - ScreenY / 100 * 37.5), null);
            canvas.drawBitmap(a1, (float)ScreenX / 100 * 5, (float)(ScreenY - ScreenY / 100 * 52.5), null);
            canvas.drawBitmap(a2, (float) (ScreenX/100 * 40), (float)(ScreenY - ScreenY / 100 * 52.5), null);
            canvas.drawBitmap(a3, (float)(ScreenX/100 * 72.5), (float)(ScreenY - ScreenY / 100 * 52.5), null);
            //canvas.drawBitmap(a0, ScreenX / 20 + ScreenX / 80 * 20, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3), null);
            canvas.drawBitmap(c, (float)ScreenX / 20, (float)(ScreenY - ScreenY / 100 * 62.5), null);
            canvas.drawBitmap(backspace_x, (float)(ScreenX/4 + ScreenX/6 + ScreenX/11.35), (float)(ScreenY - ScreenY / 100 * 62.5), null);
            //canvas.drawBitmap(ent, (ScreenX / 40 * 3) + (ScreenX / 80 * 20) * 2, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3), null);
            canvas.drawBitmap(a0, (float) (ScreenX/100 * 40), (float)(ScreenY - ScreenY / 100 * 7.5), null);
            canvas.drawBitmap(ent, (float)(ScreenX/100 * 72.5), (float)(ScreenY - ScreenY / 100 * 7.5), null);

            Paint answer_text;
            answer_text = new Paint();
            answer_text.setColor(0xFF000000);
            answer_text.setTextSize(70);

            //canvas.drawText(answer_str, ScreenX / 6, ScreenY / 4, answer_text);

            Paint operation_text;
            operation_text = new Paint();
            operation_text.setColor(0xFF000000);
            operation_text.setTextSize(170);
            canvas.drawText(currentOperation + " = " + answer_str, 0, ScreenY / 5, operation_text);

            canvas.drawText(String.valueOf(score), ScreenX / 8, ScreenY / 8, answer_text);


            if(gameOver){
             canvas.drawText("Game over", ScreenX/3, ScreenY/10, answer_text);
            }else {
                canvas.drawText(timerSec + ":" +  timerMilli, ScreenX / 2, ScreenY / 10, answer_text);
            }
            ourHolder.unlockCanvasAndPost(canvas);
            //answer++;
        }



    }


    @Override
    public boolean onTouchEvent (MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                if (motionEvent.getX() > ScreenX/40 && motionEvent.getX() < (ScreenX/4 + ScreenX/40) && motionEvent.getY() > (ScreenY/3) + (((ScreenY/3)*2) / 45) && motionEvent.getY() < ((ScreenY/3) + (((ScreenY/3)*2) / 45) + (ScreenY/3*2/45*10) )){
                    answer = answer * 10 + 7;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX /4 && motionEvent.getX() < (ScreenX /20 + ScreenX /2) && motionEvent.getY() > (ScreenY/3) + (((ScreenY/3)*2) / 45) && motionEvent.getY() < ((ScreenY/3) + (((ScreenY/3)*2) / 45) + (ScreenY/3*2/45*10) )) {
                    answer = answer * 10 + 8;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX/40 + ScreenX/2 && motionEvent.getX() < (ScreenX /20 + ScreenX /2 + ScreenX/4 + ScreenX/40) && motionEvent.getY() > (ScreenY/3) + (((ScreenY/3)*2) / 45) && motionEvent.getY() < ((ScreenY/3) + (((ScreenY/3)*2) / 45) + (ScreenY/3*2/45*10) )) {
                    answer = answer * 10 + 9;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX / 40 && motionEvent.getX() < (ScreenX/4 + ScreenX/40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10)+ (ScreenY/3*2/45*10)) {
                    answer = answer * 10 + 4;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX /4 && motionEvent.getX() < (ScreenX /20 + ScreenX /2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10)+ (ScreenY/3*2/45*10)) {
                    answer = answer * 10 + 5;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX/40 + ScreenX/2 && motionEvent.getX() < (ScreenX /20 + ScreenX /2 + ScreenX/4 + ScreenX/40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10)+ (ScreenY/3*2/45*10)) {
                    answer = answer * 10 + 6;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX / 40 && motionEvent.getX() < (ScreenX/4 + ScreenX/40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                    answer = answer * 10 + 1;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX /4 && motionEvent.getX() < (ScreenX /20 + ScreenX /2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                    answer = answer * 10 + 2;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX/40 + ScreenX/2 && motionEvent.getX() < (ScreenX /20 + ScreenX /2 + ScreenX/4 + ScreenX/40) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2) && motionEvent.getY() < ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 3)) {
                    answer = answer * 10 + 3;
                    //draw();
                }
                else if (motionEvent.getX() > ScreenX /20 + ScreenX /4 && motionEvent.getX() < (ScreenX /20 + ScreenX /2) && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3) && motionEvent.getY() < ((ScreenY / 3) + ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 4))) {
                    answer = answer * 10;
                    //draw();
                }
                else if (motionEvent.getX() > (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)) && motionEvent.getX() < ScreenX - ScreenX/40 && motionEvent.getY() > (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45))) && motionEvent.getY() < (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45)) + ScreenY / 3 * 2 / 3)) {
                    answer = 0;
                    //draw();
                }
                else if (motionEvent.getX() > (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)) && motionEvent.getX() < ScreenX - ScreenX/40 && motionEvent.getY() > (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45 * 2)) + ScreenY / 3 * 2 / 3) && motionEvent.getY() < (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45 * 2)) + ScreenY / 3 * 2 / 3 * 2)) {
                    answer = (answer - answer % 10) / 10;
                    //draw();
                }else if(motionEvent.getX() > ScreenX /20 + ScreenX /4 && motionEvent.getY() > ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3)){
                    checkAnswer();
                }


            break;
        }
    return true;
    }


}


