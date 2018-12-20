package net.hoccob.mindmaster.view;


import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.ChangeColor;

public class MainView extends View {

    Paint black_paint_fill, pink_paint_fill;
    public int ScreenX;
    public int ScreenY;
    private Bitmap start;
    private  Bitmap practice;
    private Bitmap high_score;
    private Bitmap settings;

    int positionX;
    int sizeX;
    int sizeY;
    int start_y;
    int practice_y;
    int high_y;
    int settings_y;


    public MainView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(Color.parseColor("#0C0032"));

        positionX = x / 12;

        sizeX = x - (x / 6);
        sizeY = y / 10;

        start_y = (y / 12) * 2;
        practice_y = (y / 12) * 4;
        high_y = (y / 12) * 6;
        settings_y = (y / 12) * 8;

        ScreenX = x;
        ScreenY = y;

        SharedPreferences sharedPrefColor = context.getSharedPreferences("ColorCode",
                Context.MODE_PRIVATE);
        int colorCode = sharedPrefColor.getInt("code", 0);

        start = BitmapFactory.decodeResource(getResources(), R.drawable.start_game_ver_2);

        start = Bitmap.createScaledBitmap(start,
                sizeX,
                sizeY,
                false);
        practice = BitmapFactory.decodeResource(getResources(), R.drawable.practice_ver_2);

        practice = Bitmap.createScaledBitmap(practice,
                sizeX,
                sizeY,
                false);
        high_score = BitmapFactory.decodeResource(getResources(), R.drawable.high_score_ver_2);

        high_score = Bitmap.createScaledBitmap(high_score,
                sizeX,
                sizeY,
                false);

        settings = BitmapFactory.decodeResource(getResources(), R.drawable.settings_ver_2);

        settings = Bitmap.createScaledBitmap(settings,
                sizeX,
                sizeY,
                false);

        start = ChangeColor.makeTransparent(colorCode, start);
        practice = ChangeColor.makeTransparent(colorCode, practice);
        high_score = ChangeColor.makeTransparent(colorCode, high_score);
        settings = ChangeColor.makeTransparent(colorCode, settings);


    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawBitmap(start, positionX,start_y, null);

        canvas.drawBitmap(practice, positionX,practice_y, null);

        canvas.drawBitmap(high_score, positionX,high_y, null);

        canvas.drawBitmap(settings, positionX, settings_y, null);



    }

}
