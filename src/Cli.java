package projet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import projet.Gui;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import data.Repertoire;

/**
 * 
 * @author Command Line Interface
 *
 */
public class Cli {
	public static void main(String[] args) throws IOException, SAXException, TikaException {
		int i = 0;	
		Repertoire rep = new Repertoire();
		// no argument
		if ((args.length == 0)) {
			System.err.println("-you forgot to add an argument! \n  -(need help? -> use '-h')\n");
		}
		while (i < args.length) {
			//help
			if(args[0].equals("-h")){
				System.err.println("-you have selected help settings \n ('-f' for a file followed by file path OR '-d' for a folder followed by folder path)");
			}
			// check if its is a file or a folder
			if (args[i].equals("-f")) {
				String nom = args[i + 1];
				Extract File = new Extract(nom);
				String Title = File.getTitle(nom);
				String Artist = File.getArtist(nom);
				String Album = File.getAlbum(nom);
				String Type = File.getType(nom);
				String Year = File.getYear(nom);
				String Time = File.getTime(nom);																					

				Music music = new Music(Title, Artist, Album, Type, Year, Time);
				System.out.println(music.toString());

			} else if (args[i].equals("-d")) {

				String nom = args[i + 1];

				File dossier = new File(nom);
				
				ArrayList<String> musics = rep.parcoursRep(dossier);
				System.out.println("List files from the select folder:\n");
				
				rep.listDir(dossier);
				//DON'T WORK
				//System.out.println(dossier);
				/*
				Iterator<String> iter = fichiers.iterator();
				while (iter.hasNext()) {
					String elt = iter.next();
					if (!elt.equals(".DS_Store")) {						
						
						Extract File = new Extract(elt);
						String Title = File.getTitle(elt);
						String Artist = File.getArtist(elt);
						String Album = File.getAlbum(elt);
						String Type = File.getType(elt);
						String Year = File.getYear(elt);
						String Time = File.getTime(elt);						
						
						Music music = new Music(Title, Artist, Album, Type, Year, Time);
						System.out.println(music.toString());
					}
				}*/				
			}
			System.out.println("\n");
			i++;
		}				
	}
}
