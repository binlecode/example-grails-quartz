package bletest

class ReportConfig {

    String duration  // week, month, quarter, year
    String description

    static constraints = {
        description blank: false
        duration blank: false
    }

}
