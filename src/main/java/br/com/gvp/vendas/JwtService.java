package br.com.gvp.vendas;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.gvp.vendas.entity.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiration}")
	private String expiration;
	
	@Value("${security.jwt.key-signature}")
	private String keySignature;
	
	public String generateToken(Usuario usuario ) {
		long expString = Long.valueOf(expiration);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		
		return Jwts
					.builder()
					.setSubject(usuario.getLogin())
					.setExpiration(data)
					.signWith(SignatureAlgorithm.HS512, keySignature)
					.compact();
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
		JwtService service = contexto.getBean(JwtService.class);
		Usuario usuario = Usuario.builder().login("guizin").build();
		String token = service.generateToken(usuario);
		System.out.println(token);
	}
}
