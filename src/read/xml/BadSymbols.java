package read.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Scanner;

class BadSymbols {
    private static String BadSymbolsInString(String inWhere) throws IOException {
        final int propSize = 5;
        String toR = inWhere;
        for (int i = 0; i < inWhere.length() - propSize + 1; i++) {
            for (pair j : getPropList()) {
                if (toR.length() >= i + propSize)
                if (inWhere.substring(i, i + propSize).equals(j.name)) {
                    String before = toR.substring(0, i);
                    String after = toR.substring(i + propSize);

                    toR = before + j.prop + after;
                }
            }
        }
        return toR;
    }

    static LinkedList<String> changeBadSymbols(LinkedList<String> list) throws IOException {
        LinkedList<String> New = new LinkedList<>();

        for (String i : list)
            New.add(BadSymbolsInString(i));

        return New;
    }

    private static LinkedList<pair> getPropList() throws IOException {
        LinkedList<pair> list = new LinkedList<>();

        String file = "src//read//xml//symbols.properties";

        Scanner s = new Scanner(new File(file));
        Properties properties = new Properties();
        properties.load(new FileInputStream(new File(file)));
        while (s.hasNextLine()) {
            String key = s.nextLine().substring(0, 5);

            list.add(new pair(key, (String) properties.get(key)));
        }

        return list;
    }
}

class pair {
    String name;
    String prop;

    pair(String name, String prop) {
        this.name = name;
        this.prop = prop;
    }

    @Override
    public String toString() {
        return "pair{" +
                "name='" + name + '\'' +
                ", prop='" + prop + '\'' +
                '}';
    }
}
