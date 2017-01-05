import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SoundGenerator {
	
	public SoundGenerator(){
		
		try {
			SourceDataLine outLine = AudioSystem.getSourceDataLine(new AudioFormat(44100, 16, 1, true, true));
			System.out.println("got sound line");
			outLine.open();
			outLine.start();
			System.out.println("line opened: "+outLine.getBufferSize());
			int t = 0;
			double f = 18;
			int rs = (int) (44100/f);
			while(true){
				
				int out = (int) (32768*0.1*Math.sin(2.0*Math.PI*f*(double)t/44100.0));
//				System.out.println(out);
				outLine.write(new byte[]{(byte) (out>>8), (byte) (out)}, 0, 2);
//				outLine.flush();
				t++;
				if(t>=rs) t = 0;
			}
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		new SoundGenerator();
	}

}
