package net.hoccob.mindmaster;

public class Equation {

    private int level;
    private int operand1;
    private int operand2;
    private int answer;

    public Equation(int level, int operand1, int operand2){
        this.level = level;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public void calcAnswer(){
        if(level == 0 || level == 2 || level == 4 || level == 6  || level == 10){
            answer = operand1 + operand2;
        }else if(level == 1 || level == 3 || level == 5 || level == 7  || level == 11){
            answer = operand1 - operand2;
        }else if(level == 8){
            answer = operand1 * operand2;
        }else if(level == 9){
            answer = operand1 / operand2;
        }
    }

}
