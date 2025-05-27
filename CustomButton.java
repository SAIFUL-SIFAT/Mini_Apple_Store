import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomButton extends JButton {

    private Cursor handCursor;
    private Cursor defaultCursor;
    private Color color;

    private Color borderColor;
    private float fontSize = 16f;
    private int radius = 30;

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 113, 227));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(grphcs);
    }


    public CustomButton(String label, int radius, Color buttonColor, Dimension size)
    {

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(Color.BLACK);
                setForeground(Color.WHITE);
            }
        });

        setOpaque(false);
        this.radius = radius;
        this.color = buttonColor;
        this.setPreferredSize(size);

        defaultCursor = this.getCursor();
        handCursor = new Cursor(Cursor.HAND_CURSOR);

        setBackground(this.color);
        setForeground(Color.WHITE);

        setFont(Utils.getFontSFPro(fontSize));
        setText(label);

        borderColor = new Color(0, 113, 227);
        setBorderPainted(false);
        setFocusPainted(false);       

    }
}