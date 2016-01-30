package org.usfirst.frc.team948.robot.utilities;

import org.usfirst.frc.team948.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.hal.AccelerometerJNI;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class AHRSAccelerometer implements Accelerometer, LiveWindowSendable {

	private AHRS ahrs = RobotMap.ahrs;

	public AHRSAccelerometer() {
	}

	@Override
	public double getX() {
		return ahrs.getWorldLinearAccelX();
	}

	@Override
	public double getY() {
		return ahrs.getWorldLinearAccelY();
	}

	@Override
	public double getZ() {
		return ahrs.getWorldLinearAccelZ();
	}

	@Override
	public void setRange(Range range) {
		AccelerometerJNI.setAccelerometerActive(false);

		switch (range) {
		case k2G:
			AccelerometerJNI.setAccelerometerRange(0);
			break;
		case k4G:
			AccelerometerJNI.setAccelerometerRange(1);
			break;
		case k8G:
			AccelerometerJNI.setAccelerometerRange(2);
			break;
		case k16G:
			throw new RuntimeException("16G range not supported (use k2G, k4G or k8G)");
		}
	}

	@Override
	public String getSmartDashboardType() {
		return "3AxisAccelerometer";
	}

	public ITable m_table;

	public void initTable(ITable subtable) {
		m_table = subtable;
		updateTable();
	}

	@Override
	public void startLiveWindowMode() {

	}

	@Override
	public void stopLiveWindowMode() {

	}

	@Override
	public void updateTable() {
		if (m_table != null) {
			m_table.putNumber("X", ahrs.getWorldLinearAccelX());
			m_table.putNumber("Y", ahrs.getWorldLinearAccelY());
			m_table.putNumber("Z", ahrs.getWorldLinearAccelZ());
		}
	}

	@Override
	public ITable getTable() {
		return m_table;
	}

}
