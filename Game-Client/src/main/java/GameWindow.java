import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Objects;

/**
 * I fear the day technology will surpass our human interaction. The world will have a generation of idiots. -Albert Einstein
 * Date: 6/5/2015
 * Time: 1:58 PM
 * @author Galkon
 */
public class GameWindow extends JFrame implements ActionListener {

    private static GameWindow instance;
    private Applet appletInstance;
    private JToolBar toolbar;

    public GameWindow(Applet applet) {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        this.setTitle(Configuration.NAME);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setFocusTraversalKeysEnabled(false);
        this.getContentPane().setBackground(Color.BLACK);
       // this.setUndecorated(true);

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gbl_contentPane);

        appletInstance = applet;
        appletInstance.init();
        appletInstance.setMinimumSize(new Dimension(765, 503));
        appletInstance.setPreferredSize(new Dimension(765, 503));

        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        this.getContentPane().add(appletInstance, gbc_panel);

        this.pack();
        this.setMinimumSize(this.getSize());
        this.setVisible(true);

        this.requestFocus();
        this.toFront();

        // use this to set start of window
        this.setLocationRelativeTo(null);

        setInstance(this);
        setClientIcon();
    }

    public static GameWindow getInstance() {
        return instance;
    }

    public static void setInstance(GameWindow instance) {
        GameWindow.instance = instance;
    }

    public static void main(String[] args) {
        final Client client = new Client();
        try {
            SwingUtilities.invokeAndWait(() -> {
                try {
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel(new SubstanceRuneLiteLookAndFeel());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> new GameWindow(client));
    }

    public JToolBar getToolbar() {
        return toolbar;
    }

    public void exit() {
        int confirm = JOptionPane.showOptionDialog(null, "Are you sure you want to exit", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (confirm == JOptionPane.YES_OPTION) {
            setTitle("Please wait, the Client is closing...");

            if (GameWindow.getInstance() != null) {
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }*/
            }
            System.exit(0);
        }
    }

    public void setFrameSize(int width, int height) {
        Insets insets = this.getInsets();
        this.setSize(width + insets.left + insets.right, height + insets.top + insets.bottom);
    }

    public int getFrameWidth() {
        Insets insets = this.getInsets();
        return getWidth() - (insets.left + insets.right);
    }

    public int getFrameHeight() {
        Insets insets = this.getInsets();
        return getHeight() - (insets.top + insets.bottom);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd == null) {
            return;
        }
    }

    public void setClientIcon() {
        try {
            Image image = Toolkit.getDefaultToolkit().getImage(Objects.requireNonNull(getClass().getResource("icon.png")));
            setIconImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
