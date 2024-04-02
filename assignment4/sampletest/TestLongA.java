package sampletest;

import unittest.annotations.Order;
import unittest.annotations.Ordered;
import unittest.annotations.Test;
import unittest.assertions.Assert;

@Ordered
public class TestLongA {

    @Test
    @Order(3)
    public void testALong() throws InterruptedException {
        Thread.sleep(1000);

        Assert.assertEquals(1, 1);
    }
    @Test
    @Order(1)
    public void testCLong() throws InterruptedException {
        Thread.sleep(2000);
        Assert.assertEquals(3, 1 + 2);
    }
    @Test
    @Order(2)
    public void atestLong() throws InterruptedException {
        Thread.sleep(500);
        Assert.assertEquals(3, 3);
    }

}
