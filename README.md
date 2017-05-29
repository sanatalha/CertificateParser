# Evaluate request rate performance

## Problem
he ubiquitous X509 Certificate is the most commonly used type of digital certificate. To parse such a certificate, you can use the BouncyCastle PKIX lib (available at https://www.bouncycastle.org/download/bcpkix-jdk15on-157.jar) and a tip is to look at the org.bouncycastle.util.io.pem.PemReader class. 

## Task:
Create a simple client capable of printing information from the bundled certificate. It will contain several fields, among which will be:
The issuer, subject and serial number of the certificate
The validity dates of the certificate (and if it's still valid)
The validity of the signature (using the Public Key in the CA certificate) 
We will require some form of presentation layer, which should either be:
A user friendly CLI-client 
A simple (but user friendly) web client 
## Built With
1. Java 8
2. Maven

## About Application:
1. DDD has been followed
2. TDD has been followed with automated tests
3. Code has been thoroughly documented

## Run
1. mvn package
2. java -jar target/parser-1.0-jar-with-dependencies.jar /Documents/certificate.pem -help 

##Usage examples
java -jar parser.jar certificatePath [commands]

## Author
Sana Talha
