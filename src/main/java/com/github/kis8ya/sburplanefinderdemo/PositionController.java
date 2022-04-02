package com.github.kis8ya.sburplanefinderdemo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PositionController {

    private final PlaneFinderService planeFinderService;

//    @GetMapping({"/aircraft"})
//    public String get(Model model) {
//        model.addAttribute("aircrafts",  planeFinderService.getAircraft());
//        return "positions";
//    }
}
