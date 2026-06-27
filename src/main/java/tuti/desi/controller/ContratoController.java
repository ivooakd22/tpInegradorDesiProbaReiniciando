package tuti.desi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import tuti.desi.dto.ContratoDTO;
import tuti.desi.dto.ListarContratosRequestDTO;
import tuti.desi.dto.ListarPropiedadesRequestDTO;
import tuti.desi.dto.PropiedadDTO;
import tuti.desi.service.ContratoService;
import tuti.desi.service.PropiedadService;
import java.util.List;

@Controller
@RequestMapping("/contractos") 
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private PropiedadService propiedadService;

    //alta pantalla
    @GetMapping("/nuevo")
    public String mostrarFormularioAlta(Model model) {

        model.addAttribute("contrato", new ContratoDTO());

        ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
        List<PropiedadDTO> propiedades = propiedadService.findAll(filtroProp);
        model.addAttribute("listaPropiedades", propiedades);

        return "contratos/form";
    }

    //guardar
    @PostMapping("/guardar")
    public String crearContrato(@Valid @ModelAttribute("contrato") ContratoDTO contratoDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
            model.addAttribute("listaPropiedades", propiedadService.findAll(filtroProp));
            return "contratos/form";
        }
        try{
             contratoService.save(contratoDTO);
        return "redirect:/contratos/nuevo";
        } catch(IllegalArgumentException e){
            //por si hay prop no dispo mostramos con model
          model.addAttribute("error",e.getMessage());
          
          //cargamos la lista prop asi el formulariono va vacio
        }ListarPropiedadesRequestDTO filtroProp = new ListarPropiedadesRequestDTO();
        model.addAttribute("listarPropiedades", propiedadService.findAll(filtroProp));

       return "contratos/form";
    }

    //buscar
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ContratoDTO> buscarPorId(@PathVariable Long id) {
        ContratoDTO dto = contratoService.findById(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // eliminar
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> eliminarContrato(@PathVariable Long id) {
    try{  
        contratoService.delete(id);
        return ResponseEntity.noContent().build();
    
    }catch (IllegalArgumentException e){
       return ResponseEntity.badRequest().body(e.getMessage());
        
    }
}
}
