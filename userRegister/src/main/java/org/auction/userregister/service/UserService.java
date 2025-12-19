package org.auction.userregister.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.auction.userregister.entity.User;
import org.auction.userregister.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final Keycloak keycloak;

    public UserService(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
    }

    @Value("${keycloak.realm}")
    private String realm;

    @Transactional
    public User createUser(User user, String password) {
        if (userRepository.existsByEmail(user.getEmail())){
            log.info("User with email {} already exists", user.getEmail());
            throw new RuntimeException("User with email " + user.getEmail() + " already exists");
        }
        User savedUser = userRepository.save(user);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        try (Response response = usersResource.create(userRepresentation)) {
            if (response.getStatus() == 201) {
                String userId = getCreatedId(response);
                savedUser.setKeycloakId(userId);
                userRepository.save(savedUser);
                log.info("User created in Keycloak with ID: {}", userId);
            } else {
                log.error("Failed to create user in Keycloak. Status: {}", response.getStatus());
                throw new RuntimeException("Failed to create user in Keycloak");
            }
        }

        return savedUser;
    }


    private String getCreatedId(Response response) {
        String location = response.getHeaderString("Location");
        if (location != null) {
            return location.replaceAll(".*/(.*)$", "$1");
        }
        return null;
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        if (user.getKeycloakId() != null) {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();
            usersResource.delete(user.getKeycloakId());
            log.info("User deleted from Keycloak with ID: {}", user.getKeycloakId());
        }

        userRepository.delete(user);
        log.info("User deleted from local database with ID: {}", userId);
    }


    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public User updateUser(Long userId, User updatedUser, String newPassword) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());

        User savedUser = userRepository.save(existingUser);

        if (existingUser.getKeycloakId() != null) {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            UserRepresentation userRepresentation = usersResource.get(existingUser.getKeycloakId()).toRepresentation();
            userRepresentation.setUsername(updatedUser.getUsername());
            userRepresentation.setEmail(updatedUser.getEmail());
            userRepresentation.setFirstName(updatedUser.getFirstName());
            userRepresentation.setLastName(updatedUser.getLastName());

            usersResource.get(existingUser.getKeycloakId()).update(userRepresentation);

            if (newPassword != null && !newPassword.isEmpty()) {
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(false);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(newPassword);

                usersResource.get(existingUser.getKeycloakId()).resetPassword(credentialRepresentation);
            }

            log.info("User updated in Keycloak with ID: {}", existingUser.getKeycloakId());
        }

        return savedUser;
    }
}
