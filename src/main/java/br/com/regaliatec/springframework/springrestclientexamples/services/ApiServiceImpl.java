package br.com.regaliatec.springframework.springrestclientexamples.services;

import br.com.regaliatec.springframework.api.domain.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {

    private final RestTemplate restTemplate;


    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Data getData(Integer ddd) {
        Data data = restTemplate.getForObject("https://brasilapi.com.br/api/ddd/v1/" + ddd, Data.class);
        return data;
    }
}
