package org.simulation.app;

import java.util.Random;

import org.simulation.manager.SimulationMonitor;
import org.simulation.threads.AllocateClassRoom;
import org.simulation.threads.LecturerRoom;
import org.simulation.threads.StudentRoom;
import org.simulation.threads.VisitorRoom;

public class CollegeManager implements Runnable {

	/**
	 * Random rand variable use full to generate random number.
	 */
	public static Random randomNumberFrom0To4;
	/**
	 * Classroom objects created and named as W201, W202. W101, JS101 variable use
	 * full to create classroom objects.
	 */
	public static AllocateClassRoom W201;
	public static AllocateClassRoom W202;
	public static AllocateClassRoom W101;
	public static AllocateClassRoom JS101;

	/**
	 * Size Mentioned for every thread aspects for classroom, visitors, students and
	 * lecturers
	 */
	public static AllocateClassRoom allocateClassRooms[] = new AllocateClassRoom[4];
	public static VisitorRoom visitorRoomsArray[] = new VisitorRoom[5];
	public static StudentRoom studentRoomsArray[] = new StudentRoom[90];
	public static LecturerRoom lecturerRoomsArray[] = new LecturerRoom[6];

	/**
	 * laodClassrooms method initialize objects with size for classrooms
	 */
	public static void initializeClassrooms() {
		W201 = new AllocateClassRoom(60, "W201", 60);
		W202 = new AllocateClassRoom(60, "W202", 60);
		W101 = new AllocateClassRoom(20, "W101", 20);
		JS101 = new AllocateClassRoom(30, "JS101", 30);
		allocateClassRooms[0] = W101;
		allocateClassRooms[1] = JS101;
		allocateClassRooms[2] = W201;
		allocateClassRooms[3] = W202;
	}

	/**
	 * Initializing and Creating visitors arrays. The nextInt(4) is used to get a
	 * random number between 0(inclusive) and the number (4) passed in this
	 * argument(n), exclusive.
	 */
	public static void initializeVistors() {
		visitorRoomsArray[0] = new VisitorRoom(allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
		visitorRoomsArray[1] = new VisitorRoom(allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
		visitorRoomsArray[2] = new VisitorRoom(allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
		visitorRoomsArray[3] = new VisitorRoom(allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
		visitorRoomsArray[4] = new VisitorRoom(allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
	}

	/**
	 * Initializing and Creating lecturers arrays. The nextInt(4) is used to get a
	 * random number between 0(inclusive) and the number (4) passed in this
	 * argument(n), exclusive.
	 */
	public static void initializeLecturers() {
		lecturerRoomsArray[0] = new LecturerRoom("Osama", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
		lecturerRoomsArray[1] = new LecturerRoom("Barry", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
		lecturerRoomsArray[2] = new LecturerRoom("Faheem", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
		lecturerRoomsArray[3] = new LecturerRoom("Alex", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
		lecturerRoomsArray[4] = new LecturerRoom("Aqeel", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
		lecturerRoomsArray[5] = new LecturerRoom("Waseem", allocateClassRooms[randomNumberFrom0To4.nextInt(4)],
				studentRoomsArray);
	}

	/**
	 * Thread Executions starts from here.
	 */
	public static void threadsInitExecutions() {
		// loading threads for further process.
		randomNumberFrom0To4 = new Random();
		initializeClassrooms();
		initializeVistors();
		initializeLecturers();
		try {
			// Loop where students enter into classroom
			for (int i = 0; i < studentRoomsArray.length; i++) {
				studentRoomsArray[i] = new StudentRoom(i, allocateClassRooms[randomNumberFrom0To4.nextInt(4)]);
				studentRoomsArray[i].start();
			}

			// It will put the current thread on wait until the thread on which it is called
			// is dead. If thread is interrupted then it will throw InterruptedException.
			for (int i = 0; i < studentRoomsArray.length; i++) {
				studentRoomsArray[i].join();
			}

			// Loop where visitors enter into classroom
			for (int z = 0; z < visitorRoomsArray.length; z++) {
				visitorRoomsArray[z].start();
			}

			// Loop where lecturer enter into classroom
			for (int l = 0; l < lecturerRoomsArray.length; l++) {
				lecturerRoomsArray[l].start();
			}
			for (int l = 0; l < lecturerRoomsArray.length; l++) {
				lecturerRoomsArray[l].startLectureRoom();
			}

			// It will put the current thread on wait until the thread on which it is called
			// is dead. If thread is interrupted then it will throw InterruptedException.
			for (int l = 0; l < lecturerRoomsArray.length; l++) {
				lecturerRoomsArray[l].join();
			}

			// this method call execution simulation of threads.
			simulationThreadMonitor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * simulationThreadMonitor method having main functionality of simulation of
	 * threads.
	 */
	public static void simulationThreadMonitor() {
		try {
			// Monitor thread for printing status of classes
			SimulationMonitor simulationMonitor = new SimulationMonitor(allocateClassRooms);
			simulationMonitor.start();
			simulationMonitor.join();
			for (int q = 0; q < lecturerRoomsArray.length; q++) {
				lecturerRoomsArray[q].join();
			}
			for (int s = 0; s < lecturerRoomsArray.length; s++) {
				lecturerRoomsArray[s].leaveFromRoom();
			}
			// Students Thread Release loop.
			for (int t = 0; t < allocateClassRooms.length; t++) {
				if (!allocateClassRooms[t].isLectureRunningRoom) {
					for (int u = 0; u < studentRoomsArray.length; u++) {
						if (!studentRoomsArray[u].isSittingAvailable) {
							studentRoomsArray[u].leaveClassRoom();						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean threadInteruptionFlag = true;

	/**
	 * Flag to end loop upon interrupt stopThread method changes the value of flag
	 * to false which stops execution of threads.
	 */
	public void stopThread() {
		threadInteruptionFlag = false;
	}

	public void run() {
		while (threadInteruptionFlag) {
			try {
				// Calling this method for thread initialization.
				threadsInitExecutions();
				// sleep method pauses execution of thread for every 2 seconds.
				Thread.sleep(2000);
			} catch (Exception e) {
			}
		}
	}

	/**
	 * main method where program starts.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CollegeManager collegeManager = new CollegeManager();
		Thread thread = new Thread(collegeManager);
		thread.start();
		try {
			// System.in.read() reads data from input stream
			System.in.read();
			collegeManager.stopThread();
			thread.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Stopped!");
	}
}
