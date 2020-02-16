pipeline {
    agent any
    stages {
        stage("Git pull") {
            steps {
                sh "git pull origin develop && git checkout develop"
            }
        }

        stage("Build") {
            steps {
                sh "./gradlew clean build"
            }
        }

        stage("Send jar to deploy server") {
            steps {
                sh "scp -i ~/KEY-TRAINING-Deocksu.pem ./build/libs/tecolearning-0.0.1-SNAPSHOT.jar ubuntu@13.125.168.159:/home/ubuntu"
            }
        }

        stage("Run WAS") {
            steps {
                sh "ssh -i ~/KEY-TRAINING-Deocksu.pem ubuntu@13.125.168.159 \"bash -s\" < ./run_new_was.sh"
            }
        }

        stage("health check") {
            steps {
                sh "ssh -i ~/KEY-TRAINING-Deocksu.pem ubuntu@13.125.168.159 \"bash -s\" < ./health_check.sh"
            }
        }

        stage("switch") {
            steps {
                sh "ssh -i ~/KEY-TRAINING-Deocksu.pem ubuntu@13.125.168.159 \"bash -s\" < ./switch.sh"
            }
        }
    }
}
