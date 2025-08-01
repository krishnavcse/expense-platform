package com.demo.expense.i18n.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MessageServiceTest {

    @Test
    void get_shouldResolveMessage() {
        StaticMessageSource sms = new StaticMessageSource();
        sms.addMessage("greet", Locale.ENGLISH, "Hello");
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        MessageService svc = new MessageService(sms);
        assertEquals("Hello", svc.get("greet"));
    }
}