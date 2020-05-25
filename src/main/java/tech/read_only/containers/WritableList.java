package tech.read_only.containers;

import javax.annotation.Nullable;

/** A {@link ReadableList} that also supports the addition and removal of elements. */
public interface WritableList<E> extends ReadableList<E>, WritableContainer<E> {
    /**
     * Remove the element at the specified index.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds (less than 0, or greater than
     *     or equal to {@link #size()})
     */
    @Nullable
    E removeAt(final int index);

    /**
     * Set the element at the specified index.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds (less than 0, or greater than
     *     or equal to {@link #size()})
     */
    void set(final int index, @Nullable final E e);
}
