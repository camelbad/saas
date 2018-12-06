

public class request_queue_channel {
    private static final int MAX_REQUEST = 1000;
    private final Request[] requestQueue;
    private int tail;  // ä¸‹æ¬¡putRequestçš„ä½�ç½®
    private int head;  // ä¸‹æ¬¡takeRequestçš„ä½�ç½®
    private int count; // Requestçš„æ•°é‡�

   

    public request_queue_channel() {
        this.requestQueue = new Request[MAX_REQUEST];
        this.head = 0;
        this.tail = 0;
        this.count = 0;
    }
   
    public int getCount() {
		return count;
	}
    
    
    public synchronized void putRequest(Request request) {
        while (count >= requestQueue.length) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }
    
    
    public synchronized Request takeRequest() {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        Request request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();
        return request;
    }
}
