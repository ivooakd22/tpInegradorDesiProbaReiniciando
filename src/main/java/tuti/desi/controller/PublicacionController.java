package tuti.desi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.ListarPublicacionesRequestDTO;
import tuti.desi.dto.PublicacionDTO;
import tuti.desi.enums.EstadoPropiedad;
import tuti.desi.enums.EstadoPublicacion;
import tuti.desi.service.PropiedadService;
import tuti.desi.service.PublicacionService;

@Controller
@RequestMapping("/publicaciones")
public class PublicacionController {

    private final PublicacionService publicacionService;
    private final PropiedadService propiedadService;

    public PublicacionController(PublicacionService publicacionService, PropiedadService propiedadService) {
        this.publicacionService = publicacionService;
        this.propiedadService = propiedadService;
    }

    @GetMapping
    public String listar(ListarPublicacionesRequestDTO request, Model model) {
        model.addAttribute("publicaciones", publicacionService.findAll(new ListarPublicacionesRequestDTO()));
        model.addAttribute("publicaciones", publicacionService.findAll(request));
        model.addAttribute("propiedades", propiedadService.findAll(new ListarPropiedadesRequestDTO()));
        model.addAttribute("estados", EstadoPublicacion.values());
        model.addAttribute("request", request);
        return "publicaciones/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("publicacion", new PublicacionDTO());
        ListarPropiedadesRequestDTO request = new ListarPropiedadesRequestDTO();
        request.setEstado(EstadoPropiedad.DISPONIBLE);
        model.addAttribute("propiedades", propiedadService.findAll(request));
        model.addAttribute("estados", EstadoPublicacion.values());
        model.addAttribute("titulo", "Nueva Publicación");
        model.addAttribute("formAction", "/publicaciones");
        return "publicaciones/form";
    }

    @PostMapping
    public String guardar(@ModelAttribute PublicacionDTO dto, Model model,
            RedirectAttributes redirectAttributes) {
        try {
            publicacionService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Publicación guardada correctamente.");
            return "redirect:/publicaciones";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("publicacion", dto);
            ListarPropiedadesRequestDTO request = new ListarPropiedadesRequestDTO();
            request.setEstado(EstadoPropiedad.DISPONIBLE);
            model.addAttribute("propiedades", propiedadService.findAll(request));
            model.addAttribute("estados", EstadoPublicacion.values());
            model.addAttribute("titulo", "Nueva Publicación");
            model.addAttribute("formAction", "/publicaciones");
            return "publicaciones/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        PublicacionDTO dto = publicacionService.findById(id);
        model.addAttribute("publicacion", dto);
        ListarPropiedadesRequestDTO request = new ListarPropiedadesRequestDTO();
        request.setEstado(EstadoPropiedad.DISPONIBLE);
        model.addAttribute("propiedades", propiedadService.findAll(request));
        model.addAttribute("estados", EstadoPublicacion.values());
        model.addAttribute("titulo", "Editar Publicación");
        model.addAttribute("formAction", "/publicaciones/" + id);
        return "publicaciones/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute PublicacionDTO dto,
            Model model, RedirectAttributes redirectAttributes) {
        dto.setId(id);
        try {
            publicacionService.save(dto);
            redirectAttributes.addFlashAttribute("mensajeExito", "Publicación actualizada correctamente.");
            return "redirect:/publicaciones";
        } catch (Exception e) {
            model.addAttribute("mensajeError", e.getMessage());
            model.addAttribute("publicacion", dto);
            ListarPropiedadesRequestDTO request = new ListarPropiedadesRequestDTO();
            request.setEstado(EstadoPropiedad.DISPONIBLE);
            model.addAttribute("propiedades", propiedadService.findAll(request));
            model.addAttribute("estados", EstadoPublicacion.values());
            model.addAttribute("titulo", "Editar Publicación");
            model.addAttribute("formAction", "/publicaciones/" + id);
            return "publicaciones/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            publicacionService.delete(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Publicación eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/publicaciones";
    }
}
