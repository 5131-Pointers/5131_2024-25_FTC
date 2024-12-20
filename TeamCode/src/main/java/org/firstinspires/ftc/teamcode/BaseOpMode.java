package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
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
    DcMotorEx RLIFT;
    DcMotorEx LLIFT;
    DcMotorEx bottomRotate;
    DcMotorEx topRotate;
    CRServo intakeServo1;
    CRServo intakeServo2;
    Servo wrist;
    Servo angleServo;



    // Variables for hanging tasks
    boolean dpadDownPressed = false;
    long moveStartTime = 0;
    boolean detect = false;
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    int unknown = 0;
    final static int red = 1;
    final static int blue = 2;
    final static int yellow = 3;
    volatile int detectedColor;
    MecanumDrive drive;
    // Thresholds for color detection
    int redThreshold = 900;
    int greenThreshold = 900;
    int blueThreshold = 750;
    public void setThreadAmount(int amount) {
         executorService = Executors.newFixedThreadPool(amount);
    }
    protected void actualInit()
    {

        FR = hardwareMap.get(DcMotorEx.class, "rightFront");
        FL = hardwareMap.get(DcMotorEx.class, "leftFront");
        BR = hardwareMap.get(DcMotorEx.class, "rightBack");
        BL = hardwareMap.get(DcMotorEx.class, "leftBack");

        RLIFT = hardwareMap.get(DcMotorEx.class, "RLIFT");
        LLIFT = hardwareMap.get(DcMotorEx.class, "LLIFT");
        bottomRotate = hardwareMap.get(DcMotorEx.class, "bottomRotate");
        topRotate = hardwareMap.get(DcMotorEx.class, "topRotate");

        intakeServo1 = hardwareMap.get(CRServo.class, "intakeServo1");
        intakeServo2 = hardwareMap.get(CRServo.class, "intakeServo2");
        wrist = hardwareMap.get(Servo.class, "wrist");
        angleServo = hardwareMap.get(Servo.class, "angleServo");


        drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        bottomRotate.setTargetPosition(0);

        //bottomRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Reset the motor encoder
        //topRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Reset the motor encoder

        LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // Reset the motor encoder
        LLIFT.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // Reset the motor encoder

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
