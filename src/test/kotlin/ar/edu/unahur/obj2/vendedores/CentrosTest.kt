package ar.edu.unahur.obj2.vendedores

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class CentrosTest : DescribeSpec({
    val buenosAires = Provincia(3075646)
    val santaFe = Provincia(3369646)
    val rosario = Ciudad(santaFe)
    val marDelPlata = Ciudad(buenosAires)
    val certificacionOtroTipo = Certificacion(false,30 )
    val vendedorFijo = VendedorFijo(marDelPlata)
    val vendedorFijo2 = VendedorFijo(marDelPlata)
    val centro = Centro(marDelPlata)

    centro.addVendedor(vendedorFijo)
    centro.addVendedor(vendedorFijo2)
    vendedorFijo.agregarCertificacion(certificacionOtroTipo)
    vendedorFijo2.agregarCertificacion(certificacionOtroTipo)

    describe("Se intenta agregar un vendedor que ya pertence al centro") {
        shouldThrowAny { centro.addVendedor(vendedorFijo) }
    }

    describe("Vendedor estrella") {
        centro.vendedorEstrella().shouldBe(vendedorFijo)
    }

    describe("Puede cubrir en") {
        it("En una ciudad dada en que al menos uno de los vendedores registrados pueda trabajar en esa ciudad ") {
            centro.puedeCubrir(marDelPlata).shouldBeTrue()
        }
        it("En una ciudad dada en que al menos uno de los vendedores registrados no pueda trabajar en esa ciudad") {
            centro.puedeCubrir(rosario).shouldBeFalse()
        }
    }

    describe("Coleccion de vendedores genericos") {
        centro.vendedoresGenericos().shouldContain(vendedorFijo)
    }

    describe("Es robusto") {
        val centroRobusto = Centro(marDelPlata)
        val centroNoRobusto = Centro(rosario)
        val vendedorFijo3 = VendedorFijo(rosario)

        vendedorFijo3.agregarCertificacion(certificacionOtroTipo)
        centroRobusto.addVendedor(vendedorFijo)
        centroRobusto.addVendedor(vendedorFijo2)
        centroRobusto.addVendedor(vendedorFijo3)
        centroNoRobusto.addVendedor(vendedorFijo)

        it("Cuando al menos 3 de sus vendedores son firmes") {
            centroRobusto.esRobusto().shouldBeTrue()
        }
        it("Cuando 1 de sus vendedores es firmes") {
            centroNoRobusto.esRobusto().shouldBeFalse()
        }
    }
 })