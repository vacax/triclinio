package com.triclinio.domains.seguridad

class Usuario {

    String username
    String password
    String nombre
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    //Datos genericos del dominio.
    String creadoPor = "sistemas";
    String modificadoPor = "sistemas";
    Date dateCreated;
    Date lastUpdated;

    Set<Perfil> getAuthorities() {
        (UsuarioPerfil.findAllByUsuario(this) as List<UsuarioPerfil>)*.perfil as Set<Perfil>
    }

    static constraints = {
        password blank: false, password: true
        username blank: false, unique: true
    }

    static mapping = {
        table 'seg_usuario'
        password column: '`password`'
    }
}
