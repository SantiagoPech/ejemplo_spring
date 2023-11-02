package net.itinajero;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import net.itinajero.model.Categoria;
import net.itinajero.model.Perfil;
import net.itinajero.model.Usuario;
import net.itinajero.model.Vacante;
import net.itinajero.repository.CategoriasRepository;
import net.itinajero.repository.PerfilesRepository;
import net.itinajero.repository.UsuariosRepository;
import net.itinajero.repository.VacantesRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriasRepository repoCategorias;
	
	@Autowired
	private VacantesRepository repoVacantes;
	
	@Autowired
	private PerfilesRepository repoPerfiles;
	
	@Autowired
	private UsuariosRepository repoUsuarios;

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		//System.out.println(repo);
		
		//Crud
		//guardar();
		//buscarPorId();
		//modificar();
		//eliminar();
		//conteo();
		//eliminarTodos();
		//encontrarPorIds();
		//buscarTodos();
		//existeId();
		//guardarTodas();
		
		//Jpa
		//buscarTodosJpa();
		//borrarTodosEnBloque();
		
		//PagingAndSortingRepository
		//buscarTodosOrdenado();
		//buscarTodosPaginacion();
		//buscarTodosPaginacionOrdenados();
		
		//Vacantes
		//buscarVacantes();
		//guardarVacante();
		
		//Perfiles
		//crearPerfilesAplicacion();
		
		//Usuario con peril
		//crearUsuarioConDosPerfiles();
		//buscarUsuario();
		
		//keywords
		//buscarVacantesPorEstatus();
		//buscarVacantesPorDestacadoEstatus();
		//buscarVacantesSalario();
		buscarVacantesVariosEstatus();
	}
	
	/*
	 * Query Method: Buscar Vacantes por varios Estatus (In)
	 */
	public void buscarVacantesVariosEstatus() {
		String[] estatus = new String[] {"Eliminada", "Aprobada"};
		List<Vacante> lista = repoVacantes.findByEstatusIn(estatus);
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista) {
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus());
		}
	}
	
	/*
	 * Query Method: Buscar Vacantes rango de Salario (Between)
	 */
	public void buscarVacantesSalario() {
		List<Vacante> lista = repoVacantes.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista) {
			System.out.println(v.getId() + ": "+v.getNombre()+": $"+v.getSalario());
		}
	}
	
	/*
	 * Query Method: Buscar Vacantes por Destacado y  Estatus Ordenado por Id Desc
	 */
	public void buscarVacantesPorDestacadoEstatus() {
		List<Vacante> lista = repoVacantes.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista) {
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus()+": "+v.getDestacado());
		}
	}
	
	/*
	 * Query Method: Buscar Vacantes por Estatus
	 */
	public void buscarVacantesPorEstatus() {
		List<Vacante> lista = repoVacantes.findByEstatus("Eliminada");
		System.out.println("Registros encontrados: "+lista.size());
		for (Vacante v : lista) {
			System.out.println(v.getId() + ": "+v.getNombre()+": "+v.getEstatus());
		}
	}
	
	/*
	 * Metodo para buscar un usuario y desplegar sus perfiles asociados
	 */
	public void buscarUsuario() {
		Optional<Usuario> optional = repoUsuarios.findById(50);
		if(optional.isPresent()) {
			Usuario u = optional.get();
			System.out.println("Uusario: " +u.getNombre());
			System.out.println("Perfiles asignados");
			for (Perfil p : u.getPerfiles()) {
				System.out.println(p.getPerfil());
			}
		}
		else {
			System.out.println("Usuario no encontrado");
		}
	}
	
	/*
	 * Crear un usuario con 2 perfiles ("ADMISTRADOR", "USUARIO")
	 */
	private void crearUsuarioConDosPerfiles() {
		Usuario user = new Usuario();
		user.setNombre("Ivan Tinajero");
		user.setEmail("ivanetinajero@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("itinajero");
		user.setPassword("123456");
		user.setEstatus(1);
		
		Perfil per1 = new Perfil();
		per1.setId(2);
		
		Perfil per2 = new Perfil();
		per2.setId(3);
		
		user.agregar(per1);
		user.agregar(per2);
		
		repoUsuarios.save(user);
	}
	
	/*
	 * Metodo para crear PERFILES / ROLES
	 */
	private void crearPerfilesAplicacion() {
		repoPerfiles.saveAll(getPerfilesAplicacion());
	}
	
	/*
	 * Guardar una Vacante
	 */
	private void guardarVacante() {
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de Matematicas");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de Matemaicas");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.0);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		vacante.setDetalles("<h1> Los requisitos para profesor de matematicas </h1>");
		Categoria cat = new Categoria();
		cat.setId(10);
		vacante.setCategoria(cat);
		repoVacantes.save(vacante);
	}
	
	private void buscarVacantes() {
		List<Vacante> lista = repoVacantes.findAll();
		for (Vacante v : lista) {
			System.out.println(v.getId()+ " " + v.getNombre() + " -> " + v.getCategoria().getNombre());
		}
	}
	
	/*
	 * Metodo findAll [con Paginación y Ordenados] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosPaginacionOrdenados() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5, Sort.by("nombre").descending()));
		System.out.println("Total de registros: "+page.getNumberOfElements());
		System.out.println("Total de paginas: "+page.getTotalPages());
		for (Categoria c : page.getContent()) {
			System.out.println(c.getId()+ " " + c.getNombre());
		}
	}
	
	/*
	 * Metodo findAll [con Paginación] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosPaginacion() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(0, 5));
		System.out.println("Total de registros: "+page.getNumberOfElements());
		System.out.println("Total de paginas: "+page.getTotalPages());
		for (Categoria c : page.getContent()) {
			System.out.println(c.getId()+ " " + c.getNombre());
		}
	}
	

	/*
	 * Metodo findAll [Ordenados por un campo] - Interfaz PagingAndSortingRepository
	 */
	private void buscarTodosOrdenado() {
		List<Categoria> categorias = repoCategorias.findAll(Sort.by("nombre").descending()); //sin el .descending se ordenara de forma ascendente
		for (Categoria c : categorias) {
			System.out.println(c.getId()+ " " + c.getNombre());
		}
	}
	
	/*
	 * Metodo deleteAllByBatch [usar con precuación] - Interfaz JpaRepository
	 */
	private void borrarTodosEnBloque() {
		repoCategorias.deleteAllInBatch();
	}
	
	/*
	 * Metodo findAll - Interfaz JpaRepository
	 */
	private void buscarTodosJpa() {
		List<Categoria> categorias = repoCategorias.findAll();
		for (Categoria c : categorias) {
			System.out.println(c.getId()+ " " + c.getNombre());
		}
	}
	
	/*
	 * Metodo saveAll - Interfaz CrudRepository
	 */
	private void guardarTodas() {
		List<Categoria> categorias = getListaCategorias();
		repoCategorias.saveAll(categorias);
	}
	
	/*
	 * Metodo existsById - Interfaz CrudRepository
	 */
	private void existeId() {
		boolean existe = repoCategorias.existsById(50);
		System.out.println("La categoria existe: "+ existe);
	}
	
	/*
	 * Metodo findAll - Interfaz CrudRepository
	 */
	private void buscarTodos() {
		Iterable<Categoria> categorias = repoCategorias.findAll();
		for (Categoria cat : categorias) {
			System.out.println(cat);
		}
	}
	
	/*
	 * Metodo findAllById - Interfaz CrudRepository
	 */
	private void encontrarPorIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);
		ids.add(10);
		Iterable<Categoria> categorias = repoCategorias.findAllById(ids);
		for (Categoria cat : categorias) {
			System.out.println(cat);
		}
	}
	
	/*
	 * Metodo deleteAll - Interfaz CrudRepository
	 */
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}
	
	/*
	 * Metodo count - Interfaz CrudRepository
	 */
	private void conteo() {
		long count = repoCategorias.count();
		System.out.println("Total Categorias: "+count);
	}
	
	/*
	 * Metodo deleteById - Interfaz CrudRepository
	 */
	private void eliminar() {
		int idCategoria = 1;
		repoCategorias.deleteById(idCategoria);
	}
	
	/*
	 * Metodo save(update) - Interfaz CrudRepository
	 */
	private void modificar() {
		Optional<Categoria> optional = repoCategorias.findById(2);
		if (optional.isPresent()) {
			Categoria catTmp = optional.get();
			catTmp.setNombre("Ing. de software");
			catTmp.setDescripcion("Desarrollo de sistemas");
			repoCategorias.save(catTmp);
			System.out.println(optional.get());
		}
		else {
			System.out.println("Categoria no encontrada");
		}
	}
	
	/*
	 * Metodo findById - Interfaz CrudRepository
	 */
	private void buscarPorId() {
		Optional<Categoria> optional = repoCategorias.findById(5);
		if (optional.isPresent()) 
			System.out.println(optional.get());
		else 
			System.out.println("Categoria no encontrada");
	}
	
	private void guardar() {
		
		Categoria cat = new Categoria();
		cat.setNombre("Finanzas");
		cat.setDescripcion("Trabajos relacionados con finanzas y contabilidad");
		repoCategorias.save(cat);
		
		System.out.println(cat);
	}
	
	private void eliminarAnterior() {
		System.out.println("Eliminando un registro");
	}
	
	/*
	 * Metodo que regresa una lista de 3 Categorias
	 * @return
	 */
	private List<Categoria> getListaCategorias(){
		List<Categoria> lista = new LinkedList<Categoria>();
		//Categoria 1
		Categoria cat1 = new Categoria();
		cat1.setNombre("Programador de Blockchain");
		cat1.setDescripcion("Trabajos relacionados con Bitcon y Criptomonedas");
		
		//Categoria 2
		Categoria cat2 = new Categoria();
		cat2.setNombre("Soldador/Pintura");
		cat2.setDescripcion("Trabajos relacionados con soldadura, pintura y enderezado");
		
		//Categoria 3
		Categoria cat3 = new Categoria();
		cat3.setNombre("Ingeniero Industrial");
		cat3.setDescripcion("Trabajos relacionados con Ingenieria industrial");
		
		lista.add(cat1);
		lista.add(cat2);
		lista.add(cat3);
		return lista;
	}
	
	/**
	 * Metodo que regresa una lista de objetos de tipo Perfil que representa los diferentes PERFILES
	 * O ROLES que tendremos en nuestra aplicacion de Empleos
	 * @return 
	 */
	private List<Perfil> getPerfilesAplicacion(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil per1 = new Perfil();
		per1.setPerfil("SUPERVISOR");
		
		Perfil per2 = new Perfil();
		per2.setPerfil("ADMINISTRADOR");
		
		Perfil per3 = new Perfil();
		per3.setPerfil("USUARIO");
		
		lista.add(per1);
		lista.add(per2);
		lista.add(per3);
		
		return lista;
	}

}
