package net.hoccob.mindmaster;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ChangeColor {

    public static Bitmap makeTransparent(Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //int topleftPixel = bit.getPixel(0,0);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
            if (Color.red(allpixels[i]) == 0 && Color.green(allpixels[i]) == 255 && Color.blue(allpixels[i]) == 0) {
                allpixels[i] = Color.alpha(Color.TRANSPARENT);
            }
            //System.out.println(Color.green(allpixels[i]));
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }

    public static Bitmap invertColors(Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int framePixel = bit.getPixel(0, height / 2);
        int bgPixel = bit.getPixel(width / 20, height/2);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
            if(allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                if (Color.rgb(Color.red(allpixels[i]), Color.green(allpixels[i]), Color.blue(allpixels[i])) == Color.rgb(Color.red(framePixel), Color.green(framePixel), Color.blue(framePixel))) {
                   allpixels[i] = bgPixel;
                    //allpixels[i] = Color.alpha(Color.WHITE);
                } else {
                   allpixels[i] = framePixel;
                  // allpixels[i] = Color.alpha(Color.BLACK);
                }
            }
        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        return myBitmap;
    }


    public static Bitmap colorByCode(int colorCode, Bitmap bit) {
        int width = bit.getWidth();
        int height = bit.getHeight();
        Bitmap myBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int framePixel = bit.getPixel(0, height / 2);
        //int bgPixel = bit.getPixel(width / 20, height/2);
        int[] allpixels = new int[myBitmap.getHeight() * myBitmap.getWidth()];
        bit.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        myBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);

        switch (colorCode) {
            case 1:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                        if (Color.rgb(Color.red(allpixels[i]), Color.green(allpixels[i]), Color.blue(allpixels[i])) == Color.rgb(Color.red(framePixel), Color.green(framePixel), Color.blue(framePixel))) {
                            allpixels[i] = Color.parseColor("#05386B");
                        } else {
                            allpixels[i] = Color.parseColor("#8EE4AF");
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                        if (Color.rgb(Color.red(allpixels[i]), Color.green(allpixels[i]), Color.blue(allpixels[i])) == Color.rgb(Color.red(framePixel), Color.green(framePixel), Color.blue(framePixel))) {
                            allpixels[i] = Color.parseColor("#3500D3");
                        } else {
                            allpixels[i] = Color.parseColor("#0C0032");
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                        if (Color.rgb(Color.red(allpixels[i]), Color.green(allpixels[i]), Color.blue(allpixels[i])) == Color.rgb(Color.red(framePixel), Color.green(framePixel), Color.blue(framePixel))) {
                            allpixels[i] = Color.parseColor("#950740");
                        } else {
                            allpixels[i] = Color.parseColor("#1A1A1D");
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                        if (Color.rgb(Color.red(allpixels[i]), Color.green(allpixels[i]), Color.blue(allpixels[i])) == Color.rgb(Color.red(framePixel), Color.green(framePixel), Color.blue(framePixel))) {
                            allpixels[i] = Color.parseColor("#FF652F");
                        } else {
                            allpixels[i] = Color.parseColor("#272727");
                        }
                    }
                }
                break;
            default:
                for (int i = 0; i < myBitmap.getHeight() * myBitmap.getWidth(); i++) {
                    if (allpixels[i] == Color.parseColor("#FFFFFF")) {
                        allpixels[i] = Color.parseColor("#272727");
                    } else if (allpixels[i] != Color.parseColor("#FFFFFF") && allpixels[i] != Color.alpha(Color.TRANSPARENT)) {
                        allpixels[i] = Color.parseColor("#FF652F");
                    }
                    break;

                }
        }
                myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
                return myBitmap;
        }

        public static int setBGColor(int colorCode) {

            switch (colorCode) {
                case 1:
                    return Color.parseColor("#8EE4AF");
                case 2:
                    return Color.parseColor("#0C0032");
                case 3:
                    return Color.parseColor("#1A1A1D");
                case 4:
                    return Color.parseColor("#272727");
                default:
                    return Color.parseColor("#272727");

            }
        }

}
