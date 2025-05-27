import jdk.jshell.execution.Util;
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

public class detailsPanel extends JPanel
{
	Frame parent;
    public detailsPanel(Frame parent)
    {
		this.parent = parent;
		setBackground(new Color(211,211,211));
        setLayout(null);
		LeftPanel leftPanel = new LeftPanel();
		RightPanel rightPanel = new RightPanel();
		LastPanel lastPanel = new LastPanel();
        this.add(leftPanel);
        this.add(rightPanel);
        this.add(lastPanel);
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
			
			BlueButton productsButton = new BlueButton("Products", 25, new Dimension(90,30),new Color(105,105,105));
			productsButton.setBounds(50,230,90,30);
			add(productsButton);
			
			BlueButton usersButton = new BlueButton("Users", 25, new Dimension(90,30),new Color(105,105,105));
			usersButton.setBounds(50,280,90,30);
			add(usersButton);
			
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
			
			productsButton.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
				new adminMain();
				parent.setVisible(false);
				}
			});
			
			usersButton.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);					
				new usersMain();
				parent.setVisible(false);
				}
			});
        }
    }
    DefaultTableModel defaultTableModel;
    JTable table = null;
    public ResultSet usersSet = null;
    private class RightPanel extends JPanel
    {
        public RightPanel()
        {
            setLayout(new FlowLayout(FlowLayout.LEFT, 0,0));
            setBackground(Utils.superWhite);
            setBounds(190,0,655,750);


            defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("Card ID");
            defaultTableModel.addColumn("Card Number");
            defaultTableModel.addColumn("Amount");

            table = new JTable(defaultTableModel);
            table.setFont(Utils.getFontSFPro(15f));
            table.setBackground(Utils.superWhite);
            table.setDefaultEditor(Object.class, null);

            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setBackground(Utils.blue);
            tableHeader.setFont(Utils.getFontSFPro(16f));
            tableHeader.setForeground(Utils.superWhite);

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBackground(Utils.gray);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            JScrollBar scrollBar = new JScrollBar(){
                public boolean isVisible() {
                    return true;
                }
            };
            scrollBar.setUnitIncrement(10);
            scrollPane.setVerticalScrollBar(scrollBar);
            scrollPane.setPreferredSize(new Dimension(655, 750));
            scrollPane.setViewportView(table);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            detailsPanel.this.usersSet = accountFunc.getCards();
            updateTable();
		
            add(scrollPane);

        }
        public void updateTable()
        {
            Object[] contents;
            try {
                while(detailsPanel.this.usersSet.next()) {
                    contents = new Object[]{usersSet.getInt("gift_card_id"), usersSet.getString("card_number"), usersSet.getString("amount")};
                    defaultTableModel.insertRow(defaultTableModel.getRowCount(),contents);
                }
            } catch (SQLException e) {e.printStackTrace();}
        }
    }
	
	private class LastPanel extends JPanel
    {
        public LastPanel()
        {
            setLayout(null);
            setBackground(new Color(211,211,211));
			setBounds(845,0,655,750);
			
			JLabel numbers = new JLabel("Sales Statistics");
			numbers.setBounds(255,20,180,30);
			numbers.setFont(Utils.getFontSFPro(20f));
			add(numbers);

            Integer totalUsers;
            Double transactionAmount;
            Double cardRedeemded;
            Double totalCard;

            try {
                totalUsers = (productFunc.getStat()).getInt("total_users");
                transactionAmount = (productFunc.getStat()).getDouble("total_transaction_amount");
                cardRedeemded = (productFunc.getStat()).getDouble("total_giftcards_amount_reedemed");
                totalCard = (productFunc.getStat()).getDouble("total_giftcard_amount_in_db");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
			
			JLabel totalUsersL = new JLabel("Total Users:");
			totalUsersL.setBounds(230,80,180,30);
            JLabel totalUsersLabel = new JLabel(totalUsers.toString());
            totalUsersLabel.setBounds(380,80,180,30);
			
			JLabel transactionAmountL = new JLabel("Transaction Amount:");
			transactionAmountL.setBounds(230,100,180,30);
            JLabel transactionAmountLabel = new JLabel("$"+transactionAmount);
            transactionAmountLabel.setBounds(380,100,180,30);
			
			JLabel cardRedeemedL = new JLabel("Cards Redeemed:");
			cardRedeemedL.setBounds(230,120,180,30);
            JLabel cardRedeemedLabel = new JLabel("$"+cardRedeemded);
            cardRedeemedLabel.setBounds(380,120,180,30);
			
			JLabel totalCardL = new JLabel("Cards in DB:");
			totalCardL.setBounds(230,140,180,30);
            JLabel totalCardLabel = new JLabel("$"+totalCard);
            totalCardLabel.setBounds(380,140,180,30);
			
            add(totalUsersLabel);
            add(transactionAmountLabel);
            add(cardRedeemedLabel);
            add(totalCardLabel);
			add(totalUsersL);
            add(transactionAmountL);
            add(cardRedeemedL);
            add(totalCardL);
			
			JButton generateCard = new JButton("Generate Cards");
            generateCard.setFont(Utils.getFontSFPro(13f));
			generateCard.setBounds(267,650,125,30);
			generateCard.setFocusable(false);
			generateCard.setForeground(Utils.superWhite);
			generateCard.setBackground(new Color(105,105,105));
			add(generateCard);
			
			generateCard.addActionListener(e->
			{
				Thread thread = new Thread(()->{
                        ResultSet resultSet = productFunc.getCards();
                try {
                    while(resultSet.next())
                    {
                        adminUtils.generateImage(resultSet.getString("card_number"),resultSet.getDouble("amount"), resultSet.getInt("gift_card_id"));
                    }
                    JOptionPane.showMessageDialog(detailsPanel.this, "Check \"cards\" folder for generated cards", "SUCCESS", JOptionPane.PLAIN_MESSAGE, null);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                });
                thread.start();
			});
		}

    }

}

