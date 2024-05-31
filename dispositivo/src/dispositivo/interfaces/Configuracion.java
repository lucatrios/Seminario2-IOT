package dispositivo.interfaces;

public interface Configuracion {

	public static final String M2MIO_USERNAME = "<m2m.io username>";
	public static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";
	
//	public static final String TOPIC_BASE = "es/upv/inf/muiinf/ina/";

	/**
	 * La variable TOPIC_BASE se usa como una base común o prefijo para construir otros temas relacionados. Esto facilita la organización y el
	 * manejo de nombres de topicos de manera coherente y evita la repetición de cadenas de texto en diferentes partes del código.
	 */
	public static final String TOPIC_BASE = "";
	public static final String TOPIC_REGISTRO =  Configuracion.TOPIC_BASE + "gestion/dispositivos";


}
