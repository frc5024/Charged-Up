// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class PneumaticConstants {
    public static final int PneumaticHub = 50;
    public static final int minPressure = 80;
    public static final int maxPressure = 120;
  }

  public static class DrawerConstants {
    public static final int distanceWall = 200;
    public static final int drawerForwardChanel = 13;
    public static final int drawerReverseChanel = 12;
    // sometimes the ultrasonic reads a nonsense distance value when it sees the
    // cube
    // this stores it for a logic statement used in the drawer code
    public static final int CubeNonsenseValue = 5000;
    public static final int usPingChanel = 6;
    public static final int usEchoChanel = 7;
  }

  public static class GripperConstants {
    public static final int GripperOpenChanel = 14;
    public static final int GripperCloseChanel = 15;
  }
}
