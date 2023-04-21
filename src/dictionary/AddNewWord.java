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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class AddNewWord extends JFrame {

    static JTable dic_table = null;

    public AddNewWord(JTable table) {
        super("Add New Word");
        dic_table = table;
        createAndShowGUI();
    }

    void addComponentToFrame() {
        setLayout(new BorderLayout());

        //Label
        JLabel ANWLabel = new JLabel();
        if (Dictionary.isEngToVie) {
            ANWLabel.setText("ADD NEW WORD");
        } else {
            ANWLabel.setText("THÊM TỪ MỚI");
        }
        ANWLabel.setForeground(Color.BLACK);
        ANWLabel.setVerticalTextPosition(JLabel.CENTER);
        ANWLabel.setHorizontalAlignment(JLabel.CENTER);
        ANWLabel.setFont(new Font("", Font.BOLD, 20));
        ANWLabel.setPreferredSize(new Dimension(300, 60));
        ANWLabel.setBackground(null);
        //Form
        JPanel form_panel = new JPanel();
        form_panel.setLayout(new FlowLayout());
        //voc
        JTextField voc_field = new JTextField();
        voc_field.setPreferredSize(new Dimension(250, 30));
        JLabel voc_label = new JLabel();
        if (Dictionary.isEngToVie) {
            voc_label.setText("Voc: ");
        } else {
            voc_label.setText("Từ mới: ");
        }
        voc_label.setLabelFor(voc_field);
        JPanel voc_panel = createInfoFieldPanel(voc_label, voc_field);
        //meaning
        JTextField meaning_field = new JTextField();
        meaning_field.setPreferredSize(new Dimension(250, 30));
        JLabel meaning_label = new JLabel();
        if (Dictionary.isEngToVie) {
            meaning_label.setText("Meaning: ");
        } else {
            meaning_label.setText("Ngữ nghĩa: ");
        }
        meaning_label.setLabelFor(meaning_field);
        JPanel meaning_panel = createInfoFieldPanel(meaning_label, meaning_field);
        //submit button
        JPanel bottomP = new JPanel();
        bottomP.setPreferredSize(new Dimension(1000, 70));
        JButton submitButton = new JButton("SUBMIT");
        submitButton.setPreferredSize(new Dimension(100, 30));
        submitButton.addActionListener((event) -> {
            String voc = voc_field.getText();
            String meaning = meaning_field.getText();
            DefaultTableModel model = (DefaultTableModel) dic_table.getModel();
            model.addRow(new Object[]{voc, meaning});
            Dictionary.currentDic.put(voc, meaning);
            this.dispose();
        });
        bottomP.add(submitButton);
        form_panel.add(voc_panel);
        form_panel.add(meaning_panel);

        add(ANWLabel, BorderLayout.PAGE_START);
        add(form_panel, BorderLayout.CENTER);
        add(bottomP, BorderLayout.PAGE_END);
    }

    public JPanel createInfoFieldPanel(JLabel label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        label.setPreferredSize(new Dimension(70, 35));
        panel.add(label);
        panel.add(field);

        return panel;
    }

    void createAndShowGUI() {
        //setDefaultLookAndFeelDecorated(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(true);
        setSize(400, 250);
        setLocationRelativeTo(null);
        ImageIcon img = new ImageIcon(Constants.DataPath + "/images/prg_icon.png", "Dictionary");
        setIconImage(img.getImage());
        setResizable(false);

        addComponentToFrame();

        setVisible(true);
    }
}
