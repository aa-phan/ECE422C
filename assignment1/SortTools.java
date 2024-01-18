package assignment1;

import java.util.Arrays;

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
            return false;
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
            newX = Arrays.copyOf(newX, n+1);
            newX[n] = v; // insert at end of array

            insertSort(newX, n + 1);
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
        } else { // target doesn't exist, make new array up to n elements, include v, sort
            x = copyAndInsert(x, n, v);
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
            if(x[i] > x[i+1]){
                int j = i + 1;
                while(j>0 && x[j] < x[j-1]){
                    int hold = x[j];
                    x[j] = x[j-1];
                    x[j-1] = hold;
                    j--;
                }
            }
        }
    }
}
