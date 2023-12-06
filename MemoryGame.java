
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MemoryGame extends Application{

    private int WIDTH = 600;
    private int HEIGHT = 600;

    private Text scoreText;
    private Text attemptsText;
    private Text gameOver;

    private String[] images = {"Adao.png","Armin.png","Chelsea.png","Dog.png","Edward.png","Gojo.png","Kakashi.png","Kirito.png","Komi.png","Kunigami.png","Kurapika.png","Liebe.png","Luffy.png","Miki.png","Power.png","Reigen.png","Simon.png","Tenma.png","Urahara.png","Yoriichi.png"};

    private List<Image> list = new ArrayList<Image>();

    private static final int NUM_OF_PAIRS = 20;
    private static final int NUM_PER_ROW = 8;
    private static int score = 0;
    private static int attempts = 34;
    private int clickCount = 2;
    private Img selected = null;

    @Override
    public void start(Stage stage) throws Exception {

        Pane root = new Pane();
        Pane root2 = new Pane();
        root2.setPrefSize(300, 400);
        root2.setLayoutX(95);
        root2.setLayoutY(110);
        

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
            root2.getChildren().add(imagem);
        }

        Text restart = new Text();
        restart.setText("Click here to Restart");
        restart.setFont(Font.font(25));
        restart.setStroke(Color.GREEN);
        restart.setStrokeWidth(3);
        restart.setTextAlignment(TextAlignment.CENTER);
        restart.setLayoutX(170);
        restart.setLayoutY(530);
        restart.setOnMouseClicked(e -> {

            gameOver.setVisible(false);

            imgs.clear();
            
            for(int i = 0; i < NUM_OF_PAIRS; i++){
                imgs.add(new Img(list.get(i)));
                imgs.add(new Img(list.get(i)));
            }

            Collections.shuffle(imgs);

            for(int i = 0; i < imgs.size(); i++){
                Img imagem = imgs.get(i);
                imagem.setTranslateX(50 * (i % NUM_PER_ROW));
                imagem.setTranslateY(50 * (i / NUM_PER_ROW));
                root2.getChildren().add(imagem);
            }

            attempts = 34;
            score = 0;
        });

        Text text = new Text();
        text.setText("Made by Gustavo7327");
        text.setFont(Font.font(14));
        text.setStroke(Color.GREEN);
        text.setStrokeWidth(2);
        text.setLayoutX(210);
        text.setLayoutY(580);

        gameOver = new Text();
        scoreText = new Text();
        attemptsText = new Text();
        root.getChildren().addAll(root2, scoreText, attemptsText, gameOver, restart, text);
        root.setStyle("-fx-background-color: black;");

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(scoreText,attemptsText, gameOver)));
        tl.setCycleCount(Timeline.INDEFINITE);

        
        Scene scene = new Scene(root);
        stage.setTitle("Memory Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        tl.play();
    }

    public void run(Text scoreText, Text attemptsText, Text gameOver){
        scoreText.setText("Score: "+score);
        scoreText.setFont(Font.font(26));
        scoreText.setStroke(Color.GREEN);
        scoreText.setStrokeWidth(3);
        scoreText.setLayoutX(20);
        scoreText.setLayoutY(50);

        attemptsText.setText("Attempts: "+attempts);
        attemptsText.setFont(Font.font(26));
        attemptsText.setStroke(Color.GREEN);
        attemptsText.setStrokeWidth(3);
        attemptsText.setLayoutX(400);
        attemptsText.setLayoutY(50);

        if(score == 20){
            gameOver.setText("You Win");
            gameOver.setFont(Font.font(35));
            gameOver.setStroke(Color.GREEN);
            gameOver.setStrokeWidth(4);
            gameOver.setLayoutX(195);
            gameOver.setLayoutY(440);
            gameOver.setTextAlignment(TextAlignment.CENTER);
            gameOver.setVisible(true);
        }
        else if(attempts == 0){
            gameOver.setText("Game Over");
            gameOver.setFont(Font.font(35));
            gameOver.setStroke(Color.RED);
            gameOver.setStrokeWidth(4);
            gameOver.setLayoutX(195);
            gameOver.setLayoutY(440);
            gameOver.setTextAlignment(TextAlignment.CENTER);
            gameOver.setVisible(true);
        }
    }

    private class Img extends StackPane{

        private ImageView imv;

        public Img(Image image){

            imv = new ImageView(image);
            setAlignment(Pos.CENTER);
            getChildren().add(imv);
            setStyle("-fx-border-color: green; -fx-border-width: 2");

            setBackground(new Background(new BackgroundImage(new Image(getClass().getResourceAsStream("Carta.png")),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));

            setOnMouseClicked(this::handleMouseClick);

            close();
        }

        public void handleMouseClick(MouseEvent event){
            if(isOpen() || clickCount == 0 || attempts == 0)return;
                
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
                        } else{
                            score++;
                        }
                        selected = null;
                        clickCount = 2;
                        attempts--;
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