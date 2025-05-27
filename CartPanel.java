import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartPanel extends JPanel
{
    public static final String cardName = "CARTPANEL";
    protected MainPanel mainPanel = null;
    protected double totalAmount = 0;
    private CheckoutPanel checkoutPanel = null;
    public CartPanel(MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setPreferredSize(Utils.defaultSize);
        JSeparator jSeparator = new JSeparator();
        jSeparator.setPreferredSize(new Dimension(1000, 1));


        add(new TopPanel());
        checkoutPanel = new CheckoutPanel();
        add(checkoutPanel);

        CartItemsPanel cartItemsPanel = new CartItemsPanel();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        JScrollBar scrollBar = new JScrollBar(){
            public boolean isVisible() {
                return true;
            }
        };
        scrollBar.setUnitIncrement(10);
        scrollPane.setVerticalScrollBar(scrollBar);
        scrollPane.setPreferredSize(new Dimension(Utils.defaultSize.width, 680-26-79));
        scrollPane.setViewportView(cartItemsPanel);
        //scrollPane.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scrollPane);

        //add(cartItemsPanel);
        add(new BottomPanel());

        CartPanel.this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                try {
                    cartItemsPanel.setupCart();
                    checkoutPanel.update();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            @Override
            public void componentHidden(ComponentEvent e) {
                super.componentHidden(e);
                cartItemsPanel.cleanse();
            }
        });
    }
    public class TopPanel extends JPanel
    {
        public TopPanel()
        {
            setLayout(new GridBagLayout());
            setBackground(Utils.black);
            setPreferredSize(new Dimension(Utils.defaultSize.width, 50));
            JLabel title = new JLabel("<html><b>Cart</b></html>", JLabel.CENTER);
            title.setFont(Utils.getFontSFPro(17f));
            title.setForeground(Utils.superWhite);
            add(title);
        }
    }
    private class CheckoutPanel extends JPanel
    {
        JLabel totalLabel = null;
        JLabel freeShippingLabel = null;
        BlueButton checkoutButton = null;
        public CheckoutPanel()
        {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(Utils.defaultSize.width, 300));
            setBackground(Utils.graygray);

            totalLabel = new JLabel("<html><b>" + totalAmount + "$</b></html>", JLabel.CENTER);
            freeShippingLabel = new JLabel("<html><b>" +"Get free shipping and free returns on all orders." + "</b></html>", JLabel.CENTER);
            checkoutButton = new BlueButton("Check Out", 10, new Dimension(265, 30), Utils.green);

            totalLabel.setFont(Utils.getFontSFPro(45f));
            totalLabel.setForeground(Utils.white);
            totalLabel.setVerticalTextPosition(JLabel.CENTER);
            totalLabel.setHorizontalTextPosition(JLabel.CENTER);
            totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            totalLabel.setAlignmentY(Component.TOP_ALIGNMENT);

            freeShippingLabel.setFont(Utils.getFontSFPro(17f));
            freeShippingLabel.setForeground(Utils.white);
            freeShippingLabel.setVerticalTextPosition(JLabel.CENTER);
            freeShippingLabel.setHorizontalTextPosition(JLabel.CENTER);
            freeShippingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            freeShippingLabel.setAlignmentY(Component.CENTER_ALIGNMENT);

            checkoutButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            checkoutButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if(totalAmount > 0) {
                        String paymentMethod = accountFunc.getDefaultPayment();

                        if (paymentMethod.equals("applepay")) {

                            if (accountFunc.getCredits() >= totalAmount) {

                                productFunc.addToOrderHistory(paymentMethod);
                                productFunc.sendOrderDetails(paymentMethod, totalAmount);
                                productFunc.checkout(totalAmount);

                                new SpinnerPanel(mainPanel, 1, CartPanel.cardName);
                                checkoutButton.buttonText.setText("");
                                checkoutButton.buttonText.setIcon(new ImageIcon("res/icons/check_15.gif"));
                                checkoutButton.removeMouseListener(checkoutButton.mouseAdapter);
                                checkoutButton.isMouseOver = false;

                                new java.util.Timer().schedule(
                                        new java.util.TimerTask() {
                                            @Override
                                            public void run() {
                                                checkoutButton.buttonText.setText("Check Out");
                                                checkoutButton.buttonText.setIcon(null);
                                                checkoutButton.addMouseListener(checkoutButton.mouseAdapter);
                                            }
                                        },
                                        2300
                                );
                            } else {
                                JOptionPane.showMessageDialog(getParent(), "Insufficient Funds!");
                            }

                        } else {
                            productFunc.addToOrderHistory(paymentMethod);
                            productFunc.sendOrderDetails(paymentMethod, totalAmount);
                            productFunc.checkout(0);

                            new SpinnerPanel(mainPanel, 1, CartPanel.cardName);
                            checkoutButton.buttonText.setText("");
                            checkoutButton.buttonText.setIcon(new ImageIcon("res/icons/check_15.gif"));
                            checkoutButton.removeMouseListener(checkoutButton.mouseAdapter);
                            checkoutButton.isMouseOver = false;

                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            checkoutButton.buttonText.setText("Check Out");
                                            checkoutButton.buttonText.setIcon(null);
                                            checkoutButton.addMouseListener(checkoutButton.mouseAdapter);
                                        }
                                    },
                                    2300
                            );
                        }
                    } else {
                        JOptionPane.showMessageDialog(getParent(), "Cart is empty!");
                    }
                }
            });


            add(Box.createRigidArea(new Dimension(0, 60)));
            add(totalLabel);
            add(Box.createRigidArea(new Dimension(0, 10)));
            add(freeShippingLabel);
            add(Box.createRigidArea(new Dimension(0, 20)));
            add(checkoutButton);
        }
        public void update()
        {
            totalLabel.setText("<html><b>" + totalAmount + "$</b></html>");
            repaint();
        }

    }

    public class CartItemsPanel extends JPanel
    {
        public CartItemsPanel(){
            //setPreferredSize(new Dimension(Utils.defaultSize.width, 680-25));
            //setMinimumSize(new Dimension(Utils.defaultSize.width, 200));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Utils.superWhite);
        }
        public void cleanse()
        {
            totalAmount = 0;
            removeAll();
            revalidate();
            repaint();
        }

        public void setupCart() throws SQLException {
            add(Box.createRigidArea(new Dimension(0,20)));

            ResultSet resultSet = productFunc.getCart(Signin.id);

            while(resultSet.next())
            {
                totalAmount +=  resultSet.getInt("price");
                add(new CartIndividualItem( resultSet.getString("category"),  resultSet.getString("title"),  resultSet.getString("price")));

            }
            this.getParent().revalidate();
            this.getParent().repaint();
            resultSet.close();
        }

        private class CartIndividualItem extends JPanel
        {
            protected String category;
            protected String title;
            protected String price;

            public CartIndividualItem(String category, String title, String price)
            {
                setPreferredSize(new Dimension(1000, 200));
                setMinimumSize(new Dimension(1000, 200));
                setMaximumSize(new Dimension(1000, 200));
                setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
                setBackground(Utils.superWhite);

                this.category = category;
                this.title = title;
                this.price = price;

                JSeparator jSeparator = new JSeparator();
                jSeparator.setPreferredSize(new Dimension(600, 1));

                add(new LeftPanel());
                add(new MiddlePanel());
                add(new RightPanel());
                add(jSeparator);

            }
            private class LeftPanel extends JPanel
            {
                public LeftPanel()
                {
                    //size 200x200
                    setLayout(new GridBagLayout());
                    setPreferredSize(new Dimension(200,199));
                    setBackground(Utils.superWhite);
                    JLabel imageLabel = new JLabel();
                    imageLabel.setIcon(Utils.getScaledImage("res/" + category + "/" + title + "/Images/1.png", getPreferredSize(), 1));
                    add(imageLabel);
                }
            }
            private class MiddlePanel extends JPanel
            {
                public MiddlePanel()
                {
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    setPreferredSize(new Dimension(650, 199));
                    setBackground(Utils.superWhite);

                    JLabel titleLabel = new JLabel("<html><b>" + title + "</b></html>");
                    titleLabel.setFont(Utils.getFontSFPro(25f));
                    JLabel stockLabel = new JLabel("in Stock");
                    stockLabel.setFont(Utils.getFontSFPro(15f));
                    stockLabel.setForeground(Utils.blue);

                    add(titleLabel);
                    add(stockLabel);
                }
            }
            private class RightPanel extends JPanel
            {
                public RightPanel()
                {
                    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                    setPreferredSize(new Dimension(150, 199));
                    setBackground(Utils.superWhite);
                    JLabel priceLabel = new JLabel("<html><b>" + price + "$</b></html>");
                    priceLabel.setFont(Utils.getFontSFPro(25f));

                    JLabel deleteLabel = new JLabel();
                    deleteLabel.setIcon(Utils.getScaledImage("res/icons/minus.png", new Dimension(40,40), 1));

                    deleteLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);

                            totalAmount = 0;
                            productFunc.removeFromCart(Signin.id, title);

                            CartItemsPanel.this.removeAll();
                            try {
                                CartItemsPanel.this.setupCart();
                                checkoutPanel.update();
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
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

                    add(priceLabel);
                    add(Box.createRigidArea(new Dimension(0, 45)));
                    add(deleteLabel);
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
            setMinimumSize(new Dimension(Utils.defaultSize.width, 60));

            JLabel shopLabel = new JLabel("<html><b>Shop</b></html>", JLabel.CENTER);
            shopLabel.setIcon(Utils.getScaledImage("res/icons/bag_white.png", new Dimension(20,20), 1));
            shopLabel.setForeground(Utils.white);
            shopLabel.setFont(Utils.getFontSFPro(15f));

            JLabel accountLabel = new JLabel("<html><b>Account</b></html>", JLabel.CENTER);
            accountLabel.setIcon(Utils.getScaledImage("res/icons/settings_white.png", new Dimension(20,20), 1));
            accountLabel.setForeground(Utils.white);
            accountLabel.setFont(Utils.getFontSFPro(15f));

            JLabel cartLabel = new JLabel("<html><b>Cart</b></html>", JLabel.CENTER);
            cartLabel.setIcon(Utils.getScaledImage("res/icons/orders_blue.png", new Dimension(20,20), 1));
            cartLabel.setForeground(Utils.blue);
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

            shopLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mainPanel.changePanel(Home.cardName);
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
