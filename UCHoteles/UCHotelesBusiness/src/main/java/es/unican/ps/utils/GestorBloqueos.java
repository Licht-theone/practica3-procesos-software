package es.unican.ps.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.ReservaTipoHabitacion;
import jakarta.annotation.Resource;
import jakarta.ejb.Singleton;
import jakarta.ejb.Timeout;
import jakarta.ejb.Timer;
import jakarta.ejb.TimerConfig;
import jakarta.ejb.TimerService;

@Singleton
public class GestorBloqueos {
	
	@Resource
	private TimerService timer;
	
	private List<ReservaTipoHabitacion> bloqueosActivos = new CopyOnWriteArrayList<>();
	
	public void registrarBloqueo(ReservaTipoHabitacion linea) {
		bloqueosActivos.add(linea);
		
		//programar liberacion en 10 minutos (600000ms)
		timer.createSingleActionTimer(600000, new TimerConfig(linea, false));
	}
	
	public void eliminaBloqueoManual(ReservaTipoHabitacion linea) {
        //desbloquear manualmente (al finalizar la reserva)
        bloqueosActivos.remove(linea);
        
        for (Timer t : timer.getTimers()) {
            if (t.getInfo().equals(linea)) {
                t.cancel();
                break;
            }
        }
    }
	
	public int consultarHabitacionesBloqueadas(Habitacion habitacionConsultada) {
        return bloqueosActivos.stream()
                .filter(b -> b.getHabitacion().getId() == habitacionConsultada.getId())
                .mapToInt(ReservaTipoHabitacion::getNumHabitaciones)
                .sum(); //devuelve las habitaciones de ese tipo y hotel bloqueadas
    }
	
	@Timeout
    public void liberarBloqueoAutomatico(Timer timer) {
        //recuperar la linea bloqueada
        ReservaTipoHabitacion lineaExpirada = (ReservaTipoHabitacion) timer.getInfo();
        
        //liberar la linea
        bloqueosActivos.remove(lineaExpirada);
    }
}
