package assignment1.src;

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
        int[] original = x.clone();
        int n = x.length;
        assertEquals(true, SortTools.isSorted(x, n));
        assertArrayEquals(original, x);
    }

    @Test
    public void isSortedTest2() {
        int[] x = new int[] { 1123, 32, 1, 3, 5 };
        int[] original = x.clone();
        int n = x.length;
        assertEquals(false, SortTools.isSorted(x, n));
        assertArrayEquals(original, x);

    }

    @Test
    public void isSortedTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int n = 0;
        assertEquals(false, SortTools.isSorted(x, n));
        assertArrayEquals(original, x);

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
    public void findTest4() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int n = 5;
        int v = 10;
        assertEquals(-1, SortTools.find(x, n, v));
        assertArrayEquals(original, x);
    }

    @Test
    public void findTest5() {
        int[] x = new int[] { 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68, 72, 76, 80, 84, 88,
                92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144, 148, 152, 156, 160, 164, 168, 172,
                176, 180, 184, 188, 192, 196, 200, 204, 208, 212, 216, 220, 224, 228, 232, 236, 240, 244, 248, 252, 256,
                260, 264, 268, 272, 276, 280, 284, 288, 292, 296, 300, 304, 308, 312, 316, 320, 324, 328, 332, 336, 340,
                344, 348, 352, 356, 360, 364, 368, 372, 376, 380, 384, 388, 392, 396 };
        int[] original = x.clone();
        int n = x.length;
        int v = 8;
        assertEquals(2, SortTools.find(x, n, v));
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertTest1() {
        int[] x = new int[] { 1, 2, 3, 4, 5, 6 };
        int[] original = x.clone();
        int[] solution = new int[] { -1, 1, 2, 3, 4 };
        int n = 4;
        int v = -1;
        int[] output = SortTools.copyAndInsert(x, n, v);
        assertArrayEquals(solution, output);
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(output, output.length));

    }

    @Test
    public void copyAndInsertTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int[] solution = new int[] { 1, 2, 3, 4 };
        int n = 4;
        int v = 2;
        int[] output = SortTools.copyAndInsert(x, n, v);
        assertArrayEquals(solution, output);
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(output, output.length));
    }

    @Test
    public void copyAndInsertTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int[] original = x.clone();
        int[] solution = new int[] { 1, 2, 3, 4, 5 };
        int n = x.length;
        int v = 5;
        int[] output = SortTools.copyAndInsert(x, n, v);
        assertArrayEquals(solution, output);
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(output, output.length));

    }

    @Test
    public void copyAndInsertTest4() {
        int[] x = new int[] { -100, -96, -92, -88, -84, -80, -76, -72, -68, -64, -60, -56, -52, -48, -44, -40, -36, -32,
                -28, -24, -20, -16, -12, -8, -4, 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68,
                72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144, 148, 152, 156,
                160, 164, 168, 172, 176, 180, 184, 188, 192, 196, 200, 204, 208, 212, 216, 220, 224, 228, 232, 236, 240,
                244, 248, 252, 256, 260, 264, 268, 272, 276, 280, 284, 288, 292, 296 };
        int[] original = x.clone();
        int[] solution = new int[] { -100, -96, -92, -88, -84, -80, -76, -72, -68, -64, -60, -56, -52, -48, -44, -40,
                -36, -32, -28, -24, -20, -16, -12, -8, -4, 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60,
                64, 68, 72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 123, 124, 128, 132, 136, 140, 144,
                148, 152, 156, 160, 164, 168, 172, 176, 180, 184, 188, 192, 196, 200, 204, 208, 212, 216, 220, 224, 228,
                232, 236, 240, 244, 248, 252, 256, 260, 264, 268, 272, 276, 280, 284, 288, 292, 296 };
        int n = x.length;
        int v = 123;
        int[] output = SortTools.copyAndInsert(x, n, v);
        assertArrayEquals(solution, output);
        assertArrayEquals(original, x);
        assertEquals(true, SortTools.isSorted(output, output.length));

    }

    @Test
    public void InsertInPlaceTest1() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int n = 4;
        int v = 2;
        assertEquals(n, SortTools.insertInPlace(x, n, v));
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void InsertInPlaceTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 7 };
        int n = 4;
        int v = 6;
        assertEquals(n + 1, SortTools.insertInPlace(x, n, v));
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void InsertInPlaceTest3() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int n = 5;
        int v = 3;
        assertEquals(n, SortTools.insertInPlace(x, n, v));
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void InsertInPlaceTest4() {
        int[] x = new int[] { -100, -96, -92, -88, -84, -80, -76, -72, -68, -64, -60, -56, -52, -48, -44, -40, -36, -32,
                -28, -24, -20, -16, -12, -8, -4, 0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60, 64, 68,
                72, 76, 80, 84, 88, 92, 96, 100, 104, 108, 112, 116, 120, 124, 128, 132, 136, 140, 144, 148, 152, 156,
                160, 164, 168, 172, 176, 180, 184, 188, 192, 196, 200, 204, 208, 212, 216, 220, 224, 228, 232, 236, 240,
                244, 248, 252, 256, 260, 264, 268, 272, 276, 280, 284, 288, 292, 296 };
        int n = x.length;
        int v = 3;
        assertEquals(n + 1, SortTools.insertInPlace(x, n, v));
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void insertSortTest1() {
        int[] x = new int[] { 123, 13, 45, 2, 5 };
        int n = x.length;
        SortTools.insertSort(x, n);
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void insertSortTest2() {
        int[] x = new int[] { 1, 2, 3, 4, 5 };
        int n = x.length;
        SortTools.insertSort(x, n);
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void insertSortTest3() {
        int[] x = new int[] { 123, 13, 45, 2, 5 };
        int n = 1;
        SortTools.insertSort(x, n);
        assertEquals(true, SortTools.isSorted(x, n));
    }

    @Test
    public void insertSortTest4() {
        int[] x = new int[] { 86, 52, 13, 79, 56, 76, 85, 29, 16, 47, 95, 80, 33, 24, 84, 81, 18, 45, 74, 63, 10, 98,
                85, 60, 92, 77, 60, 37, 53, 32, 64, 12, 58, 18, 4, 16, 44, 82, 22, 99, 1, 58, 82, 2, 28, 65, 27, 51, 62,
                57, 56, 44, 71, 15, 27, 88, 82, 9, 74, 4, 69, 95, 12, 17, 70, 72, 74, 34, 43, 28, 42, 79, 90, 56, 37,
                29, 21, 35, 45, 58, 38, 34, 51, 7, 43, 12, 60, 37, 19, 64, 54, 55, 94, 19, 2, 33, 39, 57, 42, 49 };
        int n = x.length;
        SortTools.insertSort(x, n);
        assertEquals(true, SortTools.isSorted(x, n));
    }

}
