package tech.read_only.containers;

import java.util.Arrays;
import javax.annotation.Nullable;

/** A {@link ReadableList} backed by an array. Guarantees constant-time lookup by index. */
public class ReadableArrayList<E> extends AbstractReadableList<E> {
    protected Object[] array;

    public ReadableArrayList() {
        array = new Object[0];
    }

    public ReadableArrayList(@Nullable final E e) {
        array = new Object[1];
        array[0] = e;
    }

    @SafeVarargs
    public ReadableArrayList(final E... elems) {
        array = new Object[elems.length];
        System.arraycopy(elems, 0, array, 0, elems.length);
    }

    public ReadableArrayList(@Nullable final Container<E> container) {
        if (container == null) {
            array = new Object[0];
            return;
        }

        array = new Object[container.size()];
        final ReadableIterator<E> iter = container.iterator();
        for (int i = 0; i < container.size(); i++) {
            array[i] = iter.next();
        }
    }

    @Override
    public E get(final int index) {
        if (index < 0 || index >= array.length) {
            throw new IllegalArgumentException();
        }

        //noinspection unchecked
        return (E) array[index];
    }

    @Override
    public boolean contains(@Nullable final Object e) {
        return this.indexOf(e) >= 0;
    }

    @Override
    public int indexOf(@Nullable final Object e) {
        if (e == null) {
            return this.indexOfNull();
        }

        for (int i = 0; i < array.length; i++) {
            if (e.equals(array[i])) {
                return i;
            }
        }

        return -1;
    }

    protected int indexOfNull() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return array.length;
    }

    @Override
    public E[] toArray() {
        final Object[] copy = new Object[this.size()];
        System.arraycopy(this.array, 0, copy, 0, this.size());

        //noinspection unchecked
        return (E[]) copy;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.array);
    }
}
