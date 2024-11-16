package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TestTeleOpBased", group = "Test")
public class TestTeleOpBased extends BaseOpMode {
    @Override
    public void extendStart() {
        executorService.submit(this::servoTasks);
        executorService.submit(this::hangingTasks);
        executorService.submit(this::liftingTasks);
        executorService.submit(this::sensingTasks);
    }
    @Override
    public void extendLoop() {
        telemetry.addData("ExtenderPos", FEXT.getCurrentPosition());
        telemetry.addData("LeftLiftPos", LLIFT.getCurrentPosition());

        if (detectedColor == unknown) {
            telemetry.addData("Detected Color", "unknown");
        }
        else if (detectedColor == red) {
            telemetry.addData("Detected Color", "red");
        }
        else if (detectedColor == blue) {
            telemetry.addData("Detected Color", "blue");
        }
        else if (detectedColor == yellow) {
            telemetry.addData("Detected Color", "yellow");
        }
        telemetry.update();
    }
    @Override
    public void extendStop() {
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
    private void sensingTasks() {
        while (!Thread.interrupted()) {
            ColorSensor();
        }
    }
    public void ColorSensor() {
        // Detect if the color is red
        if (ColorSensor.red() > 800 && ColorSensor.red() > ColorSensor.blue() && ColorSensor.red() > ColorSensor.green()) {
            detectedColor = red;
        }
        // Detect if the color is yellow (requires both red and green)
        else if (ColorSensor.red() > 900 && ColorSensor.green() > 900) {
            detectedColor = yellow;
        }
        // Detect if the color is blue
        else if (ColorSensor.blue() > 500 && ColorSensor.blue() > ColorSensor.red() && ColorSensor.blue() > ColorSensor.green() ) {
            detectedColor = blue;
        }
        // If no specific color is detected
        else {
            detectedColor = unknown;
        }
    }
    public void lift() {
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
        else if (detectedColor == red) {
            Intake.setPower(0);
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
