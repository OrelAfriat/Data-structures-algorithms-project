public class NodeTree {
    NodeTree parent;
    NodeTree leftChild;
    NodeTree rightChild;
    int key;

    public NodeTree(int key){
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.key = key;
    }

}
