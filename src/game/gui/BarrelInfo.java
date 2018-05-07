package game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import api.utility.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class BarrelInfo extends JPanel {
	private JLabel wineLabel;
	private AtomicInteger mlWineVar;

	public BarrelInfo(Vector size, AtomicInteger mlWineVar) {
		this.mlWineVar = mlWineVar;

		wineLabel = new JLabel("Litri vino: ");

		setPreferredSize(size.toDimension());
		add(wineLabel);
	}
	
	public void updateWineValue() {
		wineLabel.setText("Litri vino: " + mlWineVar.get()/1000.f);
	}
}
