import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.renderable.RenderableImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Component;
import javax.swing.JLabel;

public class adminSignin extends JPanel
{
	Frame parent;
    public adminSignin(Frame parent)
    {
		this.parent = parent;
		setBackground(new Color(211,211,211));
        setLayout(null);
		LeftPanel leftPanel = new LeftPanel();
		RightPanel rightPanel = new RightPanel();
        this.add(leftPanel);
    }
	
    private class LeftPanel extends JPanel
    {
		Point initialClick;
        public LeftPanel()
        {
			addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    initialClick = e.getPoint();
                    getComponentAt(initialClick);
                }
            });
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    int thisX = parent.getLocation().x;
                    int thisY = parent.getLocation().y;
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;
                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    parent.setLocation(X, Y);
                }
            });
			
            setBackground(Color.GRAY);
            setBounds(0,0,190,750);
            setLayout(null);
			
			JLabel labela = new JLabel("Sign is as admin:");
			labela.setBounds(10,50,150,30);
			labela.setFont(Utils.getFontSFPro(20f));
			add(labela);
			
			JLabel labela1 = new JLabel("Username:");
			labela1.setBounds(10,80,80,30);
			labela1.setFont(Utils.getFontSFPro(17f));
			add(labela1);
			final JTextField nameField = new JTextField();
			nameField.setBounds(10,110,170,30);
			nameField.setFont(Utils.getFontSFPro(20f));
			add(nameField);

			JLabel labela2 = new JLabel("Password:");
			labela2.setBounds(10,150,80,30);
			labela2.setFont(Utils.getFontSFPro(17f));
			add(labela2);
			final JPasswordField passwordField = new JPasswordField();
			passwordField.setBounds(10,180,170,30);
			passwordField.setFont(Utils.getFontSFPro(17f));
			add(passwordField);
			
			JLabel l1 = new JLabel();
			l1.setBounds(20,330,170,30);
			l1.setFont(Utils.getFontSFPro(17f));
			add(l1);
			
			BlueButton login = new BlueButton("Login", 25, new Dimension(90,30),new Color(105,105,105));
			login.setBounds(50,230,90,30);
			add(login);
			
			BlueButton signup = new BlueButton("Sign up", 25, new Dimension(90,30),new Color(105,105,105));
			signup.setBounds(50,280,90,30);
			add(signup);

            login.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    String admin = nameField.getText();
					String password = new String(passwordField.getPassword());
					
					if(admin.equals("")|| password.equals(""))
					{
						l1.setText("Fill all the information");
					}
					else
					{
						if(accountFunc.isAdmin(admin)>0)
						{
							parent.setVisible(false);
							new adminMain();
						}
						else
						{
							l1.setText("You are not an admin");
						}
					}
                    
                }
            });
			
			signup.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);	
				String admin = nameField.getText();
				String password = new String(passwordField.getPassword());

					if(admin.equals("")&& password.equals(""))
					{
						l1.setText("Fill all the information");
					}
					else if((accountFunc.isAdmin(admin)>0))
					{
						l1.setText("You are already an admin");
						l1.setBounds(10,330,180,30);
					}
					else
					{
						accountFunc.adminSignup(admin,password);
						nameField.setText("");
						passwordField.setText("");
						l1.setText("You are now an admin");
						signup.setVisible(false);
					}
				}
			});
			
            JLabel closeButton = new JLabel();
            closeButton.setIcon(new ImageIcon("res/close_filled.png"));
            closeButton.setBounds(10,10,17,17);

            add(closeButton);
			
            closeButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //System.exit(1);
					parent.setVisible(false);
					JFrame jf = new JFrame();
					jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					jf.setSize(Utils.defaultSize.width, Utils.defaultSize.height+30);
					jf.setLocationRelativeTo(null);
					jf.setIconImage(new ImageIcon("res/icons/apple_icon.png").getImage());
					jf.add(new MainPanel());
					jf.setVisible(true);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    closeButton.setIcon(new ImageIcon("res/close_cross.png"));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    closeButton.setIcon(new ImageIcon("res/close_filled.png"));
                }
            });
			
        }
    }
    private class RightPanel extends JPanel
    {
        public RightPanel()
        {
            setLayout(null);
            setBackground(Utils.superWhite);
            setBounds(190,0,1310,750);
			
			JLabel statistics = new JLabel("List of users");
			statistics.setBounds(650,20,180,30);
			statistics.setFont(Utils.getFontSFPro(20f));
			add(statistics);
			
		}

    }

}

