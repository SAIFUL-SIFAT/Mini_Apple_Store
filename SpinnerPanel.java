import javax.swing.*;
import java.awt.*;
public class SpinnerPanel extends JPanel
{
    private  MainPanel mainPanel;
    private  String nextCardName;
    private String cardName = "SPINNERPANEL";
    public SpinnerPanel(MainPanel mainPanel, long seconds, String nextCardName)
    {
        this.mainPanel = mainPanel;
        this.nextCardName = nextCardName;

        setPreferredSize(Utils.defaultSize);
        setBackground(Utils.superWhite);
        setLayout(new GridBagLayout());
        mainPanel.add(this, cardName);
        mainPanel.changePanel(cardName);

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon("res/icons/spinner_small.gif"));
        add(label);

        changePanel(seconds);
    }
    public void changePanel(long seconds)
    {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        mainPanel.changePanel(nextCardName);
                        mainPanel.remove(SpinnerPanel.this);
                    }
                },
                seconds*1000
        );
    }
}