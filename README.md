# virtual-power-plant
Backend Code Challenge

You are working on a virtual power plant system for aggregating distributed power sources into a single cloud based energy provider. Please implement a REST API in spring boot that encompasses the following functionality:
The API should have an endpoint that accepts - in the HTTP request body - a list of batteries, each containing: name, postcode, and watt capacity. This data should be persisted in a database (In-memory is acceptable).
The API should have an endpoint that receives a postcode range. The response body will contain a list of names of batteries that fall within the range, sorted alphabetically. Additionally, there should be some statistics included for the returned batteries, such as: total and average watt capacity.
The implementation should use Java streams in some way.
We will be specifically looking for clean, tested and well-structured code.
If you have a way to deploy the application in a cloud environment it would be a plus, but this is not mandatory and won't impact the final outcome of the assessment.
Please implement in Java and check your code into either GitHub, Gitlab or Bitbucket to allow us to review your test.


