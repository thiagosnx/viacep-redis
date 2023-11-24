package cepredis.models;


import cepredis.request.RequestAddress;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private String cep;

    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;

    public Address(RequestAddress requestAddress) {
        this.cep = requestAddress.cep();
    }
}