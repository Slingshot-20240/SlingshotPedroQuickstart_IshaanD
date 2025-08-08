package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.teleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ActiveIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ArmClaw;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ClawPivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Lift;

@TeleOp(name = "NextFTC TeleOp Program Java")
public class TeleOpTest extends NextFTCOpMode {

    public TeleOpTest() {
        super(Extendo.INSTANCE, Lift.INSTANCE, ActiveIntake.INSTANCE);
    }


    public MotorEx leftFront, rightFront, leftBack, rightBack;
    public MotorEx[] motors;
    public Command driverControlled;


    @Override
    public void onInit() {
        leftFront = new MotorEx("leftFront");
        leftBack = new MotorEx("leftBack");
        rightBack = new MotorEx("rightBack");
        rightFront = new MotorEx("rightFront");

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[] {leftFront, rightFront, leftBack, rightBack};
    }
    @Override
    public void onStartButtonPressed() {

        //////////**********  Gamepad 1  **********\\\\\\\\\\
        //Robot centric == true
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(),true);
        driverControlled.invoke();

        //Extendo out Lift down Arm transfer Claw Transfer
        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(
                () -> new ParallelGroup(
                        Lift.INSTANCE.toTransfer(),
                        Arm.INSTANCE.toTransfer(),
                        ClawPivot.INSTANCE.toTransfer(),
                        ArmClaw.INSTANCE.open(),

                        Extendo.INSTANCE.out()
                )
        );
        //Extendo in
        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(
                Extendo.INSTANCE::in
        );

        //Intake in
        gamepadManager.getGamepad1().getRightTrigger().setPressedCommand(
                value -> new ParallelGroup(
                        ActiveIntake.INSTANCE.in(),
                        IntakePivot.INSTANCE.toIntake()
                )
        );
        gamepadManager.getGamepad2().getRightTrigger().setReleasedCommand(
                value -> new ParallelGroup(
                        ActiveIntake.INSTANCE.idle(),
                        IntakePivot.INSTANCE.toTransfer()
                )
        );

        //////////**********  Gamepad 2  **********\\\\\\\\\\

        //Lift to high basket
        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(
                () -> new SequentialGroup(
                        Lift.INSTANCE.toHighBasket(),
                        new ParallelGroup(
                                Arm.INSTANCE.toScore(),
                                ClawPivot.INSTANCE.toScore(),
                                Extendo.INSTANCE.in()
                        )
                )
        );
        //Lift to low basket
        gamepadManager.getGamepad2().getLeftTrigger().setPressedCommand(
                value -> new SequentialGroup(
                        Lift.INSTANCE.toLowBasket(),
                        new ParallelGroup(
                                Arm.INSTANCE.toScore(),
                                ClawPivot.INSTANCE.toScore(),
                                Extendo.INSTANCE.in()
                        )
                )
        );
        //Open claw
        gamepadManager.getGamepad2().getA().setPressedCommand(
                () -> new SequentialGroup(
                        ArmClaw.INSTANCE.open()
                )
        );




/*
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(Lift.INSTANCE::toScore);
        gamepadManager.getGamepad2().getDpadUp().setReleasedCommand(Extendo.INSTANCE::out);

 */

    }
}