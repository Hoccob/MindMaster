package net.hoccob.mindmaster;

import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Parcelable {

    private Integer id;
    private String opponentNickname;

    public Game(){

    }

    public Integer getId(){return id;}
    public String getOpponentNickname(){return opponentNickname;}

    public void setId(int id){this.id = id;}
    public void setOpponentNickname(String opponentNickname){this.opponentNickname = opponentNickname;}

    public Game(Parcel in){
        String[] data = new String[2];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.opponentNickname = data[1];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{String.valueOf(this.id), this.opponentNickname});
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>(){

        @Override
        public Game createFromParcel(Parcel source){
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size){
            return new Game[size];
        }
    };

}
