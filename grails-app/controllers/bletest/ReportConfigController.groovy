package bletest

import org.springframework.dao.DataIntegrityViolationException

class ReportConfigController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def reportScheduleService


    def index() {
        redirect(action: "list", params: params)
    }

    def schedule() {
        reportScheduleService.scheduleIntervalReport(intervalOfSecond: 5, reportType: 'Usage')
        render "job scheduled"
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [reportConfigInstanceList: ReportConfig.list(params), reportConfigInstanceTotal: ReportConfig.count()]
    }

    def create() {
        [reportConfigInstance: new ReportConfig(params)]
    }

    def save() {
        def reportConfigInstance = new ReportConfig(params)
        if (!reportConfigInstance.save(flush: true)) {
            render(view: "create", model: [reportConfigInstance: reportConfigInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), reportConfigInstance.id])
        redirect(action: "show", id: reportConfigInstance.id)
    }

    def show(Long id) {
        def reportConfigInstance = ReportConfig.get(id)
        if (!reportConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "list")
            return
        }

        [reportConfigInstance: reportConfigInstance]
    }

    def edit(Long id) {
        def reportConfigInstance = ReportConfig.get(id)
        if (!reportConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "list")
            return
        }

        [reportConfigInstance: reportConfigInstance]
    }

    def update(Long id, Long version) {
        def reportConfigInstance = ReportConfig.get(id)
        if (!reportConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (reportConfigInstance.version > version) {
                reportConfigInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                        [message(code: 'reportConfig.label', default: 'ReportConfig')] as Object[],
                        "Another user has updated this ReportConfig while you were editing")
                render(view: "edit", model: [reportConfigInstance: reportConfigInstance])
                return
            }
        }

        reportConfigInstance.properties = params

        if (!reportConfigInstance.save(flush: true)) {
            render(view: "edit", model: [reportConfigInstance: reportConfigInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), reportConfigInstance.id])
        redirect(action: "show", id: reportConfigInstance.id)
    }

    def delete(Long id) {
        def reportConfigInstance = ReportConfig.get(id)
        if (!reportConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "list")
            return
        }

        try {
            reportConfigInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'reportConfig.label', default: 'ReportConfig'), id])
            redirect(action: "show", id: id)
        }
    }
}
