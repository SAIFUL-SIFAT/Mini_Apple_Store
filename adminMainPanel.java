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

public class adminMainPanel extends JPanel
{
    Frame parent;
    public adminMainPanel(Frame parent)
    {
        this.parent = parent;

        setBackground(Color.GRAY);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0,0));
        
        RightPanel rightPanel = new RightPanel();
		LeftPanel leftPanel = new LeftPanel(rightPanel);

        add(leftPanel);
        add(rightPanel);



    }
    private class LeftPanel extends JPanel
    {
        Point initialClick;
        public LeftPanel(RightPanel rightPanel)
        {
			//Dragger
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
            setPreferredSize(new Dimension(190, adminUtils.defaultSize.height));
            setLayout(null);

            BlueButton detailsButton = new BlueButton("Details", 25, new Dimension(90,30),new Color(105,105,105));
			detailsButton.setBounds(50,230,90,30);
			add(detailsButton);
			
			BlueButton usersButton = new BlueButton("Users", 25, new Dimension(90,30),new Color(105,105,105));
			usersButton.setBounds(50,280,90,30);
			add(usersButton);
			
			BlueButton logout = new BlueButton("Log Out", 25, new Dimension(90,30),new Color(105,105,105));
			logout.setBounds(50,680,90,30);
			add(logout);
			
			logout.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
					new signinMain();
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
			
			usersButton.addMouseListener(new MouseAdapter() {
				@Override
                public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);				
				new usersMain();
				parent.setVisible(false);
				}
			});
			
            JLabel closeButton = new JLabel();
            closeButton.setIcon(new ImageIcon("res/close_filled.png"));
            closeButton.setBounds(10,10,17,17);

            add(closeButton);

            //listeners
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
        JTable table = null;
        ResultSet resultSet = null;
        DefaultTableModel defaultTableModel;
        String selectedCategory = null;
        String selectedTitle = null;
        public RightPanel()
        {
            //card layout
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
            setBackground(Color.YELLOW);
            setPreferredSize(new Dimension(adminUtils.defaultSize.width-190, adminUtils.defaultSize.height));

            //real stuff
            defaultTableModel = new DefaultTableModel()
            {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 0 || column > 3 && column < 9 || column == 14) return ImageIcon.class;
                    return Object.class;
                }
                @Override
                public boolean isCellEditable(int row,int column){
                    if (column == 0 || column > 3 && column < 9 || column == 14) return false;
                    return true;
                }
            };
            defaultTableModel.addColumn("Image");
            defaultTableModel.addColumn("Category");
            defaultTableModel.addColumn("Title");
            defaultTableModel.addColumn("Price");
            defaultTableModel.addColumn("Feature 1 Icon");
            defaultTableModel.addColumn("Feature 2 Icon");
            defaultTableModel.addColumn("Feature 3 Icon");
            defaultTableModel.addColumn("Feature 4 Icon");
            defaultTableModel.addColumn("Feature 5 Icon");
            defaultTableModel.addColumn("Feature 1");
            defaultTableModel.addColumn("Feature 2");
            defaultTableModel.addColumn("Feature 3");
            defaultTableModel.addColumn("Feature 4");
            defaultTableModel.addColumn("Feature 5");
            defaultTableModel.addColumn("Remove");


            table = new JTable(defaultTableModel){
                @Override
                public void editingStopped(ChangeEvent e) {
                    super.editingStopped(e);
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();

                    productFunc.updateProduct(selectedCategory, selectedTitle,
                        table.getColumnName(column).replaceAll("\s+","").toLowerCase(),
                        defaultTableModel.getValueAt(row,column).toString()
                        );

                }

            };
            table.setCellSelectionEnabled(true);
            table.setRowHeight(50);
            TableColumnModel tableColumnModel = table.getColumnModel();
            tableColumnModel.getColumn(table.getColumnCount()-1).setPreferredWidth(50);
            table.setColumnModel(tableColumnModel);

            //table.setDefaultEditor(Object.class, null);

            //change color and font of header
            JTableHeader tableHeader = table.getTableHeader();
            JButton addButton = new JButton("<html><b><center>ADD</center></b></html>");

            addButton.setFont(Utils.getFontSFPro(14f));
            addButton.setPreferredSize(new Dimension(80,30));
            addButton.setMaximumSize(new Dimension(80,30));
            addButton.setMinimumSize(new Dimension(80,30));
            addButton.setHorizontalAlignment(SwingConstants.CENTER);
            addButton.setVerticalAlignment(SwingConstants.CENTER);

            addButton.addActionListener(e->{
                Thread thread = new Thread(()->{
                    productFunc.addProduct("default","default");
                    defaultTableModel.getDataVector().removeAllElements();
                    RightPanel.this.resultSet = productFunc.getProducts();
                    updateTable();
                });
                thread.start();

            });

            //scroll bar and stuff
            JScrollPane scrollPane = new JScrollPane();
            add(scrollPane);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            JScrollBar scrollBar = new JScrollBar(){
                public boolean isVisible() {
                    return true;
                }
            };
            scrollBar.setUnitIncrement(10);
            scrollPane.setVerticalScrollBar(scrollBar);
            scrollPane.setPreferredSize(new Dimension(adminUtils.defaultSize.width-190, adminUtils.defaultSize.height));
            JPanel view = new JPanel();
            view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
            view.add(table.getTableHeader());
            view.add(table);
            view.add(addButton);
            scrollPane.setViewportView(view);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


            //listeners

            //mouse listener start
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    selectedCategory = defaultTableModel.getValueAt(table.getSelectedRow(),1).toString();
                    selectedTitle = defaultTableModel.getValueAt(table.getSelectedRow(),2).toString();

                    int column = table.getSelectedColumn();

                    if(!(column == 1 || column == 2))
                    {
                        if((selectedTitle.equals("default") || selectedCategory.equals("default")) && column != table.getColumnCount()-1)
                        {
                            JOptionPane.showMessageDialog(getParent(),"Please assign category and title first", "Critical", JOptionPane.WARNING_MESSAGE);
                        }
                        else
                        {
                            if(column == 0 || column > 3 && column < 9)
                            {
                                String imageORicon = "Images";
                                if(column > 0)
                                {
                                    imageORicon = "Icons";
                                }
                                int row = table.getSelectedRow();
                                //ImageIcon prev = (ImageIcon) defaultTableModel.getValueAt(row,column);

                                FileDialog fileDialog = new FileDialog((Frame)null, "Choose photo", FileDialog.LOAD);
                                fileDialog.setDirectory("C:\\");
                                fileDialog.setFile("*.jpg;*.jpeg;*.png");
                                fileDialog.setVisible(true);
                                if((fileDialog.getFile()) != null)
                                {
                                    try {
                                        String p;
                                        if(imageORicon.equals("Images"))
                                        {
                                            p = "res/" + defaultTableModel.getValueAt(row,1).toString() + "/" + defaultTableModel.getValueAt(row,2).toString() + "/"+ imageORicon +"/1"+".png";
                                        }
                                        else {
                                            p = "res/" + defaultTableModel.getValueAt(row,1).toString() + "/" + defaultTableModel.getValueAt(row,2).toString() + "/"+ imageORicon +"/"+(column-3)+".png";
                                        }

                                        //new File(p).delete();
                                        Path path = Paths.get(p);
                                        if(!new File(p).exists())
                                        {
                                            (new File(p)).mkdirs();
                                        }
                                        Files.copy(Paths.get(fileDialog.getDirectory() + fileDialog.getFile()), path, StandardCopyOption.REPLACE_EXISTING);
                                        defaultTableModel.setValueAt(adminUtils.getScaledImage(p, new Dimension(50,50),1), row, column);


                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }

                            }
                            else if(column == table.getColumnCount()-1)
                            {
                                System.out.println("Last column clicked");
                                int row = table.getSelectedRow();
                                String p = "res/" + defaultTableModel.getValueAt(row,1).toString() + "/" + defaultTableModel.getValueAt(row,2).toString();
                                File file = new File(p);
                                adminUtils.deleteDirectory(file);
                                file.delete();

                                productFunc.removeRow(defaultTableModel.getValueAt(row,2).toString());
                                Thread thread = new Thread(()->{
                                    defaultTableModel.getDataVector().removeAllElements();
                                    RightPanel.this.resultSet = productFunc.getProducts();
                                    updateTable();
                                });
                                thread.start();

                            }
                        }
                    }


                }
            }); //mouse listener end


            RightPanel.this.addAncestorListener(new AncestorListener() {
                @Override
                public void ancestorAdded(AncestorEvent event) {
                    Thread thread = new Thread(()->{
                        defaultTableModel.getDataVector().removeAllElements();
                        RightPanel.this.resultSet = productFunc.getProducts();
                        updateTable();
                    });
                    thread.start();
                }

                @Override
                public void ancestorRemoved(AncestorEvent event) {

                }

                @Override
                public void ancestorMoved(AncestorEvent event) {

                }
            });
			
			setVisible(true);
        }
        public void updateTable()
        {
            Object[] contents;
            ImageIcon icon = null;
            Dimension featureSize = new Dimension(35,35);

            //panel.add(icon);

            try {
                while(resultSet.next()) {

                    String path = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Images/1.png";
                    String fpath1 = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Icons/1.png";
                    String fpath2 = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Icons/2.png";
                    String fpath3 = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Icons/3.png";
                    String fpath4 = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Icons/4.png";
                    String fpath5 = "res/" + resultSet.getString("category") + "/" + resultSet.getString("title") + "/Icons/5.png";

                    JButton deleteButton = new JButton("Delete");

                    deleteButton.addActionListener(e->{

                    });
                    //image = new JLabel();

                    if(!new File(path).exists())
                    {
                        path = "res/default_feature.png";
                    }

                    if(!new File(fpath1).exists())
                    {
                        fpath1 = "res/default_feature.png";
                    }
                    if(!new File(fpath2).exists())
                    {
                        fpath2 = "res/default_feature.png";
                    }
                    if(!new File(fpath3).exists())
                    {
                        fpath3 = "res/default_feature.png";
                    }
                    if(!new File(fpath4).exists())
                    {
                        fpath4 = "res/default_feature.png";
                    }
                    if(!new File(fpath5).exists())
                    {
                        fpath5 = "res/default_feature.png";
                    }

                    contents = new Object[]
                            {
                                    adminUtils.getScaledImage(path, new Dimension(50,50),1),
                                    resultSet.getString("category"), resultSet.getString("title"),
                                    resultSet.getDouble("price"),
                                    adminUtils.getScaledImage(fpath1, featureSize, 1),
                                    adminUtils.getScaledImage(fpath2, featureSize, 1),
                                    adminUtils.getScaledImage(fpath3, featureSize, 1),
                                    adminUtils.getScaledImage(fpath4, featureSize, 1),
                                    adminUtils.getScaledImage(fpath5, featureSize, 1),
                                    resultSet.getString("feature1"),
                                    resultSet.getString("feature2"), resultSet.getString("feature3"),
                                    resultSet.getString("feature4"), resultSet.getString("feature5"),
                                    adminUtils.getScaledImage("res/icons/minus.png", new Dimension(50, 50), 1)
                            };
                    defaultTableModel.insertRow(defaultTableModel.getRowCount(), contents);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}

