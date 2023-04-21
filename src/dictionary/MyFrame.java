/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author HP
 */
public class MyFrame extends JFrame implements WindowListener {

    public MyFrame(String title) {
        super(title);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(this);

        setResizable(true);
        setSize(500, 650);
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(Constants.DataPath + "/images/prg_icon.png", "Dictionary");
        setIconImage(img.getImage());
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("hihi");
        Ulti.writeCSVFile(Dictionary.favor_ENG, Constants.DataPath + "/data/favor_ENG.csv");
        Ulti.writeCSVFile(Dictionary.favor_VIE, Constants.DataPath + "/data/favor_VIE.csv");
        Ulti.writeCSVFile(Dictionary.history, Constants.DataPath + "/data/history.csv");
        if (MyDictionary.EngDicHasChange == true) {
            Ulti.writeXmlDomParser(Constants.DataPath + "/data/Anh_Viet.xml", Dictionary.ENG_VIE);
        }
        if (MyDictionary.VieDicHasChange == true) {
            Ulti.writeXmlDomParser(Constants.DataPath + "/data/Viet_Anh.xml", Dictionary.VIE_ENG);
        }
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
