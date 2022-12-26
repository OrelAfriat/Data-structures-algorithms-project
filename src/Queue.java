class Queue
{
    private LinkedListNode rear;
    private LinkedListNode front;
    private int count;

    //constructor
    public Queue() {
        this.rear = null;
        this.front = null;
        this.count = 0;
    }


    // Utility function to dequeue the front element
    public void dequeue()     // delete at the beginning
    {
        if (front == null)
        {
            return;
        }

        LinkedListNode temp = front;
        // advance front to the next node
        front = front.next;

        // if the list becomes empty
        if (front == null) {
            rear = null;
        }

        // decrease the node's count by 1
        count -= 1;
    }

    // Utility function to add an item to the queue
    public void add(NodeTree item)     // insertion at the end
    {
        // allocate a new node in a heap
        LinkedListNode linkedNode = new LinkedListNode(item);

        // special case: queue was empty
        if (front == null)
        {
            // initialize both front and rear
            front = linkedNode;
            rear = linkedNode;
        }
        else {
            // update rear
            rear.next = linkedNode;
            rear = linkedNode;
        }

        // increase the node's count by 1
        count += 1;
    }

    // Utility function to return the top element in a queue
    public NodeTree peek()
    {
        // check for an empty queue
        if (front == null) {
            System.exit(-1);
        }

        return front.data;
    }


    // Utility function to check if the queue is empty or not
    public boolean isEmpty() {
        return rear == null && front == null;
    }

    // Function to return the size of the queue
    private int size() {
        return count;
    }
}

