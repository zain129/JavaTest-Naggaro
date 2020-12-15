# Author
Zain Imtiaz (zain.imtiaz93@gmail.com)

# JavaTest-Naggaro
This project is created as a part of on-going recruitment process.

# User Credentials
testadmin\adminpassword
testUser\userpassword

# Login
curl -X POST http://localhost:8080/auth/login -H "Content-Type:application/json" -d "{\"username\":\"testadmin\", \"password\":\"adminpassword\"}"

# Access API
curl -X GET http://localhost:8080/thisUser -H "Authorization: Bearer <TOKEN>"

# APIs
1. http://localhost:8080/auth/login
2. http://localhost:8080/auth/logout
3. http://localhost:8080/api/account/statement/{accountId}
4. http://localhost:8080/api/account/statement/date/{accountId}/{dateFrom}/{dateTo}
5. http://localhost:8080/api/account/statement/amount/{accountId}/{amountFrom}/{amountTo}
6. http://localhost:8080/api/account/statement/all/{accountId}/{dateFrom}/{dateTo}/{amountFrom}/{amountTo}

# Sample Calls
1. curl -X POST http://localhost:8080/auth/login -H "Content-Type:application/json" -d "{\"username\":\"testadmin\", \"password\":\"adminpassword\"}"
2. curl -X GET http://localhost:8080/thisUser -H "Authorization: Bearer [Token]"
3. curl -X GET http://localhost:8080/api/account/statement/1 -H "Authorization: Bearer [Token]"
4. curl -X GET http://localhost:8080/api/account/statement/amount/1/100/1000 -H "Authorization: Bearer [Token]"
5. curl -X GET http://localhost:8080/api/account/statement/date/1/01.07.2012/14.10.2012 -H "Authorization: Bearer [Token]"
6. curl -X GET http://localhost:8080/api/account/statement/all/1/01.07.2012/14.10.2012/100/1000 -H "Authorization: Bearer [Token]"
