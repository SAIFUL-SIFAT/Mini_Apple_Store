import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabButton extends JPanel
{
    
    private float fontSize = 14f;
    private int radius = 25;
    public boolean isFocused = false;
    public Color black = new Color(29, 29, 31);
    public Color white = new Color(245, 245, 247);
    JLabel buttonText;

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(black);
        if(isFocusable() && !isFocused){ g2.setColor(getParent().getBackground()); }
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }

    public TabButton(String label, int radius, Dimension size)
    {
      
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));
        this.radius = radius;
        
        
        Cursor defaultCursor = this.getCursor();
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        buttonText = new JLabel("<html><b>" + label + "</b></html>");
        buttonText.setFont(Utils.getFontSFPro(fontSize));
        buttonText.setBorder(new EmptyBorder(1, 5, 1, 5));

        add(buttonText);

        requestFocusInWindow();

        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                                
                requestFocusInWindow();                
            }
            @Override
            public void mouseEntered(MouseEvent me) {
                
                setCursor(handCursor);                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
                setCursor(defaultCursor);                
            }
        });
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                
                isFocused = true;
                repaint();
                buttonText.setForeground(white);
            }

            @Override
            public void focusLost(FocusEvent e) {
                
                isFocused = false;
                repaint();
                buttonText.setForeground(black);
            }
        });

    }
}
