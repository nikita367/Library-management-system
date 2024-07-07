module com.nikitagiri.librarymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires servlet.api;
    requires org.json;
    requires java.sql;
    requires javafx.base;

    opens com.nikitagiri.librarymanagementsystem to javafx.fxml;
    exports com.nikitagiri.librarymanagementsystem;

    opens com.nikitagiri.librarymanagementsystem.classes to javafx.base;
    exports com.nikitagiri.librarymanagementsystem.classes;
}