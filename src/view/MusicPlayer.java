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
import javax.sound.sampled.FloatControl;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import java.awt.TextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import java.awt.Component;
import java.awt.Cursor;
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
    
    private Clip clip = null;
    private JPanel panel;
    private JLabel volupLabel;
    private JLabel voldownLabel;
    private JLabel emptyLabel3;
    private JPanel textColorMode;
    private JPanel additionalButtons;
    private JLabel shuffleLabel;
    private JLabel emptyLabel4;
    private JLabel autoplayLabel;
    
    private Boolean autoplay = false;
    private Boolean shuffle = false;
    private Boolean nextSongRepeat = false;
    
    private int previousSong = 1;
    private int currentSong = 1;
    private int prevprevSong = 1;

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
		setTitle("Project Moon X Mili Music Collection");
		songFunctions = new SongFunctions();
		playlistGroupFunctions = new PlaylistFunctions();
		
		
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MusicPlayer.class.getResource("/zImages/ProjectMili.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 980, 680);
		splJPanel = new JPanel();
		splJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(splJPanel);
		GridBagLayout gbl_splJPanel = new GridBagLayout();
		gbl_splJPanel.columnWidths = new int[]{160, 460, 0};
		gbl_splJPanel.rowHeights = new int[] {40, 0, 0, 0};
		gbl_splJPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_splJPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
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
			    previousSong = 1;
			    currentSong = 1;
			    prevprevSong = 1;
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
				if(currentPlaylistGroupID != 1 && counterForRefresh >= 2)
				{
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
		backLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				backSong();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				backLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
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
			@Override
			public void mouseEntered(MouseEvent e) {
				labelPlayORPause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelPlayORPause.setCursor(Cursor.getDefaultCursor());
			}
		});
		
		labelPlayORPause.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/play.png")));
		labelPlayORPause.setAlignmentX(0.5f);
		controlsPanel.add(labelPlayORPause);
		
		JLabel emptyLabel2 = new JLabel();
		emptyLabel2.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/empty.png")));
		controlsPanel.add(emptyLabel2);
		
		nextLabel = new JLabel();
		nextLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				nextSong();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				nextLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				nextLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
		nextLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		nextLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/next.png")));
		controlsPanel.add(nextLabel);
		
		emptyLabel3 = new JLabel();
		emptyLabel3.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/empty.png")));
		controlsPanel.add(emptyLabel3);
		
		panel = new JPanel();
		controlsPanel.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		volupLabel = new JLabel();
		volupLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumeUpOrDown(true);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				volupLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				volupLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
		volupLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/volup.png")));
		panel.add(volupLabel);
		
		voldownLabel = new JLabel();
		voldownLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				volumeUpOrDown(false);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				voldownLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				voldownLabel.setCursor(Cursor.getDefaultCursor());
			}
		});
		voldownLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/voldown.png")));
		panel.add(voldownLabel);
		
		JPanel taPanel = new JPanel();
		GridBagConstraints gbc_taPanel = new GridBagConstraints();
		gbc_taPanel.insets = new Insets(0, 0, 5, 5);
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
		gbc_picPanel.insets = new Insets(0, 0, 5, 0);
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
	    
	    textColorMode = new JPanel();
	    GridBagConstraints gbc_textColorMode = new GridBagConstraints();
	    gbc_textColorMode.insets = new Insets(0, 0, 0, 5);
	    gbc_textColorMode.gridx = 0;
	    gbc_textColorMode.gridy = 2;
	    splJPanel.add(textColorMode, gbc_textColorMode);
	    textColorMode.setLayout(new BoxLayout(textColorMode, BoxLayout.X_AXIS));
	    
	    additionalButtons = new JPanel();
	    GridBagConstraints gbc_additionalButtons = new GridBagConstraints();
	    gbc_additionalButtons.gridx = 1;
	    gbc_additionalButtons.gridy = 2;
	    splJPanel.add(additionalButtons, gbc_additionalButtons);
	    additionalButtons.setLayout(new BoxLayout(additionalButtons, BoxLayout.X_AXIS));
	    
	    shuffleLabel = new JLabel();
	    shuffleLabel.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		shuffleFunction();
	    	}
			@Override
			public void mouseEntered(MouseEvent e) {
				shuffleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				shuffleLabel.setCursor(Cursor.getDefaultCursor());
			}
	    });
	    shuffleLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/shuffle.png")));
	    additionalButtons.add(shuffleLabel);
	    
	    emptyLabel4 = new JLabel();
	    emptyLabel4.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/empty.png")));
	    additionalButtons.add(emptyLabel4);
	    
	    autoplayLabel = new JLabel();
	    autoplayLabel.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseClicked(MouseEvent e) {
	    		autoplayFunction();
	    	}
			@Override
			public void mouseEntered(MouseEvent e) {
				autoplayLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				autoplayLabel.setCursor(Cursor.getDefaultCursor());
			}
	    });
	    autoplayLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/autoplay.png")));
	    additionalButtons.add(autoplayLabel);
	    
		refreshPLGCB();
		refreshSDCB();
	}
	
	private void autoplayFunction()
	{
		if(autoplay == false)
		{
			autoplay = true;
			autoplayLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/autoplayON.png")));
		}
		else
		{
			autoplay = false;
			autoplayLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/autoplay.png")));
		}
	}
	
	private void shuffleFunction()
	{
		if(shuffle == false)
		{
			shuffle = true;
			shuffleLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/shuffleON.png")));
		}
		else
		{
			shuffle = false;
			shuffleLabel.setIcon(new ImageIcon(MusicPlayer.class.getResource("/zImages/controls/shuffle.png")));
		}
	}
	
	private void volumeUpOrDown(Boolean increased)
	{
		if(clip != null && increased)
		{
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gainControl.getValue()+1.0f);
		}
		else if(clip != null && !increased)
		{
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gainControl.getValue()-1.0f);
		}
		else
			return;
	}
	
	private void nextSong()
	{
		if(!nextSongRepeat)
		{
			previousSong = currentSong;
		}
		if(previousSong != 1)
			prevprevSong = previousSong;
		if (currentPlaylistGroupID == 1)
			return;
		else if(shuffle)
		{
			Random rand = new Random();
			songCB.setSelectedIndex(rand.nextInt(songCB.getItemCount() - 1) + 1);
		}
		else if(songCB.getSelectedIndex() < (songCB.getItemCount() - 1))
		{
			songCB.setSelectedIndex(songCB.getSelectedIndex() + 1);
		}
		else
		{
			songCB.setSelectedIndex(1);
		}
		currentSong = songCB.getSelectedIndex();
		if(shuffle && (currentSong == previousSong || currentSong == prevprevSong))
		{
			nextSongRepeat = true;
			nextSong();
		}
		nextSongRepeat = false;
	}

	private void backSong()
	{
		if(!nextSongRepeat)
		{
			previousSong = currentSong;
		}
		if(previousSong != 1)
			prevprevSong = previousSong;
		if (currentPlaylistGroupID == 1)
			return;
		else if(shuffle)
		{
			Random rand = new Random();
			songCB.setSelectedIndex(rand.nextInt(songCB.getItemCount() - 1) + 1);
		}
		else if(songCB.getSelectedIndex() > 1)
		{
			songCB.setSelectedIndex(songCB.getSelectedIndex() - 1);
		}
		else
		{
			songCB.setSelectedIndex(songCB.getItemCount() - 1);
		}
		currentSong = songCB.getSelectedIndex();
		if(shuffle && (currentSong == previousSong || currentSong == prevprevSong))
		{
			nextSongRepeat = true;
			backSong();
		}
		nextSongRepeat = false;
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
		if(clip != null)
		{
			isPlaying = false;
			clip.stop();
		}
        if (currentPlaylistGroupID == 1 || currentSongID == 1) {
            artistTF.setText("All Project Moon X Mili Songs");
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
		            	// Image
		                BufferedImage img = ImageIO.read(new File(currentImagePath));
		                ImageIcon icon = new ImageIcon(img);
		                songPic.setIcon(icon);
		            } catch (Exception e) {
		            	e.printStackTrace();
		                JOptionPane.showMessageDialog(null,"Unsupported or missing Image file.");
		            }
		            File file = new File(currentSongPath);
		            try {
		            	clip = null;
		                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		                clip = AudioSystem.getClip();
		                clip.open(audioStream);
		            } catch(Exception e) {
		                e.printStackTrace();
		                JOptionPane.showMessageDialog(null,"Unsupported or missing Audio file.");
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
		                JOptionPane.showMessageDialog(null,"Unsupported or missing Lyrics file.");
		            }
		    		if(autoplay)
		    			songPlayORPause();
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
