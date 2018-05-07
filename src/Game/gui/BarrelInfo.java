package Game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import API.Utility.Vector;

import java.util.concurrent.atomic.AtomicInteger;

public class BarrelInfo extends JPanel {
	private JLabel wineLabel, lockedLabel;
	private AtomicInteger mlWineVar;

	public BarrelInfo(Vector size, AtomicInteger mlWineVar) {
		this.mlWineVar = mlWineVar;

		wineLabel = new JLabel("Litri vino: ");
		lockedLabel = new JLabel("LIBERO");

		setPreferredSize(size.toDimension());
		add(wineLabel);
		add(lockedLabel);
	}
	
	public void updateWineValue() {
		wineLabel.setText("Litri vino: " + mlWineVar.get()/1000.f);
	}

	public void setLocked(boolean locked){
		lockedLabel.setText(locked ? "BLOCCATO" : "LIBERO");
	}
}
