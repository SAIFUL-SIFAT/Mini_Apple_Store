import javax.swing.*;

public class signinMain extends JFrame
{
	public signinMain()
	{		
		setSize(adminUtils.defaultSize);
		setLocationRelativeTo(null);
		setUndecorated(true);
		add(new adminSignin(signinMain.this));
		setVisible(true);
	}
}