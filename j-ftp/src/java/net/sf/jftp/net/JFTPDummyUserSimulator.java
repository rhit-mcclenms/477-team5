package net.sf.jftp.net;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;


public class JFTPDummyUserSimulator {
	public static void main(String[] args)
	{
		long startTime = System.currentTimeMillis();
		//Delay to represent user opening jFtp and entering infomation
		randomWait(15,20);
		//Login to server
		String hostName = "477-05.csse.rose-hulman.edu";
		int port = 10001;
		String username = "stewarhr";
		String password = "password123";
		FtpConnection connection = new FtpConnection(hostName,port,"<default>","<default>");
		connection.login(username, password);
		//Navigate to another local directory
		//Small delay for finding directory
		randomWait(10,15);
		connection.setLocalPath("C:/Users/stewarhr/Downloads/");
		//Upload files from directory
		randomWait(15,20);
		connection.handleUpload("E1P1SubmissionDoc (1).docx");
		randomWait(5,10);
		connection.handleUpload("E1P2Prompt.pdf");
		randomWait(5,10);
		connection.handleUpload("E-R Diagram Rubric.doc");
		//delete file from remote directory with delay to find file and hit delete
		randomWait(5,10);
		connection.removeFileOrDir("E1P1SubmissionDoc (1).docx");
		//Navigate to another local directory
		randomWait(5,10);
		connection.setLocalPath("C:/Users/stewarhr/");
		//Navigate to remote directory 
		randomWait(5,8);
		connection.chdirRaw("/test/");
		//Download file from remote directory
		randomWait(5,10);
    	connection.handleDownload("renameBloombergC5.docx");
    	randomWait(5,8);
		connection.chdirRaw("/");
		//Disconnect from server
		randomWait(10,15);
    	connection.disconnect();
    	long endTime = System.currentTimeMillis();

		long duration = (endTime - startTime);  
	}
	
	public static void randomWait(int min, int max)
	{
		 int randomDelay = min + new Random().nextInt(max - min + 1);
	        try 
	        {
	        	Thread.sleep(randomDelay*1000);
	        }
	        catch (InterruptedException e) 
	        {
	            e.printStackTrace();
	        }
	}
}
