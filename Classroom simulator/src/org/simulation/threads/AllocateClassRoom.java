package org.simulation.threads;

import java.util.concurrent.Semaphore;

/**
 * In this class we are declaring capacity of threads execution by using
 * semaphores.
 */
public class AllocateClassRoom {
	public String classRoomName;
	public Semaphore studentVisitorSemaphore;
	public int roomSeatingCapacity;
	public int roomSeatsFilled;
	// Allowing only one lecturer for a class by using semaphores.
	public Semaphore lecturerRoomSemaphore = new Semaphore(1);
	public int filledRoomVisitor;
	public String lecturerName;
	public boolean isLectureRunningRoom;

	/**
	 * Classroom constructor takes input for class name and thread execution
	 * capacity and seating capacity.
	 * 
	 * @param permit
	 * @param className
	 * @param capacity
	 */
	public AllocateClassRoom(int permit, String className, int capacity) {
		this.studentVisitorSemaphore = new Semaphore(permit);
		this.classRoomName = className;
		this.roomSeatingCapacity = capacity;
	}

	/**
	 * checkClassFull method checks whether class seating capacity is full or not.
	 */
	public boolean checkClassFull() {
		if (roomSeatingCapacity == roomSeatsFilled) {
			return true;
		}
		return false;
	}

	public String getClassName() {
		return this.classRoomName;
	}

	public Semaphore getLecturerSemaphore() {
		return this.lecturerRoomSemaphore;
	}

	public Semaphore getStudentVisitorSemaphore() {
		return this.studentVisitorSemaphore;
	}
}