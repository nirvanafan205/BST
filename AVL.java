interface BalanceTree<E extends Comparable<E>> 
{
	void insert(E item);    
	boolean find(E item);  
	void delete(E item); 
	int height();
}

public class AVL<E extends Comparable<E>> implements BalanceTree<E> 
{
	private class Node 
	{
		E data;
		Node left, right;
		int height; 

		Node(E data) 
		{
			this.data = data;
			this.height = 1;
		}
	}

	private Node root;

	public AVL() 
	{
		this.root = null;
	}

	@Override
	public void insert(E item) 
	{
		root = insertRecursive(root, item);
	}

	@Override
	public boolean find(E item) 
	{
		// not in use
		return false;
	}

	@Override
	public void delete(E item) 
	{
		// not in use
	}

	@Override
	public int height() 
	{
		return getHeight(root);
	}

	public void heightAVL(E item) 
	{
		Node node = findNode(root, item);

		if(node != null) 
		{
			System.out.println("node height: " + node.height);
		} 

		else 
		{
			System.out.println("Item not found");
		}
	}

	public void printAVL() 
	{
		printAVLInOrder(root);
		System.out.println();
	}

	// BST insertion
	private Node insertRecursive(Node node, E item) 
	{
		if(node == null) 
		{
			return new Node(item);
		}

		int check = item.compareTo(node.data);

		if(check < 0) 
		{
			node.left = insertRecursive(node.left, item);
		} 

		else if(check > 0) 
		{

			node.right = insertRecursive(node.right, item);
		}

		else 
		{
			return node;
		}

		// update node’s height
		updateHeight(node);

		// Rebalance 
		return balance(node);
	}

	private Node findNode(Node node, E item) 
	{
		// null node, tree/leaf child empty
		if(node == null) return null;

		// compar item with current node's data
		int cmp = item.compareTo(node.data);

		// if item less than node data, search left subtree
		if(cmp < 0) return findNode(node.left, item);

		// if greater, search right subtree
		else if(cmp > 0) return findNode(node.right, item);

		// node found
		else return node;
	}

	private void printAVLInOrder(Node node) 
	{
		if(node == null) return;

		printAVLInOrder(node.left);

		int bf = getBalanceFactor(node);

		System.out.print("(" + node.data + "," + bf + ")");

		printAVLInOrder(node.right);
	}

	private Node balance(Node node) 
	{
		// balance factor of node
		int balance = getBalanceFactor(node);

		// checks if left subtree is heavier by more than 1 lvl
		// do right rotation
		if(balance > 1) 
		{
			// check balance factor for let node
			// if greater or = 0, left subtree is leaning to left
			if(getBalanceFactor(node.left) >= 0) 
			{
				// perform single right rotation 
				node = rotateRight(node);
			}

			// if left subtree leaning right, do double rotation
			else 
			{
				// rotate left on child
				node.left = rotateLeft(node.left);
				// rotate right on node 
				node = rotateRight(node);
			}
		}

		// right subtree is heavier
		// do left rotation
		else if (balance < -1) 
		{
			// if right subtre balance <= 0, do single left rotation
			if (getBalanceFactor(node.right) <= 0) 
			{
				node = rotateLeft(node);
			}

			// right subtree leaning left
			else 
			{
				// double rotation, rotate right on child
				node.right = rotateRight(node.right);
				// rotate left on node
				node = rotateLeft(node);
			}
		}
		// return new root of subtree or same node that was passed
		return node;
	}

	private Node rotateRight(Node y) 
	{
		// x is new root of subtree
		Node x = y.left;
		Node temp = x.right;

		// perform rotation
		x.right = y;
		y.left = temp;

		// update heights
		updateHeight(y);
		updateHeight(x);

		// Return new root
		return x;
	}

	private Node rotateLeft(Node x) 
	{
		// y is new root of subtree
		Node y = x.right;
		Node temp = y.left;

		// Perform rotation
		y.left = x;
		x.right = temp;

		// Update heights
		updateHeight(x);
		updateHeight(y);

		// Return new root
		return y;
	}

	private void updateHeight(Node node) 
	{
		node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
	}

	private int getHeight(Node node) 
	{
		if (node == null) 
		{
			return 0;
		}

		else 
		{
			return node.height;
		}
	}

	private int getBalanceFactor(Node node) 
	{
		if(node == null) 
		{
			return 0; // no tree
		}

		// calc balance factor
		return getHeight(node.left) - getHeight(node.right);
	}
}
