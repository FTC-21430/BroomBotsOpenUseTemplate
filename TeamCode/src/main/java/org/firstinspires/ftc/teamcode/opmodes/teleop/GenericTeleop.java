// Copyright (c) 2026 Tobin Rumsey, FTC 21430, BroomfieldSTEM. All rights reserved.
package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robot.BasicRobot;
import org.firstinspires.ftc.teamcode.robot.subsystems.Claw;

@TeleOp(name = "GenericTeleop", group = "ExampleTeleop")
public class GenericTeleop extends OpMode {
    private BasicRobot robot;
    @Override
    public void init(){
        robot = new BasicRobot(telemetry, hardwareMap);
    }
    @Override
    public void loop(){
        if (gamepad1.a){
            robot.claw.setPos(Claw.ClawPosition.CLOSED);
        }else{
            robot.claw.setPos(Claw.ClawPosition.OPEN);
        }
        robot.teleopUpdate();
        // In case the robot is about to do something bad
        if (gamepad1.shareWasPressed()){
            robot.emergencyStop();
        }
    }

}
