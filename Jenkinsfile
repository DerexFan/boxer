pipeline {
    agent any
    options {
       buildDiscarder(logRotator(numToKeepStr:'5'))
    }
   //  tools {
    //   jdk 'jdk8'
   //  }

    stages {

     stage ('Initialize'){
       steps {
           sh '''
             echo "path=${path}"
             echo | java -version
             echo | git --version
             git config credential.helper store
           '''
       }
     }

     stage ('SCM'){
       steps {
          checkout scm
          sh "ls -lat"
       }
     }

     stage ('build'){
       steps {
         sh '''
            ./gradlew clean build
         '''
       }
     }

      stage ('image'){
            steps {
              sh '''
                 ./gradlew :boxer-api:buildDockerImage --stacktrace
              '''
            }
      }

    }
    post {

        success {

            echo "success"
             sh '''
              echo "$DOCKER_HOST" > ansible/deployment/hosts
              ansible-playbook -i ansible/deployment/hosts ansible/deployment/my_playbook.yaml --private-key="ansible/mykey"
              '''

        }

    }

}
