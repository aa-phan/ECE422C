package studenttest;

import unittest.annotations.Order;
import unittest.annotations.Ordered;
import unittest.annotations.Test;
import unittest.assertions.Assert;

@Ordered
public class TestD {
    @Order(1)
    @Test
    public void test9() {
        Assert.assertEquals(9, 9 + 0);
    }

    public void THISISNOTATEST() {
        throw new RuntimeException("HEHE YOUR CODE BROKE");
    }

    @Order(1)
    @Test
    public void test8() {
        Assert.assertEquals(true, true);
    }
}
