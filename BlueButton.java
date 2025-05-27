import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class BlueButton extends JPanel
{
    private Color color;
    private float fontSize = 17f;
    private int radius = 25;
    public boolean isMouseOver = false;
    public Color blue = new Color(0, 113, 227);
    public Color white = new Color(255, 255, 255);
    public Color bgColor = null;
    private Color bgColorBackup = null;
    JLabel buttonText;
    MouseAdapter mouseAdapter;

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(bgColor);
        if(isMouseOver){ g2.setColor(bgColor.darker()); }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }

    public BlueButton(String label, int radius, Dimension size, Color bgColor)
    {
        if(bgColor == null) {this.bgColor = Utils.blue; this.bgColorBackup = this.bgColor;}
        else{this.bgColor = bgColor; this.bgColorBackup = this.bgColor;}

        setOpaque(false);
        setLayout(new GridBagLayout());
        this.radius = radius;
        setPreferredSize(size);
        setMaximumSize(size);
        Cursor defaultCursor = this.getCursor();
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        buttonText = new JLabel(label);
        buttonText.setFont(Utils.getFontSFPro(fontSize));
        buttonText.setForeground(white);

        add(buttonText);

        //  Add event mouse
        this.mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setCursor(handCursor);
                isMouseOver = true;
                repaint();

            }
            @Override
            public void mouseExited(MouseEvent me) {
                setCursor(defaultCursor);
                isMouseOver = false;
                repaint();
            }
        };
        addMouseListener(mouseAdapter);
        BlueButton.this.addPropertyChangeListener(e->
        {
            if(!BlueButton.this.isEnabled())
            {
                BlueButton.this.buttonText.setForeground(Color.GRAY);
                BlueButton.this.bgColor = Utils.graygray;
                BlueButton.this.removeMouseListener(BlueButton.this.mouseAdapter);
            }
            else {
                BlueButton.this.buttonText.setForeground(Utils.superWhite);
                BlueButton.this.bgColor = BlueButton.this.bgColorBackup;
                BlueButton.this.addMouseListener(BlueButton.this.mouseAdapter);
            }
        });
    }

}
