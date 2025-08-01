package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC;

import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;

import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ActiveIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ArmClaw;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ClawPivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Lift;

//Make sure all commands are static
public class AutonSequences {

    /**
     * Lift up, Arm scores
     */
    public static Command scoreHigh() {
        return new SequentialGroup(
                Lift.INSTANCE.toHighBasket(),

                new ParallelGroup(
                        Arm.INSTANCE.toScore(),
                        ClawPivot.INSTANCE.toScore(),
                        //Extended - Intake Ready
                        Extendo.INSTANCE.out()
                ),
                new ParallelGroup(
                        ArmClaw.INSTANCE.open(),
                        IntakePivot.INSTANCE.intake()
                )
        );
    }

    /**
     * Arm, ClawPivot, and Lift ready for Transfer
     */
    public static Command readyForPickup() {
        return new SequentialGroup(
                new ParallelGroup(
                        Arm.INSTANCE.toTransfer(),
                        ClawPivot.INSTANCE.toTransfer(
                ),
                Lift.INSTANCE.toTransfer()
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
                new Delay(seconds),
                new ParallelGroup(
                        ActiveIntake.INSTANCE.idle(),
                        IntakePivot.INSTANCE.transfer()
                )
        );
    }
    /**
     * Extendo in, Claw grabs
     */
    public static Command transferBlock() {
        return new SequentialGroup(
                new SequentialGroup(
                        Extendo.INSTANCE.in(),
                        //Move block to claw
                        new ParallelGroup(
                                moveBlockToClaw(),
                                ArmClaw.INSTANCE.close(),
                                Extendo.INSTANCE.mini_out()
                        )
                )

        );
    }
    /**
     * Pushes block towards claw for transfer
     */
    public static Command moveBlockToClaw() {
        return new SequentialGroup(
                ActiveIntake.INSTANCE.out(),
                new Delay(0.4),
                ActiveIntake.INSTANCE.idle()
        );
    }





}
