
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MemoryGame extends Application{

    private int WIDTH = 600;
    private int HEIGHT = 600;

    private String[] images = {"Adao.png","Armin.png","Chelsea.png","Dog.png","Edward.png","Gojo.png","Kakashi.png","Kirito.png","Komi.png","Kunigami.png","Kurapika.png","Liebe.png","Luffy.png","Miki.png","Power.png","Reigen.png","Simon.png","Tenma.png","Urahara.png","Yoriichi.png"};

    private List<Image> list = new ArrayList<Image>();

    private static final int NUM_OF_PAIRS = 20;
    private static final int NUM_PER_ROW = 8;
    private int score;
    private int attempts = 28;
    private int clickCount = 2;
    private Img selected = null;

    @Override
    public void start(Stage stage) throws Exception {

        Pane root = new Pane();
        root.setPrefSize(WIDTH, HEIGHT);

        for(int i = 0; i < 20; i++){
            list.add(new Image(getClass().getResourceAsStream(images[i])));
        }
        
        List<Img> imgs = new ArrayList<>();

        for(int i = 0; i < NUM_OF_PAIRS; i++){
            imgs.add(new Img(list.get(i)));
            imgs.add(new Img(list.get(i)));
        }

        Collections.shuffle(imgs);

        for(int i = 0; i < imgs.size(); i++){
            Img imagem = imgs.get(i);
            imagem.setTranslateX(50 * (i % NUM_PER_ROW));
            imagem.setTranslateY(50 * (i / NUM_PER_ROW));
            root.getChildren().add(imagem);
        }
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Memory Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private class Img extends StackPane{

        private ImageView imv;

        public Img(Image image){

            imv = new ImageView(image);
            setAlignment(Pos.CENTER);
            getChildren().add(imv);
            setStyle("-fx-border-color: black; -fx-border-width: 2");

            setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("Carta.png")),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));

            setOnMouseClicked(this::handleMouseClick);

            close();
        }

        public void handleMouseClick(MouseEvent event){
            if(isOpen() || clickCount == 0)return;
                
                clickCount--;

                if(selected == null){
                    selected = this;
                    open(() -> {});
                }
                else{
                    open(() -> {
                        if(!sameValue(selected)){
                            selected.close();
                            this.close();
                        }
                        selected = null;
                        clickCount = 2;
                    });
                    
                }

        }

        public boolean isOpen(){
            return imv.getOpacity() == 1;
        }

        public boolean sameValue(Img other){
            return imv.getImage().equals(other.imv.getImage());
        }

        public void open(Runnable action){
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), imv);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public void close(){
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), imv);
            ft.setToValue(0);
            ft.play();
        }
    }

    public static void main(String[] args){launch(args);}
}