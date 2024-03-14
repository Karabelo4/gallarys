package com.example.images;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private String[] imagePaths = {
            "20.jpeg",
            "21.jpeg",
            "22.jpeg",
            "23.jpeg",
            "24.jpeg",
            "25.jpeg",
            "26.jpeg",
            "27.jpeg",
            "28,jpg",
            "31.jpeg",
            // Add more image paths as needed
    };

    private ImageView selectedImageView = new ImageView();
    private int currentIndex = 0;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Food Aesthetics");

        GridPane gridPane = createImageGrid();
        addImageClickEvent(gridPane);

        // Declare buttons before applying style classes
        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> showPreviousImage());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> showNextImage());

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> clearSelectedImage());

        // Apply style classes to buttons
        prevButton.getStyleClass().add("button");
        nextButton.getStyleClass().add("button");
        clearButton.getStyleClass().add("button");

        HBox buttonBox = new HBox(prevButton, nextButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox root = new VBox(buttonBox, gridPane); // Use VBox to stack buttons above the grid
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/images/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearSelectedImage() {
        selectedImageView.setImage(null);
    }

    /* Add this method inside the createImageGrid() method */
    private void addHoverEffect(ImageView imageView) {
        imageView.setOnMouseEntered(event -> {
            imageView.setStyle("-fx-border-color: #ff4500;");
        });

        imageView.setOnMouseExited(event -> {
            imageView.setStyle("-fx-border-color: #333;");
        });
    }


    private GridPane createImageGrid() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        int col = 0;
        int row = 0;

        for (String imagePath : imagePaths) {
            try {
                // Load image resource
                Image image = new Image(getClass().getResourceAsStream(imagePath));

                // Create ImageView and set properties
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);

                // Add a style class to the ImageView
                imageView.getStyleClass().add("thumbnail");

                // Add ImageView to the GridPane
                gridPane.add(imageView, col, row);

                col++;
                if (col == 3) {
                    col = 0;
                    row++;
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagePath);
                e.printStackTrace();
            }
        }

        return gridPane;
    }

    private void addImageClickEvent(GridPane gridPane) {
        gridPane.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof ImageView) {
                ImageView clickedImageView = (ImageView) event.getTarget();
                selectedImageView.setImage(clickedImageView.getImage());
                showFullSizeImage();
            }
        });
    }

    private void showPreviousImage() {
        if (currentIndex > 0) {
            currentIndex--;
            Image image = new Image(getClass().getResourceAsStream(imagePaths[currentIndex]));
            selectedImageView.setImage(image);
        }
    }

    private void showNextImage() {
        if (currentIndex < imagePaths.length - 1) {
            currentIndex++;
            Image image = new Image(getClass().getResourceAsStream(imagePaths[currentIndex]));
            selectedImageView.setImage(image);
        }
    }

    private void showFullSizeImage() {
        Stage fullSizeStage = new Stage();
        fullSizeStage.setTitle("Full Size Image");

        ImageView fullSizeImageView = new ImageView(selectedImageView.getImage());
        fullSizeImageView.setFitWidth(400);
        fullSizeImageView.setFitHeight(400);

        Button prevButton = new Button("Previous");
        prevButton.setOnAction(event -> {
            showPreviousImage();
            fullSizeImageView.setImage(selectedImageView.getImage());
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> {
            showNextImage();
            fullSizeImageView.setImage(selectedImageView.getImage());
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(event -> {
            clearSelectedImage();
            fullSizeImageView.setImage(null);
        });

        prevButton.getStyleClass().add("button");
        nextButton.getStyleClass().add("button");
        clearButton.getStyleClass().add("button");

        HBox buttonBox = new HBox(prevButton, nextButton, clearButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);

        VBox root = new VBox(buttonBox, fullSizeImageView);
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root);
        fullSizeStage.setScene(scene);
        fullSizeStage.show();
    }

}
