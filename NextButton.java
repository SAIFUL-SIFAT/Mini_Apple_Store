import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NextButton extends JButton
{
    private String nextCardName = Signin.cardName;

    public void setNextCardName(String nextCardName)
    {
        this.nextCardName = nextCardName;
    }
    public String getNextCardName()
    {
        return  nextCardName;
    }
    public NextButton(MainPanel mainPanel)
    {

    }
}