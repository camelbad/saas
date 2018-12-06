import java.io.Serializable;

public class UnitResult implements Serializable{
	
		int a[];
		int unit_num_A;
		int unit_num_B;
		int passcode;
		String operator;
		public UnitResult(int[] a,int unit_num_Of_A, int unit_num_Of_B,int passcode_in,String operator_in ) {
			super();
			this.a = a;
			this.unit_num_A = unit_num_Of_A;
			this.unit_num_B = unit_num_Of_B;
			this.passcode=passcode_in;
			this.operator=operator_in;
}
}