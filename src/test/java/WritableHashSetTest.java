import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WritableHashSetTest<T extends WritableHashSet<Integer>> extends ReadableHashSetTest<T> implements WritableContainerTest {
    @Override
    protected T makeContainer() {
        //noinspection unchecked
        return (T) new WritableHashSet<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        //noinspection unchecked
        return (T) new WritableHashSet<>(elem);
    }

    @Override
    protected T makeContainer(final Integer... elems) {
        //noinspection unchecked
        return (T) new WritableHashSet<>(elems);
    }

    @Test
    public void testAdd() {
        final T set1 = this.makeContainer();
        set1.add(1);

        assertFalse(set1.isEmpty());
        assertEquals(1, set1.size());
        assertTrue(set1.contains(1));

        set1.add(1);

        assertEquals(1, set1.size());

        final T set2 = this.makeContainer(1);

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    public void testResize() {
        final T set1 = this.makeContainer(1);
        final int initialHashCode = set1.hashCode();

        set1.add(2);

        assertEquals(2, set1.size());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertNotEquals(initialHashCode, set1.hashCode());

        final T set2 = this.makeContainer(1, 2);

        assertEquals(set1, set2);
        assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    public void testIteratorRemove() {
        final T set1 = this.makeContainer(1, 2, 3);
        final WritableIterator<Integer> iter = set1.iterator();

        iter.next();
        iter.next();
        iter.remove();

        assertTrue(iter.hasNext());
        assertEquals(Integer.valueOf(3), iter.next());
        assertFalse(iter.hasNext());

        final T set2 = this.makeContainer(1, 3);

        assertEquals(set2, set1);
        assertEquals(set1.hashCode(), set2.hashCode());
    }

    @Test
    @Override
    public void testClear() {
        final T set1 = this.makeContainer(1, 2, 3);

        set1.clear();

        assertTrue(set1.isEmpty());
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(2));
        assertFalse(set1.contains(3));

        final T set2 = this.makeContainer();

        assertEquals(set2, set1);
        assertEquals(set2.hashCode(), set1.hashCode());
    }
}
