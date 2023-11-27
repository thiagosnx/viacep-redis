package cepredis.domain;


import cepredis.dtos.RequestAddressDTO;
import lombok.*;

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

    public Address(RequestAddressDTO requestAddressDTO) {
        this.cep = requestAddressDTO.cep();
    }
}