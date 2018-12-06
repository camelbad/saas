
public class Task_queue_channel {
	 private static final int MAX_REQUEST = 1000;
	    private final SendWork[] task_queue;
	    private int tail;  // ä¸‹æ¬¡putRequestçš„ä½�ç½®
	    private int head;  // ä¸‹æ¬¡takeRequestçš„ä½�ç½®
	    private int count; // Requestçš„æ•°é‡�

	   

	    public Task_queue_channel() {
	        this.task_queue = new SendWork[MAX_REQUEST];
	        this.head = 0;
	        this.tail = 0;
	        this.count = 0;
	    }
	   
	    public int getCount() {
			return count;
		}
	    
	    
	    public synchronized void putTask(SendWork task) {
	        while (count >= task_queue.length) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	            }
	        }
	        task_queue[tail] = task;
	        tail = (tail + 1) % task_queue.length;
	        count++;
	        notifyAll();
	    }
	    
	    
	    public synchronized SendWork takeTask() {
	        while (count <= 0) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	            }
	        }
	        SendWork task = task_queue[head];
	        head = (head + 1) % task_queue.length;
	        count--;
	        notifyAll();
	        return task;
	    }
}
