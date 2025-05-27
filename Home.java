import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Home extends JPanel
{
	public static final String cardName = "HOMEPANEL";
	
	public static int id = 0;
	private MainPanel mainPanel = null;
	public Home(MainPanel mainPanel)
	{
		this.mainPanel = mainPanel;
		setLayout(new CardLayout());
		setPreferredSize(Utils.defaultSize);

		ProductPage productPage = new ProductPage(this);
		CategoryPanel categoryPanel = new CategoryPanel(this, productPage);

		add(categoryPanel, CategoryPanel.cardName);
		add(productPage, ProductPage.cardName);


	}
	public void changePanel(String cardName)
	{
		CardLayout cl = (CardLayout)getLayout();
		cl.show(this, cardName);
	}
	public void changeParentPanel(String cardName)
	{
		mainPanel.changePanel(cardName);
	}

	public class CategoryPanel extends JPanel
	{
		public static final String cardName = "CATEGORYPANEL";
		protected ProductPage productPage = null;
		protected Home home = null;

		public CategoryPanel(Home home, ProductPage productPage)
		{
			this.productPage = productPage;
			this.home = home;

			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

			add(new TopPanel());
			add(new MiddlePanel());
			add(new BottomPanel());

		}
		public class MiddlePanel extends JPanel
		{
			public MiddlePanel()
			{
				setLayout(new FlowLayout(FlowLayout.LEFT, 10,10));
				setPreferredSize(new Dimension(Utils.defaultSize.width, Utils.defaultSize.height-119));
				setBackground(Utils.graygray);

				this.add(new CatagoryPanel("mac", "Mac", "res/mac/default.png", 1, productPage, home));
				this.add(new CatagoryPanel("iphone", "iPhone", "res/iphone/default.png",0.7, productPage, home));
				this.add(new CatagoryPanel("airpods", "Airpods", "res/airpod/default.jpg",0.7, productPage, home));
				this.add(new CatagoryPanel("ipad", "iPad","res/ipad/Images/1.jpg",0.7, productPage, home));
				this.add(new CatagoryPanel("applewatch","Apple Watch", "res/applewatch/default.jpg",0.7, productPage, home));
				this.add(new CatagoryPanel("homepod","Homepod", "res/homepod/default.jpg",1, productPage, home));

				setBorder(new EmptyBorder(30,55,50,30));
			}
		}
		public class TopPanel extends JPanel
		{
			public TopPanel()
			{
				setLayout(new GridBagLayout());
				setBackground(Utils.black);
				setPreferredSize(new Dimension(Utils.defaultSize.width, 50));
				JLabel title = new JLabel("<html><b>Shop</b></html>", JLabel.CENTER);
				//JLabel title = new JLabel("", JLabel.LEFT);
				title.setFont(Utils.getFontSFPro(17f));
				title.setForeground(Utils.superWhite);
				add(title);
			}
		}
		public class BottomPanel extends JPanel
		{
			public BottomPanel()
			{
				setLayout(new GridLayout(1, 4));
				setBackground(Utils.black);
				setPreferredSize(new Dimension(Utils.defaultSize.width, 60));

				JLabel shopLabel = new JLabel("<html><b>Shop</b></html>", JLabel.CENTER);
				shopLabel.setIcon(Utils.getScaledImage("res/icons/bag_blue.png", new Dimension(20,20), 1));
				shopLabel.setForeground(Utils.blue);
				shopLabel.setFont(Utils.getFontSFPro(15f));

				JLabel accountLabel = new JLabel("<html><b>Account</b></html>", JLabel.CENTER);
				accountLabel.setIcon(Utils.getScaledImage("res/icons/settings_white.png", new Dimension(20,20), 1));
				accountLabel.setForeground(Utils.white);
				accountLabel.setFont(Utils.getFontSFPro(15f));


				JLabel cartLabel = new JLabel("<html><b>Cart</b></html>", JLabel.CENTER);
				cartLabel.setIcon(Utils.getScaledImage("res/icons/orders_white.png", new Dimension(20,20), 1));
				cartLabel.setForeground(Utils.white);
				cartLabel.setFont(Utils.getFontSFPro(15f));

				JLabel logoutLabel = new JLabel("<html><b>Logout</b></html>", JLabel.CENTER);
				logoutLabel.setIcon(Utils.getScaledImage("res/icons/logout_white.png", new Dimension(20,20), 1));
				logoutLabel.setForeground(Utils.white);
				logoutLabel.setFont(Utils.getFontSFPro(15f));

				accountLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						mainPanel.changePanel(AccountPanel.cardName);
					}
					@Override
					public void mouseEntered(MouseEvent me) {
						setCursor(Utils.handCursor);
					}

					@Override
					public void mouseExited(MouseEvent me) {
						setCursor(Utils.defaultCursor);
					}
				});

				cartLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						mainPanel.changePanel(CartPanel.cardName);
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

				logoutLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						mainPanel.changePanel(Signin.cardName);
						Signin.id = 0;
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						super.mouseEntered(e);
						setCursor(Utils.handCursor);
						logoutLabel.setIcon(Utils.getScaledImage("res/icons/logout_red.png", new Dimension(20,20), 1));
						logoutLabel.setForeground(Utils.red);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						super.mouseExited(e);
						setCursor(Utils.defaultCursor);
						logoutLabel.setIcon(Utils.getScaledImage("res/icons/logout_white.png", new Dimension(20,20), 1));
						logoutLabel.setForeground(Utils.white);
					}
				});

				add(shopLabel);
				add(accountLabel);
				add(cartLabel);
				add(logoutLabel);
			}
		}
	}

}