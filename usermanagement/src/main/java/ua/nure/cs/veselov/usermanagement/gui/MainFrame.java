package ua.nure.cs.veselov.usermanagement.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ua.nure.cs.veselov.usermanagement.User;
import ua.nure.cs.veselov.usermanagement.db.DaoFactory;
import ua.nure.cs.veselov.usermanagement.db.UserDAO;
import ua.nure.cs.veselov.usermanagement.util.Messages;

public class MainFrame extends JFrame {
    
    private static final int FRAME_HEIGHT = 700;
    private static final int FRAME_WIDTH = 1000;
    
    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    private UserDAO dao;
    private EditPanel editPanel;
    
    public MainFrame() {
        super();        
        dao = DaoFactory.getInstance().getUserDAO();
        initialize();
    }
    
    private void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management")); //$NON-NLS-1$
        this.setContentPane(getContentPanel());
    }
    
    public UserDAO getUserDao() {
        return dao;
    }
    
    private JPanel getContentPanel() {
        
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel(), BorderLayout.CENTER);
        }
        return contentPanel;
    }
    
    private JPanel getBrowsePanel() {
        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }
        ((BrowsePanel) browsePanel).initializeTable();
        return browsePanel;
    }
    
    public void showAddPanel() {
        showPanel(getAddPanel());
    }
    
    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();  
    }
    
    public void showBrowsePanel() {
        showPanel(getBrowsePanel());    
    }
    
    public void showEditPanel(User user) {
        getEditPanel().setUser(user);
        showPanel(getEditPanel());    
    }
    
    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }
    
    private EditPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        return editPanel;
    }
    
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

}
