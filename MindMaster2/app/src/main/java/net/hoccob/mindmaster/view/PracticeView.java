package net.hoccob.mindmaster.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.R;

public class PracticeView extends View {

    Paint black_paint_fill_1, pink_paint_fill_1;
    public int ScreenX;
    public int ScreenY;
    Button button_pluss;
    Button button_miinus;
    Button button_jagamine;
    Button button_korrutamine;
    Button button_mix;



    public PracticeView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(0xFF000000);

        ScreenX = x;
        ScreenY = y;

        button_pluss = new Button (context, R.drawable.pluss, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY / 2), 0, 0 );
        button_miinus = new Button(context, R.drawable.miinus, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 30, (ScreenY /2), 0, 0 );
        button_jagamine = new Button (context, R.drawable.jagamine, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY / 2 + ScreenY/100*15), 0, 0 );
        button_korrutamine = new Button (context, R.drawable.iks, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 30, (ScreenY / 2 + ScreenY/100*15), 0, 0 );
        button_mix = new Button (context, R.drawable.mix, ScreenX/100 * 25, ScreenY/100*15, ScreenX / 100 * 5, (ScreenY / 2 + ScreenY/100*30), 0, 0 );
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        black_paint_fill_1 = new Paint();
        black_paint_fill_1.setColor(Color.YELLOW);
        black_paint_fill_1.setStyle(Paint.Style.FILL);

        pink_paint_fill_1 = new Paint();
        pink_paint_fill_1.setColor(0xFFFF00FF);
        pink_paint_fill_1.setStyle(Paint.Style.FILL);




    }

}
