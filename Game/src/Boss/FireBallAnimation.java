package Boss;

import Living.FireBall;

public class FireBallAnimation extends Attack{

	private FireBall[] fireballs;
	private State state;
	
	FireBallAnimation(Mage mage){
		super(mage);
		state = new State(this);
		state.setState(new Create(this));
	}
	
	

	
	    
	    public void animation() {

	    	if (System.currentTimeMillis() - getMage().getTime() < 2000){
	    		state.setState(new Rotate(this));
	    	}
	    	else {
	    		state.setState(new Attack(this));
	    		if (System.currentTimeMillis() - getMage().getTime() > 5000) {
	    			state.setState(new Extinguish(this));
	    			getMage().updateTime();
	    			completeAttack();
	    		}
	    	}
	    }
	    
	    private class State{
	    	protected State currentState;
	    	FireBallAnimation animation;
	    	public State(FireBallAnimation animation) {
	    		this.animation = animation;
	    	}
	    	
	    	protected void setState(State state) {
	    		currentState = state;
	    	}
	    }
	    
	    private class Rotate extends State{
	    	public Rotate(FireBallAnimation animation) {
	    		super(animation);
	    		rotateFireballs();
	    	}
	    	
	    	 private void rotateFireballs() {
	 	        for (FireBall fireball : fireballs) {
	 	        	fireball.update();
	 	            fireball.rotate();
	 	        }
	 	    }
	    }
	    
	    private class Attack extends State{
	    	public Attack(FireBallAnimation animation) {
	    		super(animation);
	    		fireAttack();
	    	}
	    	
	    	private void fireAttack() {
		    	for (FireBall fireball : fireballs) {
		        	fireball.update();
		            fireball.moveAwayFromCenter();
		        }
		    }
	    }
	    
	    private class Extinguish extends State{
	    	public Extinguish(FireBallAnimation animation) {
	    		super(animation);
	    		extinguish();
	    	}
	    	
	    	 private void extinguish() {
	 	    	for (FireBall fireball : fireballs) {
	 	        	fireball.extinguish();
	 	        }
	 	    }
	    }
	    
	    private class Create extends State{
	    	public Create(FireBallAnimation animation) {
	    		super(animation);
	    		createFireBalls();
	    	}
	    	
	    	private void createFireBalls() {
		    	fireballs = new FireBall[8];
		        for (int i = 0; i < 8; i++) {
		            double angle = Math.PI / 4 * i;
		            int fireballX = (int) (getMage().getX() + (getMage().getWidth() / 2) + 50 * Math.cos(angle));
		            int fireballY = (int) (getMage().getY() + (getMage().getHeight() / 2) + 50 * Math.sin(angle));
		            fireballs[i] = new FireBall(fireballX, fireballY);
		            getListener().onFireBallCreated(fireballs[i]);
		        }
		        FireBall.findCenters(fireballs);
		        for (FireBall fire : fireballs)
		            fire.initAngle();
		    }
	    }
	    
}


