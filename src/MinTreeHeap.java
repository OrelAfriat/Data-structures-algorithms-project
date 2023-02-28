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
                if (j % 2 == 0) {
                    B[j].parent = B[(j / 2) - 1];
                } else {
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
        NodeTree newNode = new NodeTree(k);
        this.heapSize = this.heapSize + 1;
        newNode.parent = getNode((this.heapSize) / 2);
        if ((this.heapSize % 2) != 0) {
            (newNode.parent).rightChild = newNode;
        } else {
            (newNode.parent).leftChild = newNode;
        }
        Update_Heap(newNode);
    }


    public void Update_Heap(NodeTree node) {
        while (node.parent != null && (node.parent).key > node.key) {
            int key = node.key;
            node.key = (node.parent).key;
            (node.parent).key = key;
            node = node.parent;
        }
    }

    public NodeTree getNode(int index) {
        int size = (int) ((Math.log(index)) / (Math.log(2)));
        int[] row = new int[size];
        int counter = 0;
        if (index == 1) {
            return this.root;
        }
        while (index > 1) {
            if (index % 2 != 0) {
                row[counter] = 0;
            } else {
                row[counter] = 1;
            }
            counter = counter + 1;
            index = index / 2;
        }
        NodeTree recentNode = this.root;
        for (int j = row.length - 1; j >= 0; j--) {
            if (row[j] == 0 && recentNode.rightChild != null) {
                recentNode = recentNode.rightChild;
            }
            if (row[j] == 1 && recentNode.leftChild != null) {
                recentNode = recentNode.leftChild;
            }
        }
        return recentNode;
    }

    public void Heapify(int recentIndex) {
        int l = 2 * recentIndex;
        int smallest;
        if (l <= this.heapSize && getNode(l).key < getNode(recentIndex).key) {
            smallest = l;
        } else {
            smallest = recentIndex;
        }
        int r = 2 * recentIndex + 1;
        if (r <= this.heapSize && getNode(r).key < getNode(smallest).key) {
            smallest = r;

        }
        if (smallest != recentIndex) {
            int swap = getNode(recentIndex).key;
            getNode(recentIndex).key = getNode(smallest).key;
            getNode(smallest).key = swap;
            Heapify(smallest);
        }
    }

    public int HeapExtractMin() {
        if (this.heapSize == 0) {
            return -1;
        }
        int min = this.root.key;
        if (this.heapSize == 1) {
            this.heapSize = 0;
            return min;
        }
        this.root.key = getNode(this.heapSize).key;
        getNode(this.heapSize).key = 0;
        getNode(this.heapSize).parent = null;
        if (heapSize % 2 != 0) {
            getNode((this.heapSize) / 2).rightChild = null;
        } else {
            getNode((this.heapSize) / 2).leftChild = null;
        }
        this.heapSize = this.heapSize - 1;
        Heapify(1);
        return min;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        if (this.root == null) {
            return;
        }
        Queue q1 = new Queue();
        Queue q2 = new Queue();
        q1.add(this.root);
        while (!q1.isEmpty() || !q2.isEmpty()) {
            while (!q1.isEmpty()) {
                NodeTree currentRoot1 = q1.peek();
                out.writeBytes(String.valueOf(currentRoot1.key));
                if (currentRoot1.leftChild != null) {
                    q2.add(currentRoot1.leftChild);
                }
                if (currentRoot1.rightChild != null) {
                    q2.add(currentRoot1.rightChild);
                }
                q1.dequeue();
                if (!q1.isEmpty()) {
                    out.writeBytes(",");
                }
            }
            if (!q2.isEmpty()) {
                out.writeBytes(System.lineSeparator());
            }
            while (!q2.isEmpty()) {
                NodeTree currentRoot2 = q2.peek();
                out.writeBytes(String.valueOf(currentRoot2.key));
                if (currentRoot2.leftChild != null) {
                    q1.add(currentRoot2.leftChild);
                }
                if (currentRoot2.rightChild != null) {
                    q1.add(currentRoot2.rightChild);
                }
                q2.dequeue();
                if (!q2.isEmpty()) {
                    out.writeBytes(",");
                }
            }
            out.writeBytes(System.lineSeparator());
        }
    }
}
