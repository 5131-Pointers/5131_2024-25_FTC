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
    public DcMotor topRotate;


    //2 servos used to grab pixels
    public Servo wrist;
    public Servo angleServo;
    //perpendicular servo - goes up and down
    public CRServo intakeServo1;
    public CRServo intakeServo2;
    private int targetPosition = 0; // Initialize target position
    private int topRotateTargetPosition = 0; // Target position for topRotate

    double wristPosition = 0.6;
    double anglePosition = 0.65;
    MecanumDrive drive;


    @Override
    public void runOpMode() {
        FR = hardwareMap.dcMotor.get("rightFront");
        FL = hardwareMap.dcMotor.get("leftFront");
        BR = hardwareMap.dcMotor.get("rightBack");
        BL = hardwareMap.dcMotor.get("leftBack");
        RLIFT = hardwareMap.dcMotor.get("RLIFT");
        LLIFT = hardwareMap.dcMotor.get("LLIFT");
        topRotate = hardwareMap.dcMotor.get("topRotate");

        intakeServo1 = hardwareMap.crservo.get("intakeServo1");
        intakeServo2 = hardwareMap.crservo.get("intakeServo2");
        wrist = hardwareMap.servo.get("wrist");
        angleServo = hardwareMap.servo.get("angleServo");

        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        topRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //RLIFT.setTargetPosition(0);
        //LLIFT.setTargetPosition(0);
        topRotate.setTargetPosition(0);

        //LLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //RLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        topRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LLIFT.setDirection(DcMotor.Direction.REVERSE);
        topRotate.setDirection(DcMotor.Direction.REVERSE);

        RLIFT.setPower(1);
        LLIFT.setPower(1);
        topRotate.setPower(0.8);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(this::mecanumDrive);
        executorService.submit(this::servoTasks);


        waitForStart();

        while (opModeIsActive()) {
            Drive();
            armTesting();
            lifting();
            ServoMovement();
            telemetry.addData("LLIFT Encoder", LLIFT.getCurrentPosition());
            telemetry.addData("RLIFT Encoder", RLIFT.getCurrentPosition());
            telemetry.addData("top Rotate Encoder", topRotate.getCurrentPosition());
            telemetry.addData("TopRotateTargetPosition", topRotateTargetPosition);

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
            ServoMovement();
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

    /*public void lifting() {
        if (gamepad2.dpad_up) {
            targetPosition = Math.min(targetPosition + 25, 1250); // Increment by 50, capped at 1250
        }
        if (gamepad2.dpad_down) {
            targetPosition = Math.max(targetPosition - 25, 0); // Decrement by 50, floored at 0
        }
        RLIFT.setTargetPosition(targetPosition);
        LLIFT.setTargetPosition(targetPosition);

    }*/

    public void lifting() {
        // Get the current encoder position for the left lift motor
        int leftLiftPosition = LLIFT.getCurrentPosition();

        // Read the right joystick Y-axis value (range: -1 to 1)
        double joystickInput = -gamepad2.right_stick_y;  // Invert for correct direction

        // Calculate motor power based on joystick input (scale from -1 to 1)
        double power = Range.clip(joystickInput, -1.0, 1.0) * 0.9; // Scaling to a safe power range

        // Ensure the lift does not exceed the encoder limits using only the left lift position
        if (leftLiftPosition >= 3500 && power > 0) {
            // Stop lifting if the position is at the upper limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        } else if (leftLiftPosition <= 10 && power < 0) {
            // Stop lowering if the position is at the lower limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        }
        else if (leftLiftPosition > 1000 && power == 0 && leftLiftPosition < 3500   ) {
            LLIFT.setPower(0.08);
            RLIFT.setPower(0.08);

        }
        else {
            // Apply the calculated power if the lift is within limits
            LLIFT.setPower(power);
            RLIFT.setPower(power);

        }
        /*
        // Get the y-axis value of the gamepad2 right joystick (range is -1 to 1)
        double joystickInput = gamepad2.left_stick_y; // Use left joystick for vertical control


        targetPosition = (int) Range.clip(targetPosition + (int)(joystickInput * -15), 0, 3400);

        // Set the target position for both lift motors
        RLIFT.setTargetPosition(targetPosition);
        LLIFT.setTargetPosition(targetPosition);

        // Set power to move motors toward the target position

*/
    }


    public void armTesting() {
        if (gamepad2.dpad_down) {
            topRotate.setPower(0.8);
            topRotateTargetPosition = topRotateTargetPosition + 2;

        }
        // Decrease the target position for topRotate
        if (gamepad2.dpad_up) {
            topRotate.setPower(0.8);
            topRotateTargetPosition = topRotateTargetPosition - 2;


        }
        if (gamepad2.a) {
            topRotate.setPower(0.4);
            topRotateTargetPosition = 390;
        }
        topRotate.setTargetPosition(topRotateTargetPosition);
    }
    public void ServoMovement() {
        if (gamepad2.left_trigger > 0.9) { //spit out
            intakeServo1.setPower(0.7);
            intakeServo2.setPower(-0.7);

        } else if (gamepad2.right_trigger > 0.9) { //intake
            intakeServo1.setPower(-0.75);
            intakeServo2.setPower(0.75);
        } else {
            intakeServo1.setPower(0);
            intakeServo2.setPower(0);
        }

        if (gamepad2.right_bumper) {
            wristPosition += 0.01;
            sleep(5);
        } else if (gamepad2.left_bumper) {
            wristPosition -= 0.01;
            sleep(5);
        }
        if (gamepad1.right_bumper) {
            anglePosition += 0.01;
            sleep(5);
        } else if (gamepad1.left_bumper) {
            anglePosition -= 0.01;
            sleep(5);
        }

        wristPosition = Range.clip(wristPosition, 0, 1);
        wrist.setPosition(wristPosition);
        anglePosition = Range.clip(anglePosition, 0, 1);
        angleServo.setPosition(anglePosition);


    }
}