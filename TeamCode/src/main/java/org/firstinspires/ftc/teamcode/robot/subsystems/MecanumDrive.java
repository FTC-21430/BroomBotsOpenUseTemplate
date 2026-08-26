// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

import java.util.ArrayList;

/** Mecanum drive subsystem for holonomic (omnidirectional) robot movement. */
public class MecanumDrive implements Subsystem {
    private boolean disabled = true;
    /** All four wheel motors ordered: front-left, front-right, back-left, back-right. */
    private final ArrayList<DcMotor> allMotors = new ArrayList<>();
    /** Cached power values corresponding to {@link #allMotors}. */
    private final ArrayList<Double> motorPowers = new ArrayList<>();

    /**
     * Initializes and configures all four drive motors from the hardware map.
     *
     * @param hardwareMap the robot's hardware map
     */
    public MecanumDrive(HardwareMap hardwareMap){
        final DcMotor frontLeftMotor;
        final DcMotor frontRightMotor;
        final DcMotor backLeftMotor;
        final DcMotor backRightMotor;
        frontLeftMotor = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotor.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotor.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotor.class, "backRight");

        allMotors.add(frontLeftMotor);
        allMotors.add(frontRightMotor);
        allMotors.add(backLeftMotor);
        allMotors.add(backRightMotor);

        for (DcMotor motor : allMotors){
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }
    /**
     * Calculates and caches motor powers for mecanum drive kinematics.
     *
     * @param forwardRate  forward/backward input [-1, 1]
     * @param sidewaysRate strafe left/right input [-1, 1]
     * @param turnRate     rotation input [-1, 1]
     */
    public void setDrivePower(double forwardRate, double sidewaysRate, double turnRate){
        double frontLeftPower = forwardRate + sidewaysRate + turnRate;
        double frontRightPower = forwardRate - sidewaysRate - turnRate;
        double backLeftPower = forwardRate - sidewaysRate + turnRate;
        double backRightPower = forwardRate + sidewaysRate - turnRate;

        motorPowers.clear();
        motorPowers.add(frontLeftPower);
        motorPowers.add(frontRightPower);
        motorPowers.add(backLeftPower);
        motorPowers.add(backRightPower);
    }
    /** Applies cached motor powers to all drive motors; no-ops if disabled or powers are unset. */
    @Override
    public void update(){
        if (disabled) return; // do not do anything if the subsystem is disabled
        if (motorPowers.size() < 4) return; // Prevent index out of bounds error
        for (int i = 0; i < 4; i++){
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

}
