package net.hoccob.mindmaster.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import net.hoccob.mindmaster.R;

/**
 * Created by karlj on 06/10/2018.
 */

@SuppressLint("ValidFragment")
public class FirstFragment extends Fragment {

    String SingleplayerScore;

    public FirstFragment(String SinglePlayerScore){
        this.SingleplayerScore = SinglePlayerScore;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.first_fragment, container, false);
        TextView b1 = (TextView) rootView.findViewById(R.id.textView3);
        //TextView c1 =  (TextView)b1.getChildAt(0);
        b1.setText(SingleplayerScore);
        return rootView;
        }

}

