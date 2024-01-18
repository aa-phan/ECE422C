package assignment1;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.rules.Timeout;

import scoreannotation.Score;
import testutils.NoExitSecurityManager;


public class SortToolsTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);

    static SecurityManager initialSecurityManager;

    /**
     * Disallow System.exit() with a SecurityManager. Mock out Arrays and
     * Collection.
     */
    @BeforeClass
    public static void setup() {
        SampleTest.initialSecurityManager = System.getSecurityManager();
        System.setSecurityManager(new NoExitSecurityManager());
    }

    /**
     * Undo setup().
     */
    @AfterClass
    public static void cleanup() {
        System.setSecurityManager(SampleTest.initialSecurityManager);
    }

    @Test
    public void sampleTest() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = x.length;

        assertEquals(2, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void sampleTest1() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 1;

        assertEquals(-1, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void sampleTest2() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 1;

        assertEquals(-1, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void sampleTest3() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 1;

        assertEquals(-1, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void sampleTest4() {
        int[] x = new int[]{-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();
        int n = 1;

        assertEquals(-1, SortTools.find(x, n, -3));
        assertArrayEquals(original, x);
    }
}

