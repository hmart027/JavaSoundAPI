import javax.dsp.tools.Complex;
import javax.dsp.tools.FFT;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

public class JavaSoundAPIMain {
	
	public static PlotterWindow plotter;
	
	JavaSoundAPIMain(){
		
		plotter = new PlotterWindow();
		
		byte[] frame = null;
		Complex[] fft = null;
		double cSample = 0, lSample = 0, lY = 0;
		double dx = 0;

		AudioFormat format = new AudioFormat(22000, 16, 1, true, true);
		int frameSize = format.getFrameSize();
		
//		PlotterWindow.pane1.autoscale(false, false);
		
		try {
			TargetDataLine microphone = AudioSystem.getTargetDataLine(format);
			microphone.open();
			dx = 1d/22000.0;
			frame = new byte[microphone.getBufferSize()/10];
			fft = new Complex[16384];
			int fftCount = 0;
			System.out.println(fft.length);
			microphone.start();
			
			double fStep = 22000.0/(double)fft.length;
				
			while(microphone.isOpen()){
				if(microphone.available() >= frame.length){
					int r = microphone.read(frame, 0, frame.length);
					if(r>0){
						for(int c = 0; c<r; c+=frameSize){
							int data = ((int)frame[c])<<8 | (frame[c+1] & 0x0FF);
							fft[fftCount++] = new Complex(data, 0);
							if(fftCount == fft.length){
								fftCount = 0;
								fft = FFT.fft(fft);
								PlotterWindow.pane2.clearLines();
								for(int x = 0; x<fft.length/2; x++){
									PlotterWindow.pane2.drawLine(x*fStep, 0, x*fStep, fft[x].mod()/(double)fft.length);
								}
//								PlotterWindow.pane2.autoscale(true, false);
							}
							PlotterWindow.pane1.drawLine(lSample, lY, cSample, data);
							double maxX = PlotterWindow.pane1.getMaxX();
							double minX = PlotterWindow.pane1.getMinX();
							double dX = maxX-minX;
							if(maxX<=cSample){
								PlotterWindow.pane1.setMaxX(maxX+dX*0.1);
								PlotterWindow.pane1.setMinX(minX+dX*0.1);
							}
							lSample = cSample;
							cSample += dx;
							lY = data;
						}
					}
				}
			}
			
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public static void listInputDevices(){
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		System.out.println("Mixers: ");
		for (Mixer.Info info: mixerInfos){
			Mixer m = AudioSystem.getMixer(info);
			if(m.getTargetLineInfo().length<=0) continue;
			System.out.println(info);
			System.out.println(info.getDescription());

			Line.Info[] lineInfos;
//			System.out.println("\tSource Lines: ");
//			lineInfos = m.getSourceLineInfo();
//			for (Line.Info lineInfo:lineInfos){
//				System.out.println ("\t\t"+info.getName()+"---"+lineInfo);
////				Line line = m.getLine(lineInfo);
////				System.out.println("\t-----"+line);
//			}
			
			System.out.println("\tTarget Lines: ");
			lineInfos = m.getTargetLineInfo();
			for (Line.Info lineInfo:lineInfos){
				System.out.println ("\t\t"+m+"---"+lineInfo);
//				Line line = m.getLine(lineInfo);
//				System.out.println("\t-----"+line);
			}
			System.out.println();
		 }
	}

	public static void getMixerInfo(){
		for(Mixer.Info i: AudioSystem.getMixerInfo()){
			System.out.println(i.getName());
			System.out.println(i.getDescription());
//			Mixer m = AudioSystem.getMixer(i);
			System.out.println();		
		}
	}
	
	public static void main(String[] args) {
		
//		getMixerInfo();	
//		if(1==1)System.exit(0);
		

		listInputDevices();
//		new JavaSoundAPIMain();
		
//		double f = 400;
//		double f1 = 200000;
//
//		Complex[] fft = new Complex[4096];
//
//		int t = 0;
//		for (int i = 0; i < fft.length; i++) {
//			fft[i] = new Complex(Math.sin(2 * Math.PI * f * t / 10000.0), 0);
//			t++;
//		}
//
//		fft = FFT.fft(fft);
//		PlotterWindow.pane2.clearLines();
//		for (int i = 0; i < fft.length / 2; i++) {
//			double x = i / (double) fft.length * 10000;
//			PlotterWindow.pane2.drawLine(x, 0, x, fft[i].mod());
//			if (fft[i].mod() > 100)
//				System.out.println(i + ": " + fft[i].mod());
//		}
//		PlotterWindow.pane2.repaint();
	}
}
