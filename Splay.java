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
		Node newNode = treeInsert(item);

		if(newNode != null) 
		{
			splay(newNode);
		}
	}

	private Node treeInsert(E item) 
	{
		// checks to see if tree is empty
		// create new node to return it
		if(root == null) 
		{
			root = new Node(item);
			return root;
		}

		// used to traverse the tree
		Node current = root;

		// tracks last non-null visited
		Node parent = null;

		// traverse tree 
		while(current != null) 
		{
			// tracks where node is 
			parent = current;
			int cmp = item.compareTo(current.data);

			// if smaller go to left child
			if(cmp < 0) 
			{
				current = current.left;
			}

			// if larger go to right child
			else if(cmp > 0) 
			{
				current = current.right;
			}

			// item exists in tree
			else 
			{
				return null;
			}
		}

		// instantiate with given item
		Node newNode = new Node(item);

		// attach new node to parent
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
		// rotate so long as x is not the root
		while(x != root) 
		{
			// get x parent pointer
			Node parent = x.parent;
			Node grandparent = null;
			
			// if parent is not null, set grandparent to parent.parent
			if(parent != null)
			{
				grandparent = parent.parent;
			}

			// Zig aka single rotation
			// check if grandparent is full
			// means parent is root
			if(grandparent == null) 
			{
				// x is a left child
				// rotate right on the parent
				if(x == parent.left) 
				{
					rotateRight(parent);
				}

				// if x is a right child, rotate left on the parent
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
				// do two same direction rotations
				// left left = rotate right on grandparent and parent
				// right right = rotate left on grandparent and parent
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
				// do two opposite diretion rotations
				// right-left means rotate right on parent then rotate left on grandparent
				// left right means rotate left on parent then right on grandparent
				else{
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
				// go back to while loop and check if x is now root
				// if not, move x two levels up until x becomes root
			}
		}
	}

	// roate left around node x to make x.right the new root of subtree
	private void rotateLeft(Node x) 
	{
		// get x's right child
		Node y = x.right;
		// if null, no rotation on left
		if(y == null) return;
		// set x right pointer to y.left
		x.right = y.left;

		// if not ull, reassign its parent pointer to x
		if(y.left != null) 
		{
			// make y parent the same as x's old parent
			// if x was not the root
			// y will now occupy x's old position
			y.left.parent = x;
		}

		y.parent = x.parent;

		// if x had no parent, x was the root of the entire tree
		// y becomes the new root
		if(x.parent == null) 
		{
			root = y;
		}

		//if x did have a parent
		//check if x was left or right child of parent
		else if(x == x.parent.left) 
		{
			//connect paretn of y on the same sid
			x.parent.left = y;
		}

		else 
		{
			x.parent.right = y;
		}

		// finish rotation by mkaing x left child of y
		y.left = x;
		// y is new root ofsubtree
		// xx is on left side
		x.parent = y;
	}

	// same process, but left child moves up
	// old node x moves down to right
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
		System.out.println();
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

	// print in ascending order
	public void printRoot() 
	{
		if (root == null) 
		{
			System.out.println("The splay tree is empty.");
		} 

		else 
		{
			System.out.println("the root contains: " + root.data);
		}
	}
}

