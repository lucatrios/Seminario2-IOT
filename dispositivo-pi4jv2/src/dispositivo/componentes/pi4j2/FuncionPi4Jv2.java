package dispositivo.componentes.pi4j2;

import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalOutputConfigBuilder;
import com.pi4j.io.gpio.digital.DigitalState;
import com.pi4j.platform.Platforms;

import dispositivo.componentes.Funcion;
import dispositivo.interfaces.FuncionStatus;
import dispositivo.interfaces.IFuncion;

public class FuncionPi4Jv2 extends Funcion implements ISignallable {

	protected Platforms platforms = null;
	protected Context pi4jContext = null;
	protected boolean isBlinking = false;
	protected ScheduledSignallerWorker blinkingWorker = null;
	
	protected int gpioPin = 0;
	protected DigitalOutput pin = null;

	public static FuncionPi4Jv2 build(String id, int gpioPin, Context pi4jContext) {
		FuncionPi4Jv2 f = new FuncionPi4Jv2(id, gpioPin, FuncionStatus.OFF, pi4jContext);
		return f;
	}
	
	public static FuncionPi4Jv2 build(String id, int gpioPin, FuncionStatus initialStatus, Context pi4jContext) {
		FuncionPi4Jv2 f = new FuncionPi4Jv2(id, gpioPin, initialStatus, pi4jContext);
		return f;
	}

	
	protected FuncionPi4Jv2(String id, int gpioPin, FuncionStatus initialStatus, Context pi4jContext) {
		super(id, initialStatus);
		this.gpioPin=gpioPin;
		this.pi4jContext = pi4jContext;
		
		
		DigitalState initialDigitalState = ( this.initialStatus == FuncionStatus.ON ) ? DigitalState.HIGH : DigitalState.LOW;
		
		DigitalOutputConfigBuilder pinConfig = DigitalOutput.newConfigBuilder(pi4jContext)
			      .id(this.id)
			      .name(this.id)
			      .address(this.gpioPin)
			      .shutdown(initialDigitalState)
			      .initial(initialDigitalState)
			      .provider("pigpio-digital-output");
			      
		this.pin = pi4jContext.create(pinConfig);
		
		this.blinkingWorker = new ScheduledSignallerWorker(1000);
		this.blinkingWorker
			.addSignallable(this);


	}
	
	
	
	
	
	@Override
	public IFuncion encender() {
		this.cancelBlinking();
		super.encender();
		this.pin.high();
		return this;
	}
	
	

	@Override
	public IFuncion apagar() {
		this.cancelBlinking();
		super.apagar();
		this.pin.low();
		return this;
	}

	@Override
	public IFuncion parpadear() {
		super.parpadear();
		
		// jjfons : 2022/04/07
		// Funcion no implementada en versión actual de plugin/library-gpio-raspberry
		// Haremos una implementación 'software'
		//		this.pin.blink(1000);

		if ( !this.isBlinking ) {
			this.blinkingWorker.start();
			this.isBlinking=true;
		}

		
		return this;
	}
	
	protected void cancelBlinking() {
		this.blinkingWorker.stop();
		this.isBlinking=false;
	}
	
	@Override
	public ISignallable signal() {
		this.pin.toggle();
		return this;
	}

}
