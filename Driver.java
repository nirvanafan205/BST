public class Driver 
{
    public static void main(String[] args) 
    {
        // create AVL tree
        AVL<Integer> avlTree = new AVL<>();

        // insert  nodes
        avlTree.insert(5);
        avlTree.insert(9);
        avlTree.insert(7);
        avlTree.insert(15);

        // print tree
        avlTree.printAVL();

        // print the height of node 7
        avlTree.heightAVL(7);  
    }
}
