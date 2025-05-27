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

public class Reset extends JPanel
{

	private NextButton resetButton;
	public static final String cardName = "RESETPANEL";
	public Reset(MainPanel mainPanel)
	{
		this.setSize(1366, 1024);
		this.setLayout(null);

		ImageIcon logoImage = new ImageIcon("logo.png");

		JLabel labelb1 = new JLabel("Username:");
		labelb1.setBounds(520,410,310,30);
		labelb1.setFont(new Font("", Font.PLAIN, 16));
		this.add(labelb1);

		final JTextField usernameField = new JTextField();
		usernameField.setBounds(520,440,310,40);
		usernameField.setFont(new Font("", Font.PLAIN, 16));
		this.add(usernameField);

		JLabel labelb2 = new JLabel("New Password:");
		labelb2.setBounds(520,490,310,30);
		labelb2.setFont(new Font("", Font.PLAIN, 16));
		this.add(labelb2);

		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(520,520,310,40);
		passwordField.setFont(new Font("", Font.PLAIN, 16));
		this.add(passwordField);

		JLabel labelb3 = new JLabel("Confirm Password:");
		labelb3.setBounds(520,570,150,30);
		labelb3.setFont(new Font("", Font.PLAIN, 16));
		this.add(labelb3);

		final JPasswordField confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(520,600,310,40);
		confirmPasswordField.setFont(new Font("", Font.PLAIN, 16));
		this.add(confirmPasswordField);

		JLabel labelb4 = new JLabel();
		labelb4.setBounds(205,340,150,30);
		this.add(labelb4);

		JLabel label5 = new JLabel("Reset your password");
		label5.setBounds(30,60,220,30);
		label5.setFont(Utils.getFontSFPro(20f));
		this.add(label5);

		resetButton = new NextButton(mainPanel);
		resetButton.setText("Reset");
		resetButton.setBounds(630,660,80,30);
		resetButton.setFont(new Font("", Font.PLAIN, 16));
		resetButton.setBackground(new Color(0x00BFFF));
		this.add(resetButton);

		JButton BackToSinginButton = new NextButton(mainPanel);
		BackToSinginButton.setText("Back to sign in?");
		BackToSinginButton.setBounds(1120,60,200,30);
		BackToSinginButton.setFont(Utils.getFontSFPro(20f));
		BackToSinginButton.setFocusable(false);
		BackToSinginButton.setBackground(Color.WHITE);
		BackToSinginButton.setBorderPainted(false);
		
		this.add(BackToSinginButton);

		JPanel topPanel = new JPanel();
		topPanel.setBounds(0,0,1366,50);
		topPanel.setBackground(Color.BLACK);
		this.add(topPanel);

		JPanel midPanel = new JPanel();
		midPanel.setBounds(500,390,350,320);
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

		resetButton.addActionListener(e->
		{
			String username = usernameField.getText();
			String password =  new String(passwordField.getPassword());
			String password1 =  new String(confirmPasswordField.getPassword());

			if(password.equals(password1))
			{
				accountFunc.resetPassword(username, password);
				mainPanel.changePanel(Signin.cardName);

				usernameField.setText("");
				passwordField.setText("");
				confirmPasswordField.setText("");

				if(accountFunc.isUser(username) == 0)
				{
					JOptionPane.showMessageDialog(this, "User not found");
					mainPanel.changePanel(Reset.cardName);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Password didn't matched");
			}


		});

		BackToSinginButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent b)
			{
				mainPanel.changePanel(Signin.cardName);
				
				
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