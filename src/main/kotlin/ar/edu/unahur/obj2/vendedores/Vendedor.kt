package ar.edu.unahur.obj2.vendedores

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()

  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  // Devuelve un valor booleano
  // Parametros :
  // ciudad : de tipo Ciudad
  abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

  // En las funciones declaradas con = no es necesario explicitar el tipo
  // Devuelve un valor booleano
  fun esVersatil() =
    certificaciones.size >= 3
      && this.certificacionesDeProducto() >= 1
      && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  // No devuelve nada
  // Parametros :
  // certificacion : de tipo Certificacion
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }

  // Devuelve un valor Booleano
  fun esFirme() = this.puntajeCertificaciones() >= 30

  // Devuelve un numero entero
  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }
  // Devuelve un numero entero
  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }
  // Devuelve un numero entero
  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  abstract fun esInfluyente(): Boolean

  fun esGenerico() = this.otrasCertificaciones() >= 1
}

// En los parámetros, es obligatorio poner el tipo
// Devuelve un valor booleano
// Parametros :
// ciudadDeOrigen : de tipo Ciudad
class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
  //Devuelve un booleano
  // Parametros :
  // ciudad : de tipo Ciudad
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudad == ciudadOrigen
  }

  override fun esInfluyente() = false
}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
  // Devuelve un valor booleano
  // Parametros :
  // ciudad : de tipo Ciudad
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }

  override fun esInfluyente() = this.poblacionTotalProvincias() >= 10000000

  fun poblacionTotalProvincias() = this.provinciasHabilitadas.sumBy { it.poblacion }
}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {
  //Devuelve un valor booleano
  // Parametros :
  // ciudad : de tipo Ciudad
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }

  override fun esInfluyente() = ciudades.size >= 5 || this.provinciasConSucursales().size >= 3
  fun provinciasConSucursales() = ciudades.map { it.provincia }.toSet()

}
