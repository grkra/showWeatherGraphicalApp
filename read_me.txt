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
2. You must register on https://home.openweathermap.org/ and generate API KEY there to be able to use the application
3. Navigate to project directory
4. Create .env file in projects direcotry (the same directory as pom.xml file)
5. In created .env file add OPENWEATHER_API_KEY={your api key}
6. Build the project
	mvn clean install
7. Run application
	mvn clean javafx:run

----------------------------------------------------
Portable JAR file
----------------------------------------------------
You can build portable JAR file and use application this way.
1. Go to pom.xml file and uncomment right part <plugins> part
2. Build package
	mvn clean package
3. Maven will build 2 JAR files and place them in ./target directory:
	- currentWeather.jar			- fat jar containing code with all necessary dependencies
	- original-currentWeather.jar	- it contains compiled code only, without dependencies
4. Navigate to JAR file directory (by default: project-direcotry/target/) and open terminal there
5. Start the application (fat jar version)
	java -jar currentWeather.jar
6. You can move or copy the JAR file wherever you want
7. WARNING: You need JDK 21 installed and to start and use JAR file

----------------------------------------------------
Portable EXE file
----------------------------------------------------
1. Go to pom.xml file and uncomment right part <plugins> part
2. Build package
	mvn clean package
3. Maven will build:
	- JAR file (without dependencies, but with Class-Path in MANIFEST.MF) in ./target directory
	- ./target/libs direcotry with all needed dependencies inside
4. Go to main directory (where pom.xml file is) and run command. You can do it with Windows CMD or PowerShell.
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
5. New directory will appear: ./output/CurrentWeather with CurrentWeather.exe file and additional app and runtime directories containing necassary JVM and dependencies.
6. You can doubleclick on exe file to start application.
7. You can zip whole direcotry and move or copy it wherever you want.

----------------------------------------------------
EXE installer file for Windows
----------------------------------------------------
1. Go to pom.xml file and uncomment right part <plugins> part
2. Build package
	mvn clean package
3. Maven will build:
	- JAR file (without dependencies, but with Class-Path in MANIFEST.MF) in ./target directory
	- ./target/libs direcotry with all needed dependencies inside
4. Go to main directory (where pom.xml file is) and run command. You can do it with Windows CMD or PowerShell.
^ marks continuation of line in Windows CMD. If you use PowerShell replace ^ with `.
	jpackage ^
		--type exe ^
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
	jpackage --type exe --input target --main-jar CurrentWeather.jar --main-class krawczyk.grzegorz.Main --name CurrentWeather --java-options "--enable-preview" --dest output --icon target\classes\icon.ico --win-shortcut --win-menu
Two last lines add shortcuts on Desktop (first one) and Start Menu (second one).
5. File will appear: ./output/CurrentWeather/CurrentWeather-version.msi.
6. You can doubleclick on msi file to start installer. It will automatically intall application to Program Files and add shortcuts to Desktop and Start Menu.
7. You can move or copy msi file wherever you want.

----------------------------------------------------
MSI installer file for Windows
----------------------------------------------------
1. Install WiX Toolset (3.x) on your machine (https://github.com/wixtoolset/wix3/releases)
2. Go to pom.xml file and uncomment right part <plugins> part
3. Build package
	mvn clean package
4. Maven will build:
	- JAR file (without dependencies, but with Class-Path in MANIFEST.MF) in ./target directory
	- ./target/libs direcotry with all needed dependencies inside
5. Go to main directory (where pom.xml file is) and run command. You can do it with Windows CMD or PowerShell.
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
	jpackage --type msi --input target --main-jar CurrentWeather.jar --main-class krawczyk.grzegorz.Main --name CurrentWeather --java-options "--enable-preview" --dest output --icon target\classes\icon.ico --win-shortcut --win-menu
Two last lines add shortcuts on Desktop (first one) and Start Menu (second one).
6. File will appear: ./output/CurrentWeather/CurrentWeather-version.msi.
7. You can doubleclick on msi file to start installer. It will automatically intall application to Program Files and add shortcuts to Desktop and Start Menu.
8. You can move or copy msi file wherever you want.