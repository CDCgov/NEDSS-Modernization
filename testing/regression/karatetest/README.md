Karate Tests for testing the Data Ingestion flow and also its integration with the modernization NBS UI

**Running the Tests**

To run the Karate tests, open the integrated terminal in the IDE and execute the following command:

./gradlew test -Dkarate.options="--tags " -Dkarate.env=test -Dtest.apiurl='' -DconnectTimeout=30000 -DreadTimeout=30000 -DretryCount=5 -DretryInterval=500 -Dtest.wrongapiurl='' -Dtest.checkstatusurl='' -Dtest.tokenurl='' -Dtest.checkerrorurl=' ' -Dtest.clientid='' -Dtest.clientsecret='' -Dtest.nbsurl='' -Dtest.nbsusername=''


**Explanation of Parameters**
* -Dkarate.options="--tags @ValidateFeature": Specifies the tags to run. You can specify multiple tags to run multiple tests.
* -Dkarate.env=test: Sets the environment to 'test'. You can update this variable for different environments.
* -Dtest.apiurl='': Sets the API URL. Update this variable as per your environment.
* -DconnectTimeout=30000: Sets the connection timeout to 30000 milliseconds.
* -DreadTimeout=30000: Sets the read timeout to 30000 milliseconds.
* -DretryCount=5: Sets the retry count to 5.
* -DretryInterval=500: Sets the retry interval to 500 milliseconds.
* -Dtest.wrongapiurl='': Sets an additional API URL. Update this variable as needed.
* Dtest.checkstatusurl =‘’:Sets the URL to check the status if ingested data in NBS Interface table.
* -Dtest.tokenurl=‘’: Sets the Token URL to generate the token
* Dtest.checkerrorurl='' : Sets the URL to check the error message if the ingested data validation fails
* Dtest.clientid='' Sets the client ID for specific environment . This is for key cloak authentication
* -Dtest.clientsecret='' Sets the client Secret for specific environment . This is for key cloak authentication
* -Dtest.nbsurl='' Sets the NBS URL for a given environment
*  -Dtest.nbsusername='' Sets the username to log into the NBS environment
