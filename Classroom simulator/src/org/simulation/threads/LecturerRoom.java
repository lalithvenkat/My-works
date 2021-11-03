package org.simulation.threads;

/**
 * Lecturer class is a thread class which perform lecturer siting functionality.
 */
public class LecturerRoom extends Thread {

	public boolean isLectureRunningRoom = false;
	public String lecturerName;
	public AllocateClassRoom classRoom;
	public StudentRoom array[];

	/**
	 * Constructor with following parameter
	 * 
	 * @param lecturerName
	 * @param obj
	 * @param array
	 */
	public LecturerRoom(String lecturerName, AllocateClassRoom obj, StudentRoom array[]) {
		this.lecturerName = lecturerName;
		if (obj.getStudentVisitorSemaphore().availablePermits() > 0) {
			this.classRoom = obj;
		}
		this.array = array;
	}

	public boolean getIsLectureRunning() {
		return this.isLectureRunningRoom;
	}

	/**
	 * Enter class function
	 */
	public void enterIntoRoom() {
		// Checks whether classroom is full. If not full lecturer can enter
		if (classRoom.getLecturerSemaphore().availablePermits() > 0) {
			try {
				this.classRoom.lecturerName = lecturerName;
				// Semaphore acquired
				classRoom.getLecturerSemaphore().acquire();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Start lecture function
	 */
	public void startLectureRoom() {
		int count = 0;
		// Loop to count the number of students sitting in class
		for (StudentRoom stu : array) {
			if (stu.allocationClassRoom != null && classRoom != null) {
				if (stu.allocationClassRoom.classRoomName == classRoom.classRoomName) {
					if (stu.isSittingAvailable) {
						count++;
					}
				}
			}
		}

		// Lecture starts if all students are sitting
		if (classRoom != null) {
			if (classRoom.roomSeatsFilled == count) {
				this.classRoom.isLectureRunningRoom = true;
				isLectureRunningRoom = true;
			}
		}
	}

	/**
	 * End lecture function
	 */
	public void leaveFromRoom() {
		// Check if lecture is running, if running end it and release semaphore
		if (classRoom.isLectureRunningRoom) {
			classRoom.isLectureRunningRoom = false;
			classRoom.lecturerName = "";
			// Semaphore released
			classRoom.getLecturerSemaphore().release();
			System.out.println(lecturerName + "'s lecture over! " );
		}
	}

	// Override run method of Thread class
	@Override
	public void run() {
		enterIntoRoom();
	}
}
