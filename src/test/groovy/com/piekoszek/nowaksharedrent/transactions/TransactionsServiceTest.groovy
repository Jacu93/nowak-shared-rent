package com.piekoszek.nowaksharedrent.transactions

import com.piekoszek.nowaksharedrent.apartment.Apartment
import com.piekoszek.nowaksharedrent.apartment.ApartmentService
import com.piekoszek.nowaksharedrent.apartment.Tenant
import com.piekoszek.nowaksharedrent.user.UserService
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
        def tenantId = "tenant1@mail.com"
        def adminId = "admin@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 1500

        when:
        userService.isAccountExists(tenantId) >> true
        Set<Tenant> tenants = new HashSet<>()
        tenants.add(Tenant.builder().email(adminId).build())
        tenants.add(Tenant.builder().email(tenantId).build())
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> Apartment.builder()
                .id("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
                .address("Street 1")
                .city("City")
                .admin(adminId)
                .tenants(tenants)
                .build()
        Transaction transaction = Transaction.builder()
                .paidBy(tenantId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, tenantId)

        then: "Transaction is added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Transactions transactionsOfTheMonth = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId)
        List<Transaction> transactions = transactionsOfTheMonth.getTransactions()
        transactions[0].getApartmentId() == apartmentId
        transactions[0].getPaidBy() == tenantId
        transactions[0].getTitle() == title
        transactions[0].getType() == transactionType
        transactions[0].getValue() == value
        Set<Payer> payers = transactionsOfTheMonth.getPayers()

        payers.each {
            if (it.getEmail() == adminId) {
                assert it.getBalance() == (int) (value / 2)
            } else {
                assert it.getBalance() == (int) (value / 2 - value)
            }
        }
    }

    def "Adding first transaction (without payer - bill type) of the given month as an administrator" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def tenantId = "tenant1@mail.com"
        def adminId = "admin@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.BILL
        def title = "Toilet paper"
        def value = 1500

        when:
        userService.isAccountExists(adminId) >> true
        Set<Tenant> tenants = new HashSet<>()
        tenants.add(Tenant.builder().email(adminId).build())
        tenants.add(Tenant.builder().email(tenantId).build())
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> Apartment.builder()
                .id("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
                .address("Street 1")
                .city("City")
                .admin(adminId)
                .tenants(tenants)
                .build()
        Transaction transaction = Transaction.builder()
                .paidBy(adminId)
                .apartmentId(apartmentId)
                .title(title)
                .value(value)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(transaction, adminId)

        then: "Transaction is added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Transactions transactionsOfTheMonth = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId)
        List<Transaction> transactions = transactionsOfTheMonth.getTransactions()
        transactions[0].getApartmentId() == apartmentId
        transactions[0].getPaidBy() == null
        transactions[0].getTitle() == title
        transactions[0].getType() == transactionType
        transactions[0].getValue() == value
        Set<Payer> payers = transactionsOfTheMonth.getPayers()

        payers.each {
            it.getBalance() == (int) (value / 2)
        }
    }

    def "Adding transaction (with payer) but there is already another transaction from this month" () {

        given: "User chose an apartment, transaction type and provided transaction title and value twice"
        def tenantId = "tenant1@mail.com"
        def adminId = "admin@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def firstTransactionTitle = "Toilet paper"
        def firstTransactionValue = 1500
        def secondTransactionTitle = "Cleaning wipes"
        def secondTransactionValue = 4500

        when:
        userService.isAccountExists(tenantId) >> true
        Set<Tenant> tenants = new HashSet<>()
        tenants.add(Tenant.builder().email(adminId).build())
        tenants.add(Tenant.builder().email(tenantId).build())
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> Apartment.builder()
                .id("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
                .address("Street 1")
                .city("City")
                .admin(adminId)
                .tenants(tenants)
                .build()
        Transaction firstTransaction = Transaction.builder()
                .paidBy(tenantId)
                .apartmentId(apartmentId)
                .title(firstTransactionTitle)
                .value(firstTransactionValue)
                .type(transactionType)
                .build()
        Transaction secondTransaction = Transaction.builder()
                .paidBy(tenantId)
                .apartmentId(apartmentId)
                .title(secondTransactionTitle)
                .value(secondTransactionValue)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(firstTransaction, tenantId)
        transactionsService.newTransaction(secondTransaction, tenantId)

        then: "Both transactions are added and balance of all tenants is updated"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        Transactions transactionsOfTheMonth = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId)
        List<Transaction> transactions = transactionsOfTheMonth.getTransactions()
        transactions.size() == 2
        transactions.each {
            assert it.getApartmentId() == apartmentId
            assert it.getPaidBy() == tenantId
            assert it.getType() == transactionType
            assert it.getTitle() in [firstTransactionTitle,secondTransactionTitle]
            assert it.getValue() in [firstTransactionValue,secondTransactionValue]
        }
        Set<Payer> payers = transactionsOfTheMonth.getPayers()
        payers.each {
            if (it.getEmail() == adminId) {
                it.getBalance() == (int) ((firstTransactionValue + secondTransactionValue) / 2)
            } else {
                it.getBalance() == (int) ((firstTransactionValue + secondTransactionValue) / 2 - firstTransactionValue - secondTransactionValue)
            }
        }
    }

    def "Adding transaction but payer doesn't exist in database" () {

        given: "Unregistered user chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 1500

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
        def ex = thrown(TransactionCreatorException)
        ex.message == "User with email " + userId + " not found!"
    }

    def "Adding transaction but apartment where it should be added doesn't exist in database" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def title = "Toilet paper"
        def value = 1500

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
        def value = 1500

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
        def value = 1500

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
        def tenantId = "tenant1@mail.com"
        def adminId = "admin@mail.com"
        def currDate = Calendar.getInstance()
        currDate.setTimeInMillis(timeService.millisSinceEpoch())
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = TransactionType.COMMON_PRODUCT
        def firstTransactionTitle = "Toilet paper"
        def firstTransactionValue = 1500
        def secondTransactionTitle = "Cleaning wipes"
        def secondTransactionValue = 4500

        when:
        userService.isAccountExists("tenant1@mail.com") >> true
        Set<Tenant> tenants = new HashSet<>()
        tenants.add(Tenant.builder().email(adminId).build())
        tenants.add(Tenant.builder().email(tenantId).build())
        apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a") >> Apartment.builder()
                .id("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
                .address("Street 1")
                .city("City")
                .admin(adminId)
                .tenants(tenants)
                .build()
        Transaction firstTransaction = Transaction.builder()
                .paidBy(tenantId)
                .apartmentId(apartmentId)
                .title(firstTransactionTitle)
                .value(firstTransactionValue)
                .type(transactionType)
                .build()
        Transaction secondTransaction = Transaction.builder()
                .paidBy(tenantId)
                .apartmentId(apartmentId)
                .title(secondTransactionTitle)
                .value(secondTransactionValue)
                .type(transactionType)
                .build()
        transactionsService.newTransaction(firstTransaction, tenantId)
        transactionsService.newTransaction(secondTransaction, tenantId)
        def transactionsHistory = transactionsService.getTransactionsFromMonth(currDate.get(Calendar.MONTH)+1, currDate.get(Calendar.YEAR), apartmentId)

        then: "Two transactions added earlier are presented to the user"
        List<Transaction> transactions = transactionsHistory.getTransactions()
        transactions.size() == 2
        transactions.each {
            assert it.getApartmentId() == apartmentId
            assert it.getPaidBy() == tenantId
            assert it.getType() == transactionType
            assert it.getTitle() in [firstTransactionTitle,secondTransactionTitle]
            assert it.getValue() in [firstTransactionValue,secondTransactionValue]
        }
        Set<Payer> payers = transactionsHistory.getPayers()
        payers.each {
            if (it.getEmail() == adminId) {
                it.getBalance() == (int) ((firstTransactionValue + secondTransactionValue) / 2)
            } else {
                it.getBalance() == (int) ((firstTransactionValue + secondTransactionValue) / 2 - firstTransactionValue - secondTransactionValue)
            }
        }
    }
}
