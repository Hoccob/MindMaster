package net.hoccob.mindmaster.view;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.ColorChange;
import net.hoccob.mindmaster.Operation;
//import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.activity.MainActivity;


import java.util.Random;

import static android.graphics.Paint.Style.FILL;
import static java.lang.Integer.parseInt;
/*
public class  SinglePlayerView extends SurfaceView implements Runnable {

    Paint black_paint_fill, pink_paint_fill, answer_text;
    public int ScreenX;
    public int ScreenY;

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
    private String timerMinute;
    private long endTime;
    private boolean gameOver = false;

    Random generator = new Random();

    private String currentOperation;
    //private double currentAnswer;
    /*private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_0;
    private Button button_ent;
    private Button button_c;
    private Button button_backspace;
    public Typeface pristina;

    Path button_1 = new Path();
    Path button_2 = new Path();
    Path button_3 = new Path();
    Path button_4 = new Path();
    Path button_5 = new Path();
    Path button_6 = new Path();
    Path button_7 = new Path();
    Path button_8 = new Path();
    Path button_9 = new Path();
    Path button_0 = new Path();
    Path button_enter = new Path();
    Path button_c = new Path();
    Path button_backspace = new Path();
    public RectF rect_1 = new RectF();
    public RectF rect_2 = new RectF();
    public RectF rect_3 = new RectF();
    public RectF rect_4 = new RectF();
    public RectF rect_5 = new RectF();
    public RectF rect_6 = new RectF();
    public RectF rect_7 = new RectF();
    public RectF rect_8 = new RectF();
    public RectF rect_9 = new RectF();
    public RectF rect_0 = new RectF();
    public RectF rect_enter = new RectF();
    public RectF rect_c = new RectF();
    public RectF rect_backspace = new RectF();
    Paint paint = new Paint();
    Paint bgPaint = new Paint();
    Paint textPaint = new Paint();
    Paint fillPaint = new Paint();
    int color = 1;
    private ColorChange colorChange;

    private boolean clicked_1 = false;
    private boolean clicked_2 = false;
    private boolean clicked_3 = false;
    private boolean clicked_4 = false;
    private boolean clicked_5 = false;
    private boolean clicked_6 = false;
    private boolean clicked_7 = false;
    private boolean clicked_8 = false;
    private boolean clicked_9 = false;
    private boolean clicked_0 = false;
    private boolean clicked_enter = false;
    private boolean clicked_c = false;
    private boolean clicked_backspace = false;



    public SinglePlayerView(Context context, int x, int y, Typeface tf) {
        super(context);

        ScreenX = x;
        ScreenY = y;
        this.pristina = tf;
        doButton(button_1, rect_1, x, y, y/5, x/5);

        /*button_1 = new Button(context, R.drawable.a1, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 60), 0,0);
        button_2 = new Button(context, R.drawable.a2, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 60), 0,0);
        button_3 = new Button(context, R.drawable.a3, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 60), 0,0);
        button_4 = new Button(context, R.drawable.a4, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 45), 0,0);
        button_5 = new Button(context, R.drawable.a5, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 45), 0,0);
        button_6 = new Button(context, R.drawable.a6, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 45), 0,0);
        button_7 = new Button(context, R.drawable.a7, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 30), 0,0);
        button_8 = new Button(context, R.drawable.a8, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 30), 0,0);
        button_9 = new Button(context, R.drawable.a9, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 30), 0,0);
        button_0 = new Button(context, R.drawable.a0, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 15), 0,0);
        button_ent = new Button(context, R.drawable.ent, ScreenX/4, ScreenY/100*15, (ScreenX/100 * 73), (ScreenY - ScreenY / 100 * 15), 0,0);
        button_c = new Button(context, R.drawable.c, ScreenX/4 + ScreenX/6, ScreenY/100 * 10, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 70), 0,0);
        button_backspace = new Button(context, R.drawable.backspace_x, ScreenX/4 + ScreenX/6, ScreenY/100*10, (ScreenX/4 + ScreenX/6 + ScreenX/11), (ScreenY - ScreenY / 100 * 70), 0,0);


        paint.setColor( Color.parseColor("#8EE4AF"));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(100);
        paint.setTypeface(pristina);
        paint.setAntiAlias(true);

        textPaint.setColor( Color.parseColor("#8EE4AF"));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(100);
        textPaint.setTypeface(pristina);
        textPaint.setAntiAlias(true);

        bgPaint.setColor(Color.parseColor("#05386B"));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setTextSize(100);
        bgPaint.setTypeface(pristina);
        bgPaint.setAntiAlias(true);
        setBackgroundColor(bgPaint.getColor());

        fillPaint.setColor(paint.getColor());
        fillPaint.setStyle(FILL);
        fillPaint.setAntiAlias(true);

        colorChange = new ColorChange(paint, textPaint, fillPaint, bgPaint, this);
        play = false;

        ourHolder = getHolder();

        operation = new Operation();
        operation.createPools();

        startGame();

    }

// 0.0625
    private void doButton(Path path, RectF rect, int x, int y, int posY, int posX){

        path.moveTo(posX, posY);
        path.lineTo((posX  + x * (float)0.125), posY + (float)0.025*y);
        path.lineTo((posX  + x * (float)0.125), posY + (float)0.075*y);
        path.lineTo(posX, posY + (float)0.1*y);
        path.lineTo((posX  - x * (float)0.125), posY + (float)0.075*y);
        path.lineTo((posX  - x * (float)0.125), posY + (float)0.025*y);
        path.lineTo(posX, posY);
        path.close();

        rect.left = posX - (float)0.125*x;
        rect.top = posY;
        rect.right = posX + (float)0.125*x;
        rect.bottom = posY + (float)0.1*y;
    }

    private void doBigButton(Path path, RectF rect, int x, int y, int posY, int posX){

        path.moveTo(x / 2, posY);
        path.lineTo(x - (x / 20), posY + (y / 25));
        path.lineTo(x - (x / 20), posY + (2 * y / 25));
        path.lineTo(x / 2, posY + (3 * y / 25));
        path.lineTo(x / 20, posY + (2 * y / 25));
        path.lineTo(x / 20, posY + (y / 25));
        path.lineTo(x / 2, posY);
        path.close();

        rect.left = x / 10;
        rect.top = posY;
        rect.right = x - (x / 20);
        rect.bottom = posY + (3 * y / 30);
    }

    public void increaseColor(){
        if(color < 4){
            colorChange.borderAnimatorAsc.get(color - 1).start();
            colorChange.bgAnimatorAsc.get(color - 1).start();
            color++;
        }
    }

    public void decreaseColor(){
        if(color > 1){
            colorChange.borderAnimatorDesc.get(color - 1).start();
            colorChange.bgAnimatorDesc.get(color - 1).start();
            color--;
        }
    }

    public void setButton1Clicked(boolean clicked){clicked_1 = clicked;}
    //public void setPracticeClicked(boolean clicked){practiceClicked = clicked;}
    //public void setHighScoreClicked(boolean clicked){highScoreClicked = clicked;}
    //public void setSettingsClicked(boolean clicked){settingsClicked = clicked;}

    @Override
    public void run(){
        while(play) {
            if(timer > 130) {
                timer = endTime - System.currentTimeMillis();
                timerMinute = String.valueOf(timer / 60000);
                timerSec = String.valueOf(timer / 1000 % 60);
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

    public void timerPlus(long add){this.endTime += add;}
    public void timerMinus(long take){this.endTime -= take;}

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
        if(answer == 2){
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
        }else if (progress < 18){
            level = 6;
        }else if (progress < 21){
            level = 7;
        }else if (progress < 24){
            level = 8;
        }else if (progress < 27){
            level = 9;
        }
        else{
            level = 10;
        }


        newEquation();
    }

    public void startGame(){
        answer = 0;
        play = true;
        gameOver = false;
        gameThread = new Thread(this);
        gameThread.start();
        endTime = System.currentTimeMillis() + 180000;
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
            //String answer_str;
            //if (answer != 0) {
            //    answer_str = String.valueOf(answer);
            //}else{
            //    answer_str = "";
            //}

            /*Color.parseColor("#0C0032")
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.parseColor("#272727"));
            black_paint_fill = new Paint();
            black_paint_fill.setColor(0xFF9F9FB5);
            black_paint_fill.setStyle(Paint.Style.FILL);

            pink_paint_fill = new Paint();
            pink_paint_fill.setColor(0xFFFF00FF);
            pink_paint_fill.setStyle(Paint.Style.FILL);

            canvas.drawBitmap(button_1.getBitmap(), button_1.getPosX(),button_1.getPosY(), null);
            canvas.drawBitmap(button_2.getBitmap(), button_2.getPosX(),button_2.getPosY(), null);
            canvas.drawBitmap(button_3.getBitmap(), button_3.getPosX(),button_3.getPosY(), null);
            canvas.drawBitmap(button_4.getBitmap(), button_4.getPosX(),button_4.getPosY(), null);
            canvas.drawBitmap(button_5.getBitmap(), button_5.getPosX(),button_5.getPosY(), null);
            canvas.drawBitmap(button_6.getBitmap(), button_6.getPosX(),button_6.getPosY(), null);
            canvas.drawBitmap(button_7.getBitmap(), button_7.getPosX(),button_7.getPosY(), null);
            canvas.drawBitmap(button_8.getBitmap(), button_8.getPosX(),button_8.getPosY(), null);
            canvas.drawBitmap(button_9.getBitmap(), button_9.getPosX(),button_9.getPosY(), null);
            canvas.drawBitmap(button_0.getBitmap(), button_0.getPosX(),button_0.getPosY(), null);
            canvas.drawBitmap(button_ent.getBitmap(), button_ent.getPosX(),button_ent.getPosY(), null);
            canvas.drawBitmap(button_c.getBitmap(), button_c.getPosX(),button_c.getPosY(), null);
            canvas.drawBitmap(button_backspace.getBitmap(), button_backspace.getPosX(),button_backspace.getPosY(), null);*/


            /*Paint answer_text;
            answer_text = new Paint();
            answer_text.setTypeface(pristina);
            answer_text.setColor(Color.parseColor("#FF652F"));
            answer_text.setTextSize(70);

            Paint operation_text;
            operation_text = new Paint();
            operation_text.setTypeface(pristina);
            operation_text.setColor(Color.parseColor("#FF652F"));
            operation_text.setTextSize(150);
            canvas.drawText(currentOperation + " = ", 20, ScreenY / 4 - 100, operation_text);
            canvas.drawText(answer_str, 20, ScreenY/4 + 50, operation_text);

            canvas.drawText("Score: " + String.valueOf(score), 0, 70, answer_text);


            if(gameOver){
             canvas.drawText("Game over", ScreenX/3, ScreenY/10, answer_text);
            }else {
                //int minute = 0;
                //if (parseInt(timerSec) / 60 > 1)

                canvas.drawText(timerMinute + ":" + timerSec + ":" +  timerMilli, ScreenX / 2 - 80, 100, answer_text);
            }
            ourHolder.unlockCanvasAndPost(canvas);
        }



    }
@Override
    public boolean onTouchEvent (MotionEvent motionEvent) {
    switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            if (button_1)

    }
    break;
}
}
/*
    @Override
    public boolean onTouchEvent (MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                if (button_7.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 7;
                }
                else if (button_8.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 8;
                }
                else if (button_9.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 9;
                }
                else if (button_4.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 4;
                }
                else if (button_5.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 5;
                }
                else if (button_6.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 6;
                }
                else if (button_1.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 1;
                }
                else if (button_2.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 2;
                }
                else if (button_3.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10 + 3;
                }
                else if (button_0.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = answer * 10;
                }
                else if (button_c.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = 0;
                }
                else if (button_backspace.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    answer = (answer - answer % 10) / 10;
                }
                 else if (button_ent.getRectF().contains(motionEvent.getX(), motionEvent.getY())){
                    checkAnswer();
                }


            break;
        }
    return true;
    }

*/



