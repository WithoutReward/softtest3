package org.example;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class TelephoneBill {

    private static final int SHORT_CALL_DURATION = 20;
    private static final double SHORT_CALL_RATE = 0.05;
    private static final double LONG_CALL_BASE_RATE = 1.0;
    private static final double LONG_CALL_RATE = 0.1;

    public static double calculateCost(LocalDateTime start, LocalDateTime end) {
        // 将开始和结束时间转换为 UTC 以正确处理夏令时
        ZoneId utc = ZoneId.of("UTC");
        ZonedDateTime utcStart = start.atZone(utc);
        ZonedDateTime utcEnd = end.atZone(utc);

        // 如果开始和结束时间不在同一天，调整结束时间为当天结束
        if (!start.toLocalDate().equals(end.toLocalDate())) {
            utcEnd = utcEnd.with(LocalTime.MAX);
        }

        // 处理跨越春季或秋季夏令时转换
        if (utcStart.isAfter(utcEnd)) {
            ZonedDateTime transition = utcStart.withZoneSameInstant(ZoneId.systemDefault()).withLaterOffsetAtOverlap();
            utcStart = transition.withZoneSameInstant(utc);
        } else if (utcEnd.isAfter(utcStart)) {
            ZonedDateTime transition = utcEnd.withZoneSameInstant(ZoneId.systemDefault()).withEarlierOffsetAtOverlap();
            utcEnd = transition.withZoneSameInstant(utc);
        }

        // 以分钟为单位计算通话时长
        long minutes = ChronoUnit.MINUTES.between(utcStart, utcEnd);

        // 计算通话费用
        if (minutes <= SHORT_CALL_DURATION) {
            return Math.ceil(minutes / 1.0) * SHORT_CALL_RATE;
        } else {
            double baseCost = LONG_CALL_BASE_RATE + LONG_CALL_RATE * (minutes - SHORT_CALL_DURATION);
            return Math.ceil(baseCost / 1.0);
        }
    }

}

