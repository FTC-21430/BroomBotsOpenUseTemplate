// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.BasicRobot;
import org.firstinspires.ftc.teamcode.robot.subsystems.Claw;

/** Example TeleOp opmode demonstrating basic robot control with a claw and emergency stop. */
@TeleOp(name = "GenericTeleop", group = "ExampleTeleop")
public class GenericTeleop extends OpMode {
    private BasicRobot robot;

    /** Instantiates the robot with telemetry and hardware bindings. */
    @Override
    public void init(){
        robot = new BasicRobot(telemetry, hardwareMap);
    }

    /**
     * Runs every loop tick: controls the claw via gamepad A button,
     * updates all subsystems, and checks for an emergency stop.
     */
    @Override
    public void loop(){
        // In case the robot is about to do something bad
        if (gamepad1.shareWasPressed()){
            robot.emergencyStop();
        }
        // Swing arm control.
        if (gamepad1.dpadDownWasPressed()){
            robot.arm.setTargetAngle(0);
        }
        if (gamepad1.dpadUpWasPressed()){
            robot.arm.setTargetAngle(80);
        }
        if (gamepad1.a){
            robot.claw.setPos(Claw.ClawPosition.CLOSED);
        }else{
            robot.claw.setPos(Claw.ClawPosition.OPEN);
        }
        robot.teleopUpdate();

    }

}
