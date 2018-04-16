# Song Finder
A simple Spring Boot application to find a song using Spotify Web API and play its preview.

## Prerequisites
* **Maven**: [Download](https://maven.apache.org/download.cgi) and [Install](https://maven.apache.org/install.html) Apache Maven using the given urls.
* **Apache Tomcat**: [Download](https://tomcat.apache.org/download-80.cgi) and *untar* Tomcat 8.5.x using the the given url.

## External Java Libraries
* **Spring Boot**: Family of libraries which are required for Spring Boot applications.
* **Google GSON**: For Json operations.

## Installation
As the requirement is to have a deployable WAR, this project is configured to be packaged as a *WAR* file.

1. Download and unzip the project.

2. Open terminal and go to the project root.

3. Use *Apache Maven* to package the project using  
```
mvn clean package
```  
After this command, a WAR file at *target/${project.name}-${project.version}.war* should have been created.

4. Copy the created WAR file to the Tomcat for deployment.
  * Copy the file using 
```
cp target/${project.name}-${project.version}.war ${catalina.home}/webapps/${context.path}.war
``` 
command. If this command is used, then the endpoint will be *http://localhost:8080/${context.path}/rest/songfinder*.

  * In order to deploy the application to the tomcat ROOT, create the file *${catalina.home}/conf/Catalina/localhost/ROOT.xml* with the given content
```
<Context docBase="path/to/deploy/application.war" />
```
There are two points to note here.  
*First,* the path of the WAR file is given as a relative path to the *${catalina.home}*.  
*Second,* since we're defining our context in a different file than the *server.xml*, our *docBase* has to be outside of *${catalina.home}/webApps*.  

5. Start the tomcat server using;
  * For Windows
```
${catalina.home}/bin/startup.bat
```

  * For Unix/Linux
```
${catalina.home}/bin/startup.sh
```

## Usage
This application has only one HTTP endpoint which accepts HTTP GET requests with two query parameters
  
* **songname**: Name of the song
* **token**: Spotify Web API Access Token

Example: Request  
```
curl -X GET "http://localhost:8080/rest/songfinder?songname=Californication&token=YOUR_ACCESS_TOKEN"
```
Example: Response

* Byte array containing the preview of the requested song if it can be found without any errors.

* If the song is not found or the method has an error then a Json Object with;
  * **statusCode**: Http status code
  * **errorMessage**: Error description
