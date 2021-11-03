package org.simulation.threads;
/**
 * Visitor class is a thread class which perform visitor siting
 * functionality.
 */
public class VisitorRoom extends Thread {
	public boolean isSittingAllocate = false;
	public AllocateClassRoom allocateClassRoom;

	// Constructor
	public VisitorRoom(AllocateClassRoom obj) {
		if (obj.getStudentVisitorSemaphore().availablePermits() > 0) {
			this.allocateClassRoom = obj;
		}
	}

	// Enter class function
	public void enterIntoClassRoom() {
		// Checks whether classroom is full. If not full visitor can enter
		if (!this.allocateClassRoom.checkClassFull()) {
			this.allocateClassRoom.filledRoomVisitor++;
			sitDownInClassRoom();
		}
	}
	// Sit_down function
	public void sitDownInClassRoom() {
		try {
			this.isSittingAllocate = true;
			allocateClassRoom.getStudentVisitorSemaphore().acquire();// Semaphore acquired
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Leave class function
	public void leaveClassRoom() {
		allocateClassRoom.getStudentVisitorSemaphore().release();// Semaphore released
	}

	// Override run method of Thread class
	@Override
	public void run() {
		enterIntoClassRoom();
	}
}
