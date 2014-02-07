This is the Java API to update the category of datasets on Socrata portal using the Socrata Open Data API (SODA).

1) To update the category the following will be needed: dataset id, the new category, and the type of the dataset (i.e. tabular,
   blob, etc.)
   	 
2) All the requried dataset ids, categories and dataset types should be provide in an excel file.
	Note: A sample excel file is provided in the  src/test/resources

3) You will need to make some updates to the two files in src/main/resources:

	a) NYCOpenDataIntegration.properties
		i) Update the Socrata credentials
		ii) Update the location of the excel file (and make sure the names match).
		iii) If proxy exists provide the proxy details and set proxy.enabled to true. If no proxy exists then just keep the current configuration.
	b) log4j.properties (optional)

4) Finally, use Apache Maven to build a jar file and then run it:

	a) mvn clean install
	b) mvn package
	c) java -jar target/SocrataUpdateCategory-1.0-SNAPSHOT.jar


	
