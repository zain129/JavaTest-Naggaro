# About Project
This is a test project, created for Nagarro.
### Author
Zain Imtiaz (zain.imtiaz93@gmail.com)
### Dated
Dec 16, 2020

# User Credentials
- testadmin\adminpassword
- testUser\userpassword

# Login
curl -X POST http://localhost:8080/auth/login -H "Content-Type:application/json" -d "{\"username\":\"testadmin\", \"password\":\"adminpassword\"}"

# APIs
- http://localhost:8080/auth/login
- http://localhost:8080/auth/logout
- http://localhost:8080/api/account/statement/account/{accountId}
- http://localhost:8080/api/account/statement/date/{accountId}/{dateFrom}/{dateTo}
- http://localhost:8080/api/account/statement/amount/{accountId}/{amountFrom}/{amountTo}
- http://localhost:8080/api/account/statement/all/{accountId}/{dateFrom}/{dateTo}/{amountFrom}/{amountTo}

# Sample Calls (Access APIs)
- curl -X POST http://localhost:8080/auth/login -H "Content-Type:application/json" -d "{\"username\":\"testadmin\", \"password\":\"adminpassword\"}"
- curl -X GET http://localhost:8080/thisUser -H "Authorization: Bearer [Token]"
- curl -X GET http://localhost:8080/api/account/statement/account/1 -H "Authorization: Bearer [Token]"
- curl -X GET http://localhost:8080/api/account/statement/amount/1/100/1000 -H "Authorization: Bearer [Token]"
- curl -X GET http://localhost:8080/api/account/statement/date/1/01.07.2012/14.10.2012 -H "Authorization: Bearer [Token]"
- curl -X GET http://localhost:8080/api/account/statement/all/1/01.07.2012/14.10.2012/100/1000 -H "Authorization: Bearer [Token]"
