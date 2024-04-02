package studenttest;

import unittest.annotations.Order;
import unittest.annotations.Test;
import unittest.annotations.Ordered;
import unittest.assertions.Assert;

@Ordered
public class TestB {

    @Test
    @Order(20)
    public void test0() {
        Assert.assertEquals(1 + 1, 10 - 8);
    }

    @Test
    @Order(30)
    public void test00() {
        Assert.assertTrue(!false);
    }

    @Test
    @Order(10)
    public void test000() {
        Assert.assertEquals("n", 'n');
    }

}
