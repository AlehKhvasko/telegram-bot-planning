package com.khvasko.telegrambotplanning.service;

import com.khvasko.telegrambotplanning.DBLocal.BaseStore;
import com.khvasko.telegrambotplanning.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;

import static com.khvasko.telegrambotplanning.constant.BotConstants.*;

@Component
public class BotPlanner extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final SendMessageService sendMessageService;
    private final BaseStore baseStore;
    private boolean startToPlan = false;

    public BotPlanner(BotConfig botConfig, SendMessageService sendMessageService, BaseStore baseStore) {
        this.botConfig = botConfig;
        this.sendMessageService = sendMessageService;
        this.baseStore = baseStore;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case START -> {
                    executeMessage(sendMessageService.createGreetingMessage(update));
                    executeMessage(sendMessageService. createInstructionMessage(update));
                }
                case START_PLANNING -> {
                    startToPlan = true;
                    executeMessage(sendMessageService.createPlanningMessage(update));
                }
                case SHOW_DAILYTASK -> {
                    if (!startToPlan) {
                        executeMessage(sendMessageService.messageToSendAsList(update, baseStore.selectAll(LocalDate.now())));
                    }
                }
                case END_PLANING -> {
                    startToPlan = false;
                    executeMessage(sendMessageService.createEndPlanningMessage(update));
                }
                default -> {
                    if (startToPlan) {
                        baseStore.save(LocalDate.now(), update.getMessage().getText());
                    }
                }
            }
        }
        if (update.hasCallbackQuery()){
            String instruction = "Bot is for making your day moreproductive. For using this bot, follow his instructions.";
            String callBackData = update.getCallbackQuery().getData();
            switch (callBackData.toLowerCase()){
                case "yes":
                    EditMessageText text = sendMessageService.createEditMessage(update, instruction);
                    try {
                        execute(text);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    private void executeMessage(SendMessage sendmessage) {
        try {
            execute(sendmessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


}
