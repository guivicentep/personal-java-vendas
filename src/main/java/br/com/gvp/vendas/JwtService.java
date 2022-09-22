package br.com.gvp.vendas;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.gvp.vendas.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		return Jwts
					.parser()
					.setSigningKey(keySignature)
					.parseClaimsJws(token)
					.getBody();
	}
	
	public boolean tokenValido(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime data = 
							dataExpiracao.toInstant()
							.atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			return !LocalDateTime.now().isAfter(data);
		} catch (Exception e) {
			return false;
		}
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run(VendasApplication.class);
		JwtService service = contexto.getBean(JwtService.class);
		Usuario usuario = Usuario.builder().login("guizin").build();
		String token = service.generateToken(usuario);
		System.out.println(token);
		
		boolean isTokenValido = service.tokenValido(token);
		System.out.println("O token está válido? " + isTokenValido);
		
		System.out.println(service.obterLoginUsuario(token));
	}
}
