package dispositivo.interfaces;

import java.util.Collection;
import java.util.Map;

public interface IDispositivo {

	public String getId();
	
	public IDispositivo iniciar();
	public IDispositivo detener();
	public IDispositivo habilita();
	public IDispositivo deshabilita();
	public IDispositivo addFuncion(IFuncion f);
	public IFuncion getFuncion(String funcionId);
	public Collection<IFuncion> getFunciones();
	public Boolean getHabilitado();
	public void publishFunctionInfo(IFuncion f);
}
