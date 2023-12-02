package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EventoDao {

    private Connection con;
    private Conexion conectar = new Conexion();

    public List<EventoModel> listar() {
        List<EventoModel> datos = new ArrayList<>();
        try {
            con = conectar.conectar();
            Statement ps = con.createStatement();
            String query = "SELECT * FROM evento";
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                EventoModel p = new EventoModel();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setTipoEvento(rs.getString(3));
                p.setFecha(rs.getString(4));
                p.setHoraInicio(rs.getString(5));
                p.setHoraFinal(rs.getString(6));
                p.setLugar(rs.getString(7));
                p.setBoleteria(rs.getString(8));
                p.setEstado(rs.getString(9));

                datos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return datos;
    }

    public boolean insertar(EventoModel evento) {
        Connection con = null;
        try {
            con = conectar.conectar();

            String query = "INSERT INTO evento (nombre, tipo, fecha, horaInicio, horaFinal, lugar, boleteria, estado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, evento.getNombre());
                ps.setString(2, evento.getTipoEvento());
                ps.setString(3, evento.getFecha());
                ps.setString(4, evento.getHoraInicio());
                ps.setString(5, evento.getHoraFinal());
                ps.setString(6, evento.getLugar());
                ps.setString(7, evento.getBoleteria());
                ps.setString(8, evento.getEstado());

                int resultado = ps.executeUpdate();
                return resultado > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean eliminar(int id) {
        try {
            con = conectar.conectar();

            String query = "DELETE FROM evento WHERE id = ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, id);

                int resultado = ps.executeUpdate();
                return resultado > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
