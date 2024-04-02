package unittest.runners;

import unittest.annotations.Order;
import unittest.annotations.Test;
import unittest.assertions.AssertionException;
import unittest.assertions.ComparisonException;
import unittest.results.TestClassResult;
import unittest.results.TestMethodResult;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class OrderedTestRunner extends TestRunner {

    public OrderedTestRunner(Class testClass) {
        super(testClass);
        Arrays.sort(methods, Comparator.comparingInt(method -> method.getAnnotation(Order.class).value()));
        methodNames = getMethodNames(methods);
    }
    private static ArrayList<String> getMethodNames(Method[] fields) {
        ArrayList<String> methodNames = new ArrayList<>();
        for (Method field : fields)
            methodNames.add(field.getName());

        return methodNames;
    }


    // TODO: Finish implementing this class
}
