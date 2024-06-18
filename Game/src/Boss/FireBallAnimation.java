package Boss;

import Living.FireBall;

public class FireBallAnimation {
	private Mage mage;
	private FireBallListener listener;
	private FireBall[] fireballs;
	
	FireBallAnimation(Mage mage, FireBallListener listener){
		this.mage = mage;
		this.listener = listener;
		createFireBalls();
	}
	
	 private void rotateFireballs() {
	        for (FireBall fireball : fireballs) {
	        	fireball.update();
	            fireball.rotate();
	        }
	    }

	    private void fireAttack() {
	    	for (FireBall fireball : fireballs) {
	        	fireball.update();
	            fireball.moveAwayFromCenter();
	        }
	    }
	    
	    private void extinguish() {
	    	for (FireBall fireball : fireballs) {
	        	fireball.extinguish();
	        }
	    }
	    
	    private void createFireBalls() {
	    	fireballs = new FireBall[8];
	        for (int i = 0; i < 8; i++) {
	            double angle = Math.PI / 4 * i;
	            int fireballX = (int) (mage.getX() + (mage.getWidth() / 2) + 50 * Math.cos(angle));
	            int fireballY = (int) (mage.getY() + (mage.getHeight() / 2) + 50 * Math.sin(angle));
	            fireballs[i] = new FireBall(fireballX, fireballY);
	            listener.onFireBallCreated(fireballs[i]);
	        }
	        FireBall.findCenters(fireballs);
	        for (FireBall fire : fireballs)
	            fire.initAngle();
	    }
	    
	    public void animation() {

	    	if (System.currentTimeMillis() - mage.getTime() < 2000){
	    		rotateFireballs();
	    	}
	    	else {
	    		fireAttack();
	    		if (System.currentTimeMillis() - mage.getTime() > 5000) {
	    			extinguish();
	    			createFireBalls();
	    			mage.updateTime();
	    		}
	    	}
	    }
}
