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
import net.sf.jftp.config.Settings;
import net.sf.jftp.gui.base.dir.DirEntry;
import net.sf.jftp.gui.framework.HFrame;
import net.sf.jftp.gui.framework.HPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class ResumeDialog extends HFrame implements ActionListener
{
    private JButton resume = new JButton( JFtp.getMessage("base", "resume"));
    private JButton skip = new JButton( JFtp.getMessage("base", "skip"));
    private JButton over = new JButton( JFtp.getMessage("base", "over"));
    private DirEntry dirEntry = null;

    public ResumeDialog(DirEntry dirEntry)
    {
        this.dirEntry = dirEntry;

        setLocation(150, 150);
        setTitle( JFtp.getMessage("base", "question"));

        resume.setEnabled(false);

        JTextArea text = new JTextArea();
        text.append( JFtp.getMessage("base", "fileNamed") + " " + dirEntry.file +
                JFtp.getMessage("base", "alreadyExists") + ".                       \n\n");

        File f = new File(JFtp.localDir.getPath() + dirEntry.file);
        long diff = 0;

        diff = dirEntry.getRawSize() - f.length();

        if(diff == 0)
        {
            text.append( JFtp.getMessage("base", "sameSize") + "\n\n");
        }
        else if(diff < 0)
        {
            text.append( JFtp.getMessage("base", "bigger") + "\n\n");
        }
        else
        {
            text.append( JFtp.getMessage("base", "smaller") + "\n\n");
            resume.setEnabled(true);
        }

        getContentPane().setLayout(new BorderLayout(5, 5));
        getContentPane().add("Center", text);

        HPanel p = new HPanel();
        p.add(resume);
        p.add(skip);
        p.add(over);

        getContentPane().add("South", p);

        resume.addActionListener(this);
        skip.addActionListener(this);
        over.addActionListener(this);

        pack();
	fixLocation();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == resume)
        {
            this.dispose();
            transfer();
        }
        else if(e.getSource() == skip)
        {
            this.dispose();
        }
        else if(e.getSource() == over)
        {
            this.dispose();

            File f = new File(JFtp.localDir.getPath() + dirEntry.file);
            f.delete();

            transfer();
        }
    }

    private void transfer()
    {
        if((dirEntry.getRawSize() < Settings.smallSize) &&
               !dirEntry.isDirectory())
        {
            JFtp.remoteDir.getCon().download(dirEntry.file);
        }
        else
        {
            JFtp.remoteDir.getCon().handleDownload(dirEntry.file);
        }
    }
}
