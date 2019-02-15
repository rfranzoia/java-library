/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.commons;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author romeu
 */
public class DefaultTableModel extends AbstractTableModel {
    private String[] columns    = null;
    private Object[][] data     = null;

    public DefaultTableModel(String[] columns, Object[][] data) {
        this.columns    = columns;
        this.data       = data;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (getValueAt(0, column) != null) {
            return getValueAt(0, column).getClass();
        } else {
            return String.class;
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int col) {
        return columns[col];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < data.length && rowIndex >= 0) {
            return data[rowIndex][columnIndex];
        } else {
            return null;
        }
    }

    public void setValueAt(String value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col < (columns.length - 1)) {
            return false;
        } else {
            return true;
        }
    }
}
