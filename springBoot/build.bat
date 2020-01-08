

rem set JAVA_HOME=C:\_program\amazon-corretto-11.0.4.11.1-windows-x64\jdk11.0.4_10
rem set path=%JAVA_HOME%\bin;%path%

mvn clean package -Dmaven.test.skip=true
rem mvn spring-boot:run