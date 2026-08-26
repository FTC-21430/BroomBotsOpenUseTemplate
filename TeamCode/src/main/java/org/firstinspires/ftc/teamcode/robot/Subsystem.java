// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.robot;

/**
 * Subsystem interface, which is implemented by all subsystems in the robot.
 * Each subsystem must implement the update() method,
 * which is called every loop to update the subsystem's state, and the setDisabled(boolean disable) method,
 * which is called when the robot is disabled to stop all motors and reset any state.
 */
public interface Subsystem {
    // Standard update method that all subsystems should implement
    public void update();
    // Method to disable the subsystem, stopping all motors and resetting any state, ensuring that it will immediately stop all motors and reset any state when called
    public void setDisabled(boolean disable);
}
