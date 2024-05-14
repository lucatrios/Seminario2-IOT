package dispositivo.componentes.pi4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduledSignallerWorker {
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> timer = null;
	protected List<ISignallable> signallables = null;

	protected long delayMS = 5000;
	protected long initialDelay = 0;
	
	public ScheduledSignallerWorker(int delay) {
		this.signallables = new ArrayList<ISignallable>();
		this.delayMS = delay;
	}

	public ScheduledSignallerWorker(int delay, int initialDelay) {
		this(delay);
		this.initialDelay=initialDelay;
	}

	public ScheduledSignallerWorker addSignallable(ISignallable signallable) {
		this.signallables.add(signallable);
		return this;
	}
	
	public void start() {
		if ( this.signallables == null || this.signallables.size() == 0 ) {
			System.out.println("Required at least one ISignallable to start the worker. Cannot start ...");
			return;
		}
		
		if ( this.timer == null )
			this.start_timer();
	}
	
	public long getDelayMS() {
		return this.delayMS;
	}
	
	public void stop() {
		if ( this.timer != null )
			this.timer.cancel(true);
		this.timer = null;
	}
	
	protected void start_timer() {  // En aquesta fase, el timer espera fins sincronitzar-se amb les hores en punt, aleshores passa a la fase de crucer

		this.timer = scheduler.scheduleAtFixedRate(new CheckEvents(this.signallables), this.initialDelay, this.delayMS, TimeUnit.MILLISECONDS);

	}
	

	
	class CheckEvents extends TimerTask {

		List<ISignallable> signallables = null;
		
		public CheckEvents(List<ISignallable> signallables) {
			this.signallables = signallables;
		}
		
	    @Override
		public void run() {
    		for(ISignallable signallable : this.signallables )
    			signallable.signal();
	    }
	    
	}

	
}
