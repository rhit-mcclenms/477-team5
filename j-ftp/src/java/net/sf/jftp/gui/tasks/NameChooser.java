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
import net.sf.jftp.gui.framework.HButton;
import net.sf.jftp.gui.framework.HFrame;
import net.sf.jftp.gui.framework.HPanel;
import net.sf.jftp.gui.framework.HTextField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class NameChooser extends HFrame implements ActionListener
{
    public HTextField text;
    private HButton ok = new HButton(JFtp.getMessage("tasks", "okSimple"));
    private HPanel okP = new HPanel();

    public NameChooser()
    {
        //setSize(400, 80);
        setTitle(JFtp.getMessage("tasks", "enterName"));
        //setLocation(150, 150);
        getContentPane().setLayout(new FlowLayout());

        text = new HTextField(JFtp.getMessage("tasks", "fileName") + ": ", "tmp.zip");
        getContentPane().add(text);
        getContentPane().add(ok);
        ok.addActionListener(this);
        text.text.addActionListener(this);

        pack();
	fixLocation();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if((e.getSource() == ok) || (e.getSource() == text.text))
        {
            setVisible(false);
        }
    }
}
