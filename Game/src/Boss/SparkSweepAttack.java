package Boss;

import Living.CurrentImage;
import Living.Spark;

public class SparkSweepAttack extends Attack{

	private Spark[] sparks = new Spark[4];
	private Spark currentSpark;
	private AttackListener listener;
	
	private int current = 0;
	
	public SparkSweepAttack(Mage mage) {
		super(mage);
		currentSpark = new Spark(mage.getX() + 50, mage.getY());
		sparks[current] = currentSpark;
		getListener().onLivingCreated(currentSpark);
	}

	@Override
	public void animation() {
		
		
		if (System.currentTimeMillis() - getMage().getTime() > 1000 && current < sparks.length - 1) {
			mage.updateTime();
			current++;
			currentSpark = new Spark(mage.getX() + 50, mage.getY());
			currentSpark.setCurrent(sparks[current - 1].getCurrent() == CurrentImage.BIG ? CurrentImage.SMALL : CurrentImage.BIG);
			getListener().onLivingCreated(currentSpark);
			sparks[current] = currentSpark;
		}
		
		for (Spark spark : sparks) {
			if(spark != null)
				spark.moveEntity();
		}
		
		if (current == sparks.length - 1) {
			this.completeAttack();
			for (Spark spark : sparks) {
				spark.ground();
				spark = null;
			}
		}
	}
	

}
