import org.junit.Test;
import static org.junit.Assert.*;

public class SelectionSortTest {

    @Test
    public void sortTest(){
        int[] a = new int[]{2,1,3,4};
        int[] sol = new int[]{1,2,3,4};
        SelectionSort.sort(a);
        assertArrayEquals(a,sol);
    }
    @Test
    public void sortTest1(){
        int[] a = new int[]{-123,0,-1000,500,123};
        int[] sol = new int[]{-1000,-123,0,123,500};
        SelectionSort.sort(a);
        assertArrayEquals(a,sol);
    }
}