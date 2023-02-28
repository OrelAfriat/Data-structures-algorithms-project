public class MedianDS {
    MinTreeHeap L;
    MaxTreeHeap S;

    public MedianDS(int[] A) {
        int size = A.length;
        kthSmallest(A, 0, size - 1, (size + 1) / 2);
        int[] B = new int[((size + 1) / 2)];
        for (int i = 0; i < ((size + 1) / 2) ; i++) {
            B[i] = A[i];
        }
        this.S = MaxTreeHeap.BuildHeapT(B);


        int[] C = new int[((size) / 2)];
        for (int i = ((size + 1)/2); i < size ; i++) {
            C[i-((size + 1)/2)] = A[i];
        }
        this.L = MinTreeHeap.BuildHeapT(C);
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], pivotloc = low;
        for (int i = low; i <= high; i++) {
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pivotloc];
                arr[pivotloc] = temp;
                pivotloc++;
            }
        }

        int temp = arr[high];
        arr[high] = arr[pivotloc];
        arr[pivotloc] = temp;

        return pivotloc;
    }


    public static int kthSmallest(int[] arr, int low, int high, int k) {
        int partition = partition(arr, low, high);

        if (partition == k - 1)
            return arr[partition];

        else if (partition < k - 1)
            return kthSmallest(arr, partition + 1, high, k);

        else
            return kthSmallest(arr, low, partition - 1, k);
    }

    public void insert (int x) {
        if (x < S.root.key){
            S.HeapInsert(x);
        }
        else {
            L.HeapInsert(x);
        }
        if (L.heapSize == S.heapSize + 1){
            int r = L.HeapExtractMin();
            S.HeapInsert(r);
        }
        if (L.heapSize == S.heapSize - 2) {
            int r = S.HeapExtractMax();
            L.HeapInsert(r);
        }
    }

     public void delMedian() {
        S.HeapExtractMax();
        if (L.heapSize == S.heapSize +1){
            int r = L.HeapExtractMin();
            S.HeapInsert(r);
        }
     }

    public int findMedian () {
        return S.root.key;
    }

}


