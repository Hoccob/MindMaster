package net.hoccob.mindmaster.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebHistoryItem;

import junit.framework.Test;

import net.hoccob.mindmaster.ColorChange;

import static android.graphics.Paint.Style.FILL;

public class SinglePlayerView extends View {

    public Typeface pristina;
    public Paint TestPaint = new Paint();

    int screenX;
    int screenY;
    public String str;
    public String string;
    public int textWidth_1;
    public int textWidth_2;
    public int textWidth_3;
    public int textWidth_4;
    public int textWidth_5;
    public int textWidth_6;
    public int textWidth_7;
    public int textWidth_8;
    public int textWidth_9;

    public int textHeight;
    public int textAscent;

    Path button_1 = new Path();
    Path button_2 = new Path();
    Path button_3 = new Path();
    Path button_4 = new Path();
    Path button_5 = new Path();
    Path button_6 = new Path();
    Path button_7 = new Path();
    Path button_8 = new Path();
    Path button_9 = new Path();
    Path button_0 = new Path();
    Path button_enter = new Path();
    Path button_c = new Path();
    Path button_backspace = new Path();
    public RectF rect_1 = new RectF();
    public RectF rect_2 = new RectF();
    public RectF rect_3 = new RectF();
    public RectF rect_4 = new RectF();
    public RectF rect_5 = new RectF();
    public RectF rect_6 = new RectF();
    public RectF rect_7 = new RectF();
    public RectF rect_8 = new RectF();
    public RectF rect_9 = new RectF();
    public RectF rect_0 = new RectF();
    public RectF rect_enter = new RectF();
    public RectF rect_c = new RectF();
    public RectF rect_backspace = new RectF();
    Paint paint = new Paint();
    Paint bgPaint = new Paint();
    Paint textPaint = new Paint();
    Paint fillPaint = new Paint();
    int color = 1;
    private ColorChange colorChange;
    int height = 0;

    private boolean clicked_1 = false;
    private boolean clicked_2 = false;
    private boolean clicked_3 = false;
    private boolean clicked_4 = false;
    private boolean clicked_5 = false;
    private boolean clicked_6 = false;
    private boolean clicked_7 = false;
    private boolean clicked_8 = false;
    private boolean clicked_9 = false;
    private boolean clicked_0 = false;
    private boolean clicked_enter = false;
    private boolean clicked_c = false;
    private boolean clicked_backspace = false;

    public SinglePlayerView(Context context, int x, int y, Typeface tf) {
        super(context);
        height = (int) (0.1 * y);
        screenX = x;
        screenY = y;
        this.pristina = tf;
        doButton(button_7, rect_7, x, y, (int) (screenY*0.6) , (int)(screenX*0.14));
        doButton(button_8, rect_8, x, y, (int) (screenY*0.6) , (int)(screenX*0.38));
        doButton(button_9, rect_9, x, y, (int) (screenY*0.6) , (int)(screenX*0.62));
        doButton(button_4, rect_4, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int)(screenX*0.14));
        doButton(button_1, rect_1, x, y, (int) (screenY*0.6) + (int)(screenY * 0.2), (int)(screenX*0.14));
        doButton(button_2, rect_2, x, y, (int) (screenY*0.6) + (int)(screenY * 0.2), (int) (screenX*0.38));
        doButton(button_3, rect_3, x, y, (int) (screenY*0.6)+ (int)(screenY * 0.2), (int) (screenX*0.62));
        doButton(button_5, rect_5, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int) (screenX*0.38));
        doButton(button_6, rect_6, x, y, (int) (screenY*0.6) + (int)(screenY * 0.1), (int) (screenX*0.62));
        doButton(button_0, rect_0, x, y, (int) (screenY*0.6)+ (int)(screenY * 0.3), (int) (screenX*0.38));

        paint.setColor(Color.parseColor("#8EE4AF"));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(screenX/6);
        //paint.setTypeface(pristina);
        paint.setAntiAlias(true);


        textPaint.setColor(Color.parseColor("#8EE4AF"));
        textPaint.setStrokeWidth(2);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(screenX/6);
        //textPaint.setTypeface(pristina);
        textPaint.setAntiAlias(true);

        textHeight = (int) calcTextHeight("1", textPaint);

        textWidth_1 = (int) calcTextWidth("1", textPaint);
        textWidth_2 = (int) calcTextWidth("2", textPaint);
        textWidth_3 = (int) calcTextWidth("3", textPaint);
        textWidth_4 = (int) calcTextWidth("4", textPaint);
        textWidth_5 = (int) calcTextWidth("5", textPaint);
        textWidth_6 = (int) calcTextWidth("6", textPaint);
        textWidth_7 = (int) calcTextWidth("7", textPaint);
        textWidth_8 = (int) calcTextWidth("8", textPaint);
        textWidth_9 = (int) calcTextWidth("9", textPaint);
        //textWidth_0 = (int) calcTextWidth("0", textPaint);
        int textWidth_ent = (int) TestPaint.measureText("ent");
        int textWidth_c = (int) TestPaint.measureText("c");



        bgPaint.setColor(Color.parseColor("#05386B"));
        bgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        bgPaint.setTextSize(screenX/6);
        //bgPaint.setTypeface(pristina);
        bgPaint.setAntiAlias(true);
        setBackgroundColor(bgPaint.getColor());

        fillPaint.setColor(paint.getColor());
        fillPaint.setStyle(FILL);
        fillPaint.setAntiAlias(true);

        colorChange = new ColorChange(paint, textPaint, fillPaint, bgPaint, this);
    }
    private int calcXposition (int posX, int charWidth){
        return  (posX - (charWidth /2));
    }

    private int calcYposition (int posY, int height, int charHeight){
        return posY + ((height + charHeight) / 2);
    }

    private float calcTextWidth(String text, Paint textPaint) {

        Rect textBoundsTmp = new Rect();

        textPaint.getTextBounds(text, 0, text.length(), textBoundsTmp);
        float width = textBoundsTmp.left + textBoundsTmp.width();

        return width;
    }
    private float calcTextHeight(String text, Paint textPaint) {

        Rect textBoundsTmp = new Rect();

        textPaint.getTextBounds(text, 0, text.length(), textBoundsTmp);
        float height = textBoundsTmp.bottom + textBoundsTmp.height();
        return height;
    }

    private void doButton(Path path, RectF rect, int x, int y, int posY, int posX) {

        path.moveTo(posX, posY);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo((posX + x * (float) 0.1), posY + (float) 0.075 * y);
        path.lineTo(posX, posY + (float) 0.1 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.075 * y);
        path.lineTo((posX - x * (float) 0.1), posY + (float) 0.025 * y);
        path.lineTo(posX, posY);
        path.close();

        rect.left = posX - (float) 0.125 * x;
        rect.top = posY;
        rect.right = posX + (float) 0.125 * x;
        rect.bottom = posY + (float) 0.1 * y;
    }

    public void increaseColor() {
        if (color < 4) {
            colorChange.borderAnimatorAsc.get(color - 1).start();
            colorChange.bgAnimatorAsc.get(color - 1).start();
            color++;
        }
    }

    public void decreaseColor() {
        if (color > 1) {
            colorChange.borderAnimatorDesc.get(color - 1).start();
            colorChange.bgAnimatorDesc.get(color - 1).start();
            color--;
        }
    }

    public void setButton_1Clicked(boolean clicked) {
        clicked_1 = clicked;
    }
    public void setButton_4Clicked(boolean clicked) {
        clicked_4 = clicked;
    }
    public void setButton_7Clicked(boolean clicked) {
        clicked_7 = clicked;
    }
    public void setButton_2Clicked(boolean clicked) {clicked_2 = clicked;  }
    public void setButton_3Clicked(boolean clicked) {clicked_3 = clicked;  }
    public void setButton_5Clicked(boolean clicked) {clicked_5 = clicked;  }
    public void setButton_6Clicked(boolean clicked) {clicked_6 = clicked;  }
    public void setButton_8Clicked(boolean clicked) {clicked_8 = clicked;  }
    public void setButton_9Clicked(boolean clicked) {clicked_9 = clicked;  }
    public void setButton_0Clicked(boolean clicked) {clicked_0 = clicked;  }



    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!clicked_7) {
            canvas.drawPath(button_7, paint);
            canvas.drawText("7", calcXposition((int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_7, fillPaint);
            canvas.drawText("7", calcXposition( (int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6) , (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_8) {
            canvas.drawPath(button_8, paint);
            canvas.drawText("8", calcXposition((int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_8, fillPaint);
            canvas.drawText("8", calcXposition( (int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_9) {
            canvas.drawPath(button_9, paint);
            canvas.drawText("9", calcXposition((int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_9, fillPaint);
            canvas.drawText("9", calcXposition( (int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_4) {
            canvas.drawPath(button_4, paint);
            canvas.drawText("4", calcXposition((int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_4, fillPaint);
            canvas.drawText("4", calcXposition( (int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_1) {
            canvas.drawPath(button_1, paint);
            canvas.drawText("1", calcXposition((int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6)+ (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_1, fillPaint);
            canvas.drawText("1", calcXposition((int) ( screenX * 0.14), textWidth_8), calcYposition( (int) (screenY*0.6)+ (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_2) {
            canvas.drawPath(button_2, paint);
            canvas.drawText("2", calcXposition((int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_2, fillPaint);
            canvas.drawText("2", calcXposition( (int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_3) {
            canvas.drawPath(button_3, paint);
            canvas.drawText("3", calcXposition((int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_3, fillPaint);
            canvas.drawText("3", calcXposition( (int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.2), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_5) {
            canvas.drawPath(button_5, paint);
            canvas.drawText("5", calcXposition((int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_5, fillPaint);
            canvas.drawText("5", calcXposition( (int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_6) {
            canvas.drawPath(button_6, paint);
            canvas.drawText("6", calcXposition((int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_6, fillPaint);
            canvas.drawText("6", calcXposition( (int) ( screenX * 0.62), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.1), (int)(screenY * 0.1), textHeight), bgPaint);
        }
        if (!clicked_0) {
            canvas.drawPath(button_0, paint);
            canvas.drawText("0", calcXposition((int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.3), (int)(screenY * 0.1), textHeight), textPaint);

        } else {
            canvas.drawPath(button_0, fillPaint);
            canvas.drawText("0", calcXposition( (int) ( screenX * 0.38), textWidth_8), calcYposition( (int) (screenY*0.6) + (int) (screenY*0.3), (int)(screenY * 0.1), textHeight), bgPaint);
        }
    }
}

//        doButton(button_2, rect_2, x, y, y /2, (int) (screenX*0.28));