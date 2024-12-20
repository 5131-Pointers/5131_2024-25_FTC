package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "CENTERSTAGE Simple Auto", group = "Autonomous")
public class CenterstageSimpleAuto extends LinearOpMode {

    public class Lift {
        private DcMotorEx RLIFT;
        private DcMotorEx LLIFT;

        public Lift() {
            RLIFT = hardwareMap.get(DcMotorEx.class, "RLIFT");
            LLIFT = hardwareMap.get(DcMotorEx.class, "LLIFT");

            RLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LLIFT.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            LLIFT.setDirection(DcMotorSimple.Direction.REVERSE); // Reverse to match the lift setup
        }

        public void moveToPosition(int targetPosition) {
            RLIFT.setTargetPosition(targetPosition);
            LLIFT.setTargetPosition(targetPosition);


            RLIFT.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            LLIFT.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

            RLIFT.setPower(0.8);
            LLIFT.setPower(0.8);

            while (RLIFT.isBusy() && LLIFT.isBusy() && opModeIsActive()) {
                telemetry.addData("RLIFT Pos", RLIFT.getCurrentPosition());
                telemetry.addData("LLIFT Pos", LLIFT.getCurrentPosition());
                telemetry.update();
            }

            RLIFT.setPower(0);
            LLIFT.setPower(0);
        }
    }

    @Override
    public void runOpMode() {
        // Initialize the drive and lift mechanisms
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Lift lift = new Lift();

        // Define trajectory
        TrajectoryActionBuilder trajectory = drive.actionBuilder(initialPose)
                .lineToY(37)
                .setTangent(Math.toRadians(0))
                .lineToX(18);

        telemetry.addLine("Waiting for start...");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        // Move to position (50, 50)
        Actions.runBlocking(trajectory.build());

        // Move lifts to encoder position 500
        lift.moveToPosition(500);

        telemetry.addLine("Autonomous Complete!");
        telemetry.update();
    }
}