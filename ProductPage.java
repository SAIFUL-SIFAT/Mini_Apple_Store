import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.geom.GeneralPath;

public class ProductPage extends JPanel
{
    public static final String cardName = "PRODUCTPANEL";
    protected Home home = null;
    Component firstButtonReference = null;
    public void cleanse()
    {
        removeAll();
        revalidate();
        repaint();
    }
    public void setupPanel(String category)
    {
        add(new MainPanel(category));
    }
    public void setFocusOnFirstButton()
    {
        if(firstButtonReference != null)
        {
            firstButtonReference.setFocusable(true);
            firstButtonReference.requestFocus();
        }

    }

    public ProductPage(Home home)
    {
        this.home = home;
        setPreferredSize(Utils.defaultSize);
        setBackground(new Color(0,0,0,0.85f));
    }



    private class MainPanel extends JPanel
    {
        protected Color superWhite = new Color(255,255,255);
        Dimension defaultSize = new Dimension(1366, 1024);
        public MainPanel(String category)
        {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));


            setBackground(new Color(0,0,0,0.85f));
            setPreferredSize(defaultSize);

            TopPanel topPanel = null;
            BottomPanel bottomPanel = null;
            CenterPanel centerPanel = null;

            try {
                bottomPanel = new BottomPanel(category);
                centerPanel = new CenterPanel(bottomPanel, category);
                topPanel = new TopPanel(bottomPanel);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            add(topPanel);
            add(centerPanel);
            add(bottomPanel);

        }
        private class TopPanel extends JPanel
        {
            public TopPanel(BottomPanel bottomPanel)
            {

                setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
                setPreferredSize(new Dimension(980, 115));
                setBackground(new Color(0,0,0,0.85f));
                RoundButton backButton = new RoundButton("X", new Dimension(36,36));
                backButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        home.changePanel(Home.CategoryPanel.cardName);
                        cleanse();

                    }
                });
                add(backButton);
            }
        }

        private class CenterPanel extends JPanel
        {


            public CenterPanel(BottomPanel bottomPanel, String category) throws SQLException
            {

                setPreferredSize(new Dimension(980, 100));
                setBackground(new Color(0,0,0,0.85f));

                setLayout(new GridBagLayout());


                TabsPanel tabsPanel = new TabsPanel(bottomPanel, category);
                add(tabsPanel);

            }
            private class TabsPanel extends JPanel
            {
                private int radius = 43;

                @Override
                protected void paintComponent(Graphics grphcs) {
                    super.paintComponent(grphcs);
                    Graphics2D g2 = (Graphics2D) grphcs;
                    g2.setColor(new Color(232,232,237));
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                }
                public TabsPanel(BottomPanel bottomPanel, String category) throws SQLException
                {

                    setOpaque(false);
                    setLayout(new FlowLayout(FlowLayout.CENTER, 3,3));
                    setBackground(new Color(232,232,237));

                    ResultSet resultSet = productFunc.getProducts(category);
                    boolean isFirst = true;

                    while(resultSet.next())
                    {
                        String title = null;
                        try {
                            title = resultSet.getString("title");

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        String finalTitle = title;

                        TabButton button = new TabButton(title, 40, new Dimension(0, 0));
                        if(isFirst)
                        {
                            firstButtonReference = button;
                            isFirst = false;
                        }

                        button.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                super.mouseClicked(e);
                                bottomPanel.changePanel(finalTitle);
                            }
                        });
                        add(button);
                    }
                    resultSet.close();
                }
            }
        }
        private class BottomPanel extends JPanel{
            @Override
            protected void paintComponent(Graphics grphcs) {
                super.paintComponent(grphcs);
                Graphics2D g2 = (Graphics2D) grphcs;
                g2.setColor(superWhite);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
            }

            public BottomPanel(String category) throws SQLException {
                setOpaque(false);
                ResultSet resultSet = productFunc.getProducts(category);

                setPreferredSize(new Dimension(980, 725));
                setBackground(superWhite);
                setLayout(new CardLayout());

                while(resultSet.next())
                {
                    /*String title = null;
                    try {
                        title = resultSet.getString("title");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ProductPanel productPanel = new ProductPanel(title);
                    add(productPanel, title);*/
                    try {
                        add(new ProductPanel(
                                category,
                                resultSet.getInt("product_id"),
                                resultSet.getString("title"),
                                resultSet.getDouble("price"),
                                resultSet.getString("feature1"),
                                resultSet.getString("feature2"),
                                resultSet.getString("feature3"),
                                resultSet.getString("feature4"),
                                resultSet.getString("feature5")), resultSet.getString("title"));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                resultSet.close();
            }
            public void changePanel(String cardName)
            {
                CardLayout cl = (CardLayout)getLayout();
                cl.show(this, cardName);
            }
            private class ProductPanel extends JPanel
            {
                protected int productID;
                protected String category;
                protected String cardName;
                protected double price;
                protected String feature1;
                protected String feature2;
                protected String feature3;
                protected String feature4;
                protected String feature5;

                @Override
                protected void paintComponent(Graphics grphcs) {
                    super.paintComponent(grphcs);
                    Graphics2D g2 = (Graphics2D) grphcs;
                    g2.setColor(superWhite);
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                }
                public ProductPanel(String category, int productID, String cardName, double price, String feature1, String feature2, String feature3, String feature4, String feature5)
                {
                    setOpaque(false);
                    this.productID = productID;
                    this.category = category;
                    this.cardName = cardName;
                    this.price = price;
                    this.feature1 = feature1;
                    this.feature2 = feature2;
                    this.feature3 = feature3;
                    this.feature4 = feature4;
                    this.feature5 = feature5;

                    setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
                    GridBagConstraints gbc = new GridBagConstraints();
                    setPreferredSize(new Dimension(980, 725));
                    setBackground(Color.ORANGE);

                    add(new DescriptionLeftPanel(), gbc);
                    add(new DescriptionRightPanel(), gbc);
                    add(new BottomBottomPanel());

                }

                private class DescriptionLeftPanel extends JPanel
                {
                    @Override
                    protected void paintComponent(Graphics grphcs) {
                        super.paintComponent(grphcs);
                        Graphics2D g2 = (Graphics2D) grphcs;
                        g2.setColor(superWhite);
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                    }
                    public DescriptionLeftPanel()
                    {
                        setOpaque(false);
                        setPreferredSize(new Dimension(980/2, 625));
                        setLayout(new GridBagLayout());
                        setBackground(superWhite);
                        JLabel imageLabel = new JLabel();

                        imageLabel.setIcon(Utils.getScaledImage("res/" + category + "/" + cardName + "/Images/1.png", getPreferredSize(), 1));
                        add(imageLabel);
                    }

                }
                private class DescriptionRightPanel extends JPanel
                {
                    @Override
                    protected void paintComponent(Graphics grphcs) {
                        super.paintComponent(grphcs);
                        Graphics2D g2 = (Graphics2D) grphcs;
                        g2.setColor(superWhite);
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
                    }
                    public DescriptionRightPanel()
                    {
                        setOpaque(false);
                        setPreferredSize(new Dimension(980/2, 625));

                        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 50));
                        setBackground(superWhite);
                        add(new RightTopPanel());
                        add(new RightBottomPanel());
                    }
                    private class RightTopPanel extends JPanel
                    {
                        public RightTopPanel()
                        {


                            setPreferredSize(new Dimension(980/2, 105));
                            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                            add(new RightTopLeftPanel());
                            add(new RightTopRightPanel());

                        }
                        private class RightTopLeftPanel extends JPanel
                        {
                            public RightTopLeftPanel()
                            {
                                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                                setPreferredSize(new Dimension(980/4 + 50, 105));
                                setBackground(superWhite);
                                JLabel productTitle = new JLabel("<html>"+cardName+"</html>");
                                JLabel productPrice = new JLabel("From " + price + "$ or " + (String.format("%.1f", (price / 24f)) + "$/month for 24 months."));
                                productTitle.setFont(Utils.getFontSFPro(32f));
                                productTitle.setForeground(new Color(29, 29, 31));
                                productPrice.setFont(Utils.getFontSFPro(14f));
                                productPrice.setForeground(getForeground().brighter());
                                add(productTitle);
                                add(productPrice);

                            }
                        }


                        private class RightTopRightPanel extends JPanel
                        {
                            private String cartButtonImage = "res/icons/cart_black.png";
                            public RightTopRightPanel()
                            {
                                setLayout(new FlowLayout(FlowLayout.CENTER, 500,10));
                                GridBagConstraints gbc = new GridBagConstraints();
                                setPreferredSize(new Dimension(980/4 - 50, 105));
                                setBackground(superWhite);

                                if(productFunc.isInCart(Signin.id, cardName))
                                {
                                    cartButtonImage = "res/icons/cart_green.png";
                                }

                                JLabel cartButton = new JLabel();
                                cartButton.setIcon(Utils.getScaledImage(cartButtonImage, new Dimension(30,30),1));

                                BlueButton buyButton = new BlueButton("Buy", 35, new Dimension(62,36), null);


                                cartButton.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        super.mouseClicked(e);
                                        if(productFunc.isInCart(Signin.id, cardName))
                                        {
                                            cartButtonImage = "res/icons/cart_black.png";
                                            cartButton.setIcon(Utils.getScaledImage(cartButtonImage, new Dimension(30,30),1));
                                            revalidate();
                                            repaint();
                                            productFunc.removeFromCart(Signin.id, cardName);

                                        }
                                        else
                                        {
                                            cartButtonImage = "res/icons/cart_green.png";
                                            cartButton.setIcon(Utils.getScaledImage(cartButtonImage, new Dimension(30,30),1));
                                            revalidate();
                                            repaint();
                                            productFunc.addToCart(Signin.id, productID, cardName, price, category);

                                        }

                                    }
                                    @Override
                                    public void mouseEntered(MouseEvent me) {
                                        cartButton.setCursor(Utils.handCursor);
                                    }
                                    @Override
                                    public void mouseExited(MouseEvent me) {
                                        cartButton.setCursor(Utils.defaultCursor);
                                    }
                                });

                                buyButton.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        super.mouseClicked(e);
                                        if(!(productFunc.isInCart(Signin.id, cardName)))
                                        {
                                            productFunc.addToCart(Signin.id, productID, cardName, price, category);
                                        }

                                        home.changePanel(Home.CategoryPanel.cardName);

                                        Thread thread = new Thread(()->{
                                            cleanse();
                                            home.changeParentPanel(CartPanel.cardName);
                                            cartButtonImage = "res/icons/cart_green.png";
                                            cartButton.setIcon(Utils.getScaledImage(cartButtonImage, new Dimension(30,30),1));
                                            revalidate();
                                            repaint();
                                        });
                                        thread.start();

                                    }
                                });

                                gbc.gridx = 0;
                                gbc.gridy = 0;
                                add(buyButton);
                                gbc.gridy = 1;
                                add(cartButton);

                            }
                        }

                    }
                    private class RightBottomPanel extends JPanel
                    {
                        public RightBottomPanel()
                        {
                            setPreferredSize(new Dimension(980/2, 470));
                            setBackground(superWhite);

                            JSeparator separator = new JSeparator();


                            add(new RightBottomFeaturePanel(feature1, 1));
                            add(new JSeparator() { public Dimension getPreferredSize() {return new Dimension(980/2 - 70,1);}});
                            add(new RightBottomFeaturePanel(feature2, 2));
                            add(new JSeparator() { public Dimension getPreferredSize() {return new Dimension(980/2 - 70,1);}});
                            add(new RightBottomFeaturePanel(feature3, 3));
                            add(new JSeparator() { public Dimension getPreferredSize() {return new Dimension(980/2 - 70,1);}});
                            add(new RightBottomFeaturePanel(feature4, 4));
                            add(new JSeparator() { public Dimension getPreferredSize() {return new Dimension(980/2 - 70,1);}});
                            add(new RightBottomFeaturePanel(feature5, 5));
                        }
                        private class RightBottomFeaturePanel extends JPanel
                        {
                            public RightBottomFeaturePanel(String feature, int index)
                            {
                                setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                                setPreferredSize(new Dimension(980/2, 70));
                                setBackground(superWhite);


                                add(new FeatureIconPanel(index));
                                add(new FeatureTextPanel(feature));
                            }
                            private class FeatureIconPanel extends JPanel
                            {
                                public FeatureIconPanel(int index)
                                {
                                    setLayout(new FlowLayout(FlowLayout.LEFT, 0 , 0));
                                    setBackground(superWhite);
                                    setPreferredSize(new Dimension(60, 42));
                                    JLabel featureIcon = new JLabel();
                                    featureIcon.setIcon(Utils.getScaledImage("res/" + category + "/" + cardName + "/Icons/"+ index + ".png", new Dimension(35,35), 1));
                                    add(featureIcon);
                                }
                            }
                            private class FeatureTextPanel extends JPanel
                            {
                                public FeatureTextPanel(String feature)
                                {
                                    setPreferredSize(new Dimension((980/2)-80, 70));
                                    setBackground(superWhite);
                                    JLabel featureLabel = new JLabel();
                                    featureLabel.setFont(Utils.getFontSFPro(17f));
                                    featureLabel.setText("<html><b>" + feature + "</b></html>");
                                    featureLabel.setVerticalAlignment(JLabel.TOP);
                                    featureLabel.setPreferredSize(new Dimension((980/2)-80 ,70));
                                    add(featureLabel);
                                }
                            }
                        }
                    }
                }
                private class BottomBottomPanel extends JPanel
                {

                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        Graphics2D g2d = (Graphics2D) g;
                        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        int width = getWidth();
                        int height = getHeight();

                        GeneralPath shape = new GeneralPath();
                        shape.moveTo(0 + 30, 0);
                        shape.lineTo(0 + width - 0, 0);
                        shape.quadTo(0 + width, 0, 0 + width, 0 + 30);
                        shape.lineTo(0 + width, 0 + height - 30);
                        shape.quadTo(0 + width, 0 + height, 0 + width - 30, 0 + height);
                        shape.lineTo(0 + 30, 0 + height);
                        shape.quadTo(0, 0 + height, 0, 0 + height - 30);
                        shape.lineTo(0, 0 + 30);
                        shape.quadTo(0, 0, 0 + 0, 0);
                        shape.closePath();

                        g2d.setColor(white);
                        g2d.fill(shape);
                    }
                    protected Color white = new Color(245, 245, 247);
                    public BottomBottomPanel()
                    {
                        setOpaque(false);
                        setLayout(new GridBagLayout());
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(0, 40, 0, 40);
                        setBackground(white);
                        setPreferredSize(new Dimension(980, 100));

                        add(new BottomBottomFeature(1, "Apple Card Monthly Installments", "Pay over time, interest-free when you choose to check out with Apple Card Monthly Installments."));
                        add(new BottomBottomFeature(2, "Trade in for credit", "Get credit toward your purchase when you trade in an eligible iPhone."), gbc);
                        add(new BottomBottomFeature(1, "Special carrier deals at Apple", "Save even more on your new iPhone when you finance with select carrier deals."));


                    }
                    private class BottomBottomFeature extends JPanel
                    {
                        public BottomBottomFeature(int index, String title, String description)
                        {
                            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
                            setBackground(white);
                            setPreferredSize(new Dimension(250, 70));

                            add(new BottomBottomFeatureIcon(index));
                            add(new BottomBottomFeatureLabel(title, description));

                        }
                        private class BottomBottomFeatureIcon extends JPanel
                        {
                            public BottomBottomFeatureIcon(int index)
                            {
                                setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
                                setBackground(white);
                                setPreferredSize(new Dimension(30,30));
                                JLabel featureIcon = new JLabel();
                                featureIcon.setIcon(new ImageIcon("res/featurePanelBottom/" + index + ".png"));
                                add(featureIcon);
                            }
                        }
                        private class BottomBottomFeatureLabel extends JPanel
                        {
                            public BottomBottomFeatureLabel(String title, String description)
                            {
                                setLayout(new FlowLayout(FlowLayout.LEADING, 0 , 0));
                                setPreferredSize(new Dimension(220, 100));
                                setBackground(white);

                                JLabel featureLabelTitle = new JLabel("<html><b>" + title + "</b></html>");
                                featureLabelTitle.setFont(Utils.getFontSFPro(14f));

                                JLabel featureLabelText = new JLabel("<html>" + description + "</html>");
                                featureLabelText.setFont(Utils.getFontSFPro(12f));

                                featureLabelText.setVerticalAlignment(JLabel.TOP);


                                featureLabelText.setPreferredSize(new Dimension(210 ,100));

                                add(featureLabelTitle);
                                add(featureLabelText);
                            }
                        }
                    }
                }
            }
        }
    }
}
