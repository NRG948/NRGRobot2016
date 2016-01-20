# NRGRobot2016
Code for 2016 Robot
##Migration from bitbucket
1. Download Github desktop app here : https://desktop.github.com/
2. Get a Github account
3. Get Sean or Chris to add your account to the team
4. Delete the original nrgrobot2016 project from your repository. (Deleting disk contents too)
4. Clone the NRGRobot2016 repository by opening the Github app, clicking the '+' on the top left corner, and going to 'clone'. Select the NRGRobot2016 repository and download it to a local file.
5. Import project in ecplise. File > Import > General > Existing Projects Into Workspace. Navigate to where you downloaded the git repository in step 4 and finish.
6. Create a .classpath file. Right click your project in eclipse and press 'properties'. Add your wpilib files as external jars and select all of them in the 'Order and Export' tab.
7. You should be done. You can commit and push within the desktop github app.
##Code Organization
###org.usfirst.frc.team948.robot
This package contains all the classes that are used throughout robot
code. Our own implementation of FRC library classes will also go here.
#####RobotMap
This class has all the acuators and sensors in the robot.
All objects are declared statically and created in an init() method
#####DS2016
This class holds all the joysticks, buttons, and driver station
stuff that we use. Commands are attached to buttons here
#####Robot
This class is where all the code comes together. 
###org.usfirst.frc.team948.robot.commands
This package holds all the commands
#####CommandBase
This class is our own commandbase which will hold all subsystem objects
as well as position tracker and preferences
###org.usfirst.frc.team948.robot.subsystems
This package holds our subsystems
###org.usfirst.frc.team948.robot.utilities
This package holds our misc. classes such as MathHelper, position tracker,
logger, and so on. 
##How to Use
More on this later.
##Authors
Frank Zhang
Tejas Rangole
Chuwei Guo

