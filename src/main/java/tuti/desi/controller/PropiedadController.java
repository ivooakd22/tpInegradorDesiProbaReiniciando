package tuti.desi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.TipoPropiedad;
import tuti.desi.service.PropiedadService;
import tuti.desi.service.PropietarioService;

@Controller
@RequestMapping("/propiedades")
public class PropiedadController {

    private final PropiedadService service;
    private final PropietarioService propietarioService;

    public PropiedadController(PropiedadService service, PropietarioService propietarioService) {
        this.service = service;
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public String listar(@ModelAttribute("filtros") ListarPropiedadesRequestDTO filtros, Model model) {
        model.addAttribute("propiedades", service.findAll(filtros));
        model.addAttribute("tipos", TipoPropiedad.values());
        model.addAttribute("estados", EstadoPropiedad.values());
        return "propiedades/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("propiedad", new PropiedadDTO());
        model.addAttribute("tipos", TipoPropiedad.values());
        model.addAttribute("estados", EstadoPropiedad.values());
        model.addAttribute("propietarios", propietarioService.findAll());
        model.addAttribute("titulo", "Nueva Propiedad");
        model.addAttribute("formAction", "/propiedades");
        return "propiedades/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute PropiedadDTO dto, Model model,
                          RedirectAttributes redirectAttributes) {
        try {
            service.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Propiedad guardada correctamente.");
            return "redirect:/propiedades";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("propiedad", dto);
            model.addAttribute("tipos", TipoPropiedad.values());
            model.addAttribute("estados", EstadoPropiedad.values());
            model.addAttribute("propietarios", propietarioService.findAll());
            model.addAttribute("propietarioSeleccionado", dto.getPropietarioId());
            model.addAttribute("titulo", "Nueva Propiedad");
            model.addAttribute("formAction", "/propiedades");
            return "propiedades/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        PropiedadDTO dto = service.findById(id);
        model.addAttribute("propiedad", dto);
        model.addAttribute("tipos", TipoPropiedad.values());
        model.addAttribute("estados", EstadoPropiedad.values());
        model.addAttribute("propietarios", propietarioService.findAll());
        model.addAttribute("propietarioSeleccionado", dto.getPropietarioId());
        model.addAttribute("titulo", "Editar Propiedad");
        model.addAttribute("formAction", "/propiedades/" + id);
        return "propiedades/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute PropiedadDTO dto,
                             Model model, RedirectAttributes redirectAttributes) {
        dto.setId(id);
        try {
            service.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Propiedad actualizada correctamente.");
            return "redirect:/propiedades";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("propiedad", dto);
            model.addAttribute("tipos", TipoPropiedad.values());
            model.addAttribute("estados", EstadoPropiedad.values());
            model.addAttribute("propietarios", propietarioService.findAll());
            model.addAttribute("propietarioSeleccionado", dto.getPropietarioId());
            model.addAttribute("titulo", "Editar Propiedad");
            model.addAttribute("formAction", "/propiedades/" + id);
            return "propiedades/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Propiedad eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/propiedades";
    }
}
