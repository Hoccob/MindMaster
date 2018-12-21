package net.hoccob.mindmaster.ASyncTasks;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;


public class ColorByCode extends AsyncTask<Void, Void, Bitmap> {
    Bitmap bit;
    int colorCode;

    public ColorByCode(Bitmap bit, int colorCode){
        this.bit = bit;
        this.colorCode = colorCode;
    }


    @Override
    protected Bitmap doInBackground(Void... params){

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
                        if (allpixels[i] == framePixel){
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
                        if (allpixels[i] == framePixel){
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
                        if (allpixels[i] == framePixel){
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
                        if (allpixels[i] == framePixel){
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

    @Override
    protected void onPostExecute(Bitmap result){
        super.onPostExecute(result);
    }
}
