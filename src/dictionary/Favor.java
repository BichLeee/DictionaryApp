/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP
 */
public class Favor extends JPanel {

    public Favor() throws IOException {
        addComponentToContainer();
    }

    public void addComponentToContainer() throws IOException {
        JTable table = createTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(420, 470));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }

    public static JTable createTable() throws FileNotFoundException, IOException {
        String[] columnNames = null;
        HashMap<String, String> dic;

        if (Dictionary.isEngToVie) {
            columnNames = new String[]{"Voc", "Pronunciation"};
            dic = Dictionary.favor_ENG;
        } else {
            columnNames = new String[]{"Từ vựng", "Phát âm"};
            dic = Dictionary.favor_VIE;
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String key : dic.keySet()) {
            String value = dic.get(key);
            Object[] row = {key, value};
            model.addRow(row);
        }

        JTable table = new JTable(model);
        //table.set

        //set column width
        int[] columnWidth = {100, 300};
        for (int i = 0; i < 2; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth[i]);
            //column.setMinWidth(columnWidth[i]);

        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //set row height
        table.setRowHeight(25);
        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 32));
        return table;
    }
}
