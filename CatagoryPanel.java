import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CatagoryPanel extends JPanel
{
	protected int radius = 30;

    @Override
    protected void paintComponent(Graphics g) {
       super.paintComponent(g);
       Graphics2D graphics = (Graphics2D) g;
       graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       graphics.setColor(Utils.superWhite);
       graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	}
	
    public CatagoryPanel(String category, String title, String imagePath, double imageMultiplier, ProductPage productPage, Home home)
    {
        setOpaque(false);

        setPreferredSize(new Dimension(300, 310));
        setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));

        JLabel categoryLabel = new JLabel(title, JLabel.CENTER);
        categoryLabel.setFont(Utils.getFontSFPro(15f));

        JLabel categoryImage = new JLabel();
        categoryImage.setIcon(Utils.getScaledImage(imagePath, new Dimension(270,270), imageMultiplier));
        categoryImage.setHorizontalTextPosition(JLabel.CENTER);
        categoryImage.setVerticalTextPosition(JLabel.CENTER);


        JPanel labelPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graphics = (Graphics2D) g;
                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setColor(Utils.superWhite);
                graphics.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            }
        };
        labelPanel.setOpaque(false);
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.setPreferredSize(new Dimension(300, 40));
        labelPanel.setBackground(Utils.superWhite);
        labelPanel.add(categoryLabel);


        JLabel imagePanel = new JLabel();
        imagePanel.setLayout(new GridBagLayout());
        imagePanel.setPreferredSize(new Dimension(270, 270));
        imagePanel.add(categoryImage);

        add(labelPanel);
        add(imagePanel);
		
		addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent me) {
                productPage.setupPanel(category);
                home.changePanel(ProductPage.cardName);
                productPage.setFocusOnFirstButton();
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
    }
}