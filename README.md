
# DiscordGriefBot
A Discord bot that deletes all channels on a Discord server and replaces them with "FUCKED-BY-NAME". This bot can be invited to external servers via social engineering and thus griefed.

*The bot was programmed in Java with JDA and is a Maven project.*

Sample video for the GriefBot: https://www.youtube.com/watch?v=ZLGZtxkxjxk

#


`pom.yml`
```maven
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>DiscordGriefBot</groupId>
  <artifactId>DiscordGriefBot</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <build>
    <sourceDirectory>src</sourceDirectory>
    <plugins>
      <plugin>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
          <source>1.8</source>
          <target>1.8</target>
        </configuration>
      </plugin>
    </plugins>
  </build>
  <repositories>
  <repository>
    <id>dv8tion</id>
    <name>m2-dv8tion</name>
    <url>https://m2.dv8tion.net/releases</url>
</repository>
  </repositories>
  <dependencies>
	  <dependency>
	    <groupId>net.dv8tion</groupId>
	    <artifactId>JDA</artifactId>
	    <version>4.2.1_253</version>
	    <exclusions>
	        <exclusion>
	            <groupId>club.minnced</groupId>
	            <artifactId>opus-java</artifactId>
	        </exclusion>
	    </exclusions>
	</dependency>
  </dependencies>
</project>
```
