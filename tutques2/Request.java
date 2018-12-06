

import java.util.Random;

public class Request {
	private int[][] Matrix_A;
	private int[][] Matrix_B;
	private int passcode;
	private String operation;
	
	
	public Request(int[][] Matrix1,int[][] Matrix2,int passcode_in,String operation_in){
		Matrix_A=Matrix1;
		Matrix_B=Matrix2;
		passcode=passcode_in;
		operation=operation_in;
	}

//getter and setter
	public int[][] getMatrix_A() {
		return Matrix_A;
	}


	public void setMatrix_A(int[][] matrix_A) {
		Matrix_A = matrix_A;
	}


	public int[][] getMatrix_B() {
		return Matrix_B;
	}


	public void setMatrix_B(int[][] matrix_B) {
		Matrix_B = matrix_B;
	}


	public int getPasscode() {
		return passcode;
	}


	public void setPasscode(int passcode) {
		this.passcode = passcode;
	}


	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}
	
    
    
}
