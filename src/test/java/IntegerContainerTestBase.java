import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.annotation.Nullable;
import org.junit.Test;

// TODO: Generalize this to any contained type
public abstract class IntegerContainerTestBase<T extends Container<Integer>> {
    protected abstract T makeContainer();

    protected abstract T makeContainer(@Nullable final Integer elem);

    protected abstract T makeContainer(final Integer... elems);

    protected abstract T makeContainer(final Container<Integer> other);

    @Test
    public void testEmpty() {
        final T container1 = this.makeContainer();

        assertTrue(container1.isEmpty());
        assertEquals(0, container1.size());
        //noinspection ConstantConditions, SimplifiableJUnitAssertion
        assertFalse(container1.equals(null));

        final T container2 = this.makeContainer();

        assertEquals(container1, container2);
        assertEquals(container1.hashCode(), container2.hashCode());
    }

    @Test
    public void testReadAndContains() {
        final T container = this.makeContainer(1, 2, 3);

        assertFalse(container.isEmpty());
        assertEquals(3, container.size());

        assertTrue(container.contains(1));
        assertTrue(container.contains(2));
        assertTrue(container.contains(3));

        assertFalse(container.contains(4));

        assertTrue(container.containsAll(container));

        final ReadableIterator<Integer> iter = container.iterator();
        for (int i = 0; i < container.size(); i++) {
            assertTrue(iter.hasNext());
            assertEquals(Integer.valueOf(i + 1), iter.next());
        }

        assertFalse(iter.hasNext());
        try {
            iter.next();
        } catch (IllegalStateException e) {
            return;
        }

        fail("Should never get here");
    }

    @Test
    public void testCopyConstructor() {
        final T container1 = this.makeContainer(1, 2, 3);
        final T container2 = this.makeContainer(container1);

        assertEquals(container1, container2);
        assertEquals(container1.hashCode(), container2.hashCode());
    }
}
