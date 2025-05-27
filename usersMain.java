import javax.swing.*;

public class usersMain extends JFrame
{
	public usersMain()
	{		
		setSize(adminUtils.defaultSize);
		setLocationRelativeTo(null);
		setUndecorated(true);
		add(new usersPanel(usersMain.this));
		setVisible(true);
	}
}