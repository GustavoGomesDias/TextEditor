package texteditor;

import javax.swing.*;

class PopUp extends JPopupMenu {
    private boolean isHideAllowed;

    public PopUp() {
        super();
        this.isHideAllowed = false;
    }

    @Override
    public void setVisible(boolean visibility) {
        if (isHideAllowed && !visibility)
            super.setVisible(false);
        else if (!isHideAllowed && visibility)
            super.setVisible(true);
    }

    public void closePopup() {
        this.isHideAllowed = true;
        this.setVisible(false);
        this.isHideAllowed = false;
    }
}
