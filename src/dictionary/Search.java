/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

/**
 *
 * @author HP
 */
public class Search extends JPanel {

    public Search() throws IOException {
        addComponentToContainer();
    }

    public void addComponentToContainer() throws IOException {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(Constants.window_width, 80));
        this.setLayout(new BorderLayout());

        //SEARCH PANEL
        JPanel search_panel = new JPanel();
        search_panel.setPreferredSize(new Dimension(Constants.window_width, 60));
        search_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        search_panel.setBackground(new Color(238, 238, 238));

        JTextField search_text = new JTextField();
        if (Dictionary.isEngToVie) {
            search_text.setText("Enter your word...");

        } else {
            search_text.setText("Nhập từ cần tìm...");

        }
        search_text.setPreferredSize(new Dimension(300, 35));
        search_text.setFont(new Font("", Font.LAYOUT_LEFT_TO_RIGHT, 14));

        //JButton 
        JButton search_btn = new JButton();
        MyButton.styleButton(search_btn, Constants.DataPath + "/images/searching.png", new Dimension(35, 35));
        search_btn.setBorderPainted(false);

        search_panel.add(search_text);
        search_panel.add(search_btn);

        //content_search_page.add(search_panel, BorderLayout.PAGE_START);
        add(search_panel, BorderLayout.PAGE_START);

        //CONTENT PANEL
        JPanel content_panel = new JPanel();
        content_panel.setLayout(new FlowLayout());
        JTextArea content_text = new JTextArea();
        content_text.setPreferredSize(new Dimension(420, 4000));
        content_text.setAutoscrolls(true);
        content_text.setEditable(false);
        content_text.setLineWrap(true);
        content_text.setWrapStyleWord(true);
        content_text.setBackground(Color.WHITE);
        JScrollPane content_scrollpane = new JScrollPane(content_text);
        content_scrollpane.setPreferredSize(new Dimension(420, 400));
        content_panel.add(content_scrollpane);

        JButton like_btn = new JButton();
        Dimension like_btn_dim = new Dimension(25, 25);
        MyButton.styleButton(like_btn, Constants.DataPath + "/images/unlike.png", like_btn_dim);
        like_btn.setBounds(350, 10, 30, 30);
        like_btn.addActionListener((e) -> {
            //ImageIcon img = (ImageIcon) like_btn.getIcon();
            String voc = search_text.getText();

            if (((ImageIcon) like_btn.getIcon()).getDescription() == "like") {
                MyButton.setImgIcon(like_btn, Constants.DataPath + "/images/unlike.png", like_btn_dim);
                ((ImageIcon) like_btn.getIcon()).setDescription("unlike");
                Dictionary.currentFavor.remove(voc);
            } else {
                MyButton.setImgIcon(like_btn, Constants.DataPath + "/images/like.png", like_btn_dim);
                ((ImageIcon) like_btn.getIcon()).setDescription("like");
                String result = content_text.getText();
                try {
                    result = result.substring(0, result.indexOf('*') - 1);
                } catch (IndexOutOfBoundsException ex) {
                    result = "";
                }
                Dictionary.currentFavor.put(voc, result);

            }
        });

        add(content_panel, BorderLayout.CENTER);

        search_btn.addActionListener((var e) -> {
            String voc = search_text.getText();
            String result = Dictionary.currentDic.get(voc.toLowerCase());
            content_text.removeAll();
            if (result == null || result.length() == 0) {
                content_text.setText("not found\n");

            } else {
                //Kiểm tra like
                boolean is_liked = (Dictionary.currentFavor.get(voc) == null) ? false : true;
                if (is_liked) {
                    MyButton.setImgIcon(like_btn, Constants.DataPath + "/images/like.png", like_btn_dim);
                    ((ImageIcon) like_btn.getIcon()).setDescription("like");

                } else {
                    MyButton.setImgIcon(like_btn, Constants.DataPath + "/images/unlike.png", like_btn_dim);
                    ((ImageIcon) like_btn.getIcon()).setDescription("unlike");
                }
                System.out.println("\n"+voc);
                System.out.println(result);
                content_text.add(like_btn);

                //Hiển thị meaning
                content_text.setText(result + "\n");

                //Thêm lịch sử
                Calendar c = Calendar.getInstance();
                Date time = c.getTime();
                Dictionary.history.put(time.toString(),voc);

            }
            content_text.revalidate();
            content_text.setCaretPosition(0);
        });
    }
}
