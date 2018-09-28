package net.hoccob.mindmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class MultiPlayerView extends SurfaceView implements Runnable {

    Paint black_paint_fill, pink_paint_fill, answer_text;
    public int ScreenX;
    public int ScreenY;
    Bitmap yks;
    Bitmap kaks;
    Bitmap kolm;
    Bitmap neli;
    Bitmap viis;
    Bitmap kuus;
    Bitmap seitse;
    Bitmap kaheksa;
    Bitmap yheksa;
    Bitmap nulll;
    Bitmap cee;
    Bitmap backspace;
    Bitmap enter;
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


    public MultiPlayerView(Context context, int x, int y) {
        super(context);

        play = false;

        //setBackgroundColor(0xFF000000);
        ourHolder = getHolder();

        ScreenX = x;
        ScreenY = y;



        yks = BitmapFactory.decodeResource(getResources(), R.drawable.yks);

        yks = Bitmap.createScaledBitmap(yks,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        kaks = BitmapFactory.decodeResource(getResources(), R.drawable.kaks);

        kaks = Bitmap.createScaledBitmap(kaks,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        kolm = BitmapFactory.decodeResource(getResources(), R.drawable.kolm);

        kolm = Bitmap.createScaledBitmap(kolm,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);


        neli = BitmapFactory.decodeResource(getResources(), R.drawable.neli);

        neli = Bitmap.createScaledBitmap(neli,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        viis = BitmapFactory.decodeResource(getResources(), R.drawable.viis);

        viis = Bitmap.createScaledBitmap(viis,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        kuus = BitmapFactory.decodeResource(getResources(), R.drawable.kuus);

        kuus = Bitmap.createScaledBitmap(kuus,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        seitse = BitmapFactory.decodeResource(getResources(), R.drawable.seitse);

        seitse = Bitmap.createScaledBitmap(seitse,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        kaheksa = BitmapFactory.decodeResource(getResources(), R.drawable.kaheksa);

        kaheksa = Bitmap.createScaledBitmap(kaheksa,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        yheksa = BitmapFactory.decodeResource(getResources(), R.drawable.yheksa);

        yheksa = Bitmap.createScaledBitmap(yheksa,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        nulll = BitmapFactory.decodeResource(getResources(), R.drawable.nulll);

        nulll = Bitmap.createScaledBitmap(nulll,
                (int) (ScreenX/4),
                (int) (ScreenY/3*2/45*10),
                false);
        cee = BitmapFactory.decodeResource(getResources(), R.drawable.cee);

        cee = Bitmap.createScaledBitmap(cee,
                (int) (ScreenX/80 * 10),
                (int) (ScreenY/3*2/3),
                false);
        backspace = BitmapFactory.decodeResource(getResources(), R.drawable.backspace);

        backspace = Bitmap.createScaledBitmap(backspace,
                (int) (ScreenX/80 * 10),
                (int) (ScreenY/3*2/3),
                false);
        enter = BitmapFactory.decodeResource(getResources(), R.drawable.enter);

        enter = Bitmap.createScaledBitmap(enter,
                (int) (ScreenX/80 * 35),
                (int) (ScreenY/3*2/45*15),
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
        while(!play){
            drawLoading();
        }
        while(play) {
            if(timer > 130) {
                timer = endTime - System.currentTimeMillis();
                timerSec = String.valueOf(timer / 1000);
                timerMilli = String.valueOf(timer / 100).substring(String.valueOf(timer /10).length() - 2);
            }else{
                gameOver = true;
            }
            drawGame();
            try {
                gameThread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean getGameOver(){
        return(gameOver);
    }
    public int getScore(){
        return(score);
    }
    public void setPlay(boolean play){this.play = play;}

    public void startGame(){
        answer = 0;
        play = true;
        gameOver = false;
        gameThread = new Thread(this);
        gameThread.start();
        endTime = System.currentTimeMillis() + 6000;
        timer = endTime - System.currentTimeMillis();
        //newEquation();
    }

    public void drawLoading() {

        canvas = ourHolder.lockCanvas();

        canvas.drawColor(Color.argb(255,0,0,0));
        black_paint_fill = new Paint();
        black_paint_fill.setColor(0xFF9F9FB5);
        black_paint_fill.setStyle(Paint.Style.FILL);

        Paint loadingText;
        loadingText = new Paint();
        loadingText.setColor(0xFF000000);
        loadingText.setTextSize(160);

        canvas.drawText("LOADOING!!", ScreenX / 3, ScreenY / 2, loadingText);

        ourHolder.unlockCanvasAndPost(canvas);

    }

    public void drawGame() {

        if(ourHolder.getSurface().isValid()) {
            String answer_str;
            if (answer != 0) {
                answer_str = String.valueOf(answer);
            }else{
                answer_str = "";
            }
            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255,0,0,0));
            black_paint_fill = new Paint();
            black_paint_fill.setColor(0xFF9F9FB5);
            black_paint_fill.setStyle(Paint.Style.FILL);

            pink_paint_fill = new Paint();
            pink_paint_fill.setColor(0xFFFF00FF);
            pink_paint_fill.setStyle(Paint.Style.FILL);

            Rect rectangle1 = new Rect();
            rectangle1.set(0, 0, ScreenX, ScreenY / 3);
            canvas.drawRect(rectangle1, black_paint_fill);

            canvas.drawBitmap(seitse, ScreenX / 40, (ScreenY / 3) + (((ScreenY / 3) * 2) / 45), null);
            canvas.drawBitmap(kaheksa, ScreenX / 20 + ScreenX / 80 * 20, (ScreenY / 3) + (((ScreenY / 3) * 2) / 45), null);
            canvas.drawBitmap(yheksa, (ScreenX / 40 * 3) + (ScreenX / 80 * 20) * 2, (ScreenY / 3) + (((ScreenY / 3) * 2) / 45), null);
            canvas.drawBitmap(neli, ScreenX / 40, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10), null);
            canvas.drawBitmap(viis, ScreenX / 20 + ScreenX / 80 * 20, (ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10, null);
            canvas.drawBitmap(kuus, (ScreenX / 40 * 3) + (ScreenX / 80 * 20) * 2, (ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 2) + ScreenY / 3 * 2 / 45 * 10, null);
            canvas.drawBitmap(yks, ScreenX / 40, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2), null);
            canvas.drawBitmap(kaks, ScreenX / 20 + ScreenX / 80 * 20, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2), null);
            canvas.drawBitmap(kolm, (ScreenX / 40 * 3) + (ScreenX / 80 * 20) * 2, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 3) + (ScreenY / 3 * 2 / 45 * 10) * 2), null);
            canvas.drawBitmap(nulll, ScreenX / 20 + ScreenX / 80 * 20, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3), null);
            canvas.drawBitmap(cee, (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)), (ScreenY / 3) + (((ScreenY / 3) * 2) / 45), null);
            canvas.drawBitmap(backspace, (ScreenX - ScreenX / 40 - (ScreenX / 80 * 10)), (((ScreenY / 3) + (((ScreenY / 3) * 2) / 45 * 2)) + ScreenY / 3 * 2 / 3), null);
            canvas.drawBitmap(enter, (ScreenX / 40 * 3) + (ScreenX / 80 * 20) * 2, ((ScreenY / 3) + ((((ScreenY / 3) * 2) / 45) * 4) + (ScreenY / 3 * 2 / 45 * 10) * 3), null);
            Paint answer_text;
            answer_text = new Paint();
            answer_text.setColor(0xFF000000);
            answer_text.setTextSize(70);

            //canvas.drawText(answer_str, ScreenX / 6, ScreenY / 4, answer_text);

            Paint operation_text;
            operation_text = new Paint();
            operation_text.setColor(0xFF000000);
            operation_text.setTextSize(90);
            canvas.drawText(currentOperation + " = " + answer_str, ScreenX / 6, ScreenY / 5, operation_text);

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

}