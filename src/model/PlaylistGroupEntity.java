package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PlaylistGroupEntity {
    @Id
    private int playlistGroupID;
    private String playlistTitle;
    
    public PlaylistGroupEntity(){
        super();
    }
    
    public int getplaylistGroupID() {
    	return playlistGroupID;
    }
    
    public void setplaylistGroupID(int playlistGroupID) {
    	this.playlistGroupID = playlistGroupID;
    }
    
    public String getPlaylistTitle() {
        return playlistTitle;
    }

    public void setPlaylistTitle(String playlistTitle) {
        this.playlistTitle = playlistTitle;
    }
}
