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
                 ./gradlew docker
              '''
            }
      }

      stage ('deploy'){
                 steps {
                   sh '''
                      docker ps -q --filter "name=oneport" | grep -q . && docker rm  -f oneport
                      docker run --rm  --name oneport -h oneport --network netlinks -d chinasoft/api-web
                   '''
                 }
           }

        stage ('tag'){
                        steps {
                          sh '''
                             git tag $(date +"%Y%m%d%H%M") & git push --tags
                          '''
                        }
                  }
    }

}
