package texteditor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class MyDocumentListener implements DocumentListener {
    String newline = "\n";
    EventAlert eventAlert;

    public MyDocumentListener(EventAlert eventAlert) {
        this.eventAlert = eventAlert;
    }

    public void insertUpdate(DocumentEvent e) {
        try {
            updateLog(e, "inserted into");
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    public void removeUpdate(DocumentEvent e) {
        try {
            updateLog(e, "removed from");
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    public void changedUpdate(DocumentEvent e) {
        //Plain text components do not fire these events
    }

    public void updateLog(DocumentEvent e, String action) throws BadLocationException {
        String text = e.getDocument().getText(0, e.getDocument().getLength());
        if (!text.equals("")) {
            String lastWord = text.split(" ")[text.split(" ").length - 1];
            if (lastWord.equals("Erro")) {
                this.eventAlert.setTextAndWord(text, lastWord);
                this.eventAlert.invokeHighLightError();
            } else {
                this.eventAlert.invokeHighLight();
            }
        }
    }
}
