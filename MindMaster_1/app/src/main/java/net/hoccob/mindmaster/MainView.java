package net.hoccob.mindmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;


public class MainView extends SurfaceView implements Runnable {


    private Context context;
    private Thread gamethread = null;
    private SurfaceHolder ourHolder;
    private volatile boolean playing = false;
    private boolean paused = true;
    private Canvas canvas;
    private Paint paint;
    private Buttonz buttonz;


    private float screenX;
    private float screenY;



    public MainView(Context context, float x, float y){
        super(context);

        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;



        //prepareLevel();
        gamethread = new Thread(this);
    }
    private void draw(){

        buttonz = new Buttonz(context, screenX);

        //if(ourHolder.getSurface().isValid()){
        canvas = ourHolder.lockCanvas();
        canvas.drawColor(Color.argb(255,255,0,127));
            //paint.setColor(Color.argb(255,255,255,255));

        canvas.drawBitmap(buttonz.getBitmap(), screenX / 2 - screenX / 4, screenX / 9, paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(28);
        canvas.drawText("Score: " + 12,10,50,paint);

            ourHolder.unlockCanvasAndPost(canvas);
        //}
    }
    @Override
    public void run(){
        draw();

    }
}
