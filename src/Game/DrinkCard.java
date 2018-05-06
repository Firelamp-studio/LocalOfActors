package Game;

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

	public boolean useConsumatABoolean(boolean bIsRedWine) {
		if (hasComsumation()) {
			if (bIsRedWine)
				redConsumations++;
			else
				witheConsumations++;
			return true;
		} else
			return false;
	}
}
