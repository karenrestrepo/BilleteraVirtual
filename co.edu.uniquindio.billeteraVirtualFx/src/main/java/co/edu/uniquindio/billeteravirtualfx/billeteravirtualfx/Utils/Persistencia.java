package co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Utils;

import co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model.BillerteraVirtual;

public class Persistencia {
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/Persistencia/log/BilleteraVirtualLog.txt";
    public static final String RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML = "src/main/resources/Persistencia/model.xml";
    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);

    }

    public static void guardarRecursoBilleteraXML(BillerteraVirtual billerteraVirtual) {
        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_BILLETERAVIRTUAL_XML, billerteraVirtual);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
