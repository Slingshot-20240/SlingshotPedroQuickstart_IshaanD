package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class ClawPivot extends Subsystem {
    public static final ClawPivot INSTANCE = new ClawPivot();
    private ClawPivot() { }

    public Servo clawPivot;

    private enum clawPivot_positions {
        TRANSFER (0),
        SCORE (0.3);
        private final double clawPivot_position;
        clawPivot_positions(double pos) {
            this.clawPivot_position = pos;
        }
        public double getPosition() {
            return clawPivot_position;
        }
    }
    public Command toTransfer() {
        return new ServoToPosition(clawPivot,
                clawPivot_positions.TRANSFER.getPosition(),
                this);
    }

    public Command toScore() {
        return new ServoToPosition(clawPivot,
                clawPivot_positions.SCORE.getPosition(),
                this);
    }

    @Override
    public void initialize() {
        clawPivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "clawPivot");
    }
}