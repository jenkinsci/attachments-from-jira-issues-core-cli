import hudson.model.*
import hudson.model.ParametersAction
import hudson.model.StringParameterValue

// Get Jenkins instance
def jenkins = Jenkins.instance

// Get the job named "test2"
def job = jenkins.getItem("test2")

if (!job) {
    println "Error: Job named 'test2' not found!"
    return
}

// Push 6000 jobs with param1 from 0 to 5999
for (int i = 0; i < 6000; i++) {
    // Create parameters
    def params = [
        new StringParameterValue("param1", i.toString())
    ]
    
    // Create parameter action
    def paramsAction = new ParametersAction(params)
    
    // Add to build queue
    def queueItem = job.scheduleBuild2(0, null, paramsAction)
    
    // Output progress information
    if (i % 100 == 0) {
        println "${i} jobs have been pushed to the queue"
    }
}

println "Completed! A total of 6000 test2 jobs have been pushed to the queue"
    