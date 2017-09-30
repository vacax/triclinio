package com.triclinio.domains.seguridad

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.codehaus.groovy.util.HashCodeHelper
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class UsuarioPerfil implements Serializable {

	private static final long serialVersionUID = 1

	Usuario usuario
	Perfil perfil

	@Override
	boolean equals(other) {
		if (other instanceof UsuarioPerfil) {
			other.usuarioId == usuario?.id && other.perfilId == perfil?.id
		}
	}

    @Override
	int hashCode() {
	    int hashCode = HashCodeHelper.initHash()
        if (usuario) {
            hashCode = HashCodeHelper.updateHash(hashCode, usuario.id)
		}
		if (perfil) {
		    hashCode = HashCodeHelper.updateHash(hashCode, perfil.id)
		}
		hashCode
	}

	static UsuarioPerfil get(long usuarioId, long perfilId) {
		criteriaFor(usuarioId, perfilId).get()
	}

	static boolean exists(long usuarioId, long perfilId) {
		criteriaFor(usuarioId, perfilId).count()
	}

	private static DetachedCriteria criteriaFor(long usuarioId, long perfilId) {
		UsuarioPerfil.where {
			usuario == Usuario.load(usuarioId) &&
			perfil == Perfil.load(perfilId)
		}
	}

	static UsuarioPerfil create(Usuario usuario, Perfil perfil, boolean flush = false) {
		def instance = new UsuarioPerfil(usuario: usuario, perfil: perfil)
		instance.save(flush: flush)
		instance
	}

	static boolean remove(Usuario u, Perfil r) {
		if (u != null && r != null) {
			UsuarioPerfil.where { usuario == u && perfil == r }.deleteAll()
		}
	}

	static int removeAll(Usuario u) {
		u == null ? 0 : UsuarioPerfil.where { usuario == u }.deleteAll() as int
	}

	static int removeAll(Perfil r) {
		r == null ? 0 : UsuarioPerfil.where { perfil == r }.deleteAll() as int
	}

	static constraints = {
	    usuario nullable: false
		perfil nullable: false, validator: { Perfil r, UsuarioPerfil ur ->
			if (ur.usuario?.id) {
				if (UsuarioPerfil.exists(ur.usuario.id, r.id)) {
				    return ['userRole.exists']
				}
			}
		}
	}

	static mapping = {
		id composite: ['usuario', 'perfil']
		version false
	}
}
