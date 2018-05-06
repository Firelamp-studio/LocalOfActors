package Game;

public class DrinkCard {
	private int redConsumations;
	private int witheConsumations;
	
	public DrinkCard() {
		redConsumations = 0;
		witheConsumations = 0;
	}
	
	public void useComsumation(boolean bIsRedWine) throws Exception {
		if ((redConsumations + witheConsumations) >= 20) {
			if (bIsRedWine) {
				redConsumations++;
			} else {
				witheConsumations++;
			}
		} else {
			throw new Exception("No more consumations left");
		}
		
	}
}
