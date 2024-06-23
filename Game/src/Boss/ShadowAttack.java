package Boss;

import Living.Shadow;

public class ShadowAttack extends Attack{

	Mage mage;
	AttackListener listener;
	Shadow shadow;
	boolean shadowMade = false;
	
	public ShadowAttack(Mage mage) {
		super(mage);
	}
	
	 public void animation() {
	    	if (System.currentTimeMillis() - getMage().getTime() > 500 && !shadowMade) {
	    		shadow = new Shadow(200, 538);
	    		getListener().onShadowCreated(shadow);
	    		shadowMade = true;
	    		if (shadow.getAnimation() == null) {
	    			System.out.println("null");
	    		}
	    	}
	    	
	    	if(shadow != null) {
	    		shadow.update();
	    		if(shadow.getAnimation().atLastIndex()) {
	    			System.out.println("ooop");
	    			shadow.light();
	    			shadow = null;
	    			getMage().updateTime();
	    			completeAttack();
	    		}
	    	
	    	}
	    }
}
