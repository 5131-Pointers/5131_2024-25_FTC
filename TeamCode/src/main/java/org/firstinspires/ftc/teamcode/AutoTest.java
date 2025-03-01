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
@Autonomous(name = "Auto Test", group = "Autonomous")
public class AutoTest extends LinearOpMode {
    public class TopRotate {
        private DcMotorEx topRotate;

        public TopRotate(HardwareMap hardwareMap) {
            topRotate = hardwareMap.get(DcMotorEx.class, "topRotate");
            topRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            topRotate.setTargetPosition(0);
            topRotate.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            topRotate.setPower(0.5);

        }

        public class topRotateDropPos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                topRotate.setTargetPosition(1850);
                return false;
            }
        }
        public Action topRotateDropPos() {
            return new topRotateDropPos();
        }
        public class topRotateCenterPos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                topRotate.setTargetPosition(350);
                return false;
            }
        }
        public Action topRotateCenterPos() {
            return new topRotateCenterPos();
        }
        public class topRotatePickUpPos implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                topRotate.setTargetPosition(210);
                return false;
            }
        }
        public Action topRotatePickUpPos() {
            return new topRotatePickUpPos();

        }
        public class topRotateEnd implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                topRotate.setTargetPosition(-10);
                return false;
            }
        }
        public Action topRotateEnd() {
            return new topRotateEnd();

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
            LLIFT.setDirection(DcMotor.Direction.REVERSE);

            RLIFT = hardwareMap.get(DcMotorEx.class, "RLIFT");
            RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RLIFT.setTargetPosition(0);
            RLIFT.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RLIFT.setPower(1);

        }

        public class LiftUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                LLIFT.setTargetPosition(3490);
                RLIFT.setTargetPosition(3490);
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
        private Servo pinch;
        private Servo wrist;
        private Servo rotate;

        public Claw(HardwareMap hardwareMap) {
            pinch = hardwareMap.get(Servo.class, "pinch");
            wrist = hardwareMap.get(Servo.class, "wrist");
            rotate = hardwareMap.get(Servo.class, "rotate");
            //rotatePosition grabbing = 0.69
            //wristPosition grabbing = 0.76
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                pinch.setPosition(0.505);
                return false;
            }
        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                pinch.setPosition(0.05);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }
        public class wristPickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.76);
                return false;
            }
        }
        public Action wristPickUpPos() {
            return new wristPickUpPos();
        }
        public class wristStartPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.15);
                return false;
            }
        }
        public Action wristStartPos() {
            return new wristStartPos();
        }
        public class wristDropPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.35);
                return false;
            }
        }
        public Action wristDropPos() {
            return new wristDropPos();
        }
        public class rotatePickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotate.setPosition(0.7);
                return false;
            }
        }
        public Action rotatePickUpPos() {
            return new rotatePickUpPos();
        }

        public class rotateDropPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotate.setPosition(0.15);
                return false;
            }
        }
        public Action rotateDropPos() {
            return new rotateDropPos();
        }
        public class rotateSidewaysPickUpPos implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rotate.setPosition(0.97);
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
                .strafeToLinearHeading(new Vector2d(8, 23), Math.toRadians(135));
        TrajectoryActionBuilder tab2 = tab1.endTrajectory().fresh()
                .waitSeconds(.3)
                .strafeToLinearHeading(new Vector2d(12, 17), Math.toRadians(180));
        TrajectoryActionBuilder tab3 = tab2.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(26.2, 17), Math.toRadians(180));

        TrajectoryActionBuilder tab4 = tab3.endTrajectory().fresh()
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(8, 23), Math.toRadians(135));
        TrajectoryActionBuilder tab5 = tab4.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(12, 26.3), Math.toRadians(180));
        TrajectoryActionBuilder tab6 = tab5.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(26.5, 26.3), Math.toRadians(180));
        TrajectoryActionBuilder tab7 = tab6.endTrajectory().fresh()
                .waitSeconds(1)
                .strafeToLinearHeading(new Vector2d(8, 22.5), Math.toRadians(135));
        TrajectoryActionBuilder tab8 = tab7.endTrajectory().fresh()
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(18, 22), Math.toRadians(270));
        TrajectoryActionBuilder tab9 = tab8.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(40, 25), Math.toRadians(270));
        TrajectoryActionBuilder tab10 = tab9.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(37.5, 29.5), Math.toRadians(270));
        TrajectoryActionBuilder tab11 = tab10.endTrajectory().fresh()
                .strafeToLinearHeading(new Vector2d(37.5, 26), Math.toRadians(270));
        TrajectoryActionBuilder tab12 = tab11.endTrajectory().fresh()
                .waitSeconds(0.75)
                .strafeToLinearHeading(new Vector2d(8, 22.5), Math.toRadians(135));
        TrajectoryActionBuilder tab13 = tab12.endTrajectory().fresh()
                .waitSeconds(0.6)
                .strafeToLinearHeading(new Vector2d(12, 19), Math.toRadians(135));
        Action trajectoryActionCloseOut = tab13.endTrajectory().fresh()
                .waitSeconds(0.1)
                .build();

        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(claw.closeClaw());
        Actions.runBlocking(claw.wristStartPos());
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
                        claw.rotatePickUpPos(),
                        topRotate.topRotateCenterPos(),
                        claw.wristPickUpPos(),
                        lift.liftDown(),
                        tab3.build(),
                        topRotate.topRotatePickUpPos(),
                        new SleepAction(.5),
                        claw.closeClaw(),
                        new SleepAction(.5),
                        claw.wristDropPos(),
                        topRotate.topRotateDropPos(),
                        claw.rotateDropPos(),
                        lift.liftUp(),
                        new SleepAction(1),
                        tab4.build(),
                        claw.openClaw(),
                        tab5.build(),
                        topRotate.topRotateCenterPos(),
                        claw.rotatePickUpPos(),
                        lift.liftDown(),
                        tab6.build(),
                        claw.wristPickUpPos(),
                        new SleepAction(.5),
                        topRotate.topRotatePickUpPos(),
                        new SleepAction(0.5),

                        claw.closeClaw(),
                        new SleepAction(0.5),
                        claw.wristDropPos(),
                        topRotate.topRotateDropPos(),
                        lift.liftUp(),
                        claw.rotateDropPos(),
                        tab7.build(),
                        claw.openClaw(),
                        tab8.build(),
                        topRotate.topRotateCenterPos(),
                        lift.liftDown(),
                        claw.wristPickUpPos(),
                        claw.rotateSidewaysPickUpPos(),
                        tab9.build(),
                        tab10.build(),
                        topRotate.topRotatePickUpPos(),
                        new SleepAction(0.5),
                        claw.closeClaw(),
                        new SleepAction(0.5),
                        tab11.build(),
                        new SleepAction(0.25),
                        topRotate.topRotateDropPos(),
                        lift.liftUp(),
                        claw.rotateDropPos(),
                        claw.wristDropPos(),
                        tab12.build(),
                        claw.openClaw(),
                        tab13.build(),
                        topRotate.topRotatePickUpPos(),
                        claw.rotateDropPos(),
                        claw.wristDropPos(),
                        lift.LiftFinal(),
                        topRotate.topRotateEnd(),
                        new SleepAction(0.1),
                        claw.wristStartPos(),
                        trajectoryActionCloseOut,
                        claw.wristStartPos(),
                        new SleepAction(1.5)


                )
        );
    }
}