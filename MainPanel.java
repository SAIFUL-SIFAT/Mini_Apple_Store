import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainPanel extends JPanel
{
    private Signin signinPanel;
    private Signup signupPanel;
    private Reset resetPanel;
    private Home homePanel;
    private AccountPanel accountPanel;
    private CartPanel cartPanel;

    public MainPanel()
    {
        signinPanel = new Signin(this);
        signupPanel = new Signup(this);
        resetPanel = new Reset(this);
        homePanel = new Home(this);
        accountPanel = new AccountPanel(this);
        cartPanel = new CartPanel(this);


        this.setLayout(new CardLayout());
        this.setPreferredSize(new Dimension(1366, 1024));

        this.add(signinPanel, Signin.cardName);
        this.add(signupPanel, Signup.cardName);
        this.add(resetPanel, Reset.cardName);
        this.add(homePanel, Home.cardName);
        this.add(accountPanel, AccountPanel.cardName);
        this.add(cartPanel, CartPanel.cardName);

        KeyboardFocusManager keyManager;
        keyManager=KeyboardFocusManager.getCurrentKeyboardFocusManager();
        keyManager.addKeyEventDispatcher(new KeyEventDispatcher() {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
                if(e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==112){
                    MainPanel.this.changePanel(Home.cardName);
                    return true;
                }
                else if(e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==113){
                    MainPanel.this.changePanel(AccountPanel.cardName);
                    return true;
                }
                else if(e.getID()==KeyEvent.KEY_PRESSED && e.getKeyCode()==114){
                    MainPanel.this.changePanel(CartPanel.cardName);
                    return true;
                }
                return false;
            }
        });
    }
    public void changePanel(String cardName)
    {
        CardLayout cl = (CardLayout)getLayout();
        cl.show(this, cardName);
    }
}
