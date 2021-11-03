package org.simulation.manager;

import org.simulation.threads.AllocateClassRoom;

/**
 * This class is used to display monitoring system of a class
 */
public class SimulationMonitor extends Thread {
	// classroom array declaration.
	public AllocateClassRoom allocateClassRoom[];

	public SimulationMonitor(AllocateClassRoom allocateClassRoom[]) {
		this.allocateClassRoom = allocateClassRoom;
	}

	/**
	 * This method is used run by thread class and executes and prints simulation
	 * matrix in console.
	 */
	@Override
	public void run() {
		System.out.println();
		System.out.println("=======================================================================");
		System.out.println("Classroom\tLecturer\tInSession\tStudents\tVisitor");
		System.out.println("=======================================================================");


		for (int i = 0; i < allocateClassRoom.length; i++) {
			System.out.print(allocateClassRoom[i].classRoomName);
			if (allocateClassRoom[i].lecturerName != null) {
				System.out.print("\t\t" + allocateClassRoom[i].lecturerName);
			} else {
				System.out.print("\t\t");
			}
			System.out.print("\t\t" + allocateClassRoom[i].isLectureRunningRoom + "\t\t");
			if (allocateClassRoom[i].lecturerName != null) {
				System.out.print(allocateClassRoom[i].roomSeatsFilled);
			}
			if (allocateClassRoom[i].lecturerName != null) {
				System.out.print("\t\t" + allocateClassRoom[i].filledRoomVisitor);
			}
			System.out.println("\n");
		}
	}
}