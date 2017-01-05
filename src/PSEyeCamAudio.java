import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class PSEyeCamAudio {
	
	private Mixer mixer;
	private Line.Info lineInfo;
	
	public static PSEyeCamAudio[] getAvailablePSEye(){
		ArrayList<PSEyeCamAudio> psEyes = new ArrayList<>();
		for(Mixer.Info info: AudioSystem.getMixerInfo()){
			Mixer mix = AudioSystem.getMixer(info);
			if(!info.getName().toLowerCase().contains("camerab409241")) continue;
			if(mix.getTargetLineInfo().length<=0) continue;
			PSEyeCamAudio psEye = new PSEyeCamAudio(mix);
			psEyes.add(psEye);
		}
		return psEyes.toArray(new PSEyeCamAudio[psEyes.size()]);
	}
	
	public static PSEyeCamAudio getPSEye(Mixer camMixer){
		if (camMixer==null) return null;
		Mixer.Info info = camMixer.getMixerInfo();
		if(!info.getName().toLowerCase().contains("camerab409241")) return null;
		if(camMixer.getTargetLineInfo().length<=0) return null;
		return new PSEyeCamAudio(camMixer);
	}
	
	private PSEyeCamAudio(Mixer camMixer){
		this.mixer = camMixer;
		this.lineInfo = mixer.getTargetLineInfo()[0];
	}
	
	public Mixer getMixer(){
		return mixer;
	}
	
	public AudioFormat[] getSupportedFormats(){
		return ((DataLine.Info)lineInfo).getFormats();
	}
	
	public TargetDataLine getTargetDataLine() throws LineUnavailableException{
		return (TargetDataLine) mixer.getLine(new DataLine.Info(DataLine.class,
				new AudioFormat(44100, 16, 4, true, true))); // 44100, 16, 4, true, true
	}
	
	public TargetDataLine getTargetDataLine(AudioFormat format) throws LineUnavailableException{
		if(!((DataLine.Info)lineInfo).isFormatSupported(format)) return null;
		return (TargetDataLine) mixer.getLine(new DataLine.Info(DataLine.class, format));
	}

}
