package cepredis.controllers;

import cepredis.models.Address;
import cepredis.request.RequestAddress;
import cepredis.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity saveAddress(@RequestBody RequestAddress requestAddress){
        int hashCode = requestAddress.hashCode();
        addressService.saveAddress(requestAddress, 300);
        return ResponseEntity.ok(requestAddress);
    }

}
