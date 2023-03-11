# digitalwallet

My System Design Description:

There are 4 entities:
1. User
2. Wallet
3. Wallet Transaction
4. Debit Transaction

+ Each User can have multiple Wallets(but not duplacte ones)

+ Each Wallet can have multiple WalletTransactions

+ Each WalletTransaction has one Debit Transaction (in the wallet transfer condition. actually in wallet transfer we have not any debit transaction)
----------------------------------------------
Aplication Description:

First of all, I recommend checking entity classes. Then you should set up your DB stuff according to items 1 and 2 from below.

1. Create a schema in the mysql DB and put the name of it in the .propertise file, instead of mine(DIGITALWALLET)

2. Set your DB's username and password in the .properties file too.

3. To create a request you should write the elements of request classes in a JSON format in Postman.

4. I tried to separate the data class(DTOs) of the controller and service layer. (decupled them to be able to extend each of them separately maybe in the future.
So my controller layer works with request type but my service layer works with dto.)
To accomplish this type of mapping, I used mapstruct, and their interfaces exist in the "mapper" package.

5. The trigger of this application is from the user side. So please first create a user By using the "UserController" class. I put the `curl` of it ih bellow:
`curl --location --request POST 'http://localhost:8080/users' \
--header 'Content-Type: application/json' \
--data-raw '{
 "username": "Mahzad",
 "nationalCode": "456",
 "iban": "564656546"
}'`

6. Then for continuining, your user(s) should have at least one wallet. So please use the "create wallet" api, from the "WalletController" class
to create the desired number of wallet(s). I put the `curl` of it in bellow:
`curl --location --request POST 'http://localhost:8080/wallets/cbc3689f-1887-449d-8304-f90ccc3f0c10' \
--header 'Content-Type: application/json' \
--data-raw '{
 "name": "toll"
}'`


7. Also you can access more feature like: disable wallet, enable wallet, display user wallets.

8. There is a Walletransaction class, that handels 3 types of wallet Transactions. such as, cash-in, cash-ou and wallet transfer(wallet to walet) 

9. Due to User Stroy and my system design, Cash-in and Cash-out transaction, have debit transaction.
So per these kind of requests (cash-in, cash-out) a debit transaction (withdraw, deposit) will happen and save. 

10. Through the application, I had thrown some custome exceptions. you can find them in the "exception" package.

11. Also I handle those custome exception in the "ExceptionHandling" class that exists in the "exception" package.

12. To add more information in my ExceptionHandling class (for adding more clarity), I wrote an "Exception Model" class to manage the payload information.
This class also exists in the "exception" package.
