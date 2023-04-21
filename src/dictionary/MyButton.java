/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author HP
 */
public class MyButton extends JButton {

    public void myDecoration(String text, String img) {
        this.setText(text);
        this.setBackground(null);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.setMargin(new Insets(0, 0, 0, 0));
        this.setFocusPainted(false);
        this.setBorder(null);

        setImgIcon(this, img, new Dimension(40, 40));
    }

    public MyButton() {

    }

    public static void setImgIcon(JButton btn, String img_path, Dimension dimension) {
        ImageIcon icon = new ImageIcon(img_path);
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(dimension.width, dimension.height, java.awt.Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(newImage));
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVerticalTextPosition(JButton.BOTTOM);
    }

    public static void styleButton(JButton btn, String img_path, Dimension img_size) throws IOException {
        //BufferedImage reverse_img = ImageIO.read(new File(Dictionary.DataPath + img_path));
        //BufferedImage resized = Dictionary.resizeImage(reverse_img, img_size.width, img_size.height);
        //btn.setText("hihihihi");
        btn.setPreferredSize(img_size);
        //btn.setOpaque(false);
        btn.setBackground(null);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(null);
        
        ImageIcon icon = new ImageIcon( img_path);
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(img_size.width, img_size.height, java.awt.Image.SCALE_SMOOTH);
        btn.setIcon(new ImageIcon(newImage));
        
        //btn.setMargin(new Insets(0, 0, 0, 0));

    }
}
