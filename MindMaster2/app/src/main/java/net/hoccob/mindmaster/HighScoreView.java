package net.hoccob.mindmaster;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class HighScoreView extends View {

    Paint black_paint_fill, pink_paint_fill;
    public int ScreenX;
    public int ScreenY;



    public HighScoreView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(0xFF000000);

        ScreenX = x;
        ScreenY = y;


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        black_paint_fill = new Paint();
        black_paint_fill.setColor(Color.WHITE);
        black_paint_fill.setStyle(Paint.Style.FILL);

        pink_paint_fill = new Paint();
        pink_paint_fill.setColor(0xFFFF00FF);
        pink_paint_fill.setStyle(Paint.Style.FILL);

        Rect rectangle1 = new Rect();
        rectangle1.set(0, ScreenY/15, ScreenX/2, (ScreenY/20) * 3);
        canvas.drawRect(rectangle1, black_paint_fill);

        Rect rectangle2 = new Rect();
        rectangle2.set(500, 500, 600, 600);
        canvas.drawRect(rectangle2, pink_paint_fill);



    }

}
