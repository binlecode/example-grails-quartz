package bletest

import grails.test.mixin.*

@TestFor(ConfigUsageReportController)
@Mock(ReportConfig)
class ConfigUsageReportControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/configUsageReport/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.configUsageReportInstanceList.size() == 0
        assert model.configUsageReportInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.configUsageReportInstance != null
    }

    void testSave() {
        controller.save()

        assert model.configUsageReportInstance != null
        assert view == '/configUsageReport/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/configUsageReport/show/1'
        assert controller.flash.message != null
        assert ReportConfig.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/configUsageReport/list'

        populateValidParams(params)
        def configUsageReport = new ReportConfig(params)

        assert configUsageReport.save() != null

        params.id = configUsageReport.id

        def model = controller.show()

        assert model.configUsageReportInstance == configUsageReport
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/configUsageReport/list'

        populateValidParams(params)
        def configUsageReport = new ReportConfig(params)

        assert configUsageReport.save() != null

        params.id = configUsageReport.id

        def model = controller.edit()

        assert model.configUsageReportInstance == configUsageReport
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/configUsageReport/list'

        response.reset()

        populateValidParams(params)
        def configUsageReport = new ReportConfig(params)

        assert configUsageReport.save() != null

        // test invalid parameters in update
        params.id = configUsageReport.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/configUsageReport/edit"
        assert model.configUsageReportInstance != null

        configUsageReport.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/configUsageReport/show/$configUsageReport.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        configUsageReport.clearErrors()

        populateValidParams(params)
        params.id = configUsageReport.id
        params.version = -1
        controller.update()

        assert view == "/configUsageReport/edit"
        assert model.configUsageReportInstance != null
        assert model.configUsageReportInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/configUsageReport/list'

        response.reset()

        populateValidParams(params)
        def configUsageReport = new ReportConfig(params)

        assert configUsageReport.save() != null
        assert ReportConfig.count() == 1

        params.id = configUsageReport.id

        controller.delete()

        assert ReportConfig.count() == 0
        assert ReportConfig.get(configUsageReport.id) == null
        assert response.redirectedUrl == '/configUsageReport/list'
    }
}
