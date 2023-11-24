package cepredis.controllers;

import cepredis.models.Address;
import cepredis.request.RequestAddress;
import cepredis.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody RequestAddress requestAddress) throws Exception {
        try {
            Address newAddress = addressService.createAddress(requestAddress);
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
