package net.hoccob.mindmaster.view;


import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.ChangeColor;

public class MainView extends View {

    Paint black_paint_fill, pink_paint_fill;
    public int ScreenX;
    public int ScreenY;
    private Bitmap start;
    private Bitmap practice;
    private Bitmap high_score;
    private Bitmap settings;

    int positionX;
    int sizeX;
    int sizeY;
    int start_y;
    int practice_y;
    int high_y;
    int settings_y;

    private RectF startRect;
    //private RectF practiceRect;

    public Button practiceButton;


    public MainView(Context context, int x, int y) {
        super(context);
        setBackgroundColor(Color.parseColor("#272727"));

        positionX = x / 12;

        sizeX = x - (x / 6);
        sizeY = y / 10;

        start_y = (y / 12) * 2;
        practice_y = (y / 12) * 4;
        high_y = (y / 12) * 6;
        settings_y = (y / 12) * 8;

        startRect = new RectF();

        startRect.top = start_y;
        startRect.bottom = start_y + sizeY;
        startRect.left = positionX;
        startRect.right = positionX + sizeX;

       /* practiceRect = new RectF();

        practiceRect.top = practice_y;
        practiceRect.bottom = practice_y + sizeY;
        practiceRect.left = positionX;
        practiceRect.right = positionX + sizeX;*/

        practiceButton = new Button(context, R.drawable.practice_ver_2, sizeX, sizeY, positionX, practice_y, 0);

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
        /*practice = BitmapFactory.decodeResource(getResources(), R.drawable.practice_ver_2);

        practice = Bitmap.createScaledBitmap(practice,
                sizeX,
                sizeY,
                false);*/
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

        start = ChangeColor.makeTransparent(start);
        //practice = ChangeColor.makeTransparent(practice);
        high_score = ChangeColor.makeTransparent(high_score);
        settings = ChangeColor.makeTransparent(settings);

        start = ChangeColor.colorByCode(colorCode, start);
        //practice = ChangeColor.colorByCode(colorCode, practice);
        high_score = ChangeColor.colorByCode(colorCode, high_score);
        settings = ChangeColor.colorByCode(colorCode, settings);


    }

    //public RectF getPracticeRect(){return practiceRect;}

    //public void invertStart(){start = ChangeColor.invertColors(start); }
    //public void invertPractice(){practice = ChangeColor.invertColors(practice);}
    //public void resetPractice(){practice = ChangeColor.colorByCode(0, practice);}


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(start, positionX,start_y, null);
        canvas.drawBitmap(practiceButton.getBitmap(), practiceButton.getPosX(),practiceButton.getPosY(), null);
        canvas.drawBitmap(high_score, positionX,high_y, null);
        canvas.drawBitmap(settings, positionX, settings_y, null);

    }

   /* @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:

                if(practiceRect.contains(motionEvent.getX(), motionEvent.getY())){
                    practice = ChangeColor.invertColors(practice);
                }


        }

        return true;
    }*/

}
