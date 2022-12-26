package com.khvasko.telegrambotplanning.config;

import com.khvasko.telegrambotplanning.service.BotPlanner;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInit {
    private final BotPlanner botPlanner;

    public BotInit(BotPlanner botPlanner) {
        this.botPlanner = botPlanner;
    }


    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(botPlanner);
        } catch (TelegramApiException e) {
            //log.error("Error in BOT creation " + e.getMessage());
        }
    }
}
