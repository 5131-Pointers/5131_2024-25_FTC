package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    com.qualcomm.robotcore.hardware.ColorSensor ColorSensor;
    CRServo HangServoRight;
    CRServo HangServoLeft;
    Servo SpoonServo;

    // Variables for hanging tasks
    boolean dpadDownPressed = false;
    long moveStartTime = 0;
    boolean detect = false;
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    int unknown = 0;
    final static int red = 1;
    final static int blue = 2;
    final static int yellow = 3;
    volatile int detectedColor;
    // Thresholds for color detection
    int redThreshold = 900;
    int greenThreshold = 900;
    int blueThreshold = 750;
    public void setThreadAmount(int amount) {
         executorService = Executors.newFixedThreadPool(amount);
    }
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
        FEXT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        FEXT.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Reset the motor encoder
        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        LLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Reset the motor encoder

        HangServoLeft.setDirection(CRServo.Direction.REVERSE);
        LLIFT.setDirection(DcMotor.Direction.REVERSE);
        RLIFT.setDirection(DcMotor.Direction.REVERSE);
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
    protected void actualStart()
    {
    }
    protected void extendStart()
    {
    }
    /**
     * @deprecated
     * This method is deprecated and has been replaced by extendLoop() and actualLoop()
     */
    @Deprecated
    @Override
    public final void start()
    {
        actualStart();
        extendStart();
    }
    protected void actualStop()
    {
    }
    protected void extendStop()
    {
    }
    /**
     * @deprecated
     * This method is deprecated and has been replaced by extendLoop() and actualLoop()
     */
    @Deprecated
    @Override
    public final void stop()
    {
        actualStop();
        extendStop();
    }
}
