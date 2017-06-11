package trash.oldschool.engine;

public interface GameTimer {

	/** Measurement in seconds. */
	double elapsedTime();

	/** Measurement in nanoseconds. */
	long getNanoTimeOnLastCall();

	/** Measurement in nanoseconds. */
	long getNanoTimeNow();

}
