//package com;
//
//import org.telegram.telegrambots.meta.api.objects.Message;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//
//public class SendMsg {
//
//   private Message message = new Message();
//    private TgDictionaryBot tgDictionaryBot;
//
//
//    public void sendMessage(String text) {
//        SendMsg sendMessage = new SendMsg();
//        sendMessage.setText(text);
//        sendMessage.setChatId(message.getChatId());
//        try {
//          tgDictionaryBot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
