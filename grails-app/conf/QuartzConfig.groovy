quartz {
    autoStartup = true
    jdbcStore = true
    waitForJobsToCompleteOnShutdown = true
    exposeSchedulerInRepository = false

    props {
        // Whether or not to skip running a quick web request to determine if
        // there is an updated version of Quartz available for download. If
        // the check runs, and an update is found, it will be reported as
        // available in Quartz's logs.
        scheduler.skipUpdateCheck = true
    }
}

jdbcProps = {
    threadPool.threadCount = 10  // default -1, only < 100 is realistic
//    threadPool.threadPriority = 5

//    jobStore.class = "org.quartz.impl.jdbcjobstore.JobStoreTX"
//    jobStore.misfireThreshold = 60000
//    jobStore.class = "org.quartz.impl.jdbcjobstore.oracle.OracleDelegate"
//    jobStore.useProperties = false
//    jobStore.tablePrefix = "qrtz_"
//    jobStore.isClustered = false
//    jobStore.clusterCheckinInterval = 5000

    // The shutdown-hook plugin catches the event of the JVM terminating,
    // and calls shutdown on the scheduler
//    plugin.shutdownhook.class = "org.quartz.plugins.management.ShutdownHookPlugin"
//    plugin.shutdownhook.cleanShutdown = true
}



environments {
    test {
        quartz {
            autoStartup = false
            jdbcStore = false
        }
    }

    development {
        quartz {
            props(jdbcProps)
        }
    }

    production {
        quartz {
            props(jdbcProps)
        }
    }

}
