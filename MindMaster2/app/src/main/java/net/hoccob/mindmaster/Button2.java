package net.hoccob.mindmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.View;

public class Button2 extends View {

    private Bitmap bitmap;
    private int fileName;
    private int sizeX;
    private int sizeY;
    private int posX;
    private int posY;
    private int value;
    private RectF rectF;
    private boolean inverted;

    public Button2 (Context context, Bitmap bitMap, int sizeX, int sizeY, int posX, int posY, int value, int colorCode){
        super(context);
        this.bitmap = bitMap;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.value = value;

        inverted = false;

        rectF = new RectF();

        rectF.top = posY;
        rectF.bottom = posY + sizeY;
        rectF.left = posX;
        rectF.right = posX + sizeX;

        bitmap = ChangeColor.makeTransparent(bitmap);
        bitmap = ChangeColor.colorByCode(colorCode,bitmap);
    }

    public Bitmap getBitmap(){return bitmap;}
    public RectF getRectF(){ return rectF;}
    public int getValue(){return value;}

    public int getPosX(){return posX;}
    public int getPosY(){return posY;}

    public boolean getInverted(){return inverted;}

    public void changeColor(int code){bitmap = ChangeColor.colorByCode(code, bitmap);}

    public Button2 getButton(){return this;}

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void invertBitmap(){
        if(!inverted){
            bitmap = ChangeColor.invertColors(bitmap);
            inverted = true;
        }
    }

    public void resetBitmap(){
        if(inverted) {
            bitmap = ChangeColor.invertColors(bitmap);
            inverted = false;
        }
    }
}
