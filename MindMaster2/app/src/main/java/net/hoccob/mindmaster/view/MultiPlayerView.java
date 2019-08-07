package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.ColorChange;

import static android.graphics.Paint.Style.FILL;
//import net.hoccob.mindmaster.R;


// View ymber kirjutada, mehhaanika activitysse!!

public class MultiPlayerView extends View {

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

    int screenX;
    int screenY;
    public String str;
    public String string;
    public Typeface pristina;
    public Paint TestPaint = new Paint();
    public int textWidth_8;
    public int textWidth_c;
    public int textWidth_back;
    public String currentEquation;
    public String currentAnswerText = "";

    public int textHeight;

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
    Path backspace = new Path();

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

    private ColorChange colorChange;
    int height;


    Paint paint = new Paint();
    Paint bgPaint = new Paint();
    Paint textPaint = new Paint();
    Paint fillPaint = new Paint();
    Paint arrowPaint = new Paint();
    Paint arrowPaintInverted = new Paint();
    Paint calculationPaint = new Paint();

    public int textAscent;

    int color = 1;


    public MultiPlayerView(Context context, int x, int y, int colorCode, Typeface tf) {
        super(context);

        height = (int) (0.1 * y);
        screenX = x;
        screenY = y;
        this.pristina = tf;
        doButton(button_7, rect_7, x, y, (int) (screenY*0.6) , (int)(screenX*0.14));
        doButton(button_8, rect_8, x, y, (int) (screenY*0.6) , (int)(screenX*0.38));
        doButton(button_9, rect_9, x, y, (int) (screenY*0.6) , (int)(screenX*0.62));
        doButton(button_4, rect_4, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int)(screenX*0.14));
        doButton(button_1, rect_1, x, y, (int) (screenY*0.6) + (int)(screenY * 0.2), (int)(screenX*0.14));
        doButton(button_2, rect_2, x, y, (int) (screenY*0.6) + (int)(screenY * 0.2), (int) (screenX*0.38));
        doButton(button_3, rect_3, x, y, (int) (screenY*0.6)+ (int)(screenY * 0.2), (int) (screenX*0.62));
        doButton(button_5, rect_5, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int) (screenX*0.38));
        doButton(button_6, rect_6, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int) (screenX*0.62));
        doButton(button_0, rect_0, x, y, (int) (screenY*0.6)+ (int)(screenY * 0.3), (int) (screenX*0.38));
        doBigButton(button_c, rect_c, x, y, (int) (screenY * 0.55), (int)(screenX * 0.04));
        doBigButton(button_backspace, rect_backspace, x, y, (int)(screenY * 0.55), (int)(screenX * 0.50));
        doBackSpace(backspace, x, y, (int)(screenY * 0.55), (int)(screenX * 0.60));
        doEnterButton(button_enter,rect_enter, x, y,(int) (screenY*0.6) ,(int) (screenX*0.86));


        paint.setColor(Color.parseColor("#8EE4AF"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(screenX/6);
        paint.setStrokeWidth(4);
        //paint.setTypeface(pristina);
        paint.setAntiAlias(true);

        arrowPaint.setColor(Color.parseColor("#8EE4AF"));
        arrowPaint.setStrokeWidth(10);
        arrowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaint.setAntiAlias(true);

        arrowPaintInverted.setColor(Color.parseColor("#05386B"));
        arrowPaintInverted.setStrokeWidth(10);
        arrowPaintInverted.setStyle(Paint.Style.FILL_AND_STROKE);
        arrowPaintInverted.setAntiAlias(true);



        textPaint.setColor(Color.parseColor("#8EE4AF"));
        textPaint.setStrokeWidth(2);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(screenX/6);
        textPaint.setAntiAlias(true);

        calculationPaint.setColor(Color.parseColor("#8EE4AF"));
        calculationPaint.setStrokeWidth(2);
        calculationPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        calculationPaint.setTextSize(screenX/6);
        calculationPaint.setAntiAlias(true);

        textHeight = (int) calcTextHeight("1", textPaint);

        textWidth_8 = (int) calcTextWidth("8", textPaint);
        textWidth_back = (int) TestPaint.measureText("BACK");
        textWidth_c = (int) TestPaint.measureText("C");



        bgPaint.setColor(Color.parseColor("#05386B"));
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bgPaint.setTextSize(screenX/6);
        bgPaint.setAntiAlias(true);
        setBackgroundColor(bgPaint.getColor());

        fillPaint.setColor(paint.getColor());
        fillPaint.setStyle(FILL);
        fillPaint.setAntiAlias(true);

        colorChange = new ColorChange(paint, textPaint, fillPaint, bgPaint, this);
    }

    private int calcXposition (int posX, int charWidth){
        return  (posX - (charWidth /2));
    }

    private int calcYposition (int posY, int height, int charHeight){
        return posY + ((height + charHeight) / 2);
    }
    private int calcYposition2 (int posY, int charHeight){
        return posY + charHeight/2;
    }
    private int calcXposition2 (int posX, int charWidth, int x){
        return (int) (posX + ( 0.22 *x  - charWidth /2));
    }

    private float calcTextWidth(String text, Paint textPaint) {

        Rect textBoundsTmp = new Rect();

        textPaint.getTextBounds(text, 0, text.length(), textBoundsTmp);
        float width = textBoundsTmp.left + textBoundsTmp.width();

        return width;
    }
    private float calcTextHeight(String text, Paint textPaint) {

        Rect textBoundsTmp = new Rect();

        textPaint.getTextBounds(text, 0, text.length(), textBoundsTmp);
        float height = textBoundsTmp.bottom + textBoundsTmp.height();
        return height;
    }

    private void doButton(Path path, RectF rect, int x, int y, int posY, int posX) {

        path.moveTo(posX, posY);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.075 * y);
        path.lineTo(posX, posY + (float) 0.1 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.075 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo(posX, posY);
        path.close();

        rect.left = posX - (float) 0.125 * x;
        rect.top = posY;
        rect.right = posX + (float) 0.125 * x;
        rect.bottom = posY + (float) 0.1 * y;
    }

    private void doBigButton(Path path, RectF rect, int x, int y, int posY, int posX ){
        path.moveTo(posX, posY);
        path.lineTo(posX + x * 0.1f, posY - y * 0.035f);
        path.lineTo(posX + x * 0.34f, posY - y * 0.035f);
        path.lineTo(posX + x * 0.44f, posY);
        path.lineTo(posX + x * 0.34f, posY + y * 0.035f);
        path.lineTo(posX + x * 0.1f, posY + y * 0.035f);
        path.lineTo(posX, posY);
        path.close();

        rect.left = posX;
        rect.top = posY - 0.1f * y;
        rect.right = posX + 0.44f *x;
        rect.bottom = posY + 0.1f *y;

    }

    private void doBackSpace(Path path, int x, int y, int posY, int posX){
        //path.moveTo(posX + x*0.24f , posY);
        path.moveTo(posX, posY);
        path.lineTo(posX + x*0.05f, posY - y*0.020f);
        path.lineTo(posX + x*0.05f, posY + y*0.020f);
        //path.lineTo(posX+ x*0.24f, posY + y*0.020f);
        //path.lineTo(posX+ x*0.05f, posY + y*0.02f);
        path.lineTo(posX, posY);
        path.close();
        path.moveTo(posX + x*0.24f , posY);
        path.lineTo(posX, posY);
        path.close();


    }
    private void doEnterButton(Path path, RectF rect, int x,int y, int posY, int posX)
    {
        path.moveTo(posX, posY);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.275 * y);
        path.lineTo(posX, posY + (float)0.3 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.275 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo(posX, posY);
        path.close();

        rect.left = posX - (float) 0.1 * x;
        rect.top = posY;
        rect.right = posX + (float) 0.1 * x;
        rect.bottom = posY + (float) 0.3 * y;

    }

    public void increaseColor() {
        if (color < 4) {
            colorChange.borderAnimatorAsc.get(color - 1).start();
            colorChange.bgAnimatorAsc.get(color - 1).start();
            color++;
        }
    }

    public void decreaseColor() {
        if (color > 1) {
            colorChange.borderAnimatorDesc.get(color - 1).start();
            colorChange.bgAnimatorDesc.get(color - 1).start();
            color--;
        }
    }

    public void setButton_1Clicked(boolean clicked) {clicked_1 = clicked;}
    public void setButton_4Clicked(boolean clicked) {clicked_4 = clicked;}
    public void setButton_7Clicked(boolean clicked) {clicked_7 = clicked;}
    public void setButton_2Clicked(boolean clicked) {clicked_2 = clicked;}
    public void setButton_3Clicked(boolean clicked) {clicked_3 = clicked;}
    public void setButton_5Clicked(boolean clicked) {clicked_5 = clicked;}
    public void setButton_6Clicked(boolean clicked) {clicked_6 = clicked;}
    public void setButton_8Clicked(boolean clicked) {clicked_8 = clicked;}
    public void setButton_9Clicked(boolean clicked) {clicked_9 = clicked;}
    public void setButton_0Clicked(boolean clicked) {clicked_0 = clicked;}
    public void setButton_BackspaceClicked(boolean clicked) {clicked_backspace = clicked;  }
    public void setButton_cClicked(boolean clicked) {clicked_c = clicked;  }
    public void setButton_EnterClicked(boolean clicked){clicked_enter = clicked;}


    /*@Override
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
            //draw();
            try {
                gameThread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
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
        //gameThread = new Thread(this);
        //gameThread.start();
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

    @Override
    public void onDraw(Canvas canvas) {
            /*String answer_str;
            if (answer != 0) {
                answer_str = String.valueOf(answer);
            }else{
                answer_str = "";
            }*/
            //canvas = ourHolder.lockCanvas();
            //canvas.drawColor(Color.argb(255,0,0,0));
        if (!clicked_7) {
            canvas.drawPath(button_7, paint);
            canvas.drawText("7", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), textPaint);


        } else {
            canvas.drawPath(button_7, fillPaint);
            canvas.drawText("7", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), bgPaint);

        }
        if (!clicked_8) {
            canvas.drawPath(button_8, paint);
            canvas.drawText("8", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_8, fillPaint);
            canvas.drawText("8", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_9) {
            canvas.drawPath(button_9, paint);
            canvas.drawText("9", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_9, fillPaint);
            canvas.drawText("9", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_4) {
            canvas.drawPath(button_4, paint);
            canvas.drawText("4", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_4, fillPaint);
            canvas.drawText("4", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_1) {
            canvas.drawPath(button_1, paint);
            canvas.drawText("1", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_1, fillPaint);
            canvas.drawText("1", calcXposition((int) (screenX * 0.14), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_2) {
            canvas.drawPath(button_2, paint);
            canvas.drawText("2", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_2, fillPaint);
            canvas.drawText("2", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_3) {
            canvas.drawPath(button_3, paint);
            canvas.drawText("3", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_3, fillPaint);
            canvas.drawText("3", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.2), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_5) {
            canvas.drawPath(button_5, paint);
            canvas.drawText("5", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_5, fillPaint);
            canvas.drawText("5", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_6) {
            canvas.drawPath(button_6, paint);
            canvas.drawText("6", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_6, fillPaint);
            canvas.drawText("6", calcXposition((int) (screenX * 0.62), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.1), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_0) {
            canvas.drawPath(button_0, paint);
            canvas.drawText("0", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.3), (int) (screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_0, fillPaint);
            canvas.drawText("0", calcXposition((int) (screenX * 0.38), textWidth_8), calcYposition((int) (screenY * 0.6) + (int) (screenY * 0.3), (int) (screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_backspace) {
            canvas.drawPath(button_backspace, paint);
            canvas.drawPath(backspace, arrowPaint);

        } else {
            canvas.drawPath(button_backspace, textPaint);
            canvas.drawPath(backspace, arrowPaintInverted);
        }
        if (!clicked_c) {
            canvas.drawPath(button_c, paint);
            canvas.drawText("C", calcXposition2((int) (screenX * 0.04), textWidth_8, screenX), calcYposition2((int) (screenY * 0.55), textHeight), textPaint);

        } else {
            canvas.drawPath(button_c, fillPaint);
            canvas.drawText("C", calcXposition2((int) (screenX * 0.04), textWidth_8, screenX), calcYposition2((int) (screenY * 0.55), textHeight), bgPaint);
        }
        if (!clicked_enter) {
            canvas.drawPath(button_enter, paint);
            canvas.drawText("=", calcXposition((int) (screenX * 0.86), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.3), textHeight), textPaint);

        } else {
            canvas.drawPath(button_enter, fillPaint);
            canvas.drawText("=", calcXposition((int) (screenX * 0.86), textWidth_8), calcYposition((int) (screenY * 0.6), (int) (screenY * 0.3), textHeight), bgPaint);
        }
        //currentEquation = "1123-1225";
        //String currentAnswerText = "599";
        int equationWidth = (int) calcTextWidth(currentEquation,  textPaint) ;
        int answerWidth = (int) calcTextWidth(currentAnswerText, textPaint);

        canvas.drawText(currentEquation, screenX / 2 - equationWidth / 2, screenY / 3 , textPaint);
        canvas.drawText(currentAnswerText,screenX / 2 - answerWidth / 2,  screenY * 0.45f, textPaint);
            /*.drawBitmap(button_ent.getBitmap(), button_ent.getPosX(),button_ent.getPosY(), null);
            canvas.drawBitmap(button_c.getBitmap(), button_c.getPosX(),button_c.getPosY(), null);
            canvas.drawBitmap(button_backspace.getBitmap(), button_backspace.getPosX(),button_backspace.getPosY(), null);

            canvas.drawBitmap(playerBar, ScreenX/2 - playerBar.getWidth(), ScreenY / 21 ,null);
            canvas.drawBitmap(opponentBar, ScreenX/2, ScreenY / 21, null);
            canvas.drawBitmap(playerBarCover, playerBarX, ScreenY / 21, null);
            canvas.drawBitmap(opponentBarCover, opponentBarX, ScreenY / 21, null);*/

            /*Paint answer_text;
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
            canvas.drawRect(rect, operation_text);*/
        }

}
