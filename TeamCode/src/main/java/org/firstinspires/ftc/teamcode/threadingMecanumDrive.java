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


@TeleOp(name = "Threading MecanumDrive", group = "Linear Opmode")
public class threadingMecanumDrive extends LinearOpMode {
    public DcMotor FR;
    public DcMotor FL;
    public DcMotor BR;
    public DcMotor BL;
    public DcMotor RLIFT;
    public DcMotor LLIFT;
    public DcMotor bottomRotate;
    public DcMotor topRotate;



    //2 servos used to grab pixels
    public Servo wrist;
    public Servo angleServo;
    //perpendicular servo - goes up and down
    public CRServo intakeServo1;
    public CRServo intakeServo2;
    private int targetPosition = 0; // Initialize target position
    private int topRotateTargetPosition = 0; // Target position for topRotate
    private int bottomRotateTargetPosition = 0; // Target position for topRotate


    MecanumDrive drive;



    @Override
    public void runOpMode(){
        FR = hardwareMap.dcMotor.get("rightFront");
        FL = hardwareMap.dcMotor.get("leftFront");
        BR = hardwareMap.dcMotor.get("rightBack");
        BL = hardwareMap.dcMotor.get("leftBack");
        RLIFT = hardwareMap.dcMotor.get("RLIFT");
        LLIFT = hardwareMap.dcMotor.get("LLIFT");
        bottomRotate = hardwareMap.dcMotor.get("bottomRotate");
        topRotate = hardwareMap.dcMotor.get("topRotate");

        intakeServo1 = hardwareMap.crservo.get("intakeServo1");
        intakeServo2 = hardwareMap.crservo.get("intakeServo2");
        wrist = hardwareMap.servo.get("wrist");
        angleServo = hardwareMap.servo.get("angleServo");

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bottomRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RLIFT.setTargetPosition(0);
        LLIFT.setTargetPosition(0);
        bottomRotate.setTargetPosition(0);
        topRotate.setTargetPosition(0);

        LLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        bottomRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        topRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LLIFT.setDirection(DcMotor.Direction.REVERSE);
        topRotate.setDirection(DcMotor.Direction.REVERSE);

        RLIFT.setPower(0.5);
        LLIFT.setPower(0.5);
        bottomRotate.setPower(1);
        topRotate.setPower(1);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(this::mecanumDrive);
        executorService.submit(this::servoTasks);




        waitForStart();

        while (opModeIsActive()) {
            Drive();
            armTesting();
            lifting();
            bottomArmTesting();
            telemetry.addData("LLIFT Encoder", LLIFT.getCurrentPosition());
            telemetry.addData("RLIFT Encoder", RLIFT.getCurrentPosition());
            telemetry.addData("Bottom Rotate Encoder", bottomRotate.getCurrentPosition());
            telemetry.addData("top Rotate Encoder", topRotate.getCurrentPosition());

            telemetry.addData("TopRotateTargetPosition", topRotateTargetPosition);
            telemetry.addData("bottomRotateTargetPosition", bottomRotateTargetPosition);

            telemetry.update();
        }
        executorService.shutdownNow(); // Shut down the ExecutorService

    }


    private void mecanumDrive() {
        while (!Thread.interrupted()) {
            Drive();
        }
    }
    private void servoTasks() {
        while (!Thread.interrupted()) {
            lifting();
            armTesting();
            bottomArmTesting();
        }
    }



    public void Drive() {
        drive.setDrivePowers(new PoseVelocity2d(
                new Vector2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x
                ),
                -gamepad1.right_stick_x
        ));

        drive.updatePoseEstimate();
    }
    public void lifting() {
        if (gamepad2.dpad_up) {
            targetPosition = Math.min(targetPosition + 25, 1250); // Increment by 50, capped at 1250
        }
        if (gamepad2.dpad_down) {
            targetPosition = Math.max(targetPosition - 25, 0); // Decrement by 50, floored at 0
        }
        RLIFT.setTargetPosition(targetPosition);
        LLIFT.setTargetPosition(targetPosition);

    }
    public void armTesting(){
        if (gamepad1.y) {
            if (topRotateTargetPosition < 1000) {
                topRotateTargetPosition = topRotateTargetPosition + 5;
                bottomRotateTargetPosition = bottomRotateTargetPosition + 5;
            }
        }

        // Decrease the target position for topRotate
        if (gamepad1.a) {
            if (topRotateTargetPosition > 0) {
                topRotateTargetPosition = topRotateTargetPosition - 5;
                bottomRotateTargetPosition = bottomRotateTargetPosition - 5;
            }

        }

        if (gamepad1.x) {
            bottomRotateTargetPosition = bottomRotateTargetPosition - 5;
        }
        if (gamepad1.b) {
            if (bottomRotateTargetPosition < topRotateTargetPosition)
                bottomRotateTargetPosition = bottomRotateTargetPosition + 5;
        }

        topRotate.setTargetPosition(topRotateTargetPosition);
        bottomRotate.setTargetPosition(bottomRotateTargetPosition);


    }
    public void bottomArmTesting() {
        /*
        if (gamepad1.x) {
            bottomRotateTargetPosition = Math.min(bottomRotateTargetPosition + 1, 1000); // Increment by 25, capped at 1000
        }
        if (gamepad1.b) {
            bottomRotateTargetPosition = Math.min(bottomRotateTargetPosition - 1, topRotateTargetPosition + 25); // Increment by 25, capped at 1000
        }
        bottomRotate.setTargetPosition(bottomRotateTargetPosition);

         */


    }





}