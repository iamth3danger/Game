package Living;

public class FireBall extends Living {
    private String animationFile = "Boss/FlameBall/flameball/00_flameball.png";
    private double angle = 0;
    private int centerX = 0;
    private int centerY = 0;
    private int initialX;
    private int initialY;
    private long createTime;
    
    public FireBall(int x, int y) {
        super(x, y);
        animationFile = "Boss/FlameBall/flameball/00_flameball.png";
        centerX = getX() - 25;
        centerY = getY();
        initialX = x;
        initialY = y;
        super.init();
        createTime = System.currentTimeMillis();
    }

    public long getCreateTime() {
    	return createTime;
    }

    @Override
    public String textSymbol() {
        // implement this method
        return "F";
    }

    @Override
    public String getFile() {
        // implement this method
        return animationFile;
    }

    public void initAngle() {
        int dx = getX() - centerX;
        int dy = getY() - centerY;
        angle = Math.atan2(dy, dx);
    }
    

    public void rotate() {
        int radius = 50;
        double newX = centerX + radius * Math.cos(angle);
        double newY = centerY + radius * Math.sin(angle);

      
        
        setX((int) newX);
        setY((int) newY);

        angle += 0.05; // adjust this value to control the speed of rotation

        if (angle > 2 * Math.PI) {
            angle = 0;
        }
        
    }
    
    
    public void moveAwayFromCenter() {
        int dx = getX() - centerX;
        int dy = getY() - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double directionX = dx / distance;
        double directionY = dy / distance;

        int newX = getX() + (int) (directionX * 2); // move 10 pixels in the x direction
        int newY = getY() + (int) (directionY * 2); // move 10 pixels in the y direction

        setX(newX);
        setY(newY);
    }
    
    public static void findCenters(FireBall[] fireballs) {
        if (fireballs.length < 3) {
            throw new IllegalArgumentException("At least three fireballs are required");
        }

        int centerX;
        int centerY;
        // Choose any three fireballs
        FireBall f1 = fireballs[0];
        FireBall f2 = fireballs[1];
        FireBall f3 = fireballs[2];

        // Calculate the circumcenter of the triangle formed by these three fireballs
        int ax = f1.getX();
        int ay = f1.getY();
        int bx = f2.getX();
        int by = f2.getY();
        int cx = f3.getX();
        int cy = f3.getY();

       
        double d = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
        centerX = (int) (((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by)) / d);
        centerY = (int) (((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax)) / d);
    
        for (FireBall fireball : fireballs) {
            fireball.setCenter(centerX, centerY);
        }
         
    }
   
    public void setCenter(int centerX, int centerY){
    	this.centerX = centerX;
    	this.centerY = centerY;
    }

	@Override
	public String getAnimationFile() {
		// TODO Auto-generated method stub
		return animationFile;
	}
}