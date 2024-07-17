package Boss;


import Living.CurrentImage;
import Living.Spark;

public class SparkSweepAttack extends Attack{

	private Spark[] sparks = new Spark[7];
	private Spark currentSpark;
	 private MovementState currentMovement = MovementState.DOWNWARD;
	private int current = 0;
	private double angle;
	private boolean left;
	private int sweepCenterX;
	private int sweepCenterY;
	private int majorAxis;
	private int minorAxis;
	
	public SparkSweepAttack(Mage mage) {
		super(mage);
		left = mage.getX() < 400 ? true : false;
		this.angle = mage.isOnLeftSide() ? (2 * Math.PI) / 3 : Math.PI / 3;
		this.sweepCenterX = mage.isOnLeftSide() ? mage.getX() + 250 : mage.getX() - 250;
		this.sweepCenterY = mage.getY();
		this.majorAxis = 500;
		this.minorAxis = 500 - sweepCenterY;
		//currentSpark = left ? new Spark(mage.getX() + 50, mage.getY()) : new Spark(mage.getX() - 50, mage.getY());
		//currentSpark.setVelocityX(left ? 10 : -10);
		//sparks[current] = currentSpark;
		//getListener().onLivingCreated(currentSpark);
	}

	@Override
	public void animation() {
		
		
		if (mage.getY() > 400) {
			if (System.currentTimeMillis() - getMage().getTime() > 250 && current < sparks.length - 1) {
				mage.updateTime();
				
				currentSpark = left ? new Spark(mage.getX() + 50, mage.getY()) : new Spark(mage.getX() - 50, mage.getY());
				currentSpark.setVelocityX(left ? 10 : -10);
				if (current > 0)
					currentSpark.setCurrent(sparks[current - 1].getCurrent() == CurrentImage.BIG ? CurrentImage.SMALL : CurrentImage.BIG);
				else
					currentSpark.setCurrent(CurrentImage.BIG);
				getListener().onLivingCreated(currentSpark);
				sparks[current] = currentSpark;
				current++;
				
			}
			
			for (Spark spark : sparks) {
				if(spark != null)
					spark.moveEntity();
			}
			
			if (current == sparks.length) {
				//System.out.println(current);
				//this.completeAttack();
				for (int i = 0; i < sparks.length; i++) {
					//sparks[i].ground();
					if(sparks[i] == null)
						System.out.println(i);
					//sparks[i] = null;

				}
			}
		}
		sweepMovement();
	}
	

	public void sweepMovement() {
		switch(currentMovement) {
		
		case DOWNWARD:
			if(mage.getY() < 468) {
				
				mage.moveInYDirection(2);
//				mage.setVelocityY(2);
//				mage.moveEntity();
			}
			else {
				currentMovement = MovementState.ELLIPTICAL;
			}
			break;
		case ELLIPTICAL:
			if ((angle > Math.PI / 3 && left
				||(angle < (2 * Math.PI) / 3 && !left))
				) {
				
				
				//System.out.println(angle);


				double newX = sweepCenterX + majorAxis * Math.cos(1 *angle);
				double newY = sweepCenterY + minorAxis * Math.sin(1 * angle);
				double angleDelta = left ? -.01 : .01;
				mage.setX((int) newX);
				mage.setY((int) newY);
	

				angle += angleDelta;
			}
			else {
				currentMovement = MovementState.UPWARD;
			}
			break;
		case UPWARD:
			mage.moveInYDirection(-2);
			//mage.moveEntity();
			if(mage.getY() < 300) {
				mage.setVelocityY(0);
				mage.setCenterX();
				this.completeAttack();
			}
			break;
		default:
			break;
		}
	}
	
	private enum MovementState{
		UPWARD,
		DOWNWARD,
		ELLIPTICAL
	}
}
