package ar.edu.unahur.obj2.vendedores

import java.lang.Exception

class Centro(ciudad: Ciudad) {
    val vendedores = mutableListOf<Vendedor>()

    fun addVendedor(vendedor: Vendedor) {
        if (this.vendedores.contains(vendedor)) {
            throw Exception("Este vendedor ya trabaja en el centro de distribucion")
        }
        this.vendedores.add(vendedor)
    }

    fun vendedorEstrella() = this.vendedores.maxBy { it.puntajeCertificaciones() }

    fun puedeCubrir(ciudad: Ciudad) = this.vendedores.any { it.puedeTrabajarEn(ciudad) }

    fun vendedoresGenericos() = this.vendedores.filter { it.esGenerico() }

    fun esRobusto() = this.vendedores.count { it.esFirme() } >= 3

}
