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
package net.sf.jftp.gui.tasks;

import net.sf.jftp.JFtp;
import net.sf.jftp.config.Settings;
import net.sf.jftp.gui.framework.HPanel;
import net.sf.jftp.system.logging.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class BookmarkManager extends JInternalFrame implements ActionListener
{
    private JTextArea info = new JTextArea(25, 50);
    private JButton save = new JButton(JFtp.getMessage("tasks", "save"));
    private JButton close = new JButton(JFtp.getMessage("tasks", "close"));

    public BookmarkManager()
    {
        super(JFtp.getMessage("tasks", "manage"), true, true, true, true);
        setLocation(50, 50);
        setSize(600, 540);
        getContentPane().setLayout(new BorderLayout());

        load(Settings.bookmarks);

        JScrollPane jsp = new JScrollPane(info);
        getContentPane().add("Center", jsp);

        HPanel closeP = new HPanel();
        closeP.setLayout(new FlowLayout(FlowLayout.CENTER));

        //closeP.add(close);
        closeP.add(save);

        close.addActionListener(this);
        save.addActionListener(this);

        getContentPane().add("South", closeP);

        info.setCaretPosition(0);
        pack();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == close)
        {
            this.dispose();
        }
        else
        {
            save(Settings.bookmarks);
            JFtp.menuBar.loadBookmarks();
            this.dispose();
        }
    }

    private void setDefaultText()
    {
        info.setText("");
        info.append("# " + JFtp.getMessage("tasks", "configHeader") + "\n");
        info.append("#\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine1") + "\n");
        info.append("#\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine2") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine3") + "\n");
        info.append(JFtp.getMessage("tasks", "configLine4"));
        info.append("#\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine5") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine6") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine7") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine8") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine9") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine10") + "\n");
        info.append("# " + JFtp.getMessage("tasks", "configLine11") +
                        JFtp.getMessage("tasks", "configLine12"));
        info.append("#\n");
        info.append("#\n");
        info.append("\n<dir>JFtp</dir>\n");
        info.append("FTP#upload.sourceforge.net#anonymous#j-ftp@sf.net#21#/incoming#false\n");
        info.append("<enddir>\n");
        info.append("\n");
        info.append("FTP#ftp.kernel.org#anonymous#j-ftp@sf.net#21#/pub/linux/kernel/v2.6#false\n");
        info.append("\n");
        info.append("SMB#(LAN)#guest#guest#-1#-#false\n\n");
    }

    private void load(String file)
    {
        String data = "";
        String now = "";

        try
        {
            DataInput in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));

            while((data = in.readLine()) != null)
            {
                now = now + data + "\n";
            }
        }
        catch(IOException e)
        {
            Log.debug("No bookmarks.txt found, using defaults.");

            setDefaultText();

            return;
        }

        info.setText(now);
    }

    private void save(String file)
    {
        try
        {
            PrintStream out = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));

            out.println(info.getText());
            out.flush();
            out.close();
        }
        catch(IOException e)
        {
            Log.debug(e + " @BookmarkManager.save()");
        }
    }

    public Insets getInsets()
    {
        Insets std = super.getInsets();

        return new Insets(std.top + 5, std.left + 5, std.bottom + 5,
                          std.right + 5);
    }
}
