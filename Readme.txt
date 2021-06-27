The File name of source code is ProbFuse.java

> List of the System Requirements:
 A PC with windows operating system(prefered) with 64bit

> Language and version of the program:
 The source code is written in Java programming language
 Java version:11
 Amazon corretto version 11 is installed 
 Amazon corretto is a open JDK provided by amazon. Any java with version 11 or above is supported for running this program.(amazon corretto version 11 is prefered).

> Instructions for running this program:
 After installing the Java version 11 or above in the machine(For easier way just go to amazon corretto website, Download and isntall corretto version 11) 
 This program can be run without IDE
 First go to the command prompt and check for java version with the following command: javac -version
 Download the program file and other historical and liveresults files and store in the same folder.
 In the Terminal change the directory to the path where the program files are stored with command CD followed by the file path.
 Now compile the Program with following command:
 javac ProbFuse.java
 Now the run the Program with Following command:
 Java ProbFuse

> Excecution of Program
1) After running the program, It asks for the user input. The user input is the name of the historical results file with extension.
2) Here the Program reads the input file which is in .csv format
3) Now the program again asks for the user input for the number of segments: The user should provided a numerical value(eg: 4 segements to divide the historical file) 
4) Again, the program asks for input file: The user should provide the name of the Livereults file with extension
5) It then again asks for the user input for choosing the IR engines to Fuse: The input should be Among A B C D.
6) The user should select different IR engines (eg: AB,ABC). If the user selects same IR engine eg:AA, BB. It returns an error saying invalid IR engines selected.
7) The program then gives an output: The output will be fusion of IR engines with Probfuse method: It contains the rank and Id of the document and score of the document in ascending order.
   The output will also be stored in file with the same .csv format.


 







