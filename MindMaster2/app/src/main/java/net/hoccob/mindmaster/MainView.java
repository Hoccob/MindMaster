package net.hoccob.mindmaster;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;


public class MainView extends View {

    Paint black_paint_fill, pink_paint_fill;
    public int ScreenX;
    public int ScreenY;
    private Bitmap one;
    int one_x, one_y;
    private  Bitmap two;
    int two_x, two_y;
    private Bitmap three;
    int three_x, three_y;
    private Bitmap four;
    int four_x, four_y;


    public MainView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(0xFF000000);
        one_x = 0;
        one_y = y/10;

        two_x = 0;
        two_y = y/10 * 3;

        three_x = 0;
        three_y =  y/2;

        four_x = 0;
        four_y = (y/10 * 7);

        ScreenX = x;
        ScreenY = y;

        one = BitmapFactory.decodeResource(getResources(), R.drawable.one);

        one = Bitmap.createScaledBitmap(one,
                (int) (600),
                (int) (150),
                false);
        two = BitmapFactory.decodeResource(getResources(), R.drawable.two);

        two = Bitmap.createScaledBitmap(two,
                (int) (600),
                (int) (150),
                false);
        three = BitmapFactory.decodeResource(getResources(), R.drawable.three);

        three = Bitmap.createScaledBitmap(three,
                (int) (600),
                (int) (150),
                false);

        four = BitmapFactory.decodeResource(getResources(), R.drawable.four);

        four = Bitmap.createScaledBitmap(four,
                (int) (600),
                (int) (150),
                false);


    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(one, one_x,one_y, null);

        canvas.drawBitmap(two, two_x,two_y, null);

        canvas.drawBitmap(three, three_x,three_y, null);

        canvas.drawBitmap(four, four_x, four_y, null);


    }

}
