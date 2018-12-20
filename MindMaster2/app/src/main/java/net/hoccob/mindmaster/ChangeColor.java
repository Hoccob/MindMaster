package net.hoccob.mindmaster;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ChangeColor {

    public static Bitmap makeTransparent(Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
            if (allpixels[i] == Color.rgb(0,255,0)) {

                allpixels[i] = Color.alpha(Color.TRANSPARENT);
            }
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

    public static Bitmap invertColors(Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int framePixel = myBitmap.getPixel(0, height / 2);
        int bgPixel = myBitmap.getPixel(5, height/2);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
            if (allpixels[i] == framePixel) {

                allpixels[i] = bgPixel;
            } else {
                allpixels[i] = framePixel;
            }
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }


    public static Bitmap colorByCode(int colorCode, Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        switch(colorCode) {
            case 1:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] == Color.rgb(255, 255, 255)) {
                        allpixels[i] = Color.rgb(12, 0, 50);
                    } else if (allpixels[i] != Color.rgb(255, 255, 255)) {
                        allpixels[i] = Color.rgb(53, 80, 211);
                    }
                }
            default:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] == Color.rgb(255, 255, 255)) {
                        allpixels[i] = Color.rgb(12, 0, 50);
                    } else if (allpixels[i] != Color.rgb(255, 255, 255)) {
                        allpixels[i] = Color.rgb(53, 80, 211);
                    }
                }

        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }
}
