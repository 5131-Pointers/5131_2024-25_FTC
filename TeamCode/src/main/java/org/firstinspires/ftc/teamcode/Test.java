package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "Test Comps", group = "Test")
public class Test extends BaseOpMode {
    @Override
    public void extendLoop() {
        telemetry.addData("LDOM", LDOM.getCurrentPosition());
        telemetry.addData("RDOM", RDOM.getCurrentPosition());
        telemetry.addData("HDOM", RDOM.getCurrentPosition());
        telemetry.addData("ColorSensor: Red", ColorSensor.red());
        telemetry.addData("ColorSensor: Green", ColorSensor.green());
        telemetry.addData("ColorSensor: Blue", ColorSensor.blue());
        // Thresholds for color detection
        int redThreshold = 900;
        int greenThreshold = 900;
        int blueThreshold = 750;

        // Detect if the color is red
        if (ColorSensor.red() > 800 && ColorSensor.red() > ColorSensor.blue() && ColorSensor.red() > ColorSensor.green()) {
            telemetry.addData("Detected Color", "Red");
        }
        // Detect if the color is yellow (requires both red and green)
        else if (ColorSensor.red() > 900 && ColorSensor.green() > 900) {
            telemetry.addData("Detected Color", "Yellow");
        }
        // Detect if the color is blue
        else if (ColorSensor.blue() > 500 && ColorSensor.blue() > ColorSensor.red() && ColorSensor.blue() > ColorSensor.green() ) {
            telemetry.addData("Detected Color", "Blue");
        }
        // If no specific color is detected
        else {
            telemetry.addData("Detected Color", "Unknown");
        }
        //if (gamepad1.a) {FR.setPower(.1);}
        //else {FR.setPower(0);}
        //if (gamepad1.b) {FL.setPower(.1);}
        //else {FL.setPower(0);}
        //if (gamepad1.x) {BR.setPower(.1);}
        //else {BR.setPower(0);}
        //if (gamepad1.y) {BL.setPower(.1);}
        //else {BL.setPower(0);}


        if (gamepad2.right_bumper) {
            Intake.setPower(0.8);
        }
/*
        if (gamepad1.right_bumper) {Intake.setPower(-1);}

        else if (ColorSensor.red() > 800) {Intake.setPower(0);}

        else if (gamepad1.left_bumper) {Intake.setPower(1);}

        if (gamepad1.dpad_up) {FEXT.setPower(.4);}
        else {FEXT.setPower(0);}
        if (gamepad1.dpad_down) {HangServoRight.setPower(-1);HangServoLeft.setPower(-1);}
        else {HangServoRight.setPower(0);HangServoLeft.setPower(0);}
        if (gamepad1.dpad_left) {RLIFT.setPower(.8);LLIFT.setPower(.8);}
        else {RLIFT.setPower(0);LLIFT.setPower(0);}
        if (gamepad1.dpad_right) {RLIFT.setPower(-.8);LLIFT.setPower(-.8);}
        else {RLIFT.setPower(0);LLIFT.setPower(0);}
        if (gamepad2.a) {SpoonServo.setPosition(.3);}

         */


    }
}
