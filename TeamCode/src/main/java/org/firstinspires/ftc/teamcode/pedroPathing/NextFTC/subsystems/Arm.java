package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Arm extends Subsystem {
    public static final Arm INSTANCE = new Arm();
    private Arm() { }

    public Servo arm;

    private enum arm_positions {
        TRANSFER (0),
        SCORE (0.6);
        private final double arm_position;
        arm_positions(double pos) {
            this.arm_position = pos;
        }
        public double getPosition() {
            return arm_position;
        }
    }
    public Command toTransfer() {
        return new ServoToPosition(arm,
                arm_positions.TRANSFER.getPosition(),
                this);
    }

    public Command toScore() {
        return new ServoToPosition(arm,
                arm_positions.SCORE.getPosition(),
                this);
    }

    @Override
    public void initialize() {
        arm = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "arm");
    }
}