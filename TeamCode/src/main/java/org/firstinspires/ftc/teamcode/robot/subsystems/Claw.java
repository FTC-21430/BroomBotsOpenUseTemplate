// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.resources.ServoPlus;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

public class Claw implements Subsystem {
    // Servo plus converts normal servo range (0-1) to degrees based on the range of motion of your desired output.
    private final ServoPlus clawServo;
    private final Telemetry telemetry;
    private boolean disabled = true;
    public enum ClawPosition {
            OPEN,
            CLOSED
    }
    private ClawPosition currentPosition = ClawPosition.OPEN;
    public Claw(Telemetry telemetry, HardwareMap hardwareMap){
        this.telemetry = telemetry;
        clawServo = new ServoPlus(hardwareMap.get(Servo.class, "clawServo"), 180);
    }
    public void setPos(ClawPosition position){
        currentPosition = position;
    }
    @Override
    public void update(){
        if (disabled) return; // do nothing
        switch (currentPosition){
            case CLOSED:
                clawServo.setServoPos(0); // units are in degrees
                break;
            case OPEN:
                clawServo.setServoPos(90); // 90 degrees of rotation for the thing we want
        }
    }
    @Override
    public void setDisabled(boolean disabled){
        this.disabled = disabled;
        clawServo.setServoPos(90); // Set servo to a safe position
    }
    public void postTelemetry(){
        telemetry.addData("Current Claw Position", currentPosition.toString());
    }
}
