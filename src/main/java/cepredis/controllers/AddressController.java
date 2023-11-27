package cepredis.controllers;

import cepredis.domain.Address;
import cepredis.dtos.RequestAddressDTO;
import cepredis.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody RequestAddressDTO requestAddressDTO) throws Exception {
        try {
            Address newAddress = addressService.createAddress(requestAddressDTO);
            return new ResponseEntity<>(newAddress, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception("Formato de CEP inválido, utilize 8 dígitos (somente números)");
        }
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Address> getAddress(@PathVariable String cep) throws Exception {
        try {
            Address address = addressService.getAddress(cep);
            if (address != null) {
                return new ResponseEntity<>(address, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new Exception("Formato de CEP inválido, utilize 8 dígitos (somente números)");
        }
    }



}
