package dispositivo.api.rest;

import java.util.Arrays;
import java.util.HashSet;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.service.CorsService;

import dispositivo.interfaces.IDispositivo;
import dispositivo.utils.MySimpleLogger;

public class Dispositivo_RESTApplication extends Application {

	protected IDispositivo dispositivo = null;
	private String loggerId = null;
	
	public Dispositivo_RESTApplication(IDispositivo dispositivo) {
		this.dispositivo = dispositivo;
		this.loggerId = dispositivo.getId() + "-apiREST";
		
	    CorsService corsService = new CorsService();         
	    corsService.setAllowedOrigins(new HashSet(Arrays.asList("*")));
	    corsService.setAllowedCredentials(true);
	    getServices().add(corsService);
	    
	}
	

	/**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {
        // Create a router Restlet that routes each call to a new instance.
        Router router = new Router(getContext());

        // Defines Routes to the different Resources
        router.attach(Funcion_Recurso.RUTA, Funcion_Recurso.class);
        MySimpleLogger.trace(this.loggerId, "Registrada ruta " + Funcion_Recurso.RUTA + " en api REST");
        router.attach(Dispositivo_Recurso.RUTA, Dispositivo_Recurso.class);
        MySimpleLogger.trace(this.loggerId, "Registrada ruta " + Dispositivo_Recurso.RUTA + " en api REST");

        return router;
    }
	
    
    public IDispositivo getDispositivo() {
    	return this.dispositivo;
    }
	
	
	


}
