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
2. Maven will build 2 JAR files and place them in ././target directory:
	- currentWeather.jar			- fat jar containing code with all necessary dependencies
	- original-currentWeather.jar	- it contains compiled code only, without dependencies
3. Navigate to JAR file directory (by default: project-direcotry/target/) and open terminal there
4. Start the application (fat jar version)
	java -jar currentWeather.jar
5. You can move or copy the JAR file wherever you want
5. WARNING: You need JDK 21 installed and to start and use JAR file

----------------------------------------------------
Portable EXE file
----------------------------------------------------
1. Build package
	mvn package
2. Maven will build:
	- JAR file (without dependencies, but with Class-Path in MANIFEST.MF) in ./target directory
	- ./target/libs direcotry with all needed dependencies inside
3. Go to main directory (where pom.xml file is) and run command. You can do it with Windows CMD or PowerShell.
^ marks continuation of line in Windows CMD. If you use PowerShell replace ^ with `.
	jpackage ^
		--type app-image ^
		--input target ^
		--main-jar CurrentWeather.jar ^
		--main-class krawczyk.grzegorz.Main ^
		--name CurrentWeather ^
		--java-options "--enable-preview" ^
		--dest output ^
		--icon target\classes\icon.ico
		
Or you can paste it in 1 line without ^ nor `
	jpackage --type app-image --input target --main-jar CurrentWeather.jar --main-class krawczyk.grzegorz.Main --name CurrentWeather --java-options "--enable-preview" --dest output --icon target\classes\icon.ico
4. New directory will appear: ./output/CurrentWeather with CurrentWeather.exe file and additional app and runtime directories containing necassary JVM and dependencies.
5. You can doubleclick on exe file to start application.
6. You can zip whole direcotry and move or copy it wherever you want.

----------------------------------------------------
MSI installer file for Windows
----------------------------------------------------
WARNING: To do this you need to install WiX Toolset (3.x) on your machine.
	Download it from https://github.com/wixtoolset/wix3/releases and install
1. Build package
	mvn package
2. Maven will build:
	- JAR file (without dependencies, but with Class-Path in MANIFEST.MF) in ./target directory
	- ./target/libs direcotry with all needed dependencies inside
3. Go to main directory (where pom.xml file is) and run command. You can do it with Windows CMD or PowerShell.
^ marks continuation of line in Windows CMD. If you use PowerShell replace ^ with `.
	jpackage ^
		--type msi ^
		--input target ^
		--main-jar CurrentWeather.jar ^
		--main-class krawczyk.grzegorz.Main ^
		--name CurrentWeather ^
		--java-options "--enable-preview" ^
		--dest output ^
		--icon target\classes\icon.ico ^
		--win-shortcut ^
		--win-menu
		
Or you can paste it in 1 line without ^ nor `.
Two last lines add shortcuts on Desktop (first one) and Start Menu (second one).
	jpackage --type msi --input target --main-jar CurrentWeather.jar --main-class krawczyk.grzegorz.Main --name CurrentWeather --java-options "--enable-preview" --dest output --icon target\classes\icon.ico --win-shortcut --win-menu
4. File will appear: ./output/CurrentWeather/CurrentWeather-version.msi.
5. You can doubleclick on msi file to start installer. It will automatically intall application to Program Files and add shortcuts to Desktop and Start Menu.
6. You can move or copy msi file wherever you want.