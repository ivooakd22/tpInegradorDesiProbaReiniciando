package tuti.desi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.dto.ListarFacturasRequestDTO;
import tuti.desi.dto.FacturaDTO;
import tuti.desi.enums.EstadoFactura;
import tuti.desi.service.ContratoService;
import tuti.desi.service.FacturaService;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final ContratoService contratoService;

    public FacturaController(FacturaService facturaService, ContratoService contratoService) {
        this.facturaService = facturaService;
        this.contratoService = contratoService;
    }

    @GetMapping
    public String listar(ListarFacturasRequestDTO request, Model model) {
        model.addAttribute("facturas", facturaService.findAll(request));
        model.addAttribute("contratos", contratoService.findAll(new ListarContratosRequestDTO()));
        model.addAttribute("estados", EstadoFactura.values());
        model.addAttribute("request", request);
        return "facturas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("factura", new FacturaDTO());
        ListarContratosRequestDTO request = new ListarContratosRequestDTO();
        model.addAttribute("contratos", contratoService.findAll(request));
        model.addAttribute("estados", EstadoFactura.values());
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
            model.addAttribute("titulo", "Nueva Factura");
            model.addAttribute("formAction", "/facturas");
            return "facturas/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
    	FacturaDTO dto = facturaService.findById(id);
        model.addAttribute("publicacion", dto);
        ListarContratosRequestDTO request = new ListarContratosRequestDTO();
        model.addAttribute("propiedades", contratoService.findAll(request));
        model.addAttribute("estados", EstadoFactura.values());
        model.addAttribute("titulo", "Editar Publicación");
        model.addAttribute("formAction", "/publicaciones/" + id);
        return "facturas/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute FacturaDTO dto,
            Model model, RedirectAttributes redirectAttributes) {
        dto.setId(id);
        try {
        	facturaService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Publicación actualizada correctamente.");
            return "redirect:/publicaciones";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("publicacion", dto);
            ListarContratosRequestDTO request = new ListarContratosRequestDTO();
            model.addAttribute("propiedades", contratoService.findAll(request));
            model.addAttribute("estados", EstadoFactura.values());
            model.addAttribute("titulo", "Editar Publicación");
            model.addAttribute("formAction", "/publicaciones/" + id);
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
