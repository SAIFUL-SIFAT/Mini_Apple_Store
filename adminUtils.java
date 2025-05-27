import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
public class adminUtils {
    public static Color red = new Color(255, 69, 58);
    public static Dimension defaultSize = new Dimension(1500, 750);

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

    public static void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        if(file.exists()){
            for (File subfile : file.listFiles()) {

                // if it is a subfolder,e.g Rohan and Ritik,
                // recursiley call function to empty subfolder
                if (subfile.isDirectory()) {
                    deleteDirectory(subfile);
                }

                // delete files and empty subfolders
                subfile.delete();
            }
        }

    }
    public static void generateImage(String cardNumber, double amount, int card_id)
    {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new File("res/icons/card.png"));
            Graphics2D graphics = (Graphics2D)(bufferedImage.getGraphics());
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(new Color(0, 113, 227));
            graphics.setFont(getFontSFProSemiBold(22f));
            graphics.drawString("$" + amount, 110, 100);

            graphics.setColor(new Color(255, 69, 58));
            graphics.setFont(getFontKredit(19f));
            graphics.drawString(cardNumber, 91, 282);

            File out = new File("cards/" + card_id + ".png");
            ImageIO.write(bufferedImage, "png", out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Font getFontKredit(float size)
    {
        Font SFPro = null;
        try {
            SFPro = Font.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/kreditf.ttf")).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(SFPro);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return SFPro;
    }
    public static Font getFontSFProSemiBold(float size)
    {
        Font SFPro = null;
        try {
            SFPro = Font.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/SFPRODISPLAYSEMIBOLD.ttf")).deriveFont(size);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(SFPro);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return SFPro;
    }

    
}
