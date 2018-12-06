



import java.net.*;
import java.util.Scanner;
import java.io.*;

public class clientSideMatrix {
	 public static void main(String[] args) 
	         throws IOException {
	     // Passing null to getByName() produces the
	     // special "Local Loopback" IP address, for
	     // testing on one machine w/o a network:
	     InetAddress addr = InetAddress.getByName("localhost");////////
	         //InetAddress.getByName(null);
	     // Alternatively, you can use 
	     // the address or name:
	     // InetAddress addr = InetAddress.getByName("127.0.0.1");
	     // InetAddress addr = InetAddress.getByName("localhost");
	     System.out.println("addr = " + addr);
	     Socket socket = 
	         new Socket("localhost", 4500);
	     // Guard everything in a try-finally to make
	     // sure that the socket is closed:
	     try {
	         System.out.println("socket = " + socket);
	         BufferedReader in =
	             new BufferedReader(
	                 new InputStreamReader(
	                     socket.getInputStream()));
	         // Output is automatically flushed
	         // by PrintWriter:
	         PrintWriter out =
	             new PrintWriter(
	                 new BufferedWriter(
	                     new OutputStreamWriter(
	                         socket.getOutputStream())),true);
	         //for(int i = 0; i < 10; i ++) {
	        	 Scanner scanner = new Scanner(System. in); 
	        	 System.out.println("Input the size of matrix:");
	        	 String input = scanner. nextLine();
	        	 
	             out.println(input);
	             
	             System.out.println("Input the operation");
	        	input = scanner. nextLine();
	        	 
	             out.println(input);
	             
	             String passCode = in.readLine();
	             System.out.println(passCode);
	             //String str = in.readLine();
	             //System.out.println(str);
	        // }
	        // out.println("END");
	     } finally {
	         System.out.println("closing...");
	         socket.close();
	     }
	 }
	} 
