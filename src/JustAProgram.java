import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JustAProgram {

    public static void main(String[] args) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(new File("src//JustAProgram.java"));

        pw.println("Xer vam a ne proga!!!");
        pw.close();
    }
}
