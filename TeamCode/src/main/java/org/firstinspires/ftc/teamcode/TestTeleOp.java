package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import java.util.concurrent.*;

@TeleOp(name = "TestTeleOp", group = "Linear Opmode")
@Disabled
public class TestTeleOp extends LinearOpMode {

    DcMotorEx FR;
    DcMotorEx FL;
    DcMotorEx BR;
    DcMotorEx BL;
    DcMotorEx LDOM;
    DcMotorEx RDOM;
    DcMotorEx HDOM;
    DcMotorEx FEXT;
    DcMotorEx RLIFT;
    DcMotorEx LLIFT;
    Servo IntakeRotate;
    Servo ExtenderRotate;
    CRServo Intake;
    com.qualcomm.robotcore.hardware.ColorSensor ColorSensor;
    CRServo HangServoRight;
    CRServo HangServoLeft;
    Servo SpoonServo;

    // Variables for hanging tasks
    boolean dpadDownPressed = false;
    long moveStartTime = 0;
    boolean detect = false;
    @Override
    public void runOpMode(){
        FR = hardwareMap.get(DcMotorEx.class, "FR");
        FL = hardwareMap.get(DcMotorEx.class, "FR");
        BR = hardwareMap.get(DcMotorEx.class, "BR");
        BL = hardwareMap.get(DcMotorEx.class, "BL");
        LDOM = BL;
        RDOM = FR;
        HDOM = FL;
        FEXT = hardwareMap.get(DcMotorEx.class, "FEXT");
        RLIFT = hardwareMap.get(DcMotorEx.class, "RLIFT");
        LLIFT = hardwareMap.get(DcMotorEx.class, "LLIFT");
        IntakeRotate = hardwareMap.get(Servo.class, "IntakeRotate");
        ExtenderRotate = hardwareMap.get(Servo.class, "ExtenderRotate");
        Intake = hardwareMap.get(CRServo.class, "Intake");
        ColorSensor = hardwareMap.get(ColorSensor.class, "ColorSensor");
        HangServoRight = hardwareMap.get(CRServo.class, "HangServoRight");
        HangServoLeft = hardwareMap.get(CRServo.class, "HangServoLeft");
        SpoonServo = hardwareMap.get(Servo.class, "SpoonServo");
        LDOM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        LDOM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        RDOM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        RDOM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        HDOM.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        HDOM.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Turn the motor back on when we are done
        FEXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        FEXT.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Reset the motor encoder
        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        LLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Reset the motor encoder

        HangServoLeft.setDirection(CRServo.Direction.REVERSE);
        LLIFT.setDirection(DcMotor.Direction.REVERSE);
        RLIFT.setDirection(DcMotor.Direction.REVERSE);

        //fl = hardwareMap.get(DcMotorEx.class, "fl");
        //fr = hardwareMap.get(DcMotorEx.class, "fr");
        //bl = hardwareMap.get(DcMotorEx.class, "bl");
        //br = hardwareMap.get(DcMotorEx.class, "br");
        //fl.setDirection(DcMotor.Direction.REVERSE);
        //fr.setDirection(DcMotor.Direction.FORWARD);
        //bl.setDirection(DcMotor.Direction.REVERSE);
        //br.setDirection(DcMotor.Direction.FORWARD);
        //intakeServo = hardwareMap.get(CRServo.class, "intake");

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(this::servoTasks);
        executorService.submit(this::hangingTasks);
        executorService.submit(this::liftingTasks);

        waitForStart();

        while (opModeIsActive()) {
            //HangingTasks();
            //extenderAdjustment();
            //intakeSpinner();
            //extenderRotateMovement();
            //lift();
            telemetry.addData("ExtenderPos", FEXT.getCurrentPosition());
            telemetry.addData("LeftLiftPos", LLIFT.getCurrentPosition());


            telemetry.update();
        }
        executorService.shutdownNow(); // Shut down the ExecutorService


    }
    private void servoTasks() {
        while (!Thread.interrupted()) {
            extenderAdjustment();
            intakeSpinner();
            extenderRotateMovement();
        }
    }
    private void liftingTasks() {
        while (!Thread.interrupted()) {
            lift();
        }
    }

    private void hangingTasks() {
        while (!Thread.interrupted()) {
            HangingTasks();
        }
    }
    public void lift() {
        sleep(1000);
        // Get the current encoder position for the left lift motor
        int leftLiftPosition = LLIFT.getCurrentPosition();

        // Read the right joystick Y-axis value (range: -1 to 1)
        double joystickInput = -gamepad2.right_stick_y;  // Invert for correct direction

        // Calculate motor power based on joystick input (scale from -1 to 1)
        double power = Range.clip(joystickInput, -1.0, 1.0) * 0.8; // Scaling to a safe power range

        // Ensure the lift does not exceed the encoder limits using only the left lift position
        if (leftLiftPosition >= 5500 && power > 0) {
            // Stop lifting if the position is at the upper limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        } else if (leftLiftPosition <= 0 && power < 0) {
            // Stop lowering if the position is at the lower limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        } else {
            // Apply the calculated power if the lift is within limits
            LLIFT.setPower(power);
            RLIFT.setPower(power);

        }
    }
    public void extenderRotateMovement() {
        if (gamepad2.a) {
            ExtenderRotate.setPosition(0.53);
            IntakeRotate.setPosition(0.15); //ready for pickup
        }
        else if (gamepad2.y) {
            ExtenderRotate.setPosition(0);
            IntakeRotate.setPosition(0.75);
        }
        else if (gamepad2.x) {
            SpoonServo.setPosition(0.3);
        }
        else if (gamepad2.b) {
            SpoonServo.setPosition(1); // drop pos
        }

    }
    public void intakeSpinner() {
        if (gamepad2.right_bumper) {
            Intake.setPower(0.8);
        }
        else if (ColorSensor.red() > 800) {
            //gamepad2.rumble(250);
            Intake.setPower(0);
        }
        else {
            detect = ColorSensor.red() > 800;
        }
        if (gamepad2.left_bumper) {
            Intake.setPower(-1);
        }
    }
    public void extenderAdjustment() {
        // Get the current position of the extender motor
        int currentPosition = FEXT.getCurrentPosition();
        double joystickInput = -gamepad2.left_stick_y;
        // Scale joystick input to motor power
        double power = Range.clip(joystickInput, -1.0, 1.0) * 0.25;  // Max power scaled to 0.4
        if (currentPosition >= 300 && power > 0) { //encoder limit
            power = 0;  //upper limit stop
        } else if (currentPosition <= 0 && power < 0) {
            power = 0;  //lower limit stop
        }
        FEXT.setPower(power);
    }
    public void HangingTasks() {
        // If D-pad Down is pressed, start moving for 3 seconds
        if (gamepad1.dpad_down && !dpadDownPressed) {
            moveStartTime = System.currentTimeMillis();
            dpadDownPressed = true;
        }
        // Move the servos for 12 seconds after D-pad Down
        if (dpadDownPressed && System.currentTimeMillis() - moveStartTime < 12000) {
            HangServoRight.setPower(1);
            HangServoLeft.setPower(1);
        } else {
            dpadDownPressed = false; // Stop after 3 seconds
            HangServoRight.setPower(0);
            HangServoLeft.setPower(0);
        }
        // If right bumper is pressed, move HangServoRight
        if (gamepad1.right_bumper) {
            HangServoRight.setPower(-1); // Reverse direction
        } else {
            // If right bumper is NOT pressed, stop HangServoRight
            if (!gamepad1.dpad_down) { // Don't stop if the DpadDown is active
                HangServoRight.setPower(0);
            }
        }
        // If left bumper is pressed, move HangServoLeft
        if (gamepad1.left_bumper) {
            HangServoLeft.setPower(-1); // Reverse direction
        } else {
            // If left bumper is NOT pressed, stop HangServoLeft
            if (!gamepad1.dpad_down) { // Don't stop if the DpadDown is active
                HangServoLeft.setPower(0);
            }
        }
        // Additional safety condition to stop both servos when neither bumper is pressed
        if (!gamepad1.right_bumper && !gamepad1.left_bumper && !dpadDownPressed) {
            HangServoRight.setPower(0);
            HangServoLeft.setPower(0);
        }
    }

}