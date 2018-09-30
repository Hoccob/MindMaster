package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LoadingView extends SurfaceView implements Runnable {
    Paint black_paint_fill;
    public int ScreenX;
    public int ScreenY;

    public Canvas canvas;
    private Thread gameThread = null;

    private SurfaceHolder ourHolder;
    private String text;

    public LoadingView(Context context, int x, int y) {
        super(context);

        ourHolder = getHolder();

        ScreenX = x;
        ScreenY = y;


        startScreen();
    }

    public void setText(String text){this.text = text;}

    @Override
    public void run() {
        while (true) {
            draw();
            try {
                gameThread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void startScreen(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void draw() {
        if(ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 0, 0, 0));
            black_paint_fill = new Paint();
            black_paint_fill.setColor(Color.BLACK);
            black_paint_fill.setStyle(Paint.Style.FILL);

            Rect rectangle1 = new Rect();
            rectangle1.set(0, 0, ScreenX / 2, ScreenY / 2);
            canvas.drawRect(rectangle1, black_paint_fill);

            Paint loadingText;
            loadingText = new Paint();
            loadingText.setColor(Color.GREEN);
            loadingText.setTextSize(70);

            canvas.drawText(text, ScreenX / 3, ScreenY / 2, loadingText);

            ourHolder.unlockCanvasAndPost(canvas);
        }

    }
}
