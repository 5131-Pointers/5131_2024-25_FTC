package org.firstinspires.ftc.teamcode;

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
        if (gamepad1.a) {FR.setPower(.1);}
        else {FR.setPower(0);}
        if (gamepad1.b) {FL.setPower(.1);}
        else {FL.setPower(0);}
        if (gamepad1.b) {BR.setPower(.1);}
        else {BR.setPower(0);}
        if (gamepad1.b) {BL.setPower(.1);}
        else {BL.setPower(0);}

        if (gamepad1.right_bumper) {Intake.setPower(-1);}

        else if (ColorSensor.red() > 800) {Intake.setPower(0);}

        else if (gamepad1.left_bumper) {Intake.setPower(1);}

        if (gamepad1.dpad_up) {FEXT.setPower(.1);}
        else {FEXT.setPower(0);}
        if (gamepad1.dpad_down) {HangServo.setPower(-1);}
        else {HangServo.setPower(0);}
        if (gamepad1.dpad_left) {RLIFT.setPower(.5);LLIFT.setPower(.5);}
        else {RLIFT.setPower(0);LLIFT.setPower(0);}
        if (gamepad1.dpad_right) {RLIFT.setPower(-.5);LLIFT.setPower(-.5);}
        else {RLIFT.setPower(0);LLIFT.setPower(0);}

    }
}
