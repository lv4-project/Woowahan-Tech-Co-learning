pipeline {
    agent any
    stages {
        stage("Build") {
            steps {
                sh "sudo sh deploy.sh"
            }
        }

        stage("Run new WAS") {
            steps {
                sh "sudo sh run_new_was.sh"
            }
        }

        stage("health check") {
            steps {
                sh "sudo sh health_check.sh"
            }
        }

        stage("switch") {
            steps {
                sh "sudo sh switch.sh"
            }
        }
    }
}