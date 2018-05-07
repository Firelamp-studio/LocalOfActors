package game;

public class DrinkCard {
	private int redConsumations;
	private int witheConsumations;
	
	public DrinkCard() {
		redConsumations = 0;
		witheConsumations = 0;
	}
	
	public boolean hasComsumation() {
		if ((redConsumations + witheConsumations) < 20)
			return true;
		return false;
	}

	public boolean useConsumation(boolean bIsRedWine) {
		if (hasComsumation()) {
			if (bIsRedWine)
				redConsumations++;
			else
				witheConsumations++;
			return true;
		} else
			return false;
	}

	public int getRedConsumations() {
		return redConsumations;
	}

	public int getWitheConsumations() {
		return witheConsumations;
	}
}
