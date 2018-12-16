import read.xml.Read_XML;

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

import static java.awt.BorderLayout.*;


public class Run extends JFrame {

    private Run() {
        JFrame window = new JFrame("From horc");

        JLabel ts = new JLabel("Tesseract file:");
        JTextField tess = new JTextField(30);

        JLabel tx = new JLabel("txt file:");
        JTextField txt = new JTextField(30);

        JButton compile = new JButton("Compile");
        compile.addActionListener((e) -> {
            try {
                Read_XML.read(tess.getText(), txt.getText());
            } catch (IOException ignored) {}

            // For example:
            // 3_txt.horc.xml
            // 3_out.txt

            JOptionPane.showMessageDialog(Run.this,
                    "Successful compilation!!!");
        });


        JPanel tesseract = new JPanel();
        tesseract.add(ts);
        tesseract.add(tess);

        JPanel text = new JPanel();
        text.add(tx);
        text.add(txt);


        window.getContentPane().add(NORTH, tesseract);
        window.getContentPane().add(text);
        window.getContentPane().add(SOUTH , compile);

        window.setDefaultCloseOperation(3);

        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        new Run();
    }
}
