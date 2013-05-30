package main.game.entities.controls;

public class ReaperControl extends SpacecraftControl {

	// TODO replace REAPER_MULTIPLYIER SENSITIVITY_* with the correct settings
	//SENSITIVITY_Y
	//SENSITIVITY_X
	@Override
	public void leftRotation() {
		changeDirection(REAPER_MULTIPLYIER*(-1)*SENSITIVITY_Y);
		
	}

	@Override
	public void rightRotation() {
		changeDirection(REAPER_MULTIPLYIER*SENSITIVITY_Y)
		
	}

	@Override
	public void upRotation() {
		changeDirection(REAPER_MULTIPLYIER*(-1)*SENSITIVITY_X);
		
	}

	@Override
	public void downRotation() {
		changeDirection(REAPER_MULTIPLYIER*SENSITIVITY_X)
		
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
