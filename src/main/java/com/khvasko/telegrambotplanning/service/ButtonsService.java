package com.khvasko.telegrambotplanning.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ButtonsService {
    public ReplyKeyboardMarkup setButtons(List<KeyboardRow> keyboardRowList) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    public List<KeyboardRow> createButtons(List<String> buttonsName) {
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        for (String buttonName : buttonsName) {
            keyboardRow.add(new KeyboardButton(buttonName));
        }
        keyboardRows.add(keyboardRow);
        return keyboardRows;
    }

    public List<List<InlineKeyboardButton>> createInlineButton(String buttonName){
        List<List<InlineKeyboardButton>> keyboardList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setText(buttonName);
        inlineKeyboardButton.setCallbackData(buttonName);
        keyboardRow.add(inlineKeyboardButton);
        keyboardList.add(keyboardRow);
        return keyboardList;
    }

    public InlineKeyboardMarkup setInlineKeyboard(List<List<InlineKeyboardButton>> keyboardList ){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboardList);
        return inlineKeyboardMarkup;
    }
}
