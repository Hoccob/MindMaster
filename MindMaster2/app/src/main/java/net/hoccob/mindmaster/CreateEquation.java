package net.hoccob.mindmaster;

public class CreateEquation {

	Equation equation;
	int id;

	public CreateEquation(){
		id = 1;
	}

	public Equation getEquation(int gameStyle, int level){

		int operand1 = 0;
		int operand2 = 0;

		switch (level){
			case 0:
				operand1 = (int) Math.floor(Math.random() * 10);
				operand2 = (int) Math.floor(Math.random() * 10);
				break;
			case 1:
				operand1 = (int) Math.floor(Math.random() * 10);
				operand2 = (int) Math.floor(Math.random() * 10);
				break;
			case 2:
				operand1 = (int) Math.floor(Math.random() * 100);
				operand2 = (int) Math.floor(Math.random() * 10);
				break;
			case 3:
				operand1 = (int) Math.floor(Math.random() * 100);
				operand2 = (int) Math.floor(Math.random() * 10);
				break;
			case 4:
				operand1 = (int) Math.floor(Math.random() * 100);
				operand2 = (int) Math.floor(Math.random() * 100);
				break;
			case 5:
				operand1 = (int) Math.floor(Math.random() * 100);
				operand2 = (int) Math.floor(Math.random() * 100);
				break;
			case 6:
				operand1 = (int) Math.floor(Math.random() * 1000);
				operand2 = (int) Math.floor(Math.random() * 100);
				break;
			case 7:
				operand1 = (int) Math.floor(Math.random() * 1000);
				operand2 = (int) Math.floor(Math.random() * 100);
				break;
			case 8:
				operand1 = (int) Math.floor(Math.random() * 10);
				operand2 = (int) Math.floor(Math.random() * 10);
				break;
			case 9:
				operand2 = (int) Math.floor(Math.random() * 10);
				operand1 = (int) Math.floor(Math.random() * 10) * operand2;
				break;
			case 10:
				operand1 = (int) Math.floor(Math.random() * 1000);
				operand2 = (int) Math.floor(Math.random() * 1000);
				break;
			case 11:
				operand1 = (int) Math.floor(Math.random() * 1000);
				operand2 = (int) Math.floor(Math.random() * 1000);
				break;
		}


		equation = new Equation(id, level, operand1, operand2);
		equation.calcAnswer();

		id++;
		return equation;
	}


}
