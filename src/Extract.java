package projet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *  a class that extract metadatas from mp3 file  
 */

public class Extract extends File {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String file;
	
	
/**
 * @param file   
 * @throws TikaException 
 * @throws SAXException 
 * @throws IOException 
 */
	
	
	public Extract(String file) throws IOException, SAXException, TikaException {
		super(file);
		this.file=file;    
	}
	
	public String getTitle(String file) throws IOException, SAXException, TikaException {		
			InputStream input = new FileInputStream(new File(file));
			ContentHandler handler = new DefaultHandler();
	        Metadata metadata = new Metadata ();
	        Parser parser = new Mp3Parser ();
	        ParseContext parseCtx = new ParseContext ();
	        parser.parse(input, handler, metadata, parseCtx);
	        input.close ();	
	        
	        String Title = metadata.get("title");
			return Title;	
	}

	public String getArtist(String file) throws IOException, SAXException, TikaException{
		InputStream input = new FileInputStream(new File(file));
		ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata ();
        Parser parser = new Mp3Parser ();
        ParseContext parseCtx = new ParseContext ();
        parser.parse(input, handler, metadata, parseCtx);
        input.close ();
        
		String Artist = metadata.get("xmpDM:artist");
		return Artist;
	}
	public String getAlbum(String file) throws IOException, SAXException, TikaException{
		InputStream input = new FileInputStream(new File(file));
		ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata ();
        Parser parser = new Mp3Parser ();
        ParseContext parseCtx = new ParseContext ();
        parser.parse(input, handler, metadata, parseCtx);
        input.close ();
        
		String Album = metadata.get("xmpDM:album");
		return Album;
	}	
	public String getType(String file) throws IOException, SAXException, TikaException{
		InputStream input = new FileInputStream(new File(file));
		ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata ();
        Parser parser = new Mp3Parser ();
        ParseContext parseCtx = new ParseContext ();
        parser.parse(input, handler, metadata, parseCtx);
        input.close ();
        
		String Type = metadata.get("xmpDM:genre");
		return Type;
	}	
	public String getYear(String file) throws IOException, SAXException, TikaException{
		InputStream input = new FileInputStream(new File(file));
		ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata ();
        Parser parser = new Mp3Parser ();
        ParseContext parseCtx = new ParseContext ();
        parser.parse(input, handler, metadata, parseCtx);
        input.close ();
        
		String Year = metadata.get("xmpDM:releaseDate");
		return Year;
	}	
	public String getTime(String file) throws IOException, SAXException, TikaException{
		InputStream input = new FileInputStream(new File(file));
		ContentHandler handler = new DefaultHandler();
        Metadata metadata = new Metadata ();
        Parser parser = new Mp3Parser ();
        ParseContext parseCtx = new ParseContext ();
        parser.parse(input, handler, metadata, parseCtx);
        input.close ();
        
		String duration = metadata.get("xmpDM:duration");
	    String duréeStringMs = duration;
	    float duréeFloatMs = Float.valueOf(duréeStringMs);
	    float duréeFloatS = duréeFloatMs/1000;
	    int duréeIntS = (int)duréeFloatS;
	    int minute = (int)(duréeIntS/60);
	    int seconde = duréeIntS%60;	
	    String Time = minute+ "min " +seconde+ "s";		
		return Time;
	}	
	
}
