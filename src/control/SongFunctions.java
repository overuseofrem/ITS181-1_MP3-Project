package control;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

import model.SongEntity;

public class SongFunctions {
    private EntityManagerFactory enmafa;

    public SongFunctions() {
        enmafa = Persistence.createEntityManagerFactory("songentity");
    }

    public void createSongData(SongEntity sd) {
        EntityManager enma = enmafa.createEntityManager();
        enma.getTransaction().begin();
        enma.persist(sd);
        enma.getTransaction().commit();
        enma.close();
    }
    
    public void deleteSongData(SongEntity sd) {
        EntityManager enma = enmafa.createEntityManager();
        enma.getTransaction().begin();
        SongEntity managedSD = enma.merge(sd);
        enma.remove(managedSD);
        enma.getTransaction().commit();
        enma.close();
    }
    
    public List<SongEntity> getAllSongData() {
        EntityManager enma = enmafa.createEntityManager();
        Query query = enma.createQuery("SELECT m FROM SongEntity m");
        @SuppressWarnings("unchecked")
		List<SongEntity> songDatas = query.getResultList();
        enma.close();
        return songDatas;
    }
}