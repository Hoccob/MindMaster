package net.hoccob.mindmaster;

public class CreateEquation {

	Equation equation;
	int id;
	int gameMode;

	public CreateEquation(int gameMode){
		this.gameMode=gameMode;
		id = 1;
	}

	public Equation getEquation(int level){

		int operand1 = 0;
		int operand2 = 0;
		int temp;
		int equationLevel;

		if(gameMode == 1 || gameMode == 3){
			switch (level){
				case 0:
					operand1 = (int) Math.floor(Math.random() * 10);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 1:
					operand1 = (int) Math.floor(Math.random() * 100);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 2:
					operand1 = (int) Math.floor(Math.random() * 100);
					operand2 = (int) Math.floor(Math.random() * 100);
					break;
				case 3:
					operand1 = (int) Math.floor(Math.random() * 1000);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 4:
					operand1 = (int) Math.floor(Math.random() * 1000);
					operand2 = (int) Math.floor(Math.random() * 100);
					break;
			}
		}
		if(gameMode == 2){
			switch (level){
				case 0:
					temp = (int) Math.floor(Math.random() * 10);
					operand1 = (int) Math.floor(Math.random() * 10);
					if(temp > operand1){
						operand2 = operand1;
						operand1 = temp;
					}
					break;
				case 1:
					operand1 = (int) Math.floor(Math.random() * 100);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 2:
					temp = (int) Math.floor(Math.random() * 100);
					operand1 = (int) Math.floor(Math.random() * 100);
					if(temp > operand1){
						operand2 = operand1;
						operand1 = temp;
					}
					break;
				case 3:
					operand1 = (int) Math.floor(Math.random() * 1000);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 4:
					operand1 = (int) Math.floor(Math.random() * 1000);
					operand2 = (int) Math.floor(Math.random() * 100);
					break;
			}
		}
		if(gameMode == 4){
			switch (level){
				case 0:
					operand2 = (int) Math.floor(Math.random() * 10);
					operand1 = (int) Math.floor(Math.random() * 10) * operand2;
					break;
				case 1:
					operand2 = (int) Math.floor(Math.random() * 100);
					operand1 = (int) Math.floor(Math.random() * 10) * operand2;
					break;
				case 2:
					operand2 = (int) Math.floor(Math.random() * 100);
					operand1 = (int) Math.floor(Math.random() * 100) * operand2;
					break;
				case 3:
					operand2 = (int) Math.floor(Math.random() * 1000);
					operand1 = (int) Math.floor(Math.random() * 100) * operand2;
					break;
				case 4:
					operand2 = (int) Math.floor(Math.random() * 1000);
					operand1 = (int) Math.floor(Math.random() * 1000) * operand2;
					break;
			}
		}

		if(gameMode == 5) {
			switch (level) {
				case 0:
					operand1 = (int) Math.floor(Math.random() * 10);
					operand2 = (int) Math.floor(Math.random() * 10);
					break;
				case 1:
					temp = (int) Math.floor(Math.random() * 10);
					operand1 = (int) Math.floor(Math.random() * 10);
					if(temp > operand1){
						operand2 = operand1;
						operand1 = temp;
					}
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
					temp = (int) Math.floor(Math.random() * 100);
					operand1 = (int) Math.floor(Math.random() * 100);
					if(temp > operand1){
						operand2 = operand1;
						operand1 = temp;
					}
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
					temp = (int) Math.floor(Math.random() * 1000);
					operand1 = (int) Math.floor(Math.random() * 1000);
					if(temp > operand1){
						operand2 = operand1;
						operand1 = temp;
					}
					break;
			}
		}

		if(gameMode == 1){
			equationLevel = 0;
		}else if(gameMode == 2){
			equationLevel = 1;
		}else if(gameMode == 3){
			equationLevel = 8;
		}else if(gameMode == 4){
			equationLevel = 9;
		}else{
			equationLevel = level;
		}

		equation = new Equation(id, equationLevel, operand1, operand2);
		equation.calcAnswer();

		id++;
		return equation;
	}


}
