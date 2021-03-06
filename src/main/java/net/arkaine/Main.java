package net.arkaine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

public class Main extends Application {
    private static String urlApiAllCoins ="https://min-api.cryptocompare.com/data/all/coinlist";
    public static JSONObject json = getJson(urlApiAllCoins);


    @Override
    public void start(Stage primaryStage) throws Exception{
        initProperties();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Cours crypto");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(600.0);primaryStage.setMinWidth(800.0);
        primaryStage.show();
    }

    public static Properties properties = new Properties();

    public static void initProperties() throws IOException{
        URL url = null;
        File file = new File("liste.properties");
        if(file.isFile())
            properties.load(new FileInputStream(file));
        else{
            url = Main.class.getResource("/liste.properties");
            if(url != null && url.getFile() != null) {
                properties.load(new InputStreamReader((url.openStream()),"UTF-8"));
            }
        }
    }

    public static JSONObject getJson(String urlApi){
        JSPCatcher parser = new JSPCatcher();

        try {
            URL url = new URL(urlApi); // URL to Parse
            URLConnection urlCon = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));

            String inputLine;
            StringBuilder sb = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            JSONObject json = (JSONObject) parser.parse(sb.toString());
            JSONObject jsonData;
            if(json.get("Data") != null && json.get("Data").getClass().equals(JSONObject.class))
                jsonData = (JSONObject) json.get("Data");
            else
                jsonData = json;
            in.close();
            return jsonData;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
