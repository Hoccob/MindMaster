package net.hoccob.mindmaster;

import android.os.Parcel;
import android.os.Parcelable;

public class Equation implements Parcelable {

    private int id;
    private int level;
    private int operand1;
    private int operand2;
    private int answer;
    private String strOperation;

    public Equation(int id, int level, int operand1, int operand2){
        this.id = id;
        this.level = level;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public int getId(){return id;}
    public int getLevel(){return level;}
    public int getOperand1(){return operand1;}
    public int getOperand2(){return operand2;}
    public String getStrOperation(){return strOperation;}
    public int getAnswer() {return answer;}

    public void calcAnswer(){
        if(level == 0 || level == 2 || level == 4 || level == 6  || level == 10){
            answer = operand1 + operand2;
            strOperation = operand1 + " + " + operand2;
        }else if(level == 1 || level == 3 || level == 5 || level == 7  || level == 11){
            answer = operand1 - operand2;
            strOperation = operand1 + " - " + operand2;
        }else if(level == 8){
            answer = operand1 * operand2;
            strOperation = operand1 + " x " + operand2;
        }else if(level == 9){
            answer = operand1 / operand2;
            strOperation = operand1 + " / " + operand2;
        }
    }

    public Equation(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.level = Integer.parseInt(data[1]);
        this.operand1 = Integer.parseInt(data[2]);
        this.operand2 = Integer.parseInt(data[3]);
        this.answer = Integer.parseInt(data[4]);
        this.strOperation = data[5];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{String.valueOf(this.id), String.valueOf(this.level), String.valueOf(this.operand1), String.valueOf(this.operand2), String.valueOf(this.answer),this.strOperation});
    }

    public static final Parcelable.Creator<Equation> CREATOR = new Parcelable.Creator<Equation>(){

        @Override
        public Equation createFromParcel(Parcel source){
            return new Equation(source);
        }

        @Override
        public Equation[] newArray(int size){
            return new Equation[size];
        }
    };


}
