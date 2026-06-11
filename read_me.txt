----------------------------------------------------
About the app
----------------------------------------------------
ShowWeatherApp is desktop Java application with graphic interface. Application connects with API to download and display weather.
User can get current location of a device on which application is running from IP location API.
User can get weather data for current and destination location from weather API by city name or (based on location provided by IP location API) by geographical coordinates.
Application is being developed using JavaFX UI toolkit to display user interface.

----------------------------------------------------
Built with
----------------------------------------------------
Make sure you have instaled:
 Java: JDK 21					(https://www.oracle.com/java/technologies/downloads/#java21)
 Maven:	Apache Maven 3.9.6		(https://maven.apache.org/download.cgi)
 
----------------------------------------------------
Installation
----------------------------------------------------
1. Clone the repository:
	git clone https://github.com/grkra/showWeatherGraphicalApp.git
2. Navigate to project directory
3. Build the project
	mvn clean install
4. Run application
	mvn clean javafx:run

----------------------------------------------------
Portable JAR file
----------------------------------------------------
You can build portable JAR file and use application this way.
1. Build package
	mvn package
2. Maven will build 2 JAR files and place them in target directory:
	- currentWeather.jar			- fat jar containing code with all necessary dependencies
	- original-currentWeather.jar	- it contains compiled code only, without dependencies
3. Navigate to JAR file directory (by default: project-direcotry/target/) and open terminal there
4. Start the application (fat jar version)
	java -jar currentWeather.jar
5. You can move or copy the JAR file wherever you want
5. WARNING: You need JDK 21 installed and to start and use JAR file