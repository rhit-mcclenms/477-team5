package net.sf.jftp.net;

import net.sf.jftp.JFtp;
import net.sf.jftp.config.Settings;

public class FtpWatchdog {

    private long actionStartTime = -1;

    public void beginFtpAction() {
        actionStartTime = System.currentTimeMillis();
    }

    public void endFtpAction() {
        if(actionStartTime != -1) {
            long actionTime = System.currentTimeMillis() - actionStartTime;
            long watchdogGracePeriod = Settings.testTimeout;
            long watchdogTimeout = Settings.connectionTimeout;
            if(actionTime > watchdogTimeout + watchdogGracePeriod) {
                JFtp.showWatchdogWarning();
            } else {
                System.out.println("Resetting action start time");
                actionStartTime = -1;
            }
        }
    }
}
