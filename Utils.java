import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Utils
{
    public static final Color black = new Color(29, 29, 31);
    public static final Color white = new Color(245, 245, 247);
    public static final Color superWhite = new Color(255,255,255);
    public static final Color blue = new Color(0, 113, 227);
    public static final Color red = new Color(255, 69, 58);
    public static final Color green = new Color(40, 205, 65);
    public static final Color gray = new Color(229,229,234);
    public static final Color graygray = new Color(51,51,51);
    public static Dimension defaultSize = new Dimension(1366, 1024-30);
    public static Cursor defaultCursor = Cursor.getDefaultCursor();
    public static Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	
    public static ImageIcon getScaledImage(String imagePath, Dimension panelSize, double multiplier)
    {
        int height = panelSize.height;
        int width = panelSize.width;

        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            if(imagePath.contains("res_user"))
            {
                try {
                    img = ImageIO.read(new File("res_user/default.jpg"));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else {
                e.printStackTrace();
            }

        }

        if(img.getHeight() < height && img.getWidth() < width){return new ImageIcon(img);}

        if(img.getWidth() > img.getHeight())
        {
            height = (img.getHeight()*width)/img.getWidth();
        }
        else
        {
            width = (img.getWidth()*height)/ img.getHeight();
        }

        Image dimg = img.getScaledInstance((int)(width * multiplier), (int)(height * multiplier),
                Image.SCALE_SMOOTH);

        return new ImageIcon(dimg);
    }

    public static Font getFontSFPro(float size)
    {
        Font SFPro = null;
        try {
            SFPro = Font.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/SFPro/SF-Pro-Display-Regular.ttf")).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(SFPro);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return SFPro;
    }

    public static class RoundedBorder implements Border {
        private int radius;
        private Color borderColor;
        RoundedBorder(int radius, Color borderColor) {
            this.radius = radius;
            this.borderColor = borderColor;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(borderColor);
            g2.fillRoundRect(0, 0, width, height, radius, radius);
        }
    }

    public static void generateCards(int amount)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/icons/card.png"));
            Graphics g = image.getGraphics();
            g.setFont(g.getFont().deriveFont(30f));
            g.drawString("Hello World!", 100, 100);
            g.dispose();

            ImageIO.write(image, "png", new File("test.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
