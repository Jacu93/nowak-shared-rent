package com.piekoszek.nowaksharedrent.transactions

import com.piekoszek.nowaksharedrent.apartment.Apartment
import com.piekoszek.nowaksharedrent.apartment.ApartmentService
import com.piekoszek.nowaksharedrent.dto.UserService
import com.piekoszek.nowaksharedrent.time.TimeService
import com.piekoszek.nowaksharedrent.transactions.exceptions.TransactionCreatorException
import com.piekoszek.nowaksharedrent.uuid.UuidService
import spock.lang.Specification
import spock.lang.Subject

class TransactionsServiceTest extends Specification {

    @Subject
    TransactionsService transactionsService
    ApartmentService apartmentService = Mock(ApartmentService)
    UserService userService = Mock(UserService)
    UuidService uuidService = Mock(UuidService)
    TimeService timeService = Mock(TimeService)

    def setup() {
        transactionsService = new TransactionsServiceConfiguration().transactionsService(apartmentService, userService, uuidService, timeService)
        timeService.millisSinceEpoch() >> 1556697600000
    }

    def "Adding first transaction (with payer) of the given month" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Set<Transaction> transactions = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId).getTransactions()
        transactions[0].getApartmentId() == apartmentId
        transactions[0].getPaidBy() == userId
        transactions[0].getTitle() == title
        transactions[0].getType() == transactionType
        transactions[0].getValue() == 15
    }

    def "Adding first transaction (without payer - bill type) of the given month as an administrator" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "admin@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.BILL
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Set<Transaction> transactions = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId).getTransactions()
        transactions[0].getApartmentId() == apartmentId
        transactions[0].getPaidBy() == null
        transactions[0].getTitle() == title
        transactions[0].getType() == transactionType
        transactions[0].getValue() == 15
    }

    def "Adding transaction (with payer) but there is already another transaction from this month" () {

        given: "User chose an apartment, transaction type and provided transaction title and value twice"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def firstTransactionTitle = "Toilet paper"
        def firstTransactionValue = 15
        def secondTransactionTitle = "Cleaning wipes"
        def secondTransactionValue = 45

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction firstTransaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(firstTransactionTitle)
                .value(firstTransactionValue)
                .type(transactionType)
                .build()
        Transaction secondTransaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(secondTransactionTitle)
                .value(secondTransactionValue)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(firstTransaction, userId)
        transactionsService.newTransaction(secondTransaction, userId)

        then: "Both transactions are added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Set<Transaction> transactions = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId).getTransactions()
        transactions.size() == 2
        for (Transaction transaction : transactions) {
            assert transaction.getApartmentId() == apartmentId
            assert transaction.getPaidBy() == userId
            assert transaction.getType() == transactionType
            assert transaction.getTitle() in [firstTransactionTitle,secondTransactionTitle]
            assert transaction.getValue() in [firstTransactionValue,secondTransactionValue]
        }
    }

    def "Adding transaction but payer doesn't exist in database" () {

        given: "Unregistered user chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> false
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is not added and proper message is returned"
        0 * apartmentService.updateBalance(userId, apartmentId, value)
        def ex = thrown(TransactionCreatorException)
        ex.message == "User with email " + userId + " not found!"
    }

    def "Adding transaction but apartment where where it should be added doesn't exist in database" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> null
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is not added and proper message is returned"
        def ex = thrown(TransactionCreatorException)
        ex.message == "Apartment doesn't exist!"
    }

    def "Adding bill type transaction (without payer) of the given month as a tenant" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.BILL
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is not added because user is not an administrator"
        def ex = thrown(TransactionCreatorException)
        ex.message == "Bill transaction type is available only for admin of an apartment!"
    }

    def "Adding transaction but it doesn't have any known transaction type" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = "COMMON"
        def title = "Toilet paper"
        def value = 15

        when:
        userService.isAccountExists(userId) >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction transaction = Transaction.builder()
                .paidBy(userId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(TransactionType.valueOf(transactionType))
                .build()
        transactionsService.newTransaction(transaction, userId)

        then: "Transaction is not added and exception is returned"
        thrown(IllegalArgumentException)
    }

    def "Extracting transactions from a current month" () {

        given: "User in order to check transaction history chose month, year and apartment"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def firstTransactionTitle = "Toilet paper"
        def firstTransactionValue = 15
        def secondTransactionTitle = "Cleaning wipes"
        def secondTransactionValue = 45

        when:
        userService.isAccountExists("tenant1@mail.com") >> true
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> new Apartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a", "Street 1", "City", "admin@mail.com")
        Transaction firstTransaction = Transaction.builder()
                .paidBy("tenant1@mail.com")
                .apartmentId(apartmentId)
                .title(firstTransactionTitle)
                .value(firstTransactionValue)
                .type(transactionType)
                .build()
        Transaction secondTransaction = Transaction.builder()
                .paidBy("tenant1@mail.com")
                .apartmentId(apartmentId)
                .title(secondTransactionTitle)
                .value(secondTransactionValue)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(firstTransaction, "tenant1@mail.com")
        transactionsService.newTransaction(secondTransaction, "tenant1@mail.com")
        def transactionsHistory = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId)

        then: "Two transactions added earlier are presented to the user"
        Set<Transaction> transactions = transactionsHistory.getTransactions()
        transactions.size() == 2
        for (Transaction transaction : transactions) {
            assert transaction.getApartmentId() == apartmentId
            assert transaction.getPaidBy() == "tenant1@mail.com"
            assert transaction.getType() == transactionType
            assert transaction.getTitle() in [firstTransactionTitle,secondTransactionTitle]
            assert transaction.getValue() in [firstTransactionValue,secondTransactionValue]
        }
    }
}
