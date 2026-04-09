package izotov.kern.iam.security;

public enum ApiPathsConstants {
    
    OPEN_API("/actuator/health",
            
            "/login",
            "/kern/v1/auth/login",
            
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/webjars/**",
            "/swagger-resources/**"
    ),
    
    OPEN_RESOURCES(
            "/static/**",
            "/js/**",
            "/css/**"),
    
    AUTHENTICATED_API(
            "/kern/v1/users",
            "/kern/v1/user/create");
    
    final String[] paths;
    
    ApiPathsConstants(String... paths) {
        this.paths = paths;
    }
    
    public String[] paths() {
        return paths;
    }
}
