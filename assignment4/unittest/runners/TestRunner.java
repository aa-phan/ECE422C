package unittest.runners;

import unittest.annotations.Test;
import unittest.assertions.AssertionException;
import unittest.assertions.ComparisonException;
import unittest.listeners.GUITestListener;
import unittest.listeners.TestListener;
import unittest.results.TestClassResult;
import unittest.results.TestMethodResult;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class TestRunner {

    Method[] methods;
    ArrayList<String> methodNames;
    Class testClass;

    public TestRunner(Class testClass) {
        // TODO: complete this constructor
        methods = testClass.getDeclaredMethods();
        methods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Test.class))
                .toArray(Method[]::new);
        methodNames = getMethodNames(methods);
        this.testClass = testClass;

    }
    public TestRunner(Class testClass, Method[] m){
        methods = m;
        methodNames = getMethodNames(methods);
        this.testClass = testClass;
    }


    private static ArrayList<String> getMethodNames(Method[] fields) {
        ArrayList<String> methodNames = new ArrayList<>();
        for (Method field : fields)
            methodNames.add(field.getName());

        methodNames.sort(Comparator.naturalOrder());

        return methodNames;
    }

    public TestClassResult run() {
        // TODO: complete this method

        TestClassResult result = new TestClassResult(testClass.getName());

        for (String method : methodNames) {

            try {

                Method m = testClass.getDeclaredMethod(method);

                // check annotations to see of the method is a test method
                if (m.isAnnotationPresent(Test.class)){
                    m.invoke(testClass.newInstance());
                    TestMethodResult methodtest = new TestMethodResult(method, true, null);
                    result.addTestMethodResult(methodtest);
                }

            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();

                if (cause instanceof ComparisonException){

                    TestMethodResult methodtest = new TestMethodResult(method, false, (ComparisonException) cause);
                    result.addTestMethodResult(methodtest);

                } else if (cause instanceof AssertionException) {
                    TestMethodResult methodtest = new TestMethodResult(method, false, (AssertionException) cause);
                    result.addTestMethodResult(methodtest);
                }
            }
        }


        return result;
    }

    public void addListener(TestListener listener) {
        // Do NOT implement this method for Assignment 4
        // You will implement this for Assignment 5
        // Do NOT remove this method
    }
}