public class RBTree {
    private  boolean isRootInit = false;
    private Node root = null;
    public boolean add(int elem){
        if(!isRootInit){
            root = new Node(true,elem, null);
            isRootInit = true;
            return true;
        }
        root = createNode(root,elem);
        return true;
    }
    private Node createNode(Node motherNode, int elem) {
        if (motherNode.value > elem) {
            if (motherNode.left == null) {
                motherNode.left = new Node(false, elem, motherNode);

                return     balance(motherNode);
            } else {
                motherNode = createNode(motherNode.left, elem);
            }
        }else if(motherNode.value < elem) {
            if (motherNode.right == null) {
                motherNode.right = new Node(false, elem, motherNode);

                return balance(motherNode);
            } else {
                motherNode = createNode(motherNode.right, elem);
            }
        }
        return motherNode;
    }
    private Node balance(Node parentNode){
        if(parentNode.right == null){
            if(parentNode.left != null && parentNode.left.left != null){
                if(!parentNode.left.black && !parentNode.left.left.black){
                    parentNode = rightFlip(parentNode);
                    balance(parentNode);
                }
            }
        }
        else if( parentNode.left == null){
            if(!parentNode.right.black){
                parentNode = leftFlip(parentNode);
                balance(parentNode);
            }
        }
        else if(!parentNode.right.black ){
            if(!parentNode.left.black){
                swap(parentNode);
                balance(parentNode);
            }else{
                parentNode = leftFlip(parentNode);
                balance(parentNode);
            }
            if(!parentNode.left.black && !parentNode.left.left.black){
                parentNode = rightFlip(parentNode);
                balance(parentNode);
            }
        }
        if(parentNode.parent != null){
            if(parentNode.parent.value > parentNode.value){
                parentNode.parent.left = parentNode;
            }else parentNode.parent.right = parentNode;
            parentNode = balance(parentNode.parent);
        }
        return parentNode;
    }
    private Node leftFlip(Node parentNode){
        Node parentSafeNode = new Node(false, parentNode.value, parentNode.right, parentNode.right.left, parentNode.left);
        parentNode.right.parent = parentNode.parent;
        if( parentNode.left != null) {
            parentNode.left.parent = parentSafeNode;
        }
        if( parentNode.right.left != null){
            parentNode.right.left.parent = parentSafeNode;
            parentNode.right.left.parent.parent = parentNode.right;
        }
        parentNode = parentNode.right;
        parentNode.left = parentSafeNode;
        parentNode.black = true;
        return parentNode;
    }
    private Node rightFlip(Node parentNode){
        Node parentSafeNode = new Node(false, parentNode.value, parentNode.left, parentNode.right, parentNode.left.right);
        parentNode.left.parent = parentNode.parent;
        parentNode = parentNode.left;
        parentNode.right = parentSafeNode;
        parentNode.black = true;
        return parentNode;
    }
    private void swap(Node parentNode){
        parentNode.left.black = true;
        parentNode.right.black = true;
        if(parentNode.parent != null) {
            parentNode.black = false;
        }
    }
}
