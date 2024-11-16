package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

/*
This is the class that contains all the default values for each OpMode so there is no need to
redefine anything, you can redefine the default values in a OpMode with the actualInit() and actualLoop()
function(s) or extend them with the extendInit() and extendLoop()
Note: The device names work under the assumption that the "default" robot config is being used
 */
public class BaseOpMode extends OpMode {
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
    ColorSensor ColorSensor;
    CRServo HangServoRight;
    CRServo HangServoLeft;
    Servo SpoonServo;
    protected void actualInit()
    {
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
        HangServoLeft.setDirection(CRServo.Direction.REVERSE);
        //LLIFT.setDirection(DcMotor.Direction.REVERSE);
        //fl = hardwareMap.get(DcMotorEx.class, "fl");
        //fr = hardwareMap.get(DcMotorEx.class, "fr");
        //bl = hardwareMap.get(DcMotorEx.class, "bl");
        //br = hardwareMap.get(DcMotorEx.class, "br");
        //fl.setDirection(DcMotor.Direction.REVERSE);
        //fr.setDirection(DcMotor.Direction.FORWARD);
        //bl.setDirection(DcMotor.Direction.REVERSE);
        //br.setDirection(DcMotor.Direction.FORWARD);
        //intakeServo = hardwareMap.get(CRServo.class, "intake");
    }
    protected void extendInit()
    {
    }
    /**
     * @deprecated
     * This method is deprecated and has been replaced by extendInit() and actualInit()
     */
    @Deprecated
    @Override
    public final void init()
    {
        actualInit();
        extendInit();
    }
    protected void actualLoop()
    {
    }
    protected void extendLoop()
    {
    }
    /**
     * @deprecated
     * This method is deprecated and has been replaced by extendLoop() and actualLoop()
     */
    @Deprecated
    @Override
    public final void loop()
    {
        actualLoop();
        extendLoop();
    }
}
