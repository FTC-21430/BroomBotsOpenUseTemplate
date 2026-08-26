// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

import java.util.ArrayList;

public class MecanumDrive implements Subsystem {
    private boolean disabled = true;
    private final ArrayList<DcMotor> allMotors = new ArrayList<>(); // all four wheel motors in one array, should be in order fl, fr, bl, br;
    private final ArrayList<Double> motorPowers = new ArrayList<>();
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
    @Override
    public void update(){
        if (disabled) return; // do not do anything if the subsystem is disabled
        if (motorPowers.size() < 4) return; // Prevent index out of bounds error
        for (int i = 0; i < 4; i++){
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
