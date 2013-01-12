package custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

public class StopPlayButton extends MainButton 
{
	private boolean playing = false;
	
	private String play, playHover, stop, stopHover;
	
	public StopPlayButton(String play, String playHover, String stop, String stopHover)
	{
		super(play, playHover);
		
		this.play = play;
		this.playHover = playHover;
		this.stop = stop;
		this.stopHover = stopHover;
		
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(playing)
					stop();
				else 
					play();
			}
		});
		
	}

	private void play() {
		playing = true;
		super.updateIcons(stop, stopHover);
	}

	private void stop() {
		playing = false;
		super.updateIcons(play, playHover);
	}

}
