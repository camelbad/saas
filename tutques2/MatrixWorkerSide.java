
	// SimpleWoker.java: a simple worker program
import java.net.*;
import java.io.*;
public class MatrixWorkerSide {


	public static synchronized UnitResult calculate(SendWork sw){
  	  
  	 
  	  int unitresult[] = new int[sw.a.length];
  	  if(sw.operator=="+") {
  		  for(int k=0;k<sw.a.length;k++)
  		  {
  			  unitresult[k]=sw.a[k]+sw.b[k];
  		  }
  		 
  	  	}
  	  else if(sw.operator == "-") {
  	  	  for(int k=0;k<sw.a.length;k++)
  	  	  {
 			  unitresult[k]=sw.a[k]-sw.b[k];
  	  	  }
  
  	  	}
  	  else if(sw.operator == '*') {
  		 /* for(int k=0;k<sw.a.length;k++)
	  	  {
			  unitresult[0]+=sw.a[k]*sw.b[k];
			  System.out.println(unitresult[0]);
	  	  }*/
  		  unitresult[0]=5;
  	  }
  	  		
  	  return new UnitResult(unitresult,sw.unit_num_A,sw.unit_num_B,sw.passcode,sw.operator);
	}
	
	
	  public static void main(String args[]) throws IOException, ClassNotFoundException {
	    // Open your connection to a server, at port 1254
	    Socket s1 = new Socket("localhost",1234);
	    System.out.println("connected to server"+s1.getLocalPort());
	    // Get an input file handle from the socket and read the input
	    //InputStream s1In = s1.getInputStream();
	    
	        
	        // Enable auto-flush:
	    ObjectOutputStream out = 
	            new ObjectOutputStream (         
	                        s1.getOutputStream());
	    ObjectInputStream in = 
	            new ObjectInputStream (
	                    s1.getInputStream());
	    while(true) {//in.readObject()!=null) {
		    SendWork sw=(SendWork)in.readObject();
		    System.out.println("input received");
		    UnitResult ur = MatrixWorkerSide.calculate(sw);
		    out.writeObject(ur);
		    for(int i=0;i<sw.a.length;i++) {
		    	System.out.println(sw.a[i]+sw.operator+sw.b[i]);
		    }
		    System.out.println(ur.a[0]); 
		   //String st = new String (dis.readUTF());
		    System.out.println("unit result sent");
		    // When done, just close the connection and exit
	    }
	   // in.close();
	   // out.close();
	    //s1.close();
	  }
}

