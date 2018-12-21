package net.hoccob.mindmaster.ASyncTasks;

import android.os.AsyncTask;

import net.hoccob.mindmaster.ChangeColor;
import net.hoccob.mindmaster.view.MainView;


public class ChangeView extends AsyncTask<Void, Void, Void> {

    MainView view;
    int colorCode;

    public interface AsyncResponse {
        void processFinish();
    }

    public AsyncResponse delegate;

    public ChangeView(MainView view, int colorCode, AsyncResponse delegate){
        this.view = view;
        this.colorCode = colorCode;
        this.delegate = delegate;
        System.out.println("TEEN CHANGEVIEW");
    }


    @Override
    protected Void doInBackground(Void... params){
        view.setColors(colorCode);
        //view.setBackgroundColor(ChangeColor.setBGColor(colorCode));
        //view.invalidate();
        System.out.println("Finished");
        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        delegate.processFinish();
        super.onPostExecute(aVoid);
    }
/*@Override
    protected void onPostExecute(Void pseudovoid){
        delegate.processFinish();
    }*/
}
