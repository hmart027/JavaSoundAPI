import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class PSEyeCamAudio {
	
	private Mixer mixer;
	
	private PSEyeCamAudio(Mixer camMixer){
		this.mixer = camMixer;
	}
	
	public static PSEyeCamAudio getPSEye(Mixer camMixer){
		if (camMixer==null) return null;
		Mixer.Info info = camMixer.getMixerInfo();
		if(!info.getName().toLowerCase().contains("camera-b4.09.24.1")) return null;
		return new PSEyeCamAudio(camMixer);
	}
	
	public TargetDataLine getTargetDataLine(){
		try {
			return (TargetDataLine) mixer.getLine(new DataLine.Info(DataLine.class,
					new AudioFormat(44100, 16, 4, true, true)));
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
