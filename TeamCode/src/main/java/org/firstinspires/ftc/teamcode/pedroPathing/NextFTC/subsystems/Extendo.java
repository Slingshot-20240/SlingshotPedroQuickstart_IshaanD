package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;

import java.util.List;

public class Extendo extends Subsystem {
    // BOILERPLATE
    public static final Extendo INSTANCE = new Extendo();
    private Extendo() { }

    public Servo extendoL;
    public Servo extendoR;

    private enum extendo_positions {
        IN (0.1),
        MINI_OUT (0.2),
        OUT (0.45);
        private final double extendo_position;
        extendo_positions(double pos) {
            this.extendo_position = pos;
        }
        public double getPosition() {
            return extendo_position;
        }
    }

    public Command in() {
        return new MultipleServosToPosition(
                List.of(
                        extendoL,
                        extendoR
                ),
                extendo_positions.IN.getPosition(),
                this);

    }

    public Command mini_out() {
        return new MultipleServosToPosition(
                List.of(
                        extendoL,
                        extendoR
                ),
                extendo_positions.MINI_OUT.getPosition(),
                this);
    }

    public Command out() {
        return new MultipleServosToPosition(
                List.of(
                        extendoL,
                        extendoR
                ),
                extendo_positions.OUT.getPosition(),
                this);
    }

    @Override
    public void initialize() {
        extendoL = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "extendoL");
        extendoR = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, "extendoR");

    }

}