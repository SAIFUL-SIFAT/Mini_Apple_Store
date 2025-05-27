import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.*;
import java.awt.*;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.MaskFormatter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class AccountPanel extends JPanel
{
	public static String name = "";
	public static String email = "";
	public static final String cardName = "ACCOUNTPANEL";
	public static ImageIcon profileImageIcon = null;
	public ResultSet orderHistorySet = null;
	private JLabel nameLabel = null;
	private JLabel emailLabel = null;
	protected MainPanel mainPanel;
	String balance = "";
	DefaultTableModel defaultTableModel;
	MainAccountPanel.RightPanel.ordersPanel _ordersPanel;

	public AccountPanel(MainPanel mainPanel)
	{
		this.mainPanel = mainPanel;
		setPreferredSize(Utils.defaultSize);
		setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));

		add(new TopGrayPanel());
		add(new TopPanel());
		add(new MainAccountPanel());
		add(new BottomPanel());
	}
	public class TopGrayPanel extends JPanel
	{
		public TopGrayPanel()
		{
			setLayout(new GridBagLayout());
			setBackground(Utils.black);
			setPreferredSize(new Dimension(Utils.defaultSize.width, 50));
			JLabel title = new JLabel("<html><b>Account</b></html>", JLabel.CENTER);
			title.setFont(Utils.getFontSFPro(17f));
			title.setForeground(Utils.superWhite);
			add(title);
		}
	}
	public class TopPanel extends JPanel
	{
		public TopPanel()
		{
			setBackground(Utils.graygray);
			setPreferredSize(new Dimension(Utils.defaultSize.width,300));
			setLayout(new GridBagLayout());
			add(new ContentsTopPanel());

		}
		private class ContentsTopPanel extends JPanel
		{
			BufferedImage image = null;

			JPanel photoPanel = null;
			public ContentsTopPanel()
			{
				setLayout(new BoxLayout(ContentsTopPanel.this, BoxLayout.Y_AXIS));
				setBackground(Utils.graygray);


				photoPanel = new JPanel(){
					@Override
					protected void paintComponent(Graphics grphcs) {
						super.paintComponent(grphcs);
						Graphics2D g2 = (Graphics2D) grphcs;
						if(profileImageIcon != null) {
							int w = profileImageIcon.getIconWidth();
							int h = profileImageIcon.getIconHeight();
							image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
							Graphics2D g2_img = image.createGraphics();
							g2_img.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
							g2_img.fillOval(0,0, w, h);
							Composite composite = g2_img.getComposite();
							g2_img.setComposite(AlphaComposite.SrcIn);
							g2_img.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
							g2_img.drawImage(profileImageIcon.getImage(), 0,0, w, h, null);
							g2_img.setComposite(composite);
							g2_img.dispose();
							g2.drawImage(image, 0, 0, null);
						}
					}};

				photoPanel.setOpaque(false);
				photoPanel.setPreferredSize(new Dimension(150,150));
				photoPanel.setMaximumSize(new Dimension(150,150));
				photoPanel.setMinimumSize(new Dimension(150,150));
				//photoPanel.setBackground(Color.GRAY);
				photoPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						FileDialog fileDialog = new FileDialog((Frame)null, "Choose photo", FileDialog.LOAD);
						fileDialog.setDirectory("C:\\");
						fileDialog.setFile("*.jpg");
						fileDialog.setVisible(true);
						if((fileDialog.getFile()) != null)
						{
							try {
								Files.copy(Paths.get(fileDialog.getDirectory() + fileDialog.getFile()), Paths.get("res_user/" + Signin.id + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
								profileImageIcon = Utils.getScaledImage("res_user/" + Signin.id + ".jpg", photoPanel.getPreferredSize(),1);
								photoPanel.repaint();
							} catch (IOException ex) {
								throw new RuntimeException(ex);
							}
						}

					}
					@Override
					public void mouseEntered(MouseEvent e) {
						super.mouseEntered(e);
						photoPanel.setCursor(Utils.handCursor);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						super.mouseExited(e);
						photoPanel.setCursor(Utils.defaultCursor);
					}
				});

				nameLabel = new JLabel();
				nameLabel.setVerticalAlignment(JLabel.CENTER);
				nameLabel.setHorizontalAlignment(JLabel.CENTER);
				nameLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
				nameLabel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
				nameLabel.setFont(Utils.getFontSFPro(20f));
				nameLabel.setForeground(Utils.white);

				emailLabel = new JLabel();
				emailLabel.setFont(Utils.getFontSFPro(18f));
				emailLabel.setVerticalAlignment(JLabel.CENTER);
				emailLabel.setHorizontalAlignment(JLabel.CENTER);
				emailLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
				emailLabel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
				emailLabel.setForeground(Utils.white.darker());

				add(photoPanel);
				add(Box.createRigidArea(new Dimension(0, 7)));
				add(nameLabel);
				add(emailLabel);

				AccountPanel.this.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentShown(ComponentEvent e) {
						super.componentShown(e);
						updateProfilePanel();
						ContentsTopPanel.this.repaint();
					}
				});
			}
			public void updateProfilePanel()
			{
				//profileImageIcon = Utils.getScaledImage("res_user/" + Signin.id + ".jpg", photoPanel.getPreferredSize(),1);

				nameLabel.setText("<html><b>" + AccountPanel.name + "</b></html>");
				emailLabel.setText("<html>" + AccountPanel.email + "</html>");
			}
		}
	}
	private class MainAccountPanel extends JPanel
	{
		private RightPanel rightPanel;
		public MainAccountPanel()
		{
			setPreferredSize(new Dimension(Utils.defaultSize.width, 614-39));
			setBackground(new Color(255,255,255));
			rightPanel = new RightPanel();
			LeftPanel leftPanel = new LeftPanel(rightPanel);
			add(leftPanel);
			add(rightPanel);

		}
		public class LeftPanel extends JPanel
		{
			public LeftPanel(RightPanel rightPanel)
			{
				setLayout(null);
				setBackground(new Color(255,255,255));
				setPreferredSize(new Dimension(400,580));
				
				JLabel details = new JLabel("<html><b>Account Details</b><html>");
				details.setIcon(new ImageIcon("res/icons/account_profile.png"));
				details.setBounds(0,20,200,30);
				details.setForeground(Utils.blue);
				details.setFont(Utils.getFontSFPro(20f));
				add(details);
				
				JLabel orders = new JLabel("<html><b>Orders</b><html>");
				orders.setIcon(new ImageIcon("res/icons/account_orders.png"));
				orders.setBounds(0,170,250,30);
				orders.setFont(Utils.getFontSFPro(20f));
				add(orders);
				
				JLabel payment = new JLabel("<html><b>Payment Methods</b><html>");
				payment.setIcon(new ImageIcon("res/icons/account_payment.png"));
				payment.setBounds(0,120,200,30);
				payment.setFont(Utils.getFontSFPro(20f));
				add(payment);
				
				JLabel credits = new JLabel("<html><b>About</b><html>");
				credits.setIcon(new ImageIcon("res/icons/account_about.png"));
				credits.setBounds(0,220,200,30);
				credits.setFont(Utils.getFontSFPro(20f));
				add(credits);
				
				JLabel security = new JLabel("<html><b>Password and Security</b><html>");
				security.setIcon(new ImageIcon("res/icons/secure-icon.png"));
				security.setBounds(0,70,250,30);
				security.setFont(Utils.getFontSFPro(20f));
				add(security);

				details.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						details.setForeground(Utils.blue);
						orders.setForeground(Utils.black);
						payment.setForeground(Utils.black);
						credits.setForeground(Utils.black);
						security.setForeground(Utils.black);
						rightPanel.changePanel(AccountPanel.MainAccountPanel.RightPanel.acPanel.cardName);
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
				
				orders.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						super.mouseClicked(e);
						details.setForeground(Utils.black);
						orders.setForeground(Utils.blue);
						payment.setForeground(Utils.black);
						credits.setForeground(Utils.black);
						security.setForeground(Utils.black);
						rightPanel.changePanel(RightPanel.ordersPanel.cardName);
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
				
				payment.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						details.setForeground(Utils.black);
						orders.setForeground(Utils.black);
						payment.setForeground(Utils.blue);
						credits.setForeground(Utils.black);
						security.setForeground(Utils.black);
						rightPanel.changePanel(AccountPanel.MainAccountPanel.RightPanel.paymentPanel.cardName);
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
				
				credits.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						details.setForeground(Utils.black);
						orders.setForeground(Utils.black);
						payment.setForeground(Utils.black);
						credits.setForeground(Utils.blue);
						security.setForeground(Utils.black);
						rightPanel.changePanel(AccountPanel.MainAccountPanel.RightPanel.creditPanel.cardName);
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
				
				security.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						details.setForeground(Utils.black);
						orders.setForeground(Utils.black);
						payment.setForeground(Utils.black);
						credits.setForeground(Utils.black);
						security.setForeground(Utils.blue);
						rightPanel.changePanel(AccountPanel.MainAccountPanel.RightPanel.securityPanel.cardName);
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
				
				AccountPanel.this.addComponentListener(new ComponentAdapter() {
					@Override
					public void componentShown(ComponentEvent e) {
						super.componentShown(e);
						details.setForeground(Utils.blue);
						orders.setForeground(Utils.black);
						payment.setForeground(Utils.black);
						credits.setForeground(Utils.black);
						rightPanel.changePanel(RightPanel.acPanel.cardName);
					}
				});

			}
		}

		public class RightPanel extends JPanel
		{
			JLabel balanceLabel;
			 public void changePanel(String cardName)
            {
                CardLayout cl = (CardLayout)getLayout();
                cl.show(this, cardName);
            }
			
			public RightPanel()
			{
				setLayout(new CardLayout());
				setBackground(Color.GREEN);
				setPreferredSize(new Dimension(830,580));
				acPanel acpanel = new acPanel();
				add(acpanel, acPanel.cardName);
				_ordersPanel = new ordersPanel();
				add(_ordersPanel, ordersPanel.cardName);
				add(new paymentPanel(), paymentPanel.cardName);
				add(new creditPanel(), creditPanel.cardName);
				add(new securityPanel(), securityPanel.cardName);

			}
			public class  acPanel extends JPanel
			{
				String fullName = null;
				String email = null;
				String phone = null;
				String address = null;
				JTextField fullNameField = null;
				JTextField emailField = null;
				JTextField phoneField  = null;
				JTextField addressField = null;

				public static final String cardName = "ACPANEL";
				public acPanel()
				{
					
					setBackground(Color.WHITE);
					setPreferredSize(new Dimension(830,580));
					setLayout(null);
					BalancePanel balancePanel = new BalancePanel();
					this.add(balancePanel);
					/*JLabel label = new JLabel("Account Details");
					label.setBounds(20,20,300,30);
					label.setFont(Utils.getFontSFPro(20f));
					this.add(label);*/
					
					JLabel fullNameLabel = new JLabel("Full Name:");
					fullNameLabel.setBounds(20,20,200,30);
					fullNameLabel.setFont(Utils.getFontSFPro(18f));
					this.add(fullNameLabel);
					fullNameField = new JTextField(fullName);
					fullNameField.setBounds(20,50,240,30);
					fullNameField.setFont(Utils.getFontSFPro(18f));
					fullNameField.setEditable(false);
					this.add(fullNameField);

					JLabel emailLabel = new JLabel("Email:");
					emailLabel.setBounds(20,100,200,30);
					emailLabel.setFont(Utils.getFontSFPro(18f));
					this.add(emailLabel);
					emailField = new JTextField(email);
					emailField.setBounds(20,130,240,30);
					emailField.setFont(Utils.getFontSFPro(18f));
					emailField.setEditable(false);
					this.add(emailField);

					JLabel phoneLabel = new JLabel("Phone No:");
					phoneLabel.setBounds(20,180,180,30);
					phoneLabel.setFont(Utils.getFontSFPro(18f));
					this.add(phoneLabel);
					phoneField = new JTextField(phone);
					phoneField.setBounds(20,210,240,30);
					phoneField.setFont(Utils.getFontSFPro(18f));
					phoneField.setEditable(false);
					this.add(phoneField);

					JLabel addressLabel = new JLabel("Address:");
					addressLabel.setBounds(20,260,80,30);
					addressLabel.setFont(Utils.getFontSFPro(18f));
					this.add(addressLabel);
					addressField = new JTextField(address);
					addressField.setBounds(20,290,240,30);
					addressField.setFont(Utils.getFontSFPro(18f));
					addressField.setEditable(false);
					this.add(addressField);
					
					BlueButton changeD = new BlueButton("Change Details", 15, new Dimension(150,30),Utils.green);
					changeD.setBounds(20,350,150,30);
					//changeD.setFocusable(false);

					BlueButton changeConf = new BlueButton("Update Details", 15, new Dimension(150,30),Utils.green);
					changeConf.setBounds(20,400,150,30);
					changeConf.setEnabled(false);

					this.add(changeD);
					this.add(changeConf);

					MouseAdapter changeDetailsMouseAdapter = new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							super.mouseClicked(e);
							changeD.setEnabled(false);
							changeConf.setEnabled(true);
							fullNameField.setEditable(true);
							emailField.setEditable(true);
							phoneField.setEditable(true);
							addressField.setEditable(true);
						}
					};

					MouseAdapter updateDetailsMouseAdapter = new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							super.mouseClicked(e);
							changeD.setEnabled(true);
							changeConf.setEnabled(false);
							JOptionPane.showMessageDialog(mainPanel, "Account updated successfully");
							fullNameField.setEditable(false);
							emailField.setEditable(false);
							phoneField.setEditable(false);
							addressField.setEditable(false);

							String fullName = fullNameField.getText();
							String email = emailField.getText();
							String phone = phoneField.getText();
							String address = addressField.getText();
							
							if(email.contains("@") && email.length()>10)
							{
								if(phone.length()>9)
								{
									if(fullName.equals("") || email.equals("") || phone.equals("") || address.equals(""))
									{
										JOptionPane.showMessageDialog(mainPanel, "Please fill up all the information");
									}
									else
									{
										accountFunc.updateUserDetails(fullName, email, phone, address);
										AccountPanel.name = fullName;
										AccountPanel.email = email;
										AccountPanel.this.nameLabel.setText("<html><b>" + AccountPanel.name + "</b></html>");
										AccountPanel.this.emailLabel.setText("<html>" + AccountPanel.email + "</html>");
									}
								}
								else
								{
									JOptionPane.showMessageDialog(mainPanel, "Invalid phone number");
								}
							}
							else
							{
								JOptionPane.showMessageDialog(mainPanel, "Invalid email");
							}
						}
					};
					
					changeD.addMouseListener(changeDetailsMouseAdapter);
					changeConf.addMouseListener(updateDetailsMouseAdapter);

					AccountPanel.this.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentShown(ComponentEvent e) {
							super.componentShown(e);
							setupAcPanel();
							changeD.setEnabled(true);
							changeConf.setEnabled(false);
							fullNameField.setEditable(false);
							emailField.setEditable(false);
							phoneField.setEditable(false);
							addressField.setEditable(false);
						}
					});
				}
				public void setupAcPanel()
				{
					//ResultSet result = null;
					fullName = accountFunc.getUserDetail(Signin.id, "full_name");
					email = accountFunc.getUserDetail(Signin.id, "email");
					phone = accountFunc.getUserDetail(Signin.id, "phone");
					address = accountFunc.getUserDetail(Signin.id, "address");

					fullNameField.setText(fullName);
					emailField.setText(email);
					phoneField.setText(phone);
					addressField.setText(address);
				}
			}
			
			public class ordersPanel extends JPanel
			{
				JTable table = null;

				public static final String cardName = "ORDERS";
				public ordersPanel()
				{
					setBackground(Utils.superWhite);
					setPreferredSize(new Dimension(830,580));
					setLayout(new FlowLayout(FlowLayout.LEFT, 0,0));

					defaultTableModel = new DefaultTableModel();
					defaultTableModel.addColumn("Order ID");
					defaultTableModel.addColumn("Product");
					defaultTableModel.addColumn("Price");
					defaultTableModel.addColumn("Payment Method");

					table = new JTable(defaultTableModel);
					table.setFont(Utils.getFontSFPro(15f));
					table.setBackground(Utils.superWhite);
					table.setDefaultEditor(Object.class, null);

					JTableHeader tableHeader = table.getTableHeader();
					tableHeader.setBackground(Utils.blue);
					tableHeader.setFont(Utils.getFontSFPro(16f));
					tableHeader.setForeground(Utils.superWhite);

					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBackground(Utils.superWhite);
					scrollPane.setBorder(BorderFactory.createEmptyBorder());
					JScrollBar scrollBar = new JScrollBar(){
						public boolean isVisible() {
							return true;
						}
					};
					scrollBar.setUnitIncrement(10);
					scrollPane.setVerticalScrollBar(scrollBar);
					scrollPane.setPreferredSize(new Dimension(650, 570));
					scrollPane.setViewportView(table);
					scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

					add(scrollPane);

					AccountPanel.this.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentShown(ComponentEvent e) {
							super.componentShown(e);
							Thread thread = new Thread(()->{
								defaultTableModel.getDataVector().removeAllElements();
								AccountPanel.this.orderHistorySet = accountFunc.getOrderHistory(Signin.id);
								updateTable();
							});
							thread.start();
						}
					});

				}
				public void updateTable()
				{
					Object[] contents;
					try {
						while(AccountPanel.this.orderHistorySet.next()) {
							contents = new Object[]{orderHistorySet.getInt("order_id"), orderHistorySet.getString("title"), orderHistorySet.getString("price")+"$", orderHistorySet.getString("payment_method")};
							defaultTableModel.insertRow(defaultTableModel.getRowCount(),contents);
						}

					} catch (SQLException e) {e.printStackTrace();}

				}
			}
			
			public class paymentPanel extends JPanel
			{

				public static final String cardName = "PAYMENT";
				JRadioButton masterRadioButton;
				JRadioButton appleRadioButton;
				JLabel mastercardNumberLabel;
				JLabel lastFourLabel;
				public paymentPanel()
				{
					setBackground(Utils.superWhite);
					setPreferredSize(new Dimension(830,580));
					setLayout(new FlowLayout(FlowLayout.LEADING));

					MastercardPanel mastercardPanel = new MastercardPanel();
					ApplePayPanel applePayPanel = new ApplePayPanel();
					
					add(Box.createRigidArea(new Dimension(500,10)));
					add(mastercardPanel);
					add(Box.createRigidArea(new Dimension(500,10)));
					add(applePayPanel);

					AccountPanel.this.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentShown(ComponentEvent e) {
							super.componentShown(e);
							String lastDigits = accountFunc.getLastCardDigits(Signin.id);
							mastercardPanel.updateDigits(lastDigits);
							if(lastDigits.equals("n"))
							{
								masterRadioButton.setEnabled(false);
								masterRadioButton.setSelected(false);
								appleRadioButton.setSelected(true);
								mastercardNumberLabel.setText("");
								lastFourLabel.setText("");
							}
							else
							{
								lastFourLabel.setText("Last four digits: ");
								if(accountFunc.getDefaultPayment().equals("mastercard"))
								{
									masterRadioButton.setSelected(true);
									masterRadioButton.setEnabled(true);
									appleRadioButton.setSelected(false);

								}
								else
								{
									appleRadioButton.setSelected(true);
									masterRadioButton.setSelected(false);
									masterRadioButton.setEnabled(true);
								}
							}

						}
					});
				}

				private class MastercardPanel extends JPanel
				{
					@Override
					protected void paintComponent(Graphics grphcs) {
						super.paintComponent(grphcs);
						Graphics2D g2 = (Graphics2D) grphcs;
						g2.setColor(Utils.gray);
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
					}

					JFormattedTextField numberField = null;
					JFormattedTextField expiryField = null;
					JFormattedTextField cvvField = null;
					public MastercardPanel()
					{
						setOpaque(false);
						setPreferredSize(new Dimension(400, 70));
						setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

						JPanel dummyPanel = new JPanel();
						dummyPanel.setLayout(new BoxLayout(dummyPanel, BoxLayout.X_AXIS));
						dummyPanel.setBackground(Utils.gray);

						masterRadioButton = new JRadioButton();
						masterRadioButton.setBackground(Utils.gray);
						masterRadioButton.addActionListener(e->
						{
							appleRadioButton.setSelected(false);
							if(!(masterRadioButton.isSelected()))
							{
								masterRadioButton.setSelected(true);
							}
							accountFunc.setDefaultPaymentMethod("mastercard");
						});

						JLabel mastercardTitleLabel = new JLabel("<html><b>Mastercard</b><html>");
						mastercardTitleLabel.setIcon(new ImageIcon("res/icons/mastercard.png"));
						mastercardTitleLabel.setFont(Utils.getFontSFPro(17f));

						String[] options = {"Confirm!", "Cancel"};

						mastercardTitleLabel.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								super.mouseClicked(e);
								int choice = JOptionPane.showOptionDialog(getParent(),new CardDialogPanel(), "Update Card", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
								if(choice == 0)
								{
									Thread thread = new Thread(()->{
										if(accountFunc.getLastCardDigits(Signin.id).equals("n"))
										{
											accountFunc.setCardInfo(Signin.id, numberField.getText(), expiryField.getText(), cvvField.getText(), false);
										}
										else {
											accountFunc.setCardInfo(Signin.id, numberField.getText(), expiryField.getText(), cvvField.getText(), true);
										}
										String digits = (numberField.getText()).substring(numberField.getText().length() - 4);
										mastercardNumberLabel.setText(digits);
										accountFunc.updateUserDetail(Signin.id, "last_four_digits_of_card", digits);
										masterRadioButton.setEnabled(true);
										lastFourLabel.setText("Last four digits: ");

									});
									thread.start();
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


						lastFourLabel = new JLabel("Last four digits: ");
						lastFourLabel.setFont(Utils.getFontSFPro(17f));
						lastFourLabel.setForeground(Color.GRAY);

						mastercardNumberLabel = new JLabel();
						mastercardNumberLabel.setForeground(Color.GRAY);
						mastercardNumberLabel.setText("000");
						mastercardNumberLabel.setFont(Utils.getFontSFPro(17f));

						dummyPanel.add(Box.createRigidArea(new Dimension(5,0)));
						dummyPanel.add(masterRadioButton);
						dummyPanel.add(Box.createRigidArea(new Dimension(7,0)));
						dummyPanel.add(mastercardTitleLabel);
						dummyPanel.add(Box.createRigidArea(new Dimension(20,0)));
						dummyPanel.add(lastFourLabel);
						dummyPanel.add(mastercardNumberLabel);
						dummyPanel.add(Box.createRigidArea(new Dimension(15,0)));

						add(dummyPanel);

					}
					public void updateDigits(String digits)
					{
						mastercardNumberLabel.setText(digits);
					}

					private class CardDialogPanel extends JPanel
					{
						public CardDialogPanel()
						{
							setPreferredSize(new Dimension(300, 220-90));
							setBackground(Utils.superWhite);
							setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));


							MaskFormatter numberFormat;
							MaskFormatter expiryFormat;
							MaskFormatter cvvFormat;

							try {
								numberFormat = new MaskFormatter("################");
								expiryFormat = new MaskFormatter("##/##");
								cvvFormat = new MaskFormatter("###");
							} catch (ParseException e) {
								throw new RuntimeException(e);
							}
							numberFormat.setPlaceholderCharacter('0');
							expiryFormat.setPlaceholderCharacter('0');
							cvvFormat.setPlaceholderCharacter('0');

							numberField = new JFormattedTextField(numberFormat);
							expiryField = new JFormattedTextField(expiryFormat);
							cvvField = new JFormattedTextField(cvvFormat);

							numberField.setFont(Utils.getFontSFPro(15f));
							cvvField.setFont(Utils.getFontSFPro(15f));
							expiryField.setFont(Utils.getFontSFPro(15f));

							JLabel numberLabel = new JLabel("Card Number :");
							numberLabel.setFont(Utils.getFontSFPro(14f));

							JLabel expiryLabel = new JLabel("Expiry               :");
							expiryLabel.setFont(Utils.getFontSFPro(14f));

							JLabel cvvLabel = new JLabel("CVV                  :");
							cvvLabel.setFont(Utils.getFontSFPro(14f));

							add(numberLabel);
							add(numberField);
							//add(Box.createRigidArea(new Dimension(50,0)));
							add(expiryLabel);
							add(expiryField);
							add(Box.createRigidArea(new Dimension(130,0)));
							add(cvvLabel);
							add(cvvField);
						}
					}

				}
				private class ApplePayPanel extends JPanel
				{

					@Override
					protected void paintComponent(Graphics grphcs) {
						super.paintComponent(grphcs);
						Graphics2D g2 = (Graphics2D) grphcs;
						g2.setColor(Utils.gray);
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
					}
					
					JFormattedTextField numberField1 = null;
					public ApplePayPanel()
					{
						setOpaque(false);
						setPreferredSize(new Dimension(400, 70));
						setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
						setBackground(new Color(0,0,0,0));
						
						appleRadioButton = new JRadioButton();
						appleRadioButton.setBackground(Utils.gray);
						appleRadioButton.addActionListener(e->
						{
							masterRadioButton.setSelected(false);

							if(!(appleRadioButton.isSelected()))
							{
								appleRadioButton.setSelected(true);
							}
							accountFunc.setDefaultPaymentMethod("applepay");

						});

						JLabel applePayTitleLabel = new JLabel("<html><b>Apple Pay</b><html>");
						applePayTitleLabel.setIcon(new ImageIcon("res/icons/applepay.png"));
						applePayTitleLabel.setFont(Utils.getFontSFPro(17f));
	
						String[] options = {"Confirm!", "Cancel"};

						applePayTitleLabel.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								super.mouseClicked(e);
								int choiceA = JOptionPane.showOptionDialog(getParent(),new AppleDialogPanel(), "Redeem Card", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
								if(choiceA == 0)
								{
									double tempBalance = accountFunc.redeemCard(Signin.id,  numberField1.getText());
									if(tempBalance > 0)
									{
										balance = String.valueOf(tempBalance);
										balanceLabel.setText(balance + "$");
										JOptionPane.showMessageDialog(getParent(), "Balance added", "Successful", JOptionPane.PLAIN_MESSAGE);
										Thread thread = new Thread(()->{
											defaultTableModel.getDataVector().removeAllElements();
											AccountPanel.this.orderHistorySet = accountFunc.getOrderHistory(Signin.id);
											_ordersPanel.updateTable();
										});
										thread.start();
									}
									else
									{
										JOptionPane.showMessageDialog(getParent(), "Invalid Number", "ERROR!", JOptionPane.ERROR_MESSAGE);
									}

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
						
						add(Box.createRigidArea(new Dimension(5,0)));
						add(appleRadioButton);
						add(Box.createRigidArea(new Dimension(7,0)));
						add(applePayTitleLabel);
					}
					
					private class AppleDialogPanel extends JPanel
					{
						public AppleDialogPanel()
						{
							setPreferredSize(new Dimension(300, 50));
							setBackground(Utils.superWhite);
							setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));


							MaskFormatter numberFormat;

							try {
								numberFormat = new MaskFormatter("UUU-########");
							} catch (ParseException e) {
								throw new RuntimeException(e);
							}
							numberFormat.setPlaceholder("AAA-00000000");

							numberField1 = new JFormattedTextField(numberFormat);;

							numberField1.setFont(Utils.getFontSFPro(15f));

							JLabel numberLabel1 = new JLabel("Redeem :");
							numberLabel1.setFont(Utils.getFontSFPro(14f));
							
							add(numberLabel1);
							add(numberField1);
							add(Box.createRigidArea(new Dimension(130,0)));
						}
					}
				}
			}
			
			public class creditPanel extends JPanel
			{
				public static final String cardName = "CREDITS";
				public creditPanel()
				{
					setBackground(Color.WHITE);
					setPreferredSize(new Dimension(830,580));
					setLayout(null);
					
					JLabel about = new JLabel("Store. Version 2.7");
					about.setBounds(20,20,400,30);
					JLabel about1 = new JLabel("Released : 21 December, 2022");
					about1.setBounds(20,50,400,30);
					JLabel about2 = new JLabel("Created by:");
					about2.setBounds(20,100,400,30);
					JLabel about3 = new JLabel("© All rights reserved");
					about3.setBounds(20,540,400,30);
					this.add(about);
					this.add(about1);
					this.add(about2);
					this.add(about3);

					JLabel dipto = new JLabel("Shahriar Rahman Dipto "+"(22-46622-1)");
					dipto.setBounds(20,120,400,30);
					dipto.setFont(Utils.getFontSFPro(17f));
					JLabel dipto1 = new JLabel("Product page, Ui design, E-mail & Database");
					dipto1.setIcon(new ImageIcon("res/icons/work.png"));
					dipto1.setBounds(20,150,600,30);
					dipto1.setFont(Utils.getFontSFPro(17f));
					
					JLabel maruf = new JLabel("Muntasir Maruf "+"(22-46620-1)");
					maruf.setBounds(20,190,400,30);
					maruf.setFont(Utils.getFontSFPro(17f));
					JLabel maruf1 = new JLabel("Ui design, Account Panel, Admin Panel & Database");
					maruf1.setIcon(new ImageIcon("res/icons/work.png"));
					maruf1.setBounds(20,220,600,30);
					maruf1.setFont(Utils.getFontSFPro(17f));
					
					JLabel sifat = new JLabel("Md. Shaiful Alam "+"(22-46576-1)");
					sifat.setBounds(20,260,400,30);
					sifat.setFont(Utils.getFontSFPro(17f));
					JLabel sifat1 = new JLabel("Ui design & Cart Panel");
					sifat1.setIcon(new ImageIcon("res/icons/work.png"));
					sifat1.setBounds(20,290,600,30);
					sifat1.setFont(Utils.getFontSFPro(17f));
					
					JLabel akash = new JLabel("Fezaul Haque Shaju "+ "(22-46584-1)");
					akash.setBounds(20,330,400,30);
					akash.setFont(Utils.getFontSFPro(17f));
					JLabel akash1 = new JLabel("Sign in, Sign up, Reset & Shop");
					akash1.setIcon(new ImageIcon("res/icons/work.png"));
					akash1.setBounds(20,360,600,30);
					akash1.setFont(Utils.getFontSFPro(17f));
					
					JLabel miraj = new JLabel("Sayed Nazmul Hasan Miraj "+"(22-47987-2)");
					miraj.setBounds(20,400,450,30);
					miraj.setFont(Utils.getFontSFPro(17f));
					JLabel miraj1 = new JLabel("Account Panel & Account details");
					miraj1.setIcon(new ImageIcon("res/icons/work.png"));
					miraj1.setBounds(20,430,650,30);
					miraj1.setFont(Utils.getFontSFPro(17f));
				
					this.add(dipto);
					this.add(dipto1);
					this.add(maruf);
					this.add(maruf1);
					this.add(sifat);
					this.add(sifat1);
					this.add(akash);
					this.add(akash1);
					this.add(miraj);
					this.add(miraj1);
				}
			}
			public class BalancePanel extends JPanel{
					@Override
					protected void paintComponent(Graphics grphcs) {
						super.paintComponent(grphcs);
						Graphics2D g2 = (Graphics2D) grphcs;
						g2.setColor(Utils.gray);
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
					}

					public BalancePanel()
					{
						setLayout(null);
						setOpaque(false);
						setBounds(450,20,150,70);
						setBackground(new Color(0,0,0,0));
						
						JLabel balancePanelLabel = new JLabel("<html><b>Balance</b><html>");
						balancePanelLabel.setFont(Utils.getFontSFPro(17f));
						balancePanelLabel.setBounds(50,5,80,30);
						
						balanceLabel = new JLabel();
						balanceLabel.setFont(Utils.getFontSFPro(17f));
						balanceLabel.setBounds(45,35,80,30);						
						
						add(balancePanelLabel);
						add(balanceLabel);

						AccountPanel.this.addComponentListener(new ComponentAdapter() {
						@Override
						public void componentShown(ComponentEvent e) {
						super.componentShown(e);
						balance = accountFunc.getUserDetail(Signin.id, "credits");
						balanceLabel.setText(balance + "$");
						}
					});
				}
			}
			
			public class securityPanel extends JPanel
			{
				public static final String cardName = "SECURITY";
				public securityPanel()
				{
					setBackground(Color.WHITE);
					setPreferredSize(new Dimension(830,580));
					setLayout(null);
					
					JLabel oldPass = new JLabel("Old Password:");
					oldPass.setBounds(20,20,200,30);
					oldPass.setFont(Utils.getFontSFPro(17f));
					this.add(oldPass);

					JPasswordField oldPasswordField = new JPasswordField();
					oldPasswordField.setBounds(20,50,240,30);
					oldPasswordField.setFont(Utils.getFontSFPro(17f));
					this.add(oldPasswordField);

					JLabel newPass = new JLabel("New Password:");
					newPass.setBounds(20,100,200,30);
					newPass.setFont(Utils.getFontSFPro(17f));
					this.add(newPass);

					JPasswordField NewPasswordField = new JPasswordField();
					NewPasswordField.setBounds(20,130,240,30);
					NewPasswordField.setFont(Utils.getFontSFPro(17f));
					this.add(NewPasswordField);

					JLabel confirmPass = new JLabel("Confirm New Password:");
					confirmPass.setBounds(20,180,180,30);
					confirmPass.setFont(Utils.getFontSFPro(17f));
					this.add(confirmPass);

					JPasswordField confirmPasswordField = new JPasswordField();
					confirmPasswordField.setBounds(20,210,240,30);
					confirmPasswordField.setFont(Utils.getFontSFPro(17f));
					this.add(confirmPasswordField);	
					

					BlueButton changeP = new BlueButton("Change Password", 15, new Dimension(150,30),Utils.green);
					changeP.setBounds(20,270,150,30);
					this.add(changeP);
					
					changeP.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

						String newP = new String(NewPasswordField.getPassword());
						String CnewP = new String(confirmPasswordField.getPassword());
						String oldP = new String(oldPasswordField.getPassword());
						String testP = accountFunc.getUserDetail(Signin.id,"password");
						
						if(newP.length()>5)
						{
							if(newP.contains("@") || newP.contains("!") || newP.contains("$") || newP.contains("%") || newP.contains("^") || newP.contains("&") || newP.contains("*") || newP.contains("/") || newP.contains("\\"))
							{
								if(newP.equals("") || CnewP.equals("") || oldP.equals(""))
								{
									JOptionPane.showMessageDialog(mainPanel, "Please fill up all the information");
								}
								else
								{
									if(oldP.equals(testP))
									{
										if(newP.equals(oldP))
										{
											JOptionPane.showMessageDialog(mainPanel, "Old password cannot be new password");
										}
										else if(newP.equals(CnewP))
										{
											JOptionPane.showMessageDialog(mainPanel, "Password updated successfully");
											accountFunc.updateUserPassword(newP);
											oldPasswordField.setText("");
											NewPasswordField.setText("");
											confirmPasswordField.setText("");
										}
										else
										{
											JOptionPane.showMessageDialog(mainPanel, "Password didn't match");
										}
									}
									else
									{
										JOptionPane.showMessageDialog(mainPanel, "Invalid Password");
									}
								}
							}
							else
							{
								JOptionPane.showMessageDialog(mainPanel, "Password must contain at least one special character");
							}
						}
						else
						{
							JOptionPane.showMessageDialog(mainPanel, "Password must be six characters long");
						}
					}
					});	
				}
			}
			
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
			shopLabel.setIcon(Utils.getScaledImage("res/icons/bag_white.png", new Dimension(20,20), 1));
			shopLabel.setForeground(Utils.white);
			shopLabel.setFont(Utils.getFontSFPro(15f));

			JLabel accountLabel = new JLabel("<html><b>Account</b></html>", JLabel.CENTER);
			accountLabel.setIcon(Utils.getScaledImage("res/icons/settings_blue.png", new Dimension(20,20), 1));
			accountLabel.setForeground(Utils.blue);
			accountLabel.setFont(Utils.getFontSFPro(15f));

			JLabel cartLabel = new JLabel("<html><b>Cart</b></html>", JLabel.CENTER);
			cartLabel.setIcon(Utils.getScaledImage("res/icons/orders_white.png", new Dimension(20,20), 1));
			cartLabel.setForeground(Utils.white);
			cartLabel.setFont(Utils.getFontSFPro(15f));

			JLabel logoutLabel = new JLabel("<html><b>Logout</b></html>", JLabel.CENTER);
			logoutLabel.setIcon(Utils.getScaledImage("res/icons/logout_white.png", new Dimension(20,20), 1));
			logoutLabel.setForeground(Utils.white);
			logoutLabel.setFont(Utils.getFontSFPro(15f));


			shopLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					mainPanel.changePanel(Home.cardName);
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
