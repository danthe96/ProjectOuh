package main.game.entities.controls;

public class ReaperControl extends SpacecraftControl {


	private static final double SENSITIVITY_X = 1;
	private static final double SENSITIVITY_Y = 1;

	// TODO replace REAPER_MULTIPLYIER SENSITIVITY_* with the correct settings
	//SENSITIVITY_Y
	//SENSITIVITY_X
	@Override
	public void leftRotation() {
		changeDirection(0d, (-1)*SENSITIVITY_Y);
		
	}

	@Override
	public void rightRotation() {
		changeDirection(0d, SENSITIVITY_Y);
		
	}

	@Override
	public void upRotation() {
		changeDirection((-1)*SENSITIVITY_X, 0d);
		
	}

	@Override
	public void downRotation() {
		changeDirection(SENSITIVITY_X, 0d);
		
	}

	@Override
	public void accelerate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decelerate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void land() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lift() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void primaryShoot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void secondShoot() {
		// TODO Auto-generated method stub
		
	}

}
