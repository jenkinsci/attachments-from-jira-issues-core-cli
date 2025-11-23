pipeline {
    agent {
        kubernetes {
            yaml """
apiVersion: "v1"
kind: "Pod"
spec:
  containers:
  - name: "jnlp"
    image: "imageWith_Jnlp_Maven_JenkinsRepo2.390-SNAPSHOT-m2cache-BasedOnInboundAgentJDK11"
    imagePullPolicy: "IfNotPresent"
    resources:
      limits:
        memory: "2Gi"
        cpu: "1000m"
      requests:
        memory: "512Mi"
        cpu: "500m"
        ephemeral-storage: "4Gi"
    tty: true
"""
        }
    }
    stages {
        stage ("Prepare") {
            steps {
                sh """
                    mv /home/jenkins/jenkinsci/jenkins ${WORKSPACE}
                    mkdir data
                """
                
                dir('jenkins/test') {
                    writeFile(
                        file : 'runUntilFailure.sh',
                        text : """
#!/bin/bash 

removeIteration() {
    iter=\$1
    rm -rf ${env.WORKSPACE}/data/jenkins-59910-\${iter}-*
}

prepareIteration () {
    iter=\$1
    echo "Executing iteration \${iter} at \$(date)"

    cat > ${env.WORKSPACE}/data/logging.properties << EOL
.handlers = java.util.logging.FileHandler
.level = INFO

# Console Logging
java.util.logging.ConsoleHandler.level = INFO

# File Logging
# java.util.logging.FileHandler.level = ALL
java.util.logging.FileHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.FileHandler.pattern=${env.WORKSPACE}/data/jenkins-59910-\${iter}-%u-%g.log
java.util.logging.FileHandler.limit = 10000000
java.util.logging.FileHandler.count = 5

# Packages
hudson.level = INFO
winstone.level = INFO
org.apache.sshd.level = INFO
org.jenkinsci.remoting.protocol.level = ALL
org.jenkinsci.remoting.protocol.handlers = java.util.logging.FileHandler
EOL
    
    # Keep the last iteration only
    if [ \$iter -gt "2" ]; then
        removeIteration \$((\$iter-2))
    fi
}

iteration=1
prepareIteration \${iteration}
while mvn -Djava.util.logging.config.file='${env.WORKSPACE}/data/logging.properties' -Dcheckstyle.skip -Dspotbugs.skip verify -Dtest=jenkins.slaves.restarter.JnlpSlaveRestarterInstallerTest --log-file ${env.WORKSPACE}/data/jenkins-59910-\${iteration}-maven.log
do
    iteration=\$((iteration+1))
    prepareIteration \${iteration}
done
                        """
                    )
                
                    sh "chmod a+x runUntilFailure.sh"
                }

            }
        }

        stage ("Replicate") {
            steps {
                script {
                    try {
                        dir('jenkins/test') {
                            sh "./runUntilFailure.sh"
                        }
                    } finally {
                        archiveArtifacts(artifacts: "data/*.log")
                        slackSend(
                            message: "[JENKINS-59910] Reproduced at <${env.RUN_DISPLAY_URL}|#${env.BUILD_NUMBER}> ?",
                            channel: "myself"
                        )
                    }
                }
            }
        }
    }
}