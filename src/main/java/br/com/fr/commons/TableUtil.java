/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author romeu
 */
public class TableUtil {
    
    private final JTable table;
    private final DefaultService service;
    
    public TableUtil(JTable table, DefaultService service) {
        this.table = table;
        this.service = service;
    }
    
    public void updateTable() {
        
        if (service == null || table == null) {
            return;
        }
        
        Object[][] data = service.getData();
        
        DefaultTableModel tableModel = new DefaultTableModel(service.getColumnNames(), data);
        TableRowSorter<TableModel> tableSorter = new TableRowSorter<TableModel>(tableModel);

        table.setModel(tableModel);
        table.setRowSorter(tableSorter);
        table.getTableHeader().setFont(table.getFont().deriveFont(Font.BOLD));

        int[] sizes = service.getColumnSizes();

        for (int col = 0; col < sizes.length; col++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(col);
            int fontSize = table.getFont().getSize();

            if (sizes[col] == 0) {
                tableColumn.setMaxWidth(0);
                tableColumn.setMinWidth(0);
                tableColumn.setResizable(false);
            }
            tableColumn.setPreferredWidth(sizes[col] * fontSize);
        }

        int currentSelectedRow = table.getSelectedRow();

        table.requestFocus();
        if (tableModel.getRowCount() > 0) {
            if (currentSelectedRow >= 0 && currentSelectedRow < table.getRowCount()) {
                table.setRowSelectionInterval(currentSelectedRow, currentSelectedRow);

            } else {
                table.setRowSelectionInterval(0, 0);
            }
        }
        table.doLayout();
    }

    public Object[] getSelectedRow() {
        
        if (service == null || table == null) {
            return null;
        }
        
        Object[] selectedRowData = new Object[table.getModel().getColumnCount()];

        int selectedRow = table.getSelectedRow();

        if (selectedRow < 0) {
            return null;

        } else {

            int row = table.convertRowIndexToModel(selectedRow);
            for (int col = 0; col < table.getModel().getColumnCount(); col++) {
                selectedRowData[col] = table.getModel().getValueAt(row, col);
            }

            return selectedRowData;
        }
    }
    
}
