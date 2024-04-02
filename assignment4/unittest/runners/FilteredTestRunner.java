package unittest.runners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FilteredTestRunner extends TestRunner {

    public FilteredTestRunner(Class testClass, List<String> testMethods) {
        super(testClass);
        methodNames = getMethodNames(testMethods.get(0));
        // TODO: complete this constructor
    }

    private static ArrayList<String> getMethodNames(String fields) {
        ArrayList<String> methodNames = new ArrayList<>();

        int start = 0; int cur = 0;
        for(int i = 0; i<fields.length(); i++){
            if(fields.charAt(i)==','){
                cur = i;
                methodNames.add(fields.substring(start, i));
                start = i + 1;
            }
            if(i+1 == fields.length()){
                methodNames.add(fields.substring(start));
            }
        }

        return methodNames;
    }
    // TODO: Finish implementing this class
}
