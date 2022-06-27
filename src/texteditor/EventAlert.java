package texteditor;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Objects;

public class EventAlert {
    TextEditor editor;
    private String text;
    private String word;
    public boolean hasTypeError = false;
    private PopUp popup = new PopUp();

    public EventAlert(TextEditor editor) {
        this.editor = editor;
    }

    public void setTextColorError() {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, Color.red);
        StyledDocument sdoc = this.editor.textComponent.getStyledDocument();
        sdoc.setCharacterAttributes(this.text.length() - this.word.length(), this.word.length(), attrs, this.editor.getRootPaneCheckingEnabled());
    }

    public void setTextColor() {
        if (this.hasTypeError) {
            this.popup.closePopup();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, Color.black);
            StyledDocument sdoc = this.editor.textComponent.getStyledDocument();
            sdoc.setCharacterAttributes(this.text.length() - this.word.length(), this.word.length(), attrs, this.editor.getRootPaneCheckingEnabled());
            this.hasTypeError = false;
        }
    }

    public void setTextAndWord(String text, String word) {
        this.text = text;
        this.word = word;
    }

    public void makePopUp() {
        popup.add("Teste");
        popup.setFocusable(false);
        popup.show(this.editor.textComponent, this.text.length() + 15, this.text.length() + 15);
        popup.setVisible(true);
        System.out.println(popup.isVisible());
    }

    public void highlightError() {
        this.makePopUp();
        this.hasTypeError = true;
        this.setTextColorError();
    }

    public void highlight() {
        this.setTextColor();
    }

    public void invokeHighLightError() {
        EventAlert ea = this;
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                ea.highlightError();
            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }

    public void invokeHighLight() {
        EventAlert ea = this;
        Runnable doHighlight = new Runnable() {
            @Override
            public void run() {
                ea.highlight();
            }
        };
        SwingUtilities.invokeLater(doHighlight);
    }
}
