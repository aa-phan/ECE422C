package unittest.runners;

import unittest.annotations.Parameters;
import unittest.annotations.Test;
import unittest.annotations.UseParameters;
import unittest.assertions.AssertionException;
import unittest.assertions.ComparisonException;
import unittest.results.TestClassResult;
import unittest.results.TestMethodResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ParameterizedTestRunner extends TestRunner {


    public ParameterizedTestRunner(Class testClass) {
        super(testClass);
        methods = testClass.getDeclaredMethods();
        methods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Test.class) | method.isAnnotationPresent(Parameters.class))
                .toArray(Method[]::new);
        getMethodNames(methods);
        this.testClass = testClass;
        // TODO: complete this constructor
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
        Object output = inputArray();
        for (String method : methodNames) {
            try {
                if(methodAnno(method, methods, UseParameters.class)){
                    Class<?> methodType = methodType(method, methods);
                    Method m = testClass.getDeclaredMethod(method, methodType);
                    for(int i = 0; i<Array.getLength(output);i++){
                        m.invoke(testClass.newInstance(), Array.get(output, i));
                        TestMethodResult methodtest = new TestMethodResult(method + "[" + Array.get(output,i) + "]", true, null);
                        result.addTestMethodResult(methodtest);
                    }
                }
                else{
                    Method m = testClass.getDeclaredMethod(method, new Class[0]);
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
    private Class<?> methodType(String methodName, Method[] methods){
        for(Method methodz : methods){
            if(methodz.getName().equals(methodName)){
                Class<?>[] parameterType = methodz.getParameterTypes();
                return parameterType[0];
            }
        }
        return null;
    }
    private boolean methodAnno(String methodName, Method[] methods, Class<?> targetAnnotation){
        for(Method methodz : methods){
            if(methodz.getName().equals(methodName)){
                return methodz.isAnnotationPresent((Class<? extends Annotation>) targetAnnotation);
            }
        }
        return false;
    }
    private Object inputArray(){
        try{
            Method p = testClass.getDeclaredMethod("parameters");
            return p.invoke(testClass.newInstance());
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
    // Method to wrap primitive return type in its corresponding wrapper class

    // TODO: Finish implementing this class
}
