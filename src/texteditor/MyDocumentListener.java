package texteditor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.io.IOException;

public class MyDocumentListener implements DocumentListener {
    String newline = "\n";
    EventAlert eventAlert;
    Hash hash;

    public MyDocumentListener(EventAlert eventAlert) throws IOException {
        this.eventAlert = eventAlert;
        this.hash = new Hash();
        hash.loadWords();
    }

    public void insertUpdate(DocumentEvent e) {
        try {
            updateLog(e, "inserted into");
        } catch (BadLocationException | IOException ex) {
            ex.printStackTrace();
        }
    }
    public void removeUpdate(DocumentEvent e) {
        try {
            updateLog(e, "removed from");
        } catch (BadLocationException | IOException ex) {
            ex.printStackTrace();
        }
    }
    public void changedUpdate(DocumentEvent e) {
        //Plain text components do not fire these events
    }

    public void updateLog(DocumentEvent e, String action) throws BadLocationException, IOException {
        String text = e.getDocument().getText(0, e.getDocument().getLength());

        if (!text.equals("")) {
            boolean isSpace = Character.isWhitespace(text.charAt(text.length() - 1));
            if (isSpace) {
                String lastWord = text.split(" ")[text.split(" ").length - 1];

                System.out.println(hash.h(lastWord));
                if (hash.words[(int) hash.h(lastWord)] == null) {
                    this.eventAlert.setTextAndWord(text, lastWord);
                    this.eventAlert.invokeHighLightError();
                }
            } else {
                this.eventAlert.invokeHighLight();
            }
        }
    }
}
