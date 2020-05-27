package com.piekoszek.nowaksharedrent.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean isAccountExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void addApartmentToUser(UserApartment userApartment, User user) {
        user.addApartment(userApartment);
    }
}
