package com.utill;

import com.TgDictionaryBot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Component
public class OperationsWithDocuments {
    @Autowired
    @Qualifier("connection")
    private Connection connection;

    @Autowired
    private TgDictionaryBot tgDictionaryBot;
    @Autowired
    private DictionaryFunctions dictionaryFunctions;

    public void saveFile(Update update) {
        String fileName = update.getMessage().getDocument().getFileName();
        Document document = update.getMessage().getDocument();
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());

        try {
            File downloadedFile = tgDictionaryBot.downloadFile(tgDictionaryBot.execute(getFile), new File("./src/main/resources/userFiles/" + fileName));

            JSONObject inputJson = parseJson(downloadedFile);
            sendFromFileToDictionary(inputJson);
            downloadedFile.delete();

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendFromFileToDictionary(JSONObject jsonObject) {
        if (jsonObject.has("add")) {
            addWords(jsonObject);
        }
        if (jsonObject.has("update")) {
            updateWords(jsonObject);
        }
        if (jsonObject.has("delete")) {
            deleteWords(jsonObject);
        }
    }

    private void deleteWords(JSONObject jsonObject) {
        JSONArray deleteWordsFromJson = jsonObject.getJSONArray("delete");
        for (int i = 0; i < deleteWordsFromJson.length(); i++) {
            String[] emptyArray = new String[1];
            emptyArray[0] = deleteWordsFromJson.getString(i);
            dictionaryFunctions.deleteWord(emptyArray);
        }
    }

    private void addWords(JSONObject jsonObject) {
        JSONArray addWordsFromJson = jsonObject.getJSONArray("add");
        for (int i = 0; i < addWordsFromJson.length(); i++) {
            Map<String, Object> pair = addWordsFromJson.getJSONObject(i).toMap();
            dictionaryFunctions.addWord(collectWordsFromMap(pair));
        }
    }

    private void updateWords(JSONObject jsonObject) {
        JSONArray addWordsFromJson = jsonObject.getJSONArray("update");
        for (int i = 0; i < addWordsFromJson.length(); i++) {
            Map<String, Object> pair = addWordsFromJson.getJSONObject(i).toMap();
            dictionaryFunctions.updateWord(collectWordsFromMap(pair));
        }
    }

    public static JSONObject parseJson(File file) {
        String text;
        try {
            text = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JSONObject(text);
    }

    private static String[] collectWordsFromMap(Map<String, Object> pair) {
        String[] text = new String[2];
        text[0] = pair.keySet().stream().findFirst().get();
        text[1] = (String) pair.values().stream().findFirst().get();
        return text;
    }

    public JSONObject saveDbToJson()  {
        String sqlCommand;
        JSONObject jsonObject = new JSONObject();

        try {
            Statement statement = connection.createStatement();

            sqlCommand = "SELECT * FROM english_dictionary";
            ResultSet resultSet = statement.executeQuery(sqlCommand);

            while (resultSet.next()) {
                jsonObject.put(resultSet.getString("english"), resultSet.getString("french"));
            }

            return jsonObject;

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void saveDbFileLocally(JSONObject jsonDb){
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "myDb.json";

        try {
            FileWriter file = new FileWriter("./src/main/resources/userFiles/" + fileName);
            file.write(jsonDb.toString());
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument(){

            File fileToDelete =  new File("./src/main/resources/userFiles/myDb.json");
            fileToDelete.delete();
    }

    public InputFile getFile(JSONObject myDB){

        InputFile file =  new InputFile().setMedia( new File("./src/main/resources/userFiles/myDb.json"));
        return file;
    }

}
