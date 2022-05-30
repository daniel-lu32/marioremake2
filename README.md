# Super Mario Bros 
#### created by: Daniel Lu and Breanna Thayillam
## Objective 
![final gif](https://user-images.githubusercontent.com/90793524/170924315-098eb6b4-9b00-44d1-8a48-12bf6080a26e.gif)
The main objective of the game is to free Princess Peach. In order to do this, you must collect all 3 keys while watching out for Goombas and spikes along the way, which could cost you some lives. There are some hidden power ups to help you as well, can you find them?

#### Keys:
* left arrow: run left
* right arrow: run right
* up arrow: jump
* down arrow: teleport through pipes

## Classes

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
