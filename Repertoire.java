package projet;

import java.io.File;
import java.util.ArrayList;

/**
 * 
 * @author repertory class
 *
 */
public class Repertoire {
	/**
	 * ArrayList for music
	 */
	private ArrayList<Music> musics = new ArrayList<Music>();

	/**
	 *  add music in ArrayList
	 * 
	 * @param file un Fichier
	 */
	public void add(Music file) {
		if (!musics.contains(file)) {
			musics.add(file);
		}
	}

	/** 
	 * @return ArrayList with musics
	 */
	protected ArrayList<Music> getFichiers() {
		return musics;
	}

	/**
	 * list the repertory
	 * 
	 * @param dossier
	 * @return elements from dossier
	 */
	public ArrayList<String> parcoursRep(File dossier) {
		ArrayList<String> fichiers = new ArrayList<>();
		File[] liste = dossier.listFiles();
		for (File elt : liste) {
			if (elt.isFile()) {
				fichiers.add(elt.getName());
			}
			if (elt.isDirectory()) {
				File[] liste2 = elt.listFiles();
				for (File element : liste2) {
					if (element.isFile()) {
						fichiers.add(element.getName());
					} else {
						fichiers.addAll(parcoursRep(element));
					}
				}
			}
		}
		return fichiers;
	}
	
	/**
	 * display all paths to extract
	 */
	
	public void listDir(File file) {
		if (file.isDirectory()) {
			File results [] = file.listFiles();
			if (results != null) {
				for (int x=0; x<results.length; x++) {
					listDir(results[x]);
				}
			}
		}
		System.out.println(file);
	}
	/**
	 * display all paths from the ArrayList
	 */
	public String toString() {
		String result = "";
		for (Music music : getFichiers()) {
			result += music.toString() + "\n";
		}
		return result;
	}

}
