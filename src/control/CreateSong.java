package control;

import model.SongEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateSong {
	
	private static String basePath;
	private static String baseSongsResourcesPath = "/src/"; //Default
	
    private static String musicFT = ".wav";
    private static String lyricFT = ".txt";
    private static String imageFT = ".jpg";
    
    public static void main(String args[])
    {
    	
        basePath = System.getProperty("user.dir");
        String baseAndResourcePath = basePath + baseSongsResourcesPath;
        String newbaseAndResourcePath = baseAndResourcePath.replace("\\", "/");
        System.out.println(newbaseAndResourcePath);
        String lyricDefault = "zLyrics/";
        String songDefault = "zSongs/";
        String imageDefault = "zImages/";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("songentity");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        
        int i = 1;
        
        SongEntity sd1 = new SongEntity();
        SongEntity sd2 = new SongEntity();
        SongEntity sd3 = new SongEntity();
        SongEntity sd4 = new SongEntity();
        SongEntity sd5 = new SongEntity();
        SongEntity sd6 = new SongEntity();
        SongEntity sd7 = new SongEntity();
        SongEntity sd8 = new SongEntity();
        SongEntity sd9 = new SongEntity();
        SongEntity sd10 = new SongEntity();
        SongEntity sd11 = new SongEntity();
        SongEntity sd12 = new SongEntity();
        SongEntity sd13 = new SongEntity();
        sd1.setId(i);
        sd1.setTitle("Select A Song...");
        sd1.setArtist("N/A");
        sd1.setSongPath("N/A");
        sd1.setImagePath("N/A");
        sd1.setLyricPath("N/A");
        sd1.setSongPlaylistGroupID(1);
        em.persist(sd1);
        
        i++;
        sd2.setId(i);
        sd2.setTitle("String Theocracy");
        sd2.setArtist("Mili");
        sd2.setSongPath(newbaseAndResourcePath + songDefault + sd2.getTitle() + musicFT);
        sd2.setImagePath(newbaseAndResourcePath + imageDefault + sd2.getTitle() + imageFT);
        sd2.setLyricPath(newbaseAndResourcePath + lyricDefault + sd2.getTitle() + lyricFT);
        sd2.setSongPlaylistGroupID(2);
        em.persist(sd2);
        
        i++;
        sd3.setId(i);
        sd3.setTitle("From a Place of Love");
        sd3.setArtist("Mili");
        sd3.setSongPath(newbaseAndResourcePath + songDefault + sd3.getTitle() + musicFT);
        sd3.setImagePath(newbaseAndResourcePath + imageDefault + sd3.getTitle() + imageFT);
        sd3.setLyricPath(newbaseAndResourcePath + lyricDefault + sd3.getTitle() + lyricFT);
        sd3.setSongPlaylistGroupID(2);
        em.persist(sd3);
        
        i++;
  		sd4.setId(i);
  		sd4.setTitle("And Then is Heard No More");
  		sd4.setArtist("Mili");
  		sd4.setSongPath(newbaseAndResourcePath + songDefault + sd4.getTitle() + musicFT);
  		sd4.setImagePath(newbaseAndResourcePath + imageDefault + sd4.getTitle() + imageFT);
  		sd4.setLyricPath(newbaseAndResourcePath + lyricDefault + sd4.getTitle() + lyricFT);
  		sd4.setSongPlaylistGroupID(2);
  		em.persist(sd4);
  		
  		i++;
      	sd5.setId(i);
      	sd5.setTitle("Iron Lotus");
      	sd5.setArtist("Mili");
      	sd5.setSongPath(newbaseAndResourcePath + songDefault + sd5.getTitle() + musicFT);
      	sd5.setImagePath(newbaseAndResourcePath + imageDefault + sd5.getTitle() + imageFT);
      	sd5.setLyricPath(newbaseAndResourcePath + lyricDefault + sd5.getTitle() + lyricFT);
      	sd5.setSongPlaylistGroupID(2);
      	em.persist(sd5);
      	
		i++;
		sd6.setId(i);
		sd6.setTitle("Children of The City");
		sd6.setArtist("Mili");
		sd6.setSongPath(newbaseAndResourcePath + songDefault + sd6.getTitle() + musicFT);
		sd6.setImagePath(newbaseAndResourcePath + imageDefault + sd6.getTitle() + imageFT);
		sd6.setLyricPath(newbaseAndResourcePath + lyricDefault + sd6.getTitle() + lyricFT);
		sd6.setSongPlaylistGroupID(2);
		em.persist(sd6);        
		
		i++;
		sd7.setId(i);
		sd7.setTitle("Gone Angels");
		sd7.setArtist("Mili");
		sd7.setSongPath(newbaseAndResourcePath + songDefault + sd7.getTitle() + musicFT);
		sd7.setImagePath(newbaseAndResourcePath + imageDefault + sd7.getTitle() + imageFT);
		sd7.setLyricPath(newbaseAndResourcePath + lyricDefault + sd7.getTitle() + lyricFT);
		sd7.setSongPlaylistGroupID(2);
		em.persist(sd7);   
		
		i++;
		sd8.setId(i);
		sd8.setTitle("Poems of a Machine");
		sd8.setArtist("Mili");
		sd8.setSongPath(newbaseAndResourcePath + songDefault + sd8.getTitle() + musicFT);
		sd8.setImagePath(newbaseAndResourcePath + imageDefault + sd8.getTitle() + imageFT);
		sd8.setLyricPath(newbaseAndResourcePath + lyricDefault + sd8.getTitle() + lyricFT);
		sd8.setSongPlaylistGroupID(2);
		em.persist(sd8);   
      	
		i++;
		sd9.setId(i);
		sd9.setTitle("In Hell We Live, Lament");
		sd9.setArtist("Mili ft. KIHOW");
		sd9.setSongPath(newbaseAndResourcePath + songDefault + sd9.getTitle() + musicFT);
		sd9.setImagePath(newbaseAndResourcePath + imageDefault + sd9.getTitle() + imageFT);
		sd9.setLyricPath(newbaseAndResourcePath + lyricDefault + sd9.getTitle() + lyricFT);
		sd9.setSongPlaylistGroupID(3);
		em.persist(sd9);
       
		i++;
		sd10.setId(i);
		sd10.setTitle("Between Two Worlds");
		sd10.setArtist("Mili");
		sd10.setSongPath(newbaseAndResourcePath + songDefault + sd10.getTitle() + musicFT);
		sd10.setImagePath(newbaseAndResourcePath + imageDefault + sd10.getTitle() + imageFT);
		sd10.setLyricPath(newbaseAndResourcePath + lyricDefault + sd10.getTitle() + lyricFT);
		sd10.setSongPlaylistGroupID(3);
		em.persist(sd10);
		
		i++;
		sd11.setId(i);
		sd11.setTitle("Fly, My Wings");
		sd11.setArtist("Mili");
		sd11.setSongPath(newbaseAndResourcePath + songDefault + sd11.getTitle() + musicFT);
		sd11.setImagePath(newbaseAndResourcePath + imageDefault + sd11.getTitle() + imageFT);
		sd11.setLyricPath(newbaseAndResourcePath + lyricDefault + sd11.getTitle() + lyricFT);
		sd11.setSongPlaylistGroupID(3);
		em.persist(sd11);
		
		i++;
		sd12.setId(i);
		sd12.setTitle("Compass");
		sd12.setArtist("Mili");
		sd12.setSongPath(newbaseAndResourcePath + songDefault + sd12.getTitle() + musicFT);
		sd12.setImagePath(newbaseAndResourcePath + imageDefault + sd12.getTitle() + imageFT);
		sd12.setLyricPath(newbaseAndResourcePath + lyricDefault + sd12.getTitle() + lyricFT);
		sd12.setSongPlaylistGroupID(3);
		em.persist(sd12);
		
		i++;
		sd13.setId(i);
		sd13.setTitle("Through Patches of Violet");
		sd13.setArtist("Mili");
		sd13.setSongPath(newbaseAndResourcePath + songDefault + sd13.getTitle() + musicFT);
		sd13.setImagePath(newbaseAndResourcePath + imageDefault + sd13.getTitle() + imageFT);
		sd13.setLyricPath(newbaseAndResourcePath + lyricDefault + sd13.getTitle() + lyricFT);
		sd13.setSongPlaylistGroupID(3);
		em.persist(sd13);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}