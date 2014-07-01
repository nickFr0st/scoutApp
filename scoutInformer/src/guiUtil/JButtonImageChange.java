package guiUtil;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Malloch on 7/1/14
 */
public class JButtonImageChange extends JButton {
    private Icon defaultImage;
    private Icon selectedImage;

    public JButtonImageChange() {
        super();

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(selectedImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(defaultImage);
            }
        });
    }

    public Icon getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(Icon defaultImage) {
        this.defaultImage = defaultImage;
        setIcon(defaultImage);
    }

    public Icon getSelectedImage() {
        return selectedImage;
    }

    public void setSelectedImage(Icon selectedImage) {
        this.selectedImage = selectedImage;
    }

    public void setDefault() {
        setIcon(defaultImage);
    }
}
