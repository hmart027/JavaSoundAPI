import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

import com.sun.media.sound.DirectAudioDeviceProvider;


public class SoundGenerator {
	
	public SoundGenerator(){
		
		listOutputDevices();
		
//		try {
//			SourceDataLine outLine = AudioSystem.getSourceDataLine(new AudioFormat(44100, 16, 1, true, true));
//			System.out.println("got sound line");
//			outLine.open();
//			outLine.start();
//			System.out.println("line opened: "+outLine.getBufferSize());
//			int t = 0;
//			double f = 18;
//			int rs = (int) (44100/f);
//			while(true){
//				
//				int out = (int) (32768*0.1*Math.sin(2.0*Math.PI*f*(double)t/44100.0));
////				System.out.println(out);
//				outLine.write(new byte[]{(byte) (out>>8), (byte) (out)}, 0, 2);
////				outLine.flush();
//				t++;
//				if(t>=rs) t = 0;
//			}
//			
//		} catch (LineUnavailableException e) {
//			e.printStackTrace();
//		}
		
	}
	
	public void listOutputDevices(){
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		System.out.println("Mixers: ");
		for (Mixer.Info info: mixerInfos){
			Mixer m = AudioSystem.getMixer(info);
			if(m.getSourceLineInfo().length<=0) continue;
			if(!m.isLineSupported(new Line.Info(SourceDataLine.class))) continue;
			System.out.println(info);
			System.out.println(info.getDescription()+": "+m.getClass());
			
			Line.Info[] lineInfos;
			System.out.println("\tSource Lines: ");
			lineInfos = m.getSourceLineInfo();
			for (Line.Info lineInfo:lineInfos){
				System.out.println ("\t\t"+info.getName()+"---"+lineInfo);
				System.out.println ("\t\t"+lineInfo.getLineClass());
//				for(AudioFormat format: ((DataLine.Info)lineInfo).getFormats()){
//					System.out.println("\t\t"+format);
//				}
//				Line line = m.getLine(lineInfo);
//				System.out.println("\t-----"+line);
			}
			System.out.println();
		 }
	}
	
	public static void main(String[] args){
		new SoundGenerator();
	}

}
