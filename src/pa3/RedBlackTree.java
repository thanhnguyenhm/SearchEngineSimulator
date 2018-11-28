package pa3;

/**
 * Red Black Tree implementation based on CLRS textbook
 */
public class RedBlackTree{
    public static RBTreeNode root;
    public static int size = 0;
    static int sizeTemp = 0;

    public RedBlackTree() {
        root = null;
    }

    /**
     * Walk the tree in-order
     * @param x node
     */
    public static void inOrderTreeWalk(RBTreeNode x) {
        if (x != null) {
            inOrderTreeWalk(x.left);
            System.out.print(x.key + " ");
            inOrderTreeWalk(x.right);
        }
    }

    public static void leftRotate(RBTreeNode x) {
        RBTreeNode y = x.right;
        x.right = y.left;
        if (y.left != null)
            y.left.p = x;
        y.p = x.p;
        if (x.p == null)
            root = y;
        else if (x == x.p.left)
            x.p.left = y;
        else x.p.right = y;
        y.left = x;
        x.p = y;
    }

    public static void rightRotate(RBTreeNode x) {
        RBTreeNode y = x.left;
        x.left = y.right;
        if (y.right != null)
            y.right.p = x;
        y.p = x.p;
        if (x.p == null)
            root = y;
        else if (x == x.p.left)
            x.p.left = y;
        else x.p.right = y;
        y.right = x;
        x.p = y;
    }

    /**
     * Search method using recursion
     * @param x: node
     * @param k: key to search
     * @return RBTreeNode
     */
    public static RBTreeNode treeSearch(RBTreeNode x, int k) {
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
    public static RBTreeNode iterativeTreeSearch(RBTreeNode x, int k) {
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
    public static RBTreeNode treeMinimum(RBTreeNode x) {
        while (x.left != null) x = x.left;
        return x;
    }

    /**
     * Find maximum by going all the way to the right
     * @param x node
     * @return node
     */
    public static RBTreeNode treeMaximum(RBTreeNode x) {
        while (x.right != null) x = x.right;
        return x;
    }

    /**
     * Find successor of the RBT
     * @param x node
     * @return node
     */
    public static RBTreeNode treeSuccessor(RBTreeNode x) {
        if (x.right != null) return treeMinimum(x.right);
        RBTreeNode y = x.p;
        while (y != null && x == y.right) {
            x = y;
            y = y.p;
        }
        return y;
    }

    /**
     * Insert a node into RBT
     * @param z node
     */
    public static void treeInsert(RBTreeNode z) {
        RBTreeNode y = null;
        RBTreeNode x = root;
        while (x != null) {
            y = x;
            if (z.key < x.key)
                x = x.left;
            else x = x.right;
        }
        z.p = y;
        if (y == null)
            root = z;
        else if (z.key < y.key)
            y.left = z;
        else y.right = z;
        z.left = null;
        z.right = null;
        z.color = 1;
        RBInsertFixup(z);
        size++;
        sizeTemp++;
    }

    public static void RBInsertFixup(RBTreeNode z) {
        RBTreeNode y;
        while (z.p != null && z.p.color == 1) {
            if (z.p == z.p.p.left) {
                y = z.p.p.right;
                if (y == null || y.color == 1) {
                    z.p.color = 0;
                    if (y != null)
                        y.color = 0;
                    z.p.p.color = 1;
                    z = z.p.p;
                } else {
                    if (z == z.p.right) {
                        z = z.p;
                        leftRotate(z);
                    }
                    z.p.color = 0;
                    z.p.p.color = 1;
                    rightRotate(z.p.p);
                }
            } else {
                y = z.p.p.left;
                if (y == null || y.color == 1) {
                    z.p.color = 0;
                    if (y != null)
                        y.color = 0;
                    z.p.p.color = 1;
                    z = z.p.p;
                } else {
                    if (z == z.p.left) {
                        z = z.p;
                        rightRotate(z);
                    }
                    z.p.color = 0;
                    z.p.p.color = 1;
                    leftRotate(z.p.p);
                }
            }
        }
        root.color = 0;
    }

    public static void RBtransplant(RBTreeNode u, RBTreeNode v) {
        if (u.p == null) root = v;
        else if (u == u.p.left) u.p.left = v;
        else u.p.right = v;
        if (v != null)
            v.p = u.p;
    }

    /**
     * Delete a node in RBT
     * @param z node to be deleted
     */
    public static void RBtreeDelete(RBTreeNode z) {
        RBTreeNode y = z;
        RBTreeNode x;
        int yOriginalColor = y.color;
        if (z.left == null) {
            x = z.right;
            RBtransplant(z, z.right);
        } else if ( z.right == null) {
            x = z.left;
            RBtransplant(z, z.left);
        } else {
            y = treeMinimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.p == z)
                x.p = y;
            else {
                RBtransplant(y, y.right);
                y.right = z.right;
                y.right.p = y;
            }
            RBtransplant(z, y);
            y.left = z.left;
            y.left.p = y;
            y.color = z.color;
        }
        if (yOriginalColor == 0)
            RBTreeDeleteFixup(x);
        size--;
        sizeTemp--;
    }

    public static void RBTreeDeleteFixup(RBTreeNode x) {
        while (x != root && x.color == 0) {
            if (x == x.p.left) {
                RBTreeNode w = x.p.right;
                if (w.color == 1) {
                    w.color = 0;
                    x.p.color = 1;
                    leftRotate(x.p);
                    w = x.p.right;
                }
                if (w.left.color == 0 && w.right.color == 0) {
                    w.color = 1;
                    x = x.p;
                }
                else {
                    if (w.right.color == 0) {
                        w.left.color = 0;
                        w.color = 1;
                        rightRotate(w);
                        w = x.p.right;
                    }
                    w.color = x.p.color;
                    x.p.color = 0;
                    w.right.color = 0;
                    leftRotate(x.p);
                    x = root;
                }
            }
            else {
                RBTreeNode w = x.p.left;
                if (w.color == 1) {
                    w.color = 0;
                    x.p.color = 1;
                    rightRotate(x.p);
                    w = x.p.left;
                }
                if (w.right.color == 0 && w.left.color == 0) {
                    w.color = 1;
                    x = x.p;
                }
                else {
                    if (w.left.color == 0) {
                        w.right.color = 0;
                        w.color = 1;
                        leftRotate(w);
                        w = x.p.left;
                    }
                    w.color = x.p.color;
                    x.p.color = 0;
                    w.left.color = 0;
                    rightRotate(x.p);
                    x = root;
                }
            }
        }
        x.color = 0;
    }

    /**
     * Method to display the BST in order (descending order)
     * @return the output String of BST
     */
    public static String display(RBTreeNode x) {
        String output = "";

        if (x != null) {
            output += display(x.left);
            output += x.getUrl().toString() + " " + x.color + "\n";
            output += display(x.right);
        }

        return output;
    }

    /**
     * Method to display the BST with Page Rank in order (descending order)
     * @return the output String of BST
     */
    public static String displayWithRank(RBTreeNode x) {
        String output = "";

        if (x != null) {
            output += displayWithRank(x.left);
            output += x.getUrl().toString(x.getUrl().getPageRank()) + " " + x.color + "\n";
            output += displayWithRank(x.right);
        }

        return output;
    }

    /**
     * Add page rank for each Link obj in-order
     * @param x node
     */
    public static void addPageRank(RBTreeNode x) {
        if (x != null) {
            addPageRank(x.left);
            x.getUrl().setPageRank(sizeTemp--);
            addPageRank(x.right);
        }
    }
}
