module co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires java.logging;
    requires java.desktop;


    opens co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx to javafx.fxml;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController;
    opens co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.ViewController to javafx.fxml;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Dto;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Mapping.Mappers;
    exports co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Model;
    opens co.edu.uniquindio.billeteravirtualfx.billeteravirtualfx.Controller to javafx.fxml;
}