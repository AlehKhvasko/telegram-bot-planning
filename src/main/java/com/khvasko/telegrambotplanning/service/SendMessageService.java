package com.khvasko.telegrambotplanning.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

import static com.khvasko.telegrambotplanning.constant.BotConstants.*;
import static java.util.Arrays.asList;

@Component
public class SendMessageService {
    private final String instruction = "Do you want to read instruction?";
    private final ButtonsService buttonsService;

    public SendMessageService(ButtonsService buttonsService) {
        this.buttonsService = buttonsService;
    }

    public SendMessage defaultMessage(Update update) {
        return messageToSend(update, "Please enter a valid command");
    }

    public SendMessage createGreetingMessage(Update update) {
        String greetingMessage = "Hi " + update.getMessage().getChat().getFirstName() + ", I'm a BOT to help you plan a daily routine \n"
                + "...and not to forget anything hahahaha";
        ReplyKeyboardMarkup keyboardMarkup =
                buttonsService.setButtons(buttonsService.createButtons(asList(START_PLANNING, END_PLANING, SHOW_DAILYTASK)));
        SendMessage message = messageToSend(update, greetingMessage);
        message.setReplyMarkup(keyboardMarkup);
        return message;
    }

    public SendMessage createPlanningMessage(Update update) {
        String message = "Start to write plans below. After you fininsh press 'END PLANNING'";
        return messageToSend(update, message);
    }

    public SendMessage createEndPlanningMessage(Update update) {
        String message = "Planning ended.\n For list of tasks please press 'All tasks'";
        return messageToSend(update, message);
    }

    public SendMessage messageToSend(Update update, String textMessage) {
        Message updateMessage = update.getMessage();
        Long chatId = updateMessage.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textMessage);
        return sendMessage;
    }

    public SendMessage messageToSendAsList(Update update, List<String> tasks) {
        Message updateMessage = update.getMessage();
        Long chatId = updateMessage.getChatId();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        StringBuilder message = new StringBuilder();
        for(String s : tasks){
            message.append(s).append("\n");
        }
        sendMessage.setText(message.toString());
        return sendMessage;
    }

    public SendMessage createInstructionMessage(Update update){
        SendMessage sendMessage = messageToSend(update, instruction);
        InlineKeyboardMarkup replyKeyboardMarkup =
                buttonsService.setInlineKeyboard(buttonsService.createInlineButton("Yes"));
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public EditMessageText createEditMessage(Update update, String instruction) {
        EditMessageText editMessageText = new EditMessageText();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId((int) messageId);
        editMessageText.setText(instruction);
        return editMessageText;
    }
}
