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

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private PropiedadService propiedadService;

    @Autowired
    private InquilinoService inquilinoService;

    // listado
    @GetMapping
    public String listarContratos(Model model) {
        // Traemos todos los contratos usando un filtro vacío
        ListarContratosRequestDTO filtro = new ListarContratosRequestDTO();
        List<ContratoDTO> contratos = contratoService.findAll(filtro);

        model.addAttribute("contratos", contratos);
        return "contratos/lista";
    }

    // alta
    @GetMapping("/crear")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("titulo", "Registrar Nuevo Contrato");
        model.addAttribute("formAction", "/contratos/guardar");
        model.addAttribute("contrato", new ContratoDTO());

        ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
        List<PropiedadDTO> propiedades = propiedadService.findAll(filtroProp);
        model.addAttribute("propiedades", propiedades);

        List<Inquilino> inquilinos = inquilinoService.findAll(); 
        model.addAttribute("inquilinos", inquilinos);

     
        model.addAttribute("estados", EstadoContrato.values());

        return "contratos/form";
    }

    // guardar
    @PostMapping("/guardar")
    public String crearContrato(@Valid @ModelAttribute("contrato") ContratoDTO contratoDTO,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Registrar Nuevo Contrato");
            model.addAttribute("formAction", "/contratos/guardar");
            ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
            model.addAttribute("propiedades", propiedadService.findAll(filtroProp));
            return "contratos/form";
        }

        try {
            contratoService.save(contratoDTO);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Contrato guardado con éxito!");
            return "redirect:/contratos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("titulo", "Registrar Nuevo Contrato");
            model.addAttribute("formAction", "/contratos/guardar");
            model.addAttribute("mensajeError", e.getMessage());
            ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
            model.addAttribute("propiedades", propiedadService.findAll(filtroProp));
            return "contratos/form";
        }
    }

    // eliminar
    @GetMapping("/eliminar/{id}")
    public String eliminarContrato(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            contratoService.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Contrato eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/contratos";
    }
}
