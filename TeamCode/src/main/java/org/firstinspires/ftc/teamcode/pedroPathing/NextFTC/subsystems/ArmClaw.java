package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class ArmClaw extends Subsystem {
    public static final ArmClaw INSTANCE = new ArmClaw();
    private ArmClaw() { }

    public Servo armClaw;

    private enum armClaw_positions {
        OPEN (0),
        CLOSE(0.5);
        private final double armClaw_position;
        armClaw_positions(double pos) {
            this.armClaw_position = pos;
        }
        public double getPosition() {
            return armClaw_position;
        }
    }
    public Command open() {
        return new ServoToPosition(armClaw,
                armClaw_positions.OPEN.getPosition(),
                this);
    }

    public Command close() {
        return new ServoToPosition(armClaw,
                armClaw_positions.CLOSE.getPosition(),
                this);
    }

    @Override
    public void initialize() {
        armClaw = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "armClaw");
    }
}