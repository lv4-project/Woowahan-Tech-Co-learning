pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                sh 'sudo sh /home/ubuntu/run_new_was.sh'
            }

        stage('health check') {
            steps {
                sh 'sudo sh /home/ubuntu/health_check.sh'
            }
        }

        stage('switch') {
            steps {
                sh 'sudo sh /home/ubuntu/switch.sh'
            }
        }
    }
}