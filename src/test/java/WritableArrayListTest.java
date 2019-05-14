import org.junit.Test;

import javax.annotation.Nullable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WritableArrayListTest<T extends WritableArrayList<Integer>> extends ReadableArrayListTest<T> implements WritableContainerTest {
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
    @Override
    public void testAdd() {
        final T list1 = this.makeContainer();
        list1.add(1);

        assertFalse(list1.isEmpty());
        assertEquals(1, list1.size());
        assertTrue(list1.contains(1));

        list1.add(1);

        assertEquals(2, list1.size());

        final T list2 = this.makeContainer(1, 1);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testResize() {
        final T list1 = this.makeContainer(2);
        final int initialHashCode = list1.hashCode();

        list1.add(1);

        assertEquals(2, list1.size());
        assertTrue(list1.contains(2));
        assertTrue(list1.contains(1));
        assertNotEquals(initialHashCode, list1.hashCode());

        final T list2 = this.makeContainer(2, 1);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testIteratorRemove() {
        final T list1 = this.makeContainer(1, 2, 3);
        final WritableIterator<Integer> iter = list1.iterator();

        iter.next();
        iter.next();
        iter.remove();

        assertTrue(iter.hasNext());
        assertEquals(Integer.valueOf(3), iter.next());
        assertFalse(iter.hasNext());

        final T list2 = this.makeContainer(1, 3);

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }

    @Test
    @Override
    public void testClear() {
        final T list1 = this.makeContainer(1, 2, 3);

        list1.clear();

        assertTrue(list1.isEmpty());
        assertFalse(list1.contains(1));
        assertFalse(list1.contains(2));
        assertFalse(list1.contains(3));

        final T list2 = this.makeContainer();

        assertEquals(list2, list1);
        assertEquals(list2.hashCode(), list1.hashCode());
    }
}
