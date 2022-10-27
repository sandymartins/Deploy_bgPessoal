package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

//indica que a classe UsuarioRepositoryTest é uma classe de test, é que esse test será rodado em uma porta aleatoria local no meu computador(desde que ela não esteja já sendo usada)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//Indica que o teste a ser feito vai ser um teste unitário (por classe)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
	//Interface que busca os métodos da minha classe usuarioRepository
	@Autowired
	private UsuarioRepository usuarioRepository;
	//Inserindo usuários no meu banco de dados H2 para que eu possa testar as funções de procurar um usuário por nome e e-mail.
	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
		//foi passado um número padrão para subtituir os ID's que não vai ter mais relação com chave primária.
		//0L = id preenchido automaticamente pelo banco de dados
		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com", "12345678", "https://i.imgur.com/FETvs20.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com", "12345678", "https://i.imgur.com/FETvs20.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com", "12345678", "https://i.imgur.com/FETvs20.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com", "12345678", "https://i.imgur.com/FETvs20.jpg"));
	}
	//Indica o ínicio do teste
	@Test
	//Indica o nome do Teste, sendo opcional o uso
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario(){
		//O que eu espero receber da API, buscando um usuário pelo seu e-mail
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com");
		
		//comparando se o que eu esperava receber, foi o que eu solicitei
		assertTrue(usuario.get().getUsuario().equals("joao@email.com"));
		
	}
	
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {
		
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
	
}
