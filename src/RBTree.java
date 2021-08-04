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

    public boolean delete(int elem){
        Node node = findNode(elem, root);
        chooseNextDeleteMethod(node);
        return false;
    }

    private void chooseNextDeleteMethod(Node node){
       if(!node.black){
           if(node.right != null && node.left != null){
               delete2N(node);
           }
           else deleteR0(node);
       }else{
           if(node.left != null){
               if(node.right != null) delete2N(node);
               else deleteB1(node);
           }
           else if( node.right != null)  deleteB1(node);
           else deleteB0(node);
       }
    }
    private void deleteB1(Node node){
        if(node.right == null){
            node.value = node.left.value;
            chooseNextDeleteMethod(node.left);
        }else {
            node.value = node.right.value;
            chooseNextDeleteMethod(node.right);
        }
    }
    private void delete2N(Node node){
        Node iter = node.left;
        while (iter.right != null){
            iter = iter.right;
        }
        node.value = iter.value;
        iter.value = node.value;
        chooseNextDeleteMethod(iter);
    }
    private void deleteR0(Node node){
        if(node.parent.value > node.value){
            node.parent.left = null;
        }else  node.parent.right = null;
    }
    private void deleteB0(Node node){
        balanceB0(node);
        node.parent.right = null;
    }
    private void balanceB0(Node node){
        if(!node.parent.black){
            if(node.parent.left.black){
                if(node.parent.left.left.black && node.parent.left.right.black){
                    node.parent.black = true;
                    node.parent.left.black = false;
                } else if(!node.parent.left.left.black){
                    Node sibling = node.parent.left;
                    node.parent.left = sibling.right;
                    sibling.right = node.parent;
                    sibling.parent = node.parent.parent;
                    node.parent.parent = sibling;
                    node.parent.black = true;
                    sibling.black = false;
                    sibling.left.black = true;
                    if(sibling.parent.value > sibling.value){
                        sibling.parent.left = sibling;
                    } else sibling.parent.right = sibling;
                }
            }
        }else {
            if(!node.parent.left.black && node.parent.left.right.right.black && node.parent.left.right.left.black){
                Node sibling = node.parent.left;
                node.parent.left = sibling.right;
                node.parent.left.black = false;
                sibling.right = node.parent;
                sibling.parent = node.parent.parent;
                node.parent.parent = sibling;
                sibling.black = true;
                if(sibling.parent.value > sibling.value){
                    sibling.parent.left = sibling;
                } else sibling.parent.right = sibling;
            } else if(!node.parent.left.black && !node.parent.left.right.left.black){
                node.value = node.parent.value;
                node.parent.value = node.parent.left.right.value;
                node.left = new Node(node.parent.left.right.right.black, node.parent.left.right.right.value, node);
                node.parent.left.right.value = node.parent.left.right.left.value;
                node.parent.left.right.left = null;
                node.parent.left.right.right = null;
            } else if(node.parent.left.black && !node.parent.left.right.black){
                node.value = node.parent.value;
                node.parent.value = node.parent.left.right.value;
                node.left = new Node(true, node.parent.left.right.right.value, node);
                node.parent.left.right.black = true;
                node.parent.left.right.value = node.parent.left.right.left.value;
                node.parent.left.right.left = null;
                node.parent.left.right.right = null;

            } else {
                node.parent.left.black= false;
                balanceB0(node.parent);
            }
        }
    }

    private Node findNode(int elem, Node node){
        if( elem == node.value){
            return node;
        }
        else if(elem > node.value){
            if(node.right != null){
                findNode(elem,node.right);
            }else return null;
        }
        else {
            if(node.left != null){
                findNode(elem,node.left);
            }else return null;
        }
        return null;
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
                colorSwap(parentNode);
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
    private void colorSwap(Node parentNode){
        parentNode.left.black = true;
        parentNode.right.black = true;
        if(parentNode.parent != null) {
            parentNode.black = false;
        }
    }
}
