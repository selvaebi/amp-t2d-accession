pipeline {
  agent {
    docker {
      image 'maven:3.5.2-jdk-8'
    }
  }
  environment {
      stagingPostgresDbUrl = credentials('STAGINGPOSTGRESDBURL')
      fallBackPostgresDbUrl = credentials('STAGINGPOSTGRESDBURL')
      productionPostgresDbUrl = credentials('STAGINGPOSTGRESDBURL')
      postgresDBUserName = credentials('POSTGRESDBUSERNAME')
      postgresDBPassword = credentials('POSTGRESDBPASSWORD')
      tomcatCredentials = credentials('TOMCATCREDENTIALS')
      stagingHost = credentials('STAGINGHOST')
      fallbackHost = credentials('FALLBACKHOST')
      productionHost = credentials('PRODUCTIONHOST')
   }
   parameters {
       booleanParam(name: 'DeployToStaging' , defaultValue: false , description: '')
       booleanParam(name: 'DeployToProduction' , defaultValue: false , description: '')
    }
  stages {
    stage('Default Build pointing to Staging DB') {
      steps {
        sh "mvn clean package -DskipTests -DbuildDirectory=staging/target -Dampt2d-accession-db.url=${stagingPostgresDbUrl} -Dampt2d-accession-db.username=${postgresDBUserName} -Dampt2d-accession-db.password=${postgresDBPassword}"
      }
    }
    stage('Build For FallBack And Production') {
      when {
        expression {
          params.DeployToProduction == true
         }
       }
      steps {
        echo 'Build pointing to FallBack DB'
        sh "mvn clean package -DskipTests -DbuildDirectory=fallback/target -Dampt2d-accession-db.url=${fallBackPostgresDbUrl} -Dampt2d-accession-db.username=${postgresDBUserName} -Dampt2d-accession-db.password=${postgresDBPassword}"
        echo 'Build pointing to Production DB'
        sh "mvn clean package -DskipTests -DbuildDirectory=production/target -Dampt2d-accession-db.url=${productionPostgresDbUrl} -Dampt2d-accession-db.username=${postgresDBUserName} -Dampt2d-accession-db.password=${postgresDBPassword}"
       }
     }
    stage('Deploy To Staging') {
      when {
        expression {
          params.DeployToStaging == true
         }
       }
      steps {
        echo 'Deploying to Staging'
        sh "curl --upload-file staging/target/accessioning-service*.war 'http://'${tomcatCredentials}'@'${stagingHost}':8080/manager/text/deploy?path=/ega/t2d/accession&update=true' | grep 'OK - Deployed application at context path '"
       }
     }
    stage('Deploy To FallBack And Production') {
      when {
        expression {
          params.DeployToProduction == true
         }
       }
      steps {
        echo 'Deploying to Fallback'
        sh "curl --upload-file fallback/target/accessioning-service*.war 'http://'${tomcatCredentials}'@'${fallbackHost}':8080/manager/text/deploy?path=/ega/t2d/accession&update=true' | grep 'OK - Deployed application at context path '"
        echo 'Deploying to Production'
        sh "curl --upload-file production/target/accessioning-service*.war 'http://'${tomcatCredentials}'@'${productionHost}':8080/manager/text/deploy?path=/ega/t2d/accession&update=true' | grep 'OK - Deployed application at context path '"
        archiveArtifacts artifacts: 'production/target/*.war' , fingerprint: true
       }
     }
   }
 }
