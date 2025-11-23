'''
    API to interact with Jenkins !!!
    Using Jenkins REST API.
'''

import time
import json
import uuid
import requests

SLEEP_TIME = 10

def execute_job(job_name, parameters, token):
    '''
        Trigger a job build and get it running and retrieve the queue id.
    '''
    job_url = 'http://<Jenkins_installation_ip>:8080/job/'+job_name+    \
    '/buildWithParameters?token='+token+'&'
    
    for key, value in parameters.iteritems():
        job_url += key+"="+value+"&"
    job_url = job_url[:-1]
     
    response = requests.get(""+job_url+"")
    
    queue_url = response.headers["Location"]

    while True:

        queue_response = requests.get(""+queue_url+"api/json")
        queue_response = json.loads(queue_response.text)

        print queue_response

        if "executable" in queue_response.keys():
            break
        else:
            time.sleep(SLEEP_TIME)
    print queue_response["executable"]["number"]


if __name__ == '__main__':
    DICT_ = {}
    DICT_["key1"] = str(uuid.uuid4())
    print "Key1 : " + DICT_["key1"]
    execute_job("Temp_job", DICT_, "test_token")
