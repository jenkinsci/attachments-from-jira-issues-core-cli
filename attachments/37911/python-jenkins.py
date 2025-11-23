'''
    Jenkins API via Python-Jenkins!!
    Ensure you have python-jenkins installed in your environment(pip install python-jenkins).
'''
import jenkins
import time
import datetime

SLEEP_TIME = 10

SERVER = jenkins.Jenkins('http://<Jenkins_installation_ip>:8080', \
    username='<username>', password='<password>')

JOB_NAME = "Temp_job"
PARAMETERS = {'Key1': datetime.datetime.now()}

JOB_INSTANCE = SERVER.build_job(JOB_NAME, PARAMETERS)
print "Job sent for execution..."

print "Waiting in inQueue..."
while True:
    JOB_INFO = SERVER.get_job_info(JOB_NAME)
    if JOB_INFO["inQueue"] is False:
        break
    time.sleep(SLEEP_TIME)

BUILD_NUMBER = JOB_INFO["lastBuild"]["number"]
print "BUILD_NUMBER : " + str(BUILD_NUMBER)
print "NEXT BUILD_NUMBER : " + str(JOB_INFO["nextBuildNumber"]) 

# JOB_STATUS = SERVER.get_build_info(JOB_NAME, BUILD_NUMBER)["result"]

# print "Job under execution..."
# while JOB_STATUS is None:
#     time.sleep(SLEEP_TIME)
#     JOB_STATUS = SERVER.get_build_info(JOB_NAME, BUILD_NUMBER)["result"]

# CONSOLE_OUTPUT = SERVER.get_build_console_output(JOB_NAME, BUILD_NUMBER)

# print "Job execution completed..."

# JOB_FINAL_STATUS = {}
# JOB_FINAL_STATUS["STATUS"] = JOB_STATUS
# JOB_FINAL_STATUS["CONSOLE_OUTPUT"] = CONSOLE_OUTPUT

# print "BUILD_NUMBER : " + str(BUILD_NUMBER)
# print "JOB_FINAL_STATUS : " + str(JOB_FINAL_STATUS)
