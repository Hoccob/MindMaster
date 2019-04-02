package net.hoccob.mindmaster.view;


import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import net.hoccob.mindmaster.Button;
import net.hoccob.mindmaster.Button2;
//import net.hoccob.mindmaster.R;
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


    public Button2 startButton;
    public Button2 practiceButton;
    public Button2 highScoreButton;
    public Button2 settingsButton;


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


        ScreenX = x;
        ScreenY = y;

    }

    public void setButtons(Button2 startButton, Button2 practiceButton, Button2 highScoreButton, Button2 settingsButton){
        this.startButton = startButton;
        this.practiceButton = practiceButton;
        this.highScoreButton = highScoreButton;
        this.settingsButton = settingsButton;
    }

    public void setColors(int colorCode){
        startButton.changeColor(colorCode);
      try {
          invalidate();
      }catch (Exception e){
          e.printStackTrace();
        }
        practiceButton.changeColor(colorCode);
        try {
            invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
        highScoreButton.changeColor(colorCode);
        try {
            invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
        settingsButton.changeColor(colorCode);
        try {
            invalidate();
        }catch (Exception e){
            e.printStackTrace();
        }
      //  setBackgroundColor(ChangeColor.setBGColor(colorCode));
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
