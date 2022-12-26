import java.io.DataOutputStream;
import java.io.IOException;

public class MinTreeHeap {
    int heapSize;
    NodeTree root;

    public MinTreeHeap(int heapSize, NodeTree root) {
        this.heapSize = heapSize;
        this.root = root;
    }


    static void OriginalHeapify(int[] arr, int i) {

        int N = arr.length;
        int smallest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < N && arr[l] < arr[smallest])
            smallest = l;

        if (r < N && arr[r] < arr[smallest])
            smallest = r;

        if (smallest != i) {
            int swap = arr[i];
            arr[i] = arr[smallest];
            arr[smallest] = swap;

            OriginalHeapify(arr, smallest);
        }
    }

    static void OriginalBuildHeap(int[] arr) {
        int N = arr.length;
        int startIdx = (N / 2) - 1;

        for (int i = startIdx; i >= 0; i--) {
            OriginalHeapify(arr, i);
        }
    }

    public static MinTreeHeap BuildHeapT(int[] A) {
        OriginalBuildHeap(A);
        NodeTree[] B = new NodeTree[A.length];
        for (int i = 0; i < A.length; i++) {
            B[i] = new NodeTree(A[i]);
        }
        for (int j = 0; j < B.length; j++) {
            if (j != 0) {
                if (j % 2 == 0){
                    B[j].parent = B[(j-1 / 2)];
                }
                else {
                    B[j].parent = B[(j / 2)];
                }

            }
            if ((2 * j) + 1 < B.length) {
                B[j].leftChild = B[(2 * j) + 1];
            }
            if (2 * j + 2 < B.length) {
                B[j].rightChild = B[(2 * j) + 2];
            }
        }
        return new MinTreeHeap(B.length, B[0]);
    }

    public void HeapInsert(int k) {
        System.out.println("TEST: INSERT:" + k);//למחוק אח''כ
        NodeTree newNode = new NodeTree(k);
        //Testing_Func(newNode);//למחוק אח''כ
        this.heapSize = this.heapSize + 1;
        //System.out.println("TEST:THE HEAPSIZE IS " + this.heapSize);//למחוק אח''כ
        newNode.parent = getNode((this.heapSize) / 2);
        if ((this.heapSize % 2) != 0) {
            (newNode.parent).rightChild = newNode;
        } else {
            (newNode.parent).leftChild = newNode;
        }
        //Testing_Func(newNode);//למחוק אח''כ
        Update_Heap(newNode);
    }


    /*private void Testing_Func(NodeTree newNode) {//למחוק אח''כ
        int now = newNode.key;
        if (newNode.parent != null) {//למחוק אח''כ

            System.out.println("TEST: THE node IS " + newNode.key);//למחוק אח''כ
            System.out.println("TEST: THE parent IS " + (newNode.parent).key);//למחוק אח''כ
            if (newNode.parent.leftChild != null) {
                System.out.println("THE left child IS " + ((newNode.parent).leftChild).key);//למחוק אח''כ
            }
            if (newNode.parent.rightChild != null) {
                System.out.println("THE right child IS " + ((newNode.parent).rightChild).key);//למחוק אח''כ
            }
            if (newNode.parent.leftChild != null && newNode.key != newNode.parent.leftChild.key) {
                if (newNode.parent.rightChild != null && newNode.key != newNode.parent.rightChild.key) {
                    System.out.println("ERROR ");//למחוק אח''כ
                    System.out.println("TEST: THE node IS " + newNode.key);//למחוק אח''כ
                    System.out.println("TEST: THE parent IS " + (newNode.parent).key);//למחוק אח''כ
                    System.out.println("THE left child IS " + ((newNode.parent).leftChild).key);//למחוק אח''כ
                    System.out.println("THE right child IS " + ((newNode.parent).rightChild).key);//למחוק אח''כ
                }
            }
            Testing_Func(newNode.parent);
        }
    }*/

    public void Update_Heap(NodeTree node) {
        while (node.parent != null && (node.parent).key > node.key) {
            //System.out.println("TEST: SWAP BETWEEN THE NODE " + node.key + " AND THE PARENT " + (node.parent).key);//למחוק אח''כ
            int key = node.key;
            node.key = (node.parent).key;
            (node.parent).key = key;
            node = node.parent;
        }
    }

    public NodeTree getNode(int index){
        int size = (int) ((Math.log(index)) / (Math.log(2)));
        int [] row = new int[size];
        int counter = 0;
        if (index == 1){
            return this.root;
        }
        while (index > 1) {
            if (index % 2 != 0) { //אי זוגי = בן ימני
                row[counter] = 0;
            }
            else { // זוגי = בן שמאלי
                row[counter] = 1;
            }
            counter =counter +1;
            index = index / 2;
        }
        NodeTree recentNode = this.root;
        for (int j = row.length-1 ; j >= 0; j--) {
            if (row[j] == 0 && recentNode.rightChild != null) {
                recentNode = recentNode.rightChild;
            }
            if (row[j] == 1 && recentNode.leftChild != null) {
                recentNode = recentNode.leftChild;
            }
        }
        return recentNode;
    }

    public void Heapify (int recentIndex) {
        int l = 2*recentIndex;
        int smallest;
        if (l <= this.heapSize && getNode(l).key < getNode(recentIndex).key){
            smallest = l;
        }
        else {
            smallest = recentIndex;
        }
        int r = 2*recentIndex +1;
        if (r <= this.heapSize && getNode(r).key < getNode(smallest).key ){
            smallest = r;

        }
        if (smallest != recentIndex){
            int swap = getNode(recentIndex).key;
            getNode(recentIndex).key = getNode(smallest).key ;
            getNode(smallest).key = swap;
            Heapify(smallest);
        }
    }

    public int HeapExtractMin() {
        System.out.println("TEST: EXTRACT MIN "+ (this.root).key);//למחוק אח''כ
        //System.out.println("TEST:THE HEAPSIZE IS " + this.heapSize);//למחוק אח''כ
        if (this.heapSize == 0){
            return -1;
        }
        int min = this.root.key;
        if (this.heapSize == 1){
            this.heapSize = 0;
            return min;
        }
        this.root.key = getNode(this.heapSize).key;
        getNode(this.heapSize).key = 0;
        getNode(this.heapSize).parent = null;
        if (heapSize % 2 != 0) {
            getNode((this.heapSize)/2).rightChild = null;
        }
        else {
            getNode((this.heapSize)/2).leftChild = null;
        }
        this.heapSize = this.heapSize - 1;
        //System.out.println("TEST:THE HEAPSIZE IS " + this.heapSize);//למחוק אח''כ
        Heapify(1);
        //Testing_Func(getNode(this.heapSize));//למחוק אח''כ
        return min;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        if (this.root == null) {
            return;
        }
        //out.writeBytes("TEST: HEAPSIZE IS:" + this.heapSize + System.lineSeparator());//למחוק אח''כ
        Queue q1 = new Queue();
        Queue q2 = new Queue();
        q1.add(this.root);
        while (!q1.isEmpty() || !q2.isEmpty()) {
            while (!q1.isEmpty()) {
                NodeTree currentRoot1 = q1.peek();
                out.writeBytes(String.valueOf(currentRoot1.key));
                if (currentRoot1.leftChild != null) {
                    int left = (currentRoot1.leftChild).key;//ךמחוק אחכ
                    q2.add(currentRoot1.leftChild);
                }
                if (currentRoot1.rightChild != null) {
                    int right = (currentRoot1.rightChild).key;//ךמחוק אחכ
                    q2.add(currentRoot1.rightChild);
                }
                q1.dequeue();
                if (!q1.isEmpty()){
                    out.writeBytes(",");
                }
            }
            if (!q2.isEmpty()){
                out.writeBytes(System.lineSeparator());
            }
            while (!q2.isEmpty()) {
                NodeTree currentRoot2  = q2.peek();
                out.writeBytes(String.valueOf(currentRoot2.key));
                if (currentRoot2.leftChild != null) {
                    int left = (currentRoot2.leftChild).key;//ךמחוק אחכ
                    q1.add(currentRoot2.leftChild);
                }
                if (currentRoot2.rightChild != null) {
                    int right= (currentRoot2.rightChild).key;//ךמחוק אחכ
                    q1.add(currentRoot2.rightChild);
                }
                q2.dequeue();
                if (!q2.isEmpty()){
                    out.writeBytes(",");
                }
            }
            out.writeBytes(System.lineSeparator());
        }
    }
}


