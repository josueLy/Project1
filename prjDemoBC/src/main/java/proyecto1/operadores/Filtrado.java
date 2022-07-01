package proyecto1.operadores;

import proyecto1.model.Client;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class Filtrado {

   /* public void filter(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .filter(p -> p.getNombres().equals("Juan"))
                .subscribe(p -> System.out.println(p.toString()));
    }

    public void distinct(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(2, "Juan", "Perez",  "44444444", 50000.0));
        //afiliados.add(new Afiliado(1, "Pedro", "Torres",  "43333333", 10000.0));
        //afiliados.add(new Afiliado(1, "Jose", "Lopez",  "42222222", 80000.0));
        //afiliados.add(new Afiliado(1, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .distinct()
                .subscribe(p -> System.out.println(p.toString()));
    }

    public void take(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .take(2)
                .subscribe(p -> System.out.println(p.toString()));
    }

    public void takeLast(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .takeLast(1)
                .subscribe(p  -> System.out.println(p.toString()));
    }

    public void skip(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .skip(1)
                .subscribe(p  -> System.out.println(p.toString()));
    }

    public void skipLast(){
        List<Client> afiliados = new ArrayList<>();
        afiliados.add(new Client(1, "Juan", "Perez",  "44444444", 50000.0));
        afiliados.add(new Client(1, "Pedro", "Torres",  "43333333", 10000.0));
        afiliados.add(new Client(1, "Jose", "Lopez",  "42222222", 80000.0));
        afiliados.add(new Client(4, "Raul", "Rosas",  "45555555", 90000.0));

        Flux.fromIterable(afiliados)
                .skipLast(1)
                .subscribe(p  -> System.out.println(p.toString()));
    }

    */
}
