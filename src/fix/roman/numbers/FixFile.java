package fix.roman.numbers;

import java.io.*;
import java.util.LinkedList;

import static fix.roman.numbers.RomanNumeral.*;

public class FixFile {

    private static LinkedList<String> romanToNormalNumbers(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        for (String s : list)
            New.add(changeInString(s));

        return New;
    }

    private static String join(String[] s, String joining) {
        return join(s, joining, 0, s.length);
    }

    private static String join(String[] s, String joining, int i) {
        return join(s, joining, i, s.length);
    }

    private static String join(String[] s, String joining, int i, int j) {
        String toR = "";

        for (int k = i; k < j; k++)
            toR += s[k] + joining;

        return toR;
    }

    private static boolean equalsByPart(String a, String b, String... split) {
        for (String spl : split)
            a = join(a.split(spl), "");

        return a.equals(b);
    }

    private static String removeParts(String a, String... remove) {
        String toR = a;

        for (String r : remove)
            toR = join(toR.split(r), "");

        return toR;
    }

    private static String sec = "--$#${Section}$#$---";
    private static LinkedList<String> setSections(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        String Section = "";
        int current = 1;
        for (String s : list) {
            String[] str = s.split(" ");

            String toA = s;

            if (str.length >= 2) {
                boolean ifDig;
                String __NUM = removeParts(str[1], "\\.", "\\(", "\\)", "\\:");

                try {
                    Integer.parseInt(__NUM);

                    ifDig = true;
                } catch (NumberFormatException ignored) {
                    ifDig = false;
                }

                if (ifDig && Integer.parseInt(__NUM) == current) {
                    if (current == 1) {
                        Section = removeParts(str[0], "\\.", "\\(", "\\)", "\\:");
                        toA = sec + " " + join(str, " ", 2);

                        current++;
                    } else if (equalsByPart(str[0], Section, "\\.", "\\(", "\\)", "\\:")) {
                        toA = sec + " " + join(str, " ", 2);

                        current++;
                    }
                }
            }

            New.add(toA);
        }

        return New;
    }

    private static LinkedList<String> changeToSections(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        for (String s : list) {
            String[] str = s.split(" ");
            boolean ifNum = (str[0].equals(sec));

            String toAdd = (ifNum) ? ("\\section{" + join(str, " ", 1) + "}") : s;

            New.add(toAdd);
        }

        return New;
    }

    public static LinkedList<String> fix(LinkedList<String> list) throws FileNotFoundException {
        LinkedList<String> FILE = list;


        ///////////////////////////////////////////////////////////////////////////////
        FILE = romanToNormalNumbers(FILE);


        ///////////////////////////////////////////////////////////////////////////////
        FILE = setSections(FILE);


        ///////////////////////////////////////////////////////////////////////////////
        FILE = changeToSections(FILE);

        return FILE;
    }
}
