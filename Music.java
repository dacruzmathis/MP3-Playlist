package projet;

import java.io.Serializable;

/**
 * 
 * @author a class with all metadata of a mp3 file
 *
 */
public class Music implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String Title;
	private String Artist;
	private String Album;
	private String Type;
	private String Year;
	private String Time;
	

	public Music(String title, String artist, String album, String type, String year, String time) {
		this.Title = title;
		this.Artist = artist;
		this.Album = album;
		this.Type = type;
		this.Year = year;
		this.Time = time;		
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return Title;
	}


	/**
	 * @return the artist
	 */
	public String getArtist() {
		return Artist;
	}


	/**
	 * @return the album
	 */
	public String getAlbum() {
		return Album;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return Type;
	}


	/**
	 * @return the year
	 */
	public String getYear() {
		return Year;
	}


	/**
	 * @return the time
	 */
	public String getTime() {
		return Time;
	}


	@Override
	public String toString() {
		return "Music [Title=" + Title + ", Artist=" + Artist + ", Album=" + Album + ", Type=" + Type + ", Year=" + Year + ", Time=" + Time + "]";
	}
	

}
