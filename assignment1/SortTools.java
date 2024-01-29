package assignment1;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;

import static org.junit.Assert.*;

public class SortTools {
    /**
     * Return whether the first n elements of x are sorted in non-decreasing
     * order.
     * 
     * @param x is the array
     * @param n is the size of the input to be checked
     * @return true if first n elements of array is sorted
     */
    public static boolean isSorted(int[] x, int n) {
        if (n == 0)
            return true;
        for (int i = 0; i < n - 1; i++) {
            if (x[i] > x[i + 1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return an index of value v within the first n elements of x.
     * 
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be searched for
     * @return any index k such that k < n and x[k] == v, or -1 if no such k exists
     */

    public static int find(int[] x, int n, int v) {
        int mid = n / 2; // start at the middle of the array
        int high = n - 1, low = 0;
        while (n > 0) {
            if (x[mid] < v) { // if the middle is less than the target, go to the right half
                low = mid + 1;
                mid = (high + low) / 2;
            } else if (x[mid] > v) { // if the middle is greater, go to the left half
                high = mid - 1;
                mid = (high + low) / 2;
            } else { // if the target is found return the index
                return mid;
            }

            n /= 2; // each time the # of elements to be searched is halved, making O(log(n))
        }
        return -1;
    }

    /**
     * Return a sorted, newly created array containing the first n elements of x
     * and ensuring that v is in the new array.
     * 
     * @param x is the array
     * @param n is the number of elements to be copied from x
     * @param v is the value to be added to the new array if necessary
     * @return a new array containing the first n elements of x as well as v
     */
    public static int[] copyAndInsert(int[] x, int n, int v) {
        if (find(x, n, v) == -1) { // if the target doesn't exist, add it by making new array
            int[] newX = Arrays.copyOf(x, n);
            newX = Arrays.copyOf(newX, n + 1);
            newX[n] = v; // insert at end of array



            insertSort(newX, newX.length);
            newX = Arrays.copyOf(newX, newX.length);

            return newX;
        }

        else { // the target does exist, so return a copy of the original array up to n
               // elements
            return Arrays.copyOf(x, n);
        }
    }

    /**
     * Insert the value v in the first n elements of x if it is not already
     * there, ensuring those elements are still sorted.
     * 
     * @param x is the array
     * @param n is the number of elements in the array
     * @param v is the value to be added
     * @return n if v is already in x, otherwise returns n+1
     */
    public static int insertInPlace(int[] x, int n, int v) {
        if (find(x, n, v) != -1) { // the target exists already, return the original n
            return n;
        }

        else { // target doesn't exist, make new array up to n elements, include v, sort
            if(x.length == n) {
                x[n - 1] = v;
                insertSort(x, n);
            }
            else{
                x[n] = v;
                insertSort(x, n + 1);
            }
            return n + 1;
        }
    }

    /**
     * Sort the first n elements of x in-place in non-decreasing order using
     * insertion sort.
     * 
     * @param x is the array to be sorted
     * @param n is the number of elements of the array to be sorted
     */
    public static void insertSort(int[] x, int n) {
        for (int i = 0; i < n - 1; i++) {
            if (x[i] > x[i + 1]) {
                int j = i + 1;
                while (j > 0 && x[j] < x[j - 1]) {
                    int hold = x[j];
                    x[j] = x[j - 1];
                    x[j - 1] = hold;
                    j--;
                }
            }
        }
    }

    public static class SortToolsTest_chen {
        @Rule
        public Timeout globalTimeout = Timeout.seconds(2);

        @Test
        public void isSorted1() {
            int[] x = new int[]{0, 1, 2, 3, 4};
            int[] old = new int[]{0, 1, 2, 3, 4};
            int n = x.length;
            assertTrue(isSorted(x, n));
            assertArrayEquals(old, x);
        }

        @Test
        public void isSorted2() {
            int[] x = new int[]{};
            int[] old = new int[]{};
            assertTrue(isSorted(x, 0));
            assertArrayEquals(x, old);
        }

        @Test
        public void isSorted3() {
            int[] x = new int[]{5, 4, 3, 2, 1};
            int[] old = new int[]{5, 4, 3, 2, 1};
            assertFalse(isSorted(x, 5));
            assertArrayEquals(x, old);
        }

        @Test
        public void find1() {
            int[] x = new int[]{1, 2, 3, 4, 5};
            int[] old = new int[]{1, 2, 3, 4, 5};
            assertEquals(2, find(x, 5, 3));
            assertArrayEquals(x, old);
        }

        @Test
        public void find2() {
            int[] x = new int[]{1, 2, 3, 4, 5};
            int[] old = new int[]{1, 2, 3, 4, 5};
            assertEquals(-1, find(x, 5, 6));
            assertArrayEquals(x, old);
        }

        @Test
        public void find3() {
            int[] x = new int[]{1, 2, 3, 4, 5};
            int[] old = new int[]{1, 2, 3, 4, 5};
            assertEquals(-1, find(x, 1, 5));
            assertArrayEquals(x, old);
        }

        @Test
        public void copy1() {
            int[] x = new int[]{1, 3, 5, 7};
            int[] old = new int[]{1, 3, 5, 7};
            int[] y = new int[]{1, 3, 5, 10};
            assertArrayEquals(y, copyAndInsert(x, 3, 10));
            assertArrayEquals(x, old);
        }

        @Test
        public void copy2() {
            int[] x = new int[]{1, 3, 5, 7};
            int[] old = new int[]{1, 3, 5, 7};
            int[] y = new int[]{1, 3, 4, 5};
            assertArrayEquals(y, copyAndInsert(x, 3, 4));
            assertArrayEquals(x, old);
        }

        @Test
        public void copy3() {
            int[] x = new int[]{1, 3, 5, 7};
            int[] old = new int[]{1, 3, 5, 7};
            int[] y = new int[]{1, 3, 5, 7, 10};
            assertArrayEquals(y, copyAndInsert(x, 4, 10));
            assertArrayEquals(x, old);
        }

        @Test
        public void insertIn1() {
            int[] x = new int[]{1, 3, 5, 7, 9, 11};
            assertEquals(5, insertInPlace(x, 4, 2));
            assertTrue(isSorted(x, 5));
        }

        @Test
        public void insertIn2() {
            int[] x = new int[]{1, 3, 5, 7, 9, 11};
            assertEquals(4, insertInPlace(x, 4, 3));
            assertTrue(isSorted(x, 4));
        }

        @Test
        public void insertIn3() {
            int[] x = new int[]{1, 3, 5, 7, 9, 11};
            assertEquals(5, insertInPlace(x, 4, 13));
            assertTrue(isSorted(x, 5));
        }

        @Test
        public void insertSort1() {
            int[] x = new int[]{7, 3, 1, 3};
            insertSort(x, 4);
            assertTrue(isSorted(x, 4));
        }

        @Test
        public void insertSort2() {
            int[] x = new int[]{1, 3, 3, 7};
            insertSort(x, 4);
            assertTrue(isSorted(x, 4));
        }

        @Test
        public void insertSort3() {
            int[] x = new int[]{7, 3, 1, 3};
            insertSort(x, 3);
            assertTrue(isSorted(x, 3));
        }
    }
}
