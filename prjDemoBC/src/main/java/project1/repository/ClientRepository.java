package project1.repository;

import org.springframework.stereotype.Repository;
import project1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientRepository implements  IClientRepository{
    @Override
    public Mono<Client> registrar(Client client) {
        return null;
    }

    @Override
    public Mono<Client> modificar(Client client) {
        return null;
    }

    @Override
    public Flux<Client> listar() {
        List<Client> clientes = new ArrayList<>();

        clientes.add(new Client(01,"75509576","Rolando","920338624","Pueblo Nuevo"));
        clientes.add(new Client(02,"75509685","Junior ","938406906","Chepen"));

        return Flux.fromIterable(clientes);
    }

    @Override
    public Mono<Client> listarPorId(Integer id) {
        return null;
    }

    @Override
    public Mono<Void> eliminar(Integer id) {
        return null;
    }
}
