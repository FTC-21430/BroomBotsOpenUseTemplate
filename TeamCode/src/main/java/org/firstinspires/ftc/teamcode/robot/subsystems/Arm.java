package org.firstinspires.ftc.teamcode.robot.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.resources.PIDFController;
import org.firstinspires.ftc.teamcode.robot.Subsystem;

public class Arm implements Subsystem {
    // Tune these values for your team's specific robot or subsystem (These values were made up, not tested on any hardware)
    private double pConstant = 1;
    private double iConstant = 0.001;
    private double dConstant = 0.01;

    private double fConstantAtAngleZero = 0.2; // tune for the motor value at which the arm will hold at straight out (gravity force at max, check the direction


    private final PIDFController primaryController;
    private final DcMotorEx armMotor;
    private final double minROM = -5; // degrees, straight is forward (aligned with the forward axis when looking at robot from top)
    private final double maxROM = 175; // degrees
    private final double initialPosition = 90; //degrees, Robot will always start with the arm sticking straight up using this value
    private boolean disabled = true;

    public Arm(HardwareMap hardwareMap){


        primaryController = new PIDFController(pConstant, iConstant, dConstant, 0, new ElapsedTime()); // using a generic FIRST SDK runtime instance so that time resets do not impact controller output, F constant at zero
        armMotor = hardwareMap.get(DcMotorEx.class, "armMotor");

        setDisabled(false);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // arm init in specific position
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


    }
    @Override
    public void update(){
        if (disabled) return;
        updateFConstant();
        primaryController.update(getArmRotation());
    }
    @Override
    public void setDisabled(boolean disabled){
        this.disabled = disabled;
        if (disabled){
            armMotor.setPower(0);
            armMotor.setMotorDisable();
        }else{
            armMotor.setMotorEnable();
        }
    }
    public void postTelemetry(Telemetry telemetry){
        telemetry.addLine("----- Arm ----");
        telemetry.addData("Arm Angle", getArmRotation());
    }
    public double getArmRotation(){
        return ticksToArmDegrees(armMotor.getCurrentPosition());
    }
    public void setTargetAngle(double degrees){
        // ensure target is within angle of motion (AOM)
        degrees = Math.max(degrees, minROM); // use the lowest part of your range
        degrees = Math.min(degrees, maxROM); // use the highest part of your range
        primaryController.setTarget(degrees);
    }
    private double ticksToArmDegrees(int encoderTicks){
        double gearRatio = 1.0/2.0; // two rotations of the motor shaft is one full rotation of the arm.
        double motorShaftRatio = (double)315.0/6000; //for a gobilda 315 rpm motor, which has a 6000 rpm base motor. This should be in the spec sheet for the motor you are using
        double encoderTicksPerRetation = 28;
        double result = encoderTicks * gearRatio * motorShaftRatio;
        result /= encoderTicksPerRetation;
        return result;
    }
    private void updateFConstant(){
        double fConstant = fConstantAtAngleZero * Math.cos(Math.toRadians(getArmRotation()));
        primaryController.updatePIDFConstants(pConstant, iConstant, dConstant, fConstant);
    }

}
