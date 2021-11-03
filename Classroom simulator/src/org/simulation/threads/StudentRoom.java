package org.simulation.threads;

/**
 * Student class is a thread class which perform student siting functionality.
 */
public class StudentRoom extends Thread {

	public boolean isSittingAvailable = false;
	public AllocateClassRoom allocationClassRoom;
	public int rollNumber;

	// Constructor
	public StudentRoom(int rollNumber, AllocateClassRoom obj) {
		this.rollNumber = rollNumber;
		if (obj.getStudentVisitorSemaphore().availablePermits() > 0) {
			this.allocationClassRoom = obj;
		}
	}

	public boolean getIsSitting() {
		return this.isSittingAvailable;
	}

	// Enter class function
	public void enterIntoClass() {
		// Checks whether classroom is full. If not full student can enter
		if (this.allocationClassRoom != null) {
			if (!this.allocationClassRoom.checkClassFull()) {
				sitDownInClass();
			}
		}
	}

	// Visitors and students sit down function
	public void sitDownInClass() {
		try {
			this.isSittingAvailable = true;
			this.allocationClassRoom.getStudentVisitorSemaphore().tryAcquire();
			// Increment filled variable when student sits
			this.allocationClassRoom.roomSeatsFilled++;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Leave class function
	public void leaveClassRoom() {
		// Checks if lecture is over, if over student can leave
		if (allocationClassRoom != null && !allocationClassRoom.isLectureRunningRoom && allocationClassRoom.lecturerName != null
				&& allocationClassRoom.lecturerName.equals("")) {
			isSittingAvailable = false;
			// Semaphore released. ready for next class occupancy.
			allocationClassRoom.getStudentVisitorSemaphore().release();
		}
	}

	// Override run method of Thread class
	@Override
	public void run() {
		enterIntoClass();
	}
}
