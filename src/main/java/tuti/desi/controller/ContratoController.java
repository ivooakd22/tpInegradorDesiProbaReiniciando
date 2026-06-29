package tuti.desi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.service.ContratoService;
import tuti.desi.service.PropiedadService;
import java.util.List;
import tuti.desi.entity.Inquilino;
import tuti.desi.enums.EstadoContrato;
import tuti.desi.service.InquilinoService;

@Controller
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoService contratoService;
    private final PropiedadService propiedadService;
    private final InquilinoService inquilinoService;

    public ContratoController(ContratoService contratoService,
                              PropiedadService propiedadService,
                              InquilinoService inquilinoService) {
        this.contratoService = contratoService;
        this.propiedadService = propiedadService;
        this.inquilinoService = inquilinoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("contratos", contratoService.findAll(new ListarContratosRequestDTO()));
        return "contratos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("contrato", new ContratoDTO());
        model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
        model.addAttribute("inquilinos", inquilinoService.findAll());
        model.addAttribute("estados", EstadoContrato.values());
        model.addAttribute("titulo", "Nuevo Contrato");
        model.addAttribute("formAction", "/contratos");
        return "contratos/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute ContratoDTO dto, Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            contratoService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Contrato guardado correctamente.");
            return "redirect:/contratos";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("contrato", dto);
            model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
            model.addAttribute("inquilinos", inquilinoService.findAll());
            model.addAttribute("estados", EstadoContrato.values());
            model.addAttribute("titulo", "Nuevo Contrato");
            model.addAttribute("formAction", "/contratos");
            return "contratos/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        ContratoDTO dto = contratoService.findById(id);
        model.addAttribute("contrato", dto);
        model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
        model.addAttribute("inquilinos", inquilinoService.findAll());
        model.addAttribute("estados", EstadoContrato.values());
        model.addAttribute("titulo", "Editar Contrato");
        model.addAttribute("formAction", "/contratos/" + id);
        return "contratos/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute ContratoDTO dto,
                             Model model, RedirectAttributes redirectAttributes) {
        dto.setId(id);
        try {
            contratoService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Contrato actualizado correctamente.");
            return "redirect:/contratos";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("contrato", dto);
            model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
            model.addAttribute("inquilinos", inquilinoService.findAll());
            model.addAttribute("estados", EstadoContrato.values());
            model.addAttribute("titulo", "Editar Contrato");
            model.addAttribute("formAction", "/contratos/" + id);
            return "contratos/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            contratoService.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Contrato eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/contratos";
    }
}
