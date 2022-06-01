import com.shankar.*;

def call(body) {
    utils = new utilities()
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()
    def FAILED_ON_RESOURCE_LOCK = true


    pipeline {
        agent any

        parameters{
            string(name: 'generate_vulnerability_report', defaultValue: 'No', description: '')
        }


        environment {
                java_v = 'jdk11.0.15-linux_x64'
                maven_v = 'Maven 3.6.1'
        }

        options {
                buildDiscarder(logRotator(numToKeepStr: '4'))
                skipDefaultCheckout(true)
                disableConcurrentBuilds()
           }

        stages {

            stage('check DB') {
                steps {
                    script {
                        myVar = "cb-11"
                        env.db_var = myVar
                        }
                    echo "Setting the variable: output is ${db_var}"
                    sh "printenv  | sort"
                    }
            }

            stage('Lock CB') {
                when {
                    beforeOptions true
                    expression { utils.is_resource_available("${pipelineParams.component}-${env.db_var}") }
                }
                options {
                	lock(resource: "${pipelineParams.component}-${env.db_var}")
                 }
                steps {
                    echo "Hello World"
                }
           }
        }
    }
}