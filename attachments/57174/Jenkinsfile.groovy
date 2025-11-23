pipeline {

    agent none

    options {
        buildDiscarder(logRotator(numToKeepStr: "3"))
        disableConcurrentBuilds()
        skipDefaultCheckout()
    }

    stages {

        stage ('BuildAndDeploy') {
            failFast true

            matrix {

                axes {
                    axis {
                        name 'PLATFORM'
                        values 'centos8'
                    }
                }

                agent {
                    label "${PLATFORM}"
                }

                stages {

                    stage('Get Zip') {

                        steps {
                            sh 'curl <URL HERE>/licenses.zip --output licenses.zip'
                            unzip zipFile: "licenses.zip"
                        }
                    }

                    stage ('Package') {

                        steps {
                            catchError(buildResult: 'FAILURE', stageResult: 'FAILURE', message: 'Archive licenses failed') {
                                archiveArtifacts artifacts: 'build/licenses/**', fingerprint: true, allowEmptyArchive: true, followSymlinks: false
                            }
                        }
                    }
                }
            }
        }
    }
}