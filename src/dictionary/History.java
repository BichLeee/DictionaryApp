/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dictionary;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;

/**
 *
 * @author HP
 */
public class History extends JPanel {

    public History() throws IOException, FileNotFoundException, ParseException {
        addComponentToContainer();
    }

    public void addComponentToContainer() throws IOException, FileNotFoundException, ParseException {
        setLayout(new BorderLayout());

        //CREATE DATE PANEL
        JPanel date_panel = new JPanel();
        date_panel.setLayout(new BorderLayout());

        JPanel timestart_panel = new JPanel();
        timestart_panel.setBackground(Color.WHITE);
        timestart_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 10));
        JLabel timestart_label = new JLabel("From");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 2, 1);
        Date defaultDate = calendar.getTime();
        JDateChooser timestart_calender = new JDateChooser(defaultDate);
        timestart_calender.setPreferredSize(new Dimension(200, 30));
        timestart_calender.setDateFormatString(Constants.datePattern);
        timestart_label.setLabelFor(timestart_calender);
        timestart_panel.add(timestart_label);
        timestart_panel.add(timestart_calender);

        JPanel timeend_panel = new JPanel();
        timeend_panel.setBackground(Color.WHITE);
        timeend_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 7, 10));
        JLabel timeend_label = new JLabel("To");
        JDateChooser timeend_calender = new JDateChooser(new Date());
        timeend_calender.setPreferredSize(new Dimension(200, 30));
        timeend_calender.setDateFormatString(Constants.datePattern);
        timeend_label.setLabelFor(timeend_calender);
        timeend_panel.add(timeend_label);
        timeend_panel.add(timeend_calender);

        date_panel.add(timestart_panel, BorderLayout.LINE_START);
        date_panel.add(timeend_panel, BorderLayout.LINE_END);

        JButton btn = new JButton();
        MyButton.styleButton(btn, Constants.DataPath + "/images/searching.png", new Dimension(30, 30));
        btn.setPreferredSize(new Dimension(55, 33));
        JPanel bottom_panel = new JPanel();
        bottom_panel.setBackground(Color.WHITE);
        bottom_panel.add(btn);
        date_panel.add(bottom_panel, BorderLayout.PAGE_END);

        add(date_panel, BorderLayout.PAGE_START);

        //CREATE TABLE PANEL
        JTable table = createTable(timestart_calender.getDate(), timeend_calender.getDate());
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(420, 400));

        JPanel table_panel = new JPanel(new BorderLayout());
        table_panel.setBackground(Color.WHITE);
        table_panel.add(scrollPane, BorderLayout.CENTER);
        add(table_panel, BorderLayout.CENTER);

        btn.addActionListener((e) -> {
            Component view = scrollPane.getViewport();
            if (view instanceof JTable) {
                scrollPane.remove(view);
            }
            try {
                scrollPane.setViewportView(createTable(timestart_calender.getDate(), timeend_calender.getDate()));
            } catch (IOException ex) {
            } catch (ParseException ex) {
            }
            revalidate();
            repaint();
        });

    }

    public static JTable createTable(Date start, Date end) throws FileNotFoundException, IOException, ParseException {
        String[] columnNames = null;
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        if (Dictionary.isEngToVie) {
            columnNames = new String[]{"Voc", "Time"};
        } else {
            columnNames = new String[]{"Từ vựng", "Thời gian"};
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        HashMap<String, Integer> table_data = new HashMap<String, Integer>();

        for (String key : Dictionary.history.keySet()) {
            //check is date is suitable
            Date date = format.parse(key);
            if (start.compareTo(date) <= 0 && date.compareTo(end) <= 0) {
                //check if exist
                String voc = Dictionary.history.get(key);
                var temp = table_data.get(voc);
                if (temp != null) {//if exist
                    temp = temp + 1;
                    table_data.put(voc, temp);
                } else {
                    table_data.put(voc, 1);
                }
            }
        }

        //add to model
        for (String key : table_data.keySet()) {
            Object[] row = {key, table_data.get(key)};
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
