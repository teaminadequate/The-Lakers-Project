package guimain;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

import javax.swing.*;

import data.Database;
import guicomponents.CenterPanelDesign;
import guicomponents.CreateProjectDesign;
import guicomponents.CreateProjectPanel;
import guicomponents.FooterPanelDesign;
import guicomponents.LeftPanelDesign;
import guicomponents.ProjectInfoPanelDesign;
import guicomponents.TitlePanelDesign;


/**
 * Main controller class for the application, sets up the GUI and its related components.
 * @author Lakers Project Team
 */
public class DIYProjectMain implements Serializable{
	

	private static final long serialVersionUID = -109507167636461364L;

	
	/** APPLICATION SETTINGS */
	String myUserName;
	String myEmail;
	ImportExportHelper imp;
	Database myDatabase;
/** Version Field */
	public static double myVersion;
	
/**** FRAMES *****/
	JFrame FRAME;
	
/**** PANELS ****/
	JPanel MainPanel = new JPanel();
	static JPanel TitlePanel = new JPanel();
	static JPanel LeftPanel = new JPanel();
	static JPanel FooterPanel = new JPanel();
	static JPanel InfoPanel = new JPanel();
	static JPanel CenterPanel = new JPanel();
	static CreateProjectPanel myCreateProjectPanel;
	
/***** BUTTONS: Left Panel *****/
	JButton btnCreateProject = new JButton("Create Project");	
	JButton btnMyProject = new JButton("My Project");
	JButton btnFavoriteProject = new JButton("Favorite Project");
	JButton btnRemoveProject = new JButton("Remove Project");
	JButton btnImportProject = new JButton("Import Project");
	JButton btnExportProject = new JButton("Export Project");
	/* Menu Bar */
	JMenuBar menuBar = new JMenuBar();
	JMenu help = new JMenu("Help");
	JMenu file = new JMenu("File");
	JMenuItem about;
	
	/* Info Panel */
	JButton btnProjectInfo = new JButton("Info Panel");
	
	/***** Center Panel *****/
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Class classMain = Class.forName("DIYProjectMain");
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new DIYProjectMain();
			}
		});
	}
	
	
/**
 * 
 * This is where all the different frame objects will be added to the DIY Projects frame. 
 * 
 */
	public DIYProjectMain() {
		
		myUserName = "Not Set";
		myEmail = "Not Set";
		FRAME = new JFrame("DIY Project | " + "UserName: " + myUserName);
		imp = new ImportExportHelper();
		myDatabase = new Database();
		Version projVersion = new Version(this);
		projVersion.setVersion();
		myVersion = projVersion.getVersion();
		
		MainPanel.setLayout(new BorderLayout());
/***** TITLE PANEL *****/				
		TitlePanelDesign.addTitlePanel(MainPanel, TitlePanel);
		
/***** LEFT PANEL *****/		
		//LeftPanelDesign.addLeftPanel(MainPanel, LeftPanel, CenterPanel, CreateProjectPanel, DataPage, NorthDataPage, SouthDataPage,
		//		btnCreateProject, btnMyProject, btnFavoriteProject, btnRemoveProject, btnImportProject, btnExportProject);
		
		MainPanel.add(LeftPanel, BorderLayout.WEST);
		LeftPanel.setBackground(Color.DARK_GRAY);
		
		LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
		
	    btnCreateProject.addActionListener(new ActionListener(){  
	        public void actionPerformed(ActionEvent e){  
	                if(e.getSource() == btnCreateProject) {
	                	MainPanel.add(myCreateProjectPanel, BorderLayout.EAST);
	                	MainPanel.revalidate();
	                	MainPanel.repaint();
	                	FRAME.pack();
	                } else {
	                	System.out.println("CREATE NEW PROJECT NOT PRESSED!");
	                }
	                
	        }  
	    });  
		
		LeftPanel.add(btnCreateProject);
		LeftPanel.add(btnMyProject);
		LeftPanel.add(btnFavoriteProject);
		LeftPanel.add(btnRemoveProject);
		LeftPanel.add(btnImportProject);
		LeftPanel.add(btnExportProject);
		
		LeftPanel.add(btnProjectInfo);
		btnProjectInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnProjectInfo) {
					System.out.println("PROJECT INFO BUTTON PUSHED");
					//put your code here... 
					CenterPanelDesign.removeCenterPanel(MainPanel, CenterPanel);
					ProjectInfoPanelDesign.addProjectPanel(MainPanel, InfoPanel);
					MainPanel.revalidate();
					MainPanel.repaint();
				} else {
					System.out.println("CREATE INFO PANEL NOT PRESSED!");
				}
			}
		});
		
/***** CENTER PANEL *****/
		CenterPanelDesign.addCenterPanel(MainPanel);
		
/***** CREATE PROJECT PANEL *****/
		myCreateProjectPanel = new CreateProjectPanel(myDatabase);
		
/***** FOOTER PANEL *****/
		FooterPanelDesign.addFooterPanel(MainPanel, FooterPanel);
		
/** MENU BAR */	
		menuBar.add(file);
		
/** Info Panel */
		
		
		
		
		JMenuItem setUsername = new JMenuItem(new AbstractAction("Set/Change Username..."){
		    public void actionPerformed(ActionEvent e) {
		        myUserName = JOptionPane.showInputDialog(FRAME, "Please enter a username.");
		        FRAME.setTitle("DIY Project | " + "UserName: " + myUserName);
		        JOptionPane.showMessageDialog(FRAME, "User Name Changed Successfully!");

		    }
		});
		JMenuItem setEmail = new JMenuItem(new AbstractAction("Set/Change Email..."){
		    public void actionPerformed(ActionEvent e) {
		        myEmail = JOptionPane.showInputDialog(FRAME, "Please Enter an Email Address.");
		        JOptionPane.showMessageDialog(FRAME, "Email Changed Successfully!");
		    }
		});
		
		JMenuItem exportSettings = new JMenuItem(new AbstractAction("Export Settings..."){
		    public void actionPerformed(ActionEvent e) {
		        String fileName = JOptionPane.showInputDialog(FRAME, "Please Enter a File to export Settings to (.csv)...");      
		        imp.setFileName(fileName);
		        imp.setEmail(myEmail);
		        imp.setUserName(myUserName);
		        try {
					imp.exportSettings();
			        JOptionPane.showMessageDialog(FRAME, "Exported Settings Successfully");
				} catch (IOException e1) {

				}

		    }
		});
		
		JMenuItem importSettings = new JMenuItem(new AbstractAction("Import Settings..."){
		    public void actionPerformed(ActionEvent e) {
		        String fileName = JOptionPane.showInputDialog(FRAME, "Please Enter a File to Import Settings From.");
		        try {
					imp.importUserName(fileName);
			        myUserName = imp.myUserName;
			        myEmail = imp.myEmail;
			        JOptionPane.showMessageDialog(FRAME, "Imported Username " + myUserName + " and Email " + myEmail);
			        FRAME.setTitle("DIY Project | " + "UserName: " + myUserName);

					
				} catch (FileNotFoundException e1) {
			        JOptionPane.showMessageDialog(FRAME, "File not found, please try again!");
				}
		    }
		});
		
		file.add(setUsername);
		file.add(setEmail);
		file.add(exportSettings);
		file.add(importSettings);

		menuBar.add(help);
		 about = new JMenuItem(new AbstractAction("About..."){
			    public void actionPerformed(ActionEvent e) {
			        JOptionPane.showMessageDialog(FRAME, "Made by the Lakers Project Team!\n Version: " + myVersion);
			    }
			});
			help.add(about);
/***** FRAME *****/
		FRAME.setJMenuBar(menuBar);
		FRAME.add(MainPanel);
		FRAME.setPreferredSize(new Dimension(800, 600));
		FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FRAME.pack();
		FRAME.setLocationRelativeTo(null);
		FRAME.setVisible(true);
	}
	
	public static JPanel getCenterPanel() {
		return CenterPanel;
	}
}
