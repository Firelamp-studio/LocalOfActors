package game.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import api.utility.Vector;
import game.DrinkCard;

public class CustomerInfo extends JPanel{
	private JLabel customerNum, whiteWineLabel, redWineLabel, intentionLabel;
	private DrinkCard drinkCard;
	private final int customerIndex;
	
	public CustomerInfo(Vector size, int customerIndex) {
		whiteWineLabel = new JLabel("Consumazioni vino bianco: 0");
		redWineLabel = new JLabel("Consumazioni vino rosso: 0");
		customerNum = new JLabel("Cliente " + customerIndex);
		intentionLabel = new JLabel("\"Voglio entrare alla festa\"");

		this.customerIndex = customerIndex;
		this.drinkCard = drinkCard;

		setPreferredSize(size.toDimension());
		add(customerNum);
		add(whiteWineLabel);
		add(redWineLabel);
		add(intentionLabel);
		
		setVisible(false);
	}

	public void bindDrinkCard(DrinkCard drinkCard) {
		this.drinkCard = drinkCard;
	}

	public void updateDrinkCard() {
		if(drinkCard != null){
			whiteWineLabel.setText("Consumazioni vino bianco: " + drinkCard.getWitheConsumations());
			redWineLabel.setText("Consumazioni vino rosso: " + drinkCard.getRedConsumations());
		}
	}

	public void setIntention(String intention){
	    //Print to console the intention
        System.out.println("Cliente " + customerIndex + ": " + intention);

        intentionLabel.setText("\"" + intention + "\"");
	}
}
