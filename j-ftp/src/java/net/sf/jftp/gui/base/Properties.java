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
package net.sf.jftp.gui.base;

import net.sf.jftp.JFtp;
import net.sf.jftp.gui.framework.HButton;
import net.sf.jftp.gui.framework.HFrame;
import net.sf.jftp.gui.framework.HPanel;
import net.sf.jftp.system.logging.Log;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Properties extends HFrame implements ActionListener
{
    private Label fileL = new Label(JFtp.getMessage("base", "fileL"));
    private Label sizeL = new Label(JFtp.getMessage("base", "sizeL") + "? bytes              ");
    private HButton ok = new HButton(JFtp.getMessage("base", "ok"));
    private HPanel okP = new HPanel();
    private String type = "";
    private String file = "";

    public Properties(String file, String type)
    {
        this.file = file;
        this.type = type;

        setSize(300, 110);
        setTitle(JFtp.getMessage("base", "fileProps"));
        setLocation(150, 150);
        setLayout(new GridLayout(3, 1));

        okP.add(ok);
        add(sizeL);
        add(fileL);
        add(okP);
        ok.addActionListener(this);

        process();
        setVisible(true);
    }

    private void process()
    {
        if(type.equals("local"))
        {
            File f = new File(JFtp.localDir.getPath() + file);
            sizeL.setText("Size: " + Long.toString(f.length()) + " bytes");

            try
            {
                fileL.setText("File: " + f.getCanonicalPath());
            }
            catch(Exception ex)
            {
                Log.error(ex.toString());
            }

            sizeL.setText("Size: " + Long.toString(f.length()) + " bytes");
        }

        if(type.equals("remote"))
        {
            fileL.setText("File: " + JFtp.remoteDir.getPath() + file);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == ok)
        {
            setVisible(false);
        }
    }
}
