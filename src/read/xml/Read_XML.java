package read.xml;

import fix.roman.numbers.FixFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import static read.xml.BadSymbols.*;
import static read.xml.ToReadableFile.*;

public class Read_XML {

    private static LinkedList<String> replace(LinkedList<String> list,
                                              int i, String toReplace) {
        LinkedList<String> New = new LinkedList<>();

        for (int j = 0; j < i; j++)
            New.add(list.get(j));

        New.add(toReplace);

        for (int j = i + 1; j < list.size(); j++)
            New.add(list.get(j));

        return New;
    }

    private static LinkedList<String> removeNotSpans
        /*removeIfNotHave(FILE, "<span")*/(LinkedList<String> list) {
        return removeIfNotHave(list);
    }

    private static LinkedList<String> removeIfNotHave(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        for (String i : list) {
            String[] str = i.split(" ");

            boolean toRemove = true;
            for (String j : str)
                if (j.equals("<span"))
                    toRemove = false;


            if (!toRemove) {
                New.add(i);
            }
        }

        return New;
    }

    private static LinkedList<String> split(LinkedList<String> list, String split) {
        LinkedList<String> New = new LinkedList<>();
        for (String i : list) {
            String[] strings = i.split(split);

            New.addAll(Arrays.asList(strings));
        }
        return New;
    }

    private static LinkedList<String> defineLines(LinkedList<String> list) {
        LinkedList<String> lines = new LinkedList<>();
        for (String i : list) {
            String[] toFind = i.split(" ");

            boolean line = false;
            for (int j = 0 ; j < toFind.length; j++) {
                String cur = toFind[j];
                if (toFind[j].length() >= 9)
                    if (cur.substring(0, 9).equals("id=\'line_")) {
                        String Line = "<" + cur.substring(4, cur.length() - 1);
                        line = true;

                        Line += " " + toFind[j + 2] + " " + toFind[j + 3] + " " + toFind[j + 4] + " " +
                                toFind[j + 5].substring(0, toFind[j + 5].length() - 1) + " >";

                        lines.add(Line);
                        break;
                    }
            }

            if (!line)
                lines.add(i);
        }
        return lines;
    }

    private static LinkedList<String> removeStrong(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();
        for (String i : list)
            if (!i.equals("strong") && !i.equals("/strong"))
                New.add(i);
        return New;
    }
    
    private static LinkedList<String> join(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        for (int i = 0; i < list.size() - 1; i++) {
            String current = list.get(i);

            String ifE = list.get(i + 1);

            if (ifE.substring(0, 3).equals("em>")) {
                current += ">" + list.get(i + 1).substring(3, list.get(i + 1).length() - 4);
                list.remove(i + 1);
            }

            if (ifE.substring(0, 7).equals("strong>")) {
                current += ">" + list.get(i + 1).substring(7, list.get(i + 1).length() - 8);
                list.remove(i + 1);
            }

            /*for (int j = 0; j < ifE.length(); j++)
                if (ifE.substring(0, j).equals(ifE.substring(ifE.length() - j)))
                    current += ">" +
                            ifE.substring(j + 1, ifE.length() - ((j + 1) + 1));*/


            New.add(current);
        }
        return New;
    }

    private static LinkedList<String> removeSpanAtStart(LinkedList<String> list) {
            LinkedList<String> New = new LinkedList<>();

            for (String i: list) {
                boolean span = false;

                if (i.length() >= 7)
                    if (i.substring(0, 6).equals("/span>")) {
                        New.add(i.substring(7));
                        span = true;
                    }

                if (!span)
                    New.add(i);
            }

            return New;
    }

    private static LinkedList<String> defineWords(LinkedList<String> list) {
        LinkedList<String> words = new LinkedList<>();
        for (String i : list) {
            String[] toFind = i.split(" ");

            boolean word = false;
            for (int j = 0 ; j < toFind.length; j++) {
                String cur = toFind[j];
                if (toFind[j].length() >= 9)
                    if (cur.substring(0, 9).equals("id='word_")) {
                        String Word = "<word";
                        word = true;

                        Word += " " + toFind[j + 2] + " " + toFind[j + 3] + " " + toFind[j + 4] + " >";

                        Word += i.substring(findClosing(i) + 1);

                        words.add(Word);
                        break;
                    }
            }

            if (!word)
                words.add(i);
        }
        return words;
    }

    private static int findClosing(String where) {
            String[] str = where.split("");

        for (int i = 0; i < str.length; i++) {
            if (str[i].equals(">"))
                return i;
        }
        return 0;
    }

    private static LinkedList<String> cutSpan(LinkedList<String> list) {
            LinkedList<String> New = new LinkedList<>();

        for (String i : list) {
            boolean cut = false;

            if (i.equals("/span>"))
                continue;
            else {
                if (i.length() >= 7) {
                    if (i.substring(i.length() - 7).equals("</span>")) {
                        New.add(i.substring(0, i.length() - 7));
                        cut = true;
                    } else if (i.length() >= 8)
                        if (i.substring(i.length() - 8).equals("</span> ")) {
                            New.add(i.substring(0, i.length() - 8));
                            cut = true;
                        }
                }
            }

            if (!cut)
                New.add(i);
        }

        return New;
    }

    private static LinkedList<String> destroyEmptyLines(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        for (String i : list)
            if (!i.equals(""))
                New.add(i);

        return New;
    }

    public static void read(String Read, String Write) throws IOException {
        // scans
        /**Scanner ways = new Scanner(new File("ways.txt"));
        String Read = ways.nextLine();
        String Write = ways.nextLine();*/
        Scanner s = new Scanner(new File(Read));



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // reading file
        LinkedList<String> FILE = new LinkedList<>();
        while (s.hasNextLine())
            FILE.add(s.nextLine());


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // deleting tabs
        for (int i = 0; i < FILE.size(); i++) {
            String[] string = FILE.get(i).split(" {4}");

            StringBuilder toSet = new StringBuilder();
            for (String j : string)
                    toSet.append(j);

            FILE = replace(FILE, i, toSet.toString());
        }


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // removing needless lines
        FILE = removeNotSpans(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // if has >< then split for two lines
        FILE = split(FILE, "><");


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = defineLines(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = split(FILE, "</span> <span ");


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = removeStrong(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = join(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = removeSpanAtStart(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = defineWords(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = cutSpan(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = destroyEmptyLines(FILE);


        // To readable file



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = toNormalFile(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = changeBadSymbols(FILE);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        FILE = FixFile.fix(FILE);



        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // writing to file
        writeToTheFile(FILE, Write);
    }
}
