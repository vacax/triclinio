package triclinio

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "app", action: "index")
        "/cuenta/obtenerPlato"(controller: 'obtenerPlato', action: 'obtenerPlato')

        "500"(view:'/error')
        "404"(view:'/notFound')

    }
}
