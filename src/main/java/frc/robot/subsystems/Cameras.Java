package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;

public class Cameras extends SubsystemBase{
    private static Drawer mInstance = null;

     // Makes it a singleton
     public static Drawer getInstance() {
        if (mInstance == null) {
            mInstance = new Drawer();
        }
        return mInstance;
    }

    m_visionThread =

    new Thread(

        () -> {

          // Get the UsbCamera from CameraServer

          UsbCamera camera = CameraServer.startAutomaticCapture();

          // Set the resolution

          camera.setResolution(640, 480);


          // Get a CvSink. This will capture Mats from the camera

          CvSink cvSink = CameraServer.getVideo();


          // Mats are very memory expensive. Lets reuse this Mat.

          Mat mat = new Mat();


          // This cannot be 'true'. The program will never exit if it is. This

          // lets the robot stop this thread when restarting robot code or

          // deploying.

          while (!Thread.interrupted()) {

            // Tell the CvSink to grab a frame from the camera and put it

            // in the source mat.  If there is an error notify the output.

            if (cvSink.grabFrame(mat) == 0) {

              // Send the output the error.

              outputStream.notifyError(cvSink.getError());

              // skip the rest of the current iteration

              continue;

            }


        });

m_visionThread.setDaemon(true);

m_visionThread.start();

    @Override
    Public void periodic(){

    }
}