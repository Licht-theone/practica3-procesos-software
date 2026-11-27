package es.unican.ps.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.ReservaTipoHabitacion;
import es.unican.ps.enums.TipoHabitacion;
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

        // Programar liberacion en 10 minutos (600000ms)
        timer.createSingleActionTimer(600000, new TimerConfig(linea, false));
    }

    public void eliminaBloqueoManual(ReservaTipoHabitacion linea) {
        if (linea == null) {
            return;
        }
        // Desbloquear manualmente (al finalizar o cancelar la reserva)
        boolean eliminado = bloqueosActivos.remove(linea);
        if (!eliminado && linea.getHabitacion() != null) {
            for (ReservaTipoHabitacion b : bloqueosActivos) {
                if (b.getHabitacion() != null
                        && b.getHabitacion().getId() == linea.getHabitacion().getId()
                        && b.getNumHabitaciones() == linea.getNumHabitaciones()) {
                    bloqueosActivos.remove(b);
                    break;
                }
            }
        }

        cancelarTimerAsociado(linea);
    }

    public boolean eliminaBloqueo(long idHotel, TipoHabitacion tipo, int cantidad) {
        for (ReservaTipoHabitacion b : bloqueosActivos) {
            if (b.getHabitacion() != null
                    && b.getHabitacion().getTipo() == tipo
                    && b.getNumHabitaciones() == cantidad
                    && b.getHabitacion().getHotel() != null
                    && b.getHabitacion().getHotel().getId() == idHotel) {
                bloqueosActivos.remove(b);
                cancelarTimerAsociado(b);
                return true;
            }
        }
        return false;
    }

    private void cancelarTimerAsociado(ReservaTipoHabitacion linea) {
        try {
            for (Timer t : timer.getTimers()) {
                try {
                    Object info = t.getInfo();
                    if (info instanceof ReservaTipoHabitacion) {
                        ReservaTipoHabitacion infoLinea = (ReservaTipoHabitacion) info;
                        boolean coincide = infoLinea.equals(linea)
                                || (infoLinea.getHabitacion() != null && linea.getHabitacion() != null
                                        && infoLinea.getHabitacion().getId() == linea.getHabitacion().getId()
                                        && infoLinea.getNumHabitaciones() == linea.getNumHabitaciones());
                        if (coincide) {
                            t.cancel();
                            break;
                        }
                    }
                } catch (Exception ignored) {
                    // El timer puede haber expirado justo en este instante
                }
            }
        } catch (Exception ignored) {
            // Ignorar si no hay timers activos
        }
    }

    public int consultarHabitacionesBloqueadas(Habitacion habitacionConsultada) {
        return bloqueosActivos.stream()
                .filter(b -> b.getHabitacion().getId() == habitacionConsultada.getId())
                .mapToInt(ReservaTipoHabitacion::getNumHabitaciones)
                .sum();
    }

    @Timeout
    public void liberarBloqueoAutomatico(Timer timer) {
        ReservaTipoHabitacion lineaExpirada = (ReservaTipoHabitacion) timer.getInfo();
        if (lineaExpirada != null) {
            eliminaBloqueoManual(lineaExpirada);
        }
    }
}
