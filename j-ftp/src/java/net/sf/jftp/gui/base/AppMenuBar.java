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

import javazoom.jl.player.Player;
import net.sf.jftp.JFtp;
import net.sf.jftp.config.Settings;
import net.sf.jftp.gui.framework.HImage;
import net.sf.jftp.gui.hostchooser.*;
import net.sf.jftp.gui.tasks.*;
import net.sf.jftp.net.wrappers.StartConnection;
import net.sf.jftp.system.logging.Log;
import net.sf.jftp.tools.HttpSpider;
import net.sf.jftp.util.RawConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;


//***
public class AppMenuBar extends JMenuBar implements ActionListener
{
    public static JCheckBoxMenuItem fadeMenu = new JCheckBoxMenuItem(JFtp.getMessage("base", "fadeMenu"),
                                                                     Settings.getEnableStatusAnimation());
    public static JCheckBoxMenuItem askToDelete = new JCheckBoxMenuItem(JFtp.getMessage("base", "askToDelete"),
                                                                        Settings.getAskToDelete());
    public static JCheckBoxMenuItem debug = new JCheckBoxMenuItem(JFtp.getMessage("base", "debug"),
                                                                  Settings.getEnableDebug());
    public static JCheckBoxMenuItem disableLog = new JCheckBoxMenuItem(JFtp.getMessage("base", "disableLog"),
                                                                       Settings.getDisableLog());
    public static JMenuItem clearItems = new JMenuItem(JFtp.getMessage("base", "clearItems"));
    private JFtp jftp;
    JMenu file = new JMenu(JFtp.getMessage("base", "file"));
    JMenu opt = new JMenu(JFtp.getMessage("base", "opt"));
    JMenu view = new JMenu(JFtp.getMessage("base", "view"));
    JMenu tools = new JMenu(JFtp.getMessage("base", "tools"));
    JMenu bookmarks = new JMenu(JFtp.getMessage("base", "bookmarks"));
    JMenu info = new JMenu(JFtp.getMessage("base", "info"));
    JMenu lf = new JMenu(JFtp.getMessage("base", "lf"));
    JMenu background = new JMenu(JFtp.getMessage("base", "background"));
    JMenu locale = new JMenu(JFtp.getMessage("base", "language"));
    JMenuItem en = new JMenuItem(JFtp.getMessage("base", "english"));
    JMenuItem es = new JMenuItem(JFtp.getMessage("base", "spanish"));
    JMenuItem fr = new JMenuItem(JFtp.getMessage("base", "french"));
    JMenuItem de = new JMenuItem(JFtp.getMessage("base", "german"));
    JMenuItem pt = new JMenuItem(JFtp.getMessage("base", "portuguese"));
    JMenu ftp = new JMenu(JFtp.getMessage("base", "ftp"));
    JMenu smb = new JMenu(JFtp.getMessage("base", "smb"));
    JMenu sftp = new JMenu(JFtp.getMessage("base", "sftp"));
    JMenu security = new JMenu(JFtp.getMessage("base", "security"));
    JMenu experimental = new JMenu(JFtp.getMessage("base", "experimental"));
    JMenu rss = new JMenu(JFtp.getMessage("base", "rss"));
    JMenu cnn = new JMenu(JFtp.getMessage("base", "cnn"));
    JMenuItem localFtpCon = new JMenuItem(JFtp.getMessage("base", "localFtpCon"));
    JMenuItem localSftpCon = new JMenuItem(JFtp.getMessage("base", "localSftpCon"));
    JMenuItem localSmbCon = new JMenuItem(JFtp.getMessage("base", "localSmbCon"));
    JMenuItem localNfsCon = new JMenuItem(JFtp.getMessage("base", "localNfsCon"));
    JMenuItem localWebdavCon = new JMenuItem(JFtp.getMessage("base", "localWebdavCon"));
    JMenuItem closeLocalCon = new JMenuItem(JFtp.getMessage("base", "closeLocalCon"));
    JMenuItem ftpCon = new JMenuItem(JFtp.getMessage("base", "ftpCon"));
    JMenuItem sftpCon = new JMenuItem(JFtp.getMessage("base", "sftpCon"));
    JMenuItem smbCon = new JMenuItem(JFtp.getMessage("base", "smbCon"));
    JMenuItem nfsCon = new JMenuItem(JFtp.getMessage("base", "nfsCon"));
    JMenuItem webdavCon = new JMenuItem(JFtp.getMessage("base", "webdavCon"));
    JMenuItem close = new JMenuItem(JFtp.getMessage("base", "closeFs"));
    JMenuItem exit = new JMenuItem(JFtp.getMessage("base", "exit"));
    JMenuItem readme = new JMenuItem(JFtp.getMessage("base", "readme"));
    JMenuItem changelog = new JMenuItem(JFtp.getMessage("base", "changelog"));
    JMenuItem todo = new JMenuItem(JFtp.getMessage("base", "todo"));
    JMenuItem hp = new JMenuItem(JFtp.getMessage("base", "hp"));
    JMenuItem opts = new JMenuItem(JFtp.getMessage("base", "opts"));
    JMenuItem http = new JMenuItem(JFtp.getMessage("base", "http"));
    JMenuItem raw = new JMenuItem(JFtp.getMessage("base", "raw"));
    JMenuItem spider = new JMenuItem(JFtp.getMessage("base", "spider"));
    JMenuItem shell = new JMenuItem(JFtp.getMessage("base", "shell"));
    JMenuItem manageUsers = new JMenuItem(JFtp.getMessage("base", "manageUsers"));
    JMenuItem loadAudio = new JMenuItem(JFtp.getMessage("base", "loadAudio"));
    JCheckBoxMenuItem rssDisabled = new JCheckBoxMenuItem(JFtp.getMessage("base", "rssDisabled"),
                                                          Settings.getEnableRSS());
    JCheckBoxMenuItem nl = new JCheckBoxMenuItem(JFtp.getMessage("base", "nl"),
                                                          Settings.showNewlineOption);
    JMenuItem loadSlash = new JMenuItem(JFtp.getMessage("base", "loadSlash"));
    JMenuItem loadCNN1 = new JMenuItem(JFtp.getMessage("base", "loadCNN1"));
    JMenuItem loadCNN2 = new JMenuItem(JFtp.getMessage("base", "loadCNN2"));
    JMenuItem loadCNN3 = new JMenuItem(JFtp.getMessage("base", "loadCNN3"));
    JMenuItem loadRss = new JMenuItem(JFtp.getMessage("base", "loadRss"));
    JCheckBoxMenuItem stdback = new JCheckBoxMenuItem(JFtp.getMessage("base", "stdback"),
                                                      Settings.getUseBackground());
    JCheckBoxMenuItem resuming = new JCheckBoxMenuItem(JFtp.getMessage("base", "resuming"),
                                                       Settings.enableResuming);
    JCheckBoxMenuItem ask = new JCheckBoxMenuItem(JFtp.getMessage("base", "ask"),
                                                  Settings.askToResume);
    JMenuItem proxy = new JMenuItem(JFtp.getMessage("base", "proxy"));
    JCheckBoxMenuItem smbThreads = new JCheckBoxMenuItem(JFtp.getMessage("base", "smbThreads"),
                                                         Settings.getEnableSmbMultiThreading());
    JCheckBoxMenuItem sftpThreads = new JCheckBoxMenuItem(JFtp.getMessage("base", "sftpThreads"),
                                                          Settings.getEnableSftpMultiThreading());
    JCheckBoxMenuItem sshKeys = new JCheckBoxMenuItem(JFtp.getMessage("base", "sshKeys"),
                                                      Settings.getEnableSshKeys());
    JCheckBoxMenuItem storePasswords = new JCheckBoxMenuItem(JFtp.getMessage("base", "storePasswords"),
            Settings.getStorePasswords());

    JCheckBoxMenuItem useNewIcons = new JCheckBoxMenuItem(JFtp.getMessage("base", "useNewIcons"),
            Settings.getUseNewIcons());
    
    JCheckBoxMenuItem hideHidden = new JCheckBoxMenuItem(JFtp.getMessage("base", "hideHidden"),
            Settings.getHideLocalDotNames());
    
    JMenuItem clear = new JMenuItem(JFtp.getMessage("base", "clear"));

    //*** the menu items for the last connections
    JMenuItem[] lastConnections = new JMenuItem[jftp.CAPACITY];

    //*** information on each of the last connections
    //BUGFIX
    String[][] cons = new String[jftp.CAPACITY][JFtp.CONNECTION_DATA_LENGTH];
    String[] lastConData = new String[jftp.CAPACITY];
    Character charTab = new Character('\t');
    String tab = charTab.toString();
    JMenuItem manage = new JMenuItem(JFtp.getMessage("base", "manage"));
    JMenuItem add = new JMenuItem(JFtp.getMessage("base", "add"));
    Hashtable marks;
    JMenu current = bookmarks;
    JMenu last = bookmarks;

    private long userSeverity;

    /*
    String[] lastProtocols;
    String[] lastHosts;
    String[] lastUnames;
    */
    public AppMenuBar(JFtp jftp, long userSeverity)
    {
        this.jftp = jftp;
        this.userSeverity = userSeverity;

        ftpCon.addActionListener(this);
        close.addActionListener(this);
        exit.addActionListener(this);
        readme.addActionListener(this);
        changelog.addActionListener(this);
        todo.addActionListener(this);
        resuming.addActionListener(this);
        ask.addActionListener(this);
        smbCon.addActionListener(this);
        clear.addActionListener(this);
        sftpCon.addActionListener(this);
        fadeMenu.addActionListener(this);
        askToDelete.addActionListener(this);
        smbThreads.addActionListener(this);
        sftpThreads.addActionListener(this);
        debug.addActionListener(this);
        disableLog.addActionListener(this);
        http.addActionListener(this);
        hp.addActionListener(this);
        raw.addActionListener(this);
        nfsCon.addActionListener(this);
        spider.addActionListener(this);
        proxy.addActionListener(this);
        stdback.addActionListener(this);
        opts.addActionListener(this);
        webdavCon.addActionListener(this);
        shell.addActionListener(this);
        manageUsers.addActionListener(this);
        nl.addActionListener(this);

        localFtpCon.addActionListener(this);
        localSftpCon.addActionListener(this);
        localSmbCon.addActionListener(this);
        localNfsCon.addActionListener(this);
        localWebdavCon.addActionListener(this);
        closeLocalCon.addActionListener(this);
        add.addActionListener(this);
        storePasswords.addActionListener(this);
        rssDisabled.addActionListener(this);
        loadRss.addActionListener(this);
        loadSlash.addActionListener(this);
        loadCNN1.addActionListener(this);
        loadCNN2.addActionListener(this);
        loadCNN3.addActionListener(this);
        loadAudio.addActionListener(this);
        useNewIcons.addActionListener(this);
        hideHidden.addActionListener(this);

        en.addActionListener(this);
        de.addActionListener(this);
        es.addActionListener(this);
        fr.addActionListener(this);
        pt.addActionListener(this);

        clearItems.addActionListener(JFtp.dList);

        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
                                                    ActionEvent.ALT_MASK));
        clearItems.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
                                                         ActionEvent.ALT_MASK));
        changelog.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3,
                                                        ActionEvent.ALT_MASK));
        readme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4,
                                                     ActionEvent.ALT_MASK));
        todo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5,
                                                   ActionEvent.ALT_MASK));

        //*** setMnemonics(); was here
        //*** BELOW, ADDITIONS FOR THE FILE MENU ARE PUT IN PUBLIC METHOD
        resetFileItems();

        ftp.add(resuming);
        ftp.add(ask);
        ftp.add(nl);
        smb.add(smbThreads);
        sftp.add(sftpThreads);
        sftp.add(sshKeys);

        if(userSeverity > 0) {
            security.add(askToDelete);
            security.add(storePasswords);
            opt.add(security);
            opt.addSeparator();
        }

        cnn.add(loadCNN1);
        cnn.add(loadCNN2);
        cnn.add(loadCNN3);

        rss.add(rssDisabled);
        rss.add(loadSlash);
        rss.add(cnn);
        rss.add(loadRss);

        opt.add(ftp);
        opt.add(smb);
        opt.add(sftp);
        opt.addSeparator();
        opt.add(proxy);

        if(userSeverity > 0)
            opt.add(opts);

        tools.add(http);
        tools.add(spider);
        tools.addSeparator();
        tools.add(raw);
        tools.addSeparator();
        tools.add(shell);

        if(userSeverity > 1) {
            tools.addSeparator();
            tools.add(manageUsers);
        }

        view.add(hideHidden);
        view.addSeparator();
        view.add(useNewIcons);
        view.add(fadeMenu);
        view.add(clear);
        view.add(clearItems);

        view.addSeparator();
        view.add(debug);
        view.add(disableLog);
        if(userSeverity > 0) {
            view.addSeparator();
            view.add(rss);
            view.addSeparator();
        }

        info.add(readme);
        info.add(changelog);
        //info.add(todo);
        //info.addSeparator();
        info.add(hp);

        UIManager.LookAndFeelInfo[] m = UIManager.getInstalledLookAndFeels();

        for(int i = 0; i < m.length; i++)
        {
            //JMenuItem tmp = new JMenuItem(m[i].getName());
            //tmp.addActionListener(this);
            //lf.add(tmp);

            /*
             * Don't add menu items for unsupported look and feel's.
             *
             * It would be nice to use something like
             * isSupportedLookandFeel, but the information provided by
             * UIManager.LookAndFeelInfo is very limited. This is
             * supposedly done on purpose according to the API docs,
             * but what good does a non-supported look and feel in a
             * menu item do?
             */
            try
            {
                LookAndFeel lnf = (LookAndFeel) Class.forName(m[i].getClassName())
                                                     .newInstance();

                if(lnf.isSupportedLookAndFeel())
                {
                    JMenuItem tmp = new JMenuItem(m[i].getName());
                    tmp.addActionListener(this);
                    lf.add(tmp);
                }

            }
            catch(ClassNotFoundException cnfe)
            {
                continue;
            }
            catch(InstantiationException ie)
            {
                continue;
            }
            catch(IllegalAccessException iae)
            {
                continue;
            }
        }

        view.add(lf);

        background.add(stdback);
        view.add(background);


        locale.add(en);
        locale.add(de);
        locale.add(es);
        locale.add(fr);
        locale.add(pt);
        view.add(locale);

        manage.addActionListener(this);

        //UIManager.setLookAndFeel();
        add(file);
        add(opt);
        add(view);
        if(userSeverity > 0) {
            add(tools);
        }
        add(bookmarks);
        add(info);

        loadBookmarks();

        //add(experimental);
    }

    public void loadBookmarks()
    {
        marks = new Hashtable();
        bookmarks.removeAll();
        bookmarks.add(add);
        bookmarks.add(manage);
        bookmarks.addSeparator();

        String data = "";

        try
        {
            DataInput in = new DataInputStream(new BufferedInputStream(new FileInputStream(Settings.bookmarks)));

            while((data = in.readLine()) != null)
            {
                if(!data.startsWith("#") && !data.trim().equals(""))
                {
                    addBookmarkLine(data);
                }
            }
        }
        catch(IOException e)
        {
            Log.out("No bookmarks.txt found, using defaults.");

            addBookmark("FTP", "ftp.kernel.org", "anonymous", "j-ftp@sf.net",
                        21, "/pub/linux/kernel", "false");
            addBookmark("FTP", "upload.sourceforge.net", "anonymous",
                        "j-ftp@sf.net", 21, "/incoming", "false");
            addBookmark("SMB", "(LAN)", "guest", "guest", -1, "-", "false");

            return;
        }
    }

    private void addBookmarkLine(String tmp)
    {
        try
        {
            StringTokenizer t = new StringTokenizer(tmp, "#", false);

            if(tmp.toLowerCase().trim().startsWith("<dir>"))
            {
                String dir = tmp.substring(tmp.indexOf(">") + 1,
                                           tmp.lastIndexOf("<"));

                //Log.debug("Dir: " + dir);
                JMenu m = new JMenu(dir);
                current.add(m);

                last = current;
                current = m;
            }
            else if(tmp.toLowerCase().trim().startsWith("<enddir>"))
            {
                current = last;
            }
            else
            {
                addBookmark(t.nextToken(), t.nextToken(), t.nextToken(),
                            t.nextToken(), Integer.parseInt(t.nextToken()),
                            t.nextToken(), t.nextToken());
            }
        }
        catch(Exception ex)
        {
            Log.error("Broken line: " + tmp);
            ex.printStackTrace();
        }
    }

    public void addBookmark(String pr, String h, String u, String p, int po,
                            String d, String l)
    {
        BookmarkItem x = new BookmarkItem(h);
        x.setUserdata(u, p);

        if(l.trim().startsWith("t"))
        {
            x.setLocal(true);
        }

        x.setPort(po);
        x.setProtocol(pr);
        x.setDirectory(d);

        //bookmarks
        current.add(x);
        marks.put(x.getLabel(), x);
        x.addActionListener(this);
    }

    //*** Where changes to the file menu are made (iniitalization done here too)
    public void resetFileItems()
    {
        file.removeAll();

        file.add(ftpCon);
        file.add(sftpCon);
        file.add(smbCon);
        file.add(nfsCon);

        if(userSeverity > 0) {
            file.add(webdavCon);
        }

        file.addSeparator();
        file.add(close);
        file.addSeparator();
        file.addSeparator();
        file.add(localFtpCon);
        file.add(localSftpCon);
        file.add(localSmbCon);
        file.add(localNfsCon);

        //file.add(localWebdavCon); -> not yet
        file.addSeparator();
        file.add(closeLocalCon);
        file.addSeparator();

        //*** ADDITION OF THE REMEMBERED CONNECTIONS
        boolean connectionsExist = false;

        try
        {
            //*** get the information on the last connections
            cons = new String[jftp.CAPACITY][jftp.CONNECTION_DATA_LENGTH];

            cons = LastConnections.readFromFile(jftp.CAPACITY);

            String protocol;

            String htmp;

            String utmp;

            String conNumber;
            String usingLocal = new String("");
            Integer conNumberInt;

            //lastConData = new String("");
            //***
            for(int i = 0; i < jftp.CAPACITY; i++)
            {
                if(!(cons[i][0].equals("null")))
                {
                    protocol = cons[i][0];
                    htmp = cons[i][1];
                    utmp = cons[i][2];

                    int j = 3;

                    while(!(cons[i][j].equals(LastConnections.SENTINEL)))
                    {
                        j++;
                    }

                    //usingLocal is always last piece of data!
                    usingLocal = cons[i][j - 1];

                    if(usingLocal.equals("true"))
                    {
                        usingLocal = "(in local tab)";
                    }

                    else
                    {
                        usingLocal = "";
                    }

                    //lastConnections[i] = new JMenuItem(cons[i]);
                    conNumberInt = new Integer(i + 1);
                    conNumber = conNumberInt.toString();

                    //lastConData[i] = new String(conNumber + " " + protocol + ": Hostname: " + htmp + ";  Username: " + utmp);
                    lastConData[i] = new String(conNumber + " " + protocol +
                                                ": " + htmp + " " + usingLocal);

                    lastConnections[i] = new JMenuItem(lastConData[i]);
                    lastConnections[i].addActionListener(this);

                    connectionsExist = true;

                    //*** code repetition: maybe getting these tokens
                    //*** should be in a separate private method
                    //file.add(protocol + ": Hostname: " + htmp + ";  Username: " + utmp);
                    file.add(lastConnections[i]);
                }
            }
        }
        catch(Exception ex)
        {
            Log.error("WARNING: Remembered connections broken.");
            ex.printStackTrace();
        }

        if(connectionsExist)
        {
            file.addSeparator();
        }

        file.add(exit);

        setMnemonics();
    }

    //resetFileItems
    public void actionPerformed(ActionEvent e)
    {
    	try {
            System.out.println(e.getSource().getClass());
	        if(e.getSource() == proxy)
	        {
	            //ProxyChooser p =
	            JFtp.statusP.jftp.addToDesktop(JFtp.getMessage("base", "proxySettings"),
	                                           new ProxyChooser(), 500, 110);
	        }
	        else if(e.getSource() == add)
	        {
	            Log.out("add called");
	
	            AddBookmarks a = new AddBookmarks(JFtp.statusP.jftp);
	            a.update();
	        }
	        else if(e.getSource() == webdavCon)
	        {
	            WebdavHostChooser hc = new WebdavHostChooser();
	            hc.toFront();
	            hc.update();
	        }
	        else if((e.getSource() == localFtpCon) && (!jftp.uiBlocked))
	        {
	            HostChooser hc = new HostChooser(null, true);
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == localSmbCon) && (!jftp.uiBlocked))
	        {
	            SmbHostChooser hc = new SmbHostChooser(null, true);
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == localSftpCon) && (!jftp.uiBlocked))
	        {
	            SftpHostChooser hc = new SftpHostChooser(null, true);
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == localNfsCon) && (!jftp.uiBlocked))
	        {
	            NfsHostChooser hc = new NfsHostChooser(null, true);
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == localWebdavCon) && (!jftp.uiBlocked))
	        {
	            WebdavHostChooser hc = new WebdavHostChooser(null, true);
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if(e.getSource() == closeLocalCon)
	        {
	            JFtp.statusP.jftp.closeCurrentLocalTab();
	        }
	        else if(e.getSource() == clear)
	        {
	            jftp.clearLog();
	        }
	        else if(e.getSource() == spider)
	        {
	            jftp.addToDesktop(JFtp.getMessage("base", "httpRecursive"),
	                              new HttpSpider(jftp.localDir.getPath() +
	                                             "_httpdownload/"), 440, 250);
	        }
	        else if(e.getSource() == hp)
	        {
	            try {
	                NativeHttpBrowser.main(new String[] {"http://j-ftp.sourceforge.net"});
	            }
	            catch(Throwable ex) {
	                ex.printStackTrace();
	                Log.error("Native browser intialization failed, using JContentPane...");
	
	                HttpBrowser h = new HttpBrowser("http://j-ftp.sourceforge.net");
	                JFtp.desktop.add(h, new Integer(Integer.MAX_VALUE - 10));
	            }
	        }
	        else if(e.getSource() == raw)
	        {
	            RawConnection c = new RawConnection();
	        }
	        else if(e.getSource() == readme)
	        {
	            show(Settings.readme);
	        }
	        else if(e.getSource() == changelog)
	        {
	            show(Settings.changelog);
	        }
	        else if(e.getSource() == todo)
	        {
	            show(Settings.todo);
	        }
	        else if(e.getSource() == shell)
	        {
	        	UIUtils.runCommand("/bin/bash");
	        }
            else if(e.getSource() == manageUsers)
            {
                ManageUsers mu = new ManageUsers(jftp);
            }
	        else if(e.getSource() == loadAudio)
	        {
	            try
	            {
	                JFileChooser f = new JFileChooser();
	                f.showOpenDialog(jftp);
	
	                File file = f.getSelectedFile();
	
	                Player p = new Player(new FileInputStream(file));
	
	                p.play();
	            }
	            catch(Exception ex)
	            {
	                ex.printStackTrace();
	                Log.error("Error: (" + ex + ")");
	            }
	        }
	        else if(e.getSource() == exit)
	        {
	            jftp.windowClosing(null); // handles everything
	        }
	        else if(e.getSource() == close)
	        {
	            JFtp.statusP.jftp.closeCurrentTab();
	
	            /*
	            jftp.safeDisconnect();
	            FilesystemConnection con = new FilesystemConnection();
	            jftp.remoteDir.setCon(con);
	            con.addConnectionListener((ConnectionListener)jftp.remoteDir);
	            if(!con.chdir("/")) con.chdir("C:\\");
	            */
	        }
	        else if((e.getSource() == ftpCon) && (!jftp.uiBlocked))
	        {
	            //jftp.safeDisconnect();
	            HostChooser hc = new HostChooser();
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == smbCon) && (!jftp.uiBlocked))
	        {
	            //jftp.safeDisconnect();
	            SmbHostChooser hc = new SmbHostChooser();
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == sftpCon) && (!jftp.uiBlocked))
	        {
	            //jftp.safeDisconnect();
	            SftpHostChooser hc = new SftpHostChooser();
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if((e.getSource() == nfsCon) && (!jftp.uiBlocked))
	        {
	            // jftp.safeDisconnect();
	            NfsHostChooser hc = new NfsHostChooser();
	            hc.toFront();
	
	            //hc.setModal(true);
	            hc.update();
	        }
	        else if(e.getSource() == resuming)
	        {
	            boolean res = resuming.getState();
	            Settings.enableResuming = res;
	            Settings.setProperty("jftp.enableResuming", res);
	            ask.setEnabled(Settings.enableResuming);
	            Settings.save();
	        }
	        else if(e.getSource() == useNewIcons)
	        {
	            boolean res = useNewIcons.getState();
	            Settings.setProperty("jftp.gui.look.newIcons", res);
	            Settings.save();
	            
	            JOptionPane.showMessageDialog(this, JFtp.getMessage("base", "restart"));
	        }
	        else if(e.getSource() == hideHidden)
	        {
	            boolean res = hideHidden.getState();
	            Settings.setProperty("jftp.hideHiddenDotNames", res);
	            Settings.save();
	            
	            JFtp.localUpdate();
	        }
	        else if(e.getSource() == nl)
	        {
	            boolean res = nl.getState();
	            Settings.showNewlineOption = res;
	        }
	        else if(e.getSource() == stdback)
	        {
	            Settings.setProperty("jftp.useBackground", stdback.getState());
	            Settings.save();
	            JFtp.statusP.jftp.fireUpdate();
	        }
            else if(e.getSource() == en || e.getSource() == de || e.getSource() == es || e.getSource() == fr || e.getSource() == pt) {
                String action = e.getActionCommand();
                String localeCode;
                switch (action) {
                    case "German":
                    case "Deutsch":
                    case "Alemán":
                    case "Allemand":
                    case "Alemão":
                        localeCode = "de";
                        break;
                    case "Spanish":
                    case "Spanisch":
                    case "Español":
                    case "Espagnol":
                    case "Espanhol":
                        localeCode = "es";
                        break;
                    case "French":
                    case "Französisch":
                    case "Francés":
                    case "Français":
                    case "Francês":
                        localeCode = "fr";
                        break;
                    case "Portuguese":
                    case "Portugiesisch":
                    case "Portugués":
                    case "Portugais":
                    case "Português":
                        localeCode = "pt";
                        break;
                    default:
                        localeCode = "en";
                        break;
                }

                Settings.setProperty("jftp.useLocale", localeCode);
                Settings.save();
                JFtp.statusP.jftp.resetUI();
            }
	        else if(e.getSource() == sshKeys)
	        {
	            Settings.setProperty("jftp.useSshKeyVerification",
	                                 sshKeys.getState());
	            Settings.save();
	            JFtp.statusP.jftp.fireUpdate();
	        }
	        else if(e.getSource() == rssDisabled)
	        {
	            Settings.setProperty("jftp.enableRSS", rssDisabled.getState());
	            Settings.save();
	                                   
	            JFtp.statusP.jftp.fireUpdate();
	            
	            String feed = Settings.getProperty("jftp.customRSSFeed");
	            if(feed != null && !feed.equals("")) feed = "http://slashdot.org/rss/slashdot.rss"; 
	            
	            switchRSS(feed);
	        }
	        else if(e.getSource() == loadRss)
	        {
	            String what = JOptionPane.showInputDialog("Enter URL", "http://");
	
	            if(what == null)
	            {
	                return;
	            }
	
	            switchRSS(what);
	        }
	        else if(e.getSource() == loadSlash)
	        {
	        	switchRSS("http://slashdot.org/rss/slashdot.rss");
	        }
	        else if(e.getSource() == loadCNN1)
	        {
	            switchRSS("http://rss.cnn.com/rss/cnn_topstories.rss");
	        }
	        else if(e.getSource() == loadCNN2)
	        {
	            switchRSS("http://rss.cnn.com/rss/cnn_world.rss");
	        }
	        else if(e.getSource() == loadCNN3)
	        {
	            switchRSS("http://rss.cnn.com/rss/cnn_tech.rss");
	        }
	        else if(e.getSource() == debug)
	        {
	            Settings.setProperty("jftp.enableDebug", debug.getState());
	            Settings.save();
	        }
	        else if(e.getSource() == disableLog)
	        {
	            Settings.setProperty("jftp.disableLog", disableLog.getState());
	            Settings.save();
	        }
	        else if(e.getSource() == smbThreads)
	        {
	            Settings.setProperty("jftp.enableSmbMultiThreading",
	                                 smbThreads.getState());
	            Settings.save();
	        }
	        else if(e.getSource() == sftpThreads)
	        {
	            Settings.setProperty("jftp.enableSftpMultiThreading",
	                                 sftpThreads.getState());
	            Settings.save();
	        }
	        else if(e.getSource() == ask)
	        {
	            Settings.askToResume = ask.getState();
	        }
	        else if(e.getSource() == http)
	        {
	            HttpDownloader dl = new HttpDownloader();
	            jftp.addToDesktop("Http download", dl, 480, 100);
	            jftp.setLocation(dl.hashCode(), 100, 150);
	        }
	        else if(e.getSource() == fadeMenu)
	        {
	            Settings.setProperty("jftp.gui.enableStatusAnimation",
	                                 fadeMenu.getState());
	            Settings.save();
	        }
	        else if(e.getSource() == askToDelete)
	        {
	            Settings.setProperty("jftp.gui.askToDelete", askToDelete.getState());
	            Settings.save();
	        }
	
	        //***MY ADDITIONS (***how can I make this flexible enough to
	        //*** easily add > 5 connections?)
	        else if((e.getSource() == lastConnections[0]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(0);
	        }
	
	        else if((e.getSource() == lastConnections[1]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(1);
	        }
	        else if((e.getSource() == lastConnections[2]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(2);
	        }
	        else if((e.getSource() == lastConnections[3]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(3);
	        }
	        else if((e.getSource() == lastConnections[4]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(4);
	        }
	        else if((e.getSource() == lastConnections[5]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(5);
	        }
	        else if((e.getSource() == lastConnections[6]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(6);
	        }
	        else if((e.getSource() == lastConnections[7]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(7);
	        }
	        else if((e.getSource() == lastConnections[8]) && (!jftp.uiBlocked))
	        {
	            connectionSelected(8);
	        }
	        else if(e.getSource() == opts)
	        {
	            AdvancedOptions adv = new AdvancedOptions();
	            jftp.addToDesktop("Advanced Options", adv, 500, 180);
	            jftp.setLocation(adv.hashCode(), 110, 180);
	        }
	        else if(e.getSource() == manage)
	        {
	            BookmarkManager m = new BookmarkManager();
	            JFtp.desktop.add(m, new Integer(Integer.MAX_VALUE - 10));
	        }
	        else if(marks.contains(e.getSource()))
	        {
	            ((BookmarkItem) e.getSource()).connect();
	        }
	        else if(e.getSource() == storePasswords)
	        {
	            boolean state = storePasswords.getState();
	
	            if(!state)
	            {
	                JOptionPane j = new JOptionPane();
	                int x = j.showConfirmDialog(storePasswords,
                            JFtp.getMessage("base", "savePass"),
                            JFtp.getMessage("base", "deleteOldPass"),
	                                            JOptionPane.YES_NO_OPTION);
	
	                if(x == JOptionPane.YES_OPTION)
	                {
	                    File f = new File(Settings.login_def);
	                    f.delete();
	
	                    f = new File(Settings.login_def_sftp);
	                    f.delete();
	
	                    f = new File(Settings.login_def_nfs);
	                    f.delete();
	
	                    f = new File(Settings.login_def_smb);
	                    f.delete();
	
	                    f = new File(Settings.login);
	                    f.delete();
	
	                    f = new File(Settings.last_cons);
	                    f.delete();
	
	                    Log.debug("Deleted old login data files.\n" +
	                              "Please edit your bookmarks file manually!");
	                }
	            }
	
	            Settings.setProperty("jftp.security.storePasswords", state);
	            Settings.save();
	        }
	
	        //*** END OF NEW LISTENERS
	        else
	        {
	            String tmp = ((JMenuItem) e.getSource()).getLabel();
	
	            UIManager.LookAndFeelInfo[] m = UIManager.getInstalledLookAndFeels();
	
	            for(int i = 0; i < m.length; i++)
	            {
	                if(m[i].getName().equals(tmp))
	                {
	                    JFtp.statusP.jftp.setLookAndFeel(m[i].getClassName());
	                    Settings.setProperty("jftp.gui.look", m[i].getClassName());
	                    Settings.save();
	                }
	            }
	        }
    	}
	    catch(Exception ex) {
	    	ex.printStackTrace();
	    	Log.error(ex.toString());
	    }
    }
    
    private void switchRSS(String url) {
	    Settings.setProperty("jftp.customRSSFeed", url);
		Settings.save();
		
		
		if(JFtp.statusP.jftp.feeder == null) {
			JFtp.statusP.jftp.addRSS();
		}               
		
		JFtp.statusP.jftp.feeder.switchTo(url);    
    }
    
    private void show(String file)
    {
        java.net.URL url = ClassLoader.getSystemResource(file);

        if(url == null)
        {
            url = HImage.class.getResource("/" + file);
        }

        Displayer d = new Displayer(url, null);
        JFtp.desktop.add(d, new Integer(Integer.MAX_VALUE - 11));
    }

    // by jake
    private void setMnemonics()
    {
        //*** I added accelerators for more menu items
        //*** (issue: should ALL accelerators have the CTRL modifier (so that
        //*** the ALT modifier is for mnemonics only?)
        //*** I added mnemonics for the main menu items
        file.setMnemonic('F');
        opt.setMnemonic('O');
        view.setMnemonic('V');
        tools.setMnemonic('T');
        bookmarks.setMnemonic('B');
        info.setMnemonic('I');

        //*** set accelerators for the remote connection window
        ftpCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                                                     ActionEvent.CTRL_MASK));
        sftpCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                                      ActionEvent.CTRL_MASK));
        smbCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                                                     ActionEvent.CTRL_MASK));
        nfsCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                                                     ActionEvent.CTRL_MASK));

        //*** IMPORTANT NOTE: Adding an accelerator for disconnecting could
        //*** be something of a "gotcha" that we may not want
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                                                    ActionEvent.CTRL_MASK));

        //*** These next five lines can be commented out if we decide against having accelerators
        //*** starting with the shift key
        //*** version 1.44: we have chosen not to have shift as a modifier
        //***               as we've found this is a "gotcha"
        /*
        localFtpCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                                                          ActionEvent.SHIFT_MASK));
        localSftpCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                                                           ActionEvent.SHIFT_MASK));
        localSmbCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
                                                          ActionEvent.SHIFT_MASK));
        localNfsCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                                                          ActionEvent.SHIFT_MASK));

        closeLocalCon.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                                                            ActionEvent.SHIFT_MASK));

        */
        //*** I have decided to get areound the problem by having mnemonics within the menu
        //*** as accelerators. So to quickly start an FTP connection in the local window,
        //*** the user can enter Alt+f+f, and Alt+f+s for SFTP,etc.
        localFtpCon.setMnemonic('F');
        localSftpCon.setMnemonic('S');
        localSmbCon.setMnemonic('L');
        localNfsCon.setMnemonic('N');

        //localNfsCon.setMnemonic('N');
        closeLocalCon.setMnemonic('C');

        //*** and here are some other menu mnemonics I thought I'd include:
        //*** (I'll add more if more are wanted)
        exit.setMnemonic('X');

        proxy.setMnemonic('P');

        http.setMnemonic('D');
        spider.setMnemonic('H');
        raw.setMnemonic('T');

        readme.setMnemonic('R');
        todo.setMnemonic('N');
        changelog.setMnemonic('C');
        hp.setMnemonic('H');

        opts.setMnemonic('A');
        manage.setMnemonic('M');

        clear.setMnemonic('C');
        clearItems.setMnemonic('F');

        try
        {
            //*** end of new code section
            Integer intI;
            String stringI;
            char charI;

            for(int i = 0; i < jftp.CAPACITY; i++)
            {
                //*** I should note that functionality below only allows
                //*** a maximum of nine connections to be remembered
                //BUGFIX 1.40
                //if (!(cons[i].equals("null"))) {
                if(!(cons[i][0].equals("null")))
                {
                    intI = new Integer(i + 1);
                    stringI = intI.toString();
                    charI = stringI.charAt(0);

                    lastConnections[i].setMnemonic(charI);

                    //lastConnections[i].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
                }
            }

            //for
        }
        catch(Exception ex)
        {
            Log.out("WARNING: AppMenuBar produced Exception, ignored it");
            ex.printStackTrace();
        }
    }

    //setMnemonics
    private void connectionSelected(int position)
    {
        //*** tokenize the string to extract the required data 
        //*** or is this the thing done elsewhere in the code,
        //*** when the menu is being set up?
        //String connectionInfo = cons[position]; 
        //StringTokenizer tokens = new StringTokenizer(connectionInfo);
        //StringTokenizer tokens = new StringTokenizer(cons[position], " ", false); // tab);
        String protocol;
        int numTokens;

        String htmp = new String("");
        String utmp = new String("");
        String ptmp = new String("");
        String dtmp = new String("");
        boolean useLocal = false;
        int potmp = 0;
        String potmpString = new String("0");
        String useLocalString = new String("false");

        /*
        //numTokens = tokens.countTokens();
        protocol = tokens.nextToken();

        htmp = tokens.nextToken();
        utmp = tokens.nextToken();
        ptmp = tokens.nextToken();

        */
        protocol = cons[position][0];
        htmp = cons[position][1];
        utmp = cons[position][2];
        ptmp = cons[position][3];

        if(ptmp.equals(""))
        {
            ptmp = UIUtils.getPasswordFromUser(JFtp.statusP.jftp);
        }

        //int j=4;
        //while (cons[i][j].equals(LastConnections.SENTINEL)) {
        //        j++;
        //}
        //usingLocal = cons[i][j-1];

        /*
        System.out.println(position);
        System.out.println(protocol);
        System.out.println(cons[position][1]);
        System.out.println(cons[position][2]);
        System.out.println(cons[position][3]);
        */

        //
        if(protocol.equals("FTP"))
        {
            potmpString = cons[position][4];
            dtmp = cons[position][5];
            useLocalString = cons[position][6];

            /*
            potmpString = tokens.nextToken();
            dtmp = tokens.nextToken();
            useLocalString = tokens.nextToken();


            System.out.println(potmpString);
            System.out.println("FTP");
            */
            potmp = Integer.parseInt(potmpString);

            if(useLocalString.equals("true"))
            {
                useLocal = true;
            }
            else
            {
                useLocal = false;
            }

            StartConnection.startFtpCon(htmp, utmp, ptmp, potmp, dtmp, useLocal);

            //System.out.println(htmp + utmp + ptmp + potmpString +dtmp + useLocalString);  
        }
        else if(protocol.equals("SFTP"))
        {
            /*
                    htmp = tokens.nextToken();
                    utmp = tokens.nextToken();
                    ptmp = tokens.nextToken();
            */

            //useLocalString = tokens.nextToken();
            //System.out.println("SFTP");
            potmpString = cons[position][4];
            useLocalString = cons[position][5];

            //System.out.println(htmp + utmp + ptmp  + useLocalString);
            //if (protocol == "SFTP")
        }

        else if(protocol.equals("NFS"))
        {
            useLocalString = cons[position][4];
        }

        else if(protocol.equals("SMB"))
        {
            /*
            htmp = tokens.nextToken();
            utmp = tokens.nextToken();
            ptmp = tokens.nextToken();
            */
            /*
            dtmp = tokens.nextToken();
            useLocalString = tokens.nextToken();
            */
            dtmp = cons[position][4];
            useLocalString = cons[position][5];

            //System.out.println(htmp+utmp+ptmp+dtmp + useLocalString);
        }

        //***StartConnection functionality to be put in each
        //***if statement
        potmp = Integer.parseInt(potmpString);

        if(useLocalString.equals("true"))
        {
            useLocal = true;
        }
        else
        {
            useLocal = false;
        }

        if(protocol.equals("SFTP"))
        {
            //BUGFIX 1.40: no longer setting port # 
            //to 22, now potmp
            StartConnection.startCon(protocol, htmp, utmp, ptmp, potmp, dtmp,
                                     useLocal);
        }
        else if(!(protocol.equals("FTP")))
        {
            //System.out.println(protocol);
            StartConnection.startCon(protocol, htmp, utmp, ptmp, potmp, dtmp,
                                     useLocal);
        }
    }

    //connectionSelected
}
