package unittest.listeners;

import unittest.gui.TestGUI;
import unittest.results.TestMethodResult;

import java.util.ArrayList;
import java.util.Arrays;

import static unittest.driver.TestDriver.runTests;


public class GUITestListener implements TestListener {

    public boolean pass;

    // Call this method right before the test method starts running
    @Override
    public void testStarted(String testMethod) {

//        StringBuilder input = new StringBuilder(testMethod);
//        int folderSep = input.indexOf(" ");
//        int classSep = input.indexOf(" ", folderSep + 1);
//        int methodSep = input.indexOf(" ", classSep + 1);
//        input.replace(folderSep, folderSep+1, ".");
//        input.replace(classSep, classSep+1, ".");
//        if(input.charAt(methodSep-1) == ',' && input.charAt(methodSep-2)=='.'){
//
//            input.replace(methodSep-2, input.length(), "");
//        }
//        else{
//
//            input.replace(methodSep-1, input.length(), "");
//        }
//        String[] inputFormed = new String[]{input.toString()};
//        System.out.println(Arrays.toString(inputFormed));
        runTests(new String[]{testMethod});
    }

    // Call this method right after the test method finished running successfully
    @Override
    public void testSucceeded(TestMethodResult testMethodResult) {
        //TestGUI.passedTest(testMethodResult);
    }

    // Call this method right after the test method finished running and failed
    @Override
    public void testFailed(TestMethodResult testMethodResult) {
        //TestGUI.failedTest(testMethodResult);

    }
}
