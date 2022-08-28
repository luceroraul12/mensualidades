package ar.my.mensualidades.controllers;

import ar.my.mensualidades.models.Factura;
import ar.my.mensualidades.repositories.FacturaRepository;
import ar.my.mensualidades.repositories.PagoRepository;
import ar.my.mensualidades.response.MensualidadResponse;
import ar.my.mensualidades.services.MensualidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class MensualidadController {

    @Autowired MensualidadService mensualidadService;
    @Autowired FacturaRepository facturaRepository;
    @Autowired PagoRepository pagoRepository;



    public MensualidadController(MensualidadService mensualidadService) {
        this.mensualidadService = mensualidadService;
    }

    /**
     * Meotodo por el cual se puede obtener todos las facturas creados
     * @return
     */
    @GetMapping("/facturas")
    public MensualidadResponse getAllFacturas(){
        return mensualidadService.getallFacturas();
    }

    @PostMapping("/facturas")
    public ResponseEntity createFactura(@RequestParam("nombre-servicio") String nombreServicio){
        MensualidadResponse resultado = mensualidadService.createFactura(new Factura(nombreServicio));
        return new ResponseEntity(resultado, HttpStatus.OK);
    }

    @PostMapping("/facturas/update")
    public ResponseEntity createFactura(
            @RequestParam("id") Long id,
            @RequestParam("nombre-servicio") String nombreServicio){

        Factura factura = new Factura();
        factura.setId(id);
        factura.setNombre(nombreServicio);

        MensualidadResponse resultado = mensualidadService.updateFactura(factura);
        return new ResponseEntity(resultado, HttpStatus.OK);
    }

    /**
     * Metodo por el cual se puede obtener todos los pagos creados
     * @return
     */
    @GetMapping("/pagos")
    public MensualidadResponse getAllPagos(){
        return mensualidadService.getAllPagos();
    }

    /**
     * Meotodo por el cual se puede obtener un conjunto de resultados / resumen en función a una fecha
     * @param fecha
     * @return Objeto que contiene: facturas cargadas, facturas faltantes, pagos cargados, costo pagado.
     */
    @GetMapping("/mensual")
    public MensualidadResponse getResultadoMesElegido(@RequestParam("fecha") String fecha){
        return mensualidadService.getResumenMensual(LocalDate.parse(fecha));
    }






}
