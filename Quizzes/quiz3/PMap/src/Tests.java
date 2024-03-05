import static org.junit.Assert.*;

import org.junit.Test;

import java.util.ArrayList;

public class Tests {
    @Test
    public void clearMapTest(){
        PMap test = new PMap();
        test.put(213, 31010293);
        test.clearMap();
        assertEquals(0, test.size());
    }
    @Test
    public void containsKeyTest(){
        PMap test = new PMap(213,17);
        assertTrue(test.containsKey(213));
    }

}
