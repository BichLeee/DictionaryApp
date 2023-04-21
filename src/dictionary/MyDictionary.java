/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author HP
 */
public class MyDictionary extends JPanel {

    public static boolean EngDicHasChange = false;
    public static boolean VieDicHasChange = false;

    public MyDictionary() throws IOException {
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
            columnNames = new String[]{"English", "Vietnamese"};
            dic = Dictionary.ENG_VIE;
        } else {
            columnNames = new String[]{"Vietnamese", "English"};
            dic = Dictionary.VIE_ENG;
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (String key : dic.keySet()) {
            String value = dic.get(key);
            Object[] row = {key, value};
            model.addRow(row);
        }

        JTable table = new JTable(model);

        //set column width
        int[] columnWidth = {100, 300};
        for (int i = 0; i < 2; i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth[i]);
        }
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //set row height
        table.setRowHeight(25);
        table.getTableHeader().setPreferredSize(new Dimension(table.getWidth(), 32));

        // Tạo context menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Add");
        editItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi người dùng click Add
                new AddNewWord(table);
                if (Dictionary.isEngToVie) {
                    EngDicHasChange = true;
                } else {
                    VieDicHasChange = true;
                }
            }
        });
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete");
        deleteItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện khi người dùng click Delete
                int row = table.getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    Dictionary.currentDic.remove(model.getValueAt(row, 0));
                    model.removeRow(row);
                    if (Dictionary.isEngToVie) {
                        EngDicHasChange = true;
                    } else {
                        VieDicHasChange = true;
                    }
                }
            }
        });
        popupMenu.add(deleteItem);

        // Thêm listener cho bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                // Kiểm tra nếu người dùng chuột phải
                if (e.isPopupTrigger()) {
                    // Lấy vị trí hàng
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        // Chọn hàng
                        table.setRowSelectionInterval(row, row);
                        // Hiển thị menu ngữ cảnh tại vị trí chuột phải
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        return table;
    }

}
