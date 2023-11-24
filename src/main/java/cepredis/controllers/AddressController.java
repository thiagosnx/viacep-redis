package cepredis.controllers;

import cepredis.models.Address;
import cepredis.request.RequestAddress;
import cepredis.service.AddressService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private RedisTemplate<String, Address> redisTemplate;


    @PostMapping
    public ResponseEntity<Address> saveAddress(@RequestBody RequestAddress requestAddress) throws Exception {
        String key = "address:" + requestAddress.cep();
        Address newAddress = new Address(requestAddress);
        URL url = new URL("https://viacep.com.br/ws/"+requestAddress.cep()+"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String cep = "";
        StringBuilder jsonCep = new StringBuilder();

        while((cep = br.readLine()) != null){
            jsonCep.append(cep);
        }

        Address addressAux = new Gson().fromJson(jsonCep.toString(), Address.class);

        newAddress.setCep(addressAux.getCep());
        newAddress.setLogradouro(addressAux.getLogradouro());
        newAddress.setComplemento(addressAux.getComplemento());
        newAddress.setBairro(addressAux.getBairro());
        newAddress.setLocalidade(addressAux.getLocalidade());
        newAddress.setUf(addressAux.getUf());

        redisTemplate.opsForValue().set(key, newAddress);
        redisTemplate.expire(key, 300, TimeUnit.SECONDS);

        return ResponseEntity.ok(newAddress);
    }

}
