package read.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

class ToReadableFile {

    private static LinkedList<String> wordsToLines(LinkedList<String> list) {
        LinkedList<String> New = new LinkedList<>();

        final int size = defineSize(list.getFirst());

        int lastX = 0;

        String line = "";
        for (String i : list) {
            boolean isLine = false;

            if (i.substring(0, 6).equals("<line_")) {
                if (!line.equals(""))
                    New.add(line);

                String startSpaces = "";

                int numSpaces = Integer.parseInt(i.split(" ")[1]) / size;

                for (int j = 0; j < numSpaces; j++)
                    startSpaces += " ";

                line = startSpaces;

                isLine = true;
                lastX = Integer.parseInt(i.split(" ")[1]);
            }

            if (!isLine) { // if this is a word
                int newX = Integer.parseInt(i.split(" ")[1]);

                int countSpaces = 1;
                if (newX != lastX)
                    countSpaces = (newX - lastX) / size + 1;

                lastX = Integer.parseInt(i.split(" ")[3]);

                int start = 0;
                for (int j = 0; j < i.length(); j++)
                    if (i.split("")[j].equals(">"))
                        start = j + 1;

                StringBuilder spaces = new StringBuilder();

                for (int j = 0; j < countSpaces; j++)
                    spaces.append(" ");

                line += spaces + i.substring(start);
            }
        }

        return New;
    }

    private static int defineSize(String toDef) {
        int startY = 2;
        int endY = 4;

        return Integer.parseInt(toDef.split(" ")[endY]) - Integer.parseInt(toDef.split(" ")[startY]);
    }

    static LinkedList<String> toNormalFile(LinkedList<String> list) {
        return wordsToLines(list);
    }

    static void writeToTheFile(LinkedList<String> list, String path) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File(path));

        for (String i : list)
            pw.println(i);

        pw.close();
    }
}

