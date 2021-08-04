public class Node {
    public Node(boolean isColorBlack, int value, Node parent){
        this.parent = parent;
        this.black = isColorBlack;
        this.value = value;
    }
    public Node(boolean isColorBlack, int value, Node parent, Node right, Node left){
        this.parent = parent;
        this.black = isColorBlack;
        this.value = value;
        this.right = right;
        this.left = left;
    }
    public boolean black = true;
    int value;
    Node parent = null;
    Node left = null;
    Node right = null;
}
