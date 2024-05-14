package dispositivo.pi4j2.iniciador;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

import dispositivo.componentes.Dispositivo;
import dispositivo.componentes.pi4j2.FuncionPi4Jv2;
import dispositivo.interfaces.FuncionStatus;
import dispositivo.interfaces.IDispositivo;

public class DispositivoIniciadorPi4Jv2 {

	public static void main(String[] args) {

		if ( args.length < 4 ) {
			System.out.println("Usage: java -jar dispositivo.jar device deviceIP rest-port mqttBroker");
			System.out.println("Example: java -jar dispositivo.jar ttmi050 ttmi050.iot.upv.es 8182 tcp://ttmi008.iot.upv.es:1883");
			return;
		}

		String deviceId = args[0];
		String deviceIP = args[1];
		String port = args[2];
		String mqttBroker = args[3];
		

		// Configuramos el contexto/plataforma del GPIO de la Raspberry
		Context pi4jContext =  Pi4J.newAutoContext();
		//Platforms platforms = pi4jContext.platforms();

		
		IDispositivo d = Dispositivo.build(deviceId, deviceIP, Integer.valueOf(port), mqttBroker);

		// Añadimos funciones al dispositivo
		// f1 - GPIO_17
		FuncionPi4Jv2 f1 = FuncionPi4Jv2.build("f1", 17, FuncionStatus.OFF, pi4jContext);
		d.addFuncion(f1);
		
		// f2 - GPIO_27
		FuncionPi4Jv2 f2 = FuncionPi4Jv2.build("f2", 27, FuncionStatus.OFF, pi4jContext);
		d.addFuncion(f2);

		
		// Arrancamos el dispositivo
		d.iniciar();
		
	}

}
