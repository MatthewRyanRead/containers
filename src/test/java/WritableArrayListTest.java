import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WritableArrayListTest<T extends WritableArrayList<Integer>> extends ReadableArrayListTest<T> {
    @Override
    protected T makeContainer() {
        //noinspection unchecked
        return (T) new WritableArrayList<Integer>();
    }

    @Override
    protected T makeContainer(@Nullable final Integer elem) {
        //noinspection unchecked
        return (T) new WritableArrayList<>(elem);
    }

    @Override
    protected T makeContainer(final Integer... elems) {
        //noinspection unchecked
        return (T) new WritableArrayList<>(elems);
    }

    @Test
    public void testCompare() {
        T list1 = this.makeContainer(1, 2, 3);
        T list2 = this.makeContainer(3, 2, 3, 1);

        assertNotEquals(list1.size(), list2.size());

        assertTrue(list1.containsAll(list2));
        assertTrue(list2.containsAll(list1));

        assertNotEquals(list1, list2);
        assertNotEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void testAdd() {
        final T list1 = this.makeContainer();
        list1.add(1);

        assertFalse(list1.isEmpty());
        assertEquals(1, list1.size());
        assertTrue(list1.contains(1));

        list1.add(1);

        assertEquals(2, list1.size());

        final T list2 = this.makeContainer(1, 1);

        assertEquals(list1, list2);
        assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    public void testResize() {
        final T list1 = this.makeContainer(2);
        final int initialHashCode = list1.hashCode();

        list1.add(1);

        assertEquals(2, list1.size());
        assertTrue(list1.contains(2));
        assertTrue(list1.contains(1));
        assertNotEquals(initialHashCode, list1.hashCode());

        final T list2 = this.makeContainer(2, 1);

        assertEquals(list1, list2);
        assertEquals(list1.hashCode(), list2.hashCode());
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
}
