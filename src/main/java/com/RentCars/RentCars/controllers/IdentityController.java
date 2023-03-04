package com.RentCars.RentCars.controllers;

import com.RentCars.RentCars.persistances.entities.Identity;
import com.RentCars.RentCars.persistances.entities.User;
import com.RentCars.RentCars.services.IdentityService;
import com.RentCars.RentCars.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/identities")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Identity>> getAllIdentities() {
        List<Identity> identities = identityService.getAllIdentities();
        return new ResponseEntity<>(identities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Identity> getIdentityById(@PathVariable Long id) {
        Identity identity = identityService.getIdentityById(id);
        if (identity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(identity, HttpStatus.OK);
        }
    }

    @PostMapping
    public void saveIdentity(@RequestBody Identity identity) {
        identityService.saveIdentity(identity);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> saveIdentityWithUser(@RequestBody Identity identity , @PathVariable Long userId) {
        User user=userService.getUserById(userId);
        identity.setUser(user);
        identityService.saveIdentity(identity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateIdentity(@PathVariable Long id, @RequestBody Identity identity) {
        Identity existingIdentity = identityService.getIdentityById(id);
        if (existingIdentity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingIdentity.setPath(identity.getPath());
        existingIdentity.setStatus(identity.getStatus());
        identityService.updateIdentity(existingIdentity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIdentity(@PathVariable Long id) {
        Identity existingIdentity = identityService.getIdentityById(id);
        if (existingIdentity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            identityService.deleteIdentity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/users/{userId}")
    public List<Identity> getIdentitiesByUser(@PathVariable Long userId) {
        return identityService.getIdentitiesByUser(userId);
    }
}
