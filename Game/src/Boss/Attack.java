package Boss;

public abstract class Attack {
	Mage mage;
	AttackListener listener;
	private boolean attackComplete = false;
	
	public Attack(Mage mage) {
		this.mage = mage;
		this.listener = mage.getListener();
	}
	
	protected Mage getMage() {
		return mage;
	}
	
	public abstract void animation();
	
	protected AttackListener getListener() {
		return listener;
	}
	
	public boolean isCompleted() {
		return attackComplete;
	}
	
	protected void completeAttack() {
		attackComplete = true;
	}
}
