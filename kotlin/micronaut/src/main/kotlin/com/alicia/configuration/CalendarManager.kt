package com.alicia.configuration

import io.micronaut.scheduling.annotation.Scheduled
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.GregorianCalendar
import javax.annotation.PostConstruct
import javax.inject.Singleton

@Singleton
class CalendarManager {

    private val todayCalendar: GregorianCalendar = GregorianCalendar()

    fun getToday(): Date = todayCalendar.time

    @PostConstruct
    @Scheduled(cron = "0 0 * * *")
    fun updateCalendar() {
        val now = LocalDate.now(ZoneId.of("UTC"))
        todayCalendar.clear()
        todayCalendar.set(now.year, now.month.value - 1, now.dayOfMonth)
    }
}
