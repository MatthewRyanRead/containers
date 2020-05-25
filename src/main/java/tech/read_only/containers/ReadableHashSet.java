package tech.read_only.containers;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link ReadableSet} with efficient lookup/insertion times based on {@code E.hashCode()}'s
 * efficiency.
 */
public class ReadableHashSet<E> implements ReadableSet<E> {
    protected static final float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

    protected final float maxLoadFactor;

    // TODO: Create a Node class and use a linked list of Node<E> for each entry in the array
    protected Object[][] hashtable;
    protected int size = 0;

    protected Integer cachedHashCode = null;

    protected boolean containsNull = false;

    protected ReadableHashSet(@Nullable final E e, final float loadFactor) {
        this.maxLoadFactor = loadFactor;

        if (e == null) {
            this.hashtable = new Object[0][];
            this.containsNull = true;
        } else {
            this.hashtable = new Object[][] {new Object[] {e}};
        }

        this.size = 1;
    }

    protected ReadableHashSet(final E[] array, final float loadFactor) {
        this.maxLoadFactor = loadFactor;
        this.setup(array);
    }

    protected void setup(final E[] array) {
        this.hashtable = new Object[(int) (array.length / this.maxLoadFactor)][];
        for (final E elem : array) {
            if (this.contains(elem)) continue;

            if (elem == null) {
                if (!this.containsNull) {
                    this.containsNull = true;
                    this.size++;
                }
                continue;
            }

            final int index = this.getIndex(elem);
            Object[] elems = this.hashtable[index];

            if (elems == null) {
                elems = new Object[] {elem};
                this.size++;
            } else if (!this.contains(elem)) {
                final Object[] newElems = new Object[elems.length + 1];
                System.arraycopy(elems, 0, newElems, 0, elems.length);
                newElems[elems.length] = elem;
                elems = newElems;
                this.size++;
            }

            this.hashtable[index] = elems;
        }
    }

    protected ReadableHashSet(@Nullable final Container<E> other, final float loadFactor) {
        this.maxLoadFactor = loadFactor;

        if (other == null) {
            this.hashtable = new Object[0][];
            return;
        }

        this.setup(other.toArray());
    }

    public ReadableHashSet() {
        this.maxLoadFactor = DEFAULT_MAX_LOAD_FACTOR;
        this.hashtable = new Object[0][];
    }

    public ReadableHashSet(@Nullable final E e) {
        this(e, DEFAULT_MAX_LOAD_FACTOR);
    }

    @SafeVarargs
    public ReadableHashSet(final E... other) {
        this(other, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ReadableHashSet(@Nullable final Container<E> other) {
        this(other, DEFAULT_MAX_LOAD_FACTOR);
    }

    @Override
    public boolean contains(@Nullable final Object e) {
        if (this.isEmpty()) return false;
        if (e == null) return this.containsNull;
        if (this.hashtable.length == 0) return false;

        final Object[] candidates = this.hashtable[this.getIndex(e)];
        if (candidates == null) {
            return false;
        }

        for (final Object candidate : candidates) {
            if (e.equals(candidate)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final ReadableHashSet<?> that = (ReadableHashSet<?>) o;
        return this.size == that.size
                && this.containsNull == that.containsNull
                && Arrays.stream(this.hashtable)
                        .filter(Objects::nonNull)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .allMatch(that::contains);
    }

    @Override
    public int hashCode() {
        if (cachedHashCode == null) {
            cachedHashCode =
                    Arrays.stream(this.hashtable)
                                    .filter(Objects::nonNull)
                                    .flatMap(Arrays::stream)
                                    .filter(Objects::nonNull)
                                    .map(Object::hashCode)
                                    .reduce(1, (hc1, hc2) -> 31 * hc1 + hc2)
                            * (this.containsNull ? 31 : 1);
        }

        return cachedHashCode;
    }

    @Override
    public String toString() {
        return (this.containsNull ? "null" : "")
                + (this.containsNull && this.size() > 1 ? ", " : "")
                + Arrays.stream(this.hashtable)
                        .filter(Objects::nonNull)
                        .flatMap(Arrays::stream)
                        .filter(Objects::nonNull)
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }

    protected int getIndex(final Object e) {
        return e.hashCode() % this.hashtable.length;
    }

    @Override
    public ReadableIterator<E> iterator() {
        return new ReadableHashSetIterator();
    }

    protected class ReadableHashSetIterator implements ReadableIterator<E> {
        protected int currIndex = 0;
        protected int outerArrayIndex = 0;
        protected int innerArrayIndex = -1;

        @Override
        public boolean hasNext() {
            return this.currIndex < ReadableHashSet.this.size();
        }

        @Nullable
        @Override
        public E next() throws IllegalStateException {
            if (!this.hasNext()) throw new IllegalStateException("No elements remaining");
            if (this.currIndex++ == 0 && ReadableHashSet.this.containsNull) return null;

            Object[] elems = ReadableHashSet.this.hashtable[outerArrayIndex];
            if (elems == null || this.innerArrayIndex == elems.length - 1) {
                this.advanceOuter();
            }

            //noinspection unchecked
            return (E) ReadableHashSet.this.hashtable[outerArrayIndex][++innerArrayIndex];
        }

        protected void advanceOuter() {
            this.innerArrayIndex = -1;

            Object[] elems;
            do {
                elems = ReadableHashSet.this.hashtable[++outerArrayIndex];
            } while (elems == null || elems.length == 0);
        }
    }
}
