package Game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import API.Utility.Vector;

public class BarrelInfo extends JPanel {
	JLabel wineLabel;
	
	public BarrelInfo(Vector size) {
		wineLabel = new JLabel("Litri vino: ");
		
		setPreferredSize(size.toDimension());
		add(wineLabel);
	}
	
	public void setWineValue(int litres) {
		wineLabel.setText("Litri vino: " + litres);
	}
}
