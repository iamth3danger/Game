package Boss;

import java.util.ArrayList;

import Living.Lightning;


public class LightningAttack extends Attack{
	Lightning[] strikes = new Lightning[5];
	Lightning lightning;
	int i = 0;
	private boolean isLeft;
	
	public LightningAttack(Mage mage) {
		super(mage);
		this.lightning = (mage.getX() < 550) ? new Lightning(mage.getX() + 50, 480) : new Lightning(mage.getX() - 50, 480);
		isLeft = (mage.getX() < 550) ? true : false;
		strikes[0] = lightning;
		getListener().onLivingCreated(lightning);
	}

	@Override
	public void animation() {
		
		for (Lightning light : strikes) {
			if (light != null) {
				light.update();
				if (light.getAnimation().atLastIndex()) {
					light.stricken();
					light = null;
				}
			}
		}
		if(strikes[i].getAnimation().getCurrentIndex() == 8 && i < strikes.length - 1) {
			this.lightning = isLeft ? new Lightning(strikes[i].getX() + 100, 480) : new Lightning(strikes[i].getX() - 100, 480);
			i++;
			strikes[i] = lightning;
			getListener().onLivingCreated(lightning);
		}
		
		if (i >= strikes.length - 1 || strikes[i].getX() > 700 || strikes[i].getX() < 30) {
			if (strikes[i].getAnimation().atLastIndex()) {
				this.completeAttack();
				mage.updateTime();
				for (Lightning light : strikes)
					light.stricken();
			}
		}
	}
}
