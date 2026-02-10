pipeline {
    agent any

    tools {
        maven 'Maven_3.8.1'
    }

    environment {
        ALLURE_RESULTS = 'allure-results'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/harikavanum5/selenium-java-ddt-framework.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -U'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng.xml'
            }
        }

        stage('Generate Reports') {
            steps {
                bat 'mvn site'

                allure([
                    reportBuildPolicy: 'ALWAYS',
                    includeProperties: false,
                    jdk: '',
                    results: [[path: ALLURE_RESULTS]]
                ])
            }
        }

        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: '**/target/*.xml, allure-results/**/*.*',
                                 allowEmptyArchive: true
            }
        }

        stage('Publish Reports') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'index.html',
                    reportName: 'HTML Report'
                ])
            }
        }
    }
}
