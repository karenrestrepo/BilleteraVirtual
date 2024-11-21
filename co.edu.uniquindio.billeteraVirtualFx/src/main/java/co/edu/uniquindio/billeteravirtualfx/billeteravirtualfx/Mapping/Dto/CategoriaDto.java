package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto;

public record CategoriaDto (
        String idCategoria,
        String nombre,
        String descripcion
) {

    @Override
    public String toString() {
        return nombre;
    }
}
