# starling-tech-test

**Developer** 
* Peter Tran

**Overview**

Submission for the round-up feature challenge. Solution developed with Java & Spring Boot. 

The provided endpoint takes a given week and savings goal UID. It triggers round up savings for the week and then 
transfers to the given savings goal.

A Feign client is used to integrate the developer APIs (for the Sandbox environment). The following APIs were used:
* GET /api/v2/accounts
* GET /api/v2/feed/account/{accountUid}/category/{categoryUid}/transactions-between
* PUT /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid}/add-money/{transferUid}

**Pre-requisites**
* Java 8 or greater
* Sandbox account
* A savings goal (savingsGoalUid) that has already been created (PUT /api/v2/account/{accountUid}/savings-goals)

**Getting started**

Running the tests
```
./gradlew test
```

Start up the application
```
./gradlew bootRun
```
If running on windows terminal / command prompt please use the gradlew.bat instead

**Example request**

* savingsGoalUid - UID of savings goal that already exists
* year - e.g. 2021 used with week no.
* weekNo - e.g. 10 (this represents w/c 2021/03/08 if year 2021)
* access_token - Starling API (Sandbox env) access token 

```
curl --location --request PUT 'http://localhost:8080/savings-goal/{savingsGoalUid}/add-round-ups/{year}/{weekNo}' \ 
--header 'Authorization: Bearer <access_token>'

Response (204)
```

Example
```
curl --location --request PUT 'http://localhost:8080/savings-goal/fcf2b116-8d3a-49c9-ad5d-c5f703fdea60/add-round-ups/2021/11' \ 
--header 'Authorization: Bearer xxx'
```

**How to test**

1. Use the auto-simulator to generate some transactions
2. Call the end point, passing in the current week
3. View the logs
4. Check savings goal balance - GET /api/v2/account/{accountUid}/savings-goals/{savingsGoalUid} 

Notes:
- currently no implementation to track previously submitted round-ups
- treating spending categories as type PAYMENTS and direction OUT
