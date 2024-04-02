package studenttest;

import unittest.annotations.Order;
import unittest.annotations.Ordered;
import unittest.annotations.Test;
import unittest.assertions.Assert;


public class TestC {

    @Test
    @Order(0)
    public void testZ() {
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(i, i);
        }
    }

    @Test
    @Order(1)
    public void testY() {
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(i / 2, i % 2); // Fail, Fail, Fail, Pass
        }
    }

    @Test
    @Order(3)
    public void testX() {
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(i, 4 - i); // Fail, Fail, Pass, Fail
        }
    }

    @Test
    @Order(2)
    public void testLong() {
        try {
            Thread.sleep(2000); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            // This part is executed if the sleep is interrupted
            System.err.println("Sleep was interrupted");
        }

        Assert.assertEquals(1, 1);
    }

    @Order(6)
    public void test60() {
        System.out.println("SOMETHING WENT WRONG");
    }
}
