// REBELLION 10014

package frc.RebeLib.math;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import frc.robot.FieldConstants;

/** Utility functions for flipping from the blue to red alliance. */
public class AllianceFlipUtil {
    public static double apply(double xCoordinate) {
        if (shouldFlip()) {
            return FieldConstants.kFieldLengthMeters - xCoordinate;
        } else {
            return xCoordinate;
        }
    }

    /** Flips a translation to the correct side of the field based on the current alliance color.
     */
    public static Translation2d apply(Translation2d translation) {
        if (shouldFlip()) {
            return new Translation2d(apply(translation.getX()), FieldConstants.kFieldLengthMeters - translation.getY());
        } else {
            return translation;
        }
    }

    /** Flips a rotation based on the current alliance color. */
    public static Rotation2d apply(Rotation2d rotation) {
        if (shouldFlip()) {
            return new Rotation2d(-rotation.getCos(), -rotation.getSin());
        } else {
            return rotation;
        }
    }

    /** Flips a pose to the correct side of the field based on the current alliance color. */
    public static Pose2d apply(Pose2d pose) {
        if (shouldFlip()) {
            return new Pose2d(apply(pose.getTranslation()), apply(pose.getRotation()));
        } else {
            return pose;
        }
    }

    public static Rotation2d apply(Rotation2d rotation, boolean flip) {
        if (flip) {
            return new Rotation2d(-rotation.getCos(), -rotation.getSin());
        } else {
            return rotation;
        }
    }

    public static Translation2d apply(Translation2d translation, boolean flip) {
        if (flip) {
            return new Translation2d(apply(translation.getX()), FieldConstants.kFieldLengthMeters - translation.getY());
        } else {
            return translation;
        }
    }

    public static Pose2d apply(Pose2d pose, boolean flip) {
        if (flip) {
            return new Pose2d(apply(pose.getTranslation(), flip), apply(pose.getRotation(), flip));
        } else {
            return pose;
        }
    }

    public static Translation3d apply(Translation3d translation3d) {
        if (shouldFlip()) {
            return new Translation3d(apply(translation3d.getX()), translation3d.getY(), translation3d.getZ());
        } else {
            return translation3d;
        }
    }

    public static boolean shouldFlip() {
        return DriverStation.getAlliance().isPresent()
                && DriverStation.getAlliance().get() == Alliance.Red;
    }
}
