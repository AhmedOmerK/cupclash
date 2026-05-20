package com.cupclash.controller;

import com.cupclash.service.BracketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/bracket")
public class BracketController {

    private final BracketService bracketService;

    public BracketController(BracketService bracketService) {
        this.bracketService = bracketService;
    }

    // Load the current bracket state and render the tournament tree
    @GetMapping
    public String showBracket(Model model) {
        Map<String, String> slots = bracketService.loadBracket();

        model.addAttribute("slots",        slots);
        model.addAttribute("roundOf32",    BracketService.ROUND_OF_32);
        model.addAttribute("roundOf16",    BracketService.ROUND_OF_16);
        model.addAttribute("quarterFinal", BracketService.QUARTER_FINAL);
        model.addAttribute("semiFinal",    BracketService.SEMI_FINAL);
        model.addAttribute("finalSlot",    BracketService.FINAL_SLOT);
        model.addAttribute("winnerSlot",   BracketService.WINNER_SLOT);

        return "bracket";
    }

    // Receive the entire bracket form and save it
    @PostMapping("/save")
    public String saveBracket(@RequestParam Map<String, String> formData,
                              RedirectAttributes redirectAttributes) {
        // Strip Spring's internal form fields, keep only our slot keys
        formData.remove("_csrf");

        bracketService.saveBracket(formData);

        redirectAttributes.addFlashAttribute("saved", true);
        return "redirect:/bracket";
    }
}
