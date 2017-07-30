package bletest



import org.junit.*
import grails.test.mixin.*

@TestFor(ReportConfigController)
@Mock(ReportConfig)
class ReportConfigControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/reportConfig/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.reportConfigInstanceList.size() == 0
        assert model.reportConfigInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.reportConfigInstance != null
    }

    void testSave() {
        controller.save()

        assert model.reportConfigInstance != null
        assert view == '/reportConfig/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/reportConfig/show/1'
        assert controller.flash.message != null
        assert ReportConfig.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/reportConfig/list'

        populateValidParams(params)
        def reportConfig = new ReportConfig(params)

        assert reportConfig.save() != null

        params.id = reportConfig.id

        def model = controller.show()

        assert model.reportConfigInstance == reportConfig
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/reportConfig/list'

        populateValidParams(params)
        def reportConfig = new ReportConfig(params)

        assert reportConfig.save() != null

        params.id = reportConfig.id

        def model = controller.edit()

        assert model.reportConfigInstance == reportConfig
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/reportConfig/list'

        response.reset()

        populateValidParams(params)
        def reportConfig = new ReportConfig(params)

        assert reportConfig.save() != null

        // test invalid parameters in update
        params.id = reportConfig.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/reportConfig/edit"
        assert model.reportConfigInstance != null

        reportConfig.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/reportConfig/show/$reportConfig.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        reportConfig.clearErrors()

        populateValidParams(params)
        params.id = reportConfig.id
        params.version = -1
        controller.update()

        assert view == "/reportConfig/edit"
        assert model.reportConfigInstance != null
        assert model.reportConfigInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/reportConfig/list'

        response.reset()

        populateValidParams(params)
        def reportConfig = new ReportConfig(params)

        assert reportConfig.save() != null
        assert ReportConfig.count() == 1

        params.id = reportConfig.id

        controller.delete()

        assert ReportConfig.count() == 0
        assert ReportConfig.get(reportConfig.id) == null
        assert response.redirectedUrl == '/reportConfig/list'
    }
}
