## CXF Server Example

This is a standalone CXF restful server example.
It can be run via the HelloMain class and will start its own Jetty instance.

The CXF server includes three endpoints:

1. greet - fixed response
2. sayhello - echoes input
3. submit - shows serialisation to/from json

It shows the core parts of building a CXF restful server. i.e.
- Create a rest service interface class which you annotate with the RS annotation @WebService.
- You rest service interface class contains a method signature for each operation. Each one is annotated to say what its path is and the data format for request and response.
- Implementation class contains the code to be executed when each endpoint is hit.

The base URL is set in the cxf-beans.xml file as:

http://localhost:8080/cxf-rest

Then add the service and operation name. i.e.

/hello/greet
/hello/sayhello
/hello/submit

There is an integration test which runs an http request against each URL.
