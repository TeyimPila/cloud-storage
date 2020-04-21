package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CredentialService {

    final CredentialRepository credentialRepository;
    final EncryptionService encryptionService;

    @Autowired
    public CredentialService(
            CredentialRepository credentialRepository,
            EncryptionService encryptionService
    ) {
        this.credentialRepository = credentialRepository;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getUserCredentials(User user) {
        return credentialRepository.findByUser(user);
    }

    public Credential createOrUpdate(Credential credential) {
        if (credential.getCredentialId() == null) {
            return saveCredential(credential);
        } else {
            return updateCredential(credential);
        }
    }

    public Credential saveCredential(Credential cred) {

        String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), cred.getKey());
        cred.setPassword(encryptedPassword);

        Integer id = credentialRepository.save(cred);
        return credentialRepository.find(id);
    }

    public Credential updateCredential(Credential cred) {

        String encryptedPassword = encryptionService.encryptValue(cred.getPassword(), cred.getKey());
        cred.setPassword(encryptedPassword);

        Integer id = credentialRepository.update(cred);
        return credentialRepository.find(id);
    }

    public void deleteCredential(Integer credentialId) {
        credentialRepository.deleteCredential(credentialId);
    }
}
