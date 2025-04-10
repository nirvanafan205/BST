public class Splay<E extends Comparable<E>> implements SplayTree<E> 
{
	private class Node 
	{
		E data;
		Node left, right, parent;
		Node(E data) 
		{
			this.data = data;
		}
	}

	private Node root;

	public Splay() 
	{
		root = null;
	}

	@Override
	public void insert(E item) 
	{
		Node newNode = bstInsert(item);
		if (newNode != null) {
			splay(newNode);
		}
	}

	private Node bstInsert(E item) 
	{
		if(root == null) 
		{
			root = new Node(item);
			return root;
		}

		Node current = root;
		Node parent = null;

		while(current != null) 
		{
			parent = current;
			int cmp = item.compareTo(current.data);

			if(cmp < 0) 
			{
				current = current.left;
			}

			else if(cmp > 0) 
			{
				current = current.right;
			}

			else 
			{
				return null; // duplicate
			}
		}

		Node newNode = new Node(item);

		if(item.compareTo(parent.data) < 0) 
		{
			parent.left = newNode;
		}

		else 
		{
			parent.right = newNode;
		}

		newNode.parent = parent;
		return newNode;
	}

	private void splay(Node x) 
	{
		while(x != root) 
		{
			Node parent = x.parent;
			Node grandparent = (parent != null) ? parent.parent : null;

			// Zig
			if(grandparent == null) 
			{
				if(x == parent.left) 
				{
					rotateRight(parent);
				}

				else 
				{
					rotateLeft(parent);
				}

			}

			else 
			{
				boolean xIsLeft = (x == parent.left);
				boolean pIsLeft = (parent == grandparent.left);

				// Zig-Zig
				if(xIsLeft && pIsLeft) 
				{
					rotateRight(grandparent);
					rotateRight(parent);
				}

				else if(!xIsLeft && !pIsLeft) 
				{
					rotateLeft(grandparent);
					rotateLeft(parent);
				} 

				// Zig-Zag
				else 
				{
					if(xIsLeft && !pIsLeft) 
					{
						rotateRight(parent);
						rotateLeft(grandparent);
					}

					else 
					{
						rotateLeft(parent);
						rotateRight(grandparent);
					}
				}
			}
		}
	}

	private void rotateLeft(Node x) 
	{
		Node y = x.right;

		if(y == null) return;
		x.right = y.left;

		if(y.left != null) 
		{
			y.left.parent = x;
		}

		y.parent = x.parent;
		if(x.parent == null) 
		{
			root = y;
		} 

		else if(x == x.parent.left) 
		{
			x.parent.left = y;
		}

		else 
		{
			x.parent.right = y;
		}

		y.left = x;
		x.parent = y;
	}

	private void rotateRight(Node x) 
	{
		Node y = x.left;

		if(y == null) return;
		x.left = y.right;

		if(y.right != null) 
		{
			y.right.parent = x;
		}

		y.parent = x.parent;

		if(x.parent == null) 
		{
			root = y;
		}

		else if (x == x.parent.right) 
		{
			x.parent.right = y;
		}

		else 
		{
			x.parent.left = y;
		}

		y.right = x;
		x.parent = y;
	}

	public void printSplay() 
	{
		printInOrder(root);
		System.out.println(); // move to next line
	}

	private void printInOrder(Node node) 
	{
		if(node == null) return;
		printInOrder(node.left);
		System.out.print("(" + node.data + "," + nodeHeight(node) + ")");
		printInOrder(node.right);
	}

	private int nodeHeight(Node n) 
	{
		if (n == null) return -1;
		return 1 + Math.max(nodeHeight(n.left), nodeHeight(n.right));
	}

	@Override
	public boolean find(E item) 
	{
		return false;
	}

	@Override
	public void delete(E item) 
	{
	}

	@Override
	public int height() 
	{
		return nodeHeight(root);
	}
}
