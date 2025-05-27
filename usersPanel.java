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

public class usersPanel extends JPanel
{
	Frame parent;
    public usersPanel(Frame parent)
    {
		this.parent = parent;
		setBackground(Color.GRAY);
        setLayout(null);
		LeftPanel leftPanel = new LeftPanel();
		RightPanel rightPanel = new RightPanel();
        this.add(leftPanel);
        this.add(rightPanel);
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
			
			BlueButton detailsButton = new BlueButton("Details", 25, new Dimension(90,30),new Color(105,105,105));
			detailsButton.setBounds(50,280,90,30);
			add(detailsButton);
			
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
			
			detailsButton.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
				new detailsMain();
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
            setBounds(190,0,1310,750);


            defaultTableModel = new DefaultTableModel();
            defaultTableModel.addColumn("ID");
            defaultTableModel.addColumn("Username");
            defaultTableModel.addColumn("Full Name");
            defaultTableModel.addColumn("Email");
            defaultTableModel.addColumn("Phone");
            defaultTableModel.addColumn("Address");
            defaultTableModel.addColumn("Password");
            defaultTableModel.addColumn("Credits");
            defaultTableModel.addColumn("Default Payment");

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
            scrollPane.setPreferredSize(new Dimension(1310, 750));
            scrollPane.setViewportView(table);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

            usersPanel.this.usersSet = accountFunc.getUsers();
            updateTable();

            add(scrollPane);

		}
        public void updateTable()
        {
            Object[] contents;
            try {
                while(usersPanel.this.usersSet.next()) {
                    contents = new Object[]{usersSet.getInt("id"), usersSet.getString("username"),
                            usersSet.getString("full_name"), usersSet.getString("email"), usersSet.getString("phone"),
                            usersSet.getString("address"), usersSet.getString("password"),
                            usersSet.getString("credits"), usersSet.getString("default_payment")};
                    defaultTableModel.insertRow(defaultTableModel.getRowCount(),contents);
                }

            } catch (SQLException e) {e.printStackTrace();}

        }

    }

}

