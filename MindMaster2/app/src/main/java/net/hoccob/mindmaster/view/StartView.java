package net.hoccob.mindmaster.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.view.View;

import net.hoccob.mindmaster.ChangeColor;
import net.hoccob.mindmaster.ColorChange;

import static android.graphics.Paint.Style.FILL;

public class StartView extends View {

    int screenX;
    int screenY;
    Path startButton = new Path();
    Path practiceButton = new Path();
    Path highScoreButton = new Path();
    Path settingsButton = new Path();
    public RectF startRect = new RectF();
    public RectF practiceRect = new RectF();
    public RectF highScoreRect = new RectF();
    public RectF settingsRect = new RectF();
    Paint paint = new Paint();
    Paint bgPaint = new Paint();
    Paint textPaint = new Paint();
    Paint fillPaint = new Paint();
    int color = 1;
    Typeface pristina;
    private ColorChange colorChange;
    private float startGameLength;
    private float height;

    private boolean startClicked = false;
    private boolean practiceClicked = false;
    private boolean highScoreClicked = false;
    private boolean settingsClicked = false;

    public StartView(Context context, int x, int y, Typeface tf) {
        super(context);
        this.pristina = tf;
        //setBackgroundColor(0xFF000000);

        screenX = x;
        screenY = y;
        doButton(startButton, startRect, x, y,  y / 6);
        doButton(practiceButton, practiceRect,x,y, 2 * y / 6);
        doButton(highScoreButton, highScoreRect,x,y, 3 * y / 6);
        doButton(settingsButton, settingsRect, x,y, 4 * y / 6);

        paint.setColor( Color.parseColor("#8EE4AF"));
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(100);
        paint.setTypeface(pristina);
        paint.setAntiAlias(true);

        textPaint.setColor( Color.parseColor("#8EE4AF"));
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(100);
        //textPaint.setTypeface(pristina);
        textPaint.setAntiAlias(true);

        bgPaint.setColor(Color.parseColor("#05386B"));
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setTextSize(100);
        //bgPaint.setTypeface(pristina);
        bgPaint.setAntiAlias(true);
        setBackgroundColor(bgPaint.getColor());

        fillPaint.setColor(paint.getColor());
        fillPaint.setStyle(FILL);
        fillPaint.setAntiAlias(true);

        colorChange = new ColorChange(paint, textPaint, fillPaint, bgPaint, this);
        Paint.FontMetrics fm = paint.getFontMetrics();
        height = fm.descent - fm.ascent;

    }





    private void doButton(Path path, RectF rect, int x, int y, int posY){

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

    public void setStartClicked(boolean clicked){startClicked = clicked;}
    public void setPracticeClicked(boolean clicked){practiceClicked = clicked;}
    public void setHighScoreClicked(boolean clicked){highScoreClicked = clicked;}
    public void setSettingsClicked(boolean clicked){settingsClicked = clicked;}


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(!startClicked) {
            canvas.drawPath(startButton, paint);
            canvas.drawText("Start Game", (screenX/2 - textPaint.measureText("Start Game")/2),  ((screenY / 6) + (screenY / 20) + (height / 2)) , textPaint);
        }else{
            canvas.drawPath(startButton, fillPaint);
            canvas.drawText("Start Game", (screenX/2 - textPaint.measureText("Start Game")/2),  ((screenY / 6) + (screenY / 20) + (height / 2)), bgPaint);
        }
        if(!practiceClicked) {
            canvas.drawPath(practiceButton, paint);
            canvas.drawText("Practice", (screenX/2 - textPaint.measureText("Practice")/2), ((screenY / 3) + (screenY / 20) + (height / 2)), textPaint);
        }else{
            //canvas.drawPath(startButton, bgPaint);
            canvas.drawPath(practiceButton, fillPaint);
            canvas.drawText("Practice", (screenX/2 - textPaint.measureText("Practice")/2), ((screenY / 3) + (screenY / 20) + (height / 2)), bgPaint);
        }
        if(!highScoreClicked) {
            canvas.drawPath(highScoreButton, paint);
            canvas.drawText("High Score", (screenX/2 - textPaint.measureText("High Score")/2), ((screenY / 2) + (screenY / 20) + (height / 2)), textPaint);
        }else{
            //canvas.drawPath(startButton, bgPaint);
            canvas.drawPath(highScoreButton, fillPaint);
            canvas.drawText("High Score", (screenX/2 - textPaint.measureText("High Score")/2), ((screenY / 2) + (screenY / 20) + (height / 2)), bgPaint);
        }
        if(!settingsClicked) {
            canvas.drawPath(settingsButton, paint);
            canvas.drawText("Settings", (screenX/2 - textPaint.measureText("Settings")/2), ((int)(screenY / 1.5) + (screenY / 20) + (height / 2)), textPaint);
        }else{
            //canvas.drawPath(startButton, bgPaint);
            canvas.drawPath(settingsButton, fillPaint);
            canvas.drawText("Settings", (screenX/2 - textPaint.measureText("Settings")/2), ((int)(screenY / 1.5) + (screenY / 20) + (height / 2)), bgPaint);
        }
    }
}
