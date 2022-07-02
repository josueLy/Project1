package proyecto1.proyect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import proyecto1.model.Client;
import proyecto1.repository.IClientRepository;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = IClientRepository.class)
@ComponentScan(basePackages = {"proyecto1"})

public class Proyecto1Java /*implements CommandLineRunner*/ {

	/*public void reactor(){
		Mono.just(new Afiliado(1, "Juan", "Perez",  "44444444", 50000.0))
				.doOnNext(p -> {
					System.out.println("[Reactor]: " + p);
				})
				.subscribe(p -> System.out.println("[Reactor]: " + p));
	}*/

	/*public void mono(){
		Mono.just(new Afiliado(1, "Juan", "Perez",  "44444444", 50000.0))
				.subscribe(p -> System.out.println(p.toString()));
	}*/

	public void flux(){
		List<Client> clients = new ArrayList<>();
		clients.add(new Client(01,"75509576","Rolando","920338624","Pueblo Nuevo"));
		clients.add(new Client(02,"75509576","Rolando","920338624","Pueblo Nuevo"));

		Flux.fromIterable(clients).subscribe(p -> System.out.println(p));
	}

	/*public void fluxToMono(){
		List<Afiliado> afiliados = new ArrayList<>();
		afiliados.add(new Afiliado(1, "Juan", "Perez",  "44444444", 50000.0));
		afiliados.add(new Afiliado(2, "Pedro", "Torres",  "43333333", 10000.0));
		afiliados.add(new Afiliado(3, "Jose", "Lopez",  "42222222", 80000.0));
		afiliados.add(new Afiliado(4, "Raul", "Rosas",  "45555555", 90000.0));

		Flux<Afiliado> flux = Flux.fromIterable(afiliados);
		flux.collectList().subscribe(lista -> System.out.println(lista.toString()));
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Proyecto1Java.class, args);
	}

	//@Override
	//public void run(String... args) throws Exception {
		//reactor();
		//flux();
		//Creacion app = new Creacion();
		//Transformacion app = new Transformacion();
		//Filtrado app = new Filtrado();
		//Combinacion app = new Combinacion();
		//Error app = new Error();
		//Condicion app = new Condicion();
		//Matematicos app = new Matematicos();
		//app.range();
		//app.reapet();
		//app.map();
		//app.filter();
		//app.distinct();
		//app.take();
		//app.takeLast();
		//app.skip();
		//app.merge();
		//app.zip();
		//app.zipWith();
		//app.retry();
		//app.onError();
		//app.errorResume();
		//app.errorMap();
		//app.takeUntil();
		//app.timeOut();
		//app.average();
		//app.count();
		//sapp.min();
		//app.sum();
	//}
}
