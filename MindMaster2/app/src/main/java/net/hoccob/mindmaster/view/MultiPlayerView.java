package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import net.hoccob.mindmaster.Button;
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

    public Button button_1;
    public Button button_2;
    public Button button_3;
    public Button button_4;
    public Button button_5;
    public Button button_6;
    public Button button_7;
    public Button button_8;
    public Button button_9;
    public Button button_0;
    public Button button_ent;
    public Button button_c;
    public Button button_backspace;



    public MultiPlayerView(Context context, int x, int y, int colorCode) {
        super(context);

        play = false;

        ourHolder = getHolder();

        ScreenX = x;
        ScreenY = y;

        button_1 = new Button(context, R.drawable.a1, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 60), 0,colorCode);
        button_2 = new Button(context, R.drawable.a2, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 60), 0,colorCode);
        button_3 = new Button(context, R.drawable.a3, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 60), 0,colorCode);
        button_4 = new Button(context, R.drawable.a4, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 45), 0,colorCode);
        button_5 = new Button(context, R.drawable.a5, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 45), 0,colorCode);
        button_6 = new Button(context, R.drawable.a6, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 45), 0,colorCode);
        button_7 = new Button(context, R.drawable.a7, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 30), 0,colorCode);
        button_8 = new Button(context, R.drawable.a8, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 30), 0,colorCode);
        button_9 = new Button(context, R.drawable.a9, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 73, (ScreenY - ScreenY / 100 * 30), 0,colorCode);
        button_0 = new Button(context, R.drawable.a0, ScreenX/4, ScreenY/100*15, ScreenX / 100 * 40, (ScreenY - ScreenY / 100 * 15), 0,colorCode);
        button_ent = new Button(context, R.drawable.ent, ScreenX/4, ScreenY/100*15, (ScreenX/100 * 73), (ScreenY - ScreenY / 100 * 15), 0,colorCode);
        button_c = new Button(context, R.drawable.c, ScreenX/4 + ScreenX/6, ScreenY/100 * 10, ScreenX / 100 * 5, (ScreenY - ScreenY / 100 * 70), 0,colorCode);
        button_backspace = new Button(context, R.drawable.backspace_x, ScreenX/4 + ScreenX/6, ScreenY/100*10, (ScreenX/4 + ScreenX/6 + ScreenX/11), (ScreenY - ScreenY / 100 * 70), 0,colorCode);


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
                playerBarX = ScreenX/2 - playerBar.getWidth() - ((int) timer * playerBar.getWidth() / 30000);
                opponentBarX = ScreenX/2 + opponentBar.getWidth() - (opponentBar.getWidth() - (int) opponentTimer * opponentBar.getWidth() / 30000);
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
            canvas.drawBitmap(button_backspace.getBitmap(), button_backspace.getPosX(),button_backspace.getPosY(), null);

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

            Paint opponent_text;
            opponent_text = new Paint();
            opponent_text.setColor(Color.BLACK);
            opponent_text.setTextSize(30);

            canvas.drawText(currentOperation + " = " + answer_str, ScreenX / 24, ScreenY / 3 - (ScreenY / 100), operation_text);

            canvas.drawText(String.valueOf(score), ScreenX / 8, ScreenY / 6, answer_text);
            canvas.drawText(String.valueOf(opponentScore), ScreenX - (ScreenX / 6), ScreenY / 6, answer_text);
            canvas.drawText(String.valueOf(opponentNickname), ScreenX / 2 + ScreenX / 8, ScreenY / 14, opponent_text);


            Rect rect = new Rect(ScreenX / 2 - (ScreenX / 72), ScreenY / 21, ScreenX / 2 + (ScreenX / 72), ScreenY / 21 + playerBar.getHeight());
            canvas.drawRect(rect, operation_text);


            ourHolder.unlockCanvasAndPost(canvas);
        }

    }

}
