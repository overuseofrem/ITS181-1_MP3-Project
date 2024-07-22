package control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.PlaylistGroupEntity;

public class PlaylistFunctions {
    private EntityManagerFactory enmafa;

    public PlaylistFunctions() {
        enmafa = Persistence.createEntityManagerFactory("playlistgroupentity");
    }

    public void createPlaylistGroup(PlaylistGroupEntity plg) {
        EntityManager enma = enmafa.createEntityManager();
        enma.getTransaction().begin();
        enma.persist(plg);
        enma.getTransaction().commit();
        enma.close();
    }
    
    public void deletePlaylistGroup(PlaylistGroupEntity plg) {
        EntityManager enma = enmafa.createEntityManager();
        enma.getTransaction().begin();
        PlaylistGroupEntity managedPLG = enma.merge(plg);
        enma.remove(managedPLG);
        enma.getTransaction().commit();
        enma.close();
    }

    public void updatePlaylistGroup(PlaylistGroupEntity plg) {
        EntityManager enma = enmafa.createEntityManager();
        enma.getTransaction().begin();
        enma.merge(plg);
        enma.getTransaction().commit();
        enma.close();
    }
    
    public List<PlaylistGroupEntity> getAllPlaylistGroup() {
        EntityManager enma = enmafa.createEntityManager();
        Query query = enma.createQuery("SELECT m FROM PlaylistGroupEntity m");
        @SuppressWarnings("unchecked")
		List<PlaylistGroupEntity> playlistGroups = query.getResultList();
        enma.close();
        return playlistGroups;
    }
}
