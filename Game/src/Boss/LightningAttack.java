package Boss;

import java.util.ArrayList;

import Living.Lightning;

public class LightningAttack extends Attack{
	Lightning[] strikes = new Lightning[5];
	Lightning lightning;
	int i = 0;
	
	public LightningAttack(Mage mage) {
		super(mage);
		this.lightning = new Lightning(mage.getX() + 50, 480);
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
			this.lightning = new Lightning(strikes[i].getX() + 100, 480);
			i++;
			strikes[i] = lightning;
			getListener().onLivingCreated(lightning);
		}
		
		if (i >= strikes.length - 1) {
			if (strikes[i].getAnimation().atLastIndex()) {
				this.completeAttack();
				mage.updateTime();
				for (Lightning light : strikes)
					light.stricken();
			}
		}
		
		System.out.println(i);
	}
}
