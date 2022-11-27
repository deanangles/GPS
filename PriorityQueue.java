import java.util.Arrays;
import java.util.NoSuchElementException;

public class PriorityQueue<T extends Comparable<? super T>> implements PriorityQueueInterface<T> {
	// =====================================================================Properties
	private int size;
	private T[] heap;
	private static final int DEFAULT_CAPACITY = 11;

	// ======================================================================Constructors
	public PriorityQueue() {
		heap = (T[]) new Comparable[DEFAULT_CAPACITY];
		size = 0;
	}

	public PriorityQueue(T[] items) {
		size = 0;
		heap = (T[]) new Comparable[items.length + 1];
		for (int i = 0; i < items.length; i++)
			add(items[i]);
	}

	// ======================================================================Implemented
	// Methods
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean isFull() {
		for (int i = 1; i <= size / 2; i++) {
			if (heap[i] != null) {
				if (heap[i * 2] != null) {
					if (heap[i * 2 + 1] == null)
						return false;
				} else if (heap[i * 2 + 1] != null)
					return false;
			}
		}
		return true;
	}

	@Override
	public void clear() {
		Arrays.fill(heap, 0, size, null);
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(T newEntry) {
		verifyCapacity();
		heap[++size] = newEntry;
		reheapUp(size);
	}

	@Override
	public T peek() {
		return (isEmpty() ? null : heap[1]);
	}

	@Override
	public T remove() {
		if (isEmpty())
			throw new NoSuchElementException();
		T ret = heap[1];
		heap[1] = heap[size];
		heap[size--] = null;
		reheapDown(1);
		return ret;
	}

	// ========================================================================Helper
	// Methods
	public String toString() {
		return Arrays.toString(Arrays.copyOf(heap, size + 1));
	}

	private void verifyCapacity() {
		if (size == heap.length - 1)
			heap = Arrays.copyOf(heap, 2 * heap.length);
	}

	private void reheapUp(int index) {
		if (index < 2)
			return;
		int parentIndex = index / 2;
		if (getCompare(index, parentIndex) > 0)
			swap(index, parentIndex);
		reheapUp(parentIndex);
	}

	private void reheapDown(int index) {
		if (index > size / 2)
			return;
		int max;
		if (heap[index * 2] == null)
			max = index * 2 + 1;
		else if (heap[index * 2 + 1] == null)
			max = index * 2;
		else
			max = getMax(index * 2, index * 2 + 1);
		if (getCompare(max, index) > 0)
			swap(index, max);
		reheapDown(max);
	}

	private int getCompare(int i, int j) {
		return heap[i].compareTo(heap[j]);
	}

	private int getMax(int i, int j) {
		if (getCompare(i, j) > 0)
			return i;
		return j;
	}

	private void swap(int i, int j) {
		T tmp = heap[i];
		heap[i] = heap[j];
		heap[j] = tmp;
	}
}
