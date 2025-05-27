import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundButton extends JPanel
{
    private int fontSize = 20;
    private int radius = 50;
    private Color black = new Color(110, 110, 115);
    private Color white = new Color(232, 232, 237);
    JLabel buttonText;

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }

    public RoundButton(String label, Dimension size)
    {

        setOpaque(false);
        setLayout(new GridBagLayout());
        setPreferredSize(size);
        setMaximumSize(getPreferredSize());
        Cursor defaultCursor = this.getCursor();
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        buttonText = new JLabel(label, JLabel.CENTER);
        buttonText.setFont(new Font("Monospaced", Font.PLAIN, fontSize));
        buttonText.setForeground(black);

        add(buttonText);

        //  Add event mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                super.mouseEntered(me);
                setCursor(handCursor);
            }

            @Override
            public void mouseExited(MouseEvent me) {
                super.mouseExited(me);
                setCursor(defaultCursor);
            }

        });

    }
}
