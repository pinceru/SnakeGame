package game;

import java.util.Random;

public class Food {
	public static int getFoodX(Random random) {
		return random.nextInt(Config.SCREEN_WIDTH / Config.FOOD_SIZE) * Config.FOOD_SIZE;
	}
	
	public static int getFoodY(Random random) {
		return random.nextInt(Config.SCREEN_HEIGHT / Config.FOOD_SIZE) * Config.FOOD_SIZE;
	}
}
