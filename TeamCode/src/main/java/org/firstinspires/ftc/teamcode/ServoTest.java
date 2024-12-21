package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import java.util.concurrent.*;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;


@TeleOp(name = "ServoTest", group = "Linear Opmode")
public class ServoTest extends LinearOpMode {

    public CRServo intakeServo1;
    public CRServo intakeServo2;
    public Servo wrist;

    public int cnt = 0;


    MecanumDrive drive;



    @Override
    public void runOpMode(){
        intakeServo1 = hardwareMap.crservo.get("intakeServo1");
        intakeServo2 = hardwareMap.crservo.get("intakeServo2");
        wrist = hardwareMap.servo.get("wrist");
        waitForStart();

        while (opModeIsActive()) {
            ServoMovement();
            telemetry.addData("Gamepad2 Y", gamepad2.y);
            telemetry.addData("Gamepad2 X", gamepad2.x);
            telemetry.addData("Servo Power", intakeServo1.getPower());
            telemetry.addData("cnt", cnt);

            telemetry.update();

        }
    }
    public void ServoMovement() {
        if (gamepad2.left_trigger > 0.9) { //spit out
            cnt += 1;
            intakeServo1.setPower(0.5);
            intakeServo2.setPower(-0.5);

        }
        else if (gamepad2.right_trigger > 0.9) { //intake
            cnt += 1;
            intakeServo1.setPower(-0.5);
            intakeServo2.setPower(0.5);
        }
        else {
            intakeServo1.setPower(0);
            intakeServo2.setPower(0);
        }
        double wristPosition = wrist.getPosition();

        if (gamepad2.right_bumper) {
            wristPosition += 0.01;
            sleep(5);
        }

        else if (gamepad2.left_bumper) {
            wristPosition -= 0.01;
            sleep(5);
        }

        wristPosition = Range.clip(wristPosition, 0, 1);
        wrist.setPosition(wristPosition);


    }





}