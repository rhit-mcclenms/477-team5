/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.sf.jftp.system.logging;

import net.sf.jftp.config.Settings;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Log
{
    private static Logger logger = new SystemLogger();
    private static Log log = new Log();
    private static String loggerName = "SystemLogger";
    private static StringBuffer cache = new StringBuffer();
    private static FileOutputStream fileOutputStream = createLogFile();

    private Log()
    {
    }

    public static void setLogger(Logger logger, String name)
    {
        Log.logger = logger;
        Log.loggerName = name;
    }

    public static void debug(String msg)
    {
        if(Settings.getDisableLog())
        {
            return;
        }

        //System.out.println(msg);
        if(null != fileOutputStream)
            writeErrorToLogFile(msg);

        logger.debug(msg);
        cache.append(msg + "\n");

        if(!Settings.getEnableDebug()) System.out.println("> " + msg);
    }

    public static void debugRaw(String msg)
    {
        if(Settings.getDisableLog())
        {
            return;
        }

        logger.debugRaw(msg);
        cache.append(msg);

        if(Settings.getEnableDebug()) System.out.print(msg);
    }

    public static void out(String msg)
    {
        if(!Settings.getEnableDebug())
        {
            return;
        }

        System.out.println("> " + msg);
    }
    
    public static void devnull(Object msg)
    {
    }

    public static void error(String msg) {
        if(Settings.getDisableLog())
        {
            return;
        }

        if(null != fileOutputStream)
            writeErrorToLogFile(msg);

        logger.error(msg);
        cache.append(msg);

        if(Settings.getEnableDebug()) System.out.print(msg);
    }

    private static FileOutputStream createLogFile() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDate =  dateFormatter.format(new Date());
        String logPath = "./logs/error_log_" + currentDate + ".txt";
        try {
            Files.createFile(Paths.get(logPath));
            FileOutputStream fos = new FileOutputStream(logPath);
            String logBanner = "##################################\n" +
                    "##                              ##\n" +
                    "##                              ##\n" +
                    "##        jFTP Error Log        ##\n" +
                    "##                              ##\n" +
                    "##   Date: " + currentDate + "  ##\n" +
                    "##                              ##\n" +
                    "##                              ##\n" +
                    "##################################\n";
            fos.write(logBanner.getBytes());
            return fos;
        } catch (IOException e) {
            System.out.println("Failed to write to log file - " + e.getMessage());
        }

        return null;
    }

    private static void writeErrorToLogFile(String msg) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms");
        String currentDate =  dateFormatter.format(new Date());
        StringBuilder logLine = new StringBuilder();
        logLine.append("[")
                .append(currentDate)
                .append("] -- ")
                .append("[")
                .append(loggerName)
                .append("] -- ")
                .append(msg)
                .append("\n");
        try {
            fileOutputStream.write(logLine.toString().getBytes());
        }catch (IOException e) {
            System.out.println("Error writing to log: - " + e.getMessage());
        }
    }


    public static String getCache()
    {
        return cache.toString();
    }

    public static void clearCache()
    {
        cache = new StringBuffer();

        try {
            fileOutputStream.flush();
        } catch (IOException e) {
            System.out.println("Failed to flush log - " + e.getMessage());
        }
    }
}
