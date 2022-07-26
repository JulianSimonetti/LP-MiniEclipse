module org.formation.eeos.minieclipse {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
	requires java.logging;
	requires javafx.base;
	requires javafx.graphics;
	requires java.desktop;
	requires java.xml;

    opens org.formation.eeos.minieclipse.view to javafx.fxml, javafx.graphics, javafx.web;
    exports org.formation.eeos.minieclipse.view;
    opens org.formation.eeos.minieclipse to javafx.fxml, javafx.graphics, javafx.web;
    exports org.formation.eeos.minieclipse;
}
