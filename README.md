**Improvements**

* POST: http://localhost:8080/search

```javascript{
{
  "origin": "LHR",
  "destination": "GRU",
  "departureDate": "2020-02-19:12:00:00",
  "returnDate": "2020-02-20:12:30:00",
  "numberOfPassengers": "2"
}
```

* POST: http://localhost:8080/crazyAir

```javascript{
{
  "origin": "LHR",
  "destination": "GRU",
  "departureDate": "2020-02-19:12:00:00",
  "returnDate": "2020-02-20:12:30:00",
  "passengerCount": "2"
}
```

* POST: http://localhost:8080/crazyAirOneFlight

```javascript{
{
  "origin": "LHR",
  "destination": "GRU",
  "departureDate": "2020-02-19:12:00:00",
  "returnDate": "2020-02-20:12:30:00",
  "passengerCount": "2"
}
```
* POST: http://localhost:8080/toughJet

```javascript{
{
  "from": "GRU",
  "to": "LHR",
  "outboundDate": "2020-02-19:12:00:00",
  "inboundDate": "2020-02-20:12:30:00",
  "numberOfAdults": "2"
}
```

* POST: http://localhost:8080/toughJetOneFlight

```javascript{
{
  "from": "GRU",
  "to": "LHR",
  "outboundDate": "2020-02-19:12:00:00",
  "inboundDate": "2020-02-20:12:30:00",
  "numberOfAdults": "2"
}
```
**Docker**

Exists a Dockerfile prepared to download a Centos OS, install the **openjdk11** and install the application.

- Run the command: `docker build -t travix/interview-test:release .`
- Run the command: `docker run -p port:port <IMG_TAG>`
- Example: `docker run -p 8080:8080 8fb870f41548`
- Or download the image `docker push samueldnc/samuelcatalano:travix`

**Travix - Problem to be solved**

**Background:**

BusyFlights is a flights search solution which aggregates flight results initially from 2 different suppliers (CrazyAir and ToughJet). A future iteration (not part of the test) may add more suppliers.


**What is required:**

Use this GitHub repository as a base to implement the Busy Flights service that should produce an aggregated result from both CrazyAir and ToughJet.
The result should be a JSON response which contains a list of flights ordered by fare which has the following attributes:

**Busy Flights API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| numberOfPassengers | Maximum 4 passengers |

**Response**

| Name | Description |
| ------ | ------ |
| airline | Name of Airline |
| supplier | Eg: CrazyAir or ToughJet |
| fare | Total price rounded to 2 decimals |
| departureAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| destinationAirportCode | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_DATE_TIME format |
| arrivalDate | ISO_DATE_TIME format |

The service should connect to the both the suppliers using HTTP.

**CrazyAir API**

**Request**

| Name | Description |
| ------ | ------ |
| origin | 3 letter IATA code(eg. LHR, AMS) |
| destination | 3 letter IATA code(eg. LHR, AMS) |
| departureDate | ISO_LOCAL_DATE format |
| returnDate | ISO_LOCAL_DATE format |
| passengerCount | Number of passengers |

**Response**


| Name | Description |
| ------ | ------ |
| airline | Name of the airline |
| price | Total price |
| cabinclass | E for Economy and B for Business |
| departureAirportCode | Eg: LHR |
| destinationAirportCode | Eg: LHR |
| departureDate | ISO_LOCAL_DATE_TIME format |
| arrivalDate | ISO_LOCAL_DATE_TIME format |

**ToughJet API**

**Request**

| Name | Description |
| ------ | ------ |
| from | 3 letter IATA code(eg. LHR, AMS) |
| to | 3 letter IATA code(eg. LHR, AMS) |
| outboundDate |ISO_LOCAL_DATE format |
| inboundDate | ISO_LOCAL_DATE format |
| numberOfAdults | Number of passengers |

**Response**

| Name | Description |
| ------ | ------ |
| carrier | Name of the Airline |
| basePrice | Price without tax(doesn't include discount) |
| tax | Tax which needs to be charged along with the price |
| discount | Discount which needs to be applied on the price(in percentage) |
| departureAirportName | 3 letter IATA code(eg. LHR, AMS) |
| arrivalAirportName | 3 letter IATA code(eg. LHR, AMS) |
| outboundDateTime | ISO_INSTANT format |
| inboundDateTime | ISO_INSTANT format |

**What you need to provide:**

- A solution that meets the above requirements.
- The implementation should be made as close to 'production ready' as possible within the time constraints.

It is fine to change any of the supplied application code, if you choose to do so please add comments to indicate what has changed and why.

**Note**

Please clone this project then create your own repository from it. Do not fork/branch this project when creating your solution as it will be visible to other applicants.
