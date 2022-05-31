# Super Mario Bros
#### Creators: Daniel Lu and Breanna Thayillam
## Overview and Objective
![final gif](https://user-images.githubusercontent.com/90793524/170924315-098eb6b4-9b00-44d1-8a48-12bf6080a26e.gif)

The main objective of this game is to free Princess Peach. In order to accomplish this task, the player must collect all 3 keys while watching out for Goombas and Spikes along the way, which could cost the player some lives. There are also some hidden power ups to help the player out. Can you find them?

There are 2 types of Power Ups in this game that the player can obtain: Ice and Fire. The only way to obtain these Power Ups is by collecting the hidden Ice and Fire flowers. With these Power Ups, the player is then able to eliminate enemies.

In addition, the player can teleport to another part of the map by going through certain pipes. Furthermore, the player can collect coins. Each coin will award the player a random number of coins from 10 to 80. Collecting coins greatly boosts your score, so try to collect as many as possible!

Finally, beware of the Goombas and the Spikes. Try to free Princess Peach before it is too late! Keep in mind that you are also on a timer. Good luck and have fun!

## Controls
* Left arrow: Run Left
* Right arrow: Run Right
* Up arrow: Jump
* Down arrow: Teleport Through Pipes
* Space bar: Use Power Up

## Classes
### PowerUp Class
The PowerUp Class is used to represent the 4 friendly entities that Mario can collect. The 4 PowerUps are the Ice Flower, the Fire Flower, the Big Mushroom, and the 1-UP Mushroom. Each PowerUp object has a specific picture that is retrieved in when the object is created. The Red Mushroom and Green Mushroom move back and forth between pipes.

![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/readmeimgs/powerups.png) 

### Projectile Class
The Projectile Class is used to represent the 2 types of attacks that Mario can perform. The 2 Projectiles are iceballs and fireballs. Projectiles only travel to the right.

![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/iceball.png) 
![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/fireball.png) 

### Pipe Class
The Pipe Class is used to represent the different types of pipes that are in the game. In this specific version, there are 2 short pipes and 1 long pipe. Mario can enter the long pipe and one of the short pipes in order to teleport to the other pipe. Mario can also stand on pipes and jump along their sides.

![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/pipe.png) 

### Spikes Class
The Spikes Class is used to represent all of the spikes that are laid on the ground that Mario must avoid. Each Spikes object contains 6 spikes. Whenever Mario gets hit by a spike, he will either lose his ability, become smaller, or lose a life, depending on his current state. The player will be given invincibility frames to the spikes for a short period of time.

![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/spikes.png) 

### Goomba Class
The Goomba Class is used to represent the moving Goomba mushroom that Mario must also avoid. Only one Goomba is created in this version of the game. The Goomba moves back and forth between the 2 short pipes. The Goomba class also has a collision method to test for collisions with the pipes so that it knows when to change direction.

![](https://github.com/daniel-lu32/marioremake2/blob/master/MarioRemake/src/imgs/goombagif2.gif) 

### Character2

### Background
The background class contains instance variables and methods that control the position and other features of the Background image.
The **slide method** is what determines the horizontal scrolling of the background. Two booleans are inputted for if Mario is running left (when left arrow key pressed) or running right (right arrow pressed). If Mario is running left, then the background needs to scroll to the opposite direction to make it seem like Mario is travelling across the background, so the vx is set to a positive value of 4, then added to the current X value of background. When Mario is running right, the background should move left, so the vx is set to -4. If neither running left or right is true, then the vx is set to 0 so the background doesn't move.

![image](https://user-images.githubusercontent.com/90793524/171039337-462b614b-8f30-4852-b0c4-5dae310140c1.png)

### Assets: Block, Coin, Flag, Goomba, Key, KeyDisplay, Peach, Pipe, Projectile, Spikes
Most of these classes have a basic class structure that creates an object with a certain image and sets it to a position on the screen However, most of these classes also have methods that choose which image to display under certain conditions. For example:
#### Block Class
The **chooseImage method** determines under what conditions each image should be shown. If brickType is "Mystery" and hasCoin and available are true, then the image is set to the default mystery block gif. If brickType is mystery, hasCoin is false, and available is true, this means the coins in the block have already been collected, so available is set to false and the image is set to the gif of mystery block being hit. Since this image has a different height compared to the default gif, the height and y value have to be changed. If brickType is "Normal", then the image is set to a brick image.

![image](https://user-images.githubusercontent.com/90793524/171040090-cb8f8136-1592-4f3d-bd52-b864a6e32a8d.png)

The **mystHit method** is called when Mario collides with the mystery block. It sets hasCoins to false, then calls the chooseImage method.

![image](https://user-images.githubusercontent.com/90793524/171040237-f798395a-825e-44c7-a486-b7bc691d86d2.png)

### GameRunner
#### Music
The Music class format was reused from our Quarter 2 Projects. In GameRunner, we created different Music objects, one for each instance we needed to play a sound effect.

![image](https://user-images.githubusercontent.com/90793524/171041188-dafbbdd2-78ff-47ef-a185-596fd1395181.png)

Then, whenever Mario collided with another object or did an action, these sounds would be played.

![image](https://user-images.githubusercontent.com/90793524/171041330-32e3f94a-1077-4018-aaee-be225efcc160.png)

#### 
