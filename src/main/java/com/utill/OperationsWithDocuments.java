package com.utill;

import com.TgDictionaryBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Component
public class OperationsWithDocuments {
    @Autowired
    private TgDictionaryBot tgDictionaryBot;

    private String fileId;
    private String fileName;
    private long fileSize;

    public void saveFile(Update update){

        fileId = update.getMessage().getDocument().getFileId();
        fileName = update.getMessage().getDocument().getFileName();
        fileSize = update.getMessage().getDocument().getFileSize();

        Document document = new Document();
        document.setFileName(fileName);
        document.setFileSize(fileSize);
        document.setFileId(fileId);

        GetFile getFile = new GetFile();
        getFile.setFileId(document.getFileId());

        try {
            org.telegram.telegrambots.meta.api.objects.File file = tgDictionaryBot.execute(getFile);
           tgDictionaryBot.downloadFile(file, new File("./src/main/resources/userFiles/"+fileName));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public File getFile(){
       return new File("./src/main/resources/userFiles/"+fileId);
    }

    public void deleteFile(String fileId){
        File file = new File("./src/main/resources/userFiles/"+fileId);
        file.delete();
    }

    public String getFileId() {
        return fileId;
    }


}
