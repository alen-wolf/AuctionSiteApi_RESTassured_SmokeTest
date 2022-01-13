pipeline {
    agent any
    stages{
        stage ('Build') {
            steps {
                git 'https://github.com/alen-wolf/AuctionSiteApi_RESTassured_SmokeTest.git'
            }
        }
        stage ('Smoke Test') {
            steps{
                withMaven (maven : 'Maven') {
                  bat "mvn test -DsuiteXmlFile=testng.xml -DtestNames=smoke"
                }
            }
        }
    }
}
