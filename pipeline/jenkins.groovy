pipeline {
    agent any
    parameters {

        choice(name: 'OS', choices: ['linux', 'darwin', 'windows', 'all'], description: 'Pick OS')

        choice(name: 'ARCH', choices: ['amd64', 'arm64'], description: 'Pick ARCH')

    }
    environment {
        REPO = 'https://github.com/alexanderlukjanenko/kbot'
        BRANCH = 'main'
    }
    stages {
        stage('Example') {
            steps {
                echo "Build for platform ${params.OS}"

                echo "Build for arch: ${params.ARCH}"

            }
        }

        stage("clone") {
            steps {
                echo 'CLONE REPO'
                git branch: "${BRANCH}", url: "${REPO}"
            }
        }

        stage("test") {
            steps {
                echo "Test"
                sh 'make test'
            }
        }

        stage('build') {
            steps {
                echo "Building binary for platform ${params.OS} on ${params.ARCH} started"
                sh "make ${params.OS} ${params.ARCH}"
            }
        }

        stage('image') {
            steps {
                echo "Building image for platform ${params.OS} on ${params.ARCH} started"
                sh "make image-${params.OS} ${params.ARCH}"
            }
        }
    }
}
