package com.demo.expense.i18n.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageServiceAdditionalTest {

    @Test
    void get_shouldResolveWithArgsAndLocale() {
        StaticMessageSource sms = new StaticMessageSource();
        sms.addMessage("hello.name", Locale.FRANCE, "Bonjour {0}");
        LocaleContextHolder.setLocale(Locale.FRANCE);
        MessageService svc = new MessageService(sms);
        assertEquals("Bonjour John", svc.get("hello.name", "John"));
    }
}