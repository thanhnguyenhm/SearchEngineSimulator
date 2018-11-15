package pa2;

/**
 * Binary Search Tree with generics implementation
 */
public class BinarySearchTree{
    static Node root;

    public BinarySearchTree() {
        root = null;
    }

    /**
     * Walk the tree in-order
     * @param x node
     */
    public static void inOrderTreeWalk(Node x) {
        if (x != null) {
            inOrderTreeWalk(x.left);
            System.out.print(x.key + " ");
            inOrderTreeWalk(x.right);
        }
    }

    /**
     * Search method using recursion
     * @param x: node
     * @param k: key to search
     * @return Node
     */
    public static Node treeSearch(Node x, int k) {
        if (x == null || k == x.key) return x;
        if (k < x.key) return treeSearch(x.left, k);
        else return treeSearch(x.right, k);
    }

    /**
     * More efficient search using iterative
     * @param x node
     * @param k: key to search
     * @return node
     */
    public static Node iterativeTreeSearch(Node x, int k) {
        while (x != null && k != x.key) {
            if (k < x.key) x = x.left;
            else x = x.right;
        }
        return x;
    }

    /**
     * Find minimum by going all the way to the left
     * @param x node
     * @return node
     */
    public static Node treeMinimum(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    /**
     * Find maximum by going all the way to the right
     * @param x node
     * @return node
     */
    public static Node treeMaximum(Node x) {
        while (x.right != null) x = x.right;
        return x;
    }

    /**
     * Find successor of the BST
     * @param x node
     * @return node
     */
    public static Node treeSuccessor(Node x) {
        if (x.right != null) return treeMinimum(x.right);
        Node y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Insert a node into BST
     * @param z node
     */
    public static void treeInsert(Node z) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key) x = x.left;
            else x = x.right;
        }
        z.p = y;
        if (y == null) root = z;
        else if (z.key < y.key) y.left = z;
        else y.right = z;
    }

    public static void transplant(Node u, Node v) {
        if (u.p == null) root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        if (v != null) v.p = u.p;
    }

    /**
     * Delete a node in BST
     * @param z node to be deleted
     */
    public static void treeDelete(Node z) {
        if (z.left == null) transplant(z, z.right);
        else if (z.right == null) transplant(z, z.left);
        else {
            Node y = treeMinimum(z.right);
            if (y.p != z) {
                transplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.p = y;
        }
    }
}
