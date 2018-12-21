package net.hoccob.mindmaster.ASyncTasks;

import android.os.AsyncTask;

import net.hoccob.mindmaster.view.MainView;


public class ChangeView extends AsyncTask<Void, Void, Void> {

    MainView view;
    int colorCode;

    public interface AsyncResponse {
        void processFinish();
    }

    public ChangeView.AsyncResponse delegate;

    public ChangeView(MainView view, int colorCode, AsyncResponse asyncResponse){
        this.view = view;
        this.colorCode = colorCode;
        System.out.println("TEEN CHANGEVIEW");
    }


    @Override
    protected Void doInBackground(Void... params){
        view.setColors(colorCode);
        //view.invalidate();
        return null;

    }

   /* @Override
    protected void onPostExecute(){
        view.invalidate();
    }*/
}
