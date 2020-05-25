package tech.read_only.containers;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;
import javax.annotation.Nullable;
import org.junit.Test;

/**
 * Base tests applying to all {@link Container}s.
 *
 * @param <T> the class to test
 * @param <E> the type of element contained by the test class
 */
public abstract class ContainerTestBase<E, T extends Container<E>> {
    protected abstract T makeContainer();

    protected abstract T makeContainer(@Nullable final E elem);

    protected abstract T makeContainer(final Object... elems);

    protected abstract T makeContainer(final Container<E> other);

    /**
     * @return An element distinct from all (i.e., not equal to any) previously generated elements.
     */
    protected abstract E generateElement();

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
        final E elem1 = this.generateElement();
        final E elem2 = this.generateElement();
        final E elem3 = this.generateElement();
        final T container = this.makeContainer(elem1, elem2, elem3);

        assertFalse(container.isEmpty());
        assertEquals(3, container.size());

        assertTrue(container.contains(elem1));
        assertTrue(container.contains(elem1));
        assertTrue(container.contains(elem1));

        assertFalse(container.contains(this.generateElement()));
        assertFalse(container.contains(null));

        assertTrue(container.containsAll(container));
        assertFalse(container.containsAll(this.makeContainer(this.generateElement())));

        //noinspection unchecked
        final HashSet<E> elems = newHashSet(elem1, elem2, elem3);

        final ReadableIterator<E> iter = container.iterator();
        for (int i = 0; i < container.size(); i++) {
            assertTrue(iter.hasNext());
            assertTrue(elems.remove(iter.next()));
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
        final T container1 =
                this.makeContainer(
                        this.generateElement(), this.generateElement(), this.generateElement());
        final T container2 = this.makeContainer(container1);

        assertEquals(container1, container2);
        assertEquals(container1.hashCode(), container2.hashCode());

        assertEquals(this.makeContainer(), this.makeContainer((T) null));
    }

    @Test
    public void testEqualsClass() {
        final T containerToTest =
                this.makeContainer(
                        this.generateElement(), this.generateElement(), this.generateElement());
        final Object otherContainer =
                containerToTest.getClass() == ReadableArrayList.class
                        ? new ReadableHashSet<>(containerToTest)
                        : new ReadableArrayList<>(containerToTest);

        assertNotEquals(containerToTest, otherContainer);
    }

    @Test
    public void testNotEquals() {
        final T container1 =
                this.makeContainer(
                        this.generateElement(), this.generateElement(), this.generateElement());

        final T container2 =
                this.makeContainer(
                        this.generateElement(), this.generateElement(), this.generateElement());

        assertNotEquals(container1, container2);
        // not strictly guaranteed, but should be true here
        assertNotEquals(container1.hashCode(), container2.hashCode());
    }

    @Test
    public void testToArray() {
        final T container =
                this.makeContainer(
                        this.generateElement(), this.generateElement(), this.generateElement());

        final E[] array = container.toArray();
        assertEquals(container.size(), array.length);

        final ReadableIterator<E> iter = container.iterator();
        for (int i = 0; i < container.size(); i++) {
            assertEquals(iter.next(), array[i]);
        }
    }

    @Test
    public void testNull() {
        final T nullContainer = this.makeContainer((E) null);

        final T duplicateContainer = this.makeContainer(nullContainer);
        assertEquals(nullContainer, duplicateContainer);
        assertEquals(nullContainer.hashCode(), duplicateContainer.hashCode());
        assertNotEquals(0, nullContainer.hashCode());

        assertFalse(nullContainer.isEmpty());
        final ReadableIterator<E> iter = nullContainer.iterator();
        assertTrue(iter.hasNext());
        assertNull(iter.next());
        assertFalse(iter.hasNext());

        assertTrue(nullContainer.contains(null));
        assertTrue(nullContainer.containsAll(duplicateContainer));

        assertEquals(new Object[] {null}, nullContainer.toArray());

        final T mixedContainer = this.makeContainer(this.generateElement(), null);
        assertTrue(mixedContainer.containsAll(nullContainer));
        assertFalse(nullContainer.containsAll(mixedContainer));
    }
}
