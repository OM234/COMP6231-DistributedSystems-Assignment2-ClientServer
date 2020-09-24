<p>Name: Osman Momoh</p>
<p>ID: 26220150</p>
<p>Course: COMP 6231 - Distributed Systems</p>
<p>Assignment: 2 - Client Server</p>
Notes: 
<p>
This assignment is submitted in two packages, the nonGUI version and the GUI version. 
The nonGUI version is commented, while the GUI version is less commented as it is optional.
</p>
<p>
For the nonGUI version, start the Server class, and then the Client1, Client2, Client3 classes.
Additional Client# classes can be arbitrarily created by simply copying the source code.
</p>
<p>
For the GUI version, the JavaFx library is used. Start ServerGUI (which is essentially identical 
to the Server class in nonGUI package), then start ChatBox, ChatBox1, ChatBox2. Additional
ChatBox# can be arbitrarily created by copying the source code of ChatBox1 or ChatBox2.
</p>
<p>
A chatBox class loads the scene that is visually described by the ChatBoxView.fxml file. Each 
ChatBox window has a controller called ChatBoxController, which is responsible for responding to
user input, and refreshing the screen. Each ChatBoxController also uses a ClientGUI.
</p>
<p>
A ClientGUI is similar to Client in nonGUI package, as it connects to the Server. Another similarity
is the use of 2 threads for sending and receiving messages with the server. The major difference
is that ClientGUI does not print to the screen, but rather communicates with ChatBoxController to refresh the screen.
</p>