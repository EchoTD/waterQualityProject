package project.waterQuality.model;

public class JwtResponse {
	private String jwt;

	public JwtResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}
}
