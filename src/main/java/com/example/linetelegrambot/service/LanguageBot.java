package com.example.linetelegrambot.service;

import com.example.linetelegrambot.config.BotConfig;
import com.example.linetelegrambot.interfaces.ButtonInterface;
import com.example.linetelegrambot.interfaces.LanguageInterface;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LanguageBot extends TelegramLongPollingBot {
    private Map<Long, String> userLanguages = new HashMap<>();

    final BotConfig config;

    public LanguageBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                long chatId = message.getChatId();
                String language = userLanguages.getOrDefault(chatId, "en");

                switch (text) {
                    case "/start":
                        sendLanguageSelectMessage(chatId);
                        break;
                    case LanguageInterface.OZBEKCHA:
                        userLanguages.put(chatId, "uz");
                        sendActionButtons(chatId, "uz");
                        break;
                    case LanguageInterface.RUSSIAN:
                        userLanguages.put(chatId, "ru");
                        sendActionButtons(chatId, "ru");
                        break;
                    case LanguageInterface.ENGLISH:
                        userLanguages.put(chatId, "en");
                        sendActionButtons(chatId, "en");
                        break;
                    case ButtonInterface.Uzbek.VIEW_PROMOTIONS:
                    case ButtonInterface.Russian.VIEW_PROMOTIONS:
                    case ButtonInterface.English.VIEW_PROMOTIONS:
                        sendHelloMessage(chatId, message.getMessageId()); // Respond with "hello" for promotions button
                        break;

                    case ButtonInterface.English.GO_TO_WEBSITE:
                    case ButtonInterface.Russian.GO_TO_WEBSITE:
                    case ButtonInterface.Uzbek.GO_TO_WEBSITE:
                        goToWebsite(chatId, language);
                        break;

                    case ButtonInterface.English.GET_BONUS:
                    case ButtonInterface.Russian.GET_BONUS:
                    case ButtonInterface.Uzbek.GET_BONUS:
                        getBonus(chatId, language);
                        break;
                    case ButtonInterface.English.DOWNLOAD_ANDROID_APK:
                    case ButtonInterface.Russian.DOWNLOAD_ANDROID_APK:
                    case ButtonInterface.Uzbek.DOWNLOAD_ANDROID_APK:
                        downloadAndroid(chatId, language);
                        break;
                    case ButtonInterface.English.DOWNLOAD_IOS:
                    case ButtonInterface.Russian.DOWNLOAD_IOS:
                    case ButtonInterface.Uzbek.DOWNLOAD_IOS:
                        downloadIos(chatId, language);
                        break;
                    default:
                        // Default action for other messages
                        sendLanguageSelectMessage(chatId);
                        break;
                }
            }
        }
    }

    private void downloadAndroid(long chatId, String language) {
        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(chatId);

        // Determine the image URL based on the language
        String imageUrl;
        switch (language) {
            case "uz":
                imageUrl = "https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo";
                break;
            case "ru":
                imageUrl = "https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo";
                break;
            case "en":
            default:
                imageUrl = "https://lh3.googleusercontent.com/LYUDWiiqyTSiwzbPsJnYhfTzA3kUAoYgRy_1mpKTZOuLtpaMTaNdPKm8Xesm5mxA_zUSIGy6RO4PxhUnIDgTgbmroxgVpudnc0XKWW0cByZXppI2WGo";
                break;
        }

        // Set the image URL for the SendPhoto object
        photoMessage.setPhoto(new InputFile(imageUrl));

        // Determine the caption text based on the language
        String captionText;
        switch (language) {
            case "uz":
                captionText = "Android uchun LineBet mobil ilovasi. Hoziroq oʻrnating va sevimli jamoangizni qoʻllab-quvvatlang";
                break;
            case "ru":
                captionText = "Мобильное приложение LineBet для Android. Установите прямо сейчас и поддержите любимую команду";
                break;
            case "en":
            default:
                captionText = "Mobile application LineBet for Android. Install right now and support your favorite team";
                break;
        }

        // Set the caption text for the photo message
        photoMessage.setCaption(captionText);

        // Now, create a message with inline button

        // Determine the text of the button based on the language
        String buttonText;
        switch (language) {
            case "uz":
                buttonText = "\uD83C\uDF81Yuklab olish\uD83C\uDF81";
                break;
            case "ru":
                buttonText = "\uD83C\uDF81Скачать\uD83C\uDF81";
                break;
            case "en":
            default:
                buttonText = "\uD83C\uDF81Download\uD83C\uDF81";
                break;
        }

        // Determine the message text based on the language

        // Create InlineKeyboardMarkup
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        // Create InlineKeyboardButton for download
        InlineKeyboardButton downloadButton = new InlineKeyboardButton();
        downloadButton.setText(buttonText);
        downloadButton.setUrl("https://lb-aff.com//L?tag=d_3257403m_66803c_apk1&site=3257403&ad=66803"); // Set the URL you want to open when the button is clicked
        row.add(downloadButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        photoMessage.setReplyMarkup(keyboardMarkup);

        try {
            // Send the photo message
            execute(photoMessage);
            // Send the message with inline button
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void downloadIos(long chatId, String language) {
        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(chatId);

        // Determine the image URL based on the language
        String imageUrl;
        switch (language) {
            case "uz":
                imageUrl = "https://d1uxiwmpc9j4yg.cloudfront.net/images/all/ios-icon-logo-software-phone-apple-symbol-with-name-black-design-mobile-illustration-free-vector_1687630747.jpeg";
                break;
            case "ru":
                imageUrl = "https://d1uxiwmpc9j4yg.cloudfront.net/images/all/ios-icon-logo-software-phone-apple-symbol-with-name-black-design-mobile-illustration-free-vector_1687630747.jpeg";
                break;
            case "en":
            default:
                imageUrl = "https://d1uxiwmpc9j4yg.cloudfront.net/images/all/ios-icon-logo-software-phone-apple-symbol-with-name-black-design-mobile-illustration-free-vector_1687630747.jpeg";
                break;
        }

        // Set the image URL for the SendPhoto object
        photoMessage.setPhoto(new InputFile(imageUrl));

        // Determine the caption text based on the language
        String captionText;
        switch (language) {
            case "uz":
                captionText = "iPhone uchun LineBet mobil ilovasi. Hoziroq oʻrnating va sevimli jamoangizni qoʻllab-quvvatlang";
                break;
            case "ru":
                captionText = "Мобильное приложение LineBet для iPhone. Установите прямо сейчас и поддержите любимую команду";
                break;
            case "en":
            default:
                captionText = "Mobile application LineBet for iPhone. Install right now and support your favorite team";
                break;
        }

        // Set the caption text for the photo message
        photoMessage.setCaption(captionText);

        // Now, create a message with inline button

        // Determine the text of the button based on the language
        String buttonText;
        switch (language) {
            case "uz":
                buttonText = "\uD83C\uDF81Yuklab olish\uD83C\uDF81";
                break;
            case "ru":
                buttonText = "\uD83C\uDF81Скачать\uD83C\uDF81";
                break;
            case "en":
            default:
                buttonText = "\uD83C\uDF81Download\uD83C\uDF81";
                break;
        }

        // Determine the message text based on the language

        // Create InlineKeyboardMarkup
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        // Create InlineKeyboardButton for download
        InlineKeyboardButton downloadButton = new InlineKeyboardButton();
        downloadButton.setText(buttonText);
        downloadButton.setUrl("https://testflight.apple.com/join/0d7rBRBx"); // Set the URL you want to open when the button is clicked
        row.add(downloadButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        photoMessage.setReplyMarkup(keyboardMarkup);

        try {
            // Send the photo message
            execute(photoMessage);
            // Send the message with inline button
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void goToWebsite(long chatId, String language) {

        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(chatId);

        // Determine the image URL based on the language
        String imageUrl;
        switch (language) {
            case "uz":
                imageUrl = "https://linebet.ink/wp-content/uploads/2023/08/cropped-linebet-logo.png";
                break;
            case "ru":
                imageUrl = "https://linebet.ink/wp-content/uploads/2023/08/cropped-linebet-logo.png";
                break;
            case "en":
            default:
                imageUrl = "https://linebet.ink/wp-content/uploads/2023/08/cropped-linebet-logo.png";
                break;
        }

        // Set the image URL for the SendPhoto object
        photoMessage.setPhoto(new InputFile(imageUrl));

        // Determine the caption text based on the language
        String captionText;
        switch (language) {
            case "uz":
                captionText = "HOZIRDAN TOPISHNI BOSHLASH!\uD83D\uDD25 ✅ LineBet mobil ilovasini yuklab oling. ✅ Roʻyxatdan oʻting va $100 bonusga ega boʻling. ✅ Gambling qiling va yutib oling!";
                break;
            case "ru":
                captionText = "НАЧНИТЕ ЗАРАБАТЫВАТЬ ПРЯМО СЕЙЧАС!\uD83D\uDD25 ✅ Скачайте мобильное приложение LineBet. ✅ Зарегистрируйтесь и получите бонус в размере 100 долларов. ✅ Делайте ставки и выигрывайте!";
                break;
            case "en":
            default:
                captionText = "START EARNING RIGHT NOW!\uD83D\uDD25 ✅ Download mobile application LineBet. ✅ Register and get a $100 bonus. ✅ Place your bets and win!";
                break;
        }

        // Set the caption text for the photo message
        photoMessage.setCaption(captionText);

        // Now, create a message with inline button

        // Determine the text of the button based on the language
        String buttonText;
        switch (language) {
            case "uz":
                buttonText = "\uD83C\uDF81Saytga o'tish\uD83C\uDF81";
                break;
            case "ru":
                buttonText = "\uD83C\uDF81Перейти в сайт\uD83C\uDF81";
                break;
            case "en":
            default:
                buttonText = "\uD83C\uDF81Go to website\uD83C\uDF81";
                break;
        }

        // Determine the message text based on the language

        // Create InlineKeyboardMarkup
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        // Create InlineKeyboardButton for download
        InlineKeyboardButton downloadButton = new InlineKeyboardButton();
        downloadButton.setText(buttonText);
        downloadButton.setUrl("https://karona.lineorg.com/"); // Set the URL you want to open when the button is clicked
        row.add(downloadButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        photoMessage.setReplyMarkup(keyboardMarkup);

        try {
            // Send the photo message
            execute(photoMessage);
            // Send the message with inline button
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void getBonus(long chatId, String language) {

        SendPhoto photoMessage = new SendPhoto();
        photoMessage.setChatId(chatId);

        // Determine the image URL based on the language
        String imageUrl;
        switch (language) {
            case "uz":
                imageUrl = "https://cdn.dailysports.net/brand-rating/brand/660bf0b8578266c4bc28b5efff1b59fb4b96bc69c3d6c0009b49b6398c0bebdc.png";
                break;
            case "ru":
                imageUrl = "https://cdn.dailysports.net/brand-rating/brand/660bf0b8578266c4bc28b5efff1b59fb4b96bc69c3d6c0009b49b6398c0bebdc.png";
                break;
            case "en":
            default:
                imageUrl = "https://cdn.dailysports.net/brand-rating/brand/660bf0b8578266c4bc28b5efff1b59fb4b96bc69c3d6c0009b49b6398c0bebdc.png";
                break;
        }

        // Set the image URL for the SendPhoto object
        photoMessage.setPhoto(new InputFile(imageUrl));

        // Determine the caption text based on the language
        String captionText;
        switch (language) {
            case "uz":
                captionText = "100$ gacha bonus oling!!! \n" +
                        "✅ LineBet veb-saytida ro'yxatdan o'ting.\n" +
                        "✅ Roʻyxatdan oʻtishda KARONA promo kodini kiriting.\n" +
                        "✅ Balans to'ldirilgandan so'ng bonus avtomatik tarzda o'tkaziladi!";
                break;
            case "ru":
                captionText = "Получите бонус до 100$!!! \n" +
                        "✅ Зарегистрируйтесь на сайте LineBet.\n" +
                        "✅ При регистрации введите промокод KARONA .\n" +
                        "✅Бонус начисляется автоматически после пополнения баланса!";
                break;
            case "en":
            default:
                captionText = "Get bonus up to 100$!!! \n" +
                        "✅ Register on the LineBet website.\n" +
                        "✅ When registering, enter the promo code KARONA .\n" +
                        "✅ The bonus is automatically credited after the balance is refilled!";
                break;
        }

        // Set the caption text for the photo message
        photoMessage.setCaption(captionText);

        // Now, create a message with inline button

        // Determine the text of the button based on the language
        String buttonText;
        switch (language) {
            case "uz":
                buttonText = "\uD83C\uDF81Bonus olish\uD83C\uDF81";
                break;
            case "ru":
                buttonText = "\uD83C\uDF81Получить бонусы\uD83C\uDF81";
                break;
            case "en":
            default:
                buttonText = "\uD83C\uDF81Get bonuses\uD83C\uDF81";
                break;
        }

        // Determine the message text based on the language

        // Create InlineKeyboardMarkup
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row = new ArrayList<>();

        // Create InlineKeyboardButton for download
        InlineKeyboardButton downloadButton = new InlineKeyboardButton();
        downloadButton.setText(buttonText);
        downloadButton.setUrl("https://karona.lineorg.com/"); // Set the URL you want to open when the button is clicked
        row.add(downloadButton);

        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        photoMessage.setReplyMarkup(keyboardMarkup);

        try {
            // Send the photo message
            execute(photoMessage);
            // Send the message with inline button
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendLanguageSelectMessage(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(getLanguageSelectMessageText());
        message.setReplyMarkup(KeyboardFactory.getLanguageSelectKeyboard());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendHelloMessage(long chatId, int messageId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("https://t.me/linebet_1xbet_apk2024");
        message.setReplyToMessageId(messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getLanguageSelectMessageText() {
        return "Please select your language: \n" +
                LanguageInterface.OZBEKCHA + "\n" +
                LanguageInterface.RUSSIAN + "\n" +
                LanguageInterface.ENGLISH;
    }

    private void sendActionButtons(long chatId, String language) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(getActionButtonsText(language));
        message.setReplyMarkup(KeyboardFactory.getActionButtons(language));
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String getActionButtonsText(String language) {
        switch (language) {
            case "uz":
                return "Harakatni tanlang:";
            case "ru":
                return "Выберите действие:";
            case "en":
            default:
                return "Choose an action:";
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        for (Update update : updates) {
            onUpdateReceived(update);
        }
    }
}
