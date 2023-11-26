
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MemoryGame extends Application{

    private int WIDTH = 900;
    private int HEIGHT = 600;

    @Override
    public void start(Stage arg0) throws Exception {
        Canvas canvas = new Canvas(WIDTH,HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        Stage stage = new Stage();
        stage.setTitle("Memory Game");
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        stage.setResizable(false);
        tl.play();
    }

    public void run(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public static void main(String[] args){launch(args);}
}