package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import modelo.EventoModel;
import modelo.EventoDao;
import vista.EventoFrom;

public class EventoController {

    private EventoFrom vista;
    private EventoDao modelo;

    public EventoController(EventoFrom vista, EventoDao modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    public void iniciar() {
        cargarDatosEnTabla();
        configurarEventos();
        vista.setVisible(true);
    }

    private void cargarDatosEnTabla() {
        List<EventoModel> eventos = modelo.listar();
        vista.actualizarTabla(eventos);
    }

    private void configurarEventos() {
        vista.btnGuardar.addActionListener(evt -> btnGuardarActionPerformed());
        vista.btnEliminar.addActionListener(evt -> btnEliminarActionPerformed());
    }

    private void btnGuardarActionPerformed() {
        String nombre = vista.txtNombre.getText();
        String tipoEvento = vista.txtTipoEvento.getText();
        String fecha = vista.txtFecha.getText();
        String horaInicio = vista.txtHoraInicio.getText();
        String horaFin = vista.txtHoraFin.getText();
        String lugar = vista.txtLugar.getText();
        String boleteria = vista.txtBoleteria.getText();
        String estado = vista.txtEstado.getText();

        EventoModel nuevoEvento = new EventoModel(1, nombre, tipoEvento, fecha, horaInicio, horaFin, lugar, boleteria, estado);

        boolean exito = modelo.insertar(nuevoEvento);

        if (exito) {
            cargarDatosEnTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(vista, "Evento guardado exitosamente.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error al guardar el evento.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnEliminarActionPerformed() {
        int filaSeleccionada = vista.tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            int idEvento = (int) vista.tabla.getValueAt(filaSeleccionada, 0);

            int opcion = JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro de que deseas eliminar este evento?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (opcion == JOptionPane.YES_OPTION) {
                boolean exito = modelo.eliminar(idEvento);

                if (exito) {
                    cargarDatosEnTabla();
                    limpiarCampos();
                    JOptionPane.showMessageDialog(vista, "Evento eliminado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar el evento.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(vista, "Selecciona un evento para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCampos() {
        vista.txtNombre.setText("");
        vista.txtTipoEvento.setText("");
        vista.txtFecha.setText("");
        vista.txtHoraInicio.setText("");
        vista.txtHoraFin.setText("");
        vista.txtLugar.setText("");
        vista.txtBoleteria.setText("");
        vista.txtEstado.setText("");
    }

    public static void main(String[] args) {
        EventoFrom vista = new EventoFrom();
        EventoDao modelo = new EventoDao();

        EventoController controlador = new EventoController(vista, modelo);

        controlador.iniciar();
    }
}
