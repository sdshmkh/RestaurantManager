============ RestaurantManager  ============ 
Restaurant Manager is built using Spring Framework. 

Programming Language: Java 8
Database: H2 (in memory database). We are loading mock data at the application starup via import.sql file under main/java/resources folder.
Caching: Spring caching (by default it uses Google collections but it can work with any cache such as EhCache). Check the @Cachable and @CacheEvict annotations in RestaurantService.
API: API is implemented using REST API which can be seen in RestaurantController class


How to run?
1. Load the project in an editor like IntelliJ and run Application.java class
2. Execute using gradle- gradle bootRun
3. Create a JAR file by building the project and execute using following command- java -jar <jar_file_name>
