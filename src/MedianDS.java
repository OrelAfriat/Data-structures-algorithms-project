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
        this.S = MaxTreeHeap.BuildHeapT(B);///////////איך בונים ערימה ממערך


        int[] C = new int[((size) / 2)];
        for (int i = ((size + 1)/2); i < size ; i++) {
            C[i-((size + 1)/2)] = A[i];
        }
        this.L = MinTreeHeap.BuildHeapT(C);/////////איך בונים ערימה ממערך
    }

    public static int partition(int[] arr, int low, int high) {
        int pivot = arr[high], pivotloc = low;
        for (int i = low; i <= high; i++) {
            // inserting elements of less value
            // to the left of the pivot location
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pivotloc];
                arr[pivotloc] = temp;
                pivotloc++;
            }
        }

        // swapping pivot to the final pivot location
        int temp = arr[high];
        arr[high] = arr[pivotloc];
        arr[pivotloc] = temp;

        return pivotloc;
    }


    public static int kthSmallest(int[] arr, int low, int high, int k) {
        // find the partition
        int partition = partition(arr, low, high);

        // if partition value is equal to the kth position,
        // return value at k.
        if (partition == k - 1)
            return arr[partition];

            // if partition value is less than kth position,
            // search right side of the array.
        else if (partition < k - 1)
            return kthSmallest(arr, partition + 1, high, k);

            // if partition value is more than kth position,
            // search left side of the array.
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


