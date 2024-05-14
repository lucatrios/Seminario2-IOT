package dispositivo.api.rest;

import dispositivo.interfaces.IDispositivo;
import dispositivo.utils.MySimpleLogger;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Put;

import java.io.IOException;

public class Habilita_Recurso extends Recurso{

    public static final String RUTA = Dispositivo_Recurso.RUTA + "/habilita/";

    protected IDispositivo getDispositivo() {
        return this.getDispositivo_RESTApplication().getDispositivo();
    }

    @Put
    public Representation put(Representation entity) {

        // Obtenemos el dispositivo

        IDispositivo d = this.getDispositivo();
        if ( d == null ) {
            return this.generateResponseWithErrorCode(Status.CLIENT_ERROR_NOT_FOUND);
        }

        // Dispositivo encontrada
        // Ejecutamos  indicada en campo 'accion' del JSON recibido
        JSONObject payload = null;
        try {
            payload = new JSONObject(entity.getText());
            String action = payload.getString("habilita");

            if ( action.equalsIgnoreCase("true") )
                d.habilita();
            else if ( action.equalsIgnoreCase("false") )
                d.deshabilita();
            else {
                MySimpleLogger.warn("Habilita-Recurso", "Estado '" + payload + "' no reconocido. Sólo true o false");
                return this.generateResponseWithErrorCode(Status.CLIENT_ERROR_BAD_REQUEST);
            }

        } catch (JSONException | IOException e) {
            MySimpleLogger.warn("Habilita-Recurso", "Estado '" + payload + "' no reconocido. Sólo true o false");
            return this.generateResponseWithErrorCode(Status.CLIENT_ERROR_BAD_REQUEST);
        }

        this.setStatus(Status.SUCCESS_OK);
        return new StringRepresentation(this.getStatus().toString(), MediaType.APPLICATION_JSON);

    }
}

