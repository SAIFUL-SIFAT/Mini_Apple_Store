import javax.swing.*;

public class detailsMain extends JFrame
{
	public detailsMain()
	{		
		setSize(adminUtils.defaultSize);
		setLocationRelativeTo(null);
		setUndecorated(true);
		add(new detailsPanel(detailsMain.this));
		setVisible(true);
	}
}