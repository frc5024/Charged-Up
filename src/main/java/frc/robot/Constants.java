package frc.robot;

public final class Constants {
  public static class OperatorConstants {

    // Port for the driver controller
    public static final int kDriverControllerPort = 0;
  }

  public static class PneumaticConstants {
    // PneumaticHub port
    public static final int PneumaticHub = 50;

    // Sets max and min pressures for the compressor
    public static final int minPressure = 80;
    public static final int maxPressure = 120;
  }

  public static class DrawerConstants {

    // Distance that the robot thinks the drawer wall is at
    public static final int distanceWall = 400;

    // Channels that the solenoids in the drawer use
    public static final int drawerForwardChanel = 12;
    public static final int drawerReverseChanel = 13;

    // Possible value that might be read by the ultrasonic if a piece is too close
    public static final int CubeNonsenseValue = 5000;

    // Channels for the ultrasonic sensor
    public static final int usPingChanel = 5;
    public static final int usEchoChanel = 4;
  }

  public static class GripperConstants {

    // Channels that the solenoids in the gripper use
    public static final int GripperOpenChanel = 14;
    public static final int GripperCloseChanel = 15;
  }
}
