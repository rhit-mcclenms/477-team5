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
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.sf.jftp.net;

import net.sf.jftp.system.logging.Log;

public class FtpClient
{
    private String name = "ftp";
    private String password = "ftp@sourceforge.net";
    private FtpConnection connection = null;

    public FtpClient()
    {
    }

    public void login(String host)
    {
        connection = new FtpConnection(host);
        try {
        connection.login(name, password);
        } 
        catch (Exception ex) {
        	Log.error(ex.toString());
        }
    }

    public void setUsername(String s)
    {
        name = s;
    }

    public void setPassword(String s)
    {
        password = s;
    }

    public void disconnect()
    {
        if(connection != null)
        {
            connection.disconnect();
        }
    }

    public void cd(String s)
    {
        if(connection != null)
        {
        	try 
        	{
        		connection.chdir(s);
        	}
        	catch (Exception ex)
        	{
        		Log.error(ex.toString());
        	}
        }
    }

    public String pwd()
    {
        if(connection != null)
        {
        	try 
        	{
        		return connection.getPWD();
        	}
        	catch (Exception ex)
        	{
        		Log.error("Error:" + ex.toString());
        		return "";
        	}
           
        }
        else
        {
            return "";
        }
    }

    public void get(String file)
    {
        if(connection != null)
        {
        	try 
        	{
        		connection.handleDownload(file);
        	}
        	catch (Exception ex)
        	{
        		Log.error("Error:" + ex.toString());
        	}
            
        }
    }

    public void put(String file)
    {
        if(connection != null)
        {
        	try 
        	{
        		connection.handleUpload(file);
        	}
        	catch (Exception ex)
        	{
        		Log.error("Error:" + ex.toString());
        	}
        }
    }
}
