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


@TeleOp(name = "LC Mecanum Drive", group = "Linear Opmode")
public class LCMecanumDrive extends LinearOpMode {
    public DcMotor rightFront;
    public DcMotor leftFront;
    public DcMotor rightBack;
    public DcMotor leftBack;
    public DcMotor RLIFT;
    public DcMotor LLIFT;
    public DcMotor horzSpool;

    public Servo outtakeWrist;
    public Servo outtakePinch;
    public Servo intakeWrist;
    public Servo intakePinch;
    public Servo intakeRotate;
    public Servo outtakeArmLeft;
    public Servo outtakeArmRight;
    public Servo outtakeRotate;

    public int horzSpoolTargetPosition = 0; // Initialize target position for horzSpool

    public int gameMode = 2;
    //1 = clips
    //2 = baskets/samples
    public double intakePinchHolding = 0.775;
    public double intakePinchReleased = 0.36;

    public double intakeWristPickUpPos = 0.9;
    public double outtakeArmClipPickUp = 0.83;

    public double outtakeArmReadyToClip = 0.4;

    public double outtakePinchHolding = 0.4;
    public double outtakePinchReleased = 0.19;
    public double outtakeWristSpecimenPickUp = 0.23;
    public double outtakeWristSpecimenReadyToClip = 0.33;



    public double outtakeRotateSpeciminPickUp = 0.15;
    public double outtakeRotateSpeciminDrop = 0.71;

    public double handoffIntakeRotatePos = 0.41;
    public double handoffIntakeWristPos = 0.3;
    public double handoffOuttakeRotatePos = .43;
    public double handoffOuttakeArmPos = 0.675;
    public double handoffOuttakeWristPos = 0.66;

    public double outtakeArmBucketDepositPos = 0.45;
    public double outtakeWristBucketDepositPos = 0.18;

    public int verticalTargetPosition = 0; // Initialize target position
    MecanumDrive drive;


    @Override
    public void runOpMode() {
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        RLIFT = hardwareMap.dcMotor.get("RLIFT");
        LLIFT = hardwareMap.dcMotor.get("LLIFT");
        horzSpool = hardwareMap.dcMotor.get("horzSpool");
        outtakeWrist = hardwareMap.servo.get("outtakeWrist");
        outtakePinch = hardwareMap.servo.get("outtakePinch");
        intakeWrist = hardwareMap.servo.get("intakeWrist");
        intakePinch = hardwareMap.servo.get("intakePinch");
        intakeRotate = hardwareMap.servo.get("intakeRotate");
        outtakeArmLeft = hardwareMap.servo.get("outtakeArmLeft");
        outtakeArmRight = hardwareMap.servo.get("outtakeArmRight");
        outtakeRotate = hardwareMap.servo.get("outtakeRotate");









        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        outtakeArmLeft.setPosition(0.73);
        outtakeArmRight.setPosition(0.73);
        outtakeRotate.setPosition(0.15);
        outtakeWrist.setPosition(outtakeWristSpecimenPickUp);

        RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        RLIFT.setTargetPosition(0);
        LLIFT.setTargetPosition(0);

        LLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //RLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //LLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        RLIFT.setDirection(DcMotor.Direction.REVERSE);
        RLIFT.setPower(1);
        LLIFT.setPower(1);

        horzSpool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horzSpool.setTargetPosition(0);
        horzSpool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        horzSpool.setPower(1.0); // Set power to allow motor to move to targe

        //intakeWrist.setPosition(intakeWristPickUpPos);
        intakeRotate.setPosition(0.67);



        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(this::mecanumDrive);
        executorService.submit(this::servoTasks);


        waitForStart();

        while (opModeIsActive()) {
            Drive();
            //armTesting();
            lifting();
            //ServoMovement();
            if (gamepad1.back) {
                if (gameMode == 1) {
                    gameMode = 2;
                }
                else {
                    gameMode = 1;
                }
            }
            telemetry.addData("LLIFT Encoder", LLIFT.getCurrentPosition());
            telemetry.addData("RLIFT Encoder", RLIFT.getCurrentPosition());
            telemetry.addData("horzSpool Encoder", horzSpool.getCurrentPosition());
            telemetry.addData("encoder", rightBack.getCurrentPosition());
            telemetry.addData("intake rotate pos", intakeRotate.getPosition());
            telemetry.addData("outtake Wrist", outtakeWrist.getPosition());



            telemetry.addData("GameMode - (1 Clips) , (2 Buckets)", gameMode);


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
            horzSpooling();
            clawTesting();
            clawRotation();
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
        // Get the current encoder position for the left lift motor


        // Get the y-axis value of the gamepad2 right joystick (range is -1 to 1)
        double joystickInput = gamepad2.right_stick_y; // Use left joystick for vertical control


        verticalTargetPosition = (int) Range.clip(verticalTargetPosition + (int)(joystickInput * -25), -20, 2200);

        // Set the target position for both lift motors
        RLIFT.setTargetPosition(verticalTargetPosition);
        LLIFT.setTargetPosition(verticalTargetPosition);

        // Set power to move motors toward the target position

    }

    public void horzSpooling() {
        double joystickInput2 = -gamepad2.left_stick_y; // Inverted to match intuitive up/down

        // Change in position is proportional to joystick displacement
        int positionChange = (int) (joystickInput2 * 28);

        // Update target position within limits [0, 700]
        horzSpoolTargetPosition = Range.clip(horzSpoolTargetPosition + positionChange, -15, 730);

        // Set new target position
        horzSpool.setTargetPosition(horzSpoolTargetPosition);

    }
    public void clawRotation() {
        if (gamepad2.right_bumper) {
            intakeRotate.setPosition(intakeRotate.getPosition() + 0.01);
            sleep(3);
        } else if (gamepad2.left_bumper) {
            intakeRotate.setPosition(intakeRotate.getPosition() - 0.01);
            sleep(3);
        }
        if (gamepad1.right_bumper) {
            outtakeWrist.setPosition(outtakeWrist.getPosition() + 0.01);
            sleep(3);
        } else if (gamepad1.left_bumper) {
            outtakeWrist.setPosition(outtakeWrist.getPosition() - 0.01);
            sleep(3);
        }
        if (gamepad1.a) {
            RLIFT.setPower(1);
            LLIFT.setPower(1);
        }

    }
    public void clawTesting() {
        if (gamepad2.left_trigger > 0.5) { //opened
            outtakePinch.setPosition(outtakePinchReleased);

        } else if (gamepad2.right_trigger > 0.5) { //pinched
            outtakePinch.setPosition(outtakePinchHolding);
        }
        if (gamepad1.left_trigger > 0.5) { //opened
            intakePinch.setPosition(intakePinchReleased);

        } else if (gamepad1.right_trigger > 0.5) { //pinched
            intakePinch.setPosition(intakePinchHolding);
        }
        if (gameMode == 1) {
            if (gamepad2.x) {


                outtakePinch.setPosition(outtakePinchReleased);
                outtakeArmLeft.setPosition(outtakeArmClipPickUp);
                outtakeArmRight.setPosition(outtakeArmClipPickUp);
                outtakeWrist.setPosition(outtakeWristSpecimenPickUp);
                outtakeRotate.setPosition(outtakeRotateSpeciminPickUp);
            }
            if (gamepad2.y) {
                outtakeArmLeft.setPosition(outtakeArmReadyToClip);
                outtakeArmRight.setPosition(outtakeArmReadyToClip);
                outtakeRotate.setPosition(outtakeRotateSpeciminDrop);
                outtakeWrist.setPosition(outtakeWristSpecimenReadyToClip);
            }
            if (gamepad2.a) {
                outtakeArmLeft.setPosition(0.34);
                outtakeArmRight.setPosition(0.34);
                sleep(400);
                outtakePinch.setPosition(outtakePinchReleased);

            }

        }
        if (gameMode == 2) {
            if (gamepad2.x) {

                horzSpoolTargetPosition = -10;
                verticalTargetPosition = -10;
                horzSpool.setTargetPosition(horzSpoolTargetPosition);
                RLIFT.setTargetPosition(verticalTargetPosition);
                LLIFT.setTargetPosition(verticalTargetPosition);

                intakeRotate.setPosition(0.67);
                intakeWrist.setPosition(handoffIntakeWristPos);
                outtakePinch.setPosition(outtakePinchReleased);
                sleep(200);
                outtakeRotate.setPosition(outtakeRotateSpeciminDrop);
                outtakeArmLeft.setPosition(0.64);
                outtakeArmRight.setPosition(0.64);
                outtakeWrist.setPosition(handoffOuttakeWristPos);
                sleep(600);
                outtakeArmLeft.setPosition(handoffOuttakeArmPos);
                outtakeArmRight.setPosition(handoffOuttakeArmPos);
                sleep(200);
                outtakePinch.setPosition(outtakePinchHolding);
                sleep(100);
                intakePinch.setPosition(intakePinchReleased);
            }

            if (gamepad2.y) {
                outtakeArmLeft.setPosition(outtakeArmBucketDepositPos);
                outtakeArmRight.setPosition(outtakeArmBucketDepositPos);
                outtakeWrist.setPosition(outtakeWristBucketDepositPos);
            }


            if (gamepad2.a) {

            }

        }

        if (gamepad2.dpad_left) {
            intakeWrist.setPosition(0.5);
            intakeRotate.setPosition(0.67);
        }
        if (gamepad2.dpad_down) {
            intakePinch.setPosition(intakePinchReleased);
            intakeWrist.setPosition(intakeWristPickUpPos);
        }
        if (gamepad2.dpad_up) {
            intakeWrist.setPosition(0.45);

        }






    }




}