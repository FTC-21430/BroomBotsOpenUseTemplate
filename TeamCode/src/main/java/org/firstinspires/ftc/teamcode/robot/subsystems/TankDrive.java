// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

import java.util.ArrayList;

public class TankDrive implements Subsystem {
    private boolean disabled = true;
    private final ArrayList<DcMotor> allMotors = new ArrayList<>(); // both wheel motors in one array, should be in order left then right;
    private final ArrayList<Double> motorPowers = new ArrayList<>();
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
    public void setDrivePower(double forwardRate, double turnRate){
        double LeftPower = forwardRate + turnRate;
        double rightPower = forwardRate - turnRate;

        motorPowers.clear();
        motorPowers.add(LeftPower);
        motorPowers.add(rightPower);
    }
    @Override
    public void update(){
        if (disabled) return; // do not do anything if the subsystem is disabled
        if (motorPowers.size() < 2) return; // Prevent index out of bounds error
        for (int i = 0; i < allMotors.size(); i++){
            allMotors.get(i).setPower(motorPowers.get(i));
        }
    }
    @Override
    public void setDisabled(boolean disable){
        disabled = true;
        for (DcMotor motor : allMotors){
            motor.setPower(0);
        }
    }
}
