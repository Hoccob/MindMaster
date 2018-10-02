package net.hoccob.mindmaster.view;


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
    private String one;
    private String two;
    private String three;
    private String four;
    private String five;
    int spacing = 0;



    public HighScoreView(Context context, int x, int y, int highScore1, int highScore2, int highScore3, int highScore4, int highScore5 ) {
        super(context);
        setBackgroundColor(0xFF000000);

        ScreenX = x;
        ScreenY = y;
        one = String.valueOf(highScore1);
        two = String.valueOf(highScore2);
        three = String.valueOf(highScore3);
        four = String.valueOf(highScore4);
        five = String.valueOf(highScore5);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        Paint operation_text;
        operation_text = new Paint();
        operation_text.setColor(Color.WHITE);
        operation_text.setTextSize(90);



        canvas.drawText(one, ScreenX / 6, ScreenY / 5, operation_text);
        canvas.drawText("1.", ScreenX / 6 - 100, ScreenY / 5, operation_text);
        spacing = ScreenY / 5 + 200;
        canvas.drawText(two, ScreenX / 6, spacing, operation_text);
        canvas.drawText("2.", ScreenX / 6 - 100, spacing, operation_text);
        spacing = spacing + 200;
        canvas.drawText(three, ScreenX / 6, spacing, operation_text);
        canvas.drawText("3.", ScreenX / 6 - 100, spacing, operation_text);
        spacing = spacing + 200;
        canvas.drawText(four, ScreenX / 6, spacing, operation_text);
        canvas.drawText("4.", ScreenX / 6 - 100, spacing, operation_text);
        spacing = spacing + 200;
        canvas.drawText(five, ScreenX / 6, spacing, operation_text);
        canvas.drawText("5.", ScreenX / 6 - 100, spacing, operation_text);
    }

}
