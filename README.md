## Template for ADSI

#### Repository's initial content

```
example-jpa-app/
|_README
|_.gitignore
|_LICENSE.txt
|_pom.xml   *maven project configuration (dependencies and plugins)*
|_src/
  |_main/
  | |_java/org/irlab/  *code*
  | |_resources/
  |   |_META-INF/persistence.xml  *database and JPA configuration*
  |   |_sql  *sql scripts for populating database*
  |_test/
    |_java/org/irlab/  *tests code*
    |_resources/
      |_META-INF/persistence.xml  *persistence configuration for tests* 
```

#### Useful maven goals

```shell
mvn test
```

Runs tests allocated in ```src/test```. By default, ```src/test/resources/META-INF/persistence.xml``` will use a temporal, in-memory database.

```shell
mvn package
```

Runs tests and packages the app inside ```target/```. 

```shell
mvn exec:java -Dexec.mainClass=org.irlab.App
```

#### Populating the database

Initially, two sql scripts are included within ```src/main/resources/sql/```. Each one of those includes an ```INSERT``` sql statement to populate a specific table of the database.  The following maven goal execute the scripts:

```shell
mvn sql:execute
```

Note that this could drive into consistency issues in case the database is already populated.

The scripts must be individually included in ```pom.xml``` in order to be detected by maven:

```xml
(...)
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>sql-maven-plugin</artifactId>
    <version>1.5</version>

    <dependencies>
        <dependency>
            <groupId>org.apache.derby</groupId>
            <artifactId>derby</artifactId>
            <version>${derby.version}</version>
        </dependency>
    </dependencies>

    <configuration>
        <driver>org.apache.derby.iapi.jdbc.AutoloadedDriver</driver>
        <url>jdbc:derby:sampleDb;create=true</url>
        <username>user</username>
        <password>test</password>
    </configuration>
    <executions>
        <execution>
            <id>default-cli</id>
            <goals>
                <goal>execute</goal>
            </goals>
            <configuration>
                <delimiter>/</delimiter>
                <delimiterType>normal</delimiterType>
                <autocommit>true</autocommit>
                <fileset>
                    <basedir>${basedir}/src/main/resources/sql/</basedir>
                    <includes>
                        <include>insert_roles.sql</include>
                        <include>insert_users.sql</include>
                    </includes>
                </fileset>
            </configuration>
        </execution>
    </executions>        	
</plugin>
(...)
```
