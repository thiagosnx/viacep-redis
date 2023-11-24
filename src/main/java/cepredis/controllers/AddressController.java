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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;


    @PostMapping
    public ResponseEntity saveAddress(@RequestBody RequestAddress requestAddress) throws Exception {
        Address newAddress = new Address(requestAddress);
        URL url = new URL("https://viacep.com.br/ws/"+requestAddress.cep()+"/json/");
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        String cep = ""; //string vazia onde serão acumulados os dados
        StringBuilder jsonCep = new StringBuilder(); //o sb é utilizado para criar um objeto com varios metodos que sao facilmente manipulados, com varios metodos

        while((cep = br.readLine()) != null){ //while usa o br para ler todas as linhas da resposta até o fim, nota-se que a string que estava vazia está sendo utilizada para acumular os dados
            jsonCep.append(cep); //o br le as linhas e retorna muitas strings, aqui utilizamos o metodo append do sb para acumular todas elas em uma string só, o jsoncep
        }

        Address addressAux = new Gson().fromJson(jsonCep.toString(), Address.class); //utilizando lib do google que transforma objetos json em objetos de uma classe e vice-versa
        //aqui ele está convertendo o objeto json para um objeto da classe address

        newAddress.setCep(addressAux.getCep());
        newAddress.setLogradouro(addressAux.getLogradouro());
        newAddress.setComplemento(addressAux.getComplemento());
        newAddress.setBairro(addressAux.getBairro());
        newAddress.setLocalidade(addressAux.getLocalidade());
        newAddress.setUf(addressAux.getUf());

        int hashCode = requestAddress.hashCode();
        addressService.saveAddress(requestAddress, 300);
        return ResponseEntity.ok(newAddress);
    }

}
