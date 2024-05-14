package dispositivo.api.rest;

import org.restlet.data.Status;
import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ServerResource;

public abstract class Recurso extends ServerResource {

	public Dispositivo_RESTApplication getDispositivo_RESTApplication() {
		return (Dispositivo_RESTApplication) this.getApplication();
	}
	
	protected Representation generateResponseWithErrorCode(Status s) {
		setStatus(s);
		return new EmptyRepresentation();
	}


}
