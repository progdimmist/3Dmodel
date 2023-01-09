package com.cgvsu;


import com.cgvsu.math.AffineTransform;
import com.cgvsu.math.exception.MathExceptions;
import com.cgvsu.math.vector.Vector3F;
import com.cgvsu.rasterization.DrawUtilsJavaFX;
import com.cgvsu.rasterization.GraphicsUtils;
import com.cgvsu.render_engine.RenderRasterization;
import com.cgvsu.triangle.Triangle;
import com.cgvsu.model.Polygon;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.render_engine.RenderEngine;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;


public class GuiController {

    final private float TRANSLATION = 0.5F;
    private boolean isStructure = false;
    public static boolean isLight = true;

    private final static boolean willItWriteInformationToConsole = true;
    private BufferedImage image = null;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    @FXML
    private ComboBox<String> chooseModel;
    @FXML
    private ComboBox<String> chooseCamera;

    private String selectedValue;
    private String selectedValueCamera;
    private final List<String> names = new ArrayList<>();
    private final List<String> namesCamera = new ArrayList<>();
    private final List<Model> mesh = new ArrayList<>();

    private List<Camera> camera = new ArrayList<>(Arrays.asList(new Camera(
            new Vector3F(0, 00, 100),
            new Vector3F(0, 0, 0),
            1.0F, 1, 0.01F, 100)));

    private int numberCamera = 0;
    public static int numberMesh = 0;

    private Timeline timeline;

    @FXML
    private void initialize() {
        if (mesh.size() == 0) {
            chooseCamera.getItems().add(String.valueOf(numberCamera));
            namesCamera.add(String.valueOf(numberCamera));
        }
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        GraphicsUtils<Canvas> graphicsUtils = new DrawUtilsJavaFX(canvas);

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.get(numberCamera).setAspectRatio((float) (width / height));

            if (mesh.size() != 0) {
                try {
                    RenderRasterization.render(canvas.getGraphicsContext2D(), graphicsUtils, camera.get(numberCamera), mesh.get(numberMesh), (int) width, (int) height, image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (isStructure) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), graphicsUtils, camera.get(numberCamera), mesh.get(numberMesh), (int) width, (int) height);
                }

            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh.add(ObjReader.read(fileContent));
            names.add(file.getName());
            chooseModel.getItems().add(file.getName());
            // todo: обработка ошибок
        } catch (IOException exception) {

        }

        for (int i = 0; i < mesh.get(mesh.size() - 1).polygons.size(); i++) {
            mesh.get(mesh.size() - 1).trianglePolygons.add(new Polygon());
            mesh.get(mesh.size() - 1).trianglePolygons.get(i).getVertexIndices().addAll(mesh.get(mesh.size() - 1).polygons.get(i).getVertexIndices());
            mesh.get(mesh.size() - 1).trianglePolygons.get(i).getTextureVertexIndices().addAll(mesh.get(mesh.size() - 1).polygons.get(i).getTextureVertexIndices());
            mesh.get(mesh.size() - 1).trianglePolygons.get(i).getNormalIndices().addAll(mesh.get(mesh.size() - 1).polygons.get(i).getNormalIndices());
        }
        ArrayList<Polygon> triangles = Triangle.triangulatePolygon(mesh.get(mesh.size() - 1).trianglePolygons);
        mesh.get(mesh.size() - 1).setTrianglePolygons(triangles);
    }

    @FXML
    private void saveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");
        fileChooser.setInitialFileName("NewFileOBJ");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            ArrayList<String> fileContent = ObjWriter.write(mesh.get(mesh.size() - 1));
            FileWriter writer = new FileWriter(fileName.toFile());
            for (String s : fileContent) {
                writer.write(s + "\n");

            }
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            // Handle exception
        }

    }

    @FXML
    private void loadLight() {
        isLight = !isLight;
    }

    @FXML
    private void loadStructure() {
        isStructure = !isStructure;
    }

    @FXML
    private void loadTexture() throws IOException {

        if (!mesh.get(numberMesh).isTexture) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG (*.jpg)", "*.jpg"));
            fileChooser.setTitle("Load jpg");
            File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());

            if (file == null) {
                return;
            }
            image = ImageIO.read(file);
        }
        mesh.get(numberMesh).isTexture = !mesh.get(numberMesh).isTexture;

    }


    @FXML
    public void addCamera() {
        camera.add(new Camera(
                new Vector3F(0, 0, 100),
                new Vector3F(0, 0, 0),
                1.0F, 1, 0.01F, 100));
        numberCamera++;
        namesCamera.add(String.valueOf(numberCamera));
        chooseCamera.getItems().add(String.valueOf(numberCamera));
    }

    @FXML
    public void deleteCamera() {
        if (camera.size() > 1) {
            if (numberCamera == camera.size() - 1) numberCamera--;
            camera.remove(camera.size() - 1);
            names.remove(camera.size() - 1);
            chooseCamera.getItems().remove(numberCamera + 1);
        }
    }

    @FXML
    public void deleteMesh() {
        if (mesh.size() > 1) {
            if (numberMesh == mesh.size() - 1) numberMesh--;
            mesh.remove(mesh.size() - 1);
            names.remove(mesh.size() - 1);
            chooseModel.getItems().remove(numberMesh + 1);
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.get(numberCamera).scalePosition(new Vector3F(0.98f, 0.98f, 0.98f));

    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.get(numberCamera).scalePosition(new Vector3F(1.05f, 1.05f, 1.05f));

    }

    @FXML
    public void demonstrationAffineTransform(){
        // МЕТОД ВЫЗЫВАЕТСЯ ЧТОБЫ ПОКАЗАТЬ РАБОЧЕСТЬ АФФИННЫХ ПРЕОБРАЗОВАНИЙ
        // КНОПОЧКИ И СОХРАНЕНИЕ ПРОШУ ПРИКРУТИТЬ ЗА МЕНЯ
        scale();
    }


    @FXML
    public void handleCameraLeftAroundTarget(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationAroundChangedY(Math.PI / 30);
    }

    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3F(TRANSLATION, 0, 0));
        camera.get(numberCamera).moveTarget(new Vector3F(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRightAroundTarget(ActionEvent actionEvent) {

        camera.get(numberCamera).rotationAroundChangedY(-Math.PI / 30);
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3F(-TRANSLATION, 0, 0));
        camera.get(numberCamera).moveTarget(new Vector3F(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraAroundX(ActionEvent actionEvent) {
        //camera.get(numberCamera).rotationPositionAroundX(3);

        camera.get(numberCamera).rotationAroundChangedX(Math.PI / 20);
    }

    @FXML
    public void handleCameraAroundXBack(ActionEvent actionEvent) {
        //camera.get(numberCamera).rotationPositionAroundX(-3);

        camera.get(numberCamera).rotationAroundChangedX(-Math.PI / 20);
    }

    @FXML
    public void cameraGetPositionOnConsole() {
        System.out.println(Arrays.toString(camera.get(numberCamera).getPosition().getValues()));
    }

    //название сокращено в силу отсутствия слов в голове
    public void scale() {
        AffineTransform affineTransform = new AffineTransform();

        affineTransform.transformingVertex = mesh.get(numberMesh).vertices;


        affineTransform.transforming(3, 3, 3, 10, -10, 0,
                2, -5, 0);
        mesh.get(numberMesh).setVertices(affineTransform.transformedVertex);

    }

    @FXML
    public void handleCameraAroundY(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationPositionAroundY(3);
    }

    @FXML
    public void handleCameraAroundYBack(ActionEvent actionEvent) {
        camera.get(numberCamera).rotationPositionAroundY(-3);
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3F(0, TRANSLATION, 0));
        camera.get(numberCamera).moveTarget(new Vector3F(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.get(numberCamera).movePosition(new Vector3F(0, -TRANSLATION, 0));
        camera.get(numberCamera).moveTarget(new Vector3F(0, -TRANSLATION, 0));
    }

    @FXML
    public void choosingActualModel(ActionEvent actionEvent) {
        selectedValue = chooseModel.getSelectionModel().getSelectedItem();
        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals(selectedValue)) {
                numberMesh = i;
            }
        }

    }

    @FXML
    public void choosingCamera(ActionEvent actionEvent) {
        selectedValueCamera = chooseCamera.getSelectionModel().getSelectedItem();
        for (int i = 0; i < namesCamera.size(); i++) {
            if (namesCamera.get(i).equals(selectedValueCamera)) {
                numberCamera = i;
            }
        }

    }
}