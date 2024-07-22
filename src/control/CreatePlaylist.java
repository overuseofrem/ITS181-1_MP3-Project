package control;

import model.PlaylistGroupEntity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CreatePlaylist {

    public static void main(String args[])
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("playlistgroupentity");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        int i = 1;
        
//        PlaylistGroupEntity[] pl = new PlaylistGroupEntity[3];
        PlaylistGroupEntity plg1 = new PlaylistGroupEntity();
        PlaylistGroupEntity plg2 = new PlaylistGroupEntity();
        PlaylistGroupEntity plg3 = new PlaylistGroupEntity();
        
        plg1.setplaylistGroupID(i);
        plg1.setPlaylistTitle("Select a Playlist...");
        em.persist(plg1);
        
        i++;
        plg2.setplaylistGroupID(i);
        plg2.setPlaylistTitle("Library of Ruina");
        em.persist(plg2);
        
        i++;
        plg3.setplaylistGroupID(i);
        plg3.setPlaylistTitle("Limbus Company");
        em.persist(plg3);
        
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}