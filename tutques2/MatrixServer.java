import java.io.*;
import java.net.*;
import java.util.ArrayList;

//import kit318_changlai_zhao.Request;

//////////////////////////////////
class Decomposition_request extends Thread{
	
	
	public Decomposition_request() {
			
		start();
		
	}
/////////////////////////////////////
	 public int[] getRow(int[][] matrixT,int rowNum )
	 {
	 	int[] temRow=new int[matrixT.length];
	 	for(int k=0;k<matrixT[0].length;k++)
	 	{
	 		temRow[k]=matrixT[rowNum][k];
	 
	 		
	 	}
	 
	     return temRow;	
	 }

	 public int[] getLine(int[][] matrixT, int lineNum) {
	     int[] temLine=new int[matrixT[0].length];
	     for(int k=0;k<matrixT.length;k++)
	     {
	     	temLine[k]=matrixT[k][lineNum];
	     }
	 
	 	return temLine;
	 	
	 }
	
////////////////////////////////////////////////
	public void run() {
		//Task_queue_channel task_queue= new Task_queue_channel();
		while(true){
			Request temp=MatrixServer.request_queue.takeRequest();
			//get matrix
			int [][] matrix_1=temp.getMatrix_A();
			int[][] matrix_2=temp.getMatrix_B();
			//get the size of matrix
			int size=matrix_1.length;
			//get operator
			String operator=temp.getOperation();
			//divide request base on operator
			if(operator.equals("*")) {//for "*"
		         for(int k=0;k<size;k++)
		         {
		        	
		        	 int[] a_row=new int[size];
		        	 //get  one row
		        	 a_row =getRow(matrix_1, k);
		        	 for(int j=0;j<size;j++)
		        	 {
		        		 int[] b_line=new int[size];
		        		 //get one line
		        		 b_line=getLine(matrix_2,j);
		        		 //construct a new task
		        		 SendWork task=new SendWork(a_row,b_line,k,j,temp.getPasscode(),temp.getOperation());
		        		 //add new task into the queue
		        		 MatrixServer.task_queue.putTask(task);
		        	    System.out.println("task put into queue");
		        	 }
		         }
				
			}else {//for"+"and"-"
				
				
			}
		}
	}
	
	
	
}
////////the push task thread
class PushTask extends Thread{
	ServerSocket s_worker;
	
	int threadid=0;
	public PushTask(ServerSocket s_worker_in) {
		this.s_worker=s_worker_in;
		
		start();
	}
	public void run() {
		
		try {
			
	         while(true) {
	             // Blocks until a connection occurs:
	             Socket socket = s_worker.accept();
	             try {
	            	 
	                ServeOneWorker work= new ServeOneWorker(socket,threadid);
	                 threadid++;
	                 System.out.println("Start serveoneworker "+threadid);
	                 if(threadid==3)
	                 {       work.join();
	                	     break;
	                 }
	                
	             } catch(IOException e) {
	                 // If it fails, close the socket,
	                 // otherwise the thread will close it:
	                 socket.close();
	             } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	         }
	         
	        // double average=(out[0]+out[1]+out[2])/size;
	         //System.out.println("Final average="+average);
	     }catch(IOException e) {
	    	 e.printStackTrace();
	     }
		//////////how to collect result??????????????????????
		
		
		/*
		finally {
	         try {
				//s_worker.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     } */
		
	}
	
	
}



//////////////////////////////////////
class ServeOneWorker extends Thread {
 private Socket socket;
 private ObjectInputStream  in;
 private ObjectOutputStream  out;
 public int thId;
 public ServeOneWorker(Socket s,int threadid) 
         throws IOException {
     socket = s;
     in = 
         new ObjectInputStream (
                 socket.getInputStream());
     
     // Enable auto-flush:
     out = 
         new ObjectOutputStream (         
                     socket.getOutputStream());
     // If any of the above calls throw an 
     // exception, the caller is responsible for
     // closing the socket. Otherwise the thread
     // will close it.
     thId=threadid;
     start(); // Calls run()
 }
 public void run() {
	 
     try {
        while (true) 
         {  
            
        	 System.out.println("put task to worker");
        	 //get task from tasks queue
             SendWork sw= MatrixServer.task_queue.takeTask();
        	out.writeObject(sw);
        	//how to collect result??????????????????????????
        	UnitResult aa = (UnitResult) in.readObject();
        	for(int k=0;k<aa.a.length;k++) {
        	System.out.println("Unit result is"+aa.a[k]);
        	//collecter(aa);
        	}
         }
         
     } catch (IOException | ClassNotFoundException e) {
     } finally {
         try {
             socket.close();
         } catch(IOException e) {}
     }
 }
}




public class MatrixServer {  
 static final int PORT_worker = 1234;
 static final int PORT_client=1235;
// static final int TASK_QUEUE_LONGTH=200;
 
 
 public static int [][]MatrixA;
 public static int [][]MatrixB;
 public static int [][]Matrix_result;
 
 String passcode;
 String opetation;
 

 static public request_queue_channel request_queue;
 static public Task_queue_channel task_queue;


//////////////////////////////////
static void fillRadom(int[][] x)
{
	 for (int i=0; i<x.length; i++)
	 {
		 for(int j=0; j<x[i].length; j++)
		 {
			 x[i][j] = (int) (Math.random() * 100);
		 }
	 }
}
////////////////////////////////////
 public static void main(String[] args)
         throws IOException {
	
	 int threadid=0;
     ServerSocket s_worker = new ServerSocket(PORT_worker);
     ServerSocket s_client = new ServerSocket(PORT_client);
 
     System.out.println("Server Started");
     ///init the request queue and the task queue
     request_queue=new request_queue_channel();
   
     task_queue=new Task_queue_channel();
     
     //start the decomposititon thread
    // new Decomposition_request();  
    
    
    //for worker side; start new thread to push tasks
   // new PushTask(s_worker);
     
     //for client side
     //Socket socket_client = s_client.accept();
     
     new Decomposition_request(); 
     new PushTask(s_worker);
     
     //the main thread, add request to the request queue
     try {
    	 /*
         System.out.println(
             "Connection accepted: "+ socket_client);
         BufferedReader in = 
             new BufferedReader(
                 new InputStreamReader(
                     socket_client.getInputStream()));
         // Output is automatically flushed
         // by PrintWriter:
         PrintWriter out = 
             new PrintWriter(
                 new BufferedWriter(
                     new OutputStreamWriter(
                         socket_client.getOutputStream())),true);
                         */
        for(int i=0; i<5; i++) {  
        
             String size_matrix = "5"; //in.readLine();
             String operation="*";//in.readLine();
             
            // if (size_matrix.equals("END")||operation.equals("END")) break;
             //get random int as pass code 
              double d = Math.random();
              int passcode = (int)(d*100);
             //send pass code to client
             //out.println(passcode);
             
             int size= Integer.parseInt(size_matrix);
             //initial the matrix
             MatrixA=new int[size][size];
             MatrixB=new int[size][size];
        	 //fill the matrix
             fillRadom(MatrixA);
             fillRadom(MatrixB);
        	 //inital the result matrix
             Matrix_result=new int[size][size];
        	//get start time;
             long start = System.currentTimeMillis();
             
             Request request_in=new Request(MatrixA,MatrixB,passcode,operation);
             request_queue.putRequest(request_in);
             
             
         }
     
     } finally {
         System.out.println("closing...");
         //socket_client.close();
     }
 
     
 } 
}
