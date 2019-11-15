
# REMOTE DESKTOP CONTROL

There are two programs:

1) Server - computer which is being controlled. 
Server sends screenshot 10 times per second to client. 
And receives command to control windows and elements. 

Commands for now are:
- mouseClick X Y
- keyPressed X Y

I use 'Robot' class to create screenshots and emulate user behaviour.

2) Client - computer which controls another computer. 
Client initilizes JFrame (GUI) and renders screenshot received from server.
Client listens events, create command and sends it to server.

F.e. user pressed Enter -> event with key code called -> 
-> client sends command 'keyPressed 10'


There are two socket connections between client and server:
1) Exchanging images - ScreenSender (on server) and ScreenReceiver (on client) classes
2) Exchanging commands - Server and Client classes


There are some problems:
- click is not accurate by coordinates. if you want to open folder, but it doesn't - try to click few pixels higher and left
- specific combinations execute on client computer (f.e. Alt+F4 -  will close program)
- Drag and Drop is not implemented

## HOST and PORT are hard coded in project!!! You have to change it for your needs!

This is very simple implementation of RDC, but it is very easy to improve it.
