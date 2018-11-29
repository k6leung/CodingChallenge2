# CodingChallenge2
Microservices design:

This project uses Backend-for-frontend design pattern - 2 internal backend microservices
(account service and transaction debit credit record service) facilitate basic generic functions such
as credit, debit and create event records of transaction, credit and debit; 2 fronten BFF does the
actual business logic coordination (create transaction by calling debit and credit functions) and data aggregation
(balance enquiry through account service's find account by account number).

NOTE:  For simplicity, this project uses redis service included by PCF Dev to simulate persistance.

Target development/execution environment:<br />

OS: Windows 10 Professional<br />
IDE: Spring Tool Suite(STS)<br />
Paas: PCF Dev<br />
Storage: Redis from PCF Dev<br />
Config Server: Cloud Foundry Config Server (Spring Cloud Config Server)<br />
Config git: https://github.com/k6leung/CodingChallenge2Config.git<br />
PCF Domain: local.pcfdev.io<br />
Primary libraries:<br />
Spring boot, Spring MVC, Spring Cloud Connector, Spring Data Redis, Cloud Foundry Connector<br />
3rd party libraries:<br />
Apache commons pool2(for redis connection pooling)<br />
Apache httpclient (for PUT requests)<br />
Rest Assured (restful test cases)<br />

How to build:
1. In STS, for each "common" projects (BFF common, NoSecurityCommon, RedisStorageCommon and RestfulCommon),
   right click the project->run as->maven install
2. Maven install the microservice projects (AccountService, BalanceEnquiryBFF, TransactionBFF and TransactionDebitCreditRecordService)

How to deploy:
1. In cf CLI, run the following commands to create required pcf services:<br />
   cf create-service p-redis shared-vm accountRedis<br />
   cf create-service p-redis shared-vm transactionRedis<br />
   cf create-service p-config-server standard configServer -c "{\"git\":{\"uri\":\"https://github.com/k6leung/CodingChallenge2Config.git\",\"cloneOnStart\": true,\"searchPaths\":[\"CodingChallenge2Config/BalanceEnquiryBFF\",\"CodingChallenge2Config/TransactionBFF\"]}}"<br />
2. After services being created successfully, for each of the microservice projects (AccountService, BalanceEnquiryBFF, TransactionBFF and TransactionDebitCreditRecordService),<br />
   go to its root, run the following command:<br />
   cf push -f manifest.yml -t 180
   
How to run test case:
1. After complete deploying all 4 microservices, in STS, right click the test case project (RestTestCases), run as-> Junit Test

Swagger API Specifications:
1. https://github.com/k6leung/CodingChallenge2/blob/master/AccountService/openapi.yml
2. https://github.com/k6leung/CodingChallenge2/blob/master/BalanceEnquiryBFF/openapi.yml
3. https://github.com/k6leung/CodingChallenge2/blob/master/TransactionBFF/openapi.yml
4. https://github.com/k6leung/CodingChallenge2/blob/master/TransactionDebitCreditRecordService/openapi.yml

