package bletest

import org.quartz.JobExecutionContext



class ReportJob {
    static triggers = {
//      simple repeatInterval: 5000l // execute job once in 5 seconds
    }

    def execute(JobExecutionContext context) {
        def params = context.mergedJobDataMap
        log.debug "executing report job with params: $params"

        if (params.reportType == 'Usage') {
            log.info "running usage report job"

        } else if (params.reportType == 'Giant Eagle') {
            log.info "running giant eagle report job"

        } else if (params.reportType == 'Renorming') {
            log.info "running renorming report job"

        }

    }
}
