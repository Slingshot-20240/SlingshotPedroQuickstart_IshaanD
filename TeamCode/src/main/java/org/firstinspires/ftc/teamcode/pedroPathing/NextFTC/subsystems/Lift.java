package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.StaticFeedforward;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class Lift extends Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift() { }

    public MotorEx lift;
    //Uncomment if currentPos is needed
    //double liftPos = lift.getCurrentPosition();
    public PIDFController lift_controller = new PIDFController(0.005, 0.0, 0.0, new StaticFeedforward(0.0));
    private enum lift_positions {
        DOWN (0),
        TRANSFER (400),
        LOW_BASKET (1400),
        HIGH_BASKET(2000);
        private final int lift_position;
        lift_positions(int pos) {
            this.lift_position = pos;
        }
        public int getPosition() {
            return lift_position;
        }
    }

    public Command toDown() {
        return new RunToPosition(lift,
                lift_positions.DOWN.getPosition(),
                lift_controller,
                this);
    }
    public Command toTransfer() {
        return new RunToPosition(lift,
                lift_positions.TRANSFER.getPosition(), // TARGET POSITION, IN TICKS
                lift_controller,
                this);
    }
    public Command toLowBasket() {
        return new RunToPosition(lift,
                lift_positions.LOW_BASKET.getPosition(), // TARGET POSITION, IN TICKS
                lift_controller,
                this); //
    }
    public Command toHighBasket() {
        return new RunToPosition(lift,
                lift_positions.HIGH_BASKET.getPosition(), // TARGET POSITION, IN TICKS
                lift_controller,
                this); //
    }

    @Override
    public void initialize() {
        lift = new MotorEx("lift");
    }
}
