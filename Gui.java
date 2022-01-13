package projet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mchange.v3.filecache.FileNotCachedException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Gui extends JFrame{

	private static final long serialVersionUID = 1L;

		public static void main(String[] args) {			
								  
			        //init window
			        JFrame frameTab = new JFrame();
				    frameTab.setTitle("PlaylistMP3");
				   
				    //init jtable[data][column]
				    String data[][]= null;    
				    String column[]={"Title","Artist","Album","Type","Year","Time"}; 				    
				    JTable tab = new JTable(new DefaultTableModel(data, column));
				    
				    //centered text in each cells
				    DefaultTableCellRenderer custom = new DefaultTableCellRenderer(); 
				    custom.setHorizontalAlignment(JLabel.CENTER); 
				    for (int i=0 ; i < tab.getColumnCount() ; i++) 
				     tab.getColumnModel().getColumn(i).setCellRenderer(custom); 
				    
				    //mains fonts and colors				    
				    Color PURP = new Color(148,0,211);
				    Color PURPLE = new Color(138,43,226);
				    Color INDIGO = new Color(75,0,130);
				    tab.setForeground(Color.WHITE);
				    tab.setBackground(PURPLE);				    
				    tab.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 20));
				    tab.getTableHeader().setBackground(PURP);
				    tab.getTableHeader().setForeground(Color.WHITE);
				    
				    //select a line for updated/removed it
				    DefaultTableModel model = (DefaultTableModel) tab.getModel();
				    tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				    
				    //search bar
				    TableRowSorter<TableModel> sort = new TableRowSorter<>(tab.getModel());
				    JTextField textField = new JTextField(20);
				    Font TEXT_FIELD_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);				    
				    textField.setFont(TEXT_FIELD_FONT);
				    textField.setForeground(Color.WHITE);
				    textField.setBackground(INDIGO);				    
				    tab.setRowSorter(sort);
			        JPanel p = new JPanel(new BorderLayout());
			        p.setBackground(INDIGO);
			        Font LABEL_FONT = new Font(Font.MONOSPACED, Font.BOLD, 20);
			        JLabel search = new JLabel("Search titles & more: ");
			        search.setForeground(Color.WHITE);
			        search.setFont(LABEL_FONT);
			        p.add(search, BorderLayout.WEST);
			        p.add(textField, BorderLayout.CENTER);
			        frameTab.setLayout(new BorderLayout());
			        frameTab.add(p, BorderLayout.NORTH);
			        
			        //scroll bar
			        JScrollPane scroll =new JScrollPane(tab);
			        frameTab.add(scroll, BorderLayout.CENTER);
			        scroll.setBackground(INDIGO);
			        
			        //filter keywords for search bar
			        textField.getDocument().addDocumentListener(new DocumentListener(){
			            public void insertUpdate(DocumentEvent e) {
			                String str = textField.getText();
			                if (str.trim().length() == 0) {
			                    sort.setRowFilter(null);
			                } else {
			                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
			                }
			            }
			            public void removeUpdate(DocumentEvent e) {
			                String str = textField.getText();
			                if (str.trim().length() == 0) {
			                    sort.setRowFilter(null);
			                } else {
			                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
			                }
			            }
			            public void changedUpdate(DocumentEvent e) {}
			        });
			         
			        //init buttons
			        JPanel conteneur_button = new JPanel();
				    frameTab.add(conteneur_button, BorderLayout.SOUTH);
				    GridLayout layout2 = new GridLayout(1, 2);
				    JButton add = new JButton("Add");
				    add.setForeground(Color.WHITE);
				    add.setBackground(INDIGO);
				    JButton update = new JButton("Update");
				    update.setForeground(Color.WHITE);
				    update.setBackground(INDIGO);
				    JButton remove = new JButton("Remove");
				    remove.setForeground(Color.WHITE);
				    remove.setBackground(INDIGO);
				    Font BUTTON_FONT = new Font(Font.DIALOG, Font.BOLD, 20);
			        add.setFont(BUTTON_FONT);
			        update.setFont(BUTTON_FONT);
			        remove.setFont(BUTTON_FONT);
				    conteneur_button.add(add);
				    conteneur_button.add(update);				  
				    conteneur_button.add(remove);
				    conteneur_button.setLayout(layout2);
				    tab.setBounds(30,40,200,300); 				    	
				    
				    //addButton action
				    add.addActionListener(new ActionListener() {				   

						public void actionPerformed(ActionEvent e) {						
							
							try {
								
							    //choose a file   
								JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());	
								chooser.setDialogTitle("Select a music");
								//select one or few musics
								chooser.setMultiSelectionEnabled(true);
								//filter								
							    chooser.setAcceptAllFileFilterUsed(false);
							    FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
							    chooser.addChoosableFileFilter(filter);
								int res = chooser.showOpenDialog(null);
								if(res == JFileChooser.APPROVE_OPTION) {
									File[] files = chooser.getSelectedFiles();	
									//read this file into InputStream
									for(File f:files){
									InputStream input = new FileInputStream(new File(f.getAbsolutePath()));
							        ContentHandler handler = new DefaultHandler();
							        Metadata metadata = new Metadata ();
							        Parser parser = new Mp3Parser ();
							        ParseContext parseCtx = new ParseContext ();
							        parser.parse(input, handler, metadata, parseCtx);
							        input.close ();	
							        //converting duration from string->ms to a string->Xmin Ys
							        String duration = metadata.get("xmpDM:duration");
								    String duréeStringMs = duration;
								    float duréeFloatMs = Float.valueOf(duréeStringMs);
								    float duréeFloatS = duréeFloatMs/1000;
								    int duréeIntS = (int)duréeFloatS;
								    int minute = (int)(duréeIntS/60);
								    int seconde = duréeIntS%60;	
								    String time = minute+ "min " +seconde+ "s"; 
								    tab.setFont(new Font(metadata.get("title"), Font.BOLD, 14));	
								    
								    //add a row
									model.addRow(new Object[]{metadata.get("title"),metadata.get("xmpDM:artist"),metadata.get("xmpDM:album"),metadata.get("xmpDM:genre"),metadata.get("xmpDM:releaseDate"),time});						   								   								    
								    
								    //Display on console			       					
							        System.out.println("\nTitle: " +metadata.get("title"));
							        System.out.println("Artist: " +metadata.get("xmpDM:artist"));
							        System.out.println("Album: " +metadata.get("xmpDM:album"));
							        System.out.println("Type: " +metadata.get("xmpDM:genre"));
							        System.out.println("Year: " +metadata.get("xmpDM:releaseDate"));
							        System.out.println("Time: " +time);
									}	
									JOptionPane.showMessageDialog(null, "Added successfully");
								}
							}catch (FileNotCachedException e1) {
								 e1.printStackTrace();
							} catch (IOException e1) {
				                e1.printStackTrace();	
							} catch (SAXException e1) {
								e1.printStackTrace();
							} catch (TikaException e1) {
								e1.printStackTrace();
							}										
						}				  	
				    });
				    
				    //updateButton action
				    update.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							try {
							    //choose a file   
								JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());							
								chooser.setDialogTitle("Select a music");
								//filter
							    chooser.setAcceptAllFileFilterUsed(false);
							    FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 Files", "mp3");
							    chooser.addChoosableFileFilter(filter);
								int res = chooser.showOpenDialog(null);
								if(res == JFileChooser.APPROVE_OPTION) {
									File file = chooser.getSelectedFile();
									//read this file into InputStream
									InputStream input = new FileInputStream(new File(file.getAbsolutePath()));
							        ContentHandler handler = new DefaultHandler();
							        Metadata metadata = new Metadata ();
							        Parser parser = new Mp3Parser ();
							        ParseContext parseCtx = new ParseContext ();
							        parser.parse(input, handler, metadata, parseCtx);
							        input.close ();	
							        //converting duration from string->ms to a string->Xmin Ys
							        String duration = metadata.get("xmpDM:duration");
								    String duréeStringMs = duration;
								    float duréeFloatMs = Float.valueOf(duréeStringMs);
								    float duréeFloatS = duréeFloatMs/1000;
								    int duréeIntS = (int)duréeFloatS;
								    int minute = (int)(duréeIntS/60);
								    int seconde = duréeIntS%60;	
								    String time = minute+ "min " +seconde+ "s"; 
								    tab.setFont(new Font(metadata.get("title"), Font.BOLD, 14));
								    
								    //fill in new elements in selected row 
							        if(tab.getSelectedRow() != -1) {				             
							        	int i = tab.getSelectedRow();
							            model.setValueAt(metadata.get("title"), i, 0);
							            model.setValueAt(metadata.get("xmpDM:artist"), i, 1);
							            model.setValueAt(metadata.get("xmpDM:album"), i, 2);							            
							            model.setValueAt(metadata.get("xmpDM:genre"), i, 3);					            
							            model.setValueAt(metadata.get("xmpDM:releaseDate"), i, 4);
							            model.setValueAt(time, i, 5);
						                JOptionPane.showMessageDialog(null, "Updated successfully");	
						             }		
							        //Display on console			       					
							        System.out.println("\nTitle: " +metadata.get("title"));
							        System.out.println("Artist: " +metadata.get("xmpDM:artist"));
							        System.out.println("Album: " +metadata.get("xmpDM:album"));
							        System.out.println("Type: " +metadata.get("xmpDM:genre"));
							        System.out.println("Year: " +metadata.get("xmpDM:releaseDate"));
							        System.out.println("Time: " +time);
								}
						} catch (FileNotCachedException e1) {
							 e1.printStackTrace();
						} catch (IOException e1) {
			                e1.printStackTrace();	
						} catch (SAXException e1) {
							e1.printStackTrace();
						} catch (TikaException e1) {
							e1.printStackTrace();
						}
						}
				    });				   				    
				    
				    //removeButton action
				    remove.addActionListener(new ActionListener(){
				    	public void actionPerformed(ActionEvent e) {
				    		//remove selected row
				    		 if(tab.getSelectedRow() != -1) {				             
				                model.removeRow(tab.getSelectedRow());
				                JOptionPane.showMessageDialog(null, "Deleted successfully");
				             }
				    	}
				    });
				    				    
				    frameTab.add(scroll); 
				    frameTab.setDefaultCloseOperation(EXIT_ON_CLOSE);
				    frameTab.setSize(1000,200);  
				    frameTab.setLocationRelativeTo(null);
				    frameTab.setVisible(true);  
		
		}
}