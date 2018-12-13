package ua.nure.cs.veselov.usermanagement.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DatabaseException;
import ua.nure.cs.veselov.usermanagement.util.Messages;

public class BrowsePanel extends JPanel implements ActionListener {
    
    private MainFrame parent;
    private JScrollPane tablePanel;
    private JPanel buttonPanel;
    private JButton detailsButton;
    private JButton deleteButton;
    private JButton editButton;
    private JButton addButton;
    private JTable userTable;
    
    private final static int[] COLUMN_WIDTHS = { 25, 300, 300 };
    private final static int CELL_HEIGHT = 30;

    public BrowsePanel(MainFrame frame) {
        parent = frame;
        initialize();
    }
    
    private void initialize() {
        this.setName("browsePanel"); //$NON-NLS-1$
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);
    }
    
    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }
    
    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton(), null);
            buttonPanel.add(getEditButton(), null);
            buttonPanel.add(getDeleteButton(), null);
            buttonPanel.add(getDetailsButton(), null);
        }
        return buttonPanel;
    }
    
    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable"); //$NON-NLS-1$
            
        }
        return userTable;
    }
    
    public void initializeTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getUserDao().findall());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<>());
            JOptionPane.showMessageDialog(this, e.getMessage(), Messages.getString("BrowsePanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
        }
        getUserTable().setModel(model);
        
        userTable.setRowHeight(CELL_HEIGHT);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < userTable.getColumnCount(); ++i) {
            userTable.getColumnModel().getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
            userTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
    
    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details")); //$NON-NLS-1$
            detailsButton.setName("detailsButton");  //$NON-NLS-1$
            detailsButton.setActionCommand("details"); //$NON-NLS-1$
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete")); //$NON-NLS-1$
            deleteButton.setName("deleteButton"); //$NON-NLS-1$
            deleteButton.setActionCommand("delete"); //$NON-NLS-1$
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit")); //$NON-NLS-1$
            editButton.setName("editButton"); //$NON-NLS-1$
            editButton.setActionCommand("edit"); //$NON-NLS-1$
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add")); //$NON-NLS-1$
            addButton.setName("addButton"); //$NON-NLS-1$
            addButton.setActionCommand("add"); //$NON-NLS-1$
            addButton.addActionListener(this);
        }
        return addButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
            this.setVisible(false);
            parent.showAddPanel();
        } else if ("edit".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                                              Messages.getString("BrowsePanel.selectUserError"), //$NON-NLS-1$
                                              Messages.getString("BrowsePanel.editUserHeader"), //$NON-NLS-1$
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            User user = ((UserTableModel) userTable.getModel()).getUser(selectedRow);
            this.setVisible(false);
            parent.showEditPanel(user);
        } else if ("delete".equalsIgnoreCase(actionCommand)) { //$NON-NLS-1$
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                                              Messages.getString("BrowsePanel.selectUserError"), //$NON-NLS-1$
                                              Messages.getString("BrowsePanel.deleteUserHeader"), //$NON-NLS-1$
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int choose = JOptionPane.showConfirmDialog((Component) this,
                                                       Messages.getString("BrowsePanel.deleteConfirmation"), //$NON-NLS-1$
                                                       Messages.getString("BrowsePanel.deleteHeader"), //$NON-NLS-1$
                                                       JOptionPane.OK_CANCEL_OPTION);
            if (choose != 0) {
                return;
            }
            try {
                parent.getUserDao().delete(((UserTableModel) userTable.getModel()).getUser(selectedRow));
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this,
                                              e1.getMessage(), 
                                              Messages.getString("BrowsePanel.error"),  //$NON-NLS-1$
                                              JOptionPane.ERROR_MESSAGE);
            }
            initializeTable();
            return;
        } else if (actionCommand.equalsIgnoreCase("details")) { //$NON-NLS-1$
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this,
                                              Messages.getString("BrowsePanel.selectUserError"),  //$NON-NLS-1$
                                              Messages.getString("BrowsePanel.detailsLabel"), //$NON-NLS-1$
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                User selectedUser = ((UserTableModel) userTable.getModel()).getUser(selectedRow);
                String message = Messages.getString("BrowsePanel.fullNameLabel") + //$NON-NLS-1$
                                 selectedUser.getFullName() +
                                 Messages.getString("BrowsePanel.newLine") + //$NON-NLS-1$
                                 Messages.getString("BrowsePanel.dateOfBirthLabel") + //$NON-NLS-1$
                                 selectedUser.getDateOfBirth();
                
                JOptionPane.showMessageDialog(this,
                                              message,
                                              Messages.getString("BrowsePanel.userInfoHeader"), //$NON-NLS-1$
                                              JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this,
                                              exception.getMessage(),
                                              Messages.getString("BrowsePanel.error"), //$NON-NLS-1$
                                              JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}

