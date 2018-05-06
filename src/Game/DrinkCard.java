package Game;

public class DrinkCard {
	private int redConsumations;
	private int witheConsumations;
	
	public DrinkCard() {
		redConsumations = 0;
		witheConsumations = 0;
	}
	
	public boolean useComsumation(boolean bIsRedWine) {
		if ((redConsumations + witheConsumations) > 20) {
			if (bIsRedWine) {
				redConsumations++;
			} else {
				witheConsumations++;
			}
			return true;
		}
		return false;
	}
}
