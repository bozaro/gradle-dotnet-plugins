package kaizen.plugins.clr

public interface ClrContainer<E> extends Iterable<E> {
	void add(E element)
	boolean isEmpty()
	boolean contains(E element)
}
