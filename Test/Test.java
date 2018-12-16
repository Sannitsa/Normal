import fix.roman.numbers.FixFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class Test {

    private static LinkedList<String> toList(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));

        LinkedList<String> list = new LinkedList<>();

        while (s.hasNextLine())
            list.add(s.nextLine());

        return list;
    }

    private static void print(LinkedList<String> list, String path) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(path));

        for (String s : list)
            pw.println(s);

        pw.close();
    }

    public static void main(String[] args) throws FileNotFoundException {
        print(FixFile.fix(toList("romanNums.txt")), "toCheck_OUT.txt");
    }
}
