package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class IntakePivot extends Subsystem {
    public static final IntakePivot INSTANCE = new IntakePivot();
    private IntakePivot() { }

    public Servo intakePivot;

    private enum intakePivot_positions {
        INTAKE (0),
        UP (0.5);
        private final double intakePivot_position;
        intakePivot_positions(double pos) {
            this.intakePivot_position = pos;
        }
        public double getPosition() {
            return intakePivot_position;
        }
    }
    public Command intake() {
        return new ServoToPosition(intakePivot,
                intakePivot_positions.INTAKE.getPosition(),
                this);
    }

    public Command transfer() {
        return new ServoToPosition(intakePivot,
                intakePivot_positions.UP.getPosition(),
                this);
    }

    @Override
    public void initialize() {
        intakePivot = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "intakePivot");
    }
}