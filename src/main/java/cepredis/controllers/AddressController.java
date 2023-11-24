package cepredis.controllers;

import cepredis.models.Address;
import cepredis.request.RequestAddress;
import cepredis.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;


@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody RequestAddress requestAddress) throws Exception {
        Address newAddress = this.addressService.createAddress(requestAddress);
        return new ResponseEntity<>(newAddress, HttpStatus.OK);
    }




}
