package tuti.desi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tuti.desi.entity.Inquilino;
import tuti.desi.enums.EstadoContrato;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;
import tuti.desi.service.InquilinoService;

import java.util.List;

@RestController
@RequestMapping("/listados")
public class ListadosController {

    private final InquilinoService inquilinoService;

    public ListadosController(InquilinoService inquilinoService) {
        this.inquilinoService = inquilinoService;
    }

    @GetMapping("/inquilinos")
    public ResponseEntity<List<Inquilino>> findInquilinos() {
        return ResponseEntity.ok(inquilinoService.findAll());
    }

    @GetMapping("/estados-propiedades")
    public ResponseEntity<EstadoPropiedad[]> findEstadosPropiedades() {
        return ResponseEntity.ok(EstadoPropiedad.values());
    }

    @GetMapping("/estados-contratos")
    public ResponseEntity<EstadoContrato[]> findEstadosContratos() {
        return ResponseEntity.ok(EstadoContrato.values());
    }

    @GetMapping("/tipos-propiedad")
    public ResponseEntity<TipoPropiedad[]> findTiposPropiedad() {
        return ResponseEntity.ok(TipoPropiedad.values());
    }
}
