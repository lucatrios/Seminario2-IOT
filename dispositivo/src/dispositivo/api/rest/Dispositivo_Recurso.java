package dispositivo.api.rest;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.Put;

import dispositivo.interfaces.IDispositivo;

public class Dispositivo_Recurso extends Recurso {
	
	public static final String RUTA = "/dispositivo";

	public static JSONObject serialize(IDispositivo dispositivo) {
		JSONObject jsonResult = new JSONObject();
		
		try {
			jsonResult.put("id", dispositivo.getId());
			if ( dispositivo.getFunciones() != null ) {
				JSONArray arrayFunciones = new JSONArray();

				jsonResult.put("funciones", arrayFunciones);
			}

		} catch (JSONException e) {
		}
		
		return jsonResult;
	}
	
	public IDispositivo getDispositivo() {
		return this.getDispositivo_RESTApplication().getDispositivo();
	}

    @Get
    public Representation get() {

    	// Obtenemos el dispositivo
		IDispositivo d = this.getDispositivo();

		// Construimos el mensaje de respuesta

    	JSONObject resultJSON = Dispositivo_Recurso.serialize(d);    	
    	
		// Si todo va bien, devolvemos el resultado calculado
    	this.setStatus(Status.SUCCESS_OK);
        return new StringRepresentation(resultJSON.toString(), MediaType.APPLICATION_JSON);
    }
    
    
    
	@Put
	public Representation put(Representation entity) {

    	// Obtenemos la función indicada como parámetro en la Ruta

		IDispositivo d = this.getDispositivo();
		if ( d == null ) {
			return generateResponseWithErrorCode(Status.CLIENT_ERROR_NOT_FOUND);
		}

		// Dispositivo encontrado
		JSONObject payload = null;
		try {
			payload = new JSONObject(entity.getText());
			String action = payload.getString("accion");
			

		} catch (JSONException | IOException e) {
			this.generateResponseWithErrorCode(Status.CLIENT_ERROR_BAD_REQUEST);
		}
		
		// Construimos el mensaje de respuesta

		JSONObject resultJSON = Dispositivo_Recurso.serialize(d);
    	
    	this.setStatus(Status.SUCCESS_OK);
        return new StringRepresentation(resultJSON.toString(), MediaType.APPLICATION_JSON);

	}
    
    
    
    
	@Options
	public void describe() {
		Set<Method> meths = new HashSet<Method>();
		meths.add(Method.GET);
		meths.add(Method.OPTIONS);
		this.getResponse().setAllowedMethods(meths);
	}	

}
