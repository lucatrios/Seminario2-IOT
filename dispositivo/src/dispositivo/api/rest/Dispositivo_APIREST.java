package dispositivo.api.rest;

import org.restlet.Component;
import org.restlet.data.Protocol;

import dispositivo.interfaces.IDispositivo;
import dispositivo.utils.MySimpleLogger;

public class Dispositivo_APIREST {
		
	protected Component component = null;
	protected Dispositivo_RESTApplication app = null;
	protected int port = 8080;
	protected IDispositivo dispositivo = null;
	
	private String loggerId = null;
	
	public static Dispositivo_APIREST build(IDispositivo d) {
		return new Dispositivo_APIREST(d);
	}
	
	public static Dispositivo_APIREST build(IDispositivo d, int port) {
		Dispositivo_APIREST api = new Dispositivo_APIREST(d);
		api.setPort(port);
		return api;
	}
	
	protected Dispositivo_APIREST(IDispositivo d) {
		this.dispositivo = d;
		this.loggerId = d.getId() + "-apiREST";
	}
	
	protected void setPort(int port) {
		this.port = port;
	}
	
	public void iniciar() {
		
		if ( component == null ) {
			
			// Create a new Component.
			component = new Component();
	
			// Add a new HTTP server listening on port x.
			component.getServers().add(Protocol.HTTP, port);
	
			// Attach the REST application.
			app = new Dispositivo_RESTApplication(this.dispositivo);
	
			component.getDefaultHost().attach("", app);
	
			// Start the component.
			try {
				component.start();
				MySimpleLogger.info(this.loggerId, "Iniciado servicio REST en puerto " + port);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}

	}
	
	
	public void detener() {
		MySimpleLogger.info(this.loggerId, "Detenido servicio REST en puerto " + port);
	}

}
