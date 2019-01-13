package net.hoccob.mindmaster.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

import net.hoccob.mindmaster.ColorChange;

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
    int color = 1;
    Typeface pristina;
    private ColorChange colorChange;

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
        paint.setStrokeWidth(10);
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
        setBackgroundColor(bgPaint.getColor());

        colorChange = new ColorChange(paint, textPaint, this);

    }





    private void doButton(Path path, RectF rect, int x, int y, int posY){

        path.moveTo(x / 2, posY);
        path.lineTo(x - (x / 20), posY + (y / 20));
        path.lineTo(x - (x / 20), posY + (2 * y / 20));
        path.lineTo(x / 2, posY + (3 * y / 20));
        path.lineTo(x / 20, posY + (2 * y / 20));
        path.lineTo(x / 20, posY + (y / 20));
        path.lineTo(x / 2, posY);
        path.close();

        rect.left = x / 10;
        rect.top = posY;
        rect.right = x - (x / 20);
        rect.bottom = posY + (3 * y / 20);
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


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(startButton, paint);
        canvas.drawPath(practiceButton, paint);
        canvas.drawPath(highScoreButton, paint);
        canvas.drawPath(settingsButton, paint);
        canvas.drawText("Start Game", screenX / 4,  3 * screenY / 12, textPaint);
        canvas.drawText("Practice", screenX / 3, 5 * screenY / 12, textPaint);
        canvas.drawText("High Score", screenX / 4, 7 * screenY / 12, textPaint);
        canvas.drawText("Settings", screenX / 3, 9 * screenY / 12, textPaint);

    }
}
