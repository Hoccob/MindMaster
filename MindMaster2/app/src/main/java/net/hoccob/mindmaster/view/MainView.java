package net.hoccob.mindmaster.view;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.R;
import net.hoccob.mindmaster.ChangeColor;

public class MainView extends View {

    public int ScreenX;
    public int ScreenY;

    int positionX;
    int sizeX;
    int sizeY;
    int start_y;
    int practice_y;
    int high_y;
    int settings_y;


    public Button startButton;
    public Button practiceButton;
    public Button highScoreButton;
    public Button settingsButton;


    public MainView(Context context, int x, int y, int colorCode) {
        super(context);
        setBackgroundColor(ChangeColor.setBGColor(colorCode));

        positionX = x / 12;

        sizeX = x - (x / 6);
        sizeY = y / 10;

        start_y = (y / 12) * 2;
        practice_y = (y / 12) * 4;
        high_y = (y / 12) * 6;
        settings_y = (y / 12) * 8;


        startButton = new Button(context, R.drawable.start_game_ver_2, sizeX, sizeY, positionX, start_y, 0,colorCode);
        practiceButton = new Button(context, R.drawable.practice_ver_2, sizeX, sizeY, positionX, practice_y, 0,colorCode);
        highScoreButton = new Button(context, R.drawable.high_score_ver_2, sizeX, sizeY, positionX, high_y, 0,colorCode);
        settingsButton = new Button(context, R.drawable.settings_ver_2, sizeX, sizeY, positionX, settings_y, 0,colorCode);


        ScreenX = x;
        ScreenY = y;

    }

    public void setColors(int colorCode){
        startButton.changeColor(colorCode);
      //  invalidate();
        practiceButton.changeColor(colorCode);
     //   invalidate();
        highScoreButton.changeColor(colorCode);
      //  invalidate();
        settingsButton.changeColor(colorCode);
      //  invalidate();
        setBackgroundColor(ChangeColor.setBGColor(colorCode));
       // invalidate();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(startButton.getBitmap(), startButton.getPosX(), startButton.getPosY(), null);
        canvas.drawBitmap(practiceButton.getBitmap(), practiceButton.getPosX(),practiceButton.getPosY(), null);
        canvas.drawBitmap(highScoreButton.getBitmap(), highScoreButton.getPosX(), highScoreButton.getPosY(), null);
        canvas.drawBitmap(settingsButton.getBitmap(), settingsButton.getPosX(), settingsButton.getPosY(), null);

    }


}
