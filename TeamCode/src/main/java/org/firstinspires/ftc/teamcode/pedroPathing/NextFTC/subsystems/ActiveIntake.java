package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

public class ActiveIntake extends Subsystem {
    public static final ActiveIntake INSTANCE = new ActiveIntake();
    private ActiveIntake() { }

    public MotorEx activeIntake;

    //Remove intake PID Controller - Not necessary bc ur just setting power when creating Command Functions.
    //You aren't using run to position 
    //public PIDFController activeIntake_controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));
    private enum activeIntake_states {
        IN (0.7),
        IDLE (0),
        OUT (-0.7);
        private final double activeIntake_state;
        activeIntake_states(double state) {
            this.activeIntake_state = state;
        }
        public double getState() {
            return activeIntake_state;
        }
    }



    public Command in() {
        return new SetPower(activeIntake, activeIntake_states.IN.getState(), this);
    }
    public Command idle() {
        return new SetPower(activeIntake, activeIntake_states.IDLE.getState(), this);
    }
    public Command out() {
        return new SetPower(activeIntake, activeIntake_states.OUT.getState(), this);
    }


    @Override
    public void initialize() {
        activeIntake = new MotorEx("activeIntake");
    }
}
