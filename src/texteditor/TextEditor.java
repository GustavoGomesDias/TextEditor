package texteditor;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

public class TextEditor extends JFrame implements ActionListener {

    JTextPane textComponent;
    JFrame frame;
    JMenuBar menuBar;

    public TextEditor() throws Exception {
        this.frame = new JFrame("Editor de Texto");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            System.out.println("Erro ao criar o editor");
        }

        this.textComponent = new JTextPane();
        this.textComponent.getDocument().addDocumentListener(new MyDocumentListener(new EventAlert(this)));
        menuBar = new JMenuBar();
        JMenu menuEditor = new JMenu("Arquivo");
        JMenuItem oprNew = new JMenuItem("Novo");
        JMenuItem oprSave = new JMenuItem("Salvar");

        oprNew.addActionListener(this);
        oprSave.addActionListener(this);

        menuEditor.add(oprNew);
        menuEditor.add(oprSave);

        menuBar.add(menuEditor);

        frame.setJMenuBar(menuBar);
        frame.setSize(800, 800);
        frame.add(textComponent);
        frame.setVisible(true);
    }

    public void saveFile() {
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsSaveDialog function to show the save dialog
        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            // Set the label to the path of the selected directory
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // Create a file writer
                FileWriter wr = new FileWriter(fi, false);

                // Create buffered writer to write
                BufferedWriter w = new BufferedWriter(wr);

                // Write
                w.write(this.textComponent.getText());

                w.flush();
                w.close();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(this.frame, evt.getMessage());
            }
        } // If the user cancelled the operation
        else {
            JOptionPane.showMessageDialog(this.frame, "the user cancelled the operation");
        }

    }

    public void newFile() {
        JFileChooser j = new JFileChooser("f:");

        // Invoke the showsOpenDialog function to show the save dialog
        int r = j.showOpenDialog(null);

        // If the user selects a file
        if (r == JFileChooser.APPROVE_OPTION) {
            // Set the label to the path of the selected directory
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                // String
                String s1 = "", sl = "";

                // File reader
                FileReader fr = new FileReader(fi);

                // Buffered reader
                BufferedReader br = new BufferedReader(fr);

                // Initialize sl
                sl = br.readLine();

                // Take the input from the file
                while ((s1 = br.readLine()) != null) {
                    sl = sl + "\n" + s1;
                }

                // Set the text
                this.textComponent.setText(sl);
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(this.frame, evt.getMessage());
            }
        } // If the user cancelled the operation
        else {
            JOptionPane.showMessageDialog(this.frame, "the user cancelled the operation");
        }
    }

    public boolean getRootPaneCheckingEnabled() {
        return this.rootPaneCheckingEnabled;
    }

    public void correctText() {
        String s = this.textComponent.getText();
        System.out.println(s);

        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, Color.red);
        StyledDocument sdoc = this.textComponent.getStyledDocument();
        sdoc.setCharacterAttributes(0, 5, attrs, rootPaneCheckingEnabled);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();

        if (action.equals("cut")) {
            this.textComponent.cut();
        } else if (action.equals("copy")) {
            this.textComponent.copy();
        } else if (action.equals("paste")) {
            this.textComponent.paste();
        } else if (action.equals("Salvar")) {
            this.saveFile();
        } else if (action.equals("Novo")) {
            this.newFile();
        } else if (action.equals("Corrigir Texto")) {
            this.correctText();
        }
    }

    public static void main(String args[]) throws Exception {
        TextEditor e = new TextEditor();
    }

}
