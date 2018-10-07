package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.hoccob.mindmaster.Operation;
import net.hoccob.mindmaster.R;


public class MultiPlayerView extends SurfaceView implements Runnable {

    Paint black_paint_fill;
    public int ScreenX;
    public int ScreenY;
    Bitmap background;
    Bitmap playerBar;
    Bitmap opponentBar;
    Bitmap playerBarCover;
    Bitmap opponentBarCover;
    private int playerBarX;
    private int opponentBarX;
    int answer = 0;
    public Canvas canvas;
    private int score = 0;
    private int opponentScore = 0;
    private String opponentNickname;

    private boolean play = false;

    private Thread gameThread = null;

    private SurfaceHolder ourHolder;

    //private Operation operation;

    private long timer = 0;
    private long opponentTimer = 0;
    private long endTime;
    private long opponentEndTime;
    private boolean gameOver = false;


    private String currentOperation;


    public MultiPlayerView(Context context, int x, int y) {
        super(context);

        play = false;

        ourHolder = getHolder();

        ScreenX = x;
        ScreenY = y;

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        background = Bitmap.createScaledBitmap(background,
                ScreenX,
                ScreenY,
                false);

        playerBar = BitmapFactory.decodeResource(getResources(), R.drawable.playertimerbar);

        playerBar = Bitmap.createScaledBitmap(playerBar,
                ScreenX / 3,
                ScreenY / 21,
                false);

        opponentBar = BitmapFactory.decodeResource(getResources(), R.drawable.opponenttimerbar);

        opponentBar = Bitmap.createScaledBitmap(opponentBar,
                ScreenX / 3,
                ScreenY / 21,
                false);

        playerBarCover = BitmapFactory.decodeResource(getResources(), R.drawable.playerbarcover);

        playerBarCover = Bitmap.createScaledBitmap(playerBarCover,
                ScreenX / 3,
                ScreenY / 21,
                false);

        opponentBarCover = BitmapFactory.decodeResource(getResources(), R.drawable.opponentbarcover);

        opponentBarCover = Bitmap.createScaledBitmap(opponentBarCover,
                ScreenX / 3,
                ScreenY / 21,
                false);

        //operation = new Operation();

        //operation.createPools();

        startGame();

    }


    @Override
    public void run(){
        while(play) {
            if(timer > 130) {
                timer = endTime - System.currentTimeMillis();
                opponentTimer = opponentEndTime - System.currentTimeMillis();
                if(opponentTimer < 0){
                    opponentTimer = 0;
                }
                if(timer < 0){
                    timer = 0;
                }
                playerBarX = ScreenX/2 - playerBar.getWidth() - ((int) timer * playerBar.getWidth() / 15000);
                opponentBarX = ScreenX/2 + opponentBar.getWidth() - (opponentBar.getWidth() - (int) opponentTimer * opponentBar.getWidth() / 15000);
                //timerSec = String.valueOf(timer / 1000);
                //timerMilli = String.valueOf(timer / 100).substring(String.valueOf(timer /10).length() - 2);
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
    public boolean getGameOver(){
        return(gameOver);
    }
    public int getScore(){
        return(score);
    }
    public void setPlay(boolean play){this.play = play;}
    public void setOpponentTimer(long opponentTimer){
        this.opponentTimer = opponentTimer;
        opponentEndTime = System.currentTimeMillis() + opponentTimer;
    }

    public void startGame(){
        answer = 0;
        play = true;
        gameOver = false;
        gameThread = new Thread(this);
        gameThread.start();
        endTime = System.currentTimeMillis() + 30000;
        timer = endTime - System.currentTimeMillis();
        opponentTimer = endTime - System.currentTimeMillis();
        opponentEndTime = endTime;
    }

    public void setOpponentNickname(String opponentNickname){this.opponentNickname = opponentNickname;}
    public int getTimer(){return (int) timer;}
    public void setAnswer(int answer){this.answer = answer;}
    public void setCurrentOperation(String currentOperation){this.currentOperation = currentOperation;}
    public void setScore(int score){this.score = score;}
    public void setOpponentScore(int opponentScore){this.opponentScore = opponentScore;}
    public void timerPlus(long add){this.endTime += add;}
    public void timerMinus(long take){this.endTime -= take;}

    public void draw() {

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

            canvas.drawBitmap(background,0,0,null);
            canvas.drawBitmap(playerBar, ScreenX/2 - playerBar.getWidth(), ScreenY / 21 ,null);
            canvas.drawBitmap(opponentBar, ScreenX/2, ScreenY / 21, null);
            canvas.drawBitmap(playerBarCover, playerBarX, ScreenY / 21, null);
            canvas.drawBitmap(opponentBarCover, opponentBarX, ScreenY / 21, null);

            Paint answer_text;
            answer_text = new Paint();
            answer_text.setColor(0xFF000000);
            answer_text.setTextSize(70);

            Paint operation_text;
            operation_text = new Paint();
            operation_text.setColor(0xFF000000);
            operation_text.setTextSize(90);
            canvas.drawText(currentOperation + " = " + answer_str, ScreenX / 24, ScreenY / 3 - (ScreenY / 20), operation_text);

            canvas.drawText(String.valueOf(score), ScreenX / 8, ScreenY / 7, answer_text);
            canvas.drawText(String.valueOf(opponentScore), ScreenX - (ScreenX / 6), ScreenY / 7, answer_text);
            canvas.drawText(String.valueOf(opponentNickname), ScreenX / 2, ScreenY / 14, answer_text);


            Rect rect = new Rect(ScreenX / 2 - (ScreenX / 72), ScreenY / 21, ScreenX / 2 + (ScreenX / 72), ScreenY / 21 + playerBar.getHeight());
            canvas.drawRect(rect, operation_text);


            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

}
