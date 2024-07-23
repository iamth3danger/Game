In this project, I made a 2d side scroller called KnightOut. I thought that it would be fun to make a video game. This game contains a level and a boss level. The entire project contains features like a level designer and a lot of image handling.
# How to use
Download the Game.jar file and make sure you have java installed. Go to the directory that the jar is installed in the terminal and write java -jar Game.jar


# How it works
The game is built off of javax.swing. The game itself is a JFrame and all of the sprites and tiles are images put on to the JFrame. Then the game has an update function, run, that repaints the screen 60 times per second. The player is moved with the arrow keys to move around
and c to do an attack. The player uses an AABB collision detection system. Both the sprite and player have a hit box. When the boxes over lap it moves the player back in the opposite direction. This prevents the player from falling through the ground.
Almost everything on the screen is a subclass of the Entity class. They are then divided into Boss, Creature, Living and Moving. The enemies are part of the Creature class, The Mage Boss is a subclass of the Boss class, The Living subclass is all of the attacks that the boss
does and the Moving class is all of the tiles that move in the game. The levels are loaded from text files using characters to represent each object. Each character represents 32 pixels
I used the Factory Pattern to make an EntityFactory that makes an Entity at the position that the character is on the array.

# Level Maker
![](https://github.com/iamth3danger/Game/blob/master/GIFs/LevelMakerDemo.gif)












The way that the level was designed was through a Level Designer that I made. It would print out an object that you chose to the screen and then you can save it to a text file when it's done. The Level Designer can also be used to edit levels. 
In the LevelMaker.java file you change the levelFile variable to whatever textfile you would like to access. To use LevelMaker.java, You pressed the arrow keys to move around the level, click or click and drag to add entities, right click or right click and drag
to remove entities, M for menu to access the available entities to print, Escape to pull up the save screen, P to acess the Player Mode, And E to access Edit Mode.

# What is Player Mode?





![](https://github.com/iamth3danger/Game/blob/master/GIFs/LevelMakerPlayer.gif)





Player Mode allows the user to play through the level as a player. It has the same controls as the game. There are arrow buttons on the top of the screen. The up and down arrows control the y velocity and the left and right arrows control the x velocity. 
The reason that I did this was to be able to test the height of the jumps in real time. This was to make sure that the level didn't have any impossible jumps to make. If there is a hard jump or some other obstacle, you can go into Edit mode to change the level.

# Boss Level







![](https://github.com/iamth3danger/Game/blob/master/GIFs/Boss.gif)





This was particularly hard to design because of the attacks and movement. The boss moves around with a parametric equation (r * cos(5 * pi * t), r * sin(7 * pi * t)) . I did this to attempt to give the Boss a more natural movement than a normal circle or oval.
There are multiple attacks that the Mage casts randomly. They take the Living Objects and used them in the Attack class. These classes animate the class. For example, for the FireBalls, the FireBallAttack makes 12 fireballs and moves them in a circular pattern for 
two seconds until it stops spinning and expands in all directions. The SparkSweepAttack takes two different spark balls and alternates them and moves down in an ellipse to attack the player and also giving the player an opportunity to strike.
