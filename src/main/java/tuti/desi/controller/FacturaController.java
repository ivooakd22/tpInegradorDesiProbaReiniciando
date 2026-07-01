package tuti.desi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.dto.ListarFacturasRequestDTO;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.FacturaDTO;
import tuti.desi.enums.EstadoFactura;
import tuti.desi.enums.MedioPago;
import tuti.desi.service.ContratoService;
import tuti.desi.service.FacturaService;
import tuti.desi.service.PropiedadService;
import tuti.desi.service.InquilinoService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final ContratoService contratoService;
    private final PropiedadService propiedadService;
    private final InquilinoService inquilinoService;

    public FacturaController(FacturaService facturaService, ContratoService contratoService, PropiedadService propiedadService, InquilinoService inquilinioService) {
        this.facturaService = facturaService;
        this.contratoService = contratoService;
        this.inquilinoService = inquilinioService;
        this.propiedadService = propiedadService;
    }

    @GetMapping
    public String listar(ListarFacturasRequestDTO request, Model model) {
    	model.addAttribute("facturas", facturaService.findAll(new ListarFacturasRequestDTO()));
        model.addAttribute("facturas", facturaService.findAll(request));
        model.addAttribute("contratos", contratoService.findAll(null));
        model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
        model.addAttribute("inquilinos", inquilinoService.findAll());
        model.addAttribute("estados", EstadoFactura.values());
        model.addAttribute("mediosPago", MedioPago.values());
        model.addAttribute("request", request);
        return "facturas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("factura", new FacturaDTO());
        model.addAttribute("contratos", contratoService.findAll(new ListarContratosRequestDTO()));
        model.addAttribute("estados", EstadoFactura.values());
        model.addAttribute("mediosPago", MedioPago.values());
        model.addAttribute("titulo", "Nueva Factura");
        model.addAttribute("formAction", "/facturas");
        return "facturas/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute FacturaDTO dto, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            facturaService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Factura guardada correctamente.");
            return "redirect:/facturas";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("factura", dto);
            ListarContratosRequestDTO request = new ListarContratosRequestDTO();
            model.addAttribute("contratos", contratoService.findAll(request));
            model.addAttribute("estados", EstadoFactura.values());
            model.addAttribute("mediosPago", MedioPago.values());
            model.addAttribute("titulo", "Nueva Factura");
            model.addAttribute("formAction", "/facturas");
            return "facturas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
    	FacturaDTO dto = facturaService.findById(id);
        model.addAttribute("factura", dto);
        ListarContratosRequestDTO request = new ListarContratosRequestDTO();
        model.addAttribute("contratos", contratoService.findAll(request));
        model.addAttribute("estados", EstadoFactura.values());
        model.addAttribute("mediosPago", MedioPago.values());
        model.addAttribute("titulo", "Editar Factura");
        model.addAttribute("formAction", "/facturas/" + id);
        return "facturas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute FacturaDTO dto,
            Model model, RedirectAttributes redirectAttributes) {
        dto.setId(id);
        try {
        	facturaService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Factura actualizada correctamente.");
            return "redirect:/facturas";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("factura", dto);
            ListarContratosRequestDTO request = new ListarContratosRequestDTO();
            model.addAttribute("contratos", contratoService.findAll(request));
            model.addAttribute("estados", EstadoFactura.values());
            model.addAttribute("mediosPago", MedioPago.values());
            model.addAttribute("titulo", "Editar Factura");
            model.addAttribute("formAction", "/facturas/" + id);
            return "facturas/form";
        }
    }    

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            facturaService.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Factura eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/facturas";
    }
}
