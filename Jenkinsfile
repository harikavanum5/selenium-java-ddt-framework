pipeline {
agent any

tools {
    maven "Maven3"     // Configure in Jenkins Global Tools
    jdk "JDK11"
}

environment {
    ALLURE_RESULTS = "allure-results"
    EXTENT_REPORT  = "reports/ExtentReport.html"
}

stages {

    stage('Checkout Code') {
        steps {
            git branch: 'main',
                url: 'https://github.com/your-username/your-repo.git'
        }
    }

    stage('Clean Workspace') {
        steps {
            cleanWs()
        }
    }

    stage('Build') {
        steps {
            sh 'mvn clean compile'
        }
    }

    stage('Run Tests') {
        steps {
            sh 'mvn test'
        }
    }

    stage('Publish TestNG Results') {
        steps {
            junit 'target/surefire-reports/*.xml'
        }
    }

    stage('Allure Report') {
        steps {
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'allure-results']]
        }
    }

    stage('Publish Extent Report') {
        steps {
            publishHTML([
                reportName: 'Extent Report',
                reportDir: 'reports',
                reportFiles: 'ExtentReport.html',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: true
            ])
        }
    }

    stage('Archive Artifacts') {
        steps {
            archiveArtifacts artifacts: '''
                reports/**,
                target/surefire-reports/**,
                allure-results/**,
                src/test/resources/Output.xlsx
            ''',
            fingerprint: true
        }
    }
}

post {

    always {
        echo "Build Finished"
    }

    success {
        echo "Tests Passed ✅"
    }

    failure {
        echo "Tests Failed ❌"
    }
}

}