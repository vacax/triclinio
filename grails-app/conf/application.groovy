

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.triclinio.domains.seguridad.Usuario'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.triclinio.domains.seguridad.UsuarioPerfil'
grails.plugin.springsecurity.authority.className = 'com.triclinio.domains.seguridad.Perfil'
grails.plugin.springsecurity.requestMap.className = 'com.triclinio.domains.seguridad.Requestmap'
//grails.plugin.springsecurity.securityConfigType = 'Requestmap'
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
	[pattern: '/',               access: ['IS_AUTHENTICATED_REMEMBERED']],
	[pattern: '/error',          access: ['permitAll']],
	[pattern: '/index',          access: ['IS_AUTHENTICATED_REMEMBERED']],
	[pattern: '/index.gsp',      access: ['IS_AUTHENTICATED_REMEMBERED']],
	[pattern: '/shutdown',       access: ['permitAll']],
	[pattern: '/assets/**',      access: ['permitAll']],
	[pattern: '/webjars/**',      access: ['permitAll']],
	[pattern: '/**/js/**',       access: ['permitAll']],
	[pattern: '/**/css/**',      access: ['permitAll']],
	[pattern: '/**/images/**',   access: ['permitAll']],
	[pattern: '/facturaDetalle/**',   access: ['permitAll']],
	[pattern: '/**/favicon.ico', access: ['permitAll']],
	[pattern: '/login/*', access: ['permitAll']],
	[pattern: '/logout/*', access: ['permitAll']],
	[pattern: '/dbconsole/**', access: ['permitAll']],
	[pattern: '/console/**', access: ['ROLE_ADMIN']],


//	[pattern: '/nuevaCuenta/**', access: ['permitAll']],


]

grails.plugin.springsecurity.filterChain.chainMap = [
	[pattern: '/assets/**',      filters: 'none'],
	[pattern: '/**/js/**',       filters: 'none'],
	[pattern: '/**/css/**',      filters: 'none'],
	[pattern: '/**/images/**',   filters: 'none'],
	[pattern: '/**/favicon.ico', filters: 'none'],
	[pattern: '/**',             filters: 'JOINED_FILTERS']
]

