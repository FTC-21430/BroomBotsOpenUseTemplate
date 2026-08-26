// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

import java.util.ArrayList;

/** Tank drive subsystem for differential tank drive robot movement. */
public class TankDrive implements Subsystem {
    private boolean disabled = true;
    /** Both drive motors ordered: left, right. */
    private final ArrayList<DcMotor> allMotors = new ArrayList<>();
    /** Cached power values corresponding to {@link #allMotors}. */
    private final ArrayList<Double> motorPowers = new ArrayList<>();

    /**
     * Initializes and configures both drive motors from the hardware map.
     *
     * @param hardwareMap the robot's hardware map
     */
    public TankDrive(HardwareMap hardwareMap){
        final DcMotor leftMotor;
        final DcMotor rightMotor;
        leftMotor = hardwareMap.get(DcMotor.class, "left");
        rightMotor = hardwareMap.get(DcMotor.class, "right");

        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        allMotors.add(leftMotor);
        allMotors.add(rightMotor);

        for (DcMotor motor : allMotors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    /**
     * Calculates and caches motor powers for tank drive kinematics.
     *
     * @param forwardRate forward/backward input [-1, 1]
     * @param turnRate    rotation input [-1, 1]
     */
    public void setDrivePower(double forwardRate, double turnRate){
        double LeftPower = forwardRate + turnRate;
        double rightPower = forwardRate - turnRate;

        motorPowers.clear();
        motorPowers.add(LeftPower);
        motorPowers.add(rightPower);
    }
    /** Applies cached motor powers to all drive motors; no-ops if disabled or powers are unset. */
    @Override
    public void update(){
        if (disabled) return; // do not do anything if the subsystem is disabled
        if (motorPowers.size() < 2) return; // Prevent index out of bounds error
        for (int i = 0; i < allMotors.size(); i++){
            allMotors.get(i).setPower(motorPowers.get(i));
        }
    }
    /**
     * Enables or disables the drivetrain. Zeroes all motors when disabling.
     *
     * @param disable {@code true} to disable the subsystem
     */
    @Override
    public void setDisabled(boolean disable){
        disabled = true;
        for (DcMotor motor : allMotors){
            motor.setPower(0);
        }
    }
    @Override
    public void postTelemetry(Telemetry telemetry){
        for (DcMotor motor:allMotors){
            telemetry.addData(String.valueOf(motor.getPortNumber()), motor.getPower());
        }
    }
}
