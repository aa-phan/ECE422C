package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class SortToolsTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    @Test
    public void isSortedTest1() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int n = x.length;
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void isSortedTest2() {
        int[] x = new int[] { 1123, 32, 1, 3, 5 };
        int n = x.length;
        assertEquals(false, SortTools.isSorted(x, n));
    }

    @Test
    public void isSortedTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int n = 0;
        assertEquals(false, SortTools.isSorted(x, n));
    }

    @Test
    public void findTest1() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int n = 4;
        int v = 2;
        assertEquals(1, SortTools.find(x, n, v));
        assertArrayEquals(original, x);
    }

    @Test
    public void findTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int n = 4;
        int v = 10;
        assertEquals(-1, SortTools.find(x, n, v));
        assertArrayEquals(original, x);
    }

    @Test
    public void findTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int n = 4;
        int v = 5;
        assertEquals(-1, SortTools.find(x, n, v));
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertTest1() {
        int[] x = new int[] { 1, 2, 3, 4, 5, 6 };
        int[] original = x.clone();
        int[] solution = new int[] { -1, 1, 2, 3, 4 };
        int n = 4;
        int v = -1;
        assertArrayEquals(solution, SortTools.copyAndInsert(x, n, v));
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(SortTools.copyAndInsert(x, n, v), n + 1));

    }

    @Test
    public void copyAndInsertTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int[] solution = new int[] { 1, 2, 3, 4 };
        int n = 4;
        int v = 2;
        assertArrayEquals(solution, SortTools.copyAndInsert(x, n, v));
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(SortTools.copyAndInsert(x, n, v), n));
    }

    @Test
    public void copyAndInsertTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int[] solution = new int[] { 1, 2, 3, 4, 5 };
        int n = x.length;
        int v = 5;
        assertArrayEquals(solution, SortTools.copyAndInsert(x, n, v));
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(SortTools.copyAndInsert(x, n, v), n + 1));

    }
    /**
     * @Test
     *       public void InsertInPlaceTest1() {
     *       int[] x = new int[] { 1, 2, 3, 4, 5 };
     *       int n = 4;
     *       int v = 2;
     *       assertEquals(4, SortTools.insertInPlace(x, n, v));
     *       assertEquals(true, SortTools.isSorted(x, 4));
     *       }
     * 
     * @Test
     *       public void InsertInPlaceTest2() {
     *       int[] x = new int[] { 1, 2, 3, 4, 7 };
     *       int n = 4;
     *       int v = 6;
     *       assertEquals(5, SortTools.insertInPlace(x, n, v));
     *       assertEquals(true, SortTools.isSorted(x, 5));
     *       }
     * 
     * @Test
     *       public void InsertInPlaceTest3() {
     *       int[] x = new int[] { 1, 2, 3, 4, 5 };
     *       int n = 5;
     *       int v = 3;
     *       assertEquals(2, SortTools.insertInPlace(x, n, v));
     *       assertEquals(true, SortTools.isSorted(x, n));
     *       }
     * 
     * @Test
     *       public void insertSortTest1() {
     *       int[] x = new int[] { 123, 13, 45, 2, 5 };
     *       int n = x.length;
     *       SortTools.insertSort(x, n);
     *       assertEquals(true, SortTools.isSorted(x, n));
     *       }
     * 
     * @Test
     *       public void insertSortTest2() {
     *       int[] x = new int[] { 1, 2, 3, 4, 5 };
     *       int n = x.length;
     *       SortTools.insertSort(x, n);
     *       assertEquals(true, SortTools.isSorted(x, n));
     *       }
     * 
     * @Test
     *       public void insertSortTest3() {
     *       int[] x = new int[] { 123, 13, 45, 2, 5 };
     *       int n = 0;
     *       SortTools.insertSort(x, n);
     *       assertEquals(false, SortTools.isSorted(x, n));
     *       }
     */
}
