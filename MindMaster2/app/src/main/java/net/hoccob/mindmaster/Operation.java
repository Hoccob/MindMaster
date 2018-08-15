package net.hoccob.mindmaster;


import java.util.Random;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class Operation {

    double answer;
    double equation;

    Random generator = new Random();
    int randomNumber;

    public char randomsign(){
        randomNumber = generator.nextInt(4);
        char sign;
        randomNumber = 1;
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

    public String getOperation(){

        String first = String.valueOf(generator.nextInt(10) + 1);
        String second = String.valueOf(generator.nextInt(10) + 1);
        char c = randomsign();
        equation = new ExpressionBuilder(first + c + second)
                .build()
                .evaluate();

        answer = equation;

        return (first + ' ' + c + ' ' + second);

    }

    public long getAnswer(){

        return (long) answer;
    }

}
