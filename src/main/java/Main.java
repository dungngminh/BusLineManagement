import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/admin_view/LogIn.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("view/admin_view/MainWindow.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("view/seller_view/Dashboard.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("view/admin_view/SchedulePage.fxml"));

        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Log In");
        primaryStage.getIcons().add(new Image("/images/Icon/favicon.png"));
//        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

} 