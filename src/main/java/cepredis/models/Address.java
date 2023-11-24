package cepredis.models;


import cepredis.request.RequestAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
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