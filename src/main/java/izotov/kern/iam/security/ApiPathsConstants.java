package izotov.kern.iam.security;

public enum ApiPathsConstants {
    
    OPEN_API("/actuator/health",
            "/kern/users",
            "/kern/user/create"
    );
    
    //AUTHENTICATED_API("/kern/users");
    
    final String[] paths;
    
    ApiPathsConstants(String... paths) {
        this.paths = paths;
    }
    
    public String[] paths() {
        return paths;
    }
}
