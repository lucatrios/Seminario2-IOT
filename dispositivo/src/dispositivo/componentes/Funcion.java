package dispositivo.componentes;

import dispositivo.interfaces.FuncionStatus;
import dispositivo.interfaces.IDispositivo;
import dispositivo.interfaces.IFuncion;
import dispositivo.utils.MySimpleLogger;

public class Funcion implements IFuncion {

    protected String id = null;

    protected FuncionStatus initialStatus = null;
    protected FuncionStatus status = null;
    protected static IDispositivo dispositivo = null;

    private String loggerId = null;

    public static Funcion build(String id) {
        return new Funcion(id, FuncionStatus.OFF);
    }

    public static Funcion build(String id, FuncionStatus initialStatus, IDispositivo d) {
        dispositivo = d;
        return new Funcion(id, initialStatus);
    }

    protected Funcion(String id, FuncionStatus initialStatus) {
        this.id = id;
        this.initialStatus = initialStatus;
        this.loggerId = "Funcion " + id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public IFuncion encender() {

        if (dispositivo.getHabilitado()) {
            this.setStatus(FuncionStatus.ON);
            dispositivo.publishFunctionInfo(this);
        } else {
            MySimpleLogger.warn(this.loggerId, "El dispositivo esta deshabilitado, no se permite la modifica del estado de sus funciones");
        }
        return this;
    }

    @Override
    public IFuncion apagar() {

        MySimpleLogger.info(this.loggerId, "==> Apagar");
        if (dispositivo.getHabilitado()) {
            this.setStatus(FuncionStatus.OFF);
            dispositivo.publishFunctionInfo(this);
        } else {
            MySimpleLogger.warn(this.loggerId, "El dispositivo esta deshabilitado, no se permite la modifica del estado de sus funciones");
        }
        return this;
    }

    @Override
    public IFuncion parpadear() {

        MySimpleLogger.info(this.loggerId, "==> Parpadear");
        if (dispositivo.getHabilitado()) {
            this.setStatus(FuncionStatus.BLINK);
            dispositivo.publishFunctionInfo(this);
        } else {
			MySimpleLogger.warn(this.loggerId, "El dispositivo esta deshabilitado, no se permite la modifica del estado de sus funciones");
        }
        return this;
    }

    protected IFuncion _putIntoInitialStatus() {
        switch (this.initialStatus) {
            case ON:
                this.encender();
                break;
            case OFF:
                this.apagar();
                break;
            case BLINK:
                this.parpadear();
                break;

            default:
                break;
        }

        return this;

    }

    @Override
    public FuncionStatus getStatus() {
        return this.status;
    }

    protected IFuncion setStatus(FuncionStatus status) {
        dispositivo.publishInfoApiMQTT.publishFunctionInfo(this);
        this.status = status;
        return this;
    }

    @Override
    public IFuncion iniciar() {
        this._putIntoInitialStatus();
        return this;
    }

    @Override
    public IFuncion detener() {
        return this;
    }


}
