package net.hoccob.mindmaster;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

public class ColorChange {

    ArrayList<Integer> borderColor = new ArrayList<>();
    ArrayList<Integer> bgColor = new ArrayList<>();
    public ArrayList<ValueAnimator> borderAnimatorAsc = new ArrayList<>();
    public ArrayList<ValueAnimator> bgAnimatorAsc = new ArrayList<>();
    public ArrayList<ValueAnimator> borderAnimatorDesc = new ArrayList<>();
    public ArrayList<ValueAnimator> bgAnimatorDesc = new ArrayList<>();


    public ColorChange(final Paint borderPaint, final Paint textPaint, final View view){

        populateColors();

        for(int i = 0; i < borderColor.size(); i++){
            if(i < borderColor.size() - 1) {
                borderAnimatorAsc.add(ValueAnimator.ofObject(new ArgbEvaluator(), borderColor.get(i), borderColor.get(i + 1)));
                bgAnimatorAsc.add(ValueAnimator.ofObject(new ArgbEvaluator(), bgColor.get(i), bgColor.get(i + 1)));
            }else{
                borderAnimatorAsc.add(ValueAnimator.ofObject(new ArgbEvaluator(), borderColor.get(i), borderColor.get(0)));
                bgAnimatorAsc.add(ValueAnimator.ofObject(new ArgbEvaluator(), bgColor.get(i), bgColor.get(0)));
            }

            if(i == 0){
                borderAnimatorDesc.add(ValueAnimator.ofObject(new ArgbEvaluator(), borderColor.get(i), borderColor.get(borderColor.size() - 1)));
                bgAnimatorDesc.add(ValueAnimator.ofObject(new ArgbEvaluator(), bgColor.get(i), bgColor.get(bgColor.size() - 1)));
            }else{
                borderAnimatorDesc.add(ValueAnimator.ofObject(new ArgbEvaluator(), borderColor.get(i), borderColor.get(i - 1)));
                bgAnimatorDesc.add(ValueAnimator.ofObject(new ArgbEvaluator(), bgColor.get(i), bgColor.get(i - 1)));
            }

            borderAnimatorAsc.get(i).setDuration(500);
            bgAnimatorAsc.get(i).setDuration(500);
            borderAnimatorDesc.get(i).setDuration(500);
            bgAnimatorDesc.get(i).setDuration(500);

            borderAnimatorAsc.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    borderPaint.setColor((int) animator.getAnimatedValue());
                    textPaint.setColor((int) animator.getAnimatedValue());
                    view.invalidate();
                }
            });

            bgAnimatorAsc.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view.setBackgroundColor((int) animator.getAnimatedValue());
                    //bgPaint.setColor((int) animator.getAnimatedValue());
                    view.invalidate();
                }
            });

            borderAnimatorDesc.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    borderPaint.setColor((int) animator.getAnimatedValue());
                    textPaint.setColor((int) animator.getAnimatedValue());
                    view.invalidate();
                }
            });

            bgAnimatorDesc.get(i).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    view.setBackgroundColor((int) animator.getAnimatedValue());
                    //bgPaint.setColor((int) animator.getAnimatedValue());
                    view.invalidate();
                }
            });
        }

    }

    private void populateColors(){
        borderColor.add(Color.parseColor("#8EE4AF"));
        borderColor.add(Color.parseColor("#3500D3"));
        borderColor.add(Color.parseColor("#950740"));
        borderColor.add(Color.parseColor("#FF652F"));

        bgColor.add(Color.parseColor("#05386B"));
        bgColor.add(Color.parseColor("#0C0032"));
        bgColor.add(Color.parseColor("#1A1A1D"));
        bgColor.add(Color.parseColor("#272727"));
    }

}
