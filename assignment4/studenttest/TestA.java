package studenttest;

import unittest.annotations.Test;
import unittest.assertions.Assert;

public class TestA {
    @Test
    public void test10() {
        Assert.assertEquals("hello", "heelo");
    }

    @Test
    public void test11() {
        Assert.assertTrue("hello".equals("hello"));
    }
}
