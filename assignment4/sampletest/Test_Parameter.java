package sampletest;

import unittest.annotations.Parameters;
import unittest.annotations.Parameterized;
import unittest.annotations.UseParameters;
import unittest.annotations.Test;
import unittest.assertions.Assert;

@Parameterized
public class Test_Parameter {

    @Parameters
    public static int[] parameters(){
        return new int[]{2,1,5};
    }

    @Test
    public void test1(){
        Assert.assertTrue(true);
    }

    @Test
    @UseParameters
    public void test2(int i){
        Assert.assertEquals(i,i);
    }
    @Test
    @UseParameters
    public void test3(int i){
        Assert.assertEquals(i, i+1);
    }
}
