package org.firstinspires.ftc.teamcode;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
@Config
@Autonomous(name = "LCAUTO", group = "Autonomous")
public class LCAuto extends LinearOpMode {
    public class TopRotate {
        private Servo outtakeArmLeft;
        private Servo outtakeArmRight;

        public TopRotate(HardwareMap hardwareMap) {
            outtakeArmLeft = hardwareMap.get(Servo.class, "outtakeArmLeft");
            outtakeArmRight = hardwareMap.get(Servo.class, "outtakeArmRight");

        }

        public class topRotateDropPos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeArmLeft.setPosition(0.37);
                outtakeArmRight.setPosition(0.37);
                return false;
            }
        }
        public Action topRotateDropPos() {
            return new topRotateDropPos();
        }
        public class topRotatePickUpPos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeArmLeft.setPosition(0.75);
                outtakeArmRight.setPosition(0.75);
                return false;
            }
        }
        public Action topRotatePickUpPos() {
            return new topRotatePickUpPos();

        }

    }
    public class Lift {
        private DcMotorEx LLIFT;
        private DcMotorEx RLIFT;

        public Lift(HardwareMap hardwareMap) {
            LLIFT = hardwareMap.get(DcMotorEx.class, "LLIFT");
            LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LLIFT.setTargetPosition(0);
            LLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LLIFT.setPower(1);

            RLIFT = hardwareMap.get(DcMotorEx.class, "RLIFT");
            RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RLIFT.setTargetPosition(0);
            RLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RLIFT.setPower(1);
            RLIFT.setDirection(DcMotor.Direction.REVERSE);


        }

        public class LiftUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                LLIFT.setTargetPosition(2000);
                RLIFT.setTargetPosition(2000);
                return false;
            }
        }
        public Action liftUp() {
            return new LiftUp();
        }

        public class LiftDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                LLIFT.setTargetPosition(0);
                RLIFT.setTargetPosition(0);
                return false;
            }
        }
        public Action liftDown(){
            return new LiftDown();
        }
        public class LiftFinal implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                LLIFT.setTargetPosition(-10);
                RLIFT.setTargetPosition(-10);
                return false;
            }
        }
        public Action LiftFinal(){
            return new LiftFinal();
        }
    }


    public class Claw {
        private Servo outtakeWrist;
        private Servo outtakePinch;
        private Servo outtakeRotate;

        public Claw(HardwareMap hardwareMap) {
            outtakeWrist = hardwareMap.get(Servo.class, "outtakeWrist");
            outtakePinch = hardwareMap.get(Servo.class, "outtakePinch");
            outtakeRotate = hardwareMap.get(Servo.class, "outtakeRotate");
            //rotatePosition grabbing = 0.69

        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakePinch.setPosition(0.4);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakePinch.setPosition(0.19);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
        public class wristPickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeWrist.setPosition(0.32);
                outtakeRotate.setPosition(0.15);
                return false;
            }
        }
        public Action wristPickUpPos() {
            return new wristPickUpPos();
        }

        public class wristDropPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                outtakeWrist.setPosition(0.32);
                outtakeRotate.setPosition(0.71);
                return false;
            }
        }
        public Action wristDropPos() {
            return new wristDropPos();
        }
        public class rotatePickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                return false;
            }
        }
        public Action rotatePickUpPos() {
            return new rotatePickUpPos();
        }

        public class rotateDropPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                return false;
            }
        }
        public Action rotateDropPos() {
            return new rotateDropPos();
        }
        public class rotateSidewaysPickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                return false;
            }
        }
        public Action rotateSidewaysPickUpPos() {
            return new rotateSidewaysPickUpPos();
        }


    }
    //public class Rotate {
    //    private DcMotorEx topRotate;
//
    //    public Rotate(HardwareMap hardwareMap) {
    //        topRotate = hardwareMap.get(DcMotorEx.class, "topRotate");
    //        topRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    //        topRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    //    }
//
    //    public class RotateGrabPos implements Action {
    //        @Override
    //        public boolean run(@NonNull TelemetryPacket packet) {
    //            topRotate.setPower(1);
    //            topRotate.setTargetPosition(1500);
    //            while (topRotate.getCurrentPosition() < 1500) {
    //                return true;
    //            }
    //            return false;
    //        }
    //    }
    //    public Action rotateGrabPos() {
    //        return new RotateGrabPos();
    //    }
//
    //    //public class OpenClaw implements Action {
    //    //    @Override
    //    //    public boolean run(@NonNull TelemetryPacket packet) {
    //    //        claw.setPosition(1.0);
    //    //        return false;
    //    //    }
    //    //}
    //    //public Action openClaw() {
    //    //    return new Claw.OpenClaw();
    //    //}
    //}

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        //Rotate rotate = new Rotate(hardwareMap);
        Claw claw = new Claw(hardwareMap);
        TopRotate topRotate = new TopRotate(hardwareMap);
        Lift lift = new Lift(hardwareMap);

        // vision here that outputs position
        //int visionOutputPosition = 1;

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(15.5, 12), Math.toRadians(135));
        TrajectoryActionBuilder tab2 = tab1.endTrajectory().fresh()
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(20, 10), Math.toRadians(135));


        Action trajectoryActionCloseOut = tab2.endTrajectory().fresh()
                .waitSeconds(2)
                .build();

        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(claw.closeClaw());
        //Actions.runBlocking(claw.wristStartPos());
        Actions.runBlocking(claw.rotatePickUpPos());







        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());

        //Actions.runBlocking(rotate.rotateGrabPos());
        //while (!isStopRequested() && !opModeIsActive()) {
        //    int position = visionOutputPosition;
        //    telemetry.addData("Position during Init", position);
        //    telemetry.update();
        //}

        //int startPosition = visionOutputPosition;
        //telemetry.addData("Starting Position", startPosition);
        //telemetry.update();
        waitForStart();

        if (isStopRequested()) return;

        //Action trajectoryActionChosen;
        //trajectoryActionChosen = tab1.build();


        Actions.runBlocking(
                new SequentialAction(
                        topRotate.topRotateDropPos(),
                        lift.liftUp(),
                        claw.rotateDropPos(),
                        claw.wristDropPos(),
                        tab1.build(),
                        claw.openClaw(),
                        tab2.build(),
                        lift.liftDown(),
                        topRotate.topRotatePickUpPos(),
                        claw.rotatePickUpPos(),
                        claw.wristPickUpPos(),
                        trajectoryActionCloseOut



                )
        );
    }
}