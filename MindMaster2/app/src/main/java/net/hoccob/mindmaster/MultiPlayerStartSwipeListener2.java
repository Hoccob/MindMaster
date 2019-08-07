package net.hoccob.mindmaster;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import net.hoccob.mindmaster.activity.MultiPlayerActivity;


public class MultiPlayerStartSwipeListener2 extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private MultiPlayerActivity activity = null;

    public MultiPlayerActivity getActivity() {
        return activity;
    }

    public void setActivity(MultiPlayerActivity activity) {
        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                activity.swipeLeft();
                Toast.makeText(activity, "Swipe to left", Toast.LENGTH_LONG).show();
                //this.activity.displayMessage("Swipe to left");
            }else
            {
                activity.swipeRight();
                Toast.makeText(activity, "Swipe to right", Toast.LENGTH_LONG).show();
                //this.activity.displayMessage("Swipe to right");
            }
        }

        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                Toast.makeText(activity, "Swipe to up", Toast.LENGTH_LONG).show();
                //this.activity.displayMessage("Swipe to up");
            }else
            {
                Toast.makeText(activity, "Swipe to down", Toast.LENGTH_LONG).show();
                //this.activity.displayMessage("Swipe to down");
            }
        }


        return true;
    }


    // Invoked when single tap screen.
   /* @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        this.activity.displayMessage("Single tap occurred.");
        return true;
    }*/

    // Invoked when double tap screen.
   /* @Override
    public boolean onDoubleTap(MotionEvent e) {
        this.activity.displayMessage("Double tap occurred.");
        return true;
    }*/
}