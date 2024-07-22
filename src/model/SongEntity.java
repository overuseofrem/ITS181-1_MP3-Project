package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SongEntity {
    @Id
    private int id;
    private String title;
    private String artist;
    private String path;
    private int songPlaylistGroupID;
    
    public SongEntity(){
        super();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public int getSongPlaylistGroupID() {
    	return songPlaylistGroupID;
    }
    
    public void setSongPlaylistGroupID(int songPlaylistGroupID) {
    	this.songPlaylistGroupID = songPlaylistGroupID;
    }

}
