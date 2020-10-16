### Scene Builder (in Eclipse):
#### _or_ easily creating and designing windows in Java

(Add e(fx)clipse, helpful video: https://www.youtube.com/watch?v=2PxU7q9xl38)
1) Help > "Install New Software"

2) In the "Work with" field, type `http://download.eclipse.org/efxclipse/updates-released/2.3.0/site`

3) Check "e(fx)clipse - install"
![javafx-sb](https://resources.cnewb.co/CSCE247/javafx-sb-1.png)

4) Next > Accept the terms > Install (restart eclipse when prompted)

5) Download the scene-builder from [here](https://gluonhq.com/products/scene-builder/#download) (be sure you get the Java 11 version, we're **not** using Java 8.)
![javafx-sb-2](https://resources.cnewb.co/CSCE247/javafx-sb-2.png)

6) Run and install Scene Builder, return to Eclipse

7) You now need to add your newly installed Scene Builder. Window > Preferences > JavaFX, to the right of "SceneBuilder executable" enter `C:\Program Files\SceneBuilder\SceneBuilder.exe`
![javafx-sb-3](https://resources.cnewb.co/CSCE247/javafx-sb-3.png)

Scene-Builder is now installed, right click a `.fxml` file and click "Open with Scene Builder" to use it.
_Our window (`.fxml`) files are stored in `John/src/main/resources/com/papa247/john/`_
![fxml-example](https://resources.cnewb.co/CSCE247/open-fxml.png)






### Set Eclipse to run JDK15:
Be sure you've installed JDK15 first, download it here: [JDK15](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html).
In Eclipse do:
1) Right click the "John" project > Properties (Alt+Enter)
![jdk-1](https://resources.cnewb.co/CSCE247/jdk-1.png)

2) "Java Build Path" > "JRE System Library", then CLICK "Edit..."
![jdk-2](https://resources.cnewb.co/CSCE247/jdk-2.png)

3) Select "Alternate JRE:", click "Installed JREs..."
![jdk-3](https://resources.cnewb.co/CSCE247/jdk-3.png)

4) Check "jdk-15"... if there, otherwise continue to add it

5) Click "Add...", in the new window, select "Standard VM" > Next
![jdk-4](https://resources.cnewb.co/CSCE247/jdk-4.png)

6) In "JRE Home" click "Directory...", navigate to the install path for JDK15 (mine is `C:\Program Files\Java\jdk-15\`, (if you installed the 32 bit version, go to `Program Files (x86)`)) and click "Select Folder".
![jdk-5](https://resources.cnewb.co/CSCE247/jdk-5.png)

7) Eclipse will automatically populate the fields, click "Finish"

8) Check "jdk-15" to set as default. Click "Apply and Close"

All done!





### Troubleshooting
#### Something about Java 1.8 / 8 not being used:
![jesus-java-1](https://resources.cnewb.co/CSCE247/jesus-java-1.png)
You can disable that message in Preferences > General > Startup and Shutdown and unchecking 


#### _Version XXX of the JVM is not suitable for this product..._
![jesus-java-2](https://resources.cnewb.co/CSCE247/jesus-java-2.png)
Eclipse is trying to run version XXX of Java, not JDK15 (or at least ...)
1) In your Eclipse installation folder, open this file `eclipse.ini` (probably at `C:\Users\%username%\eclipse\java-YYYY-MMD\eclipse`)
2) Around line 13 change
`-vm 
_some path_`
to 
`-vm
C:\Program Files\Java\jdk-15\bin`
![jesus-java-3](https://resources.cnewb.co/CSCE247/jesus-java-3.png)