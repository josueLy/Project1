package proyecto1.operadores;

import proyecto1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Creacion {

    //Mono
   /* public void justFrom(){
        Mono.just(new Client(1, "Juan", "Perez",  "44444444", 50000.0));

        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados);
    }

    public void empty(){
        Mono.empty();
        Flux.empty();
    }

    public void range(){
        Flux.range(0, 3)
                .doOnNext(i -> System.out.println("i : " + i))
                .subscribe();
    }

    public void reapet(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));


        Flux.fromIterable(afiliados).repeat(3).subscribe(p -> System.out.println(p.toString()));
    }

    */

}
