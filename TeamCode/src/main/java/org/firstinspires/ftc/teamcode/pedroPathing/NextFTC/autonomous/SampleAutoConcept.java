package org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.autonomous;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

//Importing Subsystem Classes
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.Pedro.constants.LConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ActiveIntake;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Arm;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ArmClaw;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.AutonSequences;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.ClawPivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Extendo;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.IntakePivot;
import org.firstinspires.ftc.teamcode.pedroPathing.NextFTC.subsystems.Lift;

@Autonomous(name = "NextFTC Autonomous Program 2 Java")
public class SampleAutoConcept extends PedroOpMode {
    public SampleAutoConcept() {
        super(Extendo.INSTANCE, Lift.INSTANCE, IntakePivot.INSTANCE, ActiveIntake.INSTANCE,
                Arm.INSTANCE, ArmClaw.INSTANCE, ClawPivot.INSTANCE);
    }

    //Make Poses
    private final Pose startPose = new Pose(9, 111, Math.toRadians(270));
    private final Pose scorePose = new Pose(14, 129, Math.toRadians(315));
    private final Pose pickup1Pose = new Pose(37, 121, Math.toRadians(0));
    private final Pose pickup2Pose = new Pose(43, 130, Math.toRadians(0));
    private final Pose pickup3Pose = new Pose(49, 135, Math.toRadians(0));
    private final Pose parkPose = new Pose(60, 98, Math.toRadians(90));
    private final Pose parkControlPose = new Pose(60, 98, Math.toRadians(90));
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    public void buildPaths() {
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        park = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }

    public Command scorePreload() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scorePreload),
                        AutonSequences.scoreHigh()
                )

        );
    }
    public Command block1Cycle() {
        return new SequentialGroup(
            //Grab Block 1
            new ParallelGroup(
                    new FollowPath(grabPickup1),
                    AutonSequences.readyForPickup()
            ),
            AutonSequences.pickUp(3),

            //Score Pickup 1
            new ParallelGroup(
                    new FollowPath(scorePickup1),
                    AutonSequences.scoreHigh()
                )
            );

        }
    public Command block2Cycle() {
        return new SequentialGroup(
                //Grab Block 2
                new ParallelGroup(
                        new FollowPath(grabPickup2),
                        AutonSequences.readyForPickup()
                ),
                AutonSequences.pickUp(3),

                //Score Pickup 2
                new ParallelGroup(
                        new FollowPath(scorePickup2),
                        AutonSequences.scoreHigh()
                )
        );

    }
    public Command block3Cycle() {
        return new SequentialGroup(
                //Grab Block 3
                new ParallelGroup(
                        new FollowPath(grabPickup3),
                        AutonSequences.readyForPickup()
                ),
                AutonSequences.pickUp(3),

                //Score Pickup 1
                new ParallelGroup(
                        new FollowPath(scorePickup3),
                        AutonSequences.scoreHigh()
                )
        );

    }


    public Command park() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(park),
                        //reset all subsystems
                        Lift.INSTANCE.toDown(),
                        Extendo.INSTANCE.in(),
                        ActiveIntake.INSTANCE.idle()

                )
        );
    }
    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        ArmClaw.INSTANCE.close();
    }

    @Override
    public void onStartButtonPressed() {
        scorePreload().invoke();

        block1Cycle().invoke();
        block2Cycle().invoke();
        block3Cycle().invoke();

        park().invoke();
    }

}