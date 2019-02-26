package com.piekoszek.nowaksharedrent.auth;

class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;

    AuthServiceImpl (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean createAccount(Account account) {
        if(!accountRepository.existsById(account.getEmail())) {
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public Account findAccount(String id) {
        return (accountRepository.findOne(id));
    }
}