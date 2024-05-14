package dispositivo.interfaces;

public interface Configuracion {

	public static final String M2MIO_USERNAME = "<m2m.io username>";
	public static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";
	
//	public static final String TOPIC_BASE = "es/upv/inf/muiinf/ina/";
	public static final String TOPIC_BASE = "";
	public static final String TOPIC_REGISTRO =  Configuracion.TOPIC_BASE + "gestion/dispositivos";


}
