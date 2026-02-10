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

        // 🔹 Build only (skip tests)
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        // 🔹 Run tests only here
        stage('Run Tests') {
            steps {
                bat 'mvn test -DsuiteXmlFile=testng.xml || exit 0'
            }
        }

        // 🔹 Generate Allure report
        stage('Generate Reports') {
            steps {
                allure([
                    reportBuildPolicy: 'ALWAYS',
                    includeProperties: false,
                    results: [[path: 'allure-results']]
                ])
            }
        }

        // 🔹 Archive
        stage('Archive Results') {
            steps {
                archiveArtifacts artifacts: '**/target/*.xml, allure-results/**/*.*',
                                 allowEmptyArchive: true
            }
        }

        // 🔹 HTML report
        stage('Publish Reports') {
            steps {
                publishHTML([
                    allowMissing: true,
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
