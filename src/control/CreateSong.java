package control;

import model.SongEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreateSong {
	
//    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("songentity");
//    private static EntityManager em = emf.createEntityManager();
	private static String basePath;
	private static String baseSongsResourcesPath = "/src/"; //Default
//    private static SongEntity[] sd = new SongEntity[11]; //Default, NO. of Mili songs in the Project Moon Franchise
	
//	private static void CreateSD(int i, String title, String artist, int sPLGID)
//	{
//        sd[i].setId(i);
//        sd[i].setTitle("title");
//        sd[i].setArtist(artist);
//        
//        if(title != selDefault)
//        {
//        	sd[i].setPath(basePath + title);
//        	sd[i].setSongPlaylistGroupID(i);
//        }
//        else
//        {
//        	sd[i].setPath(null);
//        	sd[i].setSongPlaylistGroupID(1);
//        }
//        
//        em.persist(sd[i]);
//	}

    public static void main(String args[])
    {
        basePath = System.getProperty("user.dir");
        String baseAndResourcePath = basePath + baseSongsResourcesPath;
        String newbaseAndResourcePath = baseAndResourcePath.replace("\\", "/");
        System.out.println(newbaseAndResourcePath);
        String songPath;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("songentity");
        EntityManager em = emf.createEntityManager();
//        SongEntity[] sd = new SongEntity[11]; //Default, NO. of Mili songs in the Project Moon Franchise

        em.getTransaction().begin();
        
        int i = 1;
        
        SongEntity sd1 = new SongEntity();
        SongEntity sd2 = new SongEntity();
        SongEntity sd3 = new SongEntity();
        sd1.setId(i);
        sd1.setTitle("Select A Song...");
        sd1.setArtist("N/A");
        sd1.setPath("N/A");
        sd1.setSongPlaylistGroupID(1);
        em.persist(sd1);
        
        i++;
        sd2.setId(i);
        sd2.setTitle("String Theocracy");
        sd2.setArtist("Project Mili");
        songPath = newbaseAndResourcePath;
        sd2.setPath(songPath);
        sd2.setSongPlaylistGroupID(2);
        em.persist(sd2);
        
        i++;
        sd3.setId(i);
        sd3.setTitle("From a Place of Love");
        sd3.setArtist("Project Mili");
        songPath = newbaseAndResourcePath;
        sd3.setPath(songPath);
        sd3.setSongPlaylistGroupID(2);
        em.persist(sd3);
        
//        CreateSD(i, selDefault, null, 0);
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Select...");
//        sd[i].setArtist("N/A");
//        sd[i].setPath("N/A");
//        sd[i].setSongPlaylistGroupID(0);
//        em.persist(sd[i]);
        
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("String Theocracy");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 
//        em.persist(sd[i]);
//        System.out.println(sd[i].getTitle());
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("From a Place of Love");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("And Then is Heard No More");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Iron Lotus");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Children of The City");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Gone Angels");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Poems of a Machine");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(1); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("In Hell We Live, Lament");
//        sd[i].setArtist("Project Mili ft. KIHOW");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(2); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Between Two Worlds");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(2); 	
//        em.persist(sd[i]);
//        
//        i++;
//        sd[i].setId(i);
//        sd[i].setTitle("Fly, My Wings");
//        sd[i].setArtist("Project Mili");
//        sd[i].setPath(basePath + sd[i].getTitle());
//        sd[i].setSongPlaylistGroupID(2); 	
//        em.persist(sd[i]);
        
        em.getTransaction().commit();
        System.out.println(sd1.getPath());
        System.out.println(sd2.getPath());
        em.close();
        emf.close();
    }

}