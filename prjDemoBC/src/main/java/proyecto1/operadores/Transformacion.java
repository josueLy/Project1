package proyecto1.operadores;

import proyecto1.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Transformacion {

      /*  public void map(){
            List<Client> afiliados = new ArrayList<>();
            afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
            afiliados.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
            afiliados.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
            afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

            Flux.fromIterable(afiliados)
                    .map(p -> {
                        p.setAportes(p.getAportes() + 100000);
                        return p;
                    })
                    .subscribe(p-> System.out.println(p));

            //Flux<Integer> flux = Flux.range(0, 10);
            //Flux<Integer> flux2 = flux.map(x -> x + 10);
            //flux2.subscribe(x -> System.out.println("X : " + x));
        }

        public void flatMap(){
            List<Client> afiliados = new ArrayList<>();
            afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
            afiliados.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
            afiliados.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
            afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

            Flux.fromIterable(afiliados)
                    .flatMap(p -> {
                        p.setAportes(p.getAportes() + 10000.0);
                        return Mono.just(p);
                    })
                    .subscribe(p -> System.out.println(p.toString()));
        }

        public void groupBy(){
            List<Client> afiliados = new ArrayList<>();
            afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
            afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
            afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
            afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

            Flux.fromIterable(afiliados)
                    .groupBy(p -> p.getId())
                    .flatMap(idFlux -> idFlux.collectList())
                    .subscribe(x -> System.out.println(x.toString()));
        }

       */

}
