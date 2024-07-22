package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import control.SongFunctions;
import control.PlaylistFunctions;
import model.PlaylistGroupEntity;
import model.SongEntity;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import java.awt.TextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.TextField;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MusicPlayer extends JFrame {

    private SongFunctions songFunctions;
    private PlaylistFunctions playlistGroupFunctions;
	private static final long serialVersionUID = 1L;
	private JPanel splJPanel;
    @SuppressWarnings("rawtypes")
	private JComboBox playlistCB;
    private TextField artistTF;
    private JLabel backLabel;
    private JLabel labelPlayORPause;
    private JLabel nextLabel;
    private TextArea lyricsTA;
    private JLabel songPic;
    @SuppressWarnings("rawtypes")
	private JComboBox songCB;
    
	private int currentPlaylistGroupID = 1;
	private int currentSongID = 1;
    private String currentArtist;
    private int counterForRefresh = 0;
    
	private String currentLyricPath;
	private String currentImagePath;
	private String currentSongPath;
	
	private Boolean isPlaying = false;
    
    private List<SongEntity> songDatas;
    private List<PlaylistGroupEntity> playlistGroupEntities;
    
    private Clip clip;
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MusicPlayer frame = new MusicPlayer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes" })
	public MusicPlayer() {
		songFunctions = new SongFunctions();
		playlistGroupFunctions = new PlaylistFunctions();
		
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicPlayer.class.getResource("/zImages/ProjectMili.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 600);
		splJPanel = new JPanel();
		splJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(splJPanel);
		GridBagLayout gbl_splJPanel = new GridBagLayout();
		gbl_splJPanel.columnWidths = new int[]{160, 460, 0};
		gbl_splJPanel.rowHeights = new int[] {40, 0, 0};
		gbl_splJPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_splJPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		splJPanel.setLayout(gbl_splJPanel);
		
		JPanel cbPanel = new JPanel();
		GridBagConstraints gbc_cbPanel = new GridBagConstraints();
		gbc_cbPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_cbPanel.anchor = GridBagConstraints.NORTH;
		gbc_cbPanel.insets = new Insets(0, 0, 5, 5);
		gbc_cbPanel.gridx = 0;
		gbc_cbPanel.gridy = 0;
		splJPanel.add(cbPanel, gbc_cbPanel);
		cbPanel.setLayout(new BoxLayout(cbPanel, BoxLayout.Y_AXIS));
		
		// Action Playlist
		playlistCB = new JComboBox();
		playlistCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				counterForRefresh = 0;
				currentPlaylistGroupID = checkcurrentPlaylistID();
				counterForRefresh++;
				refreshSDCB();
			}
		});
		playlistCB.setFont(new java.awt.Font("Baker Signet Std", 1, 12));
		cbPanel.add(playlistCB);
		
		// Action Songs
		songCB = new JComboBox();
		songCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(currentPlaylistGroupID != 1 && counterForRefresh == 2)
				{
					labelPlayORPause.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/play.png")));
					isPlaying = false;
					songPlayORPause();
					checkSongDetails();
					checkCurrentSongValues();
				}
				else
				{
					counterForRefresh++;
				}
			}
		});
		songCB.setFont(new Font("Dialog", Font.BOLD, 12));
		cbPanel.add(songCB);
		
		artistTF = new TextField();
		artistTF.setFont(new Font("Dialog", Font.BOLD, 12));
		artistTF.setEditable(false);
		cbPanel.add(artistTF);
		
		JPanel controlsPanel = new JPanel();
		GridBagConstraints gbc_controlsPanel = new GridBagConstraints();
		gbc_controlsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_controlsPanel.gridx = 1;
		gbc_controlsPanel.gridy = 0;
		splJPanel.add(controlsPanel, gbc_controlsPanel);
		controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
		
		backLabel = new JLabel();
		backLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/back.png")));
		controlsPanel.add(backLabel);
		
		JLabel emptyLabel1 = new JLabel();
		emptyLabel1.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/empty.png")));
		controlsPanel.add(emptyLabel1);
		
		labelPlayORPause = new JLabel();
		labelPlayORPause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				songPlayORPause();
			}
		});
		
		labelPlayORPause.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/play.png")));
		labelPlayORPause.setAlignmentX(0.5f);
		controlsPanel.add(labelPlayORPause);
		
		JLabel emptyLabel2 = new JLabel();
		emptyLabel2.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/empty.png")));
		controlsPanel.add(emptyLabel2);
		
		nextLabel = new JLabel();
		nextLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/next.png")));
		controlsPanel.add(nextLabel);
		
		JPanel taPanel = new JPanel();
		GridBagConstraints gbc_taPanel = new GridBagConstraints();
		gbc_taPanel.insets = new Insets(0, 0, 0, 5);
		gbc_taPanel.fill = GridBagConstraints.VERTICAL;
		gbc_taPanel.gridx = 0;
		gbc_taPanel.gridy = 1;
		splJPanel.add(taPanel, gbc_taPanel);
		taPanel.setLayout(new BoxLayout(taPanel, BoxLayout.Y_AXIS));
		
		lyricsTA = new TextArea();
		lyricsTA.setFont(new java.awt.Font("Baker Signet Std", 1, 12));
		lyricsTA.setEditable(false);
		taPanel.add(lyricsTA);
		
		JPanel picPanel = new JPanel();
		GridBagConstraints gbc_picPanel = new GridBagConstraints();
		gbc_picPanel.fill = GridBagConstraints.BOTH;
		gbc_picPanel.gridx = 1;
		gbc_picPanel.gridy = 1;
		splJPanel.add(picPanel, gbc_picPanel);
		picPanel.setLayout(new BoxLayout(picPanel, BoxLayout.X_AXIS));
		
		songPic = new JLabel();
		songPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zImages/ProjectMili.png")));
		picPanel.add(songPic);
		
	    songDatas = songFunctions.getAllSongData();
	    playlistGroupEntities = playlistGroupFunctions.getAllPlaylistGroup();
	    
	    artistTF.setText("All Project Moon X Mili Songs");
	    
		refreshPLGCB();
		refreshSDCB();
	}
	
	private void songPlayORPause()
	{
		if(clip != null)
		{
			if(!isPlaying)
			{
				labelPlayORPause.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/pause.png")));
				isPlaying = true;
				clip.start();
			}
			else
			{
				labelPlayORPause.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/play.png")));
				isPlaying = false;
				clip.stop();
			}
		}
		else
		{
			return;
		}
	}
	
	private int checkcurrentPlaylistID()
	{
		String selectedPlaylist = playlistCB.getSelectedItem().toString();
		int plgID = 1;
        for (PlaylistGroupEntity playlistGroupEntity : playlistGroupEntities) {
        	String fcplgTitle = playlistGroupEntity.getPlaylistTitle();
        	if(fcplgTitle != selectedPlaylist)
        		continue;
        	else
        	{
        		plgID = playlistGroupEntity.getplaylistGroupID();
        		break;
        	}
        }
        return plgID;
	}
	
	private void checkSongDetails()
	{
		String selectedSong = songCB.getSelectedItem().toString();
	    for (SongEntity songData : songDatas) {
	    	String songTitle = songData.getTitle();
	    	if(songTitle == selectedSong)
	    	{
	    		currentSongID = songData.getId();
	    		currentArtist = songData.getArtist();
	    		currentSongPath = songData.getSongPath();
	    		currentLyricPath = songData.getLyricPath();
	    		currentImagePath = songData.getImagePath();
	    		break;
	    	}
	    	else
	    	{
	    		continue;
	    	}
	    }
	}
	
	private void checkCurrentSongValues()
	{
		songPlayORPause();
        if (currentPlaylistGroupID == 1 || currentSongID == 1) {
            artistTF.setText("");
            lyricsTA.setText("");
            songPic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zImages/ProjectMili.png")));
            return;
        }
        if(currentPlaylistGroupID != 1 && currentSongID != 1)
        {
        	String selectedSong = songCB.getSelectedItem().toString();
	        for (SongEntity songData : songDatas) {
		        if (selectedSong == songData.getTitle())
		        {
		        	artistTF.setText(currentArtist);
		            try {
		                BufferedImage img = ImageIO.read(new File(currentImagePath));
		                if (img == null) {
		                    throw new Exception("Unable to read image file");
		                }
		                ImageIcon icon = new ImageIcon(img);
		                songPic.setIcon(icon);
		            } catch (Exception e) {
		            	e.printStackTrace();
		            }
		            if (clip != null && clip.isRunning())
		            {
		                clip.stop();
		            }
		            File file = new File(currentSongPath);
		            try {
		                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		                clip = AudioSystem.getClip();
		                clip.open(audioStream);
		            } catch(Exception e) {
		                e.printStackTrace();
		            }
		        	try {
		        	    BufferedReader reader = new BufferedReader(new FileReader(currentLyricPath));
		        	    String line;
		        	    StringBuilder content = new StringBuilder();
		        	    while ((line = reader.readLine()) != null) {
		        	        content.append(line).append("\n");
		        	    }
		        	    reader.close();
		        	    lyricsTA.setText(content.toString());
			        } catch(Exception e) {
		                e.printStackTrace();
		            }
		        	break;
		        }
		        else
		        {
		        	continue;
		        }
	        }
        }
	}
	
	@SuppressWarnings("unchecked")
    private void refreshPLGCB() {
    	playlistCB.removeAllItems();
        for (PlaylistGroupEntity playlistGroupEntity : playlistGroupEntities) {
        	playlistCB.addItem(playlistGroupEntity.getPlaylistTitle());
        	
        }
    }
	
    @SuppressWarnings("unchecked")
	private void refreshSDCB() {
    	songCB.removeAllItems();
        for (SongEntity songData : songDatas) {
        	if(songData.getSongPlaylistGroupID() == 1)
        	{
                songCB.addItem(songData.getTitle());
                continue;
        	}
        	if(songData.getSongPlaylistGroupID() != currentPlaylistGroupID)
        	{
        		continue;
        	}
        	else
        	{
                songCB.addItem(songData.getTitle());
        	}
        }
    }

}
