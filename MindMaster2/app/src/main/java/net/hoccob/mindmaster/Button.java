package net.hoccob.mindmaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.View;

public class Button extends View {

    private Bitmap bitmap;
    private int fileName;
    private int sizeX;
    private int sizeY;
    private int posX;
    private int posY;
    private int value;
    private RectF rectF;
    private int inverted = 0;

    public Button (Context context, int fileName, int sizeX, int sizeY, int posX, int posY, int value){
        super(context);
        this.fileName = fileName;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.value = value;

        bitmap = BitmapFactory.decodeResource(getResources(), fileName);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                sizeX,
                sizeY,
                false);

        rectF = new RectF();

        rectF.top = posY;
        rectF.bottom = posY + bitmap.getHeight();
        rectF.left = posX;
        rectF.left = posX + bitmap.getWidth();

        bitmap = ChangeColor.colorByCode(0,bitmap);
    }

    public Bitmap getBitmap(){return bitmap;}
    public RectF getRectF(){return rectF;}
    public int getValue(){return value;}

    public void invertBitmap(){ChangeColor.invertColors(bitmap); inverted = 1;}

    public void resetBitmap(){
        if(inverted == 1) {
            ChangeColor.invertColors(bitmap);
            inverted = 0;
        }
    }
}
