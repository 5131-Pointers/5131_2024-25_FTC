package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "RedTeleOp", group = "Competition")
public class RedTeleOp extends BaseOpMode {
    @Override
    public void extendStart() {
        executorService.submit(this::servoTasks);
        executorService.submit(this::liftingTasks);
        executorService.submit(this::mecanumDrive);
    }
    @Override
    public void extendLoop() {

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
        telemetry.addData("Rumbling", gamepad2.isRumbling());
        telemetry.addData("RTrigger", gamepad2.right_trigger);
        telemetry.addData("LTrigger", gamepad2.left_trigger);


        telemetry.update();
    }
    @Override
    public void extendStop() {
        executorService.shutdownNow(); // Shut down the ExecutorService
    }

    private void servoTasks() {
        while (!Thread.interrupted()) {
            /*
            extenderAdjustment();
            intakeSpinner();
            extenderRotateMovement();

             */
        }
    }
    private void liftingTasks() {
        while (!Thread.interrupted()) {
            /*
            lift();
            HangingTasks();
            ColorDetection();

             */
        }
    }

    private void mecanumDrive() {
        while (!Thread.interrupted()) {
            Drive();
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
    /*
    public void ColorDetection() {
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
        double power = Range.clip(joystickInput, -1.0, 1.0) * 0.9; // Scaling to a safe power range

        // Ensure the lift does not exceed the encoder limits using only the left lift position
        if (leftLiftPosition >= 6000 && power > 0) {
            // Stop lifting if the position is at the upper limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        } else if (leftLiftPosition <= 10 && power < 0) {
            // Stop lowering if the position is at the lower limit
            LLIFT.setPower(0);
            RLIFT.setPower(0);

        }
        else if (leftLiftPosition > 1000 && power == 0 && leftLiftPosition < 6000) {
            LLIFT.setPower(0.08);
            RLIFT.setPower(0.08);

        }
        else {
            // Apply the calculated power if the lift is within limits
            LLIFT.setPower(power);
            RLIFT.setPower(power);

        }
    }

    public void extenderRotateMovement() {
        int leftLiftPosition = LLIFT.getCurrentPosition();
        if (gamepad2.a) {
            ExtenderRotate.setPosition(0.885);
            IntakeRotate.setPosition(0.15); //ready for pickup
        }
        else if (gamepad2.y) {
            FEXT.setPower(-0.4);
            ExtenderRotate.setPosition(0.3);
            IntakeRotate.setPosition(0.85);
            SpoonServo.setPosition(0.35);
            while (FEXT.getCurrentPosition() > 20) {};
            FEXT.setPower(0);
            if (leftLiftPosition < 100 && FEXT.getCurrentPosition() < 50) {
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intake.setPower(-0.5);
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Intake.setPower(0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ExtenderRotate.setPosition(0.5);
                IntakeRotate.setPosition(0.15); //ready for pickup
            }
        }
        else if (gamepad2.x) {
            if (leftLiftPosition > 500) {
                SpoonServo.setPosition(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                SpoonServo.setPosition(0.35);
            }
        }
        else if (gamepad2.b) {
            ExtenderRotate.setPosition(0.7);
        }
        if (gamepad2.right_trigger > gamepad2.left_trigger || gamepad2.right_trigger > 0) {
            Intake.setPower(gamepad2.right_trigger);
            if (detectedColor == red || detectedColor == yellow) {
                Intake.setPower(0);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                ExtenderRotate.setPosition(0.7);
            }
            else if (detectedColor == blue) {
                Intake.setPower(-1);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Intake.setPower(0);
            }
        }
        else {
            Intake.setPower(-gamepad2.left_trigger);
        }

    }
    public void intakeSpinner() {
        //if (gamepad2.right_trigger > gamepad2.left_trigger || gamepad2.right_trigger > 0) {
        //    Intake.setPower(gamepad2.right_trigger);
        //}
        //else {
        //    Intake.setPower(-gamepad2.left_trigger);
        //}

    }
    public void extenderAdjustment() {
        // Get the current position of the extender motor
        int currentPosition = FEXT.getCurrentPosition();
        double joystickInput = -gamepad2.left_stick_y;
        // Scale joystick input to motor power
        double power = Range.clip(joystickInput, -1.0, 1.0) * 0.3;  // Max power scaled to 0.4
        if (currentPosition >= 360 && power > 0) { //encoder limit
            power = 0;  //upper limit stop
        }

        FEXT.setPower(power);
    }
    public void HangingTasks() {
        if (gamepad1.dpad_down) {
            HangServoRight.setPower(1);
            HangServoLeft.setPower(1);
        }
        if (gamepad1.right_bumper) {
            HangServoLeft.setPower(-1); // Reverse direction
        }
        if (gamepad1.left_bumper) {
            HangServoRight.setPower(-1); // Reverse direction
        }
        else {
            HangServoRight.setPower(0); // Reverse direction
            HangServoLeft.setPower(0); // Reverse direction
        }
    }

     */
}


