/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author HP
 */
public class Dictionary extends JPanel {

    public static HashMap<String, String> ENG_VIE = null;
    public static HashMap<String, String> VIE_ENG = null;
    public static HashMap<String, String> currentDic = null;

    public static HashMap<String, String> favor_ENG = null;
    public static HashMap<String, String> favor_VIE = null;
    public static HashMap<String, String> currentFavor = null;

    public static HashMap<String, String> history = null;

    static boolean isEngToVie = true;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
        //set data path
        String jarDir = new File(Dictionary.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath();
        Constants.DataPath=jarDir;
        
        createAndShowGUI();

        //load dictionary
        ENG_VIE = Ulti.readXmlDomParser(Constants.DataPath + Constants.file_ENG_VIE);
        VIE_ENG = Ulti.readXmlDomParser(Constants.DataPath + Constants.file_VIE_ENG);

        //load list favor words
        favor_ENG = Ulti.readCSVFile(Constants.DataPath + Constants.file_favor_ENG);
        favor_VIE = Ulti.readCSVFile(Constants.DataPath + Constants.file_favor_VIE);

        //load history
        history = Ulti.readCSVFile(Constants.DataPath + Constants.file_history);

        //current
        currentDic = ENG_VIE;
        currentFavor = favor_ENG;
    }

    public void addComponentToPane(final Container pane) throws IOException {

        pane.setLayout(new BorderLayout());

        JLabel dictionary_label = new JLabel("DICTIONARY");
        dictionary_label.setForeground(Color.BLACK);
        dictionary_label.setVerticalAlignment(JLabel.TOP);
        dictionary_label.setHorizontalAlignment(JLabel.CENTER);
        dictionary_label.setFont(new Font("", Font.BOLD, 30));
        dictionary_label.setPreferredSize(new Dimension(Constants.window_width, 100));

        JLabel img_label = new JLabel();
        BufferedImage prg_icon = ImageIO.read(new File(Constants.DataPath + "/images/prg_icon.png"));
        BufferedImage resized = resizeImage(prg_icon, 160, 160);
        img_label.setIcon(new ImageIcon(resized));
        img_label.setHorizontalAlignment(JLabel.CENTER);
        img_label.setPreferredSize(new Dimension(Constants.window_width, 160));
        img_label.setVerticalAlignment(JLabel.CENTER);

        JPanel center_panel = new JPanel();
        center_panel.setLayout(new BorderLayout());
        center_panel.setBackground(Color.WHITE);
        center_panel.setPreferredSize(new Dimension(Constants.window_width, 400));
        center_panel.add(img_label, BorderLayout.CENTER);
        center_panel.add(dictionary_label, BorderLayout.PAGE_END);

        JLabel clicknext_label = new JLabel("Click to continue...");
        clicknext_label.setFont(new Font("", Font.ITALIC, 15));
        clicknext_label.setForeground(Color.gray);
        clicknext_label.setPreferredSize(new Dimension(Constants.window_width, 70));
        clicknext_label.setHorizontalAlignment(JLabel.CENTER);

        pane.add(center_panel, BorderLayout.PAGE_START);
        pane.add(clicknext_label, BorderLayout.PAGE_END);

        pane.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.print("mouse clicked!");
                newPage(pane);
                try {
                    new DirectPage(pane);
                    pane.removeMouseListener(this);
                } catch (IOException ex) {
                    Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

    }

    private static void newPage(Container c) {
        //a=null;
        c.removeAll();
        c.repaint();
    }

    public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
        Image scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(scaled, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return resized;
    }

    private static void createAndShowGUI() throws IOException {
        //Create and set up the window.
        MyFrame frame = new MyFrame("Dictionary");

        Dictionary app = new Dictionary();
        app.addComponentToPane(frame.getContentPane());

        //frame.pack();
        frame.setVisible(true);
    }

}
