package com.piekoszek.nowaksharedrent.auth;

import com.piekoszek.nowaksharedrent.email.EmailService;
import com.piekoszek.nowaksharedrent.invite.exceptions.InviteCreatorException;
import com.piekoszek.nowaksharedrent.user.User;
import com.piekoszek.nowaksharedrent.user.UserService;
import com.piekoszek.nowaksharedrent.hash.HashService;
import com.piekoszek.nowaksharedrent.jwt.JwtData;
import com.piekoszek.nowaksharedrent.jwt.JwtService;
import com.piekoszek.nowaksharedrent.uuid.UuidService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
class AuthServiceImpl implements AuthService {

    private AccountRepository accountRepository;
    private UserService userService;
    private HashService hashService;
    private JwtService jwtService;
    private EmailService emailService;
    private UuidService uuidService;

    @Override
    public Optional<String> createAccount(Account account) {

        if(!accountRepository.existsByEmail(account.getEmail())) {

            String hashPassword = hashService.encrypt(account.getPassword());
            Account accountToCreate = new Account(account.getEmail(), account.getName(), hashPassword, null);
            User userToCreate = User.builder()
                    .email(account.getEmail())
                    .name(account.getName())
                    .apartments(new HashSet<>())
                    .build();
            accountRepository.save(accountToCreate);
            userService.createUser(userToCreate);
            return Optional.of(jwtService.generateToken(userToCreate));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> loginUser(Account account) {

        Account registeredAccount = accountRepository.findByEmail(account.getEmail());
        if (registeredAccount != null && hashService.compareWithHash(account.getPassword(), registeredAccount.getPassword())) {

            return Optional.of(jwtService.generateToken(userService.getUser(account.getEmail())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> refreshToken(String oldToken) {

        try {

            jwtService.validateToken(oldToken);
            JwtData oldTokenData = jwtService.readToken(oldToken);
            User updatedData = userService.getUser(oldTokenData.getEmail());
            return Optional.of(jwtService.updateTokenData(oldTokenData, updatedData));
        }
        catch (Exception e) {

            log.error("Invalid token: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void resetPassword(String email) {

        if (!userService.isAccountExists(email)) {

            throw new InviteCreatorException("User with email " + email + " not found!");
        }

        String passwordResetKey = uuidService.generateUuid();
        Account registeredAccount = accountRepository.findByEmail(email);
        registeredAccount.setResetPasswordKey(passwordResetKey);
        accountRepository.save(registeredAccount);

        emailService.sendSimpleMessage("jackie.n93@gmail.com", "password reset", "http://nowak-dev.piekoszek.pl/reset/" + passwordResetKey);
    }

    @Override
    public void setPassword(Account account) {

        Account registeredAccount = accountRepository.findByEmail(account.getEmail());

        if (registeredAccount.getResetPasswordKey().equals(account.getResetPasswordKey())) {
            registeredAccount.setPassword(hashService.encrypt(account.getPassword()));
            registeredAccount.setResetPasswordKey(null);
            accountRepository.save(registeredAccount);
        }
    }
}