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
    private String fileId;
    private String fileName;
    private long fileSize;

    public void saveFile(Update update) {

        fileName = update.getMessage().getDocument().getFileName();
        Document document = update.getMessage().getDocument();
        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());

        try {
            org.telegram.telegrambots.meta.api.objects.File file = tgDictionaryBot.execute(getFile);
            File downloadedFile = tgDictionaryBot.downloadFile(file, new File("./src/main/resources/userFiles/" + fileName));

            JSONObject inputJson = parseJson(downloadedFile);
            sendFromFileToDictionary(inputJson);
            downloadedFile.delete();

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendFromFileToDictionary(JSONObject jsonObject) {

        if (jsonObject.has("add")) {
            getWordsFromJsonObject("add", jsonObject);
        }

        if (jsonObject.has("update")) {
            getWordsFromJsonObject("update", jsonObject);
        }

        if (jsonObject.has("delete")) {
            JSONArray deleteWordsFromJson = jsonObject.getJSONArray("delete");
            for (int i = 0; i < deleteWordsFromJson.length(); i++) {
                String[] emptyArray = new String[1];
                emptyArray[0] = deleteWordsFromJson.getString(i);

                dictionaryFunctions.deleteWord(emptyArray);
            }
        }
    }

    private JSONObject parseJson(File file) {
        String text = null;
        try {
            text = Files.readString(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new JSONObject(text);
    }

    public File getFile() {
        return new File("./src/main/resources/userFiles/" + fileId);
    }

    public String getFileId() {
        return fileId;
    }

    private void getWordsFromJsonObject(String keyword, JSONObject jsonObject) {
        String[] text = new String[2];
        JSONArray addWordsFromJson = jsonObject.getJSONArray(keyword);

        for (int i = 0; i < addWordsFromJson.length(); i++) {
            Map<String, Object> pair = addWordsFromJson.getJSONObject(i).toMap();
            text[0] = pair.keySet().stream().findFirst().get();
            text[1] = (String) pair.values().stream().findFirst().get();

            if (keyword.contentEquals("add")) {
                dictionaryFunctions.addWord(text);
            }

            if (keyword.contentEquals("update")) {
                dictionaryFunctions.updateWord(text);
            }
        }
    }

}
