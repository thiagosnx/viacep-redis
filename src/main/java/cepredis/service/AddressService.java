package cepredis.service;


import cepredis.models.Address;
import cepredis.request.RequestAddress;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AddressService {
    @Autowired
    private RedisTemplate<String, Address> redisTemplate;

    public Address createAddress(@RequestBody RequestAddress requestAddress) throws IOException {

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

        String key = "address:" + newAddress.getCep();
        redisTemplate.opsForValue().set(key, newAddress);
        redisTemplate.expire(key, 300, TimeUnit.SECONDS);

        return newAddress;

    }

    public Address getAddress(String cep) throws Exception {
        String key = "address:" + cep;

        Address cachedAddress = redisTemplate.opsForValue().get(key);

        if (cachedAddress != null) {
            return cachedAddress;
        } else {
            //criando cep diretamente no url igual a api original
            URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            StringBuilder jsonCep = new StringBuilder();

            while ((line = br.readLine()) != null) {
                jsonCep.append(line);
            }

            Address newAddress = new Gson().fromJson(jsonCep.toString(), Address.class);

            redisTemplate.opsForValue().set(key, newAddress);
            redisTemplate.expire(key, 300, TimeUnit.SECONDS);

            return newAddress;
        }
    }


}
