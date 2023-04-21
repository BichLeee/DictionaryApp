/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import static dictionary.Dictionary.favor_ENG;
import static dictionary.Dictionary.favor_VIE;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author HP
 */
public class DirectPage {

    static Container MainContainer;

    static JPanel nav = null;

    static JPanel CurPage = null;

    static JPanel language_panel = null;

    public DirectPage(final Container container) throws IOException {
        MainContainer = container;
        addComponentToContainer();
    }

    public static void addComponentToContainer() throws IOException {
        nav = createNav();
        language_panel = createLanguagePanel();
        MainContainer.setLayout(new BorderLayout());
        MainContainer.add(nav, BorderLayout.SOUTH);
        MainContainer.add(language_panel, BorderLayout.PAGE_START);

        MainContainer.revalidate();
        //MainContainer.repaint();

        CurPage = new Search();
        MainContainer.add(CurPage, BorderLayout.CENTER);
    }

    public static JPanel createNav() {
        //NAVIGATION
        JPanel _nav = new JPanel();
        _nav.setBackground(new Color(202, 221, 250));
        //bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        _nav.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
        _nav.setPreferredSize(new Dimension(Constants.window_width, 85));

        //button
        MyButton translate_btn = new MyButton();
        translate_btn.myDecoration("Translate", Constants.DataPath + "/images/search.png");
        MyButton dictionary_btn = new MyButton();
        dictionary_btn.myDecoration("Dictionary", Constants.DataPath + "/images/dictionary.png");
        MyButton favor_btn = new MyButton();
        favor_btn.myDecoration("My Favor", Constants.DataPath + "/images/heart.png");
        MyButton history_btn = new MyButton();
        history_btn.myDecoration("History", Constants.DataPath + "/images/history.png");

        _nav.add(translate_btn);
        _nav.add(dictionary_btn);
        _nav.add(favor_btn);
        _nav.add(history_btn);

        Dimension buttonSize = new Dimension(100, 70);
        translate_btn.setPreferredSize(buttonSize);
        dictionary_btn.setPreferredSize(buttonSize);
        favor_btn.setPreferredSize(buttonSize);
        history_btn.setPreferredSize(buttonSize);

        translate_btn.addActionListener((e) -> {
            try {
                BorderLayout layout = (BorderLayout) MainContainer.getLayout();
                JPanel centerPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
                MainContainer.remove(centerPanel);
                CurPage.removeAll();
                CurPage = new Search();
                MainContainer.add(CurPage, BorderLayout.CENTER);
                MainContainer.revalidate();

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        });

        dictionary_btn.addActionListener((e) -> {
            try {
                BorderLayout layout = (BorderLayout) MainContainer.getLayout();
                JPanel centerPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
                MainContainer.remove(centerPanel);
                CurPage.removeAll();
                CurPage = new MyDictionary();
            } catch (IOException ex) {
                Logger.getLogger(DirectPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            MainContainer.add(CurPage, BorderLayout.CENTER);
            MainContainer.revalidate();

        });

        favor_btn.addActionListener((e) -> {
            try {
                BorderLayout layout = (BorderLayout) MainContainer.getLayout();
                JPanel centerPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
                MainContainer.remove(centerPanel);
                CurPage.removeAll();
                CurPage = new Favor();
            } catch (IOException ex) {
                Logger.getLogger(DirectPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            //MainContainer.add(null, BorderLayout.CENTER);
            MainContainer.add(CurPage, BorderLayout.CENTER);
            MainContainer.revalidate();

        });

        history_btn.addActionListener((e) -> {
            try {
                BorderLayout layout = (BorderLayout) MainContainer.getLayout();
                JPanel centerPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
                MainContainer.remove(centerPanel);
                CurPage.removeAll();
                CurPage = new History();
            } catch (IOException|ParseException ex) {
                Logger.getLogger(DirectPage.class.getName()).log(Level.SEVERE, null, ex);
            }
            //MainContainer.add(null, BorderLayout.CENTER);
            MainContainer.add(CurPage, BorderLayout.CENTER);
            MainContainer.revalidate();

        });

        return _nav;
    }

    public static JPanel createLanguagePanel() throws IOException {
        //LANGUAGE PANEL
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(202, 221, 240));
        panel.setPreferredSize(new Dimension(Constants.window_width, 40));

        JLabel vietnamese_label = new JLabel("VIE");
        vietnamese_label.setPreferredSize(new Dimension(220, 40));
        vietnamese_label.setHorizontalAlignment(JLabel.CENTER);
        vietnamese_label.setFont(new Font("", Font.CENTER_BASELINE, 17));

        JLabel english_label = new JLabel("ENG");
        english_label.setPreferredSize(new Dimension(220, 40));
        english_label.setHorizontalAlignment(JLabel.CENTER);
        english_label.setFont(new Font("", Font.CENTER_BASELINE, 17));

        JButton reverse_button = new JButton();
        MyButton.styleButton(reverse_button, Constants.DataPath + "/images/switch.png", new Dimension(25, 25));

        if (Dictionary.isEngToVie) {
            panel.add(english_label, BorderLayout.LINE_START);
            panel.add(reverse_button, BorderLayout.CENTER);
            panel.add(vietnamese_label, BorderLayout.LINE_END);

        } else {
            panel.add(vietnamese_label, BorderLayout.LINE_START);
            panel.add(reverse_button, BorderLayout.CENTER);
            panel.add(english_label, BorderLayout.LINE_END);
        }

        reverse_button.addActionListener((e) -> {
            Dictionary.isEngToVie = !Dictionary.isEngToVie;
            if (Dictionary.isEngToVie) {
                Dictionary.currentDic = Dictionary.ENG_VIE;
                Dictionary.currentFavor = favor_ENG;
            } else {
                Dictionary.currentDic = Dictionary.VIE_ENG;
                Dictionary.currentFavor = favor_VIE;
            }
            try {
                BorderLayout layout = (BorderLayout) MainContainer.getLayout();
                // Lấy ra JPanel hiện tại đang nằm ở vị trí CENTER của BorderLayout
                JPanel centerPanel = (JPanel) layout.getLayoutComponent(BorderLayout.CENTER);
                // Lấy ra JPanel hiện tại đang nằm ở vị trí TOP của BorderLayout
                JPanel topPanel = (JPanel) layout.getLayoutComponent(BorderLayout.PAGE_START);

                // Xóa JPanel hiện tại khỏi vị trí CENTER của BorderLayout
                MainContainer.remove(centerPanel);
                MainContainer.remove(topPanel);

                language_panel.removeAll();
                language_panel = createLanguagePanel();
                renewCurPage();
            } catch (IOException|ParseException ex) {
                System.out.println(ex.getMessage());
            }

            MainContainer.add(language_panel, BorderLayout.PAGE_START);
            MainContainer.add(CurPage, BorderLayout.CENTER);
            MainContainer.revalidate();
            MainContainer.repaint();
        });

        return panel;

    }

    public static void renewCurPage() throws IOException, FileNotFoundException, ParseException {

        switch (CurPage.getClass().getSimpleName()) {
            case "Search" -> {
                CurPage.removeAll();
                CurPage = new Search();
                //copyAllJPanelComponent(new Search(), CurPage);
            }
            case "MyDictionary" -> {
                CurPage.removeAll();
                CurPage = new MyDictionary();
                //copyAllJPanelComponent(new MyDictionary(), CurPage);

            }
            case "Favor" -> {
                CurPage.removeAll();
                CurPage = new Favor();
            }
            case "History" -> {
                CurPage.removeAll();
                CurPage = new History();
            }
            default -> {
            }
        };
    }

    public static void removeAllJPanelComponent(JPanel pane) {
        Component[] components = pane.getComponents();
        for (Component component : components) {
            // Xóa các thành phần cũ khỏi CurPage
            pane.remove(component);
        }
    }
}
