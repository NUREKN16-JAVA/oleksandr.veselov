package ua.nure.cs.veselov.usermanagement.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.swing.JOptionPane;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DatabaseException;
import ua.nure.cs.veselov.usermanagement.util.Messages;

public class EditPanel extends AddPanel {

    private User user;

    public EditPanel(MainFrame parent) {
        super(parent);
        setName("editPanel"); //$NON-NLS-1$
    }
    
    @Override
    protected void doAction(ActionEvent e) throws ParseException {
        System.out.println(user);
        if ("ok".equalsIgnoreCase(e.getActionCommand())) { //$NON-NLS-1$
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                Date date = format.parse(getDateOfBirthField().getText());
                user.setDateOfBirth(date);
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                throw e1;
            }
            try {
                parent.getUserDao().update(user);
            } catch (DatabaseException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), Messages.getString("EditPanel.error"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
            }
        }
    }

    public void setUser(User user) {
        DateFormat format = DateFormat.getDateInstance();
        this.user = user;
        getFirstNameField().setText(user.getFirstName());
        getLastNameField().setText(user.getLastName());
        getDateOfBirthField().setText(format.format(user.getDateOfBirth()));
    }

}
