package com.piekoszek.nowaksharedrent.transactions

import com.piekoszek.nowaksharedrent.apartment.Apartment
import com.piekoszek.nowaksharedrent.apartment.ApartmentService
import com.piekoszek.nowaksharedrent.dto.UserService
import com.piekoszek.nowaksharedrent.uuid.UuidService
import spock.lang.Specification
import spock.lang.Subject

class TransactionsServiceTest extends Specification {

    @Subject
    TransactionsService transactionsService
    ApartmentService apartmentService = Mock(ApartmentService)
    UserService userService = Mock(UserService)
    UuidService uuidService = Mock(UuidService)

    def setup() {
        transactionsService = new TransactionsServiceConfiguration().transactionsService(apartmentService, userService, uuidService)
    }

    def "Adding first transaction (with payer) of the given month" () {

        given: "User chose an apartment, transaction type and provided transaction title and value"
        def userId = "tenant1@mail.com"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def transactionType = "COMMON_PRODUCT"
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

        then: "Transaction is added and balance of all tenants is updated"
        1 * apartmentService.updateBalance(userId, apartmentId, value)
        Set<Transaction> transactions = transactionsService.getTransactionsFromMonth("5_2019_9dc8bd06-6a89-455a-bc31-9f85f5036b5a").getTransactions()
        transactions[0].getApartmentId() == apartmentId
        transactions[0].getPaidBy() == userId
        transactions[0].getTitle() == title
        transactions[0].getType() == TransactionType.valueOf(transactionType)
        transactions[0].getValue() == 15
    }

/*    def "Adding transaction (with payer) but there is already another transaction from this month" () {

        given: ""


        when:


        then: ""

    }

    def "Adding transaction but payer doesn't exist in database" () {

        given: ""


        when:


        then: ""

    }

    def "Adding transaction but apartment where where it should be added doesn't exist in database" () {

        given: ""


        when:


        then: ""

    }

    def "Adding transaction but it doesn't have any known transaction type" () {

        given: ""


        when:


        then: ""

    }

    def "Adding transaction with negative value" () {

        given: ""


        when:


        then: ""

    }

    def "Extracting transactions from a current month" () {

        given: ""


        when:


        then: ""

    }*/
}
