class Queue
{
    private LinkedListNode rear;
    private LinkedListNode front;
    private int count;

 
    public Queue() {
        this.rear = null;
        this.front = null;
        this.count = 0;
    }


   
    public void dequeue()    
    {
        if (front == null)
        {
            return;
        }

        LinkedListNode temp = front;
    
        front = front.next;

    
        if (front == null) {
            rear = null;
        }


        count -= 1;
    }


    public void add(NodeTree item)    
    {
       
        LinkedListNode linkedNode = new LinkedListNode(item);

     
        if (front == null)
        {
           
            front = linkedNode;
            rear = linkedNode;
        }
        else {
         
            rear.next = linkedNode;
            rear = linkedNode;
        }

   
        count += 1;
    }


    public NodeTree peek()
    {
       
        if (front == null) {
            System.exit(-1);
        }

        return front.data;
    }



    public boolean isEmpty() {
        return rear == null && front == null;
    }
    
    private int size() {
        return count;
    }
}

