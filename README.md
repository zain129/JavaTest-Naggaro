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

http://localhost:8080/auth/login
http://localhost:8080/auth/logout
http://localhost:8080/api/account/statement/{accountId}
http://localhost:8080/api/account/statement/date/{accountId}/{dateFrom}/{dateTo}
http://localhost:8080/api/account/statement/amount/{accountId}/{amountFrom}/{amountTo}
http://localhost:8080/api/account/statement/all/{accountId}/{dateFrom}/{dateTo}/{amountFrom}/{amountTo}

