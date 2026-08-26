// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.

package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The Robot class is the base class for all robots in the BroomBots framework.
 * It contains the basic structure and functionality that all robots should have,
 * including subsystems, actions, and telemetry.
 */
public abstract class Robot {
    // The telemetry object for sending data to the driver station (debug information)
    public Telemetry telemetry;
    // An array of subsystems that make up the robot
    public Subsystem[] subsystems;
    // The runtime timer for tracking the duration of the match
    public ElapsedTime runtime;
    // The alliance that the robot is currently on (RED or BLUE)
    Alliance alliance = Alliance.RED;
    /**
     * An Enum for the two different alliances that the robot could be a part of
     */
    public enum Alliance {
        RED, BLUE
    }

    /**
     * Initializes the robot.
     * This method should be overridden by subclasses to provide specific initialization logic for the robot.
     * @param telemetry the object used to post new information to the driver station screen
     */
    public Robot(Telemetry telemetry){
        this.telemetry = telemetry;
        // When overriding the initialize method, you would pass this specific hardwareMap instance to the subsystem you are initializing.
        // EX: claw = new Claw(telemetry, hardwareMap, ...);
    }

    /**
     * Resets game timer
     */
    public void startMatch(){
        runtime.reset();
    }

    /**
     * @return the alliance the robot has been set to
     */
    public Alliance getAlliance(){
        return this.alliance;
    }

    /**
     * Sets the alliance for the robot
     * @param alliance alliance
     */
    public void setAlliance(Alliance alliance){
        this.alliance = alliance;
    }

    /**
     * Updates the robot during the autonomous period.
     * This method should be overridden by subclasses to provide specific autonomous logic for the robot.
     */
    public void autonomousUpdate(){
        telemetry.addLine("Teleop In progress, Override the teleopUpdate method in robot to change something!");
        telemetry.update();
    }
    /**
     * Updates the robot during the teleop period.
     * This method should be overridden by subclasses to provide specific teleop logic for the robot.
     */
    public void teleopUpdate(){
        telemetry.addLine("Autonomous In progress, Override the teleopUpdate method in robot to change something!");
        telemetry.update();
    }

    /**
     * The basic telemetry that you would want whenever working with your robot,
     * such as information that would help you fix your robot if a problem occurs at an event.
     */
    public void defaultTelemetry(){
        telemetry.addLine("Replace this line with any new data that you should have, ie: Robot position!");
    }

    /**
     * Stops all subsystems and displays an emergency stop message on the telemetry.
     * Ensure that all subsystems have their disabled functions implemented properly.
     */
    public void emergencyStop(){
        for (Subsystem subsystem : subsystems){
            subsystem.setDisabled(true);
        }
        telemetry.addLine("--ROBOT EMERGENCY STOPPED, RE INIT TO CONTINUE--");
        telemetry.update();

    }
    /**
     * Returns the current game time in seconds since the match started.
     * @return game time
     */
    public double getGameTime(){
        return runtime.seconds();
    }
    /**
     * Returns all subsystems of the robot.
     * @return array of subsystems
     */
    public Subsystem[] getAllSubsystems(){
        return subsystems;
    }
    public void updateSubsystems(){
        for (Subsystem system : subsystems){
            system.update();
        }
    }
}
