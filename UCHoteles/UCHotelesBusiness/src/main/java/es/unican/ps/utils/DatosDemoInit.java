package es.unican.ps.utils;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.unican.ps.clases.Habitacion;
import es.unican.ps.clases.Hotel;
import es.unican.ps.enums.Categoria;
import es.unican.ps.enums.TipoHabitacion;
import es.unican.ps.interfaces.IHotelDAO;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

/**
 * EJB Singleton de arranque que carga datos de prueba iniciales en la base de datos
 * cuando la tabla de hoteles esta vacia (util para la demo con drop-and-create).
 */
@Singleton
@Startup
public class DatosDemoInit {

    private static final Logger LOGGER = Logger.getLogger(DatosDemoInit.class.getName());

    @EJB
    private IHotelDAO hotelDAO;

    @PostConstruct
    public void cargarDatosDemo() {
        try {
            List<Hotel> existentes = hotelDAO.listarHoteles();
            if (existentes != null && !existentes.isEmpty()) {
                LOGGER.info("DatosDemoInit: ya existen hoteles en BD, no se cargan datos de prueba.");
                return;
            }

            // Hotel 1: Bahia (Santander)
            Hotel bahia = new Hotel();
            bahia.setNombre("Bahia");
            bahia.setLocalidad("Santander");
            bahia.setDireccion("Calderón de la Barca, 1");
            bahia.setCategoria(Categoria.FOUR_STARS);
            bahia.addHabitacion(crearHabitacion(TipoHabitacion.INDIVIDUAL, 5, 65.0));
            bahia.addHabitacion(crearHabitacion(TipoHabitacion.DOBLE, 8, 100.0));
            bahia.addHabitacion(crearHabitacion(TipoHabitacion.JUNIOR_SUITE, 3, 150.0));
            bahia.addHabitacion(crearHabitacion(TipoHabitacion.SUITE, 2, 220.0));
            hotelDAO.crearHotel(bahia);

            // Hotel 2: Vincci (Santander)
            Hotel vincci = new Hotel();
            vincci.setNombre("Vincci");
            vincci.setLocalidad("Santander");
            vincci.setDireccion("Castelar, 25");
            vincci.setCategoria(Categoria.FOUR_STARS);
            vincci.addHabitacion(crearHabitacion(TipoHabitacion.INDIVIDUAL, 4, 70.0));
            vincci.addHabitacion(crearHabitacion(TipoHabitacion.DOBLE, 6, 110.0));
            vincci.addHabitacion(crearHabitacion(TipoHabitacion.SUITE, 2, 240.0));
            hotelDAO.crearHotel(vincci);

            // Hotel 3: Real (Santander)
            Hotel real = new Hotel();
            real.setNombre("Real");
            real.setLocalidad("Santander");
            real.setDireccion("Paseo de Pérez Galdós, 28");
            real.setCategoria(Categoria.FIVE_STARS);
            real.addHabitacion(crearHabitacion(TipoHabitacion.DOBLE, 5, 180.0));
            real.addHabitacion(crearHabitacion(TipoHabitacion.JUNIOR_SUITE, 3, 260.0));
            real.addHabitacion(crearHabitacion(TipoHabitacion.SUITE, 2, 350.0));
            hotelDAO.crearHotel(real);

            // Hotel 4: Gran Hotel Bilbao (Bilbao)
            Hotel bilbao = new Hotel();
            bilbao.setNombre("Gran Hotel Bilbao");
            bilbao.setLocalidad("Bilbao");
            bilbao.setDireccion("Av. Indalecio Prieto, 1");
            bilbao.setCategoria(Categoria.FOUR_STARS);
            bilbao.addHabitacion(crearHabitacion(TipoHabitacion.INDIVIDUAL, 6, 75.0));
            bilbao.addHabitacion(crearHabitacion(TipoHabitacion.DOBLE, 10, 120.0));
            bilbao.addHabitacion(crearHabitacion(TipoHabitacion.JUNIOR_SUITE, 2, 175.0));
            hotelDAO.crearHotel(bilbao);

            LOGGER.info("DatosDemoInit: cargados 4 hoteles de prueba en la base de datos.");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "DatosDemoInit: no se pudieron cargar los datos de prueba", e);
        }
    }

    private Habitacion crearHabitacion(TipoHabitacion tipo, int disponibles, double precioPorNoche) {
        Habitacion h = new Habitacion();
        h.setTipo(tipo);
        h.setDisponibles(disponibles);
        h.setPrecioPorNoche(precioPorNoche);
        return h;
    }
}
