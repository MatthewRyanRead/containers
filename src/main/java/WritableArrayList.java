import javax.annotation.Nullable;

/**
 * A {@link ReadableArrayList} that also supports the addition and removal of elements.
 * The backing array grows when needed (doubling in size by default).
 *
 * @param <E> The type of elements contained
 */
@SuppressWarnings("unused")
public class WritableArrayList<E> extends ReadableArrayList<E> implements WritableList<E> {
    protected static final int DEFAULT_INITIAL_SIZE = 4;
    protected static final float DEFAULT_GROWTH_RATE = 2.0f;

    protected final float growthRate;

    protected int currMaxIndex;

    public WritableArrayList() {
        super();
        this.growthRate = DEFAULT_GROWTH_RATE;
        this.currMaxIndex = -1;
    }

    public WritableArrayList(@Nullable final E e) {
        super(e);
        this.growthRate = DEFAULT_GROWTH_RATE;
        this.currMaxIndex = 0;
    }

    @SafeVarargs
    public WritableArrayList(final E... elems) {
        super(elems);
        this.growthRate = DEFAULT_GROWTH_RATE;
        this.currMaxIndex = elems.length - 1;
    }

    public WritableArrayList(@Nullable final Container<E> container) {
        super(container);
        this.growthRate = DEFAULT_GROWTH_RATE;
        this.currMaxIndex = this.array.length - 1;
    }

    // TODO constructors that can provide initial size

    @Nullable
    @Override
    public E removeAt(final int index) {
        this.checkIndex(index);
        final Object elem = this.array[index];

        //noinspection ManualArrayCopy
        for (int i = index; i < this.currMaxIndex; i++) {
            this.array[i] = this.array[i+1];
        }
        this.array[currMaxIndex--] = null;

        //noinspection unchecked
        return (E) elem;
    }

    protected final void checkIndex(final int index) {
        if (index >= this.array.length || index < 0) {
            throw new IndexOutOfBoundsException("Size: " + this.array.length + " Index: " + index);
        }
    }

    @Override
    public void set(final int index, @Nullable final E e) {
        this.checkIndex(index);
        this.array[index] = e;
    }

    @Override
    public boolean add(@Nullable final E e) {
        if (this.array.length == this.currMaxIndex + 1) {
            this.growArray();
        }

        this.array[++this.currMaxIndex] = e;
        return true;
    }

    protected void growArray() {
        final int newSize = this.size() == 0 ? DEFAULT_INITIAL_SIZE : (int)(this.array.length * this.growthRate);

        final Object[] newArray = new Object[newSize];
        System.arraycopy(this.array, 0, newArray, 0, this.array.length);
        this.array = newArray;
    }

    @Override
    public boolean remove(@Nullable final E e) {
        return false;
    }

    @Override
    public void clear() {
        this.array = new Object[this.array.length];
        this.currMaxIndex = -1;
    }

    @Override
    public int size() {
        return this.currMaxIndex + 1;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < currMaxIndex; i++) {
            final Object elem = this.array[i];
            result = result*31 + (elem == null ? 0 : elem.hashCode());
        }

        return result;
    }

    @Override
    public boolean equals(@Nullable final Object obj) {
        if (!(obj instanceof ReadableArrayList)) {
            return false;
        }

        final ReadableArrayList that = ((ReadableArrayList) obj);
        if (this.size() != that.size()) {
            return false;
        }

        for (int i = 0; i < this.currMaxIndex; i++) {
            if (this.array[i] == null) {
                if (that.array[i] != null) {
                    return false;
                }
            }
            else if (!this.array[i].equals(that.array[i])) {
                return false;
            }
        }

        return true;
    }

    protected class WritableArrayListIterator<T extends WritableArrayList<E>> extends ReadableArrayListIterator<T> implements WritableIterator<E> {
        protected WritableArrayListIterator(final T list) {
            super(list);
        }

        @Override
        public void remove() throws IllegalStateException {
            this.list.removeAt(--this.currIndex);
        }
    }

    @Override
    public WritableIterator<E> iterator() {
        return new WritableArrayListIterator<>(this);
    }
}
