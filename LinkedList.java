/**
 * Represents a list of Nodes.
 */
public class LinkedList {

	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list

	/**
	 * Constructs a new list.
	 */
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}

	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */
	public Node getLast() {
		return this.last;
	}

	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Gets the node located at the given index in this list.
	 *
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}

		Node currentNode = first;

		while (index > 0) {
			if (currentNode == null) return  currentNode;

			currentNode = currentNode.next;
			index--;
		}

		return currentNode;
	}

	/**
	 * Creates a new Node object that points to the given memory block,
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given
	 * index is either 0 or the list's size, the addition time is O(1).
	 *
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		if (index < 0 || index > size)
			throw new IllegalArgumentException("index must be between 0 and size");

		Node newNode = new Node(block);

		if (index == 0) {
			newNode.next = first;
			first = newNode;
		}

		if (index == size) {
			if(last != null)
			{
				last.next = newNode;
			}
			last = newNode;
		}

		if (index != 0 && index != size) {
			Node prevNode = getNode(index - 1);
			newNode.next = prevNode.next;
			prevNode.next = newNode;
		}

		size++;
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 *
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		add(size, block);
	}

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the beginning of this list (the node will become the list's first element).
	 *
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		add(0, block);
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 *
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		if (index < 0 || index > size)
			throw new IllegalArgumentException("index must be between 0 and size.");

		if (first == null) throw new IllegalArgumentException("Null list");

		return getNode(index).block;
	}

	/**
	 * Gets the index of the node pointing to the given memory block.
	 *
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		for (int i = 0; i < size; i++) {
			if (getNode(i).block == block) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes the given node from this list.
	 *
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		if (first == node) {
			first = first.next;
			if (first == null) last = first;

			size --;
		}

		for (int i = 1 ; i < size ; i++) {
			Node currentNode = getNode(i);
			if(currentNode == node) {
				Node prevNode = getNode(i - 1);
				if(currentNode == last) {
					last = prevNode;
				}

				prevNode.next = currentNode.next;
				currentNode.next = null;

				size--;
				break;
			}
		}
	}


	/**
	 * Removes from this list the node which is located at the given index.
	 *
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 0)
			throw new IllegalArgumentException("Index is negative or greater than the list's size.");

		remove(getNode(index));
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 *
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		int indexOfBlock = indexOf(block);

		if (indexOfBlock < 0)
			throw new IllegalArgumentException("The given memory block is not in this list.");

		remove(getNode(indexOfBlock));
	}

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}

	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		String s = "";
		Node current = first;

		while (current != null) {
			s = s + current.block + " ";
			current = current.next;
		}

		return s;
	}

	public void sort (){
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - i - 1; j++) {
				Node current = getNode(j);
				Node next = current.next;

				if (current.block.baseAddress > next.block.baseAddress){
					MemoryBlock temp = current.block;
					current.block = next.block;
					next.block = temp;
				}
			}
		}
	}
}
