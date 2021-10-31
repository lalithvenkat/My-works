# Information Retrieval using ProbFuse

The process of combining the results of multiple separate searches on a document collection into a single output result set is known as data fusion. It has previously been demonstrated that approach can significantly enhance retrieval effectiveness over individual outcomes.

This work introduces probFuse, a probabilistic data fusion technique. ProbFuse posits that the separate input systems' performance on a number of training queries predicts their future performance. The merged result set is based on relevance probabilities obtained throughout the training procedure.

The information retreival system based on ProbFuse, will take a set of Historical data of searches and a set of live results and fuse them together to produce a new ranked list of results.

The File name of source code is ProbFuse.java

## List of the System Requirements:
 A PC with windows operating system(prefered) with 64bit

## Language and version of the program:
 The source code is written in Java programming language
 Java version:11
 Amazon corretto version 11 is installed 
 Amazon corretto is a open JDK provided by amazon. Any java with version 11 or above is supported for running this program.(amazon corretto version 11 is prefered).

## Instructions for running this program:
 After installing the Java version 11 or above in the machine(For easier way just go to amazon corretto website, Download and isntall corretto version 11) 
  
 This program can be run without IDE
 
 First go to the command prompt and check for java version with the following command: javac -version
 
 Download the program file and other historical and liveresults files and store in the same folder.
 
 In the Terminal change the directory to the path where the program files are stored with command CD followed by the file path.
 
 Now compile the Program with following command:
 javac ProbFuse.java
 
 Now the run the Program with Following command:
 Java ProbFuse

## Excecution of Program
* After running the program, It asks for the user input. The user input is the name of the historical results file with extension.
* Here the Program reads the input file which is in .csv format
* Now the program again asks for the user input for the number of segments: The user should provided a numerical value(eg: 4 segements to divide the historical file) 
* Again, the program asks for input file: The user should provide the name of the Livereults file with extension
* It then again asks for the user input for choosing the IR engines to Fuse: The input should be Among A B C D.
* The user should select different IR engines (eg: AB,ABC). If the user selects same IR engine eg:AA, BB. It returns an error saying invalid IR engines selected.
* The program then gives an output: The output will be fusion of IR engines with Probfuse method: It contains the rank and Id of the document and score of the document in ascending order.
* The output will also be stored in file with the same .csv format.


 







