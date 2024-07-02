package Boss;

public class AttackFactory {

	public static Attack createAttack(AttackType attack, Mage mage) {
		switch(attack) {
		case FIREBALL:
			return new FireBallAnimation(mage);
		case SHADOW:
			return new ShadowAttack(mage);
		case LIGHTNING:
			return new LightningAttack(mage);
		default:
			throw new IllegalArgumentException("Invalid Attack type");
		}
	}
}

