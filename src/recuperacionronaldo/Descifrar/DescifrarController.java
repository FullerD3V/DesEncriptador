/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recuperacionronaldo.Descifrar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import recuperacionronaldo.RecuperacionRonaldo;
import recuperacionronaldo.Tool.CifradoCesar;

/**
 * FXML Controller class
 *
 * @author fuller
 */
public class DescifrarController implements Initializable {
    
    @FXML
    private Button btnCifrar, btnDescifrar;
    
    @FXML
    private TextArea txtArea1;
    
    @FXML
    private ComboBox<String> archivos1;
    
    @FXML 
    private String texto1;
    
    @FXML
    private Label etiqueta;
    
    @FXML
    private Slider slide;
    
    int condition;
    
    @FXML
    public void archivos1OnAction() {
        texto1 = "";
        //Mostrar contenido del archivo        
        txtArea1.clear();
        try {
            Scanner input = new Scanner(new File("FILES/" + archivos1.getValue()));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                texto1 += line;
                texto1 += "\n";
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        txtArea1.setText(texto1);
    }
    
    @FXML
    private void btnCifrarOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCifrar.getScene().getWindow();
        Parent root = FXMLLoader.load(RecuperacionRonaldo.class.getResource("Main/FXMLDocument.fxml"));
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setTitle("Cifrar");
        newStage.setScene(scene);
        newStage.show();
        stage.close();
       
    }
    
    @FXML
    private void btnDescifrarOnAction(ActionEvent event) throws IOException {
               
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        File f = new File("FILES");
        File[] files = f.listFiles();
        for (int i = 0; i < files.length; i++) {
            archivos1.getItems().add(files[i].getName());    
        }
        
        slide.valueProperty().addListener(new ChangeListener<Number>() {
               
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                condition = (int) slide.getValue();
                if(condition == 100){
                    String textoDescifrado = CifradoCesar.descifradoCesar(txtArea1.getText(), 1);
                    txtArea1.setText(textoDescifrado);
                    FileWriter fichero = null;
                    PrintWriter pw = null;
                    try
                    {
                        fichero = new FileWriter("FILES/"+archivos1.getValue());
                        
                        pw = new PrintWriter(fichero);

                        
                        pw.println(textoDescifrado);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                       try {
                       // Nuevamente aprovechamos el finally para 
                       // asegurarnos que se cierra el fichero.
                       if (null != fichero)
                          fichero.close();
                       } catch (Exception e2) {
                          e2.printStackTrace();
                       }
                    }
                }
            }
            
        });
    }    
    
}
