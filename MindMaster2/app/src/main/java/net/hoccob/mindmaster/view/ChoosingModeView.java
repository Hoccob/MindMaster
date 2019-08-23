package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.View;

import net.hoccob.mindmaster.Button;

public class ChoosingModeView extends View {
    Button button_pluss;
    public int screenX;
    public int screenY;
    private Paint paint;

    public ChoosingModeView(Context context, int x, int y) {
        super(context);
        screenX = x/2;
        screenY = y/2;
        paint = new Paint();
        paint.setColor( Color.WHITE);
        paint.setStrokeWidth(6);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(100);
        paint.setAntiAlias(true);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawColor( Color.parseColor("#8EE4AF"));
        canvas.drawText("Apex", screenX/2,screenY/2, paint );
        //canvas.drawBitmap(button_pluss.getBitmap(), screenX/2, screenY/2, null);
    }

}
