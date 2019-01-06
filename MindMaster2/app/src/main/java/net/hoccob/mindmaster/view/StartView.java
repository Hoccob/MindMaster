package net.hoccob.mindmaster.view;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class StartView extends View {

    int screenX;
    int screenY;
    Path button = new Path();
    public RectF buttonRect = new RectF();
    Paint paint = new Paint();
    int color = 1;

    int colorFrom = Color.parseColor("#8EE4AF");
    int colorTo = Color.parseColor("#3500D3");
    ValueAnimator colorAnimation1 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
    ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, colorFrom);

    public StartView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(0xFF000000);

        screenX = x;
        screenY = y;
        doButton(button, buttonRect, x, y, y / 20);

        paint.setColor( Color.parseColor("#8EE4AF"));

        colorAnimation1.setDuration(500); // milliseconds
        colorAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                paint.setColor((int) animator.getAnimatedValue());
                invalidate();
            }

        });

        colorAnimation2.setDuration(500); // milliseconds
        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                paint.setColor((int) animator.getAnimatedValue());
                invalidate();
            }

        });

    }





    private void doButton(Path path, RectF rect, int x, int y, int posY){
        path.moveTo(x / 10, posY);
        path.lineTo(x - (x / 10), posY);
        path.lineTo(x - (x / 20), posY + (y / 20));
        path.lineTo(x - (x / 20), posY + (2 * y / 20));
        path.lineTo(x - (x / 10), posY + (3 * y / 20));
        path.lineTo(x / 10, posY + (3 * y / 20));
        path.lineTo(x / 20, posY + (2 * y / 20));
        path.lineTo(x / 20, posY + (y / 20));
        path.lineTo(x / 10, posY);
        path.close();

        rect.left = x / 10;
        rect.top = posY;
        rect.right = x - (x / 20);
        rect.bottom = posY + (3 * y / 20);
    }

    public void changePaint(){
        if(color == 1) {
            colorAnimation1.start();
            color = 2;
        }else{
            colorAnimation2.start();
            color = 1;
        }
    }


    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        canvas.drawPath(button, paint);
    }
}
