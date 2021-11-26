public class Queue {
    //NEW CLASS

    private static Node[] queue;
    private int head;
    private int tail;
    private final int MAX;

    /* CREATE(QUEUE) */
    public Queue (int max) {
        queue = new Node[max];
        MAX = max - 1;
        head = 1;
        tail = 1;
    }

    public boolean isEmpty () {
        return head == tail;
    }

    public boolean isFull () {
        return head == ((tail + 1) % MAX);
    }

    public void enqueue (Node x){
        if (isFull())
            System.out.println ("Queue is already full!");
        else {
            queue[tail] = x;
            if (tail == MAX)
                tail = 1;
            else
                tail++;
        }
    }

    public Node dequeue () {
        if (isEmpty())
            System.out.println ("Queue is empty!\n");
        else {
            Node x = queue[head];
            if (head == MAX)
                head = 1;
            else
                head++;
            return x;
        }
        return null; // if queue is empty
    }

    public Node queueHead () {
        if (isEmpty())
            System.out.println( "Queue is empty!\n");
        else
            return queue[head];
        return null; // if queue is empty
    }

    public Node queueTail () {
        if (isEmpty())
            System.out.println ("Queue is empty!\n");
        else
            return queue[tail];
        return null; // if queue is empty
    }

    /* returns the elements in the queue separated by spaces */
    public String getQueue () {
        String queueString = "";
        for (int i = 0; i< queue.length; i++){
            if (queue[i]!= null) {
                queueString += queue[i];
                queueString += ' ';
            }
        }
        return queueString;
    }
    
}
