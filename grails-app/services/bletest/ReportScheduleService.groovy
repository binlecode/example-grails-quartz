package bletest

/**
 * Creates cron expression from cronParams
 * cronExpression: "s m h D M W Y"
 * | | | | | | `- Year [optional]
 * | | | | | `- Day of Week, 1-7 or SUN-SAT, ?
 * | | | | `- Month, 1-12 or JAN-DEC
 * | | | `- Day of Month, 1-31, ?
 * | | `- Hour, 0-23
 * | `- Minute, 0-59
 * `- Second, 0-59
 *
 */
class ReportScheduleService {

    def createHourIntervalCronExpression(interval, minute=0, second=0) {
        return "$second $minute 0/$interval * * ? *"
    }

    def createMinuteIntervalCronExpression(interval, second=0) {
        return "$second 0/$interval * * * ? *"
    }

    def createSecondIntervalCronExpression(interval) {
        return "0/$interval * * * * ? *"   // every $interval seconds
    }

    def createMonthlyCronExpresssion(dayOfMonth=1, hour=0, minute=0, second=0) {
        return "$second $minute $hour $dayOfMonth * ? *"
    }

    def createWeeklyCronExpression(dayOfWeek=1, hour=0, minute=0, second=0) {
        return "$second $minute $hour ? * $dayOfWeek *"
    }

    def createDailyCronExpression(hourOfDay=0, minute=0, second=0) {
        return "$second $minute $hourOfDay * * ? *"
    }

    def createHourlyCronExpression(minuteOfHour=0, second=0) {
        return "$second $minuteOfHour * * * ? *"
    }

    /**
     * @param repeatInterval   in milliseconds
     * @param repeatCount  repeats job repeatCount+1 times
     * @param params
     * @return
     */
    def scheduleRepeatReport(Long repeatInterval, int repeatCount, params) {
        ReportJob.schedule(repeatInterval, repeatCount, params)
    }

    def scheduleIntervalReport(params) {
        def cronExpression = createSecondIntervalCronExpression(params.intervalOfSecond ?: 3)
        log.info "report cron scheduled with expression: $cronExpression"
        ReportJob.schedule(cronExpression, params)
    }

    def scheduleMonthlyReport(params) {
        def cronExpression = createMonthlyCronExpresssion(params.dayOfMonth, params.hour, params.minute, params.second)
        log.info "report cron scheduled with expression: $cronExpression"
        ReportJob.schedule(cronExpression, params)
    }

}
