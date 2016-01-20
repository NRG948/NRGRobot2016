package org.usfirst.frc.team948.robot.utilities;

public class MathHelper {
	/**
	 * Case 1: IF the input is greater than the max value, the max is returned
	 * Case 2: If the input is within the range, the input is returned Case 3:
	 * IF the input is less than the min value, then the min is returned
	 * 
	 * @param input
	 * @param min
	 * @param max
	 * @return
	 */
	public static double clamp(double input, double min, double max) {
		if (input < min) return min;
		if (input > max) return max;
		return input;
	}

	/**
	 * Takes the voltage value from the potentiometer, then converts it into a
	 * value on a scale of 0.0 to 1.0 (basically the percent to the max
	 * potentiometer value / 100)
	 *
	 * @param input
	 * @param min
	 * @param max
	 * @return
	 */
	public static double normalize(double input, double min, double max) {
		double sliderValue = clamp(input, min, max);
		return (sliderValue - min) / (max - min);
	}

	/**
	 * This method takes the return value of the original normalize method, and
	 * then subtracts it from 1, to reverse the scale (useful for a physically
	 * flipped potentiometer orientation)
	 * 
	 * @param input
	 * @param min
	 * @param max
	 * @return
	 */
	public static double reverseNormalize(double input, double min, double max) {
		return 1.0 - normalize(input, min, max);
	}

	public static double normalizeAngle(double angle) {
		double a = angle % 360;
		if (a < 0) {
			a += 360;
		}
		return a;
	}

	/**
	 * Converts gyro heading into a trigonometry angle in degrees.
	 * 
	 * @param heading
	 *            heading from the gyro.
	 * @return trigonometry angle in degrees.
	 */
	public static double headingToDegrees(double heading) {
		return 90.0 - heading;
	}

	/**
	 * Takes in two values and returns which one is max
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double max(double x, double y) {
		if (x > y) {
			return x;
		}
		return y;
	}

	/**
	 * Convert a heading into an equivalent heading which is within 180 degrees
	 * of the gyroHeading.
	 * 
	 * @param heading
	 *            The heading of which we're trying to find an equivalent that
	 *            is close to gyroHeading.
	 * @param gyroHeading
	 *            The current value of the gyro.
	 * @return heading+n*360 where n is chosen such that the result is within
	 *         180 degrees of gyroHeading.
	 */
	public static double nearestEquivalentHeading(double heading,
			double gyroHeading) {
		double deltaAngle = (heading - gyroHeading) % 360;
		if (deltaAngle > 180) {
			deltaAngle -= 360;
		} else if (deltaAngle <= -180) {
			deltaAngle += 360;
		}
		return gyroHeading + deltaAngle;
	}

	public static double angleTowardsXY(double initialX, double initialY,
			double finalX, double finalY) {
		double dX = finalX - initialX;
		double dY = finalY - initialY;
		return 90-Math.toDegrees(Math.atan2(dY, dX));
	}

	public static double roundTo(int nearest, double input) {
		return (Math.round(input * Math.pow(10, nearest)) / Math.pow(10,
				nearest));
	}
}