// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.subsystems.Claw;
import org.firstinspires.ftc.teamcode.robot.subsystems.MecanumDrive;

public class BasicRobot extends Robot{
    // Robot subsystems
    public MecanumDrive drivetrain;
    public Claw claw;

    public BasicRobot(Telemetry telemetry, HardwareMap hardwareMap){
        super(telemetry);
        this.drivetrain = new MecanumDrive(hardwareMap);
        this.claw = new Claw(telemetry, hardwareMap);
        subsystems = new Subsystem[]{
                drivetrain,
                claw
        };
        // add any other class setup level to this constructor
    }
    @Override
    public void teleopUpdate(){
        telemetry.addLine("Driver Controlled telop is running!");
        updateSubsystems();
        defaultTelemetry();
    }
    @Override
    public void defaultTelemetry(){
        claw.postTelemetry();
    }
}
