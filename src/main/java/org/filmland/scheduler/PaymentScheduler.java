package org.filmland.scheduler;

import org.filmland.entities.Subscription;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class PaymentScheduler {

    @Scheduled(fixedDelay = 30 * 24 * 60 * 60 * 1000)
    public void sendPaymentMessage() {
        System.out.println("Sending payment message...");
    }

    public void startSubscriptionScheduler(Subscription subscription) {
        if (subscription.getStartDate() != null) {
            long delayInMillis = calculateDelay(subscription.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::sendPaymentMessage, delayInMillis, 30L * 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
        }
    }

    private long calculateDelay(LocalDate startDate) {
        LocalDate now = LocalDate.now();
        Period period = Period.between(startDate, now);
        int months = period.getMonths() + period.getYears() * 12;
        return (long) months * 30 * 24 * 60 * 60 * 1000;
    }
}
