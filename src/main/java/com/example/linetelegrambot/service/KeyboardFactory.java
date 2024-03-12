package com.example.linetelegrambot.service;


import com.example.linetelegrambot.interfaces.ButtonInterface;
import com.example.linetelegrambot.interfaces.LanguageInterface;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class KeyboardFactory {
    public static ReplyKeyboardMarkup getLanguageSelectKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(LanguageInterface.OZBEKCHA);
        row1.add(LanguageInterface.RUSSIAN);
        row1.add(LanguageInterface.ENGLISH);

        keyboardMarkup.setKeyboard(List.of(row1));
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup getActionButtons(String language) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        KeyboardRow row1 = new KeyboardRow();
        row1.add(getDownloadAndroidAPK(language));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(getDownloadIOS(language));


        KeyboardRow row3 = new KeyboardRow();
        row3.add(getGoToWebsite(language));

        KeyboardRow row4 = new KeyboardRow();
        row4.add(getGetBonus(language));

        KeyboardRow row5 = new KeyboardRow();
        row5.add(getViewPromotions(language));

        KeyboardRow row6 = new KeyboardRow();
        row6.add(getSettings(language));

        keyboardMarkup.setKeyboard(List.of(row1, row2, row3, row4, row5, row6));
        return keyboardMarkup;
    }

    private static String getDownloadAndroidAPK(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.DOWNLOAD_ANDROID_APK;
            case "ru":
                return ButtonInterface.Russian.DOWNLOAD_ANDROID_APK;
            case "en":
            default:
                return ButtonInterface.English.DOWNLOAD_ANDROID_APK;
        }
    }

    private static String getDownloadIOS(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.DOWNLOAD_IOS;
            case "ru":
                return ButtonInterface.Russian.DOWNLOAD_IOS;
            case "en":
            default:
                return ButtonInterface.English.DOWNLOAD_IOS;
        }
    }

    private static String getGoToWebsite(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.GO_TO_WEBSITE;
            case "ru":
                return ButtonInterface.Russian.GO_TO_WEBSITE;
            case "en":
            default:
                return ButtonInterface.English.GO_TO_WEBSITE;
        }
    }

    private static String getGetBonus(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.GET_BONUS;
            case "ru":
                return ButtonInterface.Russian.GET_BONUS;
            case "en":
            default:
                return ButtonInterface.English.GET_BONUS;
        }
    }

    private static String getViewPromotions(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.VIEW_PROMOTIONS;
            case "ru":
                return ButtonInterface.Russian.VIEW_PROMOTIONS;
            case "en":
            default:
                return ButtonInterface.English.VIEW_PROMOTIONS;
        }
    }

    private static String getSettings(String language) {
        switch (language) {
            case "uz":
                return ButtonInterface.Uzbek.SETTINGS;
            case "ru":
                return ButtonInterface.Russian.SETTINGS;
            case "en":
            default:
                return ButtonInterface.English.SETTINGS;
        }
    }
}
