### Setting up GitHub Desktop and this repo

1) Open GitHub for Desktop

2) Assuming you're already setup with GitHub desktop, (I.E. signed in), click the "Current repository" drop down in the top left.
![2](https://resources.cnewb.co/CSCE247/2.png)



3) Select "Clone repository..."
![3](https://resources.cnewb.co/CSCE247/3.png)



4) Type in "PAPA-247" and select "PAPA-247/john"
![4](https://resources.cnewb.co/CSCE247/4.png)


(To change the save location: )
5) Click "Choose.." in the bottom right of the "Clone a repository" menu
![5](https://resources.cnewb.co/CSCE247/5.png)



6) Navigate to `C:\Users\%username%\eclipse-workspace`, click "Select Folder"
![6](https://resources.cnewb.co/CSCE247/6.png)


6) Click clone

7) In Eclipse, click File > Import > Maven > Existing Maven Projects > Next
![import-1](https://resources.cnewb.co/CSCE247/import-1.png)

8) Point "Root Directory" to where ever you cloned the project (For example: `C:\Users\%username%\eclipse-workspace\John`)

9) Click "Finish"


**IMPORTANT** You _need_ to (Setup JDK11!)[/Eclipse%20How-To.md#set-eclipse-to-run-jdk11] Besure you do that, otherwise you're going to see about several hundred errors.

That's it
(It may take a few seconds to build, however)
