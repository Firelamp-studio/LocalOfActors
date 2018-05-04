package Game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import API.Utility.Vector;

public class CustomerInfo extends JPanel{
	JPanel container;
	JLabel customerNum, whiteWineLabel, redWineLabel;
	
	public CustomerInfo(Vector size) {
		whiteWineLabel = new JLabel("Litri vino bianco: ");
		redWineLabel = new JLabel("Litri vino rosso: ");
		customerNum = new JLabel("Cliente");
		
		setPreferredSize(size.toDimension());
		add(customerNum);
		add(whiteWineLabel);
		add(redWineLabel);
		
		setVisible(false);
	}
	
	public void setWhiteWineValue(int litres) {
		whiteWineLabel.setText("Litri vino bianco: " + litres);
	}
	
	public void setRedWineValue(int litres) {
		redWineLabel.setText("Litri vino rosso: " + litres);
	}
	
	public void setCustomerNum(int num) {
		redWineLabel.setText("Customer " + num);
	}
}
