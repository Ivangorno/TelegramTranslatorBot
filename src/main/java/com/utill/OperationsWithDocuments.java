package com.utill;

import com.TgDictionaryBot;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@Component
public class OperationsWithDocuments {

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
            dictionaryFunctions.addWord(collectWordsFromMap(pair));
        }
    }

    private static JSONObject parseJson(File file) {
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
}
