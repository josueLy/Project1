package proyecto1.operadores;

import proyecto1.model.Client;
import proyecto1.model.Afp;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Combinacion {

   /* public void merge(){
        List<Client> afiliados1 = new ArrayList<>();
        afiliados1.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados1.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados1.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados1.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        List<Client> afiliados2 = new ArrayList<>();
        afiliados2.add(new Client(5, "Rocio", "Salas",  "51111111", 70000.0));
        afiliados2.add(new Client(6, "Andres", "Ames",  "45523344", 60000.0));
        afiliados2.add(new Client(7, "Pablo", "Robles",  "41224455", 20000.0));
        afiliados2.add(new Client(8, "Julio", "Torres",  "51223344", 80000.0));

        List<Afp> afps = new ArrayList<>();
        afps.add(new Afp(1, "Integra"));
        afps.add(new Afp(2, "Prima"));

        Flux<Client> flux1 = Flux.fromIterable(afiliados1);
        Flux<Client> flux2 = Flux.fromIterable(afiliados2);
        Flux<Afp> flux3  = Flux.fromIterable(afps);

        Flux.merge(flux1, flux2, flux3)
                .subscribe(p -> System.out.println(p.toString()));
    }

    public void zip(){
        List<Client> afiliados1 = new ArrayList<>();
        afiliados1.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados1.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados1.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados1.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        List<Client> afiliados2 = new ArrayList<>();
        afiliados2.add(new Client(5, "Rocio", "Salas",  "51111111", 70000.0));
        afiliados2.add(new Client(6, "Andres", "Ames",  "45523344", 60000.0));
        afiliados2.add(new Client(7, "Pablo", "Robles",  "41224455", 20000.0));
        afiliados2.add(new Client(8, "Julio", "Torres",  "51223344", 80000.0));

        List<Afp> afps = new ArrayList<>();
        afps.add(new Afp(1, "Integra"));
        afps.add(new Afp(2, "Prima"));

        Flux<Client> flux1 = Flux.fromIterable(afiliados1);
        Flux<Client> flux2 = Flux.fromIterable(afiliados2);
        Flux<Afp> flux3  = Flux.fromIterable(afps);

        /*Flux.zip(flux1, flux2, (p1, p2) -> String.format("Flux1: %s, Flux2: %s", p1, p2))
                .subscribe(x -> System.out.println(x));*/
/*
        Flux.zip(flux1, flux2, flux3)
                .subscribe(x -> System.out.println(x.toString()));
    }

 */

/*
    public void zipWith(){
        List<Client> afiliados1 = new ArrayList<>();
        afiliados1.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados1.add(new Client(2, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados1.add(new Client(3, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados1.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        List<Client> afiliados2 = new ArrayList<>();
        afiliados2.add(new Client(5, "Rocio", "Salas",  "51111111", 70000.0));
        afiliados2.add(new Client(6, "Andres", "Ames",  "45523344", 60000.0));
        afiliados2.add(new Client(7, "Pablo", "Robles",  "41224455", 20000.0));
        afiliados2.add(new Client(8, "Julio", "Torres",  "51223344", 80000.0));

        List<Afp> afps = new ArrayList<>();
        afps.add(new Afp(1, "Integra"));
        afps.add(new Afp(2, "Prima"));

        Flux<Client> flux1 = Flux.fromIterable(afiliados1);
        Flux<Client> flux2 = Flux.fromIterable(afiliados2);
        Flux<Afp> flux3  = Flux.fromIterable(afps);

        flux1.zipWith(flux2, (p1, p2) -> String.format("Flux1: %s, Flux2: %s", p1, p2))
                .subscribe(x -> System.out.println(x.toString()));
    }
    */
}
