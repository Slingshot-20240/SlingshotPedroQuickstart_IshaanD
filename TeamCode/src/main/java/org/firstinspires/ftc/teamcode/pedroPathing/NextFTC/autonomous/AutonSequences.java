package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.autonomous;

import com.pedropathing.follower.Follower;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;

import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ActiveIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ArmClaw;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ClawPivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Lift;


//Make sure all commands are static
//Try using WaitUntil Command
public class AutonSequences {

    /**
     * Lift up, Arm scores
     */
    public static Command scoreHigh(Follower follower) {
        return new SequentialGroup(
                new ParallelGroup(
                        Lift.INSTANCE.toHighBasket(),
                        Extendo.INSTANCE.out()
                ),

                new ParallelGroup(
                        Arm.INSTANCE.toScore().and(ClawPivot.INSTANCE.toScore()),
                        IntakePivot.INSTANCE.toIntake()
                ),
                // Wait until follower finishes path before scoring block
                new WaitUntil(
                        () -> !follower.isBusy()
                ),
                ArmClaw.INSTANCE.open()
        );
    }

    /**
     * Arm, ClawPivot, and Lift ready for Transfer
     */
    public static Command readyForPickup() {
        return new SequentialGroup(
                new ParallelGroup(
                        Lift.INSTANCE.toTransfer(),
                        Arm.INSTANCE.toTransfer().and(ClawPivot.INSTANCE.toTransfer())
                )
        );
    }

    /**
     * Intake in for (seconds), then IntakePivot ready for transfer
     * @param seconds - Time the intake runs for
     */
    public static Command pickUp(double seconds) {
        return new SequentialGroup(
                ActiveIntake.INSTANCE.in(),

                new ParallelGroup(
                        ActiveIntake.INSTANCE.idle(),
                        IntakePivot.INSTANCE.toTransfer()
                ).afterTime(seconds)
        );
    }
    /**
     * Extendo in, Claw grabs
     */
    public static Command transferBlock() {
        return new SequentialGroup(
                Extendo.INSTANCE.in(),
                //Move block to claw
                new ParallelGroup(
                        moveBlockInClaw(),
                        Extendo.INSTANCE.mini_out()
                )

        );
    }
    /**
     * Pushes block towards claw for transfer
     */
    public static Command moveBlockInClaw() {
        return new SequentialGroup(
                ActiveIntake.INSTANCE.out(),
                ArmClaw.INSTANCE.close().afterTime(0.8),
                ActiveIntake.INSTANCE.idle()
        );
    }

    public static Command scorePickup3(Follower follower) {
        return new SequentialGroup(

                Lift.INSTANCE.toHighBasket(),

                Arm.INSTANCE.toScore().and(ClawPivot.INSTANCE.toScore()),
                new WaitUntil(
                        () -> !follower.isBusy()
                ),

                ArmClaw.INSTANCE.open()
        );
    }


}
