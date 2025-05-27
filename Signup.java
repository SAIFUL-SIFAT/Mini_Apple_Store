import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Signup extends JPanel
{
	public static final String cardName = "SIGNUPPANEL";

	private NextButton signupButton;
	private NextButton goBackToSinginButton;
	public Signup(MainPanel mainPanel)
	{

		this.setSize(1366, 1024);
		this.setLayout(null);
		this.setBackground(Color.WHITE);


		ImageIcon logoImage = new ImageIcon("logo.png");

		JLabel labela1 = new JLabel("Username:");
		labela1.setBounds(420,320,80,30);
		labela1.setFont(Utils.getFontSFPro(17f));
		this.add(labela1);
		final JTextField usernameField = new JTextField();
		usernameField.setBounds(420,350,240,40);
		usernameField.setFont(Utils.getFontSFPro(17f));
		this.add(usernameField);
		
		JLabel labelaN = new JLabel("Full Name:");
		labelaN.setBounds(690,320,80,30);
		labelaN.setFont(Utils.getFontSFPro(17f));
		this.add(labelaN);
		final JTextField fullnameField = new JTextField();
		fullnameField.setBounds(690,350,240,40);
		fullnameField.setFont(Utils.getFontSFPro(17f));
		this.add(fullnameField);

		JLabel labela2 = new JLabel("Email:");
		labela2.setBounds(420,400,180,30);
		labela2.setFont(Utils.getFontSFPro(17f));
		this.add(labela2);
		final JTextField emailField = new JTextField();
		emailField.setBounds(420,430,240,40);
		emailField.setFont(Utils.getFontSFPro(17f));
		this.add(emailField);

		JLabel labela3 = new JLabel("Phone No:");
		labela3.setBounds(690,400,80,30);
		labela3.setFont(Utils.getFontSFPro(17f));
		this.add(labela3);
		final JTextField phoneField = new JTextField();
		phoneField.setBounds(690,430,240,40);
		phoneField.setFont(Utils.getFontSFPro(17f));
		this.add(phoneField);

		JLabel labela4 = new JLabel("Address:");
		labela4.setBounds(420,480,80,30);
		labela4.setFont(Utils.getFontSFPro(17f));
		this.add(labela4);
		final JTextField addressField = new JTextField();
		addressField.setBounds(420,510,240,40);
		addressField.setFont(Utils.getFontSFPro(17f));
		this.add(addressField);

		JLabel labela5 = new JLabel("Password:");
		labela5.setBounds(690,480,180,30);
		labela5.setFont(Utils.getFontSFPro(17f));
		this.add(labela5);
		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(690,510,240,40);
		passwordField.setFont(Utils.getFontSFPro(17f));
		this.add(passwordField);

		JLabel labela6 = new JLabel("Confirm Password:");
		labela6.setBounds(420,560,150,30);
		this.add(labela6);
		labela6.setFont(new Font("", Font.PLAIN, 16));
		final JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(420,590,240,40);
		confirmPasswordField.setFont(Utils.getFontSFPro(17f));
		this.add(confirmPasswordField);

		JLabel labela7 = new JLabel();
		labela7.setBounds(570,730,220,30);
		labela7.setFont(Utils.getFontSFPro(17f));
		this.add(labela7);

		JLabel label8 = new JLabel("Create your Apple ID");
		label8.setFont(Utils.getFontSFPro(20f));
		label8.setBounds(30,60,220,30);
		this.add(label8);

		signupButton = new NextButton(mainPanel);
		signupButton.setText("Sign up");
		signupButton.setBounds(625,660,100,30);
		signupButton.setFocusable(false);
		signupButton.setFont(Utils.getFontSFPro(17f));
		signupButton.setBackground(new Color(0x00BFFF));
		this.add(signupButton);

		goBackToSinginButton = new NextButton(mainPanel);
		goBackToSinginButton.setText("Back to sign in");
		goBackToSinginButton.setBounds(1170,60,170,30);
		goBackToSinginButton.setFont(Utils.getFontSFPro(20f));
		goBackToSinginButton.setFocusable(false);
		goBackToSinginButton.setBackground(Color.WHITE);
		goBackToSinginButton.setBorderPainted(false);
		goBackToSinginButton.setContentAreaFilled(false);
		this.add(goBackToSinginButton);

		JPanel topPanel = new JPanel();
		topPanel.setBounds(0,0,1366,50);
		topPanel.setBackground(Color.BLACK);
		this.add(topPanel);

		JPanel midPanel = new JPanel();
		midPanel.setBounds(400,300,550,420);
		midPanel.setBackground(Color.WHITE);
		midPanel.setBorder(new RoundedBorder(20));
		this.add(midPanel);

		JPanel rearPanel = new JPanel();
		rearPanel.setBounds(20,100,1306,2);
		rearPanel.setBackground(Color.BLACK);
		this.add(rearPanel);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0,925,1366,60);
		bottomPanel.setBackground(Color.BLACK);
		this.add(bottomPanel);

		signupButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String username = usernameField.getText();
				String full_name = fullnameField.getText();
				String email = emailField.getText();
				String phone = phoneField.getText();
				String address = addressField.getText();
				String pass = new String(passwordField.getPassword());
				String pass1 = new String(confirmPasswordField.getPassword());

				if(username.equals("")|| full_name.equals("") || email.equals("")|| phone.equals("")|| address.equals("")|| pass.equals("")|| pass1.equals(""))
				{
					JOptionPane.showMessageDialog(mainPanel, "Please fill up all the information");
				}
				else
				{
					if(accountFunc.isUser(username) == 0)
					{
						if(phone.length()>9)
						{
							if(pass.equals(pass1))
							{
								if(pass.length()>5)
								{
									if(email.contains("@") && email.length()>10)
									{
									  if(pass.contains("@") || pass.contains("!") || pass.contains("$") || pass.contains("%") || pass.contains("^") || pass.contains("&") || pass.contains("*") || pass.contains("/") || pass.contains("\\"))
									  {
										accountFunc.signup(username,full_name, email, phone, address, pass);
										mainPanel.changePanel(Signin.cardName);
										JOptionPane.showMessageDialog(mainPanel, "Account created sucsessfully. Please sign in to continue.");
										usernameField.setText("");
										fullnameField.setText("");
										emailField.setText("");
										phoneField.setText("");
										addressField.setText("");
										passwordField.setText("");
										confirmPasswordField.setText("");
										//this.setVisible(false);
										//new Home();
									  }
									  else 
									  {
										JOptionPane.showMessageDialog(mainPanel, "Password must contain at least one special character");
									  }
									}
									else 
									{
										JOptionPane.showMessageDialog(mainPanel, "Invalid email");
									}
								}
								else
								{
									JOptionPane.showMessageDialog(mainPanel, "Password must be six characters long");
								}
							}
							else
							{
							  JOptionPane.showMessageDialog(mainPanel, "Password didn't matched");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(mainPanel, "Invalid Phone No.");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(mainPanel, "The username is taken. Please try another name");
					}
				}
			}
		});

		goBackToSinginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent b)
			{
				mainPanel.changePanel(Signin.cardName);
				//this.setVisible(false);
				//new Signin();
			}
		});
	}
	

	public static class RoundedBorder implements Border {

		private int radius;

		RoundedBorder(int radius) {
			this.radius = radius;
		}
		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
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
		Dimension arcs = new Dimension(radius,radius);
		int width = getWidth();
		int height = getHeight();
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.WHITE);
		graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height);
	}
}
		