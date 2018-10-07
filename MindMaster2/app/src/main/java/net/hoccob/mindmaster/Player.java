package net.hoccob.mindmaster;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private Integer id;
    private String userName;
    private int points;
    private String nickname;

    public Player(){
        this.id = 0;

    }

    public Integer getId(){return id;}
    public String getUserName(){return userName;}
    public int getPoints(){return points;}
    public String getNickname(){return nickname;}

    public void setId(int id){this.id = id;}
    public void setUserName(String userName){this.userName = userName;}
    public void setPoints(int points){this.points = points;}
    public void setNickname(String nickname){this.nickname = nickname;}

    public Player(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.userName = data[1];
        this.points = Integer.parseInt(data[2]);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{String.valueOf(this.id), this.userName, String.valueOf(this.points)});
    }

    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>(){

        @Override
        public Player createFromParcel(Parcel source){
            return new Player(source);
        }

        @Override
        public Player[] newArray(int size){
            return new Player[size];
        }
    };

}
