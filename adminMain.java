import javax.swing.*;

public class adminMain extends JFrame
{
	public adminMain()
	{		
		setSize(adminUtils.defaultSize);
		setLocationRelativeTo(null);
		setUndecorated(true);
		add(new adminMainPanel(adminMain.this));
		setVisible(true);
	}
}