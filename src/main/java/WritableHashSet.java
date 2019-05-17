import javax.annotation.Nullable;

public class WritableHashSet<E> extends ReadableHashSet<E> implements WritableContainer<E> {
    protected static final float DEFAULT_GROWTH_FACTOR = 2.0f;
    // TODO: Support provided growth factors
    protected float growthFactor = DEFAULT_GROWTH_FACTOR;

    public WritableHashSet() {
        super();
    }

    public WritableHashSet(@Nullable final E e) {
        super(e);
    }

    @SafeVarargs
    public WritableHashSet(final E... elems) {
        super(elems);
    }

    public WritableHashSet(final Container<E> other) {
        super(other);
    }

    @Override
    public boolean add(@Nullable final E e) {
        if (this.contains(e)) {
            return false;
        }

        if (e == null) {
            this.containsNull = true;
            this.cachedHashCode = null;
            return true;
        }

        if (this.isEmpty()) {
            this.resize();
        }

        final boolean result = this.addToTable(this.hashtable, e);

        if (result) {
            this.size++;
            this.cachedHashCode = null;

            if (this.size() / (double) this.hashtable.length > this.maxLoadFactor) {
                this.resize();
            }
        }

        return result;
    }

    protected void resize() {
        final int newSize = this.hashtable.length == 0 ? 4 : (int)(this.hashtable.length * this.growthFactor);
        final Object[][] newHashtable = new Object[newSize][];

        for (final Object[] array : this.hashtable) {
            if (array == null) {
                continue;
            }

            for (final Object elem : array) {
                //noinspection unchecked
                this.addToTable(newHashtable, (E) elem);
            }
        }

        this.hashtable = newHashtable;
    }

    /**
     * @param table Must not be zero-length
     */
    protected int getIndex(final Object[][] table, final E e) {
        return e.hashCode() % table.length;
    }

    /**
     * @param table Must not be zero-length
     */
    protected boolean addToTable(final Object[][] table, final E e) {
        final int index = this.getIndex(table, e);

        Object[] array = table[index];
        if (array == null) {
            array = new Object[]{e};
        }
        else {
            final Object[] newArray = new Object[array.length + 1];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;

            array[array.length - 1] = e;
        }

        table[index] = array;

        return true;
    }

    @Override
    public boolean remove(@Nullable final E e) {
        if (e == null) {
            if (this.containsNull) {
                this.containsNull = false;
                this.size--;
                this.cachedHashCode = null;

                return true;
            }

            return false;
        }

        final int index = this.getIndex(e);
        final Object[] array = this.hashtable[index];
        if (array == null) {
            return false;
        }

        for (int i = 0; i < array.length; i++) {
            if (e.equals(array[i])) {
                final Object[] newArray = new Object[array.length - 1];
                System.arraycopy(array, 0, newArray, 0, i);
                System.arraycopy(array, i+1, newArray, i, array.length - i - 1);
                this.hashtable[index] = newArray;

                this.size--;
                this.cachedHashCode = null;
                return true;
            }
        }

        return false;
    }

    @Override
    public void clear() {
        if (this.isEmpty()) {
            return;
        }

        for (int i = 0; i < this.hashtable.length; i++) {
            this.hashtable[i] = null;
        }
        this.size = 0;
        this.cachedHashCode = null;
    }

    protected class WritableHashSetIterator extends ReadableHashSetIterator implements WritableIterator<E> {
        protected boolean canRemove = false;

        @Nullable
        @Override
        public E next() throws IllegalStateException {
            E elem = super.next();
            this.canRemove = true;

            return elem;
        }

        @Override
        public void remove() throws IllegalStateException {
            if (!this.canRemove) {
                throw new IllegalStateException("No previously successful call to next()");
            }

            this.canRemove = false;
            //noinspection unchecked
            WritableHashSet.this.remove((E) WritableHashSet.this.hashtable[outerArrayIndex][innerArrayIndex--]);
            this.currIndex--;
        }
    }

    @Override
    public WritableIterator<E> iterator() {
        return new WritableHashSetIterator();
    }
}
