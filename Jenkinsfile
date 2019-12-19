pipeline {
    agent any
    stages {
        stages("Build") {
            steps {
                sh "sudo sh deploy.sh"
            }
        }

        stage("Run new WAS") {
            steps {
                sh "sudo sh /home/ubuntu/run_new_was.sh"
            }
        }

        stage("health check") {
            steps {
                sh "sudo sh /home/ubuntu/health_check.sh"
            }
        }

        stage("switch") {
            steps {
                sh "sudo sh /home/ubuntu/switch.sh"
            }
        }
    }
}