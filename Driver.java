public class Driver 
{
    public static void main(String[] args) 
    {
        System.out.println("AVL Tree");
        AVL<Integer> avlTree = new AVL<>();

        avlTree.insert(5);
        avlTree.insert(9);
        avlTree.insert(7);
        avlTree.insert(15);

        avlTree.printAVL();
        avlTree.heightAVL(7);

        System.out.println("-------------------\n");

        System.out.println("Splay Tree");
        Splay<Integer> splayTree = new Splay<>();

        splayTree.insert(5);
        splayTree.insert(9);
        splayTree.insert(7);
        splayTree.insert(15);

        splayTree.printSplay();
    }
}
