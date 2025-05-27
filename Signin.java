import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signin extends JPanel
{
	public static int id = 0;

	public static final String cardName = "SIGNINPANEL";
	NextButton signupButton;
	NextButton resetButton;
	//NextButton adminButton;
	BlueButton signinButton = null;
	public Signin(MainPanel mainPanel)
	{
		JLabel bg;
		this.setSize(1366, 1024);
		this.setLayout(null);
		this.setBackground(Utils.superWhite);


		//ImageIcon logoImage = new ImageIcon("login.png");

		JLabel label = new JLabel();
		label.setBounds(640,340,80,80);
		label.setIcon(Utils.getScaledImage("res/icons/apple.png", new Dimension(80,80), 1));
		label.setFont(Utils.getFontSFPro(20f));
		this.add(label);


		JLabel label1 = new JLabel("Username:");
		label1.setBounds(520,520,310,30);
		label1.setFont(Utils.getFontSFPro(20f));


		final JTextField usernameField = new JTextField("Username");
		usernameField.setBounds(520,520,310,30);
		usernameField.setFont(Utils.getFontSFPro(20f));
		usernameField.setBorder(null);
		usernameField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) {
				if(usernameField.getText().equals("Username"))
				{
					usernameField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(usernameField.getText().equals(""))
				{
					usernameField.setText("Username");
				}
			}

		});
		this.add(usernameField);

		JLabel label2 = new JLabel("Password:");
		label2.setBounds(520,570,310,30);
		label2.setFont(Utils.getFontSFPro(20f));


		final JPasswordField passwordField = new JPasswordField("Password");
		passwordField.setEchoChar((char)0);
		passwordField.setBounds(520,570,250,30);
		passwordField.setFont(Utils.getFontSFPro(20f));
		passwordField.setBorder(null);
		passwordField.addFocusListener(new FocusListener()
		{
			@Override
			public void focusGained(FocusEvent e) {
				if(String.valueOf(passwordField.getPassword()).equals("Password"))
				{
					passwordField.setEchoChar('*');
					passwordField.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(String.valueOf(passwordField.getPassword()).equals(""))
				{
					passwordField.setEchoChar((char)0);
					passwordField.setText("Password");
				}
			}

		});

		this.add(passwordField);

		JLabel visible = new JLabel();
		visible.setText("Show password");
		visible.setFont(Utils.getFontSFPro(16f));
		visible.setBounds(620,620,120,30);
		this.add(visible);
		visible.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				passwordField.setEchoChar((char)0);
				visible.setForeground(Utils.red);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!(String.valueOf(passwordField.getPassword())).equals("Password")) {passwordField.setEchoChar('*');}
				visible.setForeground(Utils.black);
			}
		});

		JLabel label3 = new JLabel("Apple ID");
		label3.setBounds(30,60,100,30);
		label3.setFont(Utils.getFontSFPro(20f));
		this.add(label3);

		signinButton = new BlueButton("", 50, new Dimension(35, 35), Utils.superWhite);
		signinButton.setBounds(800,573,35,35);
		signinButton.buttonText.setText("");
		signinButton.buttonText.setIcon(new ImageIcon("res/icons/signicon1.png"));

		this.add(signinButton);

		signupButton = new NextButton(mainPanel);
		signupButton.setText("Create your ID");
		signupButton.setBounds(1080,60,160,30);
		signupButton.setFocusable(false);
		signupButton.setBackground(Utils.superWhite);
		signupButton.setBorderPainted(false);
		signupButton.setFont(Utils.getFontSFPro(20f));
		signupButton.setContentAreaFilled(false);

		this.add(signupButton);

		JButton adminButton = new JButton();
		adminButton.setText("Admin");
		adminButton.setBounds(1240,60,100,30);
		adminButton.setFocusable(false);
		adminButton.setBackground(Utils.superWhite);
		adminButton.setBorderPainted(false);
		adminButton.setFont(Utils.getFontSFPro(20f));
		adminButton.setContentAreaFilled(false);

		this.add(adminButton);

		JLabel reset = new JLabel();
		reset.setText("Forgot password?");
		reset.setFont(Utils.getFontSFPro(20f));
		reset.setBounds(600,680,200,30);
		reset.setBackground(Utils.superWhite);
		reset.setForeground(Utils.blue);

		this.add(reset);


		JPanel topPanel = new JPanel();
		topPanel.setBounds(0,0,1366,50);
		topPanel.setBackground(Utils.black);
		this.add(topPanel);

		JPanel linePanel = new JPanel();
		linePanel.setBounds(500,560,350,1);
		linePanel.setBackground(Utils.black);
		this.add(linePanel);

		JPanel midPanel = new JPanel();
		midPanel.setBounds(500,500,350,120);
		midPanel.setBackground(Utils.superWhite);
		midPanel.setBorder(new RoundedBorder(20));
		this.add(midPanel);

		JPanel rearPanel = new JPanel();
		rearPanel.setBounds(20,100,1306,2);
		rearPanel.setBackground(Utils.black);
		this.add(rearPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0,925,1366,60);
		bottomPanel.setBackground(Utils.black);
		this.add(bottomPanel);

		signinButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				String userName = usernameField.getText();
				String passWord = new String(passwordField.getPassword());

				id = accountFunc.isLoggedin(userName,passWord);
				if(id > 0)
				{
					Thread newThread = new Thread(() -> {
						AccountPanel.profileImageIcon = Utils.getScaledImage("res_user/" + Signin.id + ".jpg", new Dimension(150,150),1);
					});
					newThread.start();

					new SpinnerPanel((MainPanel)getParent(),1,Home.cardName);

					AccountPanel.name = accountFunc.getUserDetail(Signin.id, "full_name");
					AccountPanel.email = accountFunc.getUserDetail(Signin.id, "email");

					usernameField.setText("Username");
					passwordField.setText("");

				}
				else
				{
					JOptionPane.showMessageDialog(mainPanel, "Invalid username or password");
					//resetButton.setVisible(true);
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setCursor(Utils.handCursor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setCursor(Utils.defaultCursor);
			}
		});

		signupButton.addActionListener(e->
		{
			mainPanel.changePanel(Signup.cardName);

		});

		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mainPanel.changePanel(Reset.cardName);
			}
		});
		Signin.this.addAncestorListener(new AncestorListener() { //interface
			@Override
			public void ancestorAdded(AncestorEvent event) {
				//System.out.println("fired");
				visible.requestFocusInWindow();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {

			}

			@Override
			public void ancestorMoved(AncestorEvent event) {

			}
		});

		adminButton.addActionListener(e->
		{
			// create admin gui here
			new signinMain();
			SwingUtilities.getWindowAncestor(this).setVisible(false);
		});
	}

	public static class RoundedBorder implements Border {

		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}
		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius, this.radius, this.radius, this.radius);
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
			g.drawRoundRect(x,y,width-1,height-1,radius,radius);

		}

	}
	private int radius = 25;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Utils.superWhite);
		graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	}
}