package net.hoccob.mindmaster;


import java.util.Random;

import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class Operation {

    double answer;
    double equation;

    private String[][] equationPools;
    private int[][] answerPools;
    private int[] lastEquation;

    Random generator = new Random();
    int randomNumber;

    public Operation(){
        equationPools = new String[20][10];
        answerPools = new int[20][10];
        lastEquation = new int[20];
        for(int i = 0; i < 20; i++){
            lastEquation[i] = 0;
        }
    }

    public String getOperation(int pool){
        if(lastEquation[pool] == 10){
            lastEquation[pool] = 1;
        }else{
            lastEquation[pool]++;
        }
        return equationPools[pool][lastEquation[pool]-1];
    }

    public int getAnswer(int pool){
        return answerPools[pool][lastEquation[pool]-1];
    }

    public void addToPool(int pool){
        int first;
        int second;
        switch (pool) {
            case 0:
                // 1 + 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[0][lastEquation[pool] - 1] = first + second;
                equationPools[0][lastEquation[pool] - 1] = first + " + " + second;
                break;
            case 1:
                //1 - 1
                do {
                    first = generator.nextInt(9) + 1;
                    second = generator.nextInt(9) + 1;
                }while(first != second);
                if (first < second) {
                    answerPools[1][lastEquation[pool] - 1] = second - first;
                    equationPools[1][lastEquation[pool] - 1] = second + " - " + first;
                } else {
                    answerPools[1][lastEquation[pool] - 1] = first - second;
                    equationPools[1][lastEquation[pool] - 1] = first + " - " + second;
                }
                break;
            case 2:
                // 12 + 1
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(9) + 1;
                answerPools[2][lastEquation[pool] - 1] = first + second;
                equationPools[2][lastEquation[pool] - 1] = first + " + " + second;
                break;
            case 3:
                //12 - 1
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(9) + 1;
                answerPools[3][lastEquation[pool] - 1] = first - second;
                equationPools[3][lastEquation[pool] - 1] = first + " - " + second;
                break;
            case 4:
                //13 + 12
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(90) + 10;
                answerPools[4][lastEquation[pool] - 1] = first + second;
                equationPools[4][lastEquation[pool] - 1] = first + " + " + second;
                break;
            case 5:
                //13 - 12
                do {
                    first = generator.nextInt(90) + 10;
                    second = generator.nextInt(90) + 10;
                }while(first != second);
                if(first < second){
                    answerPools[5][lastEquation[pool] - 1] = second - first;
                    equationPools[5][lastEquation[pool] - 1] = second + " - " + first;
                }else{
                    answerPools[5][lastEquation[pool] - 1] = first - second;
                    equationPools[5][lastEquation[pool] - 1] = first + " - " + second;
                }
                break;
            case 6:
                //123 + 12
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(90) + 10;
                answerPools[6][lastEquation[pool] - 1] = first + second;
                equationPools[6][lastEquation[pool] - 1] = first + " + " + second;
                break;
            case 7:
                //123 - 12
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(90) + 10;
                answerPools[7][lastEquation[pool] - 1] = first - second;
                equationPools[7][lastEquation[pool] - 1] = first + " - " + second;
                break;
            case 8:
                //1 * 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[8][lastEquation[pool] - 1] = first * second;
                equationPools[8][lastEquation[pool] - 1] = first + " x " + second;
                break;
            case 9:
                //12 / 1 == 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[9][lastEquation[pool] - 1] = first;
                equationPools[9][lastEquation[pool] - 1] = (first*second) + " / " + second;
                break;
            case 10:
                //133 + 122
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(900) + 100;
                answerPools[10][lastEquation[pool] - 1] = first + second;
                equationPools[10][lastEquation[pool] - 1] = first + " + " + second;
                break;
            case 11:
                //133 - 122
                do {
                    first = generator.nextInt(900) + 100;
                    second = generator.nextInt(900) + 100;
                }while(first != second);
                if(first < second){
                answerPools[11][lastEquation[pool] - 1] = second - first;
                equationPools[11][lastEquation[pool] - 1] = second + " - " + first;
            }else{
                answerPools[11][lastEquation[pool] - 1] = first - second;
                equationPools[11][lastEquation[pool] - 1] = first + " - " + second;
            }
                break;
        }
    }

    public void createPools(){
        int first;
        int second;
            for(int i = 0; i < 10; i++){
                // 1 + 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[0][i] = first + second;
                equationPools[0][i] = first + " + " + second;

                //1 - 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                if(first < second){
                    answerPools[1][i] = second - first;
                    equationPools[1][i] = second + " - " + first;
                }else{
                    answerPools[1][i] = first - second;
                    equationPools[1][i] = first + " - " + second;
                }

                // 12 + 1
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(9) + 1;
                answerPools[2][i] = first + second;
                equationPools[2][i] = first + " + " + second;

                //12 - 1
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(9) + 1;
                answerPools[3][i] = first - second;
                equationPools[3][i] = first + " - " + second;

                //13 + 12
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(90) + 10;
                answerPools[4][i] = first + second;
                equationPools[4][i] = first + " + " + second;

                //13 - 12
                first = generator.nextInt(90) + 10;
                second = generator.nextInt(90) + 10;
                if(first < second){
                    answerPools[5][i] = second - first;
                    equationPools[5][i] = second + " - " + first;
                }else{
                    answerPools[5][i] = first - second;
                    equationPools[5][i] = first + " - " + second;
                }

                //123 + 12
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(90) + 10;
                answerPools[6][i] = first + second;
                equationPools[6][i] = first + " + " + second;

                //123 - 12
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(90) + 10;
                answerPools[7][i] = first - second;
                equationPools[7][i] = first + " - " + second;

                //1 * 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[8][i] = first * second;
                equationPools[8][i] = first + " x " + second;

                //12 / 1 == 1
                first = generator.nextInt(9) + 1;
                second = generator.nextInt(9) + 1;
                answerPools[9][i] = first;
                equationPools[9][i] = (first*second) + " / " + second;

                //133 + 122
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(900) + 100;
                answerPools[10][i] = first + second;
                equationPools[10][i] = first + " + " + second;

                //133 - 122
                first = generator.nextInt(900) + 100;
                second = generator.nextInt(900) + 100;
                if(first < second){
                    answerPools[11][i] = second - first;
                    equationPools[11][i] = second + " - " + first;
                }else{
                    answerPools[11][i] = first - second;
                    equationPools[11][i] = first + " - " + second;
                }

                //23 / 12 = 1
                //23 / 1 = 12
                //1234 + 123
                //1234 - 123
                //12 * 1
                //1234 + 1234 = 1234
                //1234 - 1234 = 12
                //1234 + 1234 = 12345
                //1234 - 1234 = 123
                //1234 - 1234 = 1234
                //12 * 12
                //12 / 12 = 12
            }
    }

}
