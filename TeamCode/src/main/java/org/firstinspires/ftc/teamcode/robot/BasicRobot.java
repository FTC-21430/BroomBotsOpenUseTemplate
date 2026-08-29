// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.subsystems.Arm;
import org.firstinspires.ftc.teamcode.robot.subsystems.Claw;
import org.firstinspires.ftc.teamcode.robot.subsystems.MecanumDrive;

/** Concrete robot configuration with a mecanum drivetrain and claw subsystem. */
public class BasicRobot extends Robot{
    /** Mecanum drivetrain subsystem. */
    public MecanumDrive drivetrain;
    /** Claw subsystem. */
    public Claw claw;
    // Pivoting Arm subsystem
    public Arm arm;

    /**
     * Constructs the robot, initializing all subsystems.
     *
     * @param telemetry   the telemetry instance for driver station output
     * @param hardwareMap the hardware map for device bindings
     */
    public BasicRobot(Telemetry telemetry, HardwareMap hardwareMap){
        super(telemetry);
        this.drivetrain = new MecanumDrive(hardwareMap);
        this.claw = new Claw(telemetry, hardwareMap);
        this.arm = new Arm(hardwareMap);
        subsystems = new Subsystem[]{
                drivetrain,
                arm,
                claw,
        };
        // Add any other class-level setup to this constructor
    }

    /**
     *  Updates all subsystems specifically during teleop
     */
    @Override
    public void teleopUpdate(){
        telemetry.addLine("Driver Controlled telop is running!");
        updateSubsystems();
        defaultTelemetry();
    }
    /** Posts telemetry to the driver station we will want to see often. */
    @Override
    public void defaultTelemetry(){
        claw.postTelemetry();
    }
}
