package net.hoccob.mindmaster;


import java.util.Random;

public class Operation {

    Random generator = new Random();
    int randomNumber;

    public char randomsign(){
        randomNumber = generator.nextInt(4);
        char sign;
        if(randomNumber == 1){
            sign = '+';
        }
        else if(randomNumber == 2){
            sign ='-';
        }
        else if (randomNumber == 3){
            sign ='x';
        }
        else{
            sign ='/';
        }
        return(sign);
    }

    public String operation(int a, int b){

        String first = String.valueOf(a);
        String second = String.valueOf(b);
        String answer;
        char c = randomsign();

        return (first + ' ' + c + ' ' + second);

    }

}
