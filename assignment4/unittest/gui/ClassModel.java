package unittest.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ClassModel {

    private final String fileName;

    private ArrayList<String> methods;

    public ClassModel(String fileName) {

        this.fileName = fileName;
        this.methods = new ArrayList<>();
        this.methods.add("");
    }

    public String getFileName() {

        return fileName;
    }

    public void addMethod(String m) {
        methods.add(m);
    }

    public ArrayList<String> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        String result = fileName + ": [";
        for (String s : methods) {
            result = result + s + ", ";
        }

        result = result.substring(0, result.length() - 2) + "]";

        return result;
    }
}
