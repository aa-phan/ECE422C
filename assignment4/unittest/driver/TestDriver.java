package unittest.driver;

import unittest.annotations.*;
import unittest.results.TestClassResult;
import unittest.results.TestMethodResult;
import unittest.runners.FilteredTestRunner;
import unittest.runners.OrderedTestRunner;
import unittest.runners.ParameterizedTestRunner;
import unittest.runners.TestRunner;

import java.lang.reflect.Method;
import java.util.*;

import static unittest.gui.TestGUI.TestResultsGUI;

public class TestDriver {

    /**
     * Execute the specified test classes, returning the results of running
     * each test class, in the order given.
     */
    public static List<TestClassResult> runTests(String[] testclasses) {
        // TODO: complete this method
        // We will call this method from our JUnit test cases.

        List<TestClassResult> fullTestResults = new ArrayList<>();

        for (int i = 0; i < testclasses.length; i++) {
            try {
                List<String> targetMethods = new ArrayList<>();
                Method[] methods;
                TestRunner run;
                Class test;
                boolean orderAnno; boolean parameterAnno;
                boolean filterAnno = false;
                if(testclasses[i].contains("#")){
                    filterAnno = true;
                    targetMethods.add(testclasses[i].substring(testclasses[i].indexOf("#")+1));
                    testclasses[i] = testclasses[i].substring(0, testclasses[i].indexOf("#"));
                }
                if(testclasses[i].chars().filter(c -> c == '.').count() >= 2){
                    int folderSep = testclasses[i].indexOf(".");
                    int classSep = testclasses[i].indexOf(".", folderSep+1);
                    String folderName = testclasses[i].substring(0, folderSep);
                    String className = testclasses[i].substring(folderSep+1, classSep);
                    String methodName = testclasses[i].substring(classSep + 1);
                    testclasses[i] = folderName + "." + className;
                    test = Class.forName(testclasses[i]);
                    methods = test.getDeclaredMethods();
                    for(Method m : methods){
                        if(m.getName().equals(methodName)){
                            methods = new Method[]{m};
                        }
                    }
                    orderAnno = false; parameterAnno = false;
                }
                else {
                    test = Class.forName(testclasses[i]);
                    methods = test.getDeclaredMethods();
                    orderAnno = Arrays.stream(test.getDeclaredAnnotations())
                            .anyMatch(annotation -> annotation.annotationType().equals(Ordered.class));
                    // Check if class has the Parameterized annotation
                    parameterAnno = Arrays.stream(test.getDeclaredAnnotations())
                            .anyMatch(annotation -> annotation.annotationType().equals(Parameterized.class));
                    //check which annotation the test class contains
                }
                boolean testAnno = Arrays.stream(methods).anyMatch(method -> method.isAnnotationPresent(Test.class));
                //ordered test runner
                if(filterAnno){
                    run = new FilteredTestRunner(test, targetMethods);
                }
                //filtered test runner
                else if(orderAnno){
                    run = new OrderedTestRunner(test);
                }
                //parametrized test runner
                else if(parameterAnno){
                    run = new ParameterizedTestRunner(test);
                }
                //default test runner
                else{
                    if(methods.length==1){
                        run = new TestRunner(test, methods);
                    }
                    else{

                        run = new TestRunner(test);
                    }
                }

                fullTestResults.add(run.run());


            } catch (ClassNotFoundException c) {
                throw new RuntimeException(c);
            }
        }

        printResults(fullTestResults);
        TestResultsGUI.addAll(fullTestResults);
        return fullTestResults;
    }

    public static void main(String[] args) {
        // Use this for your testing.  We will not be calling this method.

        String[] arguments = new String[1];

        /*arguments[0] = "sampletest.TestA";

        */
        //arguments[0] = "sampletest.TestC#test4,test6";
        //arguments[0] = "sampletest.TestD";
        //arguments[1] = "sampletest.TestB";
        //arguments[0] = "sampletest.TestA";
//        arguments[0] = "studenttest.TestC.testLong";
//        runTests(arguments);
        String s = "a b ";
        System.out.println(Arrays.toString(s.split(" ")));



    }
    public static String printResults(List<TestClassResult> result){
        int total = 0; int fail = 0;
        final String[] output = {""};

        SortedMap<String, TestMethodResult> failures = new TreeMap<>();
        for(TestClassResult a : result){
            for(TestMethodResult b : a.getTestMethodResults()){
                if(b.isPass()){
                    output[0] = output[0] + a.getTestClassName() + "." + b.getName() + " : PASS\n";
                    System.out.println(a.getTestClassName() + "." + b.getName() + " : PASS"); total++;
                }
                else{
                    output[0] = output[0] + a.getTestClassName() + "." + b.getName() + " : FAIL\n";
                    System.out.println(a.getTestClassName() + "." + b.getName() + " : FAIL"); fail++; total++;
                    failures.put(a.getTestClassName() + "." + b.getName(), b);
                }
            }
        }

        output[0] = output[0] + "==========\n" + "FAILURES:\n";
        System.out.println("==========");
        System.out.println("FAILURES:");
        failures.forEach((key, value) -> {
            output[0] = output[0] + key + "\n";
           System.out.println(key + ":");
           value.getException().printStackTrace();

        });

        System.out.println("==========");
        System.out.println("Tests run: " + total + ", Failures: " + fail);
        output[0] = output[0] + "==========\n" + "Tests run: " + total + ", Failures: " + fail + "\n";

        return output[0];
    }
}
